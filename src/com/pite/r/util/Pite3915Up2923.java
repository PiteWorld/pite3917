package com.pite.r.util;

import java.io.Serializable;
import java.util.List;

public class Pite3915Up2923 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean sucess; // 是否成功
	private String station; // 站
	private String group;// 组
	private String flags;
	private String testTime;
	private String locatorid; // 厂家编号
	private String bttyType; // 电池类型
	private String orgid; // 电池厂家
	private String apptype;

	private List<Data> data;

	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	public String getLocatorid() {
		return locatorid;
	}

	public void setLocatorid(String locatorid) {
		this.locatorid = locatorid;
	}

	public String getBttyType() {
		return bttyType;
	}

	public void setBttyType(String bttyType) {
		this.bttyType = bttyType;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getTestTime() {
		return testTime;
	}

	public void setTestTime(String testTime) {
		this.testTime = testTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isSucess() {
		return sucess;
	}

	public void setSucess(boolean sucess) {
		this.sucess = sucess;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public List<Data> getData() {
		return data;
	}

	public void setData(List<Data> data) {
		this.data = data;
	}

}
