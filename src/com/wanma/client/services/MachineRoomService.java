package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wanma.domain.HostRoom;

@RemoteServiceRelativePath("machineRoomService")
public interface MachineRoomService extends RemoteService {

	public String addMachineRoom(HostRoom room);
	
	public List<HostRoom> getMachineRoomInfosByStationName(String stationName);
	
	public List<HostRoom> getMachineRoomInfos();
	
	public List<HostRoom> getHostRoomsByStationName(String stationName);
	
}
