package com.pite.JsonData;

public class DataInfo {
	private String serial_number;

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getInternal() {
		return internal;
	}

	public void setInternal(String internal) {
		this.internal = internal;
	}

	public String getEligible() {
		return eligible;
	}

	public void setEligible(String eligible) {
		this.eligible = eligible;
	}

	public String getNFCnumber() {
		return NFCnumber;
	}

	public void setNFCnumber(String nFCnumber) {
		NFCnumber = nFCnumber;
	}

	public String getBatterynumber() {
		return Batterynumber;
	}

	public void setBatterynumber(String batterynumber) {
		Batterynumber = batterynumber;
	}

	private String voltage;
	private String internal;
	private String eligible;
	private String NFCnumber;
	private String Batterynumber;
}
