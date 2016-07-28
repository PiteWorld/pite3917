package com.pite.r.prol;

import com.pite.r.util.Data;
import com.pite.r.util.Data3919Utils;
import com.pite.r.util.ProUtils;

import struct.StructClass;
import struct.StructField;

/**
 * 3915 全部数据结构 成组类型
 */
@StructClass
public class Pite3915LongFullData extends APiteData {
	@StructField(order = 0)
	public float Voltage;
	@StructField(order = 1)
	public float OhmR;
	@StructField(order = 2)
	public float LinkRes;
	@StructField(order = 3)
	public float standRes;
	@StructField(order = 4)
	public short standVolt;
	@StructField(order = 5)
	public short BatteryNum;
	@StructField(order = 6)
	public short BattNo;
	@StructField(order = 7)
	public byte Status;
	@StructField(order = 8)
	public byte Cap;
	@StructField(order = 9)
	public byte[] LocationNo = new byte[10];
	@StructField(order = 10)
	public byte[] StringNo = new byte[10];
	@StructField(order = 11)
	public byte[] Company = new byte[10];
	@StructField(order = 12)
	public byte[] bak = new byte[2];

	@Override
	public String[] getString() {
		return new String[] { Voltage + "", OhmR + "", LinkRes + "", standRes + "", standVolt + "", BatteryNum + "",
				BattNo + "", Status + "", Cap + "", LocationNo + "", StringNo + "", Company + "", bak + "" };
	}

	@Override
	public String[] get3919StringData() {
		return null;
	}
	@Override
	public Data getData() {
		Data data = new Data();
		data.setBatteryNum(BattNo + "");
		data.setBatteryV(ProUtils.getFloat3(Voltage));
		data.setBatteryR1(ProUtils.getFloat3(OhmR));
		data.setBatteryCap((int) Cap + "");
		data.setR2("0");
		data.setC2("0");
		data.setIsFinsh("0");
		return data;
	}

	@Override
	public Data3919Utils getData3919() {
		// TODO Auto-generated method stub
		return null;
	}
}
