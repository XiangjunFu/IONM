package com.wanma.client.worklist.board;

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
import com.wanma.client.services.BoardWorkListService;
import com.wanma.client.services.BoardWorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.BoardTask;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public class BoardWorkListManagerTable extends Composite {

	private static WorkListManagerTableUiBinder uiBinder = GWT
			.create(WorkListManagerTableUiBinder.class);

	interface WorkListManagerTableUiBinder extends
			UiBinder<Widget, BoardWorkListManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private BoardWorkListServiceAsync boardWorkListService = GWT
			.create(BoardWorkListService.class);

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

	private BoardWorkListDetail boardWorkListDetail;

	private List<BoardTask> boardWorkList = null;

	public BoardWorkListManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
		initBoardWorkListTable();
		// workListDetail.setWorkListManagerTable(this);
	}

	private void initBoardWorkListTable() {
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
				updateBoardWorkList(index);
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
					String checkTaskCode = table.getText(row, 0);
					Iterator<BoardTask> iterator = boardWorkList.iterator();
					BoardTask temp = null;
					while (iterator.hasNext()) {
						temp = iterator.next();
						if (checkTaskCode.equals(temp.getCode())) {
							break;
						}
					}
					// 填充详细信息
					boardWorkListDetail.setBoardTaskInfo(temp);
				}
			}
		});
	}

	public void updateBoardWorkList(int status) {

		table.removeAllRows();
		for (int i = 0; i < boardWorkList.size(); i++) {
			BoardTask boardTask = boardWorkList.get(i);
			// 等于0，全部显示
			if (0 == status) {
				table.setText(i, 0, boardTask.getCode());
				table.setText(i, 1, boardTask.getName());
				table.setText(i, 2,
						Constants.boardWorkListStatus[boardTask.getStatus()]);
				table.setText(i, 3, boardTask.getGroupId());
				table.setText(i, 4, boardTask.getWorkerId());
				table.setText(
						i,
						5,
						boardTask.getCreatetime() == null ? "--" : DateUtils
								.formatDateToString(boardTask.getCreatetime()));
				table.setText(i, 6, boardTask.getCreator());
				table.setText(i, 7, boardTask.getDeviceType());
				table.setText(i, 8, boardTask.getDeviceCode());
				table.setText(i, 9, boardTask.getDeviceName());
				table.setText(i, 10, Constants.boardOpType[Integer.parseInt(boardTask.getOpType())]);
				continue;
			}
			// 等于过滤状态，则显示
			if (status == boardTask.getStatus()) {
				table.setText(i, 0, boardTask.getCode());
				table.setText(i, 1, boardTask.getName());
				table.setText(i, 2,
						Constants.boardWorkListStatus[boardTask.getStatus()]);
				table.setText(i, 3, boardTask.getGroupId() == null ? ""
						: boardTask.getGroupId());
				table.setText(i, 4, boardTask.getWorkerId() == null ? ""
						: boardTask.getWorkerId());
				table.setText(
						i,
						5,
						boardTask.getCreatetime() == null ? "--" : DateUtils
								.formatDateToString(boardTask.getCreatetime()));
				table.setText(i, 6, boardTask.getCreator());
				table.setText(i, 7, boardTask.getDeviceType());
				table.setText(i, 8, boardTask.getDeviceCode());
				table.setText(i, 9, boardTask.getDeviceName());
				table.setText(i, 10, Constants.boardOpType[Integer.parseInt(boardTask.getOpType())]);
			}
			// 每一次更新数据后，选中第一行
			if (selectedRow == -1) {
				selectRow(0);
			}
		}
	}

	// 刷新表中数据
	public void setBoardWorkList(String deviceType,
			List<BoardTask> boardWorkList) {
		this.boardWorkList = boardWorkList;
		this.deviceType = deviceType;
		// 初始化列表头部，过滤条件，过滤事件
		initBoardWorkListTable();
		// 填充数据，填充之前应该移除所有数据
		updateBoardWorkList(0);

		boardWorkListDetail.setAssignTaskEnabled(false);

		boardWorkListDetail.updateDetailInfoToEmpty();
	}

	// 刷新表格数据，从数据库中获取数据后填充
	public void updateBoardWorkList() {
		boardWorkListService.getAllBoardWorkList(deviceType,
				allBoardworkListCallback);
	}

	private AsyncCallback<List<BoardTask>> allBoardworkListCallback = new AsyncCallback<List<BoardTask>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("刷新数据失败！！！");
		}

		@Override
		public void onSuccess(List<BoardTask> result) {
			if (result != null) {
				setBoardWorkList(deviceType, result);
			}
		}
	};

	@UiHandler("refresh")
	public void onRefreshClick(ClickEvent event) {
		updateBoardWorkList();
	}

	public void setBoardWorkListDetail(BoardWorkListDetail boardWorkListDetail) {
		this.boardWorkListDetail = boardWorkListDetail;
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
