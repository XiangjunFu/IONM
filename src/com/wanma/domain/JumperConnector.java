package com.wanma.domain;

// Generated 2013-12-18 15:06:34 by Hibernate Tools 3.4.0.CR1

/**
 * JumperConnector generated by hbm2java
 */
public class JumperConnector implements java.io.Serializable {

	private int jumperConnectorId;
	private String ameType;
	private String ameCode;
	private String aportCode;
	private String zmeType;
	private String zmeCode;
	private String zportCode;
	private String importTag;
	private String acard;
	private String zcard;
	private String code;

	public JumperConnector() {
	}

	public JumperConnector(int jumperConnectorId, String ameCode, String zmeCode) {
		this.jumperConnectorId = jumperConnectorId;
		this.ameCode = ameCode;
		this.zmeCode = zmeCode;
	}

	public JumperConnector(int jumperConnectorId, String ameType,
			String ameCode, String aportCode, String zmeType, String zmeCode,
			String zportCode, String importTag, String acard, String zcard,
			String code) {
		this.jumperConnectorId = jumperConnectorId;
		this.ameType = ameType;
		this.ameCode = ameCode;
		this.aportCode = aportCode;
		this.zmeType = zmeType;
		this.zmeCode = zmeCode;
		this.zportCode = zportCode;
		this.importTag = importTag;
		this.acard = acard;
		this.zcard = zcard;
		this.code = code;
	}

	public int getJumperConnectorId() {
		return this.jumperConnectorId;
	}

	public void setJumperConnectorId(int jumperConnectorId) {
		this.jumperConnectorId = jumperConnectorId;
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

	public String getImportTag() {
		return this.importTag;
	}

	public void setImportTag(String importTag) {
		this.importTag = importTag;
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

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
