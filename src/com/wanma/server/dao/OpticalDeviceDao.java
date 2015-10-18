package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Frame;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

public interface OpticalDeviceDao {

	public abstract Integer addIFDT(Ifdt ifdt);

	public abstract List<Ifdt> getIfdts();

	public abstract Integer addOBD(Obd obd);

	public abstract Integer addIODF(Iodf iodf);

	public abstract Integer addIFAT(Ifat ifat);

	public abstract List<TerminalUnit> getBoards(String deviceName);

	public abstract List<DeviceCommonInfo> getAllDeviceInfo();

	public abstract List<Obd> getObds();

	public abstract List<Iodf> getIodfs();

	public abstract List<Ifat> getIfats();

	public abstract Ifdt getIfdtByCode(String paramDeviceCode);

	public abstract Obd getObdInfoByCode(String paramDeviceCode);

	public abstract Iodf getIodfInfoByCode(String paramDeviceCode);

	public abstract Ifat getIfatInfoByCode(String paramDeviceCode);

	public abstract List<DeviceCommonInfo> getDeviceInfosByHostRoomName(
			String name);

	public abstract List<Port> getPortsByDeviceCode(String deviceCode);

	public abstract void addDeviceCommonInfo(DeviceCommonInfo deviceCommonInfo);

	public abstract List<DeviceCommonInfo> getDeviceInfosByType(String type);

	public abstract Integer savePort(Port port);

	public abstract List<Port> getAllPorts();

	public abstract List<Port> getPortsByDeviceName(String deviceName);

	public abstract List<TerminalUnit> getBoardsByHostDeviceID(
			String hostDeviceID,String status);

	public abstract List<Port> getPortsByBoardCode(String boardCode,String status);

	public abstract TerminalUnit getBoardByBoardCode(String boardCode);

	public abstract DeviceCommonInfo getDeviceCommonInfoByDeviceCode(
			String deviceCode);

	public abstract Integer addBoard(TerminalUnit terminalUnit);

	public abstract Integer addFrame(Frame frame);

	public abstract DeviceCommonInfo getDeviceCommonInfoByDeviceName(String deviceName);
	
	//更新机框状态
	public boolean updateDeviceFrameStatus(String deviceCode,String frameNum,String status);
	
	//更新板卡状态
	public boolean updateDeviceBoardStatus(String deviceCode,String frameNum,String boardNum,String status);
	
	//删除机框
	public boolean deleteFrame(String deviceCode,String frameNum);
	
	//删除板卡
	public boolean deleteBoard(String deviceCode,String frameNum,String boardNum);
	
	//更新设备信息
	public boolean updateDeviceInfo(String preCode,String newCode,String infos);
	
	//更新设备编码
	public boolean updateDeviceCode(String preCode, String newCode);
	
	//判断机框是否存在
	public boolean judgeFrameExist(String deviceCode,int frameNum);
	
	//判断板卡是否存在
	public boolean judgeBoardExist(String deviceCode,int boardNum);
	
	//增加子框
	public int addFrameInfo(String deviceCode,int frameNum,int comment);
	
	//增加板卡
	public int addBoardInfo(String deviceCode,int frameNum,int boardNum,int comment);
	
	//跳纤
	public boolean jumpLine(String deviceCode, String aBoard, String aPort,
			String zBoard, String zPort, String comment);

	//拆纤
	public boolean removeLine(String deviceCode, String aBoard, String aPort,
			String zBoard, String zPort, String comment);

	public abstract Integer deleteBoardInfo(String boardCode);

	public abstract Integer upDatePosition(String deviceCode, int x, int y);
}