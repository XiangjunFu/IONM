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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.wanma.client.services.MachineRoomService;
import com.wanma.client.services.MachineRoomServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.client.worklist.config.AddWorkListDialogBox;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Ifat;
import com.wanma.domain.Station;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.DoubleBox;

public class IFATInfo extends DialogBox {

	private static IFATInfoUiBinder uiBinder = GWT
			.create(IFATInfoUiBinder.class);
	@UiField
	Button cancel;
	@UiField
	Button save;
	@UiField TextBox ifatName;
	@UiField TextBox ifatCode;
	@UiField TextBox assemblyType;
	@UiField TextBox assemblyCode;
	@UiField TextBox manufacturer;
	@UiField TextBox insalledAddress;
	@UiField TextBox dutyPerson;
	@UiField ListBox serviceStatus;
	@UiField ListBox phyStatus;
	@UiField IntegerBox capacity;
	@UiField IntegerBox insalledCapacity;
	@UiField ListBox station;
	@UiField ListBox hostRoom;
	@UiField CheckBox toConfig;
	@UiField IntegerBox xPosition;
	@UiField IntegerBox yPosition;
	@UiField ListBox isIntegrate;

	private Ifat ifat = null;
	
	public boolean isAddSuccess =false;
	
	interface IFATInfoUiBinder extends UiBinder<Widget, IFATInfo> {
	}

	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);
	
	
	public IFATInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		isIntegrate.insertItem("", 0);
		isIntegrate.insertItem("是", 1);
		isIntegrate.insertItem("否", 2);
		
		serviceStatus.insertItem("0", 0);
		serviceStatus.insertItem("1", 1);
		
		for(int i = 0;i<Constants.deviceStatus.length;i++){
			phyStatus.insertItem(Constants.deviceStatus[i], i);
		}
		//局站编码获取：所有局站信息
		stationService.getStations(stationCallback);
		
		station.addChangeHandler(changeHandler);
		
		setText("添加光分纤盒...");
		setSize("560px", "430px");
		setGlassEnabled(true);
	}

	public IFATInfo(int xPosition,int yPosition) {
		setWidget(uiBinder.createAndBindUi(this));
		
		isIntegrate.insertItem("", 0);
		isIntegrate.insertItem("是", 1);
		isIntegrate.insertItem("否", 2);
		
		serviceStatus.insertItem("0", 0);
		serviceStatus.insertItem("1", 1);
		
		for(int i = 0;i<Constants.deviceStatus.length;i++){
			phyStatus.insertItem(Constants.deviceStatus[i], i);
		}
		//局站编码获取：所有局站信息
		stationService.getStations(stationCallback);
		
		station.addChangeHandler(changeHandler);
		
		this.xPosition.setValue(xPosition);
		this.yPosition.setValue(yPosition);
		
		this.xPosition.setEnabled(false);
		this.yPosition.setEnabled(false);
		
		setText("添加光分纤盒...");
		setSize("560px", "430px");
		setGlassEnabled(true);
	}
	
	private AsyncCallback<List<Station>> stationCallback = new AsyncCallback<List<Station>>(){
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！");
		}
		@Override
		public void onSuccess(List<Station> result) {
			if(result.size() > 0){
				station.insertItem("", 0);
				for(int i = 0;i<result.size();i++){
					Station sation = result.get(i);
					station.insertItem(sation.getName() + "#" + sation.getCode(), i+1);
				}
			}else{
				Window.alert("未获取到局站信息...");
			}
		}
	};
	
	private ChangeHandler changeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			if(station.getSelectedIndex() <= 0){
				Window.alert("请选择局站...");
				return;
			}
			String stationName = station.getItemText(station.getSelectedIndex()).split("#")[0];
			//机房编码获取：
			machineRoomService.getMachineRoomInfosByStationName(stationName,new AsyncCallback<List<HostRoom>>() {
				@Override
				public void onFailure(Throwable caught) {
					Window.alert("获取信息失败！");
				}
				@Override
				public void onSuccess(List<HostRoom> result) {
					if(result.size() > 0){
						for(int i = 0;i<result.size();i++){
							HostRoom hr = result.get(i);
							hostRoom.insertItem(hr.getName() + "#" + hr.getCode(), i);
						}
					}else{
						Window.alert("未获取到所选局站的机房信息...");
					}
				}
			});
		}
	};
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		if(isIntegrate.getSelectedIndex() == 0){
			Window.alert("选择是否光设备！！！");
			isIntegrate.setFocus(true);
			return;
		}
		
		String tempIfatName = ifatName.getText();
		String tempIfatCode = ifatCode.getText();
		String tempStationCode = station.getItemText(station.getSelectedIndex()).split("#")[1];
		String tempHostRoomCode = hostRoom.getItemText(hostRoom.getSelectedIndex()).split("#")[1];
		
		if(StringVerifyUtils.verifyEmpty(tempIfatName) && StringVerifyUtils.verifyEmpty(tempIfatCode)
				&& StringVerifyUtils.verifyEmpty(tempStationCode) && StringVerifyUtils.verifyEmpty(tempHostRoomCode)){
			String tempAssemblyType = assemblyType.getText();
			String tempAssemblyCode = assemblyCode.getText();
			String tempManufacturer = manufacturer.getText();
			int tempCapacity = capacity.getValue().intValue();
			int tempInstalledCapacity = insalledCapacity.getValue().intValue();
			String tempInsalledAddress = insalledAddress.getText();
			String tempDutyPerson = dutyPerson.getText();
			String tempServiceStatus = serviceStatus.getItemText(serviceStatus.getSelectedIndex());
			String tempPhyStatus = phyStatus.getItemText(phyStatus.getSelectedIndex());
			ifat = new Ifat();
			ifat.setName(tempIfatName);
			ifat.setCode(tempIfatCode);
			ifat.setCapacity(tempCapacity);
			ifat.setInsalledCapacity(tempInstalledCapacity);
			ifat.setAssemblyType(tempAssemblyType);
			ifat.setAssemblyNumber(tempAssemblyCode);
			ifat.setDutyPerson(tempDutyPerson);
			ifat.setHostRoomCode(tempHostRoomCode);
			ifat.setStationCode(tempStationCode);
			ifat.setInstallation(tempInsalledAddress);
			ifat.setManufacturer(tempManufacturer);
			ifat.setServicdeStatus(tempServiceStatus);
			ifat.setPhyStatus(tempPhyStatus);
			ifat.setType(isIntegrate.getSelectedIndex() == 1?"IFAT":"FAT");
			ifat.setXpositon(xPosition.getValue().intValue());
			ifat.setYpositon(yPosition.getValue().intValue());
			opticalDeviceService.addIFAT(ifat,addIfatCallback);
			
		}else{
			Window.alert("部分字段不能为空，请检查...");
			return;
		}
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	//添加Ifat回调对象
	private AsyncCallback<Integer> addIfatCallback = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！");
			hide();
		}
		@Override
		public void onSuccess(Integer result) {
			if(result != -1){
				Window.alert("添加成功！");
				hide();
				isAddSuccess = true;
				if(toConfig.getValue()){
					AddWorkListDialogBox addWorkList = new AddWorkListDialogBox(ifatCode.getText());
					addWorkList.center();
					addWorkList.show();
				}
			}else{
				Window.alert("添加失败！");
				hide();
			}
		}
	};
}
