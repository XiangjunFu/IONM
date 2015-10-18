package com.wanma.domain;

// Generated 2013-12-21 16:24:00 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * FrameTask generated by hbm2java
 */
public class FrameTask implements java.io.Serializable {

	private int frameTaskId;
	private String name;
	private String code;
	private String groupId;
	private String workerId;
	private String deviceType;
	private String deviceCode;
	private String deviceName;
	private String frameNum;
	private String opType;
	private String comment;
	private String creator;
	private Date createtime;
	private Date deadline;
	private Date finishtime;
	private Integer status;
	private String result;
	private String station;
	private String hostroom;

	public FrameTask() {
	}

	public FrameTask(int frameTaskId) {
		this.frameTaskId = frameTaskId;
	}

	public FrameTask(int frameTaskId, String name, String code, String groupId,
			String workerId, String deviceType, String deviceCode,
			String deviceName, String frameNum, String opType, String comment,
			String creator, Date createtime, Date deadline, Date finishtime,
			Integer status, String result, String station, String hostroom) {
		this.frameTaskId = frameTaskId;
		this.name = name;
		this.code = code;
		this.groupId = groupId;
		this.workerId = workerId;
		this.deviceType = deviceType;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.frameNum = frameNum;
		this.opType = opType;
		this.comment = comment;
		this.creator = creator;
		this.createtime = createtime;
		this.deadline = deadline;
		this.finishtime = finishtime;
		this.status = status;
		this.result = result;
		this.station = station;
		this.hostroom = hostroom;
	}

	public int getFrameTaskId() {
		return this.frameTaskId;
	}

	public void setFrameTaskId(int frameTaskId) {
		this.frameTaskId = frameTaskId;
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

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getWorkerId() {
		return this.workerId;
	}

	public void setWorkerId(String workerId) {
		this.workerId = workerId;
	}

	public String getDeviceType() {
		return this.deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
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

	public String getFrameNum() {
		return this.frameNum;
	}

	public void setFrameNum(String frameNum) {
		this.frameNum = frameNum;
	}

	public String getOpType() {
		return this.opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getFinishtime() {
		return this.finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStation() {
		return this.station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getHostroom() {
		return this.hostroom;
	}

	public void setHostroom(String hostroom) {
		this.hostroom = hostroom;
	}

}
