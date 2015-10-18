package com.wanma.client.worklist.board;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.BoardTask;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;

public class BoardWorkListDetail extends ResizeComposite {

	private static WorkListDetailUiBinder uiBinder = GWT
			.create(WorkListDetailUiBinder.class);
	
	@UiField HTML workListCode;
	@UiField HTML workListName;
	@UiField HTML stationCode;
	@UiField HTML hostRoomCode;
	@UiField HTML deviceType;
	@UiField HTML deviceCode;
	@UiField HTML deviceName;
	@UiField HTML status;
	@UiField HTML worker;
	@UiField HTML workgroup;
	@UiField HTML createTime;
	@UiField HTML finishedTime;
	@UiField HTML result;
	@UiField Button assignTask;

	interface WorkListDetailUiBinder extends UiBinder<Widget, BoardWorkListDetail> {
	}

	private BoardWorkListManagerTable boardWorkListManagerTable = null;
	
	private BoardTask boardTask;
	
	public BoardWorkListDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		//起始状态，指派按钮为不可用状态
		assignTask.setEnabled(false);
	}

	public void setBoardTaskInfo(BoardTask boardTask) {
		this.boardTask = boardTask;
		//填充详细信息，如果工单不为空并且工单状态为预配置时，按钮才能是可用状态
		if(boardTask != null){
			assignTask.setEnabled(boardTask.getStatus() == 1);//是预配置工单，
			updateDetailInfo();
		}
		//testUpdate();
	}

	private void testUpdate(){
		workListCode.setText("Code");
		workListName.setText("Name");
		stationCode.setText("需要添加字段");
		hostRoomCode.setText("需要添加字段");
		deviceType.setText("DeviceType");
		deviceName.setText("DeviceName");
		deviceCode.setText("DeviceCode()");
		status.setText("Status");
		worker.setText("WorkerId");
		workgroup.setText("WorkerGroup");
		createTime.setText("CreateTime");
		finishedTime.setText("FinishedTime");
		result.setText("Result");
	}
	
	private void updateDetailInfo() {
		workListCode.setText(boardTask.getCode());
		workListName.setText(boardTask.getName());
		stationCode.setText(boardTask.getStation());
		hostRoomCode.setText(boardTask.getHostroom());
		deviceType.setText(boardTask.getDeviceType());
		deviceName.setText(boardTask.getDeviceName());
		deviceCode.setText(boardTask.getDeviceCode());
		status.setText(Constants.boardWorkListStatus[boardTask.getStatus()]);
		worker.setText(boardTask.getWorkerId() == null ? "--":boardTask.getWorkerId());
		workgroup.setText(boardTask.getGroupId() == null ? "--":boardTask.getGroupId());
		createTime.setText(DateUtils.formatDateToString(boardTask.getCreatetime()));
	}

	@UiHandler("assignTask")//指派施工人员
	void onAssignTaskClick(ClickEvent event){
		SelectGroupAndUser assign = new SelectGroupAndUser(boardTask.getCode(),boardWorkListManagerTable);
		assign.center();
		assign.show();
	}

	public void setBoardWorkListManagerTable(BoardWorkListManagerTable boardWorkListManagerTable) {
		this.boardWorkListManagerTable = boardWorkListManagerTable;
	}
	
	public void setAssignTaskEnabled(boolean enabled){
		this.assignTask.setEnabled(enabled);
	}
	
	public void updateDetailInfoToEmpty(){
		workListCode.setText("");
		workListName.setText("");
		stationCode.setText("");
		hostRoomCode.setText("");
		deviceType.setText("");
		deviceName.setText("");
		deviceCode.setText("");
		status.setText("");
		worker.setText("");
		workgroup.setText("");
		createTime.setText("");
		finishedTime.setText("");
		result.setText("");
	}
}
