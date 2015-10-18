package com.wanma.client.worklist.frame;

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
import com.wanma.client.services.FrameWorkListService;
import com.wanma.client.services.FrameWorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.FrameTask;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class FrameWorkListManagerTable extends Composite {

	private static WorkListManagerTableUiBinder uiBinder = GWT
			.create(WorkListManagerTableUiBinder.class);

	interface WorkListManagerTableUiBinder extends
			UiBinder<Widget, FrameWorkListManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private FrameWorkListServiceAsync frameWorkListService = GWT
			.create(FrameWorkListService.class);

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

	private FrameWorkListDetail frameWorkListDetail;

	private List<FrameTask> frameWorkList = null;

	public FrameWorkListManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
		initFrameWorkListTable();
		// workListDetail.setWorkListManagerTable(this);
	}

	private void initFrameWorkListTable() {
		condition.clear();
		condition.insertItem("全部", 0);
		condition.insertItem("未指派", 1);// 预配置工单
		condition.insertItem("已指派待施工", 2);// 已指派待施工工单
		condition.insertItem("施工中", 3);// 完工待工单
		condition.insertItem("完工待同步", 4);// 完工待工单
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
		header.getColumnFormatter().setWidth(9, "50px");
		header.getColumnFormatter().setWidth(10, "50px");

		//
		header.setText(0, 0, "工单编号");
		header.setText(0, 1, "工单名称");
		header.setText(0, 2, "工单状态");
		header.setText(0, 3, "施工班组");
		header.setText(0, 4, "施工人员");
		header.setText(0, 5, "创建时间");
		header.setText(0, 6, "创建者");
		header.setText(0, 7, "设备类型");
		header.setText(0, 8, "设备编码");
		header.setText(0, 9, "设备名称");
		header.setText(0, 10, "操作类型");
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
		// 移除事件 HandlerRegistration handlerRegistration =
		condition.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// 过滤掉不满足条件的行
				int index = condition.getSelectedIndex();// 0 1 2 3 4 5 6 7
				updateFrameWorkList(index);
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
					String frameTaskCode = table.getText(row, 0);
					Iterator<FrameTask> iterator = frameWorkList.iterator();
					FrameTask temp = null;
					while (iterator.hasNext()) {
						temp = iterator.next();
						if (frameTaskCode.equals(temp.getCode())) {
							break;
						}
					}
					// 填充详细信息
					frameWorkListDetail.setFrameTaskInfo(temp);
				}
			}
		});
	}

	public void updateFrameWorkList(int status) {

		table.removeAllRows();
		for (int i = 0; i < frameWorkList.size(); i++) {
			FrameTask frameTask = frameWorkList.get(i);
			// 等于0，全部显示
			if (0 == status) {
				table.setText(i, 0, frameTask.getCode());
				table.setText(i, 1, frameTask.getName());
				table.setText(i, 2,
						Constants.frameWorkListStatus[frameTask.getStatus()]);
				table.setText(i, 3, frameTask.getGroupId());
				table.setText(i, 4, frameTask.getWorkerId());
				table.setText(
						i,
						5,
						frameTask.getCreatetime() == null ? "--" : DateUtils
								.formatDateToString(frameTask.getCreatetime()));
				table.setText(i, 6, frameTask.getCreator());
				table.setText(i, 7, frameTask.getDeviceType());
				table.setText(i, 8, frameTask.getDeviceCode());
				table.setText(i, 9, frameTask.getDeviceName());
				table.setText(i, 10,
						Constants.frameOpType[Integer.parseInt(frameTask.getOpType())]);
				continue;
			}
			// 等于过滤状态，则显示
			if (status == frameTask.getStatus()) {
				table.setText(i, 0, frameTask.getCode());
				table.setText(i, 1, frameTask.getName());
				table.setText(i, 2,
						Constants.frameWorkListStatus[frameTask.getStatus()]);
				table.setText(i, 3, frameTask.getGroupId() == null ? ""
						: frameTask.getGroupId());
				table.setText(i, 4, frameTask.getWorkerId() == null ? ""
						: frameTask.getWorkerId());
				table.setText(
						i,
						5,
						frameTask.getCreatetime() == null ? "--" : DateUtils
								.formatDateToString(frameTask.getCreatetime()));
				table.setText(i, 6, frameTask.getCreator());
				table.setText(i, 7, frameTask.getDeviceType());
				table.setText(i, 8, frameTask.getDeviceCode());
				table.setText(i, 9, frameTask.getDeviceName());
				table.setText(i, 10,Constants.frameOpType[Integer.parseInt(frameTask.getOpType())]);
			}
			// 每一次更新数据后，选中第一行
			if (selectedRow == -1) {
				selectRow(0);
			}
		}

	}

	// 刷新表中数据
	public void setFrameWorkList(String deviceType,
			List<FrameTask> frameWorkList) {
		this.frameWorkList = frameWorkList;
		this.deviceType = deviceType;
		// 初始化列表头部，过滤条件，过滤事件
		initFrameWorkListTable();
		// 填充数据，填充之前应该移除所有数据
		updateFrameWorkList(0);

		frameWorkListDetail.setAssignTaskEnabled(false);

		frameWorkListDetail.updateDetailInfoToEmpty();
	}

	// 刷新表格数据，从数据库中获取数据后填充
	public void updateFrameWorkList() {
		frameWorkListService.getAllFrameWorkList(deviceType,
				allFrameworkListCallback);
	}

	private AsyncCallback<List<FrameTask>> allFrameworkListCallback = new AsyncCallback<List<FrameTask>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("刷新数据失败！！！");
		}

		@Override
		public void onSuccess(List<FrameTask> result) {
			if (result != null) {
				setFrameWorkList(deviceType, result);
			}
		}
	};

	@UiHandler("refresh")
	public void onRefreshClick(ClickEvent event) {
		updateFrameWorkList();
	}

	public void setFrameWorkListDetail(FrameWorkListDetail checkWorkListDetail) {
		this.frameWorkListDetail = checkWorkListDetail;
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
