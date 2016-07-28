package com.pite.r.util;

import java.io.Serializable;

public class Data3919Utils implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String batteryNum; // µÁ≥ÿ∫≈
	private String batteryV; // µÁ—π
	private String batteryR2; // R2
	private String batteryR; // R1
	private String batteryC2; // C2
	private String batteryCap; // Cap
	private String statuss ;
	
	
	public String getStatuss() {
		return statuss;
	}
	public void setStatuss(String statuss) {
		this.statuss = statuss;
	}
	public String getBatteryR2() {
		return batteryR2;
	}
	public void setBatteryR2(String batteryR2) {
		this.batteryR2 = batteryR2;
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
	public String getBatteryR() {
		return batteryR;
	}
	public void setBatteryR(String batteryR) {
		this.batteryR = batteryR;
	}
	public String getBatteryC2() {
		return batteryC2;
	}
	public void setBatteryC2(String batteryC2) {
		this.batteryC2 = batteryC2;
	}
	public String getBatteryCap() {
		return batteryCap;
	}
	public void setBatteryCap(String batteryCap) {
		this.batteryCap = batteryCap;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
