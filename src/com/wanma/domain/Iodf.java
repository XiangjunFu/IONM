package com.wanma.domain;

// Generated 2013-12-5 21:53:08 by Hibernate Tools 3.4.0.CR1

/**
 * Iodf generated by hbm2java
 */
public class Iodf implements java.io.Serializable {

	private int iodfId;
	private String name;
	private String code;
	private String assemblyType;
	private String assemblyNumber;
	private String structStatus;
	private String phyStatus;
	private String stationCode;
	private String hostRoomCode;
	private String manufacturer;
	private String type;
	private String oid;
	private String address;
	private String dutyPerson;
	private Integer xpositon;
	private Integer yposition;
	private Double longtitude;
	private Double latitude;
	private int odfId;
	private String importTag;
	private String configurationType;
	private Boolean needSnmp;
	private Boolean needTimeSyn;
	private String ip;
	private String subnetMask;
	private String gateway;
	private Integer temperature;
	private String targetHost;
	private String snmpVersion;
	private String hostTrapPort;
	private Boolean trapEnabled;
	private String trapHostDomain;
	private String cabinetType;

	public Iodf() {
	}

	public Iodf(int iodfId, String code, int odfId) {
		this.iodfId = iodfId;
		this.code = code;
		this.odfId = odfId;
	}

	public Iodf(int iodfId, String name, String code, String assemblyType,
			String assemblyNumber, String structStatus, String phyStatus,
			String stationCode, String hostRoomCode, String manufacturer,
			String type, String oid, String address, String dutyPerson,
			Integer xpositon, Integer yposition, Double longtitude,
			Double latitude, int odfId, String importTag,
			String configurationType, Boolean needSnmp, Boolean needTimeSyn,
			String ip, String subnetMask, String gateway, Integer temperature,
			String targetHost, String snmpVersion, String hostTrapPort,
			Boolean trapEnabled, String trapHostDomain, String cabinetType) {
		this.iodfId = iodfId;
		this.name = name;
		this.code = code;
		this.assemblyType = assemblyType;
		this.assemblyNumber = assemblyNumber;
		this.structStatus = structStatus;
		this.phyStatus = phyStatus;
		this.stationCode = stationCode;
		this.hostRoomCode = hostRoomCode;
		this.manufacturer = manufacturer;
		this.type = type;
		this.oid = oid;
		this.address = address;
		this.dutyPerson = dutyPerson;
		this.xpositon = xpositon;
		this.yposition = yposition;
		this.longtitude = longtitude;
		this.latitude = latitude;
		this.odfId = odfId;
		this.importTag = importTag;
		this.configurationType = configurationType;
		this.needSnmp = needSnmp;
		this.needTimeSyn = needTimeSyn;
		this.ip = ip;
		this.subnetMask = subnetMask;
		this.gateway = gateway;
		this.temperature = temperature;
		this.targetHost = targetHost;
		this.snmpVersion = snmpVersion;
		this.hostTrapPort = hostTrapPort;
		this.trapEnabled = trapEnabled;
		this.trapHostDomain = trapHostDomain;
		this.cabinetType = cabinetType;
	}

	public int getIodfId() {
		return this.iodfId;
	}

	public void setIodfId(int iodfId) {
		this.iodfId = iodfId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAssemblyType() {
		return this.assemblyType;
	}

	public void setAssemblyType(String assemblyType) {
		this.assemblyType = assemblyType;
	}

	public String getAssemblyNumber() {
		return this.assemblyNumber;
	}

	public void setAssemblyNumber(String assemblyNumber) {
		this.assemblyNumber = assemblyNumber;
	}

	public String getStructStatus() {
		return this.structStatus;
	}

	public void setStructStatus(String structStatus) {
		this.structStatus = structStatus;
	}

	public String getPhyStatus() {
		return this.phyStatus;
	}

	public void setPhyStatus(String phyStatus) {
		this.phyStatus = phyStatus;
	}

	public String getStationCode() {
		return this.stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getHostRoomCode() {
		return this.hostRoomCode;
	}

	public void setHostRoomCode(String hostRoomCode) {
		this.hostRoomCode = hostRoomCode;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOid() {
		return this.oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDutyPerson() {
		return this.dutyPerson;
	}

	public void setDutyPerson(String dutyPerson) {
		this.dutyPerson = dutyPerson;
	}

	public Integer getXpositon() {
		return this.xpositon;
	}

	public void setXpositon(Integer xpositon) {
		this.xpositon = xpositon;
	}

	public Integer getYposition() {
		return this.yposition;
	}

	public void setYposition(Integer yposition) {
		this.yposition = yposition;
	}

	public Double getLongtitude() {
		return this.longtitude;
	}

	public void setLongtitude(Double longtitude) {
		this.longtitude = longtitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public int getOdfId() {
		return this.odfId;
	}

	public void setOdfId(int odfId) {
		this.odfId = odfId;
	}

	public String getImportTag() {
		return this.importTag;
	}

	public void setImportTag(String importTag) {
		this.importTag = importTag;
	}

	public String getConfigurationType() {
		return this.configurationType;
	}

	public void setConfigurationType(String configurationType) {
		this.configurationType = configurationType;
	}

	public Boolean getNeedSnmp() {
		return this.needSnmp;
	}

	public void setNeedSnmp(Boolean needSnmp) {
		this.needSnmp = needSnmp;
	}

	public Boolean getNeedTimeSyn() {
		return this.needTimeSyn;
	}

	public void setNeedTimeSyn(Boolean needTimeSyn) {
		this.needTimeSyn = needTimeSyn;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSubnetMask() {
		return this.subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
	}

	public String getGateway() {
		return this.gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public Integer getTemperature() {
		return this.temperature;
	}

	public void setTemperature(Integer temperature) {
		this.temperature = temperature;
	}

	public String getTargetHost() {
		return this.targetHost;
	}

	public void setTargetHost(String targetHost) {
		this.targetHost = targetHost;
	}

	public String getSnmpVersion() {
		return this.snmpVersion;
	}

	public void setSnmpVersion(String snmpVersion) {
		this.snmpVersion = snmpVersion;
	}

	public String getHostTrapPort() {
		return this.hostTrapPort;
	}

	public void setHostTrapPort(String hostTrapPort) {
		this.hostTrapPort = hostTrapPort;
	}

	public Boolean getTrapEnabled() {
		return this.trapEnabled;
	}

	public void setTrapEnabled(Boolean trapEnabled) {
		this.trapEnabled = trapEnabled;
	}

	public String getTrapHostDomain() {
		return this.trapHostDomain;
	}

	public void setTrapHostDomain(String trapHostDomain) {
		this.trapHostDomain = trapHostDomain;
	}

	public String getCabinetType() {
		return this.cabinetType;
	}

	public void setCabinetType(String cabinetType) {
		this.cabinetType = cabinetType;
	}

}