package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.MachineRoomService;
import com.wanma.client.services.MachineRoomServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.District;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Station;

public class SelectDevFrameBoard extends DialogBox {

	private static SelectDevFrameBoardUiBinder uiBinder = GWT
			.create(SelectDevFrameBoardUiBinder.class);
	@UiField ListBox district;
	@UiField ListBox station;
	@UiField ListBox hostroom;
	@UiField ListBox device;
	@UiField Button select;
	@UiField Button cancel;

	interface SelectDevFrameBoardUiBinder extends UiBinder<Widget, SelectDevFrameBoard> {
	}

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private TextBox deviceName = null;
	
	private AddBoardInfo addBoardInfo = null;
	
	private AddFrameInfo addFrameInfo = null;
	
	public SelectDevFrameBoard(TextBox deviceName,AddFrameInfo addFrameInfo) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.deviceName = deviceName;
		this.addFrameInfo = addFrameInfo;
		this.addBoardInfo = null;
		setSize("440px", "277px");
		setGlassEnabled(true);
		
		initListBoxValue();
		initListBoxChangeEvent();
	}
	
	public SelectDevFrameBoard(TextBox deviceName,AddBoardInfo addBoardInfo) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.deviceName = deviceName;
		this.addBoardInfo = addBoardInfo;
		this.addFrameInfo = null;
		setSize("440px", "277px");
		setGlassEnabled(true);
		
		initListBoxValue();
		initListBoxChangeEvent();
	}
	
	private void initListBoxValue(){
		stationService.getDistricts(districtsCallback);
		stationService.getStations(stationsCallback);
		machineRoomService.getMachineRoomInfos(hostRoomCallback);
		opticalDeviceService.getAllDeviceInfo(deviceCallback);
	}
	
	//获取区域列表  返回调用
	private AsyncCallback<List<District>> districtsCallback = new AsyncCallback<List<District>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<District> result) {
			if(result != null){
				district.clear();
				district.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					District dis = result.get(i);
					district.insertItem(dis.getDistrictName(),i+1);
				}
			}
		}
	};
	
	private void initListBoxChangeEvent(){
		district.addChangeHandler(districtChangeEvent);
		station.addChangeHandler(stationChangeHandler);
		hostroom.addChangeHandler(hostroomChangeHandler);
	}
	
	@UiHandler("select")//提交
	public void onSelectClick(ClickEvent event){
		//将设备信息 端口信息设置为添加界面中
		String deviceNameCode = device.getItemText(device.getSelectedIndex());
		if("".equals(deviceNameCode)){
			Window.alert("请选择设备....");
		}
		//返回信息应该包括name#code。格式：name#code
		if(this.addBoardInfo != null){
			addBoardInfo.setDevice(deviceNameCode);
			this.hide();
		}else if(this.addFrameInfo != null){
			this.addFrameInfo.setDevice(deviceNameCode);
			this.hide();
		}
	}
	
	@UiHandler("cancel")//关闭
	public void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	//区域列表 变动事件，引起局站列表变动
	private ChangeHandler districtChangeEvent = new ChangeHandler(){
		@Override
		public void onChange(ChangeEvent event) {
			int index = district.getSelectedIndex();
			String disName = district.getItemText(index);
			if("".equals(disName)){
				//"",所有局站
				stationService.getStations(stationsCallback);
				return;
			}
			//获取所选区域下局站,并填充列表
			stationService.getStationsByDistName(disName,stationsCallback);
		}
	};
	
	private AsyncCallback<List<Station>> stationsCallback = new AsyncCallback<List<Station>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<Station> result) {
			if(result != null){
				station.clear();
				station.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					Station sta = result.get(i);
					station.insertItem(sta.getName(), i+1);
				}
			}
		}
	};
	
	//局站列表变动，引起机房列表变动
	private ChangeHandler stationChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = station.getSelectedIndex();
			String stationName = station.getItemText(index);
			if("".equals(stationName)){
				machineRoomService.getMachineRoomInfos(hostRoomCallback);
				return;
			}
			//获取所选区域下局站,并填充列表
			machineRoomService.getHostRoomsByStationName(stationName,hostRoomCallback);
		}
	};
	
	private AsyncCallback<List<HostRoom>> hostRoomCallback = new AsyncCallback<List<HostRoom>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<HostRoom> result) {
			if(result != null){
				hostroom.clear();
				hostroom.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					HostRoom hostRoom = result.get(i);
					hostroom.insertItem(hostRoom.getName(), i+1);
				}
			}
		}
	};
	
	//机房列表变动，引起设备列表变动
	private ChangeHandler hostroomChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = hostroom.getSelectedIndex();
			String hostRoomName = hostroom.getItemText(index);
			if("".equals(hostRoomName)){
				opticalDeviceService.getAllDeviceInfo(deviceCallback);
				return;
			}
			//获取所选区域下局站,并填充列表
			opticalDeviceService.getDeviceInfoByHostRoomName(hostRoomName,deviceCallback);
		}
	};
	
	private AsyncCallback<List<DeviceCommonInfo>> deviceCallback = new AsyncCallback<List<DeviceCommonInfo>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<DeviceCommonInfo> result) {
			if(result != null){
				device.clear();
				device.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					DeviceCommonInfo deviceInfo = result.get(i);
					device.insertItem(deviceInfo.getDeviceName() + "#" + deviceInfo.getDeviceCode(), i+1);
				}
			}
		}
	};
}
