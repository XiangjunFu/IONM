package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IntegerBox;
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.client.services.TerminalWeldJumpServiceAsync;
import com.wanma.domain.TerminalUnit;

public class SelectBoard extends DialogBox {

	private static SelectBoardUiBinder uiBinder = GWT
			.create(SelectBoardUiBinder.class);
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	IntegerBox originalFrame;
	@UiField
	IntegerBox frameNum;
	@UiField
	IntegerBox rowNum;
	@UiField
	IntegerBox colNum;

	private String deviceName = "";

	interface SelectBoardUiBinder extends UiBinder<Widget, SelectBoard> {
	}

	private TerminalWeldJumpServiceAsync terminalWeldJumpService = GWT
			.create(TerminalWeldJumpService.class);

	DeviceCableSelect deviceCableSelect = null;
	// 界面对象
	private Widget widget = null;

	public SelectBoard(String deviceName) {
		if(widget == null){
			widget = uiBinder.createAndBindUi(this);
		}
		setWidget(widget);

		this.deviceName = deviceName;
		Window.alert(deviceName);
		setText("新增模块...");
		setSize("347px", "251px");
		setGlassEnabled(true);
	}

	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		Integer tempOriginalFrame = originalFrame.getValue();
		Integer tempFrameNum = frameNum.getValue();
		Integer tempRowNum = rowNum.getValue();
		Integer tempColNum = colNum.getValue();
		if (tempOriginalFrame == null || tempFrameNum == null
				|| tempRowNum == null || tempColNum == null) {
			Window.alert("部分字段不能为空！");
			return;
		}
		//机框号
		TerminalUnit terminalUnit = new TerminalUnit();
		terminalUnit.setColumnNumber(tempColNum);
		terminalUnit.setRowNumber(tempRowNum);
		terminalUnit.setHostDeviceId(deviceName);
		terminalUnit.setTerminalNum(tempColNum * tempRowNum);
		terminalUnit.setOccupiedNum(0);//刚创建均未使用
		terminalUnit.setDamagedNum(0);//刚创建均未损坏
		terminalUnit.setFrameNum(tempFrameNum);
		terminalUnit.setOriginalFrame(tempOriginalFrame);

		//获取设备相关端口端子模块Id，设置端口信息：设备号、机框
		terminalWeldJumpService.addNewModule(terminalUnit,
				new AsyncCallback<String>() {
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("添加新模块失败！");
					}

					@Override
					public void onSuccess(String result) {
						if (result != null) {
							Window.alert("模块添加成功...");
							hide();
							// 如果deviceCableSelect为null，new一个
							if (deviceCableSelect == null) {
								deviceCableSelect = new DeviceCableSelect(
										deviceName);
							}
							if (!deviceCableSelect.isVisible()) {// 显示该对话框
								deviceCableSelect.center();
								deviceCableSelect.show();
							}
						}else{
							Window.alert("模块添加失败！");
						}
					}
				});
		/*
		 * 1.将模块信息保存 是否要保存 2.选择设备及其起始端子 选择光纤及其起始光纤
		 */
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
