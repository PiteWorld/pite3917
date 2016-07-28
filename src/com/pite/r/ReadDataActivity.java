package com.pite.r;

import com.pite.r.util.HostUtils;
import com.pite.r.util.NFCAndBluetoothUtil;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ReadDataActivity extends BaseActivity{
	private TextView readNFC_id,readNFC_V,readNFC_R,readNFC_hege;
	private Tag tag= null ;
	private boolean haveMifareUltralight;
	private NFCAndBluetoothUtil nfcread = null ;
	private NfcAdapter mNfcAdapter;// NFC管理器
	private PendingIntent mPendingIntent  = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("读NFC");
		initdata();
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		nfcread = new NFCAndBluetoothUtil(ReadDataActivity.this);

		 mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
	}
	private void initdata() {
		readNFC_id = (TextView) findViewById(R.id.readNFC_id);
		readNFC_V = (TextView) findViewById(R.id.readNFC_V);
		readNFC_R = (TextView) findViewById(R.id.readNFC_R);
		readNFC_hege = (TextView) findViewById(R.id.readNFC_hege);
	}
	// 当应用程序发现NFC标签时会执行该方法
		@Override
		public void onNewIntent(Intent intent) {
			Log.e("tag", "onNewIntent");
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			HostUtils.tag = tag;
			String[] techList = tag.getTechList();// 获取NFC支持的所有格式
			for (String tech : techList) {// 遍历查询
				Log.e("tag", "NfC所支持的格式 " + tech);
				if (tech.indexOf("MifareUltralight") >= 0) {
					haveMifareUltralight = true;
					break;// 找到后则退出
				}
			}
			// 假如NFC标签不支持MifareUltralight格式
			if (!haveMifareUltralight) {
				Toast.makeText(this, "不支持MifareUltralight数据格式", Toast.LENGTH_LONG).show();
				return;
			}
			setText(nfcread.readTag());
		}
	/**
	 * 赋值
	 * */
	private void setText(String str){
		if(str.trim().length()>3){
			String[] data = str.trim().split("--"); 
			readNFC_id.setText(data[0]);
			readNFC_V.setText(data[1]);
			readNFC_R.setText(data[2]);
			readNFC_hege.setText(data[3].equals("0")?"合格":"不合格");
		}
	}
	// 将当前窗口设为处理NFC的第一个窗口
		@Override
		public void onResume() {
			super.onResume();
			// 当应用程序恢复运行时，创建一个IntentFilter，当有NFC标签被扫描时，调用OnNewIntent方法
			if (mNfcAdapter != null) {
				mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
			}
		}

		/// 应用程序暂停，所以需要移除对NFC标签的监听
		@Override
		public void onPause() {
			super.onPause();
			if (mNfcAdapter != null) {
				mNfcAdapter.disableForegroundDispatch(this);
			}
		}
	@Override
	public View getcontent() {
		return View.inflate(ReadDataActivity.this, R.layout.readnfc, null);
	}

}
