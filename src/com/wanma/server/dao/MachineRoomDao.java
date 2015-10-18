package com.wanma.server.dao;

import java.util.List;

import com.wanma.domain.HostRoom;

public interface MachineRoomDao {

	public abstract String addMachineRoom(HostRoom room);

	public abstract List<HostRoom> getMachineRoomInfosByStationName(
			String stationName);

	public abstract List<HostRoom> getMachineRoomInfos();

	public abstract HostRoom getMachineRoomNameByCode(String code);

}