package com.wanma.client.worklist.business;

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
import com.wanma.domain.Port;
import com.wanma.domain.Station;
import com.wanma.domain.TerminalUnit;

public class SelectDevice extends DialogBox {

	private static SelectDeviceUiBinder uiBinder = GWT
			.create(SelectDeviceUiBinder.class);
	@UiField ListBox district;
	@UiField ListBox station;
	@UiField ListBox hostroom;
	@UiField ListBox device;
	@UiField ListBox port;
	@UiField Button select;
	@UiField Button cancel;
	@UiField ListBox boards;

	interface SelectDeviceUiBinder extends UiBinder<Widget, SelectDevice> {
	}

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private TextBox routeAName = null;
	
	private TextBox routeZName = null;
	
	private String mark = "";
	
	private AddBusinessWorkList add;
	
	private AddRouteWorkList addRoute;
	
	private AddTestRouteWorkList addTest = null;
	
	public SelectDevice(AddBusinessWorkList add,String mark) {
		setWidget(uiBinder.createAndBindUi(this));
		
		this.mark = mark;
		this.add = add;
		this.addRoute = null;
		this.addTest = null;
		setSize("390px", "317px");
		setGlassEnabled(true);
		
		initListBoxValue();
		initListBoxChangeEvent();
	}
	
	public SelectDevice(AddRouteWorkList addRoute,String mark) {
		setWidget(uiBinder.createAndBindUi(this));
		this.mark = mark;
		this.addRoute = addRoute;
		this.add = null;
		this.addTest = null;
		setSize("390px", "317px");
		setGlassEnabled(true);
		
		initListBoxValue();
		initListBoxChangeEvent();
	}
	
	public SelectDevice(AddTestRouteWorkList addTestRoute,String mark){
		setWidget(uiBinder.createAndBindUi(this));
		this.mark = mark;
		this.addRoute = null;
		this.add = null;
		this.addTest = addTestRoute;
		setSize("390px", "317px");
		setGlassEnabled(true);
		
		initListBoxValue();
		initListBoxChangeEvent();
	}
	
	private void initListBoxValue(){
		stationService.getDistricts(districtsCallback);
		stationService.getStations(stationsCallback);
		machineRoomService.getMachineRoomInfos(hostRoomCallback);
		opticalDeviceService.getAllDeviceInfo(deviceCallback);
		opticalDeviceService.getBoardsByHostDeviceID("",boardCallback);
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
		device.addChangeHandler(deviceChnageHandler);
		boards.addChangeHandler(boardChnageHandler);
	}
	
	@UiHandler("select")//提交
	public void onSelectClick(ClickEvent event){
		//将设备信息 端口信息设置为添加界面中
		String deviceNameCode = device.getItemText(device.getSelectedIndex());
		if("".equals(deviceNameCode)){
			Window.alert("请选择设备....");
			return;
		}
		//返回信息应该包括设备名称,设备编码,板卡编码,端口编码。格式：设备名称,设备编码,板卡编码,端口编码
		String boardNum = boards.getItemText(boards.getSelectedIndex());
		String portCode = port.getItemText(port.getSelectedIndex());
		String text = deviceNameCode + "," + boardNum + "," + portCode;
		if("AB".equals(mark)){
			add.setADeviceText(text);
			this.hide();
		}else if("ZB".equals(mark)){
			add.setZDeviceText(text);
			this.hide();
		}
		if("AR".equals(mark)){
			this.addRoute.setADeviceText(text);
			this.hide();
		}else if("ZR".equals(mark)){
			this.addRoute.setZDeviceText(text);
			this.hide();
		}
		if("TESTA".equalsIgnoreCase(mark)){
			this.addTest.setARouteText(text);
			this.hide();
		}else if("TESTZ".equalsIgnoreCase(mark)){
			this.addTest.setZRouteText(text);
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
					device.insertItem(deviceInfo.getDeviceName() + "," + deviceInfo.getDeviceCode(), i+1);
				}
			}
		}
	};
	
	//设备列表变动，引起板卡列表变动
	private ChangeHandler deviceChnageHandler  = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = device.getSelectedIndex();
			String deviceNameCode = device.getItemText(index);
			if("".equals(deviceNameCode)){
				port.clear();
				Window.alert("请选择设备...");
				opticalDeviceService.getBoardsByHostDeviceID("",boardCallback);
				return;
			}
			//获取所选区域下局站,并填充列表
			String deviceCode = deviceNameCode.split(",")[1];
			opticalDeviceService.getBoardsByHostDeviceID(deviceCode,boardCallback);
		}
	};
	
	
	private AsyncCallback<List<TerminalUnit>> boardCallback = new AsyncCallback<List<TerminalUnit>>() {
		@Override
		public void onFailure(Throwable caught) {
			//Window.alert("获取信息失败！！");
		}
		@Override
		public void onSuccess(List<TerminalUnit> result) {
			if(result != null){
				boards.clear();
				boards.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					TerminalUnit board = result.get(i);
					//板卡编码
					boards.insertItem(board.getTerminalUnitCode(), i+1);
				}
			}
		}
	};
	
	
	//设备列表变动，引起板卡列表变动
	private ChangeHandler boardChnageHandler  = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = boards.getSelectedIndex();
			String code = boards.getItemText(index);
			if("".equals(code)){
				port.clear();
				Window.alert("请选择板卡...");
				//查询所有空闲端口：板卡为"",状态为"1"
				opticalDeviceService.getPortsByBoardCode("","1",portCallback);
				return;
			}
			//String deviceCode = deviceNameCode.split(",")[1];
			//查找指定板卡的所有空闲端口
			opticalDeviceService.getPortsByBoardCode(code,"1",portCallback);
		}
	};
	
	
	private AsyncCallback<List<Port>> portCallback = new AsyncCallback<List<Port>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<Port> result) {
			if(result != null){
				port.clear();
				for(int i = 0;i<result.size();i++){
					Port p = result.get(i);
					//端口编码
					port.insertItem(p.getCode(), i);
				}
			}
		}
	};

	
}
