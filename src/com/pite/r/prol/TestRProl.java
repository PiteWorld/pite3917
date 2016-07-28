package com.pite.r.prol;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

import com.pite.r.tool.TestRCMD;
import com.pite.r.util.ProUtils;

import android.util.Log;

/**
 * 3915 数据解析
 */
public class TestRProl {
	private Pite3915Header pite3915Header;
	private Map<Integer, List<APiteData>> piteObjectData;

	/**
	 * 添加数据
	 */
	public boolean addBytes(byte[] bt, int dataLen) {
		try {
			Log.e("3", "数据解析   " + TestRCMD.bytesToHexString(bt));
			byte[] headBt = new byte[12];
			System.arraycopy(bt, 0, headBt, 0, headBt.length);
			pite3915Header = new Pite3915Header();
			MyStruct.uppack(pite3915Header, headBt, ByteOrder.LITTLE_ENDIAN);
			Pite3915SwitchData pite3915SwitchData = new Pite3915SwitchData();
			byte[] btData = new byte[bt.length - headBt.length ];
			 System.arraycopy(bt, headBt.length, btData, 0, btData.length);
			piteObjectData = pite3915SwitchData.setSwitch(btData, pite3915Header, dataLen);
			Log.e("3", "数据解析完成   " + piteObjectData);
			return true;
		} catch (Exception e) {
			Log.e("3", "解析异常   " + e);
			return false;
		}
	}

	/**
	 * 数据头
	 * 
	 * @return
	 */
	public Pite3915Header get3915Header() {
		return pite3915Header;
	}

	public Map<Integer, List<APiteData>> getPite3915Data() {
		return piteObjectData;
	}

}
