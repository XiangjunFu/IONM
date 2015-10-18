package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
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
import com.google.gwt.user.client.ui.ListBox;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.domain.Frame;
import com.wanma.domain.TerminalUnit;

public class AddBoardInfo extends DialogBox {

	private static AddBoardInfoUiBinder uiBinder = GWT
			.create(AddBoardInfoUiBinder.class);
	@UiField Button cancel;
	@UiField Button save;
	@UiField TextBox boardName;
	@UiField IntegerBox rowNum;
	@UiField IntegerBox colNum;
	@UiField ListBox frame;
	@UiField TextBox device;
	@UiField Button selectDevice;
	@UiField TextBox boardCode;
	@UiField TextBox boardSeq;
	@UiField ListBox boardType;

	interface AddBoardInfoUiBinder extends UiBinder<Widget, AddBoardInfo> {
	}
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	public AddBoardInfo() {
		setWidget(uiBinder.createAndBindUi(this));
		
		setSize("450px", "325px");
		setGlassEnabled(true);
		boardCode.setEnabled(false);
		boardSeq.addChangeHandler(seqChangeHandler);
		
		for(int i = 0;i<Constants.boardType.length;i++){
			boardType.insertItem(Constants.boardType[i], i);
		}
	}
	
	private ChangeHandler seqChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			String dev = device.getText();
			if(dev == null || "".equals(dev)){
				Window.alert("请先选择设备...");
				return;
			}
			//name#code
			String deviceCode = dev.split("#")[1];
			String deviceName = dev.split("#")[0];
			boardCode.setText(deviceCode + "/" + boardSeq.getText());
		}
	};
	
	private ChangeHandler deviceChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			String dev = device.getText();
			if(dev == null || "".equals(dev)){
				Window.alert("请先选择设备...");
				return;
			}
			//name#code
			String deviceCode = dev.split("#")[1];
			String deviceName = dev.split("#")[0];
			//opticalDeviceService.getFramesByDevicecode(deviceCode,framesCallback);
		}
	};
	
	private AsyncCallback<List<Frame>> framesCallback = new AsyncCallback<List<Frame>>() {
		@Override
		public void onFailure(Throwable caught) {
			
		}

		@Override
		public void onSuccess(List<Frame> result) {
			if(result != null){
				frame.clear();
				for(int i = 0;i<result.size();i++){
					Frame f = result.get(i);
					frame.insertItem(f.getFrameName(), i);
				}
			}
		}
	};
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event){
		TerminalUnit terminalUnit = new TerminalUnit();
		String dev = device.getText();
		if(dev == null || "".equals(dev)){
			Window.alert("请先选择设备...");
			return;
		}
		//name#code
		String deviceCode = dev.split("#")[1];
		String deviceName = dev.split("#")[0];
		terminalUnit.setHostDeviceId(deviceCode);
		
		String code = boardCode.getText();
		
		terminalUnit.setTerminalUnitCode(code);//板卡编码：设备编码/板卡序号(例如：设备编码/1-设备编码/40)
		terminalUnit.setTerminalUnitName(boardName.getText());
		terminalUnit.setTerminalUnitSeq(boardSeq.getText());
		
		int temp = Integer.parseInt(boardSeq.getText()) / Constants.boardNumPerFrame;
		int temp0 = Integer.parseInt(boardSeq.getText()) % Constants.boardNumPerFrame;
		int frameNo = temp0 == 0? temp : temp + 1;
		
		terminalUnit.setFrameNum(frameNo);
		
		int row = rowNum.getValue();
		int col = colNum.getValue();
		terminalUnit.setRowNumber(row);
		terminalUnit.setColumnNumber(col);
		terminalUnit.setDamagedNum(0);
		terminalUnit.setType(boardType.getSelectedIndex()+"");
		terminalUnit.setOccupiedNum(0);
		terminalUnit.setTerminalNum(row * col);
		terminalUnit.setStatus(0+"");//0表示未管理状态
		
		opticalDeviceService.addBoard(terminalUnit,addBoardCallback);
	}
	
	private AsyncCallback<Integer> addBoardCallback = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("板卡添加失败！！！");
		}
		@Override
		public void onSuccess(Integer result) {
			if(result == null || result.intValue() == -1){
				Window.alert("板卡添加失败！！！");
			}else{
				//成功关闭对话框
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
	
	@UiHandler("cancel")
	void onCancelClick(ClickEvent event) {
		this.hide();
	}


	public void setDevice(String deviceNameCode) {
		this.device.setText(deviceNameCode);
	}
}
