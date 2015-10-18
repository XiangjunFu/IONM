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
import com.wanma.domain.Obd;
import com.wanma.domain.Station;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.CheckBox;

public class OBDInfo extends DialogBox {

	private static OBDInfoUiBinder uiBinder = GWT.create(OBDInfoUiBinder.class);
	@UiField
	Button save;
	@UiField
	Button cancel;
	@UiField
	TextBox obdName;
	@UiField
	TextBox obdCode;
	@UiField
	TextBox parentCode;
	@UiField
	TextBox inPortLocation;
	@UiField
	TextBox dutyPerson;
	@UiField
	TextBox manufacturer;
	@UiField
	ListBox splitLevel;
	@UiField
	ListBox parentType;
	@UiField
	ListBox physicalStatus;
	@UiField DoubleBox splitRatio;
	@UiField IntegerBox obdSeq;
	@UiField IntegerBox frameNo;
	@UiField IntegerBox slotNo;
	@UiField ListBox station;
	@UiField ListBox machineRoom;
	@UiField CheckBox toConfig;

	interface OBDInfoUiBinder extends UiBinder<Widget, OBDInfo> {
	}

	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private StationServiceAsync stationService = GWT.create(StationService.class);
	
	private MachineRoomServiceAsync machineRoomService = GWT.create(MachineRoomService.class);

	
	public OBDInfo() {
		setWidget(uiBinder.createAndBindUi(this));

		for(int i = 0;i<Constants.splitLevel.length;i++){
			splitLevel.insertItem(Constants.splitLevel[i],i);
		}
		
		for(int i = 0;i<Constants.deviceType.length;i++){
			parentType.insertItem(Constants.deviceType[i], i);
		}
		
		for(int i = 0;i<Constants.deviceStatus.length;i++){
			physicalStatus.insertItem(Constants.deviceStatus[i], i);
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
								machineRoom.insertItem(hr.getName() + "#" + hr.getCode(), i);
							}
						}else{
							Window.alert("未获取到所选局站的机房信息...");
						}
					}
				});
			}
		});
		
		setText("添加分光器...");
		setSize("600px", "394px");
		setGlassEnabled(true);
	}

	private Obd obd = null;
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		String tempObdName = obdName.getText();
		String tempObdCode = obdCode.getText();
		String tempStationCode = station.getItemText(station.getSelectedIndex()).split("#")[1];
		String tempHostRoomCode = machineRoom.getItemText(machineRoom.getSelectedIndex()).split("#")[1];
		String tempParentCode = parentCode.getText();
		Window.alert(tempParentCode + ':' + tempObdCode + ':' + tempObdName + ':' +tempStationCode + ':' + tempHostRoomCode);
		if(StringVerifyUtils.verifyEmpty(tempObdName) && StringVerifyUtils.verifyEmpty(tempObdCode)
				&& StringVerifyUtils.verifyEmpty(tempStationCode) && StringVerifyUtils.verifyEmpty(tempHostRoomCode)
				&& StringVerifyUtils.verifyEmpty(tempParentCode)){
			
			double tempSplitRatio = splitRatio.getValue().doubleValue();//分光比
			String tempStationName = station.getItemText(station.getSelectedIndex()).split("#")[0];
			int tempSlotNo = slotNo.getValue().intValue();
			String tempInPortLocation = inPortLocation.getText();
			String tempDutyPerson = dutyPerson.getText();
			String tempManufacturer = manufacturer.getText();
			String tempSplitLevel = splitLevel.getItemText(splitLevel.getSelectedIndex());
			String tempParentType = parentType.getItemText(parentType.getSelectedIndex());
			String tempPhysicalStatus = physicalStatus.getItemText(physicalStatus.getSelectedIndex());
			int tempObdSeq = obdSeq.getValue().intValue();
			int tempFrameNo = frameNo.getValue().intValue();
			obd = new Obd();
			obd.setCode(tempObdCode);
			obd.setName(tempObdName);
			obd.setDutyPerson(tempDutyPerson);
			obd.setFrameNo(tempFrameNo);
			obd.setHostCode(tempHostRoomCode);
			obd.setInPortLocation(tempInPortLocation);
			obd.setManufacurer(tempManufacturer);
			obd.setObdSeq(tempObdSeq);
			obd.setStationName(tempStationName);
			obd.setStationCode(tempStationCode);
			obd.setSplitRatio((float)tempSplitRatio);
			obd.setSplitLevel(tempSplitLevel);
			obd.setSlotNo(tempSlotNo);
			obd.setPhysicalStatue(tempPhysicalStatus);
			obd.setParentType(tempParentType);
			obd.setParentCode(tempParentCode);
			obd.setType("OBD");
			opticalDeviceService.addOBD(obd,addObdCallback);
			
		}else{
			Window.alert("部分字段不能为空，请检查...");
			return;
		}
	}

	@UiHandler("cancel")
	void onCancelCLick(ClickEvent event) {
		this.hide();
	}
	
	private AsyncCallback<Integer> addObdCallback = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！");
		}
		@Override
		public void onSuccess(Integer result) {
			if(result != -1){
				hide();
				if(toConfig.getValue()){
					AddWorkListDialogBox addWorkListDialogBox = new AddWorkListDialogBox(obdCode.getText());
					addWorkListDialogBox.center();
					addWorkListDialogBox.show();
				}
			}else{
				Window.alert("添加失败！");
				hide();
			}
		}
	};
}
