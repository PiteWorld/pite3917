package com.pite.r.tool;

/**
 * 3915 命令 集合
 */
public class TestRCMD {
	/**
	 * 连接上3915 0x35命令
	 * 
	 * @return
	 */
	public static byte[] set0x35Connect() {
		return new byte[] { (byte) 0x43, (byte) 0x4D, (byte) 0x44, (byte) 0x5F, (byte) 0x35, (byte) 0x0C, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x30,
				(byte) 0x30, (byte) 0x30, (byte) 0x30 };
	}

	/**
	 * 连接上3915 0x38命令
	 * 
	 * @return
	 */
	public static byte[] set0x38Connect() {
		return new byte[] { (byte) 0x43, (byte) 0x4D, (byte) 0x44, (byte) 0x5F, (byte) 0x38, (byte) 0x04, (byte) 0x30,
				(byte) 0x30, (byte) 0x30, (byte) 0x30 };
	}

	/**
	 * 请求发送下一个数据块 0x36
	 */
	public static byte[] getNextPacket(int len) {
		int dateLen = len + 6;
		byte[] bt = new byte[dateLen];
		byte[] send = configsendDatePackageHeadBt(0x36, dateLen);
		System.arraycopy(send, 0, bt, 0, 6);
		bt[6] = 0x30;
		bt[7] = 0x30;
		bt[8] = 0x30;
		bt[9] = 0x30;
		return bt;
	}

	/**
	 * byte16
	 * 
	 * @param b
	 * @return
	 */
	public static String bytesToHexString(byte[] b) {
		if (b == null || b.length < 1) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase() + " ");
		}
		return result.toString();
	}

	/**
	 * byte16
	 * 
	 * @param b
	 * @return
	 */
	public static String bytesToHexStrings(byte[] b) {
		StringBuffer result = new StringBuffer();
		String hex;
		for (int i = 0; i < b.length; i++) {
			hex = Integer.toHexString(b[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			result.append(hex.toUpperCase());
		}
		return result.toString();
	}

	/**
	 * Convert hex string to byte[]
	 * 
	 * @param hexString
	 *            the hex string
	 * @return byte[]
	 */
	public static byte[] hexStringToBytes(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static byte toByte(char c) {
		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		return b;
	}

	/**
	 * 配置数据包的命令头
	 * 
	 * @param commCode
	 *            命令码
	 * @param dateLen
	 *            命令数据的长度
	 */
	public static byte[] configsendDatePackageHeadBt(int commCode, int dateLen) {
		byte[] bt = new byte[6];
		bt[0] = "C".getBytes()[0];
		bt[1] = "M".getBytes()[0];
		bt[2] = "D".getBytes()[0];
		bt[3] = "_".getBytes()[0];
		bt[4] = (byte) commCode;
		bt[5] = (byte) dateLen;
		return bt;
	}

	/**
	 * 返回的~DT包头
	 * 
	 * @param paklen
	 * @param packenum
	 * @return
	 */
	public static byte[] setHeader(int paklen, int packenum) {
		byte[] bt = new byte[11];
		byte[] sendDT = cofingDT();
		System.arraycopy(sendDT, 0, bt, 0, 3);
		byte[] lengBt = zeroBt(String.valueOf(paklen), 4);
		System.arraycopy(lengBt, 0, bt, 3, 4);
		byte[] numBt = zeroBt(String.valueOf(packenum), 4);
		System.arraycopy(numBt, 0, bt, 7, 4);
		return bt;
	}

	/**
	 * DT头
	 * 
	 * @return
	 */
	public static byte[] cofingDT() {
		byte[] bt = new byte[3];
		bt[0] = "~".getBytes()[0];
		bt[1] = "D".getBytes()[0];
		bt[2] = "T".getBytes()[0];
		return bt;
	}

	/**
	 * ID头
	 * 
	 * @return
	 */
	public static byte[] cofingID() {
		byte[] bt = new byte[3];
		bt[0] = "~".getBytes()[0];
		bt[1] = "I".getBytes()[0];
		bt[2] = "D".getBytes()[0];
		// bt[3] = (byte) commCode;
		// bt[4] = (byte) dateLen;
		return bt;
	}

	/**
	 * 数据添加
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static byte[] zeroBt(String str, int length) {
		int strlen = str.length();
		if (strlen < length) {
			while (strlen < length) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);
				str = sb.toString();
				strlen = str.length();
			}
		}
		return str.getBytes();
	}

}
