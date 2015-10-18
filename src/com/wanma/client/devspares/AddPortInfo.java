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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Button;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.StringVerifyUtils;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

public class AddPortInfo extends DialogBox {

	private static AddPortInfoUiBinder uiBinder = GWT
			.create(AddPortInfoUiBinder.class);
	@UiField TextBox code;
	@UiField TextBox name;
	@UiField ListBox type;
	@UiField TextBox cellNo;
	@UiField TextBox portSequence;
	@UiField TextBox slotNo;
	@UiField TextBox portNo;
	@UiField TextBox rowIndex;
	@UiField TextBox colIndex;
	@UiField ListBox physicalStatue;
	@UiField ListBox serviceStatue;
	@UiField Button save;
	@UiField Button cancel;
	@UiField ListBox meType;
	@UiField ListBox meNameCode;
	@UiField ListBox board;

	interface AddPortInfoUiBinder extends UiBinder<Widget, AddPortInfo> {
	}

	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	private DeviceBoardDetail deviceBoardDetail;
	
	private String boardCode = null;
	
	private String deviceCode = "";
	
	public AddPortInfo(SpaceResourceManagerTable spaceResourceManagerTable) {
		setWidget(uiBinder.createAndBindUi(this));
		
		meType.insertItem("", 0);
		meType.insertItem("IODF", 1);
		meType.insertItem("IFDT", 2);
		meType.insertItem("IFAT", 3);
		meType.insertItem("OBD", 4);
		
		meType.addChangeHandler(meTypeChangeHandler);
		
		type.insertItem("0", 0);
		type.insertItem("1", 1);
		
		physicalStatue.insertItem("", 0);
		physicalStatue.insertItem("空闲", 1);
		physicalStatue.insertItem("占用", 2);
		
		serviceStatue.insertItem("", 0);
		serviceStatue.insertItem("空闲", 1);
		serviceStatue.insertItem("占用", 2);
		
		//this.spaceResourceManagerTable = spaceResourceManagerTable;
		setSize("617px", "390px");
		setGlassEnabled(true);
	}
	
	public AddPortInfo(String boardCode, DeviceBoardDetail deviceBoardDetail) {
		setWidget(uiBinder.createAndBindUi(this));
		
		type.insertItem("0", 0);
		type.insertItem("1", 1);
		
		physicalStatue.insertItem("", 0);
		physicalStatue.insertItem("空闲", 1);
		physicalStatue.insertItem("占用", 2);
		
		serviceStatue.insertItem("0", 0);
		serviceStatue.insertItem("1", 1);
		
		this.deviceBoardDetail = deviceBoardDetail;
		this.boardCode = boardCode;
		
		board.insertItem(boardCode,0);
		board.setSelectedIndex(0);
		board.setEnabled(false);
		
		int boardSeq = Integer.parseInt(boardCode.split("/")[1]);
		slotNo.setText(boardSeq+"");
		slotNo.setEnabled(false);
		
		opticalDeviceService.getBoardByBoardCode(boardCode, getBoardCallback);
		
		setSize("617px", "390px");
		setGlassEnabled(true);
	}

	private AsyncCallback<TerminalUnit> getBoardCallback = new AsyncCallback<TerminalUnit>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取板卡失败！！！");
		}
		@Override
		public void onSuccess(TerminalUnit result) {
			if(result != null){
				deviceCode = result.getHostDeviceId();
				opticalDeviceService.getDeviceCommonInfosByDeviceCode(deviceCode, deviceCommonInfoCallback);
			}
		}
	};
	
	private AsyncCallback<DeviceCommonInfo> deviceCommonInfoCallback = new AsyncCallback<DeviceCommonInfo>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("失败！！！");
		}
		@Override
		public void onSuccess(DeviceCommonInfo result) {
			if(result != null){
				
				meNameCode.insertItem(result.getDeviceName() + "#" + result.getDeviceCode(),0);
				meNameCode.setSelectedIndex(0);
				meNameCode.setEnabled(false);
				
				meType.insertItem(result.getDeviceType(),0);
				meType.setSelectedIndex(0);
				meType.setEnabled(false);
			}
		}
	};
	
	@UiHandler("save")
	public void onSaveClick(ClickEvent event){
		String tempCode = code.getText();
		String tempName = name.getText();
		String tempType = type.getItemText(type.getSelectedIndex());
		int tempPortSequence = Integer.parseInt(portSequence.getText());
		int tempSlotNo = Integer.parseInt(slotNo.getText());
		int tempPortNo = Integer.parseInt(portNo.getText());
		int tempRowIndex = Integer.parseInt(rowIndex.getText());
		int tempColIndex = Integer.parseInt(colIndex.getText());
		String tempMeType = meType.getItemText(meType.getSelectedIndex());
		String tempMeName = meNameCode.getItemText(meNameCode.getSelectedIndex()).split("#")[0];
		String tempMeCode = meNameCode.getItemText(meNameCode.getSelectedIndex()).split("#")[1];
		String tempBoard = board.getItemText(board.getSelectedIndex());

		if(!StringVerifyUtils.verifyEmpty(tempCode) || !StringVerifyUtils.verifyEmpty(tempName) || !StringVerifyUtils.verifyEmpty(tempBoard)
				|| !StringVerifyUtils.verifyEmpty(tempMeCode) || !StringVerifyUtils.verifyEmpty(tempMeName)){
			Window.alert("部分参数不能为空!!!");
			return;
		}
		
		Port port = new Port();
		
		port.setColIndex(tempColIndex);
		port.setMeCode(tempMeCode);
		port.setMeType(tempMeType);
		port.setName(tempName);
		
		port.setPortSequence(tempPortSequence);//框内端口号
		
		port.setSlotNo(tempSlotNo);//板卡内端口号
		
		port.setPortNo(tempPortNo);//端口号
		
		//boardNumPerFrame
		int boardNum = Integer.parseInt(tempBoard.split("/")[1]);
		int temp = boardNum / Constants.boardNumPerFrame;
		int temp0 = boardNum % Constants.boardNumPerFrame;
		int cellNo = temp0 == 0?temp:temp+1;
		port.setCellNo(cellNo);
		
		String code = tempMeCode + "/" + cellNo + "/" + boardNum + "/" + tempSlotNo;
		port.setCode(code);//端口编码：设备编码/机框序号/板卡序号/端口序号
		
		port.setParentCode(tempBoard);
		
		port.setPhysicalStatue(physicalStatue.getSelectedIndex()+"");//物理状态为1  表示端口空闲，2表示已占用
		port.setRowIndex(tempRowIndex);
		port.setServiceStatue(physicalStatue.getSelectedIndex()+"");
		port.setPortType(tempType);
		
		opticalDeviceService.addPortInfo(port,addPortCallback);
	}
	
	private AsyncCallback<Integer> addPortCallback = new AsyncCallback<Integer>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("添加失败！！！");
		}
		@Override
		public void onSuccess(Integer result) {
			hide();
			//String deviceCode = meNameCode.getItemText(meNameCode.getSelectedIndex()).split("#")[1];
			//""全部，物理状态为1  表示端口空闲，2表示已占用
			opticalDeviceService.getPortsByBoardCode(boardCode,"", updatePortTableCallback);
		}
	};
	
	private AsyncCallback<List<Port>> updatePortTableCallback = new AsyncCallback<List<Port>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取端口信息失败！！！");
		}
		@Override
		public void onSuccess(List<Port> result) {
			if(result != null){
				deviceBoardDetail.setBoardCode(boardCode);
			}
		}
	};
	
	@UiHandler("cancel")
	public void onCancelClick(ClickEvent event){
		this.hide();
	}
	
	//选择meType初始化MeNameCode
	private ChangeHandler meTypeChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = meType.getSelectedIndex();
			if(index == 0){
				Window.alert("请选择...");
				return;
			}
			String type = meType.getItemText(index);
			opticalDeviceService.getDeviceCommonInfosByType(type,getDeviceCommonInfosCallback);
		}
	};
	
	private AsyncCallback<List<DeviceCommonInfo>> getDeviceCommonInfosCallback = new AsyncCallback<List<DeviceCommonInfo>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("无该类型设备信息！！！");
		}
		@Override
		public void onSuccess(List<DeviceCommonInfo> result) {
			if(result != null){
				meNameCode.clear();
				for(int i = 0;i<result.size();i++){
					DeviceCommonInfo deviceInfo = result.get(i);
					meNameCode.insertItem(deviceInfo.getDeviceName() + "#" + deviceInfo.getDeviceCode(), i);
				}
			}
		}
	};
	
	//选择meNameCode初始化board
	private ChangeHandler meNameCodeChangeHandler = new ChangeHandler() {
		@Override
		public void onChange(ChangeEvent event) {
			int index = meNameCode.getSelectedIndex();
			String type = meNameCode.getItemText(index);
			String deviceName = meNameCode.getItemText(meNameCode.getSelectedIndex()).split("#")[0];
			opticalDeviceService.getBoards(deviceName, boardCallback);
		}
	};

	private AsyncCallback<List<TerminalUnit>> boardCallback = new AsyncCallback<List<TerminalUnit>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("无该设备板卡信息！！！");
		}
		@Override
		public void onSuccess(List<TerminalUnit> result) {
			if(result != null){
				board.clear();
				for(int i = 0;i < result.size();i++){
					TerminalUnit terminalUnit = result.get(i);
					board.insertItem(terminalUnit.getTerminalUnitId()+"", i);
				}
			}
		}
	};
	
}
