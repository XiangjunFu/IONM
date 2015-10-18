package com.wanma.client.worklist.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.WorkListService;
import com.wanma.client.services.WorkListServiceAsync;
import com.wanma.domain.ConfigTask;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Iodf;
import com.google.gwt.user.client.ui.Button;

public class AddIODFConfigTask extends  DialogBox{

	private static AddIODFConfigTaskUiBinder uiBinder = GWT
			.create(AddIODFConfigTaskUiBinder.class);
	@UiField TextBox configType;
	@UiField TextBox deviceCode;
	@UiField TextBox deviceName;
	@UiField TextBox address;
	@UiField TextBox station;
	@UiField TextBox hostRoom;
	@UiField TextBox ip;
	@UiField TextBox subnetMask;
	@UiField TextBox defaultGateway;
	@UiField TextBox TemperatureThreshold;
	@UiField TextBox snmpParam;
	@UiField TextBox configTaskName;
	@UiField TextBox configTaskCode;
	@UiField Button save;
	@UiField Button cancel;

	interface AddIODFConfigTaskUiBinder extends
			UiBinder<Widget, AddIODFConfigTask> {
	}
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);

	private WorkListServiceAsync workListService = GWT.create(WorkListService.class);
	
	public AddIODFConfigTask(){
		setWidget(uiBinder.createAndBindUi(this));
		
		setSize("600px", "400px");
		setGlassEnabled(true);
	}
	
	public AddIODFConfigTask(String iodfCode) {
		setWidget(uiBinder.createAndBindUi(this));
		
		opticalDeviceService.getDeviceCommonInfosByDeviceCode(iodfCode, deviceCommonInfoCallback);
		setSize("600px", "400px");
		setGlassEnabled(true);
	}
	
	private AsyncCallback<DeviceCommonInfo> deviceCommonInfoCallback = new AsyncCallback<DeviceCommonInfo>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(DeviceCommonInfo device) {
			if(device != null){
				configType.setText(device.getDeviceType());
				deviceCode.setText(device.getDeviceCode());
				deviceName.setText(device.getDeviceName());
				//address.setText(device.getAddress());
				station.setText(device.getStationCode());
				hostRoom.setText(device.getHostRoomCode());
			}
		}
	};
	
	@UiHandler("save")
	public void onSaveClick(ClickEvent event){
		//生成该设备配置工单，将IP等信息更新如设备。
		ConfigTask configTask = new ConfigTask();
		
		configTask.setCode(configTaskCode.getText());
		configTask.setName(configTaskName.getText());
		configTask.setDeviceCode(deviceCode.getText());
		configTask.setDeviceName(deviceName.getText());
		configTask.setDeviceType(configType.getText());
		configTask.setStatus((short)1);//刚生成工单，1 代表未指派
		
		workListService.addConfigTask(configTask, addConfigTaskCallback);
	}
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event){
		this.hide();
	}

	private AsyncCallback<String> addConfigTaskCallback = new AsyncCallback<String>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.getMessage());
		}
		@Override
		public void onSuccess(String result) {
			if(result != null){
				hide();
			}else{
				Window.alert("保存失败！！！");
			}
		}
	};
	
}
