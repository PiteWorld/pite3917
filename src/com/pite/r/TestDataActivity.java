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
 * 查看 数据
 */
public class TestDataActivity extends BaseActivity implements OnItemClickListener {
	private TextView test_voltage, test_internal, test_testing_time, test_Battery_type, test_Battery_model,
			test_Battery_manufacturers, test_long, test_width, test_height;
	private ListView test_lv;
	private TestRProl prol = null;
	private Myadapter adapter;
	private List<JsonData> jsonData = null;
	private List<String[]> listdata;// 适配器数据源
	private List<ShowData> showData = null;// 历史数据
	private List<BatteryExterior> batteryExterior = null;
	private Tag tag = null;
	private PendingIntent mPendingIntent;// 延迟的Intent
	private boolean haveMifareUltralight = false;// 判断是否有这种格式
	private NFCAndBluetoothUtil nfcAndBluetoothUtil = null;
	private BluetoothAdapter badApter = null;// 蓝牙适配器
	private NfcAdapter mNfcAdapter;// NFC管理器
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

	// 初始化数据
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
		return View.inflate(TestDataActivity.this, R.layout.activity_test_data, null);
	}

	// 当应用程序发现NFC标签时会执行该方法
	@Override
	public void onNewIntent(Intent intent) {
		Log.e("tag", "onNewIntent");
		tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		HostUtils.tag = tag;
		HostUtils.read = nfcAndBluetoothUtil ;
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
	}

	/**
	 * 加载getBatteryExterior数据
	 */
	private void setBatteryExterior() {
		test_testing_time.setText(batteryExterior.get(0).getRegDate());
		test_long.setText(batteryExterior.get(0).getWLength() + "mm");
		test_width.setText(batteryExterior.get(0).getWWidth() + "mm");
		test_height.setText(batteryExterior.get(0).getWHeigth() + "mm");
	}

	/**
	 * 加载getBatteryData数据
	 */
	private void setBatteryData() {
		// test_voltage, test_internal, test_testing_time, test_Battery_type,
		// test_Battery_model,test_Battery_manufacturers
		test_voltage.setText(jsonData.get(0).getNominalcu() + "V");
		test_internal.setText(jsonData.get(0).getNominalr() + "mΩ");
		test_Battery_type.setText(jsonData.get(0).getTypename());
		test_Battery_model.setText(jsonData.get(0).getProdname());
		test_Battery_manufacturers.setText(jsonData.get(0).getAutomationid());
	}

	/**
	 * 查看历史数据的接口
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
		// 清除handler消息，避免內存泄漏
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
	 * NFC写入
	 */
	private String NFCWrite() {
		// NFC写入
		StringBuilder sb = new StringBuilder();
		String station = texIsFinsh.getText().toString().trim();
		if (station.equals("是")) {
			station = 0 + ""; // 合格
		} else {
			station = 1 + ""; // 不合格
		}
		sb.append(texId.getText().toString().trim()).append("--").append(texV.getText().toString().trim()).append("--")
				.append(texR.getText().toString().trim()).append("--").append(station).append("             ");
		return sb.toString();
	}

	/**
	 * 蓝牙写入
	 */
	private void BluetoothData() {
		sb = new StringBuilder();
		String station = texIsFinsh.getText().toString().trim();
		sb.append("序号:").append(texId.getText().toString().trim()).append("\n电压:")
				.append(texV.getText().toString().trim()).append("V").append("\n内阻R:")
				.append(texR.getText().toString().trim()).append("mΩ").append("\n合格:").append(station);
	}

}
