package com.pite.r;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.JsonData.BatteryExterior;
import com.pite.JsonData.JsonData;
import com.pite.JsonData.ShowData;
import com.pite.jsontool.JsonTools;
import com.pite.r.adapter.Myadapter;
import com.pite.r.constant.Constant;
import com.pite.r.http.HttpReustClient;
import com.pite.r.prol.TestRProl;
import com.pite.r.util.HostUtils;
import com.pite.r.util.NFCAndBluetoothUtil;

import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * �鿴 ����
 */
public class TestDataActivity extends BaseActivity implements OnItemClickListener {
	private TextView test_voltage, test_internal, test_testing_time, test_Battery_type, test_Battery_model,
			test_Battery_manufacturers, test_long, test_width, test_height;
	private ListView test_lv;
	private TestRProl prol = null;
	private Myadapter adapter;
	private List<JsonData> jsonData = null;
	private List<String[]> listdata;// ����������Դ
	private List<ShowData> showData = null;// ��ʷ����
	private List<BatteryExterior> batteryExterior = null;
	private Tag tag = null;
	private PendingIntent mPendingIntent;// �ӳٵ�Intent
	private boolean haveMifareUltralight = false;// �ж��Ƿ������ָ�ʽ
	private NFCAndBluetoothUtil nfcAndBluetoothUtil = null;
	private BluetoothAdapter badApter = null;// ����������
	private NfcAdapter mNfcAdapter;// NFC������
	public static StringBuilder sb = null;
	private String nfcData = null;
	private TextView texId, texV, texR, texIsFinsh;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.test_data));
		initdata();
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		listdata = new ArrayList<String[]>();
		nfcAndBluetoothUtil = new NFCAndBluetoothUtil(this);
		adapter = new Myadapter(TestDataActivity.this, listdata);
		getLoadInfo(Constant.GETBATTERYDATAUPLOADNFCNUM + GroupActivity.groupID, null);
		test_lv.setAdapter(adapter);
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()), 0);
		badApter = BluetoothAdapter.getDefaultAdapter();
		if (!badApter.isEnabled()) {
			badApter.enable();
		}
	}

	// ��ʼ������
	private void initdata() {
		test_voltage = (TextView) findViewById(R.id.test_voltage);
		test_internal = (TextView) findViewById(R.id.test_internal);
		test_testing_time = (TextView) findViewById(R.id.test_testing_time);
		test_Battery_type = (TextView) findViewById(R.id.test_Battery_type);
		test_Battery_model = (TextView) findViewById(R.id.test_Battery_model);
		test_Battery_manufacturers = (TextView) findViewById(R.id.test_Battery_manufacturers);
		test_long = (TextView) findViewById(R.id.test_long);
		test_width = (TextView) findViewById(R.id.test_width);
		test_height = (TextView) findViewById(R.id.test_height);
		test_lv = (ListView) findViewById(R.id.test_lv);
		test_lv.setOnItemClickListener(this);
		jsonData = MainRActivity.jsonData;
		batteryExterior = MainRActivity.batteryExterior;
		if (jsonData != null || batteryExterior != null)
			setBatteryData();
		setBatteryExterior();
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
		return View.inflate(TestDataActivity.this, R.layout.activity_test_data, null);
	}

	// ��Ӧ�ó�����NFC��ǩʱ��ִ�и÷���
	@Override
	public void onNewIntent(Intent intent) {
		Log.e("tag", "onNewIntent");
		tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		HostUtils.tag = tag;
		HostUtils.read = nfcAndBluetoothUtil ;
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
	}

	/**
	 * ����getBatteryExterior����
	 */
	private void setBatteryExterior() {
		test_testing_time.setText(batteryExterior.get(0).getRegDate());
		test_long.setText(batteryExterior.get(0).getWLength() + "mm");
		test_width.setText(batteryExterior.get(0).getWWidth() + "mm");
		test_height.setText(batteryExterior.get(0).getWHeigth() + "mm");
	}

	/**
	 * ����getBatteryData����
	 */
	private void setBatteryData() {
		// test_voltage, test_internal, test_testing_time, test_Battery_type,
		// test_Battery_model,test_Battery_manufacturers
		test_voltage.setText(jsonData.get(0).getNominalcu() + "V");
		test_internal.setText(jsonData.get(0).getNominalr() + "m��");
		test_Battery_type.setText(jsonData.get(0).getTypename());
		test_Battery_model.setText(jsonData.get(0).getProdname());
		test_Battery_manufacturers.setText(jsonData.get(0).getAutomationid());
	}

	/**
	 * �鿴��ʷ���ݵĽӿ�
	 */
	private void getLoadInfo(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

			}

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg2.length > 5) {
					showData = JsonTools.getListJson(new String(arg2), ShowData.class);
					for (int i = 0; i < showData.size(); i++) {
						String[] data = new String[] { showData.get(i).getBatteryNum(), showData.get(i).getBatteryV(),
								showData.get(i).getBatteryR1(), showData.get(i).getBatteryCap(),
								showData.get(i).getBatteryR2(), showData.get(i).getBatteryC2(),
								showData.get(i).getBatteryQC() };
						listdata.add(data);
					}
					adapter.notifyDataSetChanged();
				}
			}

		});
	}

	@Override
	protected void onDestroy() {
		// ���handler��Ϣ������ȴ�й©
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		texId = (TextView) view.findViewById(R.id.test_lv_number);
		texV = (TextView) view.findViewById(R.id.test_voltage);
		texR = (TextView) view.findViewById(R.id.test_internal);
		texIsFinsh = (TextView) view.findViewById(R.id.test_hege);
		nfcData = NFCWrite();
		BluetoothData();
		nfcAndBluetoothUtil.showDialog(nfcData);
	}

	/**
	 * NFCд��
	 */
	private String NFCWrite() {
		// NFCд��
		StringBuilder sb = new StringBuilder();
		String station = texIsFinsh.getText().toString().trim();
		if (station.equals("��")) {
			station = 0 + ""; // �ϸ�
		} else {
			station = 1 + ""; // ���ϸ�
		}
		sb.append(texId.getText().toString().trim()).append("--").append(texV.getText().toString().trim()).append("--")
				.append(texR.getText().toString().trim()).append("--").append(station).append("             ");
		return sb.toString();
	}

	/**
	 * ����д��
	 */
	private void BluetoothData() {
		sb = new StringBuilder();
		String station = texIsFinsh.getText().toString().trim();
		sb.append("���:").append(texId.getText().toString().trim()).append("\n��ѹ:")
				.append(texV.getText().toString().trim()).append("V").append("\n����R:")
				.append(texR.getText().toString().trim()).append("m��").append("\n�ϸ�:").append(station);
	}

}
