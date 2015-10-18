package com.wanma.client.devspares;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.MachineRoomService;
import com.wanma.client.services.MachineRoomServiceAsync;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.client.worklist.config.AddWorkListDialogBox;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Station;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.IntegerBox;

public class IFDTInfo extends DialogBox {

	private static IFDTInfoUiBinder uiBinder = GWT
			.create(IFDTInfoUiBinder.class);
	@UiField
	TextBox deviceName;
	@UiField
	TextBox deviceCode;
	@UiField
	TextBox assemblyName;
	@UiField
	TextBox assemblyCode;
	@UiField
	ListBox phyStatus;
	@UiField
	ListBox serviceStatus;
	@UiField
	DateBox createTime;
	@UiField
	DateBox updateTime;
	@UiField
	TextBox capacity;
	@UiField
	TextBox installedCapacity;
	@UiField
	ListBox isIntegrate;
	@UiField
	TextBox address;
	@UiField
	TextBox notes;
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField ListBox station;
	@UiField ListBox machineRoom;
	@UiField CheckBox toConfig;
	@UiField IntegerBox xPosition;
	@UiField IntegerBox yPosition;

	interface IFDTInfoUiBinder extends
			UiBinder<Widget, IFDTInfo> {
	}
	
	public boolean isAddSuccess = false;
	
	//public AtomicBoolean isAddSuccess = false; 
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);

	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);

	
	public IFDTInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		//ListBox 配置
		isIntegrate.insertItem("是", 0);
		isIntegrate.insertItem("否", 1);
		
		phyStatus.insertItem("0", 0);
		phyStatus.insertItem("1", 1);
		
		serviceStatus.insertItem("0", 0);
		serviceStatus.insertItem("1", 1);
		
		//初始化局站，机房
		//局站编码获取：所有局站信息
		stationService.getStations(stationCallback);
		
		station.addChangeHandler(changeHandler);
		
		setTitle("添加光交接箱...");
		setGlassEnabled(true);
		setSize("600px", "423px");
	}
	
	public IFDTInfo(int xPosition,int yPosition) {
		setWidget(uiBinder.createAndBindUi(this));
		
		//ListBox 配置
		isIntegrate.insertItem("是", 0);
		isIntegrate.insertItem("否", 1);
		
		phyStatus.insertItem("0", 0);
		phyStatus.insertItem("1", 1);
		
		serviceStatus.insertItem("0", 0);
		serviceStatus.insertItem("1", 1);
		
		//初始化局站，机房
		//局站编码获取：所有局站信息
		stationService.getStations(stationCallback);
		
		station.addChangeHandler(changeHandler);
		
		this.xPosition.setText(xPosition+"");
		this.yPosition.setText(yPosition+"");
		this.xPosition.setEnabled(false);
		this.yPosition.setEnabled(false);
		
		setTitle("添加光交接箱...");
		setGlassEnabled(true);
		setSize("600px", "423px");
	}

	
	private Ifdt ifdt = null;
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		ifdt = new Ifdt();
		if(StringVerifyUtils.verifyEmpty(deviceName.getText()) &&
			StringVerifyUtils.verifyEmpty(deviceCode.getText()) &&
			StringVerifyUtils.verifyEmpty(station.getItemText(station.getSelectedIndex())) &&
			StringVerifyUtils.verifyEmpty(address.getText())){
			ifdt.setName(deviceName.getText());
			ifdt.setCode(deviceCode.getText());//不能有 /
			ifdt.setAddress(address.getText());
			ifdt.setAssemblyType(assemblyName.getText());
			ifdt.setAssemblyNumber(assemblyCode.getText());
			if(StringVerifyUtils.verifyEmpty(capacity.getText())){ifdt.setCapacity(Integer.parseInt(capacity.getText()));}
			if(StringVerifyUtils.verifyEmpty(installedCapacity.getText())){ifdt.setInstalledCapacity(Integer.parseInt(installedCapacity.getText()));}
			ifdt.setServicdeStatus(serviceStatus.getItemText(serviceStatus.getSelectedIndex()));
			ifdt.setStructStatus(phyStatus.getItemText(phyStatus.getSelectedIndex()));
			ifdt.setStationCode(station.getItemText(station.getSelectedIndex()).split("#")[1]);
			ifdt.setHostRoomCode(machineRoom.getItemText(machineRoom.getSelectedIndex()).split("#")[1]);
			ifdt.setIsIntegrate(isIntegrate.getSelectedIndex() == 0);//第一个true，第二个false
			ifdt.setType(isIntegrate.getSelectedIndex() == 0?"IFDT":"FDT");
			ifdt.setXpositon(xPosition.getValue().intValue());
			ifdt.setYposition(yPosition.getValue().intValue());
			
			opticalDeviceService.addIFDT(ifdt, addIFDTCallback);
			
		}else{
			Window.alert("部分字段不能为空，请检查！");
			return;
		}
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	private AsyncCallback<Integer> addIFDTCallback = new AsyncCallback<Integer>() {
		@Override
		public void onSuccess(Integer result) {
			if(result != null && !"".equals(result)){
				isAddSuccess = true;
				hide();
				if(toConfig.getValue()){
					AddWorkListDialogBox addWorkList = new AddWorkListDialogBox(deviceCode.getText());
					addWorkList.center();
					addWorkList.show();
				}
			}else{
				Window.alert("添加失败！");
				hide();
			}
		}
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！");
			hide();
		}
	};
	
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
							machineRoom.insertItem(hr.getName() + "#" + hr.getCode(), i);
						}
					}else{
						Window.alert("未获取到所选局站的机房信息...");
					}
				}
			});
		}
	};
}
