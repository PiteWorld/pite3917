package com.pite.r.tool;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

/**
 * ���� WIFI�ȵ�
 */
public class WifiMangerOpen {
	// wifi�ȵ㿪��
	public boolean setWifiApEnabled(WifiManager wifiManager, boolean enabled) {
		if (enabled) { // disable WiFi in any case
			// wifi���ȵ㲻��ͬʱ�򿪣����Դ��ȵ��ʱ����Ҫ�ر�wifi
			wifiManager.setWifiEnabled(false);
		}
		try {
			// �ȵ��������
			WifiConfiguration apConfig = new WifiConfiguration();
			// �����ȵ������(���������ֺ���ӵ������ʲô��)
			apConfig.SSID = "PITE3915";
			// �����ȵ������
			apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//			apConfig.preSharedKey = "12345678";
			// ͨ��������������ȵ�
			java.lang.reflect.Method method = wifiManager.getClass().getMethod("setWifiApEnabled",
					WifiConfiguration.class, Boolean.TYPE);
			// �����ȵ��״̬
			return (Boolean) method.invoke(wifiManager, apConfig, enabled);
		} catch (Exception e) {
			return false;
		}
	}
}
