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
import com.wanma.client.worklist.config.AddIODFConfigTask;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Iodf;
import com.wanma.domain.Station;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.IntegerBox;

public class IODFInfo extends DialogBox {

	private static IODFInfoUiBinder uiBinder = GWT
			.create(IODFInfoUiBinder.class);
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	TextBox manufacturer;
	@UiField
	TextBox iodfName;
	@UiField
	TextBox iodfCode;
	@UiField
	TextBox assemblyType;
	@UiField
	TextBox assemblyCode;
	@UiField
	TextBox type;
	@UiField
	TextBox installedAddress;
	@UiField
	TextBox dutyPerson;
	@UiField
	ListBox structStatus;
	@UiField
	ListBox phyStatus;
	@UiField ListBox station;
	@UiField ListBox hostRoom;
	@UiField CheckBox toConfig;
	@UiField IntegerBox xPosition;
	@UiField IntegerBox yPosition;
	@UiField ListBox isIntegrate;

	interface IODFInfoUiBinder extends UiBinder<Widget, IODFInfo> {
	}

	private OpticalDeviceServiceAsync opticalDeviceService = GWT
			.create(OpticalDeviceService.class);

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);
	
	public IODFInfo() {
		setWidget(uiBinder.createAndBindUi(this));

		isIntegrate.insertItem("", 0);
		isIntegrate.insertItem("是", 1);
		isIntegrate.insertItem("否", 2);
		
		structStatus.insertItem("0", 0);
		structStatus.insertItem("1", 1);

		for (int i = 0; i < Constants.deviceStatus.length; i++) {
			phyStatus.insertItem(Constants.deviceStatus[i], i);
		}
		
		//局站编码获取：所有局站信息
		stationService.getStations(new AsyncCallback<List<Station>>(){
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
		});
		
		station.addChangeHandler(new ChangeHandler() {
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
		});
		setText("光纤配线架...");
		setSize("568px", "350px");
		setGlassEnabled(true);
	}

	private Iodf iodf = null;
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		if(isIntegrate.getSelectedIndex() == 0){
			Window.alert("请选择是否光设备！！！");
			isIntegrate.setFocus(true);
			return;
		}
		
		String tempIodfName = iodfName.getText();
		String tempIodfCode = iodfCode.getText();
		String tempStationCode = station.getItemText(station.getSelectedIndex()).split("#")[1];
		String tempHostRoomCode = hostRoom.getItemText(hostRoom.getSelectedIndex()).split("#")[1];
		if (StringVerifyUtils.verifyEmpty(tempIodfName)
				&& StringVerifyUtils.verifyEmpty(tempIodfCode)
				&& StringVerifyUtils.verifyEmpty(tempStationCode) && StringVerifyUtils
				.verifyEmpty(tempHostRoomCode)) {
			String tempManufacturer = manufacturer.getText();
			String tempAssemblyType = assemblyType.getText();
			String tempAssemblyCode = assemblyType.getText();
			String tempType = type.getText();
			String tempInstalledAddress = installedAddress.getText();
			String tempDutyPerson = dutyPerson.getText();
			String tempStructStatus = structStatus.getItemText(structStatus
					.getSelectedIndex());
			String tempPhyStatus = phyStatus.getItemText(phyStatus
					.getSelectedIndex());

			iodf = new Iodf();
			iodf.setAddress(tempInstalledAddress);
			iodf.setName(tempIodfName);
			iodf.setCode(tempIodfCode);
			iodf.setAssemblyNumber(tempAssemblyCode);
			iodf.setAssemblyType(tempAssemblyType);
			iodf.setDutyPerson(tempDutyPerson);
			iodf.setHostRoomCode(tempHostRoomCode);
			iodf.setPhyStatus(tempPhyStatus);
			iodf.setType(tempType);
			iodf.setStationCode(tempStationCode);
			iodf.setStructStatus(tempStructStatus);
			iodf.setManufacturer(tempManufacturer);
			iodf.setType(isIntegrate.getSelectedIndex() == 1?"IODF":"ODF");
			iodf.setXpositon(xPosition.getValue().intValue());
			iodf.setYposition(yPosition.getValue().intValue());
			
			opticalDeviceService.addIODF(iodf, addConfigCallback);
			
		} else {
			Window.alert("部分字段不能为空，请检查...");
			return;
		}
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}
	
	private AsyncCallback<Integer> addConfigCallback = new AsyncCallback<Integer>(){
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！");
			hide();
		}
		@Override
		public void onSuccess(Integer result) {
			if (result != -1) {
				Window.alert("添加成功！");
				hide();
				if(toConfig.getValue()){
					AddIODFConfigTask addIODFConfigTask = new AddIODFConfigTask(iodfCode.getText());
					addIODFConfigTask.center();
					addIODFConfigTask.show();
				}
			} else {
				Window.alert("添加失败！");
				hide();
			}
		}
	};
}
