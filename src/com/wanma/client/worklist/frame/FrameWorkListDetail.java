package com.wanma.client.worklist.frame;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.FrameTask;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;

public class FrameWorkListDetail extends ResizeComposite {

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

	interface WorkListDetailUiBinder extends UiBinder<Widget, FrameWorkListDetail> {
	}

	private FrameWorkListManagerTable frameWorkListManagerTable = null;
	
	private FrameTask frameTask;
	
	public FrameWorkListDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		//起始状态，指派按钮为不可用状态
		assignTask.setEnabled(false);
	}

	public void setFrameTaskInfo(FrameTask frameTask) {
		this.frameTask = frameTask;
		//填充详细信息，如果工单不为空并且工单状态为预配置时，按钮才能是可用状态
		if(frameTask != null){
			assignTask.setEnabled(frameTask.getStatus() == 1);//是预配置工单，
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
		workListCode.setText(frameTask.getCode());
		workListName.setText(frameTask.getName());
		stationCode.setText(frameTask.getStation());
		hostRoomCode.setText(frameTask.getHostroom());
		deviceType.setText(frameTask.getDeviceType());
		deviceName.setText(frameTask.getDeviceName());
		deviceCode.setText(frameTask.getDeviceCode());
		status.setText(Constants.frameWorkListStatus[frameTask.getStatus()]);
		worker.setText(frameTask.getWorkerId() == null ? "--":frameTask.getWorkerId());
		workgroup.setText(frameTask.getGroupId() == null ? "--":frameTask.getGroupId());
		createTime.setText(DateUtils.formatDateToString(frameTask.getCreatetime()));
	}

	@UiHandler("assignTask")//指派施工人员
	void onAssignTaskClick(ClickEvent event){
		SelectGroupAndUser assign = new SelectGroupAndUser(frameTask.getCode(),frameWorkListManagerTable);
		assign.center();
		assign.show();
	}

	public void setFrameWorkListManagerTable(FrameWorkListManagerTable frameWorkListManagerTable) {
		this.frameWorkListManagerTable = frameWorkListManagerTable;
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
