package com.wanma.client.alarm;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.uibinder.client.UiField;
import com.wanma.client.services.CommonService;
import com.wanma.client.services.CommonServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.client.utils.DateUtils;
import com.wanma.domain.AlarmLog;
import com.google.gwt.user.client.ui.ListBox;

public class AlarmManagerTable extends Composite {

	private static WorkListManagerTableUiBinder uiBinder = GWT
			.create(WorkListManagerTableUiBinder.class);

	interface WorkListManagerTableUiBinder extends
			UiBinder<Widget, AlarmManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private CommonServiceAsync commonService = GWT
			.create(CommonService.class);

	@UiField
	FlexTable table;
	@UiField
	FlexTable header;
	@UiField
	ListBox condition;
	@UiField
	SelectionStyle selectionStyle;

	private int selectedRow = -1;

	private String deviceType = "";

	private List<AlarmLog> alarmInfoList = null;

	public AlarmManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
		initAlarmWorkListTable();
		// workListDetail.setWorkListManagerTable(this);
	}

	private void initAlarmWorkListTable() {
		condition.clear();
		condition.insertItem("全部", 0);
		condition.insertItem("恢复正常", 1);//
		condition.insertItem("有跳纤插入", 2);//
		condition.insertItem("有跳纤拔出", 3);//

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

		//
		header.setText(0, 0, "控制器地址");
		header.setText(0, 1, "设备类型");
		header.setText(0, 2, "设备编码");
		header.setText(0, 3, "设备名称");
		header.setText(0, 4, "板卡序号");
		header.setText(0, 5, "端口序号");
		header.setText(0, 6, "告警类型");
		header.setText(0, 7, "告警时间");
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

		//移除事件  HandlerRegistration handlerRegistration =
		condition.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				// 过滤掉不满足条件的行
				int index = condition.getSelectedIndex();// 0 1 2 3
				updateAlarmInfoList(index);
			}
		});
		
		table.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell cell = table.getCellForEvent(event);
				if(cell != null){
					int row = cell.getRowIndex();
					selectRow(row);
				}
			}
		});
	}

	public void updateAlarmInfoList(int status) {

		table.removeAllRows();
		for (int i = 0; i < alarmInfoList.size(); i++) {
			AlarmLog alarm = alarmInfoList.get(i);
			// 等于0，全部显示
			if (0 == status) {
				table.setText(i, 0, alarm.getIfieldAddress());
				table.setText(i, 1, alarm.getDeviceType());
				table.setText(i, 2, alarm.getDeviceCode());
				table.setText(i, 3, alarm.getDeviceName());
				table.setText(i, 4, alarm.getBoard());
				table.setText(i, 5, alarm.getPort());
				table.setText(i, 6, Constants.alarmType[alarm.getAlarmtype()]);
				table.setText(i, 7, alarm.getLogtime() == null ? "--":DateUtils.formatDateToString(alarm.getLogtime()));

				continue;
			}
			// 等于过滤状态，则显示
			if (status == alarm.getAlarmtype() + 1) {
				table.setText(i, 0, alarm.getIfieldAddress());
				table.setText(i, 1, alarm.getDeviceType());
				table.setText(i, 2, alarm.getDeviceCode());
				table.setText(i, 3, alarm.getDeviceName());
				table.setText(i, 4, alarm.getBoard());
				table.setText(i, 5, alarm.getPort());
				table.setText(i, 6, Constants.alarmType[alarm.getAlarmtype() + 1]);
				table.setText(i, 7, alarm.getLogtime() == null ? "--":DateUtils.formatDateToString(alarm.getLogtime()));
			}
			// 每一次更新数据后，选中第一行
			if (selectedRow == -1) {
				selectRow(0);
			}
		}
	}

	// 刷新表中数据
	public void setAlarmInfoList(String deviceType,
			List<AlarmLog> alarmInfoList) {
		this.alarmInfoList = alarmInfoList;
		this.deviceType = deviceType;
		// 初始化列表头部，过滤条件，过滤事件
		initAlarmWorkListTable();
		// 填充数据，填充之前应该移除所有数据
		updateAlarmInfoList(0);
	}

	// 刷新表格数据，从数据库中获取数据后填充
	public void updateAlarmInfoList() {
		commonService.getAlarmLogInfoByDeviceType(deviceType, allAlarmListCallback);
	}

	private AsyncCallback<List<AlarmLog>> allAlarmListCallback = new AsyncCallback<List<AlarmLog>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("刷新数据失败！！！");
		}
		@Override
		public void onSuccess(List<AlarmLog> result) {
			if (result != null) {
				setAlarmInfoList(deviceType, result);
			}
		}
	};

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
