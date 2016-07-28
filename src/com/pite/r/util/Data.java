package com.pite.r.util;

import java.io.Serializable;

public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batteryNum; // 电池号
	private String batteryV; // 电压
	private String batteryR1; // R1
	private String standVolt; // 标准电压
	private String standR; // 标准内阻
	private String batteryCap; // Cap
	private String statuss; // 是否合格
	private String R2;
	private String C2;
	private String isFinsh;

	public String getR2() {
		return R2;
	}

	public void setR2(String r2) {
		R2 = r2;
	}

	public String getC2() {
		return C2;
	}

	public void setC2(String c2) {
		C2 = c2;
	}

	public String getIsFinsh() {
		return isFinsh;
	}

	public void setIsFinsh(String isFinsh) {
		this.isFinsh = isFinsh;
	}

	public String getStatuss() {
		return statuss;
	}

	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStandVolt() {
		return standVolt;
	}

	public void setStandVolt(String standVolt) {
		this.standVolt = standVolt;
	}

	public String getStandR() {
		return standR;
	}

	public void setStandR(String standR) {
		this.standR = standR;
	}

	public String getBatteryNum() {
		return batteryNum;
	}

	public void setBatteryNum(String batteryNum) {
		this.batteryNum = batteryNum;
	}

	public String getBatteryV() {
		return batteryV;
	}

	public void setBatteryV(String batteryV) {
		this.batteryV = batteryV;
	}

	public String getBatteryR1() {
		return batteryR1;
	}

	public void setBatteryR1(String batteryR1) {
		this.batteryR1 = batteryR1;
	}

	public String getBatteryCap() {
		return batteryCap;
	}

	public void setBatteryCap(String batteryCap) {
		this.batteryCap = batteryCap;
	}

	@Override
	public String toString() {
		return "Data [batteryNum=" + batteryNum + ", batteryV=" + batteryV + ", batteryR1=" + batteryR1 + ", standVolt="
				+ standVolt + ", standRes=" + standR + ", batteryCap=" + batteryCap + "]";
	}

}
