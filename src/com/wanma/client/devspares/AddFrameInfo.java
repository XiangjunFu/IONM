package com.wanma.client.devspares;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.domain.Frame;

public class AddFrameInfo extends DialogBox {

	private static AddFrameInfoUiBinder uiBinder = GWT
			.create(AddFrameInfoUiBinder.class);
	@UiField Button cancel;
	@UiField Button close;
	@UiField Button save;
	@UiField TextBox frameName;
	@UiField IntegerBox boardNum;
	@UiField IntegerBox portNum;
	@UiField TextBox device;//设备名称#设备编码
	@UiField Button selectDevice;
	@UiField TextBox frameCode;
	@UiField IntegerBox frameSeq;

	interface AddFrameInfoUiBinder extends UiBinder<Widget, AddFrameInfo> {
	}

	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	public AddFrameInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		setSize("450px", "250px");
		setGlassEnabled(true);
		
		frameCode.setEnabled(false);
		
		frameSeq.addChangeHandler(frameSeqChangeHandler);
	}
	
	private ChangeHandler frameSeqChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			String deviceCode = device.getText().split("#")[1];
			frameCode.setText(deviceCode + "/" + frameSeq.getText());
		}
	};
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		Frame frame = new Frame();
		
		//String code = device.getText() + "-" + frameSeq.getValue();
		
		frame.setCode(frameCode.getText());//机框编码：设备编码/机框序号
		frame.setBoardsCapacity(boardNum.getValue());
		frame.setFrameName(frameName.getText());
		frame.setHostCabinet(device.getText().split("#")[1]);
		frame.setPortsCapacity(portNum.getValue());
		frame.setNumberInCabinet(frameSeq.getValue());
		frame.setUsedBoardsNumber(0);//新建
		frame.setUsedPortsNumber(0);//新建
		frame.setStatus(0+"");//0未管理状态
		
		opticalDeviceService.addFrame(frame,addFrameCallback);
	}
	
	private AsyncCallback<Integer> addFrameCallback = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！！！");
		}
		@Override
		public void onSuccess(Integer result) {
			if(result == null || result.intValue() == -1){
				Window.alert("添加失败！！！");
			}else{
				hide();
			}
		}
	};
	
	@UiHandler("selectDevice")
	void onSelectDeviceClick(ClickEvent event){
		SelectDevFrameBoard selectDevFrameBoard = new SelectDevFrameBoard(device,this);
		selectDevFrameBoard.center();
		selectDevFrameBoard.show();
	}
	
	@UiHandler("close")
	void onCloseClick(ClickEvent event){
		this.hide();
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide(true);
	}
	
	public void setDevice(String device){
		this.device.setText(device);
	}
}
