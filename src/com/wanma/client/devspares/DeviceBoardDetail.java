package com.wanma.client.devspares;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.Messages.Select;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Widget;
import com.wanma.client.devspares.SpaceResourceManagerTable.SelectionStyle;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.client.services.OpticalDeviceServiceAsync;
import com.wanma.client.utils.Constants;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

public class DeviceBoardDetail extends Composite {

	private static DeviceBoardDetailUiBinder uiBinder = GWT
			.create(DeviceBoardDetailUiBinder.class);

	interface DeviceBoardDetailUiBinder extends
			UiBinder<Widget, DeviceBoardDetail> {
	}

	@UiField
	HTML boardCode;
	@UiField
	HTML boardName;
	@UiField
	FlexTable portTable;
	@UiField
	FlexTable portHeader;
	@UiField
	SelectionStyle selectionStyle;
	@UiField
	Button addPort;
	@UiField
	Button delBoard;
	
	private int selectedRow = -1;
	
	private TerminalUnit board;
	
	private int boardId = -1;
	
	private OpticalDeviceServiceAsync opticalDeviceService = GWT.create(OpticalDeviceService.class);
	
	public DeviceBoardDetail() {
		initWidget(uiBinder.createAndBindUi(this));
		addPort.setEnabled(false);
		portTable.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Cell cell  = portTable.getCellForEvent(event);
				if(cell != null){
					int row = cell.getRowIndex();
					selectRow(row);
				}
			}
		});
	}

	//
	private void setBoard(TerminalUnit board) {
		this.board = board;
		//获取列表端口信息列表
		String boardCode = board.getTerminalUnitCode();
		opticalDeviceService.getPortsByBoardCode(boardCode,"",allPortsCallback);
		addPort.setEnabled(true);
	}

	//
	public void setBoardCode(String boardCode){
		this.boardCode.setText(boardCode);
		//状态设置为"",表示要查询该板卡上 所有端口
		opticalDeviceService.getPortsByBoardCode(boardCode,"",allPortsCallback);
		opticalDeviceService.getBoardByBoardCode(boardCode, getBoardCallback);
	}
	
	private AsyncCallback<TerminalUnit> getBoardCallback = new AsyncCallback<TerminalUnit>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取信息失败！！！");
		}

		@Override
		public void onSuccess(TerminalUnit result) {
			if(result != null){
				addPort.setEnabled(true);
				delBoard.setEnabled(!"3".equals(result.getStatus()));//如果板卡状态为3，表示已经删除
			}
		}
	};
	
	//
	private void setBoardId(int id) {
		this.boardId = id;
		addPort.setEnabled(true);
		//通过Id获取属于当前板卡的端口信息
		//opticalDeviceService.getPortsByBoardId(id,allPortsCallback);
	}
	
	private AsyncCallback<List<Port>> allPortsCallback = new AsyncCallback<List<Port>>() {
		@Override
		public void onFailure(Throwable caught) {
			Window.alert("获取端口失败！！！");
		}
		@Override
		public void onSuccess(List<Port> ports) {
			if(ports != null){
				updateportTableInfo(ports);
			}
		}
	};
	
	private void initPortTableInfo(){
		portHeader.removeAllRows();
		// Initialize the header.
		portHeader.getColumnFormatter().setWidth(0, "100px");
		//portHeader.getColumnFormatter().setWidth(1, "100px");
		portHeader.getColumnFormatter().setWidth(1, "100px");
		portHeader.getColumnFormatter().setWidth(2, "50px");
		portHeader.getColumnFormatter().setWidth(3, "100px");
		portHeader.getColumnFormatter().setWidth(4, "30px");
		portHeader.getColumnFormatter().setWidth(5, "20px");
		portHeader.getColumnFormatter().setWidth(6, "20px");
		portHeader.getColumnFormatter().setWidth(7, "20px");
		portHeader.getColumnFormatter().setWidth(8, "20px");
		portHeader.getColumnFormatter().setWidth(9, "20px");
		portHeader.getColumnFormatter().setWidth(10, "20px");
		portHeader.getColumnFormatter().setWidth(11, "50px");
		//portHeader.getColumnFormatter().setWidth(12, "50px");
		
		portHeader.setText(0, 0, "端口编码");
		//portHeader.setText(0, 1, "端口名称");
		portHeader.setText(0, 1, "所属板卡");
		portHeader.setText(0, 2, "所属设备类型");
		portHeader.setText(0, 3, "所属设备编码");
		portHeader.setText(0, 4, "端口类型");
		portHeader.setText(0, 5, "行号");
		portHeader.setText(0, 6, "列号");
		portHeader.setText(0, 7, "机框号");
		portHeader.setText(0, 8, "框内端口号");
		portHeader.setText(0, 9, "槽号");
		portHeader.setText(0, 10, "端口号");
		//portHeader.setText(0, 11, "物理状态");
		portHeader.setText(0, 11, "业务状态");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		portTable.removeAllRows();
		portTable.getColumnFormatter().setWidth(0, "100px");
		//portTable.getColumnFormatter().setWidth(1, "50px");
		portTable.getColumnFormatter().setWidth(1, "100px");
		portTable.getColumnFormatter().setWidth(2, "50px");
		portTable.getColumnFormatter().setWidth(3, "100px");
		portTable.getColumnFormatter().setWidth(4, "30px");
		portTable.getColumnFormatter().setWidth(5, "20px");
		portTable.getColumnFormatter().setWidth(6, "20px");
		portTable.getColumnFormatter().setWidth(7, "20px");
		portTable.getColumnFormatter().setWidth(8, "20px");
		portTable.getColumnFormatter().setWidth(9, "20px");
		portTable.getColumnFormatter().setWidth(10, "20px");
		portTable.getColumnFormatter().setWidth(11, "50px");
		//portTable.getColumnFormatter().setWidth(12, "50px");
	}
	
	private void updateportTableInfo(List<Port> ports){
		initPortTableInfo();
		for(int i = 0;i<ports.size();i++){
			Port port = ports.get(i);
			portTable.setText(i, 0, port.getCode());
			//portTable.setText(i, 1, port.getName());
			portTable.setText(i, 1, port.getParentCode());
			portTable.setText(i, 2, port.getMeType());
			portTable.setText(i, 3, port.getMeCode());
			portTable.setText(i, 4, port.getPortType());
			portTable.setText(i, 5, port.getRowIndex()+"");
			portTable.setText(i, 6, port.getColIndex()+"");
			portTable.setText(i, 7, port.getCellNo()+"");
			portTable.setText(i, 8, port.getPortSequence()+"");
			portTable.setText(i, 9, port.getSlotNo()+"");
			portTable.setText(i, 10, port.getPortNo()+"");
			//portTable.setText(i, 11, Constants.portStatus[Integer.parseInt(port.getPhysicalStatue())]);
			portTable.setText(i, 11, Constants.serviceStatus[Integer.parseInt(port.getServiceStatue())]);
		}
		
		if(selectedRow == -1){
			selectRow(0);
		}
	}
	
	public void setAddPortEnabled(boolean enabled){
		addPort.setEnabled(enabled);
	}
	
	@UiHandler("delBoard")
	public void onDelBoardClick(ClickEvent event){
		String boardCode = this.boardCode.getText();
		opticalDeviceService.deleteBoardInfos(boardCode,new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("删除失败！！！");
			}
			@Override
			public void onSuccess(Integer result) {
				if(result == null || result == -1){
					Window.alert("删除失败");
				}
			}
		});
	}
	
	@UiHandler("addPort")
	public void onAddPortClick(ClickEvent event){
		String boardCode = this.boardCode.getText();
		AddPortInfo addPortInfo = new AddPortInfo(boardCode,this);
		addPortInfo.center();
		addPortInfo.show();
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
				portTable.getRowFormatter().addStyleName(row, style);
			} else {
				portTable.getRowFormatter().removeStyleName(row, style);
			}
		}
	}
}
