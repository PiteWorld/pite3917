package com.pite.r.prol;

import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.sax.SAXTransformerFactory;

import com.pite.r.tool.TestRCMD;

import android.util.Log;
import struct.StructException;

/**
 * 数据解析 判断
 */
public class Pite3915SwitchData {
	private Map<Integer, List<APiteData>> map = null;

	/**
	 * 数据选择
	 */
	public Map<Integer, List<APiteData>> setSwitch(byte[] bt, Pite3915Header header, int dataLen) {
		Log.e("5", "解析收到的数据： " +header.Equipment+ " " + header.DataType+ " "  +header.Version+ " "+TestRCMD.bytesToHexString(bt));
		map = new HashMap<>();
		if (header.Equipment == 3915) {
			if (header.DataType == 2) { // 单节
				if (header.Version == 0) { // 短数据
					byte[] fullData = new byte[bt.length - ((dataLen / 36) - 1) * 12];
					for (int j = 0; j < dataLen / 36; j++) {
						System.arraycopy(bt, j * 36, fullData, j * 24, 24);
					}
					int[] start = new int[9];
					start[0] = 0;
					for (int i = 0; i < dataLen / 36; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 5) {
								start[k] = start[k - 1] + 4;
							}
							if (k == 5) {
								start[k] = start[k - 1] + 2;
							}
							if (k > 5 && k < 9) {
								start[k] = start[k - 1] + 1;
							}
							if (k == 9) {
								start[k] = start[k - 1] + 3;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[24];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 2);
						System.arraycopy(fullData, start[5], oneData, 18, 1);
						System.arraycopy(fullData, start[6], oneData, 19, 1);
						System.arraycopy(fullData, start[7], oneData, 20, 1);
						System.arraycopy(fullData, start[8], oneData, 21, 3);
						start[0] += 24;
						Pite3915ShortOneData shortOneData = new Pite3915ShortOneData();
						MyStruct.uppack(shortOneData, oneData, ByteOrder.LITTLE_ENDIAN);
						Log.e("4", "3915短数据结构单节：" + shortOneData.toString());
						list.add(shortOneData);
					}
					return map;
				}
				if (header.Version == 1) {
					byte[] fullData = new byte[bt.length - ((dataLen / 36) - 1) * 12];
					for (int j = 0; j < dataLen / 36; j++) {
						System.arraycopy(bt, j * 36, fullData, j * 24, 24);
					}
					int[] start = new int[9];
					start[0] = 0;
					for (int i = 0; i < dataLen / 36; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 5) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 5 && k < 7) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 7 && k < 9) {
								start[k] = start[k - 1] + 1;
							}
							if (k == 9) {
								start[k] = start[k - 1] + 2;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[24];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 2);
						System.arraycopy(fullData, start[5], oneData, 18, 2);
						System.arraycopy(fullData, start[6], oneData, 20, 1);
						System.arraycopy(fullData, start[7], oneData, 21, 1);
						System.arraycopy(fullData, start[8], oneData, 22, 2);
						start[0] += 24;
						Pite3915LongOneData longOneData = new Pite3915LongOneData();
						MyStruct.uppack(longOneData, oneData, ByteOrder.LITTLE_ENDIAN);
						Log.e("4", "3915长数据结构单节：" + longOneData.toString());
						list.add(longOneData);
					}
					return map;
				}
			} else { // 成组
				if (header.Version == 0) {
					byte[] fullData = new byte[bt.length - ((dataLen / 36) - 1) * 12];
					for (int j = 0; j < dataLen / 36; j++) {
						System.arraycopy(bt, j * 36, fullData, j * 24, 24);
					}
					int[] start = new int[11];
					start[0] = 0;
					for (int i = 0; i < dataLen / 36; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 5) {
								start[k] = start[k - 1] + 4;
							}
							if (k == 5) {
								start[k] = start[k - 1] + 2;
							}
							if (k > 5) {
								start[k] = start[k - 1] + 1;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[24];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 2);
						System.arraycopy(fullData, start[5], oneData, 18, 1);
						System.arraycopy(fullData, start[6], oneData, 19, 1);
						System.arraycopy(fullData, start[7], oneData, 20, 1);
						System.arraycopy(fullData, start[8], oneData, 21, 1);
						System.arraycopy(fullData, start[9], oneData, 22, 1);
						System.arraycopy(fullData, start[10], oneData, 23, 1);
						start[0] += 24;
						Pite3915ShortStringData pite3915ShortStringData = new Pite3915ShortStringData();
						MyStruct.uppack(pite3915ShortStringData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(pite3915ShortStringData);
					}
					return map;
				}
				if (header.Version == 1) { // 成组长数据 结构
					byte[] fullData = new byte[bt.length - ((dataLen / 56) - 1) * 12];
					for (int j = 0; j < dataLen / 56; j++) {
						System.arraycopy(bt, j * 56, fullData, j * 44, 44);
					}
					int[] start = new int[11];
					start[0] = 0;
					for (int i = 0; i < dataLen / 56; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 5) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 5 && k < 8) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 8 && k < 10) {
								start[k] = start[k - 1] + 1;
							}
							if (k >= 10) {
								start[k] = start[k - 1] + 10;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[44];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 2);
						System.arraycopy(fullData, start[5], oneData, 18, 2);
						System.arraycopy(fullData, start[6], oneData, 20, 2);
						System.arraycopy(fullData, start[7], oneData, 22, 1);
						System.arraycopy(fullData, start[8], oneData, 23, 1);
						System.arraycopy(fullData, start[9], oneData, 24, 10);
						System.arraycopy(fullData, start[10], oneData, 34, 10);
						// Log.e("5", "start[0] " + start[0] + " start[1] " +
						// start[1]);
						// Log.e("5", "start[2] " + start[2] + " start[3] " +
						// start[3]);
						// Log.e("5", "start[4] " + start[4] + " start[5] " +
						// start[5]);
						// Log.e("5", "start[6] " + start[6] + " start[7] " +
						// start[7]);
						// Log.e("5", "start[8] " + start[8] + " start[9] " +
						// start[9]);
						// Log.e("5", "start[10] " + start[10]);
						start[0] += 44;
						Pite3915LongStringData longStringData = new Pite3915LongStringData();
						MyStruct.uppack(longStringData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(longStringData);
						Log.e("2", "3915长数据结构成组： " + longStringData.toString());
					}
					return map;
				}
				if (header.Version == 5) {
					byte[] fullData = new byte[bt.length - ((dataLen / 68) - 1) * 12];
					for (int j = 0; j < dataLen / 68; j++) {
						System.arraycopy(bt, j * 68, fullData, j * 56, 56);
					}
					int[] start = new int[13];
					start[0] = 0;
					for (int i = 0; i < dataLen / 68; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 5) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 5 && k < 8) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 8 && k < 10) {
								start[k] = start[k - 1] + 1;
							}
							if (k >= 10 && k < 13) {
								start[k] = start[k - 1] + 10;
							}
							if (k == 13) {
								start[k] = start[k - 1] + 2;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[56];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 2);
						System.arraycopy(fullData, start[5], oneData, 18, 2);
						System.arraycopy(fullData, start[6], oneData, 20, 2);
						System.arraycopy(fullData, start[7], oneData, 22, 1);
						System.arraycopy(fullData, start[8], oneData, 23, 1);
						System.arraycopy(fullData, start[9], oneData, 24, 10);
						System.arraycopy(fullData, start[10], oneData, 34, 10);
						System.arraycopy(fullData, start[11], oneData, 44, 10);
						System.arraycopy(fullData, start[12], oneData, 54, 2);
						start[0] += 56;
						Pite3915LongFullData longFullData = new Pite3915LongFullData();
						MyStruct.uppack(longFullData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(longFullData);
					}
				}
				return map;
			}
		} else {
			if (header.DataType == 2) { // 3919 3917 单节
				if (header.Version == 2) {
					byte[] fullData = new byte[bt.length - ((dataLen / 48) - 1) * 12];
					for (int j = 0; j < dataLen / 48; j++) {
						System.arraycopy(bt, j * 48, fullData, j * 36, 36);
					}
					int[] start = new int[12];
					start[0] = 0;
					for (int i = 0; i < dataLen / 48; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 8) {
								start[k] = start[k - 1] + 4;
							}
							if (k == 8) {
								start[k] = start[k - 1] + 2;
							}
							if (k > 8 && k < 12) {
								start[k] = start[k - 1] + 1;
							}
							if (k == 12) {
								start[k] = start[k - 1] + 3;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[36];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 4);
						System.arraycopy(fullData, start[5], oneData, 20, 4);
						System.arraycopy(fullData, start[6], oneData, 24, 4);
						System.arraycopy(fullData, start[7], oneData, 28, 2);
						System.arraycopy(fullData, start[8], oneData, 30, 1);
						System.arraycopy(fullData, start[9], oneData, 31, 1);
						System.arraycopy(fullData, start[10], oneData, 32, 1);
						System.arraycopy(fullData, start[11], oneData, 33, 3);
						start[0] += 36;
						Pite3919ShortOneData pite3919ShortOneData = new Pite3919ShortOneData();
						MyStruct.uppack(pite3919ShortOneData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(pite3919ShortOneData);
					}
					return map;
				}
				if (header.Version == 3) {
					byte[] fullData = new byte[bt.length - ((dataLen / 48) - 1) * 12];
					for (int j = 0; j < dataLen / 48; j++) {
						System.arraycopy(bt, j * 48, fullData, j * 36, 36);
					}
					int[] start = new int[12];
					start[0] = 0;
					for (int i = 0; i < dataLen / 48; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 8) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 8 && k < 10) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 10 && k < 12) {
								start[k] = start[k - 1] + 1;
							}
							if (k == 12) {
								start[k] = start[k - 1] + 2;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[36];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 4);
						System.arraycopy(fullData, start[5], oneData, 20, 4);
						System.arraycopy(fullData, start[6], oneData, 24, 4);
						System.arraycopy(fullData, start[7], oneData, 28, 2);
						System.arraycopy(fullData, start[8], oneData, 30, 2);
						System.arraycopy(fullData, start[9], oneData, 32, 1);
						System.arraycopy(fullData, start[10], oneData, 33, 1);
						System.arraycopy(fullData, start[11], oneData, 34, 2);
						start[0] += 36;
						Pite3919LongOneData pite3919LongOneData = new Pite3919LongOneData();
						MyStruct.uppack(pite3919LongOneData, oneData, ByteOrder.LITTLE_ENDIAN);
						Log.e("2", "收到的数据：" + header.Equipment+"     "+pite3919LongOneData.toString());
						list.add(pite3919LongOneData);
					}
					return map;
				}
			} else {
				if (header.Version == 2) { // 成组
					byte[] fullData = new byte[bt.length - ((dataLen / 48) - 1) * 12];
					for (int j = 0; j < dataLen / 48; j++) {
						System.arraycopy(bt, j * 48, fullData, j * 36, 36);
					}
					int[] start = new int[14];
					start[0] = 0;
					for (int i = 0; i < dataLen / 48; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 8) {
								start[k] = start[k - 1] + 4;
							}
							if (k == 8) {
								start[k] = start[k - 1] + 2;
							}
							if (k > 8) {
								start[k] = start[k - 1] + 1;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[36];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 4);
						System.arraycopy(fullData, start[5], oneData, 20, 4);
						System.arraycopy(fullData, start[6], oneData, 24, 4);
						System.arraycopy(fullData, start[7], oneData, 28, 2);
						System.arraycopy(fullData, start[8], oneData, 30, 1);
						System.arraycopy(fullData, start[9], oneData, 31, 1);
						System.arraycopy(fullData, start[10], oneData, 32, 1);
						System.arraycopy(fullData, start[11], oneData, 33, 1);
						System.arraycopy(fullData, start[12], oneData, 34, 1);
						System.arraycopy(fullData, start[13], oneData, 35, 1);
						start[0] += 36;
						Pite3919ShortStringData pite3919ShortStringData = new Pite3919ShortStringData();
						MyStruct.uppack(pite3919ShortStringData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(pite3919ShortStringData);
					}
					return map;
				}
				if (header.Version == 3) {
					byte[] fullData = new byte[bt.length - ((dataLen / 68) - 1) * 12];
					for (int j = 0; j < dataLen / 68; j++) {
						System.arraycopy(bt, j * 68, fullData, j * 56, 56);
					}
					Log.e("5", "39193成组长数据结构 解析： " + TestRCMD.bytesToHexString(fullData));
					int[] start = new int[14];
					start[0] = 0;
					for (int i = 0; i < dataLen / 68; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 8) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 8 && k < 11) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 11 && k < 13) {
								start[k] = start[k - 1] + 1;
							}
							if (k >= 13) {
								start[k] = start[k - 1] + 10;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[56];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 4);
						System.arraycopy(fullData, start[5], oneData, 20, 4);
						System.arraycopy(fullData, start[6], oneData, 24, 4);
						System.arraycopy(fullData, start[7], oneData, 28, 2);
						System.arraycopy(fullData, start[8], oneData, 30, 2);
						System.arraycopy(fullData, start[9], oneData, 32, 2);
						System.arraycopy(fullData, start[10], oneData, 34, 1);
						System.arraycopy(fullData, start[11], oneData, 35, 1);
						System.arraycopy(fullData, start[12], oneData, 36, 10);
						System.arraycopy(fullData, start[13], oneData, 46, 10);
						start[0] += 56;
						Pite3919LongStringData pite3919LongStringData = new Pite3919LongStringData();
						MyStruct.uppack(pite3919LongStringData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(pite3919LongStringData);
						Log.e("5", "39193成组长数据结构： " + pite3919LongStringData.toString() + " dataLen: " + dataLen);
					}
					return map;
				}
				if (header.Version == 7) {
					byte[] fullData = new byte[bt.length - ((dataLen / 80) - 1) * 12];
					for (int j = 0; j < dataLen / 80; j++) {
						System.arraycopy(bt, j * 80, fullData, j * 68, 68);
					}
					int[] start = new int[16];
					start[0] = 0;
					for (int i = 0; i < dataLen / 80; i++) {
						for (int k = 1; k < start.length; k++) {
							if (k < 8) {
								start[k] = start[k - 1] + 4;
							}
							if (k >= 8 && k < 11) {
								start[k] = start[k - 1] + 2;
							}
							if (k >= 11 && k < 13) {
								start[k] = start[k - 1] + 1;
							}
							if (k >= 13 && k < 16) {
								start[k] = start[k - 1] + 10;
							}
							if (k == 16) {
								start[k] = start[k - 1] + 2;
							}
						}
						List<APiteData> list = null;
						byte[] oneData = new byte[68];
						if (map.containsKey(i)) {
							list = (List<APiteData>) map.get(i);
						} else {
							list = new ArrayList<APiteData>();
							map.put(i, list);
						}
						System.arraycopy(fullData, start[0], oneData, 0, 4);
						System.arraycopy(fullData, start[1], oneData, 4, 4);
						System.arraycopy(fullData, start[2], oneData, 8, 4);
						System.arraycopy(fullData, start[3], oneData, 12, 4);
						System.arraycopy(fullData, start[4], oneData, 16, 4);
						System.arraycopy(fullData, start[5], oneData, 20, 4);
						System.arraycopy(fullData, start[6], oneData, 24, 4);
						System.arraycopy(fullData, start[7], oneData, 28, 2);
						System.arraycopy(fullData, start[8], oneData, 30, 2);
						System.arraycopy(fullData, start[9], oneData, 32, 2);
						System.arraycopy(fullData, start[10], oneData, 34, 1);
						System.arraycopy(fullData, start[11], oneData, 35, 1);
						System.arraycopy(fullData, start[12], oneData, 36, 10);
						System.arraycopy(fullData, start[13], oneData, 46, 10);
						System.arraycopy(fullData, start[14], oneData, 56, 10);
						System.arraycopy(fullData, start[15], oneData, 66, 2);
						start[0] += 68;
						Pite3919LongFullData pite3919LongFullData = new Pite3919LongFullData();
						MyStruct.uppack(pite3919LongFullData, oneData, ByteOrder.LITTLE_ENDIAN);
						list.add(pite3919LongFullData);
					}
					return map;
				}
			}
		}
		return null;
	}
}
