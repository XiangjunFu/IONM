package com.wanma.domain;

// Generated 2013-12-24 13:31:02 by Hibernate Tools 3.4.0.CR1

/**
 * DeviceCommonInfo generated by hbm2java
 */
public class DeviceCommonInfo implements java.io.Serializable {

	private int id;
	private String deviceCode;
	private String deviceName;
	private String deviceType;
	private String stationCode;
	private String stationName;
	private String hostRoomCode;
	private String hostRoomName;
	private Integer xposition;
	private Integer yposition;

	public DeviceCommonInfo() {
	}

	public DeviceCommonInfo(int id, String deviceCode, String deviceName,
			String deviceType) {
		this.id = id;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
	}

	public DeviceCommonInfo(int id, String deviceCode, String deviceName,
			String deviceType, String stationCode, String stationName,
			String hostRoomCode, String hostRoomName, Integer xposition,
			Integer yposition) {
		this.id = id;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.stationCode = stationCode;
		this.stationName = stationName;
		this.hostRoomCode = hostRoomCode;
		this.hostRoomName = hostRoomName;
		this.xposition = xposition;
		this.yposition = yposition;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceCode() {
		return this.deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return this.deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getHostRoomCode() {
		return this.hostRoomCode;
	}

	public void setHostRoomCode(String hostRoomCode) {
		this.hostRoomCode = hostRoomCode;
	}

	public String getHostRoomName() {
		return this.hostRoomName;
	}

	public void setHostRoomName(String hostRoomName) {
		this.hostRoomName = hostRoomName;
	}

	public Integer getXposition() {
		return this.xposition;
	}

	public void setXposition(Integer xposition) {
		this.xposition = xposition;
	}

	public Integer getYposition() {
		return this.yposition;
	}

	public void setYposition(Integer yposition) {
		this.yposition = yposition;
	}

}
