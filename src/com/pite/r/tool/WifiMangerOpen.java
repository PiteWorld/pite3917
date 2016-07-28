package com.pite.r.tool;

import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

/**
 * 创建 WIFI热点
 */
public class WifiMangerOpen {
	// wifi热点开关
	public boolean setWifiApEnabled(WifiManager wifiManager, boolean enabled) {
		if (enabled) { // disable WiFi in any case
			// wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
			wifiManager.setWifiEnabled(false);
		}
		try {
			// 热点的配置类
			WifiConfiguration apConfig = new WifiConfiguration();
			// 配置热点的名称(可以在名字后面加点随机数什么的)
			apConfig.SSID = "PITE3915";
			// 配置热点的密码
			apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
//			apConfig.preSharedKey = "12345678";
			// 通过反射调用设置热点
			java.lang.reflect.Method method = wifiManager.getClass().getMethod("setWifiApEnabled",
					WifiConfiguration.class, Boolean.TYPE);
			// 返回热点打开状态
			return (Boolean) method.invoke(wifiManager, apConfig, enabled);
		} catch (Exception e) {
			return false;
		}
	}
}
