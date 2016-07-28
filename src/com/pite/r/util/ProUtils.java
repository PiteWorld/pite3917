package com.pite.r.util;

import java.text.DecimalFormat;

public class ProUtils {
	/**
	 * 保留1%进度
	 */
	public static String getFloat4(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.000");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number);// format 返回的是字符串
		return p;
	}
	/**
	 * 保留1%进度
	 */
	public static String getFloat5(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.0000");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number);// format 返回的是字符串
		return p;
	}

	/**
	 * 保留1%进度
	 */
	public static String getFloat1(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number);// format 返回的是字符串
		return p;
	}
	/**
	 * 保留1%进度
	 */
	public static String getFloat1s(float number) {
		DecimalFormat decimalFormat = new DecimalFormat("##0.0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
		String p = decimalFormat.format(number);// format 返回的是字符串
		return p;
	}

	/**
	 * 当前电价
	 */
	public static String getFloat3(float number) {
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String dd = fnum.format(number);
		return dd + "";
	}

	/***
	 * 设置时间
	 * 
	 * @param bt
	 * @return
	 */
	public static String getTestTime(byte[] bt) {
		StringBuffer sb = new StringBuffer();
		int year = 0, mouth = 0, date = 0, hour = 0, mintues = 0, sceonds = 0;
		for (int i = 0; i < bt.length; i++) {
			if (i == 0) {
				year = bt[0];
				sb.append((year + 2000)).append("-");
			}
			if (i == 1) {
				mouth = bt[1];
				if (mouth < 10) {
					sb.append("0").append(mouth).append("-");
				} else {
					sb.append(mouth).append("-");
				}
			}
			if (i == 2) {
				date = bt[2];
				if (date < 10) {
					sb.append("0").append(date).append("  ");
				} else {
					sb.append(date).append("-");
				}
			}
			if (i == 3) {
				hour = bt[3];
				if (hour < 10) {
					sb.append("0").append(hour).append(":");
				} else {
					sb.append(hour).append(":");
				}
			}
			if (i == 4) {
				mintues = bt[4];
				if (mintues < 10) {
					sb.append("0").append(mintues).append(":");
				} else {
					sb.append(mintues).append(":");
				}
			}
			if (i == 5) {
				sceonds = bt[5];
				if (sceonds < 10) {
					sb.append("0").append(sceonds);
				} else {
					sb.append(sceonds);
				}
			}
		}
		return sb.toString();

	}
}
