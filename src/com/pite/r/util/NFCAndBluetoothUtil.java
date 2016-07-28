package com.pite.r.util;

import java.io.IOException;
import java.nio.charset.Charset;

import com.pite.r.BluetoothDiaLogActiyity;
import com.pite.r.SendDataActivity;
import com.pite.r.TestDataActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.util.Log;
import android.widget.Toast;

public class NFCAndBluetoothUtil {
	private AlertDialog.Builder dialog = null;
	private final String[] items = { "NFCд��", "������ӡ" };
	private Context context;

	public NFCAndBluetoothUtil(Context context) {
		this.context = context;
	}

	// ����д��ҳ������16���ֽ�! NFCд������
	// д�����ݱ���Ҫд���ĸ��ֽ�
	public void writeTag(String str) {
		if (HostUtils.tag == null) {
			Toast.makeText(context, "��ɨ��NFC��Ƭ", 0).show();
			return;
		}
		MifareUltralight ultralight = MifareUltralight.get(HostUtils.tag);
		try {
			ultralight.connect();// ��������,�����MifarfdeClassic��ǩ����IO����
			byte[][] by = getByte(str);
			for (int page = 0; page < by.length; page++) {
				ultralight.writePage(page + 4, by[page]);
			}
			Toast.makeText(context, "�ɹ�д��ultralight��ʽ����", Toast.LENGTH_LONG).show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(context, "ʧ��", Toast.LENGTH_LONG).show();
		} finally {
			try {
				ultralight.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * д�����ݸ�ʽ
	 * 
	 * @param b
	 * @return
	 */
	String mFormat = "GB2312";

	private byte[][] getByte(String str) {
		byte[] resource = str.getBytes(Charset.forName(mFormat));
		int arrY = str.getBytes(Charset.forName(mFormat)).length / 4;
		if ((str.getBytes(Charset.forName(mFormat)).length % 4) != 0)
			arrY++;
		byte[][] res = new byte[arrY][4];
		for (int index = 0, y = 0, x = 0; index < res.length * res[0].length; index++, x++) {
			if (x > 3) {
				x = 0;
				y++;
			}
			;
			if (index < resource.length)
				res[y][x] = resource[index];
			else
				res[y][x] = " ".getBytes(Charset.forName(mFormat))[0];
		}
		return res;
	}

	// NFC��������
	private byte[] datas;

	public String readTag() {
		if (HostUtils.tag == null) {
			return "��ɨ��NFC��Ƭ";
		}
		MifareUltralight ultralight = MifareUltralight.get(HostUtils.tag);
		try {
			ultralight.connect();
			byte[] data = ultralight.readPages(4);
			byte[] data2 = ultralight.readPages(8);
			byte[] data3 = ultralight.readPages(12);
			if (data3.length > 0) {
				datas = new byte[data.length + data2.length + data3.length];
				System.arraycopy(data, 0, datas, 0, data.length);
				System.arraycopy(data2, 0, datas, data.length, data2.length);
				System.arraycopy(data3, 0, datas, data.length + data2.length, data3.length);
			} else if (data2.length > 0) {
				datas = new byte[data.length + data2.length];
				System.arraycopy(data, 0, datas, 0, data.length);
				System.arraycopy(data2, 0, datas, data.length, data2.length);
			} else {
				datas = new byte[data.length];
				System.arraycopy(data, 0, datas, 0, data.length);
			}
			return new String(datas, Charset.forName(mFormat));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ultralight.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;// ���ɹ��Ļ��򷵻�λnull
	}

	/***
	 * ����һ��dialog
	 */
	int select = 0;

	public void showDialog(final String str) {
		if (dialog == null) {
			dialog = new AlertDialog.Builder(context)
					.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							select = which;
						}
					}).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							Log.e("3", "select  " + select);
							switch (select) {
							case 0:
								writeTag(str);
								break;
							case 1:
								// ������ӡ
								if (HostUtils.bs != null) {
									if (HostUtils.bs.connect()) {
										HostUtils.bs.send(TestDataActivity.sb.toString() + "\n");
									} else {
										context.startActivity(new Intent(context, BluetoothDiaLogActiyity.class));
									}
								} else {
									context.startActivity(new Intent(context, BluetoothDiaLogActiyity.class));
								}
								break;
							}
							 select = 0;
						}

					}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							select = 0;
						}
					});
			 

		}
		dialog.create().show();
	}
}
