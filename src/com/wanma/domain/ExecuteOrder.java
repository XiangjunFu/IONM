package com.wanma.domain;

// Generated 2013-12-22 16:00:49 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * ExecuteOrder generated by hbm2java
 */
public class ExecuteOrder implements java.io.Serializable {

	private String code;
	private String name;
	private String jobOderCode;
	private String station;
	private String creator;
	private String address;
	private String groupName;
	private String worker;
	private Boolean isChange;
	private String changeReason;
	private Boolean isIodn;
	private Date createTime;
	private Date expectedFinishTime;
	private Date realFinishedTime;
	private Date receivedTime;
	private Short status;
	private String notes;
	private String ameType;
	private String ameCode;
	private String aportCode;
	private String zmeType;
	private String zmeCode;
	private String zportCode;
	private String acard;
	private String zcard;
	private String type;
	private String result;

	public ExecuteOrder() {
	}

	public ExecuteOrder(String code) {
		this.code = code;
	}

	public ExecuteOrder(String code, String name, String jobOderCode,
			String station, String creator, String address, String groupName,
			String worker, Boolean isChange, String changeReason,
			Boolean isIodn, Date createTime, Date expectedFinishTime,
			Date realFinishedTime, Date receivedTime, Short status,
			String notes, String ameType, String ameCode, String aportCode,
			String zmeType, String zmeCode, String zportCode, String acard,
			String zcard, String type, String result) {
		this.code = code;
		this.name = name;
		this.jobOderCode = jobOderCode;
		this.station = station;
		this.creator = creator;
		this.address = address;
		this.groupName = groupName;
		this.worker = worker;
		this.isChange = isChange;
		this.changeReason = changeReason;
		this.isIodn = isIodn;
		this.createTime = createTime;
		this.expectedFinishTime = expectedFinishTime;
		this.realFinishedTime = realFinishedTime;
		this.receivedTime = receivedTime;
		this.status = status;
		this.notes = notes;
		this.ameType = ameType;
		this.ameCode = ameCode;
		this.aportCode = aportCode;
		this.zmeType = zmeType;
		this.zmeCode = zmeCode;
		this.zportCode = zportCode;
		this.acard = acard;
		this.zcard = zcard;
		this.type = type;
		this.result = result;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobOderCode() {
		return this.jobOderCode;
	}

	public void setJobOderCode(String jobOderCode) {
		this.jobOderCode = jobOderCode;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWorker() {
		return this.worker;
	}

	public void setWorker(String worker) {
		this.worker = worker;
	}

	public Boolean getIsChange() {
		return this.isChange;
	}

	public void setIsChange(Boolean isChange) {
		this.isChange = isChange;
	}

	public String getChangeReason() {
		return this.changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public Boolean getIsIodn() {
		return this.isIodn;
	}

	public void setIsIodn(Boolean isIodn) {
		this.isIodn = isIodn;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpectedFinishTime() {
		return this.expectedFinishTime;
	}

	public void setExpectedFinishTime(Date expectedFinishTime) {
		this.expectedFinishTime = expectedFinishTime;
	}

	public Date getRealFinishedTime() {
		return this.realFinishedTime;
	}

	public void setRealFinishedTime(Date realFinishedTime) {
		this.realFinishedTime = realFinishedTime;
	}

	public Date getReceivedTime() {
		return this.receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getAmeType() {
		return this.ameType;
	}

	public void setAmeType(String ameType) {
		this.ameType = ameType;
	}

	public String getAmeCode() {
		return this.ameCode;
	}

	public void setAmeCode(String ameCode) {
		this.ameCode = ameCode;
	}

	public String getAportCode() {
		return this.aportCode;
	}

	public void setAportCode(String aportCode) {
		this.aportCode = aportCode;
	}

	public String getZmeType() {
		return this.zmeType;
	}

	public void setZmeType(String zmeType) {
		this.zmeType = zmeType;
	}

	public String getZmeCode() {
		return this.zmeCode;
	}

	public void setZmeCode(String zmeCode) {
		this.zmeCode = zmeCode;
	}

	public String getZportCode() {
		return this.zportCode;
	}

	public void setZportCode(String zportCode) {
		this.zportCode = zportCode;
	}

	public String getAcard() {
		return this.acard;
	}

	public void setAcard(String acard) {
		this.acard = acard;
	}

	public String getZcard() {
		return this.zcard;
	}

	public void setZcard(String zcard) {
		this.zcard = zcard;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
