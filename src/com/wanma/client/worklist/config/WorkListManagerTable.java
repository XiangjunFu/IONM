package com.wanma.client.worklist.config;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import com.wanma.client.services.WorkListService;
import com.wanma.client.services.WorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.ConfigTask;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class WorkListManagerTable extends Composite {

	private static WorkListManagerTableUiBinder uiBinder = GWT
			.create(WorkListManagerTableUiBinder.class);

	interface WorkListManagerTableUiBinder extends
			UiBinder<Widget, WorkListManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private WorkListServiceAsync workListService = GWT
			.create(WorkListService.class);

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

	private int selectedRow = -1;

	private String deviceType = "";

	private WorkListDetail workListDetail;

	private List<ConfigTask> configWorkList = null;

	public WorkListManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
		initConfigWorkListTable();
		// workListDetail.setWorkListManagerTable(this);
	}

	private void initConfigWorkListTable() {
		condition.clear();
		condition.insertItem("全部", 0);
		condition.insertItem("预配置工单", 1);// 预配置工单
		condition.insertItem("已指派待施工", 2);// 已指派待施工工单
		condition.insertItem("施工中", 3);// 施工中
		condition.insertItem("完工待同步", 4);// 完工待同步工单
		condition.insertItem("完工已同步", 5);// 完工已同步工单
		condition.insertItem("拒绝待退单", 6);// 拒绝待退单工单
		condition.insertItem("取消工单", 7);// 取消工单

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

		//
		header.setText(0, 0, "工单编号");
		header.setText(0, 1, "设备名称");
		header.setText(0, 2, "工单状态");
		header.setText(0, 3, "施工班组");
		header.setText(0, 4, "施工人员");
		header.setText(0, 5, "创建时间");
		header.setText(0, 6, "截止时间");
		header.setText(0, 7, "创建者");
		header.setText(0, 8, "完成时间");
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

		//移除事件  HandlerRegistration handlerRegistration =
		condition.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// 过滤掉不满足条件的行
				int index = condition.getSelectedIndex();// 0 1 2 3 4 5 6 7
				updateConfigWorkList(index);
			}
		});

		table.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Select the row that was clicked (-1 to account for header
				// row).
				Cell cell = table.getCellForEvent(event);
				if (cell != null) {
					int row = cell.getRowIndex();
					selectRow(row);
					String configTaskCode = table.getText(row, 0);
					Iterator<ConfigTask> iterator = configWorkList.iterator();
					ConfigTask temp = null;
					while (iterator.hasNext()) {
						temp = iterator.next();
						if (configTaskCode.equals(temp.getCode())) {
							break;
						}
					}
					// 填充详细信息
					workListDetail.setConfigTask(temp);
				}
			}
		});
	}

	public void updateConfigWorkList(int status) {

		table.removeAllRows();
		for (int i = 0; i < configWorkList.size(); i++) {
			ConfigTask configTask = configWorkList.get(i);
			// 等于0，全部显示
			if (0 == status) {
				table.setText(i, 0, configTask.getCode());
				table.setText(i, 1, configTask.getDeviceName());
				table.setText(i, 2,
						Constants.ConfigWorkListStatus[configTask.getStatus()]);
				table.setText(i, 3, configTask.getGroupId());
				table.setText(i, 4, configTask.getWorkerId());
				table.setText(
						i,
						5,
						configTask.getCreateTime() == null ? "--" : DateUtils
								.formatDateToString(configTask.getCreateTime()));
				table.setText(
						i,
						6,
						configTask.getDeadline() == null ? "--" : DateUtils
								.formatDateToString(configTask.getDeadline()));
				table.setText(i, 7, configTask.getCreator());
				table.setText(
						i,
						8,
						configTask.getFinishedTime() == null ? "--" : DateUtils
								.formatDateToString(configTask
										.getFinishedTime()));

				continue;
			}
			// 等于过滤状态，则显示
			if (status == configTask.getStatus()) {
				table.setText(i, 0, configTask.getCode());
				table.setText(i, 1, configTask.getDeviceName());
				table.setText(i, 2,
						Constants.ConfigWorkListStatus[configTask.getStatus()]);
				table.setText(i, 3, configTask.getGroupId());
				table.setText(i, 4, configTask.getWorkerId());
				table.setText(
						i,
						5,
						configTask.getCreateTime() == null ? "--" : DateUtils
								.formatDateToString(configTask.getCreateTime()));
				table.setText(
						i,
						6,
						configTask.getDeadline() == null ? "--" : DateUtils
								.formatDateToString(configTask.getDeadline()));
				table.setText(i, 7, configTask.getCreator());
				table.setText(
						i,
						8,
						configTask.getFinishedTime() == null ? "--" : DateUtils
								.formatDateToString(configTask
										.getFinishedTime()));
			}
			// 每一次更新数据后，选中第一行
			if (selectedRow == -1) {
				selectRow(0);
			}
		}
	}

	// 刷新表中数据
	public void setConfigWorkList(String deviceType,
			List<ConfigTask> configWorkList) {
		this.configWorkList = configWorkList;
		this.deviceType = deviceType;
		// 初始化列表头部，过滤条件，过滤事件
		initConfigWorkListTable();
		// 填充数据，填充之前应该移除所有数据
		updateConfigWorkList(0);
		workListDetail.setAssignTaskEnabled(false);

		workListDetail.updateDetailInfoToEmpty();
	}

	// 刷新表格数据，从数据库中获取数据后填充
	public void updateConfigWorkList() {
		workListService.getAllConfigWorkList(deviceType,
				allConfigworkListCallback);
	}

	private AsyncCallback<List<ConfigTask>> allConfigworkListCallback = new AsyncCallback<List<ConfigTask>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("刷新数据失败！！！");
		}
		@Override
		public void onSuccess(List<ConfigTask> result) {
			if (result != null) {
				setConfigWorkList(deviceType, result);
			}
		}
	};

	@UiHandler("refresh")
	public void onRefreshClick(ClickEvent event) {
		updateConfigWorkList();
	}

	public void setWorkListDetail(WorkListDetail workListDetail) {
		this.workListDetail = workListDetail;
	}

	// 高亮选中行
	private void selectRow(int row) {
		/**
		 * 1、去除当前选中的行的高亮 2、高亮第row行
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
