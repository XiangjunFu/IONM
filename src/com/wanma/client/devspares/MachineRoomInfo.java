package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.wanma.client.services.MachineRoomService;
import com.wanma.client.services.MachineRoomServiceAsync;
import com.wanma.client.services.StationService;
import com.wanma.client.services.StationServiceAsync;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Station;

public class MachineRoomInfo extends DialogBox {

	private static MachineRoomInfoUiBinder uiBinder = GWT
			.create(MachineRoomInfoUiBinder.class);
	@UiField TextBox name;//名称 *
	@UiField TextBox alias;
	@UiField ListBox station;//局站 *
	@UiField TextBox roomType;
	@UiField ListBox localOrRemote;
	@UiField TextBox floors;
	@UiField TextBox length;
	@UiField TextBox width;
	@UiField TextBox code;//编码 *
	@UiField TextBox shortName;
	@UiField TextBox address;// 地址 *
	@UiField TextBox remark;
	@UiField Button save;
	@UiField Button cancel;
	@UiField TextBox height;
	@UiField DateBox createDate;
	@UiField DateBox updateDate;
	
	interface MachineRoomInfoUiBinder extends UiBinder<Widget, MachineRoomInfo> {
	}

	private MachineRoomServiceAsync machineService = GWT.create(MachineRoomService.class);
	
	private StationServiceAsync stationService = GWT.create(StationService.class);
	//private List<String> stations = null;
	
	public MachineRoomInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		//局站选择
		//stations = null;
		stationService.getStations(new AsyncCallback<List<Station>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("未获取到局站数据！");
			}

			@Override
			public void onSuccess(List<Station> result) {
				if(result.size() > 0){
					for(int i = 0;i<result.size();i++){
						station.insertItem(result.get(i).getName(), i);
					}
				}else{
					Window.alert("请先添加局站信息！");
				}
			}
		});
		
		
		localOrRemote.insertItem("本地", 0);
		localOrRemote.insertItem("长途", 1);
		
		setSize("710px", "500px");
		setGlassEnabled(true);
	}
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		HostRoom room = new HostRoom();
		if(verifyEmpty(name.getText()) || verifyEmpty(code.getText()) || verifyEmpty(address.getText())){
			Window.alert("某些字段不能为空，请检查...");
			return;
		}
		room.setName(name.getText());
		room.setAddress(address.getText());
		room.setCode(code.getText());
		room.setAliasName(alias.getText());
		if(floors != null && !"".equals(floors)){room.setFloors(Integer.parseInt(floors.getText()));}
		if(length != null && !"".equals(length)){room.setHighth(Integer.parseInt(length.getText()));}
		if(width != null && !"".equals(width)){room.setWidth(Integer.parseInt(width.getText()));}
		if(height != null && !"".equals(height)){room.setHighth(Integer.parseInt(height.getText()));}
		room.setHostType(roomType.getText());
		String str = localOrRemote.getItemText(localOrRemote.getSelectedIndex());
		boolean temp = "本地".equals(str);
		room.setLocalOrremote(temp);
		room.setNotes(remark.getText());
		room.setStationName(station.getItemText(station.getSelectedIndex()));
		room.setBriefName(shortName.getText());
		room.setCreatTime(createDate.getValue());
		room.setReviseTime(updateDate.getValue());
		
		machineService.addMachineRoom(room, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				Window.alert("机房添加成功！");
				hide();
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("机房添加失败！");
			}
		});
	}
	
	private boolean verifyEmpty(String text){
		return text == null || "".equals(text);
	}
}
