package com.wanma.client.worklist.check;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.RouteCheckTask;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;

public class CheckWorkListDetail extends ResizeComposite {

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

	interface WorkListDetailUiBinder extends UiBinder<Widget, CheckWorkListDetail> {
	}

	private CheckWorkListManagerTable checkWorkListManagerTable = null;
	
	private RouteCheckTask checkTask;
	
	public CheckWorkListDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		//起始状态，指派按钮为不可用状态
		assignTask.setEnabled(false);
	}

	public void setCheckTaskInfo(RouteCheckTask checkTask) {
		this.checkTask = checkTask;
		//填充详细信息，如果工单不为空并且工单状态为预配置时，按钮才能是可用状态
		if(checkTask != null){
			assignTask.setEnabled(checkTask.getStatus() == 1);//是预配置工单，
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
		workListCode.setText(checkTask.getCode());
		workListName.setText(checkTask.getName());
		stationCode.setText(checkTask.getStation());
		hostRoomCode.setText(checkTask.getHostroom());
		deviceType.setText(checkTask.getDeviceType());
		deviceName.setText(checkTask.getDeviceName());
		deviceCode.setText(checkTask.getDeviceCode());
		status.setText(Constants.checkWorkListStatus[checkTask.getStatus()]);
		worker.setText(checkTask.getWorkerId() == null ? "--":checkTask.getWorkerId());
		workgroup.setText(checkTask.getGroupId() == null ? "--":checkTask.getGroupId());
		createTime.setText(DateUtils.formatDateToString(checkTask.getCreateTime()));
	}

	@UiHandler("assignTask")//指派施工人员
	void onAssignTaskClick(ClickEvent event){
		SelectGroupAndUser assign = new SelectGroupAndUser(checkTask.getCode(),checkWorkListManagerTable);
		assign.center();
		assign.show();
	}

	public void setCheckWorkListManagerTable(CheckWorkListManagerTable checkWorkListManagerTable) {
		this.checkWorkListManagerTable = checkWorkListManagerTable;
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
