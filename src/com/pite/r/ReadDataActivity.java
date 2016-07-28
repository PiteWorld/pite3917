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
	private NfcAdapter mNfcAdapter;// NFC������
	private PendingIntent mPendingIntent  = null ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("��NFC");
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
	// ��Ӧ�ó�����NFC��ǩʱ��ִ�и÷���
		@Override
		public void onNewIntent(Intent intent) {
			Log.e("tag", "onNewIntent");
			tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
			HostUtils.tag = tag;
			String[] techList = tag.getTechList();// ��ȡNFC֧�ֵ����и�ʽ
			for (String tech : techList) {// ������ѯ
				Log.e("tag", "NfC��֧�ֵĸ�ʽ " + tech);
				if (tech.indexOf("MifareUltralight") >= 0) {
					haveMifareUltralight = true;
					break;// �ҵ������˳�
				}
			}
			// ����NFC��ǩ��֧��MifareUltralight��ʽ
			if (!haveMifareUltralight) {
				Toast.makeText(this, "��֧��MifareUltralight���ݸ�ʽ", Toast.LENGTH_LONG).show();
				return;
			}
			setText(nfcread.readTag());
		}
	/**
	 * ��ֵ
	 * */
	private void setText(String str){
		if(str.trim().length()>3){
			String[] data = str.trim().split("--"); 
			readNFC_id.setText(data[0]);
			readNFC_V.setText(data[1]);
			readNFC_R.setText(data[2]);
			readNFC_hege.setText(data[3].equals("0")?"�ϸ�":"���ϸ�");
		}
	}
	// ����ǰ������Ϊ����NFC�ĵ�һ������
		@Override
		public void onResume() {
			super.onResume();
			// ��Ӧ�ó���ָ�����ʱ������һ��IntentFilter������NFC��ǩ��ɨ��ʱ������OnNewIntent����
			if (mNfcAdapter != null) {
				mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
			}
		}

		/// Ӧ�ó�����ͣ��������Ҫ�Ƴ���NFC��ǩ�ļ���
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
