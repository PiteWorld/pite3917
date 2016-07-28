package com.pite.r;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.JsonData.BatteryGroupUtils;
import com.pite.jsontool.JsonTools;
import com.pite.r.adapter.StationAdapter;
import com.pite.r.constant.Constant;
import com.pite.r.netty.ConnectServer;
import com.pite.r.tool.WifiMangerOpen;
import com.pite.r.util.LogoUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 站信息
 * 
 * @author Administrator
 *
 */
public class StationActivity extends BaseActivity implements OnItemClickListener {
	private ListView lv;
	private StationAdapter adapter;
	private List<BatteryGroupUtils> list;
	private String str;
	public static int nodeid;
	private WifiManager manager;
	private WifiMangerOpen open = null;
	private ConnectServer connect;

	private String url = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.string12));
		manager = (WifiManager) getSystemService(StationActivity.this.WIFI_SERVICE);
		open = new WifiMangerOpen();
		lv = (ListView) findViewById(R.id.station_lv);
		lv.setOnItemClickListener(this);

		String logoimange = LogoUser.getInstance().getLogourl();
		File file = new File(Constant.LOGOIMAGE + "/" + logoimange.substring(logoimange.lastIndexOf("/") + 1));
		if (file.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), null);
			setBitmap(bitmap);
		} else {
			url = "http://203.191.147.81:8001/bmcp/logopng/" + logoimange.substring(logoimange.lastIndexOf("/") + 1);
			Log.e("tag", "Thread1.............");
			new Thread1().start();

		}
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		setWifiManger();
		HttpGetData(LoginActivity.basic_ip + "/rest/" + Constant.BATTERY_GOURP + LoginActivity.nodid + "/" + str, null); // 网络请求
	}

	public void setWifiManger() {
		if (getIntent().getBooleanExtra("wifiStatus", false)) {
			new Thread() {
				public void run() {
					connect = new ConnectServer(8888);
				};
			}.start();
		}
	}

	/**
	 * 网络Get 请求
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetData(final String url, final RequestParams params) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {

				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getListJson(new String(arg2), BatteryGroupUtils.class);
						adapter = new StationAdapter(StationActivity.this, list);
						lv.setAdapter(adapter);
					} else {
						Toast.makeText(StationActivity.this, R.string.resqustFails, 0).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

			}

		});
		Log.e("页面一返回的url", url);
	}

	/**
	 * 手机返回 键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitMenu(StationActivity.this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void ExitMenu(Context context) {
		new AlertDialog.Builder(this).setTitle(R.string.ok_exit)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 退出时关闭wifi
						open.setWifiApEnabled(manager, false);
						StationActivity.this.finish();
					}
				}).setNegativeButton(R.string.no, null).show();
	}

	@Override
	public View getcontent() {
		return View.inflate(StationActivity.this, R.layout.activity_station, null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// 保存返回的登录地址
		Constant.BATTERY_BASIC_ADDRESS_LOGINs = list.get(position).getRooturl() + "/";
		nodeid = list.get(position).getNodeid();
		startActivity(new Intent(StationActivity.this, GroupActivity.class));
	}

	public Bitmap getHttpBitmap(String url) {
		URL myFileURL;
		Bitmap bitmap = null;
		Log.e("tag", url + " -----");
		try {
			myFileURL = new URL(url);
			// 获得连接
			HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
			// 设置超时时间为6000毫秒，conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(6000);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			// 这句可有可无，没有影响
			// conn.connect();
			Log.e("tag", conn.getContentLength() + "---");
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;

	}

	/**
	 * 获取网落图片资源
	 * 
	 * @param url
	 * @return
	 */
	class Thread1 extends Thread {

		@Override
		public void run() {
			final Bitmap bit = getHttpBitmap(url);
			runOnUiThread(new Runnable() {
				public void run() {
					setBitmap(bit);
				}
			});
		}
	}

}
