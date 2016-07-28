package com.pite.r.prol;

import java.nio.ByteOrder;
import java.util.List;
import java.util.Map;

import com.pite.r.tool.TestRCMD;
import com.pite.r.util.ProUtils;

import android.util.Log;

/**
 * 3915 ���ݽ���
 */
public class TestRProl {
	private Pite3915Header pite3915Header;
	private Map<Integer, List<APiteData>> piteObjectData;

	/**
	 * �������
	 */
	public boolean addBytes(byte[] bt, int dataLen) {
		try {
			Log.e("3", "���ݽ���   " + TestRCMD.bytesToHexString(bt));
			byte[] headBt = new byte[12];
			System.arraycopy(bt, 0, headBt, 0, headBt.length);
			pite3915Header = new Pite3915Header();
			MyStruct.uppack(pite3915Header, headBt, ByteOrder.LITTLE_ENDIAN);
			Pite3915SwitchData pite3915SwitchData = new Pite3915SwitchData();
			byte[] btData = new byte[bt.length - headBt.length ];
			 System.arraycopy(bt, headBt.length, btData, 0, btData.length);
			piteObjectData = pite3915SwitchData.setSwitch(btData, pite3915Header, dataLen);
			Log.e("3", "���ݽ������   " + piteObjectData);
			return true;
		} catch (Exception e) {
			Log.e("3", "�����쳣   " + e);
			return false;
		}
	}

	/**
	 * ����ͷ
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
