package com.pite.r.prol;

import com.pite.r.util.Data;
import com.pite.r.util.Data3919Utils;
import com.pite.r.util.ProUtils;

import struct.StructClass;
import struct.StructField;

/**
 * 3915 短数据结构 成组类型
 */
@StructClass
public class Pite3915ShortStringData extends APiteData {
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
	public byte LocationNo;
	@StructField(order = 6)
	public byte StringNo;
	@StructField(order = 7)
	public byte BatteryNum;
	@StructField(order = 8)
	public byte BattNo;
	@StructField(order = 9)
	public byte Status;
	@StructField(order = 10)
	public byte Cap;

	@Override
	public String[] getString() {
		return new String[] { Voltage + "", OhmR + "", LinkRes + "", standRes + "", standVolt + "", LocationNo + "",
				StringNo + "", BatteryNum + "", BattNo + "", Status + "", Cap + "" };
	}

	@Override
	public String[] get3919StringData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data getData() {
		Data data = new Data();
		data.setBatteryNum(BattNo + "");
		data.setBatteryV(ProUtils.getFloat5(Voltage));
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
