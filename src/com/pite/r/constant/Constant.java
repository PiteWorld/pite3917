package com.pite.r.constant;

import com.pite.r.LoginActivity;

import android.os.Environment;

public class Constant {
	public static final String GETBATTERYTYPE = "getBatterytype";// 查看数据标题
	public static final String GETBATTERYLOCATOR = "getbatteryLocator";// 查看数据标题电池尺寸
	public static final String GETFACTORYINFO = "getFactoryInfo";// 所有电池工厂
	public static final String GETBATTERYFACTORYTYPE = "http://203.191.147.81:8011/bms/rest/getFromFactoryTobttyInfo";
	public static final String GETFACTORYBATTERYINFO = "http://203.191.147.81:8011/bms/rest/getBatterytype";// 所有电池类型
	public static final String GETBATTERYDATAUPLOADNFCNUM = "getBatteryDataUploadNfcNum/";// 查看历史数据
																							// +
																							// groupid
	public static String BATTERY_BASIC_ADDRESS = LoginActivity.basic_ip; //

	public static String BATTERY_BASIC_ADDRESS_LOGINs = ""; // 第二页开始基础地址
	public static final String BATTERY_BASIC_ADDRESS_LOGIN = "http://203.191.147.81:8080/bms/rest/"; // 登录基础地址
	public static final String BATTERY_BASIC_LOGIN = "getCheckUserLoginIP/";
	public static final String BATTERY_GOURP = "getFirstPageData/";// 电池组
	public static final String BATTERY_PACKET = "getGroupPageData";// 电池分组
	public static final String LOGIN_LOGO = "http://203.191.147.81:8001/";// 企业logo地址+登录返回的地址
	public static final String LOGIN_LOGOADSS = "http://203.191.147.81:8011/bms/rest/"; // 获得最新版本的地址
	public static final String GETVERSION_NAME = "getTestInitUpgradeInfo/1";// 版本号的获取
	public static final String LOGOFILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pite_Battery";
	public static final String LOGOIMAGE = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Pite_Battery/loge";// logo保存地址
	public static final String BATTERY_UPLOAD_DATA = "updateBatteryDataUpload";// 上传数据
}
