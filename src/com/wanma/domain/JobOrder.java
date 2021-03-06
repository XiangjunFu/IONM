package com.wanma.domain;

// Generated 2013-12-18 21:06:54 by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * JobOrder generated by hbm2java
 */
public class JobOrder implements java.io.Serializable {

	private int jobOrderId;
	private String name;
	private String code;
	private String businessType;
	private Integer businessNo;
	private String customer;
	private Integer customerOrder;
	private String businessInfor;
	private String fiberLinkType;
	private String fiberLinkCode;
	private String ameType;
	private String ameCode;
	private String aportCode;
	private String zmeType;
	private String zmeCode;
	private String zportCode;
	private String creator;
	private byte workOrderType;
	private Date createTime;
	private Date deadline;
	private Date dispatchedTime;
	private Short status;
	private String acard;
	private String zcard;

	public JobOrder() {
	}

	public JobOrder(int jobOrderId, String code, byte workOrderType) {
		this.jobOrderId = jobOrderId;
		this.code = code;
		this.workOrderType = workOrderType;
	}

	public JobOrder(int jobOrderId, String name, String code,
			String businessType, Integer businessNo, String customer,
			Integer customerOrder, String businessInfor, String fiberLinkType,
			String fiberLinkCode, String ameType, String ameCode,
			String aportCode, String zmeType, String zmeCode, String zportCode,
			String creator, byte workOrderType, Date createTime, Date deadline,
			Date dispatchedTime, Short status, String acard, String zcard) {
		this.jobOrderId = jobOrderId;
		this.name = name;
		this.code = code;
		this.businessType = businessType;
		this.businessNo = businessNo;
		this.customer = customer;
		this.customerOrder = customerOrder;
		this.businessInfor = businessInfor;
		this.fiberLinkType = fiberLinkType;
		this.fiberLinkCode = fiberLinkCode;
		this.ameType = ameType;
		this.ameCode = ameCode;
		this.aportCode = aportCode;
		this.zmeType = zmeType;
		this.zmeCode = zmeCode;
		this.zportCode = zportCode;
		this.creator = creator;
		this.workOrderType = workOrderType;
		this.createTime = createTime;
		this.deadline = deadline;
		this.dispatchedTime = dispatchedTime;
		this.status = status;
		this.acard = acard;
		this.zcard = zcard;
	}

	public int getJobOrderId() {
		return this.jobOrderId;
	}

	public void setJobOrderId(int jobOrderId) {
		this.jobOrderId = jobOrderId;
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

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Integer getBusinessNo() {
		return this.businessNo;
	}

	public void setBusinessNo(Integer businessNo) {
		this.businessNo = businessNo;
	}

	public String getCustomer() {
		return this.customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getCustomerOrder() {
		return this.customerOrder;
	}

	public void setCustomerOrder(Integer customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getBusinessInfor() {
		return this.businessInfor;
	}

	public void setBusinessInfor(String businessInfor) {
		this.businessInfor = businessInfor;
	}

	public String getFiberLinkType() {
		return this.fiberLinkType;
	}

	public void setFiberLinkType(String fiberLinkType) {
		this.fiberLinkType = fiberLinkType;
	}

	public String getFiberLinkCode() {
		return this.fiberLinkCode;
	}

	public void setFiberLinkCode(String fiberLinkCode) {
		this.fiberLinkCode = fiberLinkCode;
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

	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public byte getWorkOrderType() {
		return this.workOrderType;
	}

	public void setWorkOrderType(byte workOrderType) {
		this.workOrderType = workOrderType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getDeadline() {
		return this.deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getDispatchedTime() {
		return this.dispatchedTime;
	}

	public void setDispatchedTime(Date dispatchedTime) {
		this.dispatchedTime = dispatchedTime;
	}

	public Short getStatus() {
		return this.status;
	}

	public void setStatus(Short status) {
		this.status = status;
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

}
