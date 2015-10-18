package com.wanma.client.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wanma.domain.DeviceCommonInfo;
import com.wanma.domain.Frame;
import com.wanma.domain.Ifat;
import com.wanma.domain.Ifdt;
import com.wanma.domain.Iodf;
import com.wanma.domain.Obd;
import com.wanma.domain.Port;
import com.wanma.domain.TerminalUnit;

public interface OpticalDeviceServiceAsync {

	public void addIFDT(Ifdt ifdt,AsyncCallback<Integer> callback);

	public void getIfdts(AsyncCallback<List<Ifdt>> asyncCallback);

	public void addOBD(Obd obd, AsyncCallback<Integer> asyncCallback);

	public void addIODF(Iodf iodf, AsyncCallback<Integer> asyncCallback);

	public void addIFAT(Ifat ifat, AsyncCallback<Integer> asyncCallback);

	public void getBoards(String deviceName,
			AsyncCallback<List<TerminalUnit>> callback);

	public void getAllDeviceInfo(AsyncCallback<List<DeviceCommonInfo>> asyncCallback);

	public void getObds(AsyncCallback<List<Obd>> asyncCallback);

	public void getIfats(AsyncCallback<List<Ifat>> asyncCallback);

	public void getIodfs(AsyncCallback<List<Iodf>> asyncCallback);

	public void getIfdtByCode(String paramDeviceCode,
			AsyncCallback<Ifdt> asyncCallback);

	public void getIodfInfoByCode(String paramDeviceCode,
			AsyncCallback<Iodf> asyncCallback);

	public void getIfatInfoByCode(String paramDeviceCode,
			AsyncCallback<Ifat> asyncCallback);

	public void getObdInfoByCode(String paramDeviceCode,
			AsyncCallback<Obd> asyncCallback);

	public void getPortsByDeviceCode(String deviceCode,
			AsyncCallback<List<Port>> asyncCallback);

	public void getDeviceCommonInfosByType(String type,
			AsyncCallback<List<DeviceCommonInfo>> getDeviceCommonInfoCallback);

	public void addPortInfo(Port port, AsyncCallback<Integer> addPortCallback);

	public void getDeviceInfoByHostRoomName(String hostRoomName,
			AsyncCallback<List<DeviceCommonInfo>> deviceCallback);

	public void getAllPortInfo(AsyncCallback<List<Port>> portCallback);

	public void getPortInfoByDeviceName(String deviceName,
			AsyncCallback<List<Port>> portCallback);

	public void getBoardsByHostDeviceID(String hostDeviceID,
			AsyncCallback<List<TerminalUnit>> asyncCallback);

	public void getBoardsByHostDeviceID(String hostDeviceID,String status,AsyncCallback<List<TerminalUnit>> callback);
	
	public void getPortsByBoardCode(String boardCode,String status,
			AsyncCallback<List<Port>> allPortsCallback);

	public void getBoardByBoardCode(String boardCode,
			AsyncCallback<TerminalUnit> getBoardCallback);

	public void getDeviceCommonInfosByDeviceCode(String deviceCode,
			AsyncCallback<DeviceCommonInfo> deviceCommonInfoCallback);

	public void addBoard(TerminalUnit terminalUnit,
			AsyncCallback<Integer> addBoardCallback);

	public void addFrame(Frame frame, AsyncCallback<Integer> addFrameCallback);

	public void deleteBoardInfos(String boardCode,
			AsyncCallback<Integer> asyncCallback);
	
	public void upDatePosition(String deviceCode, int x, int y,AsyncCallback<Integer> callback);
}
