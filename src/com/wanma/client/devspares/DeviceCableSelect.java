package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.client.services.TerminalWeldJumpServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.CableSpan;
import com.wanma.domain.TerminalUnit;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;

public class DeviceCableSelect extends DialogBox {

	private static DeviceCableSelectUiBinder uiBinder = GWT
			.create(DeviceCableSelectUiBinder.class);
	@UiField
	Button cancel;
	@UiField
	Button ok;
	@UiField
	IntegerBox startPort;
	@UiField
	IntegerBox startFiber;
	@UiField
	ListBox board;
	@UiField
	ListBox cableSpan;

	private String deviceName = "";

	interface DeviceCableSelectUiBinder extends
			UiBinder<Widget, DeviceCableSelect> {
	}

	private TerminalWeldJumpServiceAsync terminalWeldJumpService = GWT
			.create(TerminalWeldJumpService.class);

	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private CableServiceAsync cableService = GWT.create(CableService.class);
	
	DeviceTerminalUnit unit = null;

	// 界面对象
	private Widget widget = null;

	public DeviceCableSelect(String deviceName) {
		if (widget == null) {
			widget = uiBinder.createAndBindUi(this);
		}
		setWidget(widget);
		this.deviceName = deviceName;
		
		//获取设备名为deviceName的设备上的板卡列表
		opticalDeviceService.getBoards(deviceName,new AsyncCallback<List<TerminalUnit>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
			}
			@Override
			public void onSuccess(List<TerminalUnit> result) {
				if(result.size() > 0){
					for(int i = 0;i < result.size();i++){
						board.insertItem(result.get(i).getTerminalUnitName(), i);
					}
				}
			}
		});
		
		//获取所有光缆段信息列表
		cableService.getAllCableSpan(new AsyncCallback<List<CableSpan>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("获取信息失败！");
			}
			@Override
			public void onSuccess(List<CableSpan> result) {
				if(result.size() > 0){
					for(int i = 0;i<result.size();i++){
						cableSpan.insertItem(result.get(i).getName(), i);
					}
				}else{
					Window.alert("请先添加光缆段...");
				}
			}
		});
		
		
		setText("成端...");
		setSize("450px", "178px");
		setGlassEnabled(true);
	}

	@UiHandler("ok")
	void onOkClickEvent(ClickEvent event) {
		Integer tempStartPort = startPort.getValue();
		Integer tempStartFiber = startFiber.getValue();
		String tempBoard = board.getItemText(board.getSelectedIndex());
		String tempCableCode = cableSpan.getItemText(cableSpan
				.getSelectedIndex());
		if (!StringVerifyUtils.verifyEmpty(deviceName) || tempStartPort == null
				|| tempStartFiber == null
				|| !StringVerifyUtils.verifyEmpty(tempCableCode)) {
			Window.alert("部分字段不能为空！请检查...");
			return;
		}

		/**
		 * 端子模块属于设备
		 */
		// 应该是选择的端子模块

		// TerminalUnit terminalUnit = null;

		this.hide();
		if (unit == null) {
			unit = new DeviceTerminalUnit(deviceName, tempBoard,
					tempStartPort.intValue(), tempStartFiber.intValue(),
					tempCableCode);
		}
		//if (!unit.isVisible()) {
		unit.center();
		unit.show();
		//}
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
