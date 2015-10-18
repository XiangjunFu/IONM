package com.wanma.domain;

// Generated 2013-10-30 7:44:51 by Hibernate Tools 3.4.0.CR1

/**
 * FiberLinkAttribute generated by hbm2java
 */
public class FiberLinkAttribute implements java.io.Serializable {

	private int fiberLinkAttributeId;
	private String name;
	private String code;
	private String linkType;
	private String parentCode;
	private String ameCode;
	private String aportCode;
	private String zmeCode;
	private String zportCode;
	private String serviceStatue;
	private Integer length;
	private Boolean singleType;
	private String businessType;
	private String textRoute;
	private String importTag;

	public FiberLinkAttribute() {
	}

	public FiberLinkAttribute(int fiberLinkAttributeId) {
		this.fiberLinkAttributeId = fiberLinkAttributeId;
	}

	public FiberLinkAttribute(int fiberLinkAttributeId, String name,
			String code, String linkType, String parentCode, String ameCode,
			String aportCode, String zmeCode, String zportCode,
			String serviceStatue, Integer length, Boolean singleType,
			String businessType, String textRoute, String importTag) {
		this.fiberLinkAttributeId = fiberLinkAttributeId;
		this.name = name;
		this.code = code;
		this.linkType = linkType;
		this.parentCode = parentCode;
		this.ameCode = ameCode;
		this.aportCode = aportCode;
		this.zmeCode = zmeCode;
		this.zportCode = zportCode;
		this.serviceStatue = serviceStatue;
		this.length = length;
		this.singleType = singleType;
		this.businessType = businessType;
		this.textRoute = textRoute;
		this.importTag = importTag;
	}

	public int getFiberLinkAttributeId() {
		return this.fiberLinkAttributeId;
	}

	public void setFiberLinkAttributeId(int fiberLinkAttributeId) {
		this.fiberLinkAttributeId = fiberLinkAttributeId;
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

	public String getLinkType() {
		return this.linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
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

	public String getServiceStatue() {
		return this.serviceStatue;
	}

	public void setServiceStatue(String serviceStatue) {
		this.serviceStatue = serviceStatue;
	}

	public Integer getLength() {
		return this.length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Boolean getSingleType() {
		return this.singleType;
	}

	public void setSingleType(Boolean singleType) {
		this.singleType = singleType;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getTextRoute() {
		return this.textRoute;
	}

	public void setTextRoute(String textRoute) {
		this.textRoute = textRoute;
	}

	public String getImportTag() {
		return this.importTag;
	}

	public void setImportTag(String importTag) {
		this.importTag = importTag;
	}

}