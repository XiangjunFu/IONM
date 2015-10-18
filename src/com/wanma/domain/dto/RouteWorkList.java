package com.wanma.domain.dto;

import java.io.Serializable;
import java.util.Date;

public class RouteWorkList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5575445398859093951L;
	private String code;
	private String name;
	
	private String aType;
	private String aCode;
	private String aPortCode;
	private String aCard;
	
	private String zType;
	private String zCode;
	private String zPortCode;
	private String zCard;
	
	private String type;
	private String status;
	private String station;
	private String address;
	private String createTime;
	private String deadline;
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getaType() {
		return aType;
	}
	public void setaType(String aType) {
		this.aType = aType;
	}
	public String getaCode() {
		return aCode;
	}
	public void setaCode(String aCode) {
		this.aCode = aCode;
	}
	public String getaPortCode() {
		return aPortCode;
	}
	public void setaPortCode(String aPortCode) {
		this.aPortCode = aPortCode;
	}
	public String getzType() {
		return zType;
	}
	public void setzType(String zType) {
		this.zType = zType;
	}
	public String getzCode() {
		return zCode;
	}
	public void setzCode(String zCode) {
		this.zCode = zCode;
	}
	public String getzPortCode() {
		return zPortCode;
	}
	public void setzPortCode(String zPortCode) {
		this.zPortCode = zPortCode;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getaCard() {
		return aCard;
	}
	public void setaCard(String aCard) {
		this.aCard = aCard;
	}
	public String getzCard() {
		return zCard;
	}
	public void setzCard(String zCard) {
		this.zCard = zCard;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
