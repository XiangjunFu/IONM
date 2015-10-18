package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ListBox;
import com.wanma.client.services.CableService;
import com.wanma.client.services.CableServiceAsync;
import com.wanma.client.services.TerminalWeldJumpService;
import com.wanma.client.services.TerminalWeldJumpServiceAsync;
import com.wanma.domain.Fiber;

public class DeviceTerminalUnit extends DialogBox {

	private static DeviceTerminalUnitUiBinder uiBinder = GWT
			.create(DeviceTerminalUnitUiBinder.class);
	@UiField
	Button cancel;
	@UiField
	Button terminal;
	@UiField
	IntegerBox startPort;
	@UiField
	IntegerBox portStep;
	@UiField
	IntegerBox startFiber;
	@UiField
	IntegerBox fiberStep;
	@UiField
	IntegerBox step;
	@UiField
	ListBox portPreview;
	@UiField
	ListBox fiberPreview;

	private String deviceName = "";
	private String boardName = "";
	private String cableSpanCode;
	
	private CableServiceAsync cableService = GWT.create(CableService.class);
	
	private TerminalWeldJumpServiceAsync terminalWeldJumpService = GWT.create(TerminalWeldJumpService.class);
	
	interface DeviceTerminalUnitUiBinder extends
			UiBinder<Widget, DeviceTerminalUnit> {
	}

	// 界面对象
	private Widget widget = null;

	public DeviceTerminalUnit(String deviceName, String boardName, int tempStarPort,
			int tempStartFiber, String cableSpanCode) {
		if(widget == null){
			widget = uiBinder.createAndBindUi(this);
		}
		setWidget(widget);
		setText("成端...");
		this.deviceName = deviceName;
		this.boardName = boardName;
		this.cableSpanCode = cableSpanCode;
		
		startPort.setEnabled(false);
		startFiber.setEnabled(false);
		startPort.setValue(tempStarPort);
		startFiber.setValue(tempStartFiber);
		
		step.setValue(1);
		setSize("537px", "389px");
		setGlassEnabled(true);
		
		step.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				int steps = event.getValue().intValue();
				int portTemp = portStep.getValue().intValue();
				int fiberTemp = fiberStep.getValue().intValue();
				portPreview.clear();
				fiberPreview.clear();
				for(int i = 0;i< steps;i++){
					int portValue = startPort.getValue().intValue() + portTemp * i;
					int fiberValue = startFiber.getValue().intValue() + fiberTemp * i;
					portPreview.insertItem(portValue + "", i);
					fiberPreview.insertItem(fiberValue + "", i);
				}
			}
		});
		
		portStep.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				int temp = event.getValue().intValue();
				int steps = step.getValue().intValue();
				//在重新设置好步长后，清空数据，进行预览
				portPreview.clear();
				for(int i = 0;i< steps;i++){
					String value = startPort.getValue().intValue() + temp * i + "";
					portPreview.insertItem(value, i);
				}
			}
		});
		
		fiberStep.addValueChangeHandler(new ValueChangeHandler<Integer>() {
			@Override
			public void onValueChange(ValueChangeEvent<Integer> event) {
				int temp = event.getValue().intValue();
				int steps = step.getValue().intValue();
				//在重新设置好步长后，清空数据，进行预览
				fiberPreview.clear();
				for(int i = 0;i<steps;i++){
					String value = startFiber.getValue().intValue() + temp * i + "";
					fiberPreview.insertItem(value, i);
				}
			}
		});
	}
	
	Fiber tempFiber = null;
	@UiHandler("terminal")
	void onTerminalClick(ClickEvent event) {
		/**
		 * 保存映射关系进入数据库，fiber表
		 * 成端关系：
		 * 设备：aMeCode:A端设备编码，aMeType：类型，aPortCode：端子
		 * 光纤：zMeCode：Z端设备编码，zMeType：类型，zPortCode：端子
		 * 
		 * 成端关系：
		 * ID、类型
		 * A端：设备类型、设备编码、端子号
		 * Z端：设备编码、设备类型、端子号
		 * 修改：
		 * 关系表、端口表
		 * 
		 */
		
		int aStartPort = startPort.getValue().intValue();
		int zStartFiber = startFiber.getValue().intValue();
		
		int aStep = portStep.getValue().intValue();
		int zStep = fiberStep.getValue().intValue();
		for(int i = 0;i<step.getValue().intValue();i++){
			tempFiber = null;
			//光纤逻辑编码
			int logicalNo = zStartFiber + i * zStep;
			
			//端子编码：设备编码-板卡名-端子号
			//设备名称：deviceName
			//板卡名称：boardName
			//端子号
			int portNo = aStartPort + i * aStep;
			String aPortCode = deviceName + "_" + boardName + "_" + portNo;
			cableService.updateFiberByLogicalNo(cableSpanCode, logicalNo,deviceName,aPortCode, new AsyncCallback<Integer>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("成端失败！");
				}
				@Override
				public void onSuccess(Integer result) {
					if(result.intValue() > 0){
						hide();
					}else{
						Window.alert("失败！");
					}
				}
			});
			
			//修改端子状态
			String serviceStatus = "1";
			terminalWeldJumpService.updatePortInfo(aPortCode, serviceStatus, new AsyncCallback<Integer>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("端口状态改变失败！");
				}
				@Override
				public void onSuccess(Integer result) {
					if(result.intValue() <= 0){
						Window.alert("端口状态改变失败！");
					}
				}
			});
		}
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
}
