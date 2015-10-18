package com.wanma.domain;

// Generated 2013-10-30 7:44:51 by Hibernate Tools 3.4.0.CR1

/**
 * FiberLink generated by hbm2java
 */
public class FiberLink implements java.io.Serializable {

	private int fiberLinkId;
	private String parentCode;
	private Integer bundleSeqNo;
	private Integer routeSeqNo;
	private String connectType;
	private String ameCode;
	private String aportCode;
	private String zmeCode;
	private String zportCode;
	private String importTag;

	public FiberLink() {
	}

	public FiberLink(int fiberLinkId) {
		this.fiberLinkId = fiberLinkId;
	}

	public FiberLink(int fiberLinkId, String parentCode, Integer bundleSeqNo,
			Integer routeSeqNo, String connectType, String ameCode,
			String aportCode, String zmeCode, String zportCode, String importTag) {
		this.fiberLinkId = fiberLinkId;
		this.parentCode = parentCode;
		this.bundleSeqNo = bundleSeqNo;
		this.routeSeqNo = routeSeqNo;
		this.connectType = connectType;
		this.ameCode = ameCode;
		this.aportCode = aportCode;
		this.zmeCode = zmeCode;
		this.zportCode = zportCode;
		this.importTag = importTag;
	}

	public int getFiberLinkId() {
		return this.fiberLinkId;
	}

	public void setFiberLinkId(int fiberLinkId) {
		this.fiberLinkId = fiberLinkId;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getBundleSeqNo() {
		return this.bundleSeqNo;
	}

	public void setBundleSeqNo(Integer bundleSeqNo) {
		this.bundleSeqNo = bundleSeqNo;
	}

	public Integer getRouteSeqNo() {
		return this.routeSeqNo;
	}

	public void setRouteSeqNo(Integer routeSeqNo) {
		this.routeSeqNo = routeSeqNo;
	}

	public String getConnectType() {
		return this.connectType;
	}

	public void setConnectType(String connectType) {
		this.connectType = connectType;
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

	public String getImportTag() {
		return this.importTag;
	}

	public void setImportTag(String importTag) {
		this.importTag = importTag;
	}

}