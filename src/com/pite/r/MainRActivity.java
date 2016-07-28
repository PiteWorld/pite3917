package com.pite.r;

import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.JsonData.BatteryExterior;
import com.pite.JsonData.Batterys;
import com.pite.JsonData.JsonData;
import com.pite.jsontool.JsonTools;
import com.pite.r.constant.Constant;
import com.pite.r.http.HttpReustClient;
import com.pite.r.netty.ConnectServer;
import com.pite.r.tool.WifiMangerOpen;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainRActivity extends BaseActivity implements OnClickListener {
	private Button test_data, send_data, send_NFC;
	public static List<JsonData> jsonData = null;
	public static List<BatteryExterior> batteryExterior = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				startActivity(new Intent(MainRActivity.this, TestDataActivity.class));
				break;
			case 2:
				startActivity(new Intent(MainRActivity.this, SendDataActivity.class));
				break;
			case 3:
				startActivity(new Intent(MainRActivity.this, ReadDataActivity.class));
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getResources().getString(R.string.MainRActivity));
		setVisibility(View.GONE);
		initdata();
		getBatterytype(Constant.GETBATTERYTYPE, null);
		getBatteryLocat(Constant.GETBATTERYLOCATOR, null);
	}
	private void initdata() {
		test_data = (Button) findViewById(R.id.test_data);
		send_data = (Button) findViewById(R.id.send_data);
		send_NFC = (Button) findViewById(R.id.send_NFC);
		test_data.setOnClickListener(this);
		send_data.setOnClickListener(this);
		send_NFC.setOnClickListener(this);
	}

	@Override
	public View getcontent() {
		return View.inflate(MainRActivity.this, R.layout.activity_main_r, null);
	}

	/**
	 * getBatteryExterior
	 */
	private void getBatterytype(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg2.length > 5) {
					jsonData = JsonTools.getListJson(new String(arg2), JsonData.class);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

			}
		});
	}

	/**
	 * getBatteryExterior ³ß´çÊôÐÔ
	 */
	private void getBatteryLocat(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg2.length > 5) {
					batteryExterior = JsonTools.getListJson(new String(arg2), BatteryExterior.class);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.test_data:
			handler.sendEmptyMessageDelayed(1, 500);
			break;
		case R.id.send_data:
			handler.sendEmptyMessageDelayed(2, 500);
			break;
		case R.id.send_NFC:
			handler.sendEmptyMessageDelayed(3, 500);
			break;

		}
	}
}
