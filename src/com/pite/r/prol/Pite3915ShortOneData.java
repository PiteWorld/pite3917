package com.pite.r.prol;

import java.util.Arrays;

import com.google.gson.Gson;
import com.pite.r.util.Data;
import com.pite.r.util.Data3919Utils;
import com.pite.r.util.ProUtils;

import android.net.LinkProperties;
import struct.StructClass;
import struct.StructField;

/**
 * 3915 短数据结构 单节数据
 */
@StructClass
public class Pite3915ShortOneData extends APiteData {
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
	public byte BattNo;
	@StructField(order = 6)
	public byte Status;
	@StructField(order = 7)
	public byte Cap;
	@StructField(order = 8)
	public byte[] bak = new byte[3];

	@Override
	public String[] getString() {
		return new String[] { Voltage + "", OhmR + "", LinkRes + "", standRes + "", standVolt + "", BattNo + "",
				Status + "", Cap + "", bak + "" };
	}

	@Override
	public String toString() {
		return "Pite3915ShortOneData [Voltage=" + Voltage + ", OhmR=" + OhmR + ", LinkRes=" + LinkRes + ", standRes="
				+ standRes + ", standVolt=" + standVolt + ", BattNo=" + BattNo + ", Status=" + Status + ", Cap=" + Cap
				+ ", bak=" + Arrays.toString(bak) + "]";
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
