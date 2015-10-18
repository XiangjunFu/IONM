package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Frame;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

@RemoteServiceRelativePath("opticalDeviceService")
public interface OpticalDeviceService extends RemoteService {

	/**
	 * IFDT有关操作
	 */
	
	public Integer addIFDT(Ifdt ifdt);
	
	public List<Ifdt> getIfdts();
	
	public Ifdt getIfdtByCode(String paramDeviceCode);

	/**
	 * OBD相关操作
	 */
	public Integer addOBD(Obd obd);
	
	public List<Obd> getObds();

	public Obd getObdInfoByCode(String paramDeviceCode);
	
	/**
	 * IODF相关操作
	 */
	public Integer addIODF(Iodf iodf);

	public List<Iodf> getIodfs();
	
	public Iodf getIodfInfoByCode(String paramDeviceCode);
	
	/**
	 * IFAT相关操作
	 */
	public Integer addIFAT(Ifat ifat);
	
	public List<Ifat> getIfats();
	
	public Ifat getIfatInfoByCode(String paramDeviceCode);
	
	
	/**
	 * Port操作
	 */
	
	public List<Port> getAllPortInfo();
	
	public Integer addPortInfo(Port port);
	
	public List<Port> getPortInfoByDeviceName(String deviceName);
	
	public List<Port> getPortsByBoardCode(String boardCode,String status);
	
	/**
	 * 板卡相关操作
	 */
	public List<TerminalUnit> getBoards(String deviceName);
	
	public List<TerminalUnit> getBoardsByHostDeviceID(String hostDeviceID);
	
	public List<TerminalUnit> getBoardsByHostDeviceID(String hostDeviceID,String status);
	
	public TerminalUnit getBoardByBoardCode(String boardCode);
	
	public Integer addBoard(TerminalUnit terminalUnit);
	
	public Integer deleteBoardInfos(String boardCode);
	
	/**
	 * Frame
	 */
	
	public Integer addFrame(Frame frame);
	
	/**
	 * 其他操作
	 * @return
	 */
	public Integer upDatePosition(String deviceCode, int x, int y);
	
	public List<DeviceCommonInfo> getAllDeviceInfo();
	
	public List<Port> getPortsByDeviceCode(String deviceCode);
	
	public List<DeviceCommonInfo> getDeviceCommonInfosByType(String type);
	
	public List<DeviceCommonInfo> getDeviceInfoByHostRoomName(String hostRoomName);
	
	public DeviceCommonInfo getDeviceCommonInfosByDeviceCode(String deviceCode);
}
