package com.pite.r.constant;

import com.pite.r.LoginActivity;

import android.os.Environment;

public class Constant {
	public static final String GETBATTERYTYPE = "getBatterytype";// �鿴���ݱ���
	public static final String GETBATTERYLOCATOR = "getbatteryLocator";// �鿴���ݱ����سߴ�
	public static final String GETFACTORYINFO = "getFactoryInfo";// ���е�ع���
	public static final String GETBATTERYFACTORYTYPE = "http://203.191.147.81:8011/bms/rest/getFromFactoryTobttyInfo";
	public static final String GETFACTORYBATTERYINFO = "http://203.191.147.81:8011/bms/rest/getBatterytype";// ���е������
	public static final String GETBATTERYDATAUPLOADNFCNUM = "getBatteryDataUploadNfcNum/";// �鿴��ʷ����
																							// +
																							// groupid
	public static String BATTERY_BASIC_ADDRESS = LoginActivity.basic_ip; //

	public static String BATTERY_BASIC_ADDRESS_LOGINs = ""; // �ڶ�ҳ��ʼ������ַ
	public static final String BATTERY_BASIC_ADDRESS_LOGIN = "http://203.191.147.81:8080/bms/rest/"; // ��¼������ַ
	public static final String BATTERY_BASIC_LOGIN = "getCheckUserLoginIP/";
	public static final String BATTERY_GOURP = "getFirstPageData/";// �����
	public static final String BATTERY_PACKET = "getGroupPageData";// ��ط���
	public static final String LOGIN_LOGO = "http://203.191.147.81:8001/";// ��ҵlogo��ַ+��¼���صĵ�ַ
	public static final String LOGIN_LOGOADSS = "http://203.191.147.81:8011/bms/rest/"; // ������°汾�ĵ�ַ
	public static final String GETVERSION_NAME = "getTestInitUpgradeInfo/1";// �汾�ŵĻ�ȡ
	public static final String LOGOFILE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pite_Battery";
	public static final String LOGOIMAGE = Environment.getExternalStorageDirectory().getAbsolutePath()
			+ "/Pite_Battery/loge";// logo�����ַ
	public static final String BATTERY_UPLOAD_DATA = "updateBatteryDataUpload";// �ϴ�����
}
