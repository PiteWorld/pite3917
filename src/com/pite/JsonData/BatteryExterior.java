package com.pite.JsonData;

public class BatteryExterior {
	private int locatorid;

	private int wid;
	
	private int waid;
	
	private int wLength;

	private int wWidth;

	private int wHeigth;

	private String regDate;

	private String qcType;

	public void setWaid(int waid) {
		this.waid = waid;
	}

	public int getWaid() {
		return this.waid;
	}

	public void setLocatorid(int locatorid) {
		this.locatorid = locatorid;
	}

	public int getLocatorid() {
		return this.locatorid;
	}

	public void setWid(int wid) {
		this.wid = wid;
	}

	public int getWid() {
		return this.wid;
	}

	public void setWLength(int wLength) {
		this.wLength = wLength;
	}

	public int getWLength() {
		return this.wLength;
	}

	public void setWWidth(int wWidth) {
		this.wWidth = wWidth;
	}

	public int getWWidth() {
		return this.wWidth;
	}

	public void setWHeigth(int wHeigth) {
		this.wHeigth = wHeigth;
	}

	public int getWHeigth() {
		return this.wHeigth;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setQcType(String qcType) {
		this.qcType = qcType;
	}

	public String getQcType() {
		return this.qcType;
	}
}
