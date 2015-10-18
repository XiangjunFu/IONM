package com.wanma.client.worklist.config;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.utils.Constants;
import com.wanma.domain.ConfigTask;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;

public class WorkListDetail extends ResizeComposite {

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

	interface WorkListDetailUiBinder extends UiBinder<Widget, WorkListDetail> {
	}

	private WorkListManagerTable workListManagerTable = null;
	
	private ConfigTask configTask;
	
	public WorkListDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		//起始状态，指派按钮为不可用状态
		assignTask.setEnabled(false);
	}

	public void setConfigTask(ConfigTask configTask) {
		this.configTask = configTask;
		//填充详细信息，如果工单不为空并且工单状态为预配置时，按钮才能是可用状态
		if(configTask != null){
			assignTask.setEnabled(configTask.getStatus() == 1);//是预配置工单，
			updateDetailInfo();
		}
		//testUpdate();
	}

	private void updateDetailInfo() {
		workListCode.setText(configTask.getCode());
		workListName.setText(configTask.getName());
		stationCode.setText(configTask.getStation());
		hostRoomCode.setText(configTask.getHostroom());
		deviceType.setText(configTask.getDeviceType());
		deviceName.setText(configTask.getDeviceName());
		deviceCode.setText(configTask.getDeviceCode());
		status.setText(Constants.ConfigWorkListStatus[configTask.getStatus()]);
		worker.setText(configTask.getWorkerId());
		workgroup.setText(configTask.getWorkerId());
		createTime.setText(configTask.getCreateTime() + "");
		finishedTime.setText(configTask.getFinishedTime() + "");
		result.setText(configTask.getResult());
	}

	@UiHandler("assignTask")//指派施工人员
	void onAssignTaskClick(ClickEvent event){
		SelectGroupAndUser assign = new SelectGroupAndUser(configTask.getCode(),workListManagerTable);
		assign.center();
		assign.show();
	}

	public void setWorkListManagerTable(WorkListManagerTable workListManagerTable) {
		this.workListManagerTable = workListManagerTable;
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
