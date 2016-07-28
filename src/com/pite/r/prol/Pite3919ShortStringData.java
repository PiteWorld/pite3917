package com.pite.r.prol;

import com.pite.r.util.Data;
import com.pite.r.util.Data3919Utils;
import com.pite.r.util.ProUtils;

import struct.StructClass;
import struct.StructField;

/**
 * 3919 短数据结构 成组类型
 */
@StructClass
public class Pite3919ShortStringData extends APiteData {
	@StructField(order = 0)
	public float Voltage;
	@StructField(order = 1)
	public float OhmR;
	@StructField(order = 2)
	public float LinkRes;
	@StructField(order = 3)
	public float standRes;
	@StructField(order = 4)
	public float ExternalCurrent;
	@StructField(order = 5)
	public float R2;
	@StructField(order = 6)
	public float C2;
	@StructField(order = 7)
	public short standVolt;
	@StructField(order = 8)
	public byte LocationNo;
	@StructField(order = 9)
	public byte StringNo;
	@StructField(order = 10)
	public byte BatteryNum;
	@StructField(order = 11)
	public byte BattNo;
	@StructField(order = 12)
	public byte Status;
	@StructField(order = 13)
	public byte Cap;

	@Override
	public String[] getString() {
		return new String[] { Voltage + "", OhmR + "", LinkRes + "", standRes + "", ExternalCurrent + "", R2 + "",
				C2 + "", standVolt + "", LocationNo + "", StringNo + "", BatteryNum + "", BattNo + "", Status + "",
				Cap + "" };
	}

	@Override
	public String toString() {
		return "Pite3919ShortStringData [Voltage=" + Voltage + ", OhmR=" + OhmR + ", LinkRes=" + LinkRes + ", standRes="
				+ standRes + ", ExternalCurrent=" + ExternalCurrent + ", R2=" + R2 + ", C2=" + C2 + ", standVolt="
				+ standVolt + ", LocationNo=" + LocationNo + ", StringNo=" + StringNo + ", BatteryNum=" + BatteryNum
				+ ", BattNo=" + BattNo + ", Status=" + Status + ", Cap=" + Cap + "]";
	}

	@Override
	public String[] get3919StringData() {
		return new String[] { BattNo + "", Voltage + "", OhmR + "", R2 + "", C2 + "", Cap + "" };
	}

	@Override
	public Data getData() {

		return null;
	}

	@Override
	public Data3919Utils getData3919() {
		Data3919Utils utils = new Data3919Utils();
		utils.setBatteryNum((int) BattNo + "");
		utils.setBatteryV(Voltage < 10 ? ProUtils.getFloat4(Voltage) : ProUtils.getFloat3(Voltage));
		utils.setBatteryR(OhmR < 10 ? ProUtils.getFloat3(OhmR) : ProUtils.getFloat1s(OhmR));
		utils.setBatteryR2(R2 < 10 ? ProUtils.getFloat3(R2 * 1000) : ProUtils.getFloat1s(R2 * 1000));
		utils.setBatteryC2(C2 < 10 ? ProUtils.getFloat3(C2) : ProUtils.getFloat1s(C2));
		utils.setBatteryCap((int) Cap + "");
		utils.setStatuss(Status + "");
		return utils;
	}

}
