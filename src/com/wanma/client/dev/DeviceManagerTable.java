package com.wanma.client.dev;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.google.gwt.user.client.ui.FlexTable;

public class DeviceManagerTable extends Composite {

	private static DeviceManagerTableUiBinder uiBinder = GWT
			.create(DeviceManagerTableUiBinder.class);
	
	@UiField FlexTable deviceTable;
	@UiField FlexTable header;

	interface DeviceManagerTableUiBinder extends UiBinder<Widget, DeviceManagerTable> {
	}
	
	public DeviceManagerTable() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void init(){
		header.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");
		header.getColumnFormatter().setWidth(6, "100px");
		
		header.setText(0, 0, "设备名称");
		header.setText(0, 1, "设备编码");
		header.setText(0, 2, "局站编码");
		header.setText(0, 3, "机房编码");
		header.setText(0, 4, "型号");
		header.setText(0, 5, "安装地址");
		header.setText(0, 6, "设施状态");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		deviceTable.getColumnFormatter().setWidth(0, "100px");
		deviceTable.getColumnFormatter().setWidth(1, "100px");
		deviceTable.getColumnFormatter().setWidth(2, "100px");
		deviceTable.getColumnFormatter().setWidth(3, "100px");
		deviceTable.getColumnFormatter().setWidth(4, "100px");
		deviceTable.getColumnFormatter().setWidth(5, "100px");
		deviceTable.getColumnFormatter().setWidth(6, "100px");
	}
	
	/**
	 * 初始化Ifdt表格头部信息
	 */
	public void initTableIfdtInfo(){
		header.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");
		
		header.setText(0, 0, "用户名称");
		header.setText(0, 1, "真实姓名");
		header.setText(0, 2, "电话号码");
		header.setText(0, 3, "施工组名");
		header.setText(0, 4, "电子邮件");
		header.setText(0, 5, "用户类型");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		deviceTable.getColumnFormatter().setWidth(0, "100px");
		deviceTable.getColumnFormatter().setWidth(1, "100px");
		deviceTable.getColumnFormatter().setWidth(2, "100px");
		deviceTable.getColumnFormatter().setWidth(3, "100px");
		deviceTable.getColumnFormatter().setWidth(4, "100px");
		deviceTable.getColumnFormatter().setWidth(5, "100px");
	}
	
	/**
	 * 更新表格Ifdt数据
	 * @param ifdts
	 */
	public void updateTableIfdtInfo(List<Ifdt> ifdts){
		init();
		for(int i  = 0;i < ifdts.size();i++){
			Ifdt ifdt = ifdts.get(i);
			deviceTable.setText(i, 0, ifdt.getName());
			deviceTable.setText(i, 1, ifdt.getCode());
			deviceTable.setText(i, 2, ifdt.getStationCode());
			deviceTable.setText(i, 3, ifdt.getHostRoomCode());
			deviceTable.setText(i, 4, ifdt.getType());
			deviceTable.setText(i, 5, ifdt.getAddress());
			deviceTable.setText(i, 6, ifdt.getServicdeStatus());
		}
	}
	
	
	public void initTableIfatInfo(){
		header.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");
		header.getColumnFormatter().setWidth(6, "100px");
		
		header.setText(0, 0, "设备名称");
		header.setText(0, 1, "设备编码");
		header.setText(0, 2, "局站编码");
		header.setText(0, 3, "机房编码");
		header.setText(0, 4, "型号");
		header.setText(0, 5, "安装地址");
		header.setText(0, 6, "设施状态");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		deviceTable.getColumnFormatter().setWidth(0, "100px");
		deviceTable.getColumnFormatter().setWidth(1, "100px");
		deviceTable.getColumnFormatter().setWidth(2, "100px");
		deviceTable.getColumnFormatter().setWidth(3, "100px");
		deviceTable.getColumnFormatter().setWidth(4, "100px");
		deviceTable.getColumnFormatter().setWidth(5, "100px");
		deviceTable.getColumnFormatter().setWidth(6, "100px");
	}
	
	/**
	 * 更新表格Ifat信息
	 * @param ifats
	 */
	public void updateTableIfatInfo(List<Ifat> ifats){
		initTableIfatInfo();
		for(int i = 0;i<ifats.size();i++){
			Ifat ifat = ifats.get(i);
			deviceTable.setText(i, 0, ifat.getName());
			deviceTable.setText(i, 1, ifat.getCode());
			deviceTable.setText(i, 2, ifat.getStationCode());
			deviceTable.setText(i, 3, ifat.getHostRoomCode());
			deviceTable.setText(i, 4, ifat.getConfigurationType());
			deviceTable.setText(i, 5, ifat.getInstallation());
			deviceTable.setText(i, 6, ifat.getServiceStatus());
		}
	}
	
	
	public void initTableIodfInfo(){
		header.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");
		
		header.setText(0, 0, "用户名称");
		header.setText(0, 1, "真实姓名");
		header.setText(0, 2, "电话号码");
		header.setText(0, 3, "施工组名");
		header.setText(0, 4, "电子邮件");
		header.setText(0, 5, "用户类型");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		deviceTable.getColumnFormatter().setWidth(0, "100px");
		deviceTable.getColumnFormatter().setWidth(1, "100px");
		deviceTable.getColumnFormatter().setWidth(2, "100px");
		deviceTable.getColumnFormatter().setWidth(3, "100px");
		deviceTable.getColumnFormatter().setWidth(4, "100px");
		deviceTable.getColumnFormatter().setWidth(5, "100px");
	}
	/**
	 * 更新表格Iodf信息
	 * @param iodfs
	 */
	public void updateTableIodfInfo(List<Iodf> iodfs){
		init();
		for(int i = 0;i<iodfs.size();i++){
			Iodf iodf = iodfs.get(i);
			deviceTable.setText(i, 0, iodf.getName());
			deviceTable.setText(i, 1, iodf.getCode());
			deviceTable.setText(i, 2, iodf.getStationCode());
			deviceTable.setText(i, 3, iodf.getHostRoomCode());
			deviceTable.setText(i, 4, iodf.getType());
			deviceTable.setText(i, 5, iodf.getAddress());
			deviceTable.setText(i, 6, iodf.getPhyStatus());
		}
	}
	
	
	public void initTableObdInfo(){
		header.removeAllRows();
		// Initialize the header.
		header.getColumnFormatter().setWidth(0, "100px");
		header.getColumnFormatter().setWidth(1, "100px");
		header.getColumnFormatter().setWidth(2, "100px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "100px");
		header.getColumnFormatter().setWidth(5, "100px");
		header.getColumnFormatter().setWidth(6, "100px");
		header.getColumnFormatter().setWidth(7, "100px");
		
		header.setText(0, 0, "OBD名称");
		header.setText(0, 1, "OBD编码");
		header.setText(0, 2, "局站编码");
		header.setText(0, 3, "机房编码");
		header.setText(0, 4, "所在设备类型");
		header.setText(0, 5, "所在设备编码");
		header.setText(0, 6, "分光比");
		header.setText(0, 7, "设施状态");
		//header.getCellFormatter().setHorizontalAlignment(0, 3, HasHorizontalAlignment.ALIGN_RIGHT);

		// Initialize the table.
		deviceTable.getColumnFormatter().setWidth(0, "100px");
		deviceTable.getColumnFormatter().setWidth(1, "100px");
		deviceTable.getColumnFormatter().setWidth(2, "100px");
		deviceTable.getColumnFormatter().setWidth(3, "100px");
		deviceTable.getColumnFormatter().setWidth(4, "100px");
		deviceTable.getColumnFormatter().setWidth(5, "100px");
		deviceTable.getColumnFormatter().setWidth(6, "100px");
		deviceTable.getColumnFormatter().setWidth(7, "100px");
	}
	
	/**
	 * 更新表格Obd信息
	 * @param obds
	 */
	public void updateTableObdInfo(List<Obd> obds){
		initTableObdInfo();
		for(int i = 0;i<obds.size();i++){
			Obd obd = obds.get(i);
			deviceTable.setText(i, 0, obd.getName());
			deviceTable.setText(i, 1, obd.getCode());
			deviceTable.setText(i, 2, obd.getStationCode());
			deviceTable.setText(i, 3, obd.getHostCode());
			deviceTable.setText(i, 4, obd.getParentType());
			deviceTable.setText(i, 5, obd.getParentCode());
			deviceTable.setText(i, 6, obd.getSplitRatio() + "");
			deviceTable.setText(i, 7, obd.getPhysicalStatue());
		}
	}
}
