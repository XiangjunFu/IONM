package com.wanma.client.worklist.business;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.ExecuteOrder;
import com.wanma.domain.JobOrder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class BusinessWorkListDetail extends ResizeComposite {

	private static WorkListDetailUiBinder uiBinder = GWT
			.create(WorkListDetailUiBinder.class);
	
	interface SelectionStyle extends CssResource {
		String selectedRow();
	}
	
	@UiField HTML workListCode;
	@UiField HTML workListName;
	@UiField Button assignTask;
	@UiField Button addRouteWorkList;
	@UiField FlexTable routeTable;
	@UiField FlexTable routeHeader;
	@UiField SelectionStyle selectionStyle;
	
	private int selectedRow = -1;
	
	interface WorkListDetailUiBinder extends UiBinder<Widget, BusinessWorkListDetail> {
	}
	
	private BusinessWorkListServiceAsync businessWorkListService = GWT.create(BusinessWorkListService.class);
	
	private BusinessWorkListManagerTable businessWorkListManagerTable = null;
	
	private JobOrder jobOrder;
	
	private List<ExecuteOrder> executeOrders;
	
	public BusinessWorkListDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		//起始状态，指派按钮为不可用
		assignTask.setEnabled(false);
		addRouteWorkList.setEnabled(false);
		//业务工单详情列表表头初始化
		initRouteTableHeader();
		//光路由工单列表，行点击事件，选中行，未指派的光路由工单可以进行指派（指派按钮可用）
		routeTable.addClickHandler(tableRowClickHandler);
	}
	
	private ClickHandler tableRowClickHandler = new ClickHandler() {
		@Override
		public void onClick(ClickEvent event) {
			// Select the row that was clicked (-1 to account for header
			// row).
			Cell cell = routeTable.getCellForEvent(event);
			if (cell != null) {
				int row = cell.getRowIndex();
				selectRow(row);
				//如果工单状态不是未指派状态，指派按钮应不可用
				String status = routeTable.getText(row, 13);
				if(Constants.routeWorkListStatus[1].equals(status)){
					assignTask.setEnabled(true);
				}else{
					assignTask.setEnabled(false);
				}
			}
		}
	};

	//设置完JobOrder后，如果不为空,该业务工单下的路由工单就是最新的从数据库中获取的数据（前提是有路由工单）
	public void setJobOrder(JobOrder jobOrder) {
		this.jobOrder = jobOrder;
		if(jobOrder != null){
			//工单编码以及名称指定
			workListCode.setHTML(jobOrder.getCode());
			workListName.setHTML(jobOrder.getName());
			//指派按钮要根据工单状态来确定是否要指派，
			int status = jobOrder.getStatus();
			//创建路由未指派状态工单才能指派
			//assignTask.setEnabled(status == 2);
			
			//未创建路由状态的工单才可用
			//addRouteWorkList.setEnabled(status == 1);
			
			//设置业务工单 光路由工单列表
			updateDetailInfo();
		}else{
			
		}
		//testUpdate();
	}

	private void testUpdate(){
		workListCode.setText("Code");
		workListName.setText("Name");
	}
	
	//获取该业务工单下所有光路由工单信息，填充列表
	public void updateDetailInfo() {
		String jobOrderCode = jobOrder.getCode();
		businessWorkListService.getAllExecuteOrderByJobOrder(jobOrderCode,allExecuteOrdersCallBack);
	}
	
	//仅仅通过业务工单编码来更新路由工单数据
	public void updateDetailInfoByJobOrderCode(String jobOrderCode){
		businessWorkListService.getAllExecuteOrderByJobOrder(jobOrderCode,allExecuteOrdersCallBack);
	}
	
	private AsyncCallback<List<ExecuteOrder>> allExecuteOrdersCallBack = new AsyncCallback<List<ExecuteOrder>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}
		@Override
		public void onSuccess(List<ExecuteOrder> result) {
			if(result != null){
				//不为空说明。业务工单有数据, 填充列表
				setExecuteOrders(result);
			}else{
				//为空，说明业务工单没有路由工单数据，初始化路由工单列表头部即可
				initRouteTableHeader();
			}
		}
	};
	
	public void setExecuteOrders(List<ExecuteOrder> result) {
		this.executeOrders = result;
		updateRouteTableInfo(result);
	}
	
	//初始光路由工单表格表头
	private void initRouteTableHeader() {
		routeHeader.removeAllRows();
		//初始化表头内容 各列宽度
		routeHeader.getColumnFormatter().setWidth(0, "50px");
		routeHeader.getColumnFormatter().setWidth(1, "50px");
		routeHeader.getColumnFormatter().setWidth(2, "50px");
		routeHeader.getColumnFormatter().setWidth(3, "50px");
		routeHeader.getColumnFormatter().setWidth(4, "50px");
		routeHeader.getColumnFormatter().setWidth(5, "50px");
		routeHeader.getColumnFormatter().setWidth(6, "50px");
		routeHeader.getColumnFormatter().setWidth(7, "50px");
		routeHeader.getColumnFormatter().setWidth(8, "50px");
		routeHeader.getColumnFormatter().setWidth(9, "50px");
		routeHeader.getColumnFormatter().setWidth(10, "50px");
		routeHeader.getColumnFormatter().setWidth(11, "50px");
		routeHeader.getColumnFormatter().setWidth(12, "50px");
		routeHeader.getColumnFormatter().setWidth(13, "50px");
		routeHeader.getColumnFormatter().setWidth(14, "50px");
		routeHeader.getColumnFormatter().setWidth(15, "50px");
		//初始化表头
		routeHeader.setText(0, 0, "业务工单编号");
		routeHeader.setText(0, 1, "路由工单编号");
		routeHeader.setText(0, 2, "工单名称");
		routeHeader.setText(0, 3, "A设备类型");
		routeHeader.setText(0, 4, "A设备编码");
		routeHeader.setText(0, 5, "A端板卡");
		routeHeader.setText(0, 6, "A端口编码");
		routeHeader.setText(0, 7, "Z设备类型");
		routeHeader.setText(0, 8, "Z设备编码");
		routeHeader.setText(0, 9, "Z端板卡");
		routeHeader.setText(0, 10, "Z端口编码");
		routeHeader.setText(0, 11, "创建时间");
		routeHeader.setText(0, 12, "创建者");
		routeHeader.setText(0, 13, "状态");
		routeHeader.setText(0, 14, "施工班组");
		routeHeader.setText(0, 15, "施工人员");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);
		//初始化表格内容 各列宽度
		routeTable.getColumnFormatter().setWidth(0, "50px");
		routeTable.getColumnFormatter().setWidth(1, "50px");
		routeTable.getColumnFormatter().setWidth(2, "50px");
		routeTable.getColumnFormatter().setWidth(3, "50px");
		routeTable.getColumnFormatter().setWidth(4, "50px");
		routeTable.getColumnFormatter().setWidth(5, "50px");
		routeTable.getColumnFormatter().setWidth(6, "50px");
		routeTable.getColumnFormatter().setWidth(7, "50px");
		routeTable.getColumnFormatter().setWidth(8, "50px");
		routeTable.getColumnFormatter().setWidth(9, "50px");
		routeTable.getColumnFormatter().setWidth(10, "50px");
		routeTable.getColumnFormatter().setWidth(11, "50px");
		routeTable.getColumnFormatter().setWidth(12, "50px");
		routeTable.getColumnFormatter().setWidth(13, "50px");
		routeTable.getColumnFormatter().setWidth(14, "50px");
		routeTable.getColumnFormatter().setWidth(15, "50px");
	}

	//填充业务工单的光路由工单列表
	private void updateRouteTableInfo(List<ExecuteOrder> executeOrders){
		//this.executeOrders = executeOrders;
		routeTable.removeAllRows();
		for(int i = 0;i<executeOrders.size();i++){
			ExecuteOrder executeOrder = executeOrders.get(i);
			routeTable.setText(i, 0, executeOrder.getJobOderCode());
			routeTable.setText(i, 1, executeOrder.getCode());
			routeTable.setText(i, 2, executeOrder.getName());
			routeTable.setText(i, 3, executeOrder.getAmeType());
			routeTable.setText(i, 4, executeOrder.getAmeCode());
			routeTable.setText(i, 5, executeOrder.getAcard());
			routeTable.setText(i, 6, executeOrder.getAportCode());
			routeTable.setText(i, 7, executeOrder.getZmeType());
			routeTable.setText(i, 8, executeOrder.getZmeCode());
			routeTable.setText(i, 9, executeOrder.getZcard());
			routeTable.setText(i, 10, executeOrder.getZportCode());
			routeTable.setText(i, 11, DateUtils.formatDateToString(executeOrder.getCreateTime()));
			routeTable.setText(i, 12, executeOrder.getCreator());
			routeTable.setText(i, 13, Constants.routeWorkListStatus[executeOrder.getStatus()]);
			routeTable.setText(i, 14, executeOrder.getGroupName() == null?"--":executeOrder.getGroupName());
			routeTable.setText(i, 15, executeOrder.getWorker() == null?"--":executeOrder.getWorker());
		}
		this.selectedRow = -1;
	}
	
	@UiHandler("assignTask")//指派施工人员
	void onAssignTaskClick(ClickEvent event){
		/**
		 * 需要在此处判断是否选中业务工单，确保工单不能为null
		 * 
		 * 应该是按照光路由工单来进行指派
		 * 获取选中的光路由工单，进行指派
		 * 传递table参数是为了更新该表格
		 */
		//所选中行的第一列即为路由工单列表
		String routeTaskCode = routeTable.getText(selectedRow, 1);
		SelectGroupAndUser assign = new SelectGroupAndUser(routeTaskCode,businessWorkListManagerTable,this,jobOrder.getCode());
		assign.center();
		assign.show();
	}

	public void setWorkListManagerTable(BusinessWorkListManagerTable businessWorkListManagerTable) {
		this.businessWorkListManagerTable = businessWorkListManagerTable;
	}
	
	@UiHandler("addRouteWorkList")
	public void onAddRouteWLClick(ClickEvent event){
		/**
		 * 需要在此处判断是否选中业务工单，确保工单不能为null
		 * 保证没有行选中时按钮不可用就可以了
		 */
		AddRouteWorkList addRouteWL = new AddRouteWorkList(jobOrder,businessWorkListManagerTable);
		addRouteWL.setBusinessWorkListDetail(this);
		addRouteWL.center();
		addRouteWL.show();
	}
	
	public void setAddRouteWorkListEnabled(boolean enabled){
		addRouteWorkList.setEnabled(enabled);
	}
	
	public void setAssignTaskEnabled(boolean enabled){
		this.assignTask.setEnabled(enabled);
	}
	

	//高亮选中行
	private void selectRow(int row) {
		/**
		 * 1、去除当前选中的行的高亮
		 * 2、高亮第row行
		 */
		styleRow(selectedRow, false);
		styleRow(row, true);
		selectedRow = row;
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = selectionStyle.selectedRow();

			if (selected) {
				routeTable.getRowFormatter().addStyleName(row, style);
			} else {
				routeTable.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

	public void updateDetailInfoToEmpty() {
		//工单编码以及名称指定
		workListCode.setHTML("");
		workListName.setHTML("");
		initRouteTableHeader();
	}
	
}
