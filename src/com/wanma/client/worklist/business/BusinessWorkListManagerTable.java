package com.wanma.client.worklist.business;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.uibinder.client.UiField;
import com.wanma.client.services.BusinessWorkListService;
import com.wanma.client.services.BusinessWorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.JobOrder;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class BusinessWorkListManagerTable extends Composite {

	private static WorkListManagerTableUiBinder uiBinder = GWT
			.create(WorkListManagerTableUiBinder.class);

	interface WorkListManagerTableUiBinder extends
			UiBinder<Widget, BusinessWorkListManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	@UiField
	FlexTable table;
	@UiField
	FlexTable header;
	@UiField
	Button query;
	@UiField
	Button refresh;
	@UiField
	Button importData;
	@UiField
	ListBox condition;
	@UiField
	SelectionStyle selectionStyle;

	private BusinessWorkListServiceAsync businessWorkListService = GWT.create(BusinessWorkListService.class);
	
	private int selectedRow = -1;
	
	private BusinessWorkListDetail businessWorkListDetail;

	private List<JobOrder> businessWorkList = null;

	public BusinessWorkListManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void updateBusinessWorkList(int status) {
		// initBusinessWorkListTableHeader();
		//每一次更新都要移除原来数据
		table.removeAllRows();

		for (int i = 0; i < businessWorkList.size(); i++) {
			JobOrder jobOrder = businessWorkList.get(i);
			if (0 == status) {
				//表格信息填充
				table.setText(i, 0, jobOrder.getCode());
				table.setText(i, 1, jobOrder.getName());
				table.setText(i, 2, jobOrder.getBusinessType());
				table.setText(i, 3, jobOrder.getBusinessNo()+"");
				table.setText(i, 4, jobOrder.getCustomer());
				table.setText(i, 5, jobOrder.getCustomerOrder()+"");
				
				table.setText(i, 6, jobOrder.getAmeType());
				table.setText(i, 7, jobOrder.getAmeCode());
				table.setText(i, 8, jobOrder.getAcard());
				table.setText(i, 9, jobOrder.getAportCode());
				
				table.setText(i, 10, jobOrder.getZmeType());
				table.setText(i, 11, jobOrder.getZmeCode());
				table.setText(i, 12, jobOrder.getZcard());
				table.setText(i, 13, jobOrder.getZportCode());
				
				table.setText(i, 14, DateUtils.formatDateToString(jobOrder.getCreateTime()));
				table.setText(i, 15, DateUtils.formatDateToString(jobOrder.getDeadline()));
				table.setText(i, 16, Constants.BusinessWorkListStatus[jobOrder.getStatus()]);
				
				continue;
			}
			if (status == jobOrder.getStatus()) {
				//
				table.setText(i, 0, jobOrder.getCode());
				table.setText(i, 1, jobOrder.getName());
				table.setText(i, 2, jobOrder.getBusinessType());
				table.setText(i, 3, jobOrder.getBusinessNo()+"");
				table.setText(i, 4, jobOrder.getCustomer());
				table.setText(i, 5, jobOrder.getCustomerOrder()+"");
				
				table.setText(i, 6, jobOrder.getAmeType());
				table.setText(i, 7, jobOrder.getAmeCode());
				table.setText(i, 8, jobOrder.getAcard());
				table.setText(i, 9, jobOrder.getAportCode());
			
				table.setText(i, 10, jobOrder.getZmeType());
				table.setText(i, 11, jobOrder.getZmeCode());
				table.setText(i, 12, jobOrder.getZcard());
				table.setText(i, 13, jobOrder.getZportCode());
				
				table.setText(i, 14, DateUtils.formatDateToString(jobOrder.getCreateTime()));
				table.setText(i, 15, DateUtils.formatDateToString(jobOrder.getDeadline()));
				table.setText(i, 16, Constants.BusinessWorkListStatus[jobOrder.getStatus()]);
			}
		}
		//每一次更新后，选中问题
		if (selectedRow == -1) {
			selectRow(0);
		}
	}

	/**
	 * 业务工单数据填充
	 */
	private void initBusinessWorkListTableHeader() {
		condition.clear();
		condition.insertItem("全部", 0);
		condition.insertItem("创建未建立路由", 1);// 创建未建立路由
		condition.insertItem("创建并已建立路由", 2);// 创建并已建立路由
		condition.insertItem("指派未完工", 3);// 指派未完工
		condition.insertItem("指派已完工", 4);// 指派已完工工单
		condition.insertItem("指派要求改纤", 5);// 指派要求改纤工单
		condition.insertItem("退单", 6);// 退单工单
		condition.insertItem("撤销", 7);// 撤销工单

		table.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "50px");
		header.getColumnFormatter().setWidth(1, "50px");
		header.getColumnFormatter().setWidth(2, "50px");
		header.getColumnFormatter().setWidth(3, "50px");
		header.getColumnFormatter().setWidth(4, "50px");
		header.getColumnFormatter().setWidth(5, "50px");
		header.getColumnFormatter().setWidth(6, "50px");
		header.getColumnFormatter().setWidth(7, "50px");
		header.getColumnFormatter().setWidth(8, "50px");
		header.getColumnFormatter().setWidth(9, "50px");
		header.getColumnFormatter().setWidth(10, "50px");
		header.getColumnFormatter().setWidth(11, "50px");
		header.getColumnFormatter().setWidth(12, "50px");
		header.getColumnFormatter().setWidth(13, "50px");
		header.getColumnFormatter().setWidth(14, "50px");
		header.getColumnFormatter().setWidth(15, "50px");
		header.getColumnFormatter().setWidth(16, "50px");

		// 待定？？？？，第一列 一定要是工单编码
		header.setText(0, 0, "工单编码");
		header.setText(0, 1, "工单名称");
		header.setText(0, 2, "业务类型");
		header.setText(0, 3, "业务编码");
		header.setText(0, 4, "顾客姓名");
		header.setText(0, 5, "顾客编号");
		header.setText(0, 6, "A设备名称");
		header.setText(0, 7, "A设备编码");
		header.setText(0, 8, "A端板卡");
		header.setText(0, 9, "A设备端口");
		header.setText(0, 10, "Z设备名称");
		header.setText(0, 11, "Z设备编码");
		header.setText(0, 12, "Z端板卡");
		header.setText(0, 13, "Z设备端口");
		header.setText(0, 14, "创建时间");
		header.setText(0, 15, "截止时间");
		header.setText(0, 16, "状态");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		table.getColumnFormatter().setWidth(0, "50px");
		table.getColumnFormatter().setWidth(1, "50px");
		table.getColumnFormatter().setWidth(2, "50px");
		table.getColumnFormatter().setWidth(3, "50px");
		table.getColumnFormatter().setWidth(4, "50px");
		table.getColumnFormatter().setWidth(5, "50px");
		table.getColumnFormatter().setWidth(6, "50px");
		table.getColumnFormatter().setWidth(7, "50px");
		table.getColumnFormatter().setWidth(8, "50px");
		table.getColumnFormatter().setWidth(9, "50px");
		table.getColumnFormatter().setWidth(10, "50px");
		table.getColumnFormatter().setWidth(11, "50px");
		table.getColumnFormatter().setWidth(12, "50px");
		table.getColumnFormatter().setWidth(13, "50px");
		table.getColumnFormatter().setWidth(14, "50px");
		table.getColumnFormatter().setWidth(15, "50px");
		table.getColumnFormatter().setWidth(16, "50px");

		HandlerRegistration handlerRegistration = condition
				.addChangeHandler(new ChangeHandler() {
					@Override
					public void onChange(ChangeEvent event) {
						// 过滤掉不满足条件的行
						int index = condition.getSelectedIndex();// 0 1 2 3 4 5 6 7
						updateBusinessWorkList(index);
					}
				});
		
		table.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Select the row that was clicked (-1 to account for header row).
				Cell cell = table.getCellForEvent(event);
				if (cell != null) {
					int row = cell.getRowIndex();
					selectRow(row);
					String jobOrderCode = table.getText(row, 0);
					Iterator<JobOrder> iterator = businessWorkList.iterator();
					JobOrder temp = null;
					while (iterator.hasNext()) {
						temp = iterator.next();
						if (jobOrderCode.equals(temp.getCode())) {
							break;
						}
					}
					//设置工单详细信息
					businessWorkListDetail.setJobOrder(temp);
				}
			}
		});
	}

	//设置业务工单列表，并更新业务工单列表
	public void setBusinessWorkList(List<JobOrder> businessWorkList) {
		this.businessWorkList = businessWorkList;
		// 初始化业务列表头部，过滤条件，过滤事件
		initBusinessWorkListTableHeader();
		// 填充业务工单数据，填充之前应该移除所有数据
		updateBusinessWorkList(0);
		//由于工单列表已经改变（就是说没有选中的行），指派按钮以及创建路由工单按钮应为不可用状态,工单详情信息表此时应该为空
		//businessWorkListDetail.setAddRouteWorkListEnabled(false);
		//businessWorkListDetail.setAssignTaskEnabled(false);
		businessWorkListDetail.updateDetailInfoToEmpty();
	}

	@UiHandler("refresh")
	public void onRefreshClick(ClickEvent event){
		updateBusinessWorkList();
	}
	
	//刷新表格数据，此时应该从数据库中再次读取数据
	public void updateBusinessWorkList(){
		businessWorkListService.getAllBusinessWorkList(allBusinessWorkListCallback);
	}
	
	private AsyncCallback<List<JobOrder>> allBusinessWorkListCallback = new AsyncCallback<List<JobOrder>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("刷新失败！！！");
		}
		@Override
		public void onSuccess(List<JobOrder> result) {
			if(result != null){
				setBusinessWorkList(result);
			}
		}
	};
	
	public void setWorkListDetail(BusinessWorkListDetail businessWorkListDetail) {
		this.businessWorkListDetail = businessWorkListDetail;
	}
	
	public BusinessWorkListDetail getBusinessWorkListDetail() {
		return businessWorkListDetail;
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
				table.getRowFormatter().addStyleName(row, style);
			} else {
				table.getRowFormatter().removeStyleName(row, style);
			}
		}
	}
}
