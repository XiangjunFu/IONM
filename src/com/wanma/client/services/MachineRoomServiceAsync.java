package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.HostRoom;

public interface MachineRoomServiceAsync {

	public void addMachineRoom(HostRoom room,AsyncCallback<String> callback);

	public void getMachineRoomInfosByStationName(String station,
			AsyncCallback<List<HostRoom>> asyncCallback);

	public void getMachineRoomInfos(AsyncCallback<List<HostRoom>> asyncCallback);

	public void getHostRoomsByStationName(String stationName,
			AsyncCallback<List<HostRoom>> hostRoomCallback);
}
