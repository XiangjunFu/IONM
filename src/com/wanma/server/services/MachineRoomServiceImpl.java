package com.wanma.server.services;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wanma.client.services.MachineRoomService;
import com.wanma.domain.HostRoom;
import com.wanma.server.dao.MachineRoomDao;
import com.wanma.server.dao.impl.MachineRoomDaoImpl;

public class MachineRoomServiceImpl extends RemoteServiceServlet implements MachineRoomService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2624629527423092236L;

	private MachineRoomDao machineRoomDao;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		machineRoomDao = new MachineRoomDaoImpl();
	}

	@Override
	public String addMachineRoom(HostRoom room) {
		return machineRoomDao.addMachineRoom(room);
	}

	@Override
	public List<HostRoom> getMachineRoomInfosByStationName(String stationName) {
		return machineRoomDao.getMachineRoomInfosByStationName(stationName);
	}

	@Override
	public List<HostRoom> getMachineRoomInfos() {
		return machineRoomDao.getMachineRoomInfos();
	}

	@Override
	public List<HostRoom> getHostRoomsByStationName(String stationName) {
		return machineRoomDao.getMachineRoomInfosByStationName(stationName);
	}
	
}
