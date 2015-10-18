package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.services.CheckWorkListService;
import com.wanma.client.services.CheckWorkListServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.domain.District;
import com.wanma.domain.HostRoom;
import com.wanma.domain.RouteCheckTask;
import com.wanma.domain.Station;
import com.wanma.domain.TerminalUnit;
import com.google.gwt.user.client.ui.Button;

/**
 * 空间资源信息表： 区域 局站 机房 设备 板卡
 */
public class SpaceResourceManagerTable extends Composite {

	private static SpaceResourceManagerTableUiBinder uiBinder = GWT
			.create(SpaceResourceManagerTableUiBinder.class);

	interface SpaceResourceManagerTableUiBinder extends
			UiBinder<Widget, SpaceResourceManagerTable> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	@UiField
	FlexTable boardTable;
	@UiField
	FlexTable boardHeader;
	@UiField
	SelectionStyle selectionStyle;
	@UiField
	Button refresh;

	private String deviceCode = "";

	private int selectedRow = -1;

	private List<TerminalUnit> boards = null;

	private DeviceBoardDetail deviceBoardDetail;

	private CheckWorkListServiceAsync checkWorkListService = GWT
			.create(CheckWorkListService.class);

	public SpaceResourceManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));

		// 为Table加点击事件，用户高亮所在行，显示详细信息。
		boardTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell cell = boardTable.getCellForEvent(event);
				if (cell != null) {
					int row = cell.getRowIndex();
					selectRow(row);
					// 显示详细信息
					// 获取板卡Id
					// int id = Integer.parseInt(boardTable.getText(row, 0));
					String boardCode = boardTable.getText(row, 0);
					deviceBoardDetail.setBoardCode(boardCode);
				}
			}
		});
	}

	/**
	 * 初始化表格头部信息
	 */
	public void initDistrictTable() {
		boardHeader.removeAllRows();
		// Initialize the header.
		boardHeader.getColumnFormatter().setWidth(0, "100px");
		boardHeader.getColumnFormatter().setWidth(1, "100px");

		boardHeader.setText(0, 0, "区域名称");
		boardHeader.setText(0, 1, "区域编码");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		boardTable.getColumnFormatter().setWidth(0, "100px");
		boardTable.getColumnFormatter().setWidth(1, "100px");
	}

	/**
	 * 表格填充区域信息 需要重置表格头部信息
	 * 
	 * @param spaceResources
	 */
	public void updateTableDistrictInfo(List<District> districts) {
		initDistrictTable();
		for (int i = 0; i < districts.size(); i++) {
			District district = districts.get(i);
			boardTable.setText(i, 0, district.getDistrictName());
			boardTable.setText(i, 1, district.getZipCode());
		}
	}

	public void initStationTableHeader() {
		boardHeader.removeAllRows();
		// Initialize the header.
		boardHeader.getColumnFormatter().setWidth(0, "100px");
		boardHeader.getColumnFormatter().setWidth(1, "100px");
		boardHeader.getColumnFormatter().setWidth(2, "100px");
		boardHeader.getColumnFormatter().setWidth(3, "100px");
		boardHeader.getColumnFormatter().setWidth(4, "100px");
		boardHeader.getColumnFormatter().setWidth(5, "100px");
		boardHeader.getColumnFormatter().setWidth(6, "100px");

		boardHeader.setText(0, 0, "名称");
		boardHeader.setText(0, 1, "编码");
		boardHeader.setText(0, 2, "区域");
		boardHeader.setText(0, 3, "区号");
		boardHeader.setText(0, 4, "地址");
		boardHeader.setText(0, 5, "类型");
		boardHeader.setText(0, 6, "设施状态");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		boardTable.getColumnFormatter().setWidth(0, "100px");
		boardTable.getColumnFormatter().setWidth(1, "100px");
		boardTable.getColumnFormatter().setWidth(2, "100px");
		boardTable.getColumnFormatter().setWidth(3, "100px");
		boardTable.getColumnFormatter().setWidth(4, "100px");
		boardTable.getColumnFormatter().setWidth(5, "100px");
		boardTable.getColumnFormatter().setWidth(6, "100px");
	}

	/**
	 * 表格填充局站信息
	 * 
	 * @param stations
	 */
	public void updateTableStationInfo(List<Station> stations) {
		initStationTableHeader();
		for (int i = 0; i < stations.size(); i++) {
			Station station = stations.get(i);
			boardTable.setText(i, 0, station.getName());
			boardTable.setText(i, 1, station.getCode());
			boardTable.setText(i, 2, station.getDistrictName());
			boardTable.setText(i, 3, station.getZipCode());
			boardTable.setText(i, 4, station.getAddress());
			boardTable.setText(i, 5, station.getStationType());
			boardTable.setText(i, 6, station.getPhysicalStatue());
		}
	}

	/**
	 * 
	 */
	public void initMachineRoomTableHeader() {
		boardHeader.removeAllRows();
		// Initialize the header.
		boardHeader.getColumnFormatter().setWidth(0, "100px");
		boardHeader.getColumnFormatter().setWidth(1, "100px");
		boardHeader.getColumnFormatter().setWidth(2, "100px");
		boardHeader.getColumnFormatter().setWidth(3, "100px");
		boardHeader.getColumnFormatter().setWidth(4, "100px");
		boardHeader.getColumnFormatter().setWidth(5, "100px");
		boardHeader.getColumnFormatter().setWidth(6, "100px");

		boardHeader.setText(0, 0, "名称");
		boardHeader.setText(0, 1, "编码");
		boardHeader.setText(0, 2, "局站");
		boardHeader.setText(0, 3, "类型");
		boardHeader.setText(0, 4, "地址");
		boardHeader.setText(0, 5, "楼层数");
		boardHeader.setText(0, 6, "长途/本地");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		boardTable.getColumnFormatter().setWidth(0, "100px");
		boardTable.getColumnFormatter().setWidth(1, "100px");
		boardTable.getColumnFormatter().setWidth(2, "100px");
		boardTable.getColumnFormatter().setWidth(3, "100px");
		boardTable.getColumnFormatter().setWidth(4, "100px");
		boardTable.getColumnFormatter().setWidth(5, "100px");
		boardTable.getColumnFormatter().setWidth(6, "100px");
	}

	/**
	 * 表格填充机房信息
	 * 
	 * @param hostRooms
	 */
	public void updateTableMachineRoomInfo(List<HostRoom> hostRooms) {
		initMachineRoomTableHeader();
		for (int i = 0; i < hostRooms.size(); i++) {
			HostRoom hostRoom = hostRooms.get(i);
			boardTable.setText(i, 0, hostRoom.getName());
			boardTable.setText(i, 1, hostRoom.getCode());
			boardTable.setText(i, 2, hostRoom.getStationName());
			boardTable.setText(i, 3, hostRoom.getHostType());
			boardTable.setText(i, 4, hostRoom.getAddress());
			boardTable.setText(i, 5, hostRoom.getFloors() + "");
			boardTable.setText(i, 6, hostRoom.getLocalOrremote() ? "长途" : "本地");
		}
	}

	private void initBoardTableInfo() {
		boardHeader.removeAllRows();
		// Initialize the header.
		boardHeader.getColumnFormatter().setWidth(0, "100px");
		boardHeader.getColumnFormatter().setWidth(1, "100px");
		boardHeader.getColumnFormatter().setWidth(2, "20px");
		boardHeader.getColumnFormatter().setWidth(3, "20px");
		boardHeader.getColumnFormatter().setWidth(4, "20px");
		boardHeader.getColumnFormatter().setWidth(5, "20px");
		boardHeader.getColumnFormatter().setWidth(6, "20px");
		boardHeader.getColumnFormatter().setWidth(7, "20px");
		boardHeader.getColumnFormatter().setWidth(8, "20px");
		boardHeader.getColumnFormatter().setWidth(9, "50px");
		boardHeader.getColumnFormatter().setWidth(10, "50px");
		//boardHeader.getColumnFormatter().setWidth(11, "50px");
		
		boardHeader.setText(0, 0, "板卡编码");
		//boardHeader.setText(0, 1, "板卡名称");
		boardHeader.setText(0, 1, "所在设备");
		boardHeader.setText(0, 2, "端口总数");
		boardHeader.setText(0, 3, "已用端口数");
		boardHeader.setText(0, 4, "损坏端口数");
		boardHeader.setText(0, 5, "行数");
		boardHeader.setText(0, 6, "列数");
		boardHeader.setText(0, 7, "机框号");
		boardHeader.setText(0, 8, "起始机框");
		boardHeader.setText(0, 9, "板卡状态");
		boardHeader.setText(0, 10, "板卡类型");
		// header.getCellFormatter().setHorizontalAlignment(0, 3,
		// HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		boardTable.removeAllRows();
		boardTable.getColumnFormatter().setWidth(0, "100px");
		//boardTable.getColumnFormatter().setWidth(1, "50px");
		boardTable.getColumnFormatter().setWidth(1, "100px");
		boardTable.getColumnFormatter().setWidth(2, "20px");
		boardTable.getColumnFormatter().setWidth(3, "20px");
		boardTable.getColumnFormatter().setWidth(4, "20px");
		boardTable.getColumnFormatter().setWidth(5, "20px");
		boardTable.getColumnFormatter().setWidth(6, "20px");
		boardTable.getColumnFormatter().setWidth(7, "20px");
		boardTable.getColumnFormatter().setWidth(8, "20px");
		boardTable.getColumnFormatter().setWidth(9, "50px");
		boardTable.getColumnFormatter().setWidth(10, "50px");
	}

	private void updateBoardTableInfo(List<TerminalUnit> boards) {
		initBoardTableInfo();
		for (int i = 0; i < boards.size(); i++) {
			TerminalUnit board = boards.get(i);
			boardTable.setText(i, 0, board.getTerminalUnitCode());
			//boardTable.setText(i, 1, board.getTerminalUnitName());
			boardTable.setText(i, 1, board.getHostDeviceId());
			boardTable.setText(i, 2, board.getTerminalNum() + "");
			boardTable.setText(i, 3, board.getOccupiedNum() + "");
			boardTable.setText(i, 4, board.getDamagedNum() + "");
			boardTable.setText(i, 5, board.getRowNumber() + "");
			boardTable.setText(i, 6, board.getColumnNumber() + "");
			boardTable.setText(i, 7, board.getFrameNum() + "");
			boardTable.setText(i, 8, board.getOriginalFrame() + "");
			boardTable.setText(i, 9, Constants.boardStatus[Integer.parseInt(board.getStatus())]);
			boardTable.setText(i, 10, Constants.boardType[Integer.parseInt(board.getType())]);
		}
		if (selectedRow == -1) {
			selectRow(0);
		}
	}

	public void setBoards(String deviceCode, List<TerminalUnit> boards) {
		this.deviceCode = deviceCode;
		this.boards = boards;
		updateBoardTableInfo(boards);
	}

	public void setDeviceBoardDetail(DeviceBoardDetail deviceBoardDetail) {
		this.deviceBoardDetail = deviceBoardDetail;
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
				boardTable.getRowFormatter().addStyleName(row, style);
			} else {
				boardTable.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

	@UiHandler("refresh")
	public void onRefreshClick(ClickEvent event){
		/**
		 * 刷新，创建某设备的巡检工单，
		 */
		RouteCheckTask checkWorkList = new RouteCheckTask();
		checkWorkList.setDeviceCode(deviceCode);
		checkWorkListService.addRouteCheckTask(checkWorkList, new AsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				if(result == null || "".equals(result)){
					Window.alert("刷新失败！！！");
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("刷新失败！！！");
			}
		});
	}
}
