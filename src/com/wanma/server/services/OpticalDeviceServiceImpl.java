package com.wanma.server.services;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.OpticalDeviceService;
import com.wanma.domain.BoardTask;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Frame;
import com.wanma.domain.FrameTask;
import com.wanma.domain.HostRoom;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.wanma.domain.Port;
import com.wanma.domain.Station;
import com.wanma.domain.TerminalUnit;
import com.wanma.server.dao.BoardWorkListDao;
import com.wanma.server.dao.FrameWorkListDao;
import com.wanma.server.dao.MachineRoomDao;
import com.wanma.server.dao.OpticalDeviceDao;
import com.wanma.server.dao.StationDao;
import com.wanma.server.dao.impl.BoardWorkListDaoImpl;
import com.wanma.server.dao.impl.FrameWorkListDaoImpl;
import com.wanma.server.dao.impl.MachineRoomDaoImpl;
import com.wanma.server.dao.impl.OpticalDeviceDaoImpl;
import com.wanma.server.dao.impl.StationDaoImpl;

public class OpticalDeviceServiceImpl extends RemoteServiceServlet implements
		OpticalDeviceService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6093960664389241798L;

	private OpticalDeviceDao opticalDeviceDao;
	
	private MachineRoomDao machineRoomDao;
	
	private StationDao stationDao;
	
	private BoardWorkListDao boardWorkListDao = null;
	
	private FrameWorkListDao frameWorkListDao = null;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		opticalDeviceDao = new OpticalDeviceDaoImpl();
		machineRoomDao = new MachineRoomDaoImpl();
		stationDao = new StationDaoImpl();
		boardWorkListDao = new BoardWorkListDaoImpl();
		frameWorkListDao = new FrameWorkListDaoImpl();
	}

	@Override
	public Integer addIFDT(Ifdt ifdt) {
		HostRoom hostRoom = machineRoomDao.getMachineRoomNameByCode(ifdt.getHostRoomCode());
		DeviceCommonInfo deviceCommonInfo = new DeviceCommonInfo();
		
		deviceCommonInfo.setDeviceCode(ifdt.getCode());
		deviceCommonInfo.setDeviceName(ifdt.getName());
		deviceCommonInfo.setDeviceType(ifdt.getType());
		deviceCommonInfo.setHostRoomName(hostRoom.getName());
		deviceCommonInfo.setHostRoomCode(ifdt.getHostRoomCode());
		deviceCommonInfo.setStationCode(ifdt.getStationCode());
		Station station = stationDao.getStationByCode(ifdt.getStationCode());
		deviceCommonInfo.setStationName(station.getName());
		deviceCommonInfo.setXposition(ifdt.getXpositon());
		deviceCommonInfo.setYposition(ifdt.getYposition());
		
		opticalDeviceDao.addDeviceCommonInfo(deviceCommonInfo);
		
		Integer ifdtIden = opticalDeviceDao.addIFDT(ifdt);
		return ifdtIden;
	}

	@Override
	public List<Ifdt> getIfdts() {
		return opticalDeviceDao.getIfdts();
	}

	@Override
	public Integer addOBD(Obd obd) {
		HostRoom hostRoom = machineRoomDao.getMachineRoomNameByCode(obd.getHostCode());
		DeviceCommonInfo deviceCommonInfo = new DeviceCommonInfo();
		deviceCommonInfo.setDeviceCode(obd.getCode());
		deviceCommonInfo.setDeviceName(obd.getName());
		deviceCommonInfo.setDeviceType(obd.getType());
		deviceCommonInfo.setHostRoomName(hostRoom.getName());
		deviceCommonInfo.setHostRoomCode(obd.getHostCode());
		deviceCommonInfo.setStationCode(obd.getStationCode());
		Station station = stationDao.getStationByCode(obd.getStationCode());
		deviceCommonInfo.setStationName(station.getName());
		opticalDeviceDao.addDeviceCommonInfo(deviceCommonInfo);
		return opticalDeviceDao.addOBD(obd);
	}

	@Override
	public Integer addIODF(Iodf iodf) {
		HostRoom hostRoom = machineRoomDao.getMachineRoomNameByCode(iodf.getHostRoomCode());
		DeviceCommonInfo deviceCommonInfo = new DeviceCommonInfo();
		deviceCommonInfo.setDeviceCode(iodf.getCode());
		deviceCommonInfo.setDeviceName(iodf.getName());
		deviceCommonInfo.setDeviceType(iodf.getType());
		deviceCommonInfo.setHostRoomName(hostRoom.getName());
		deviceCommonInfo.setHostRoomCode(iodf.getHostRoomCode());
		deviceCommonInfo.setStationCode(iodf.getStationCode());
		Station station = stationDao.getStationByCode(iodf.getStationCode());
		deviceCommonInfo.setStationName(station.getName());
		deviceCommonInfo.setXposition(iodf.getXpositon());
		deviceCommonInfo.setYposition(iodf.getYposition());
		opticalDeviceDao.addDeviceCommonInfo(deviceCommonInfo);
		
		return opticalDeviceDao.addIODF(iodf);
	}

	@Override
	public Integer addIFAT(Ifat ifat) {
		HostRoom hostRoom = machineRoomDao.getMachineRoomNameByCode(ifat.getHostRoomCode());
		DeviceCommonInfo deviceCommonInfo = new DeviceCommonInfo();
		deviceCommonInfo.setDeviceCode(ifat.getCode());
		deviceCommonInfo.setDeviceName(ifat.getName());
		deviceCommonInfo.setDeviceType(ifat.getType());
		deviceCommonInfo.setHostRoomName(hostRoom.getName());
		deviceCommonInfo.setHostRoomCode(ifat.getHostRoomCode());
		deviceCommonInfo.setStationCode(ifat.getStationCode());
		Station station = stationDao.getStationByCode(ifat.getStationCode());
		deviceCommonInfo.setStationName(station.getName());
		deviceCommonInfo.setXposition(ifat.getXpositon());
		deviceCommonInfo.setYposition(ifat.getYpositon());
		
		opticalDeviceDao.addDeviceCommonInfo(deviceCommonInfo);
		return opticalDeviceDao.addIFAT(ifat);
	}

	@Override
	public List<TerminalUnit> getBoards(String deviceName) {
		DeviceCommonInfo deviceCommonInfo = opticalDeviceDao.getDeviceCommonInfoByDeviceName(deviceName);
		return opticalDeviceDao.getBoardsByHostDeviceID(deviceCommonInfo.getDeviceCode(),"");
	}

	@Override
	public List<DeviceCommonInfo> getAllDeviceInfo() {
		return opticalDeviceDao.getAllDeviceInfo();
	}

	@Override
	public List<Obd> getObds() {
		return opticalDeviceDao.getObds();
	}

	@Override
	public List<Iodf> getIodfs() {
		return opticalDeviceDao.getIodfs();
	}

	@Override
	public List<Ifat> getIfats() {
		return opticalDeviceDao.getIfats();
	}

	@Override
	public Ifdt getIfdtByCode(String paramDeviceCode) {
		return opticalDeviceDao.getIfdtByCode(paramDeviceCode);
	}
	
	@Override
	public Obd getObdInfoByCode(String paramDeviceCode) {
		return opticalDeviceDao.getObdInfoByCode(paramDeviceCode);
	}

	@Override
	public Iodf getIodfInfoByCode(String paramDeviceCode) {
		return opticalDeviceDao.getIodfInfoByCode(paramDeviceCode);
	}

	@Override
	public Ifat getIfatInfoByCode(String paramDeviceCode) {
		return opticalDeviceDao.getIfatInfoByCode(paramDeviceCode);
	}

	@Override
	public List<Port> getPortsByDeviceCode(String deviceCode) {
		return opticalDeviceDao.getPortsByDeviceCode(deviceCode);
	}

	@Override
	public List<DeviceCommonInfo> getDeviceCommonInfosByType(String type) {
		return opticalDeviceDao.getDeviceInfosByType(type);
	}

	@Override
	public Integer addPortInfo(Port port) {
		return opticalDeviceDao.savePort(port);
	}

	@Override
	public List<DeviceCommonInfo> getDeviceInfoByHostRoomName(
			String hostRoomName) {
		return opticalDeviceDao.getDeviceInfosByHostRoomName(hostRoomName);
	}

	@Override
	public List<Port> getAllPortInfo() {
		return opticalDeviceDao.getAllPorts();
	}

	@Override
	public List<Port> getPortInfoByDeviceName(String deviceName) {
		return opticalDeviceDao.getPortsByDeviceName(deviceName);
	}

	@Override
	public List<TerminalUnit> getBoardsByHostDeviceID(String hostDeviceID) {
		return opticalDeviceDao.getBoardsByHostDeviceID(hostDeviceID,"");
	}

	@Override
	public List<TerminalUnit> getBoardsByHostDeviceID(String hostDeviceID,
			String status) {
		return opticalDeviceDao.getBoardsByHostDeviceID(hostDeviceID,status);
	}
	
	@Override
	public List<Port> getPortsByBoardCode(String boardCode,String status) {
		return opticalDeviceDao.getPortsByBoardCode(boardCode,status);
	}

	@Override
	public TerminalUnit getBoardByBoardCode(String boardCode) {
		return opticalDeviceDao.getBoardByBoardCode(boardCode);
	}

	@Override
	public Integer upDatePosition(String deviceCode, int x, int y) {
		return opticalDeviceDao.upDatePosition(deviceCode,x,y);
	}
	
	@Override
	public DeviceCommonInfo getDeviceCommonInfosByDeviceCode(String deviceCode) {
		return opticalDeviceDao.getDeviceCommonInfoByDeviceCode(deviceCode);
	}

	@Override
	public Integer addBoard(TerminalUnit terminalUnit) {
		
		DeviceCommonInfo device = this.getDeviceCommonInfosByDeviceCode(terminalUnit.getHostDeviceId());
		
		HttpSession session = this.perThreadRequest.get().getSession();
		BoardTask boardTask = new BoardTask();
		
		//设备编码/板卡序号/操作类型
		String code = device.getDeviceCode() + "/" +terminalUnit.getTerminalUnitSeq() + "/1";
		
		boardTask.setBoardNum(terminalUnit.getTerminalUnitSeq());
		boardTask.setFrameNum(terminalUnit.getFrameNum()+"");
		boardTask.setBoardType(terminalUnit.getType());
		boardTask.setCode(code);
		boardTask.setName(code);
		boardTask.setCreatetime(new Date());
		boardTask.setCreator((String)session.getAttribute("loginName"));
		boardTask.setDeviceCode(device.getDeviceCode());
		boardTask.setDeviceName(device.getDeviceName());
		boardTask.setDeviceType(device.getDeviceType());
		boardTask.setStatus(1);//工单为未指派
		boardTask.setOpType("1");//添加板卡，操作类型为1，表示加载
		boardTask.setStation(device.getStationName());
		boardTask.setHostroom(device.getHostRoomName());
		boardWorkListDao.addBoardTask(boardTask);
		
		return opticalDeviceDao.addBoard(terminalUnit);
	}

	public Integer deleteBoardInfos(String boardCode){
		
		HttpSession session = this.perThreadRequest.get().getSession();

		TerminalUnit board = this.getBoardByBoardCode(boardCode);
		
		DeviceCommonInfo device = this.getDeviceCommonInfosByDeviceCode(board.getHostDeviceId());
		
		BoardTask boardTask = new BoardTask();
		
		//板卡工单编码：deviceCode/boardSeq/opType
		String code = device.getDeviceCode() + "/" +board.getTerminalUnitSeq() + "/2";
		
		boardTask.setCode(code);
		boardTask.setName(boardTask.getCode());
		boardTask.setBoardNum(board.getTerminalUnitSeq());
		boardTask.setCreatetime(new Date());
		boardTask.setCreator((String)session.getAttribute("loginName"));
		boardTask.setDeviceCode(device.getDeviceCode());
		boardTask.setDeviceName(device.getDeviceName());
		boardTask.setDeviceType(device.getDeviceType());
		boardTask.setOpType("2");//删除单板，拆除
		boardTask.setStatus(1);//未指派状态
		boardTask.setBoardType(board.getType());
		boardTask.setFrameNum(board.getFrameNum()+"");
		
		boardWorkListDao.addBoardTask(boardTask);
		
		return opticalDeviceDao.deleteBoardInfo(boardCode);
	} 
	
	@Override
	public Integer addFrame(Frame frame) {
		DeviceCommonInfo device = this.getDeviceCommonInfosByDeviceCode(frame.getHostCabinet());
		
		HttpSession session = this.perThreadRequest.get().getSession();
		
		int count = frameWorkListDao.getAllFrameWorkList(device.getDeviceType()).size();
		
		FrameTask frameTask = new FrameTask();
		
		//子框工单编码：deviceCode/frameSeq/opType
		String code = frame.getHostCabinet() + "/" + frame.getNumberInCabinet() + "/1";
		
		frameTask.setCode(code);
		frameTask.setName(frame.getCode() + "/" + count);
		frameTask.setCreatetime(new Date());
		frameTask.setCreator((String)session.getAttribute("loginName"));
		frameTask.setDeviceCode(frame.getHostCabinet());
		frameTask.setFrameNum(frame.getNumberInCabinet()+"");
		frameTask.setDeviceName(device.getDeviceName());
		frameTask.setDeviceType(device.getDeviceType());
		frameTask.setStatus(1);//未指派
		frameTask.setOpType("1");//加载机框
		
		frameWorkListDao.addFrameTask(frameTask);
		
		return opticalDeviceDao.addFrame(frame);
	}
	
	@Test
	public void testAdd(){
		//ifdt01/1/1
	}
}
