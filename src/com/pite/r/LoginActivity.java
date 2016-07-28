package com.pite.r;

import java.io.File;
import java.util.Locale;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.r.constant.Constant;
import com.pite.r.http.HttpReustClient;
import com.pite.r.service.LogoServer;
import com.pite.r.tool.WifiMangerOpen;
import com.pite.r.util.LogoUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener, OnCheckedChangeListener {
	private EditText login_name, login_pwd;
	private Button login_btn, sp_language;
	private TextView versionNumber;
	private String name, pwd;
	private CheckBox save_pwd;
	private boolean isCheck;
	private SharedPreferences share;
	public static int isChinese = 0; // 中英文选择
	private WifiManager manager;
	private WifiMangerOpen open = null;
	private boolean flag = false;
	private String versionName;
	private boolean wifiStatus;
	public static String basic_ip;// 登录返回的地址
	public static String nodid;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		manager = (WifiManager) getSystemService(LoginActivity.this.WIFI_SERVICE);
		setTitle("电动汽车管理系统");
		// 获取版本号
		PackageManager pm = LoginActivity.this.getPackageManager();
		PackageInfo pi;
		initData();
		open = new WifiMangerOpen();
		openWIFI();
		setVisibility(View.GONE);
		try {
			pi = pm.getPackageInfo(LoginActivity.this.getPackageName(), 0);
			versionName = pi.versionName;
			versionNumber.setText(getResources().getString(R.string.version) + versionName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initData() {
		login_name = (EditText) findViewById(R.id.login_name);
		login_pwd = (EditText) findViewById(R.id.login_pwd);
		login_btn = (Button) findViewById(R.id.login_btn);
		save_pwd = (CheckBox) findViewById(R.id.save_pwd);
		sp_language = (Button) findViewById(R.id.sp_language);
		versionNumber = (TextView) findViewById(R.id.versionNumber);
		login_btn.setOnClickListener(this);
		sp_language.setOnClickListener(this);
		save_pwd.setOnCheckedChangeListener(this);
		share = getSharedPreferences("test", LoginActivity.this.MODE_PRIVATE);

		if (share.getBoolean("isCheck", false)) {
			login_name.setText(share.getString("name", ""));
			login_pwd.setText(share.getString("pwd", ""));
			save_pwd.setChecked(share.getBoolean("isCheck", false));
		}
		//判断是否需要更新
		HttpGetVersion(Constant.LOGIN_LOGOADSS.concat(Constant.GETVERSION_NAME),null);
	}

	/***
	 * 用户打开WIFI还是热点 (默认打开热点)
	 */
	private void openWIFI() {
		new AlertDialog.Builder(LoginActivity.this).setTitle(R.string.WIFI)
				.setItems(R.array.wifi, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							if (!manager.isWifiEnabled())
								manager.setWifiEnabled(true);
							showToast(R.string.openWIFI);
						} else {
							// 打开热点
							flag = !flag;
							wifiStatus = open.setWifiApEnabled(manager, flag);
						}
					}
				}).create().show();
	}

	// 登录
	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.login_btn) {
			name = login_name.getText().toString().trim();
			pwd = login_pwd.getText().toString().trim();
			HttpGetLogin(Constant.BATTERY_BASIC_ADDRESS_LOGIN + Constant.BATTERY_BASIC_LOGIN + name + "/" + pwd, null);

		} else {
			selelanguage(LoginActivity.this);
		}
	}

	/***
	 * 登录
	 * 
	 * @param string
	 * @param object
	 */
	private void HttpGetLogin(final String url, final RequestParams params) {
		HttpReustClient.getLogin(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("2", "登陆参数：" + new String(arg2));
				if (arg0 == 200) {
					String str = new String(arg2);
					try {
						JSONObject object = new JSONObject(str);
						nodid = object.getString("userid");
						basic_ip = object.getString("ip");
						LogoUser(object);
						// Constant.BATTERY_BASIC_ADDRESS = basic_ip;
						String imageUri = LogoUser.getInstance().getLogourl();
						// 判断logo是否存在
						if (!new File(Constant.LOGOIMAGE + "/" + imageUri.substring(imageUri.lastIndexOf("/") + 1))
								.exists()) {
							Intent intent = new Intent(LoginActivity.this, LogoServer.class);
							// 标记
							intent.putExtra("flage", "2");
							intent.putExtra("image", Constant.LOGIN_LOGO + object.getString("logourl"));
							startService(intent);
						}
						// 跳转到下一个页面 wifiStatus true 打开的热点 false 打开的是WiFi

						Intent intent2 = new Intent(LoginActivity.this, StationActivity.class);
						intent2.putExtra("wifiStatus", wifiStatus);
						startActivity(intent2);
						finish();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Toast.makeText(LoginActivity.this, R.string.pwserror, 0).show();
				Log.e("tag", "登录失败");
			}
		});
	}

	/*-*
	 * 获取登录返回的信信息
	 */
	private void LogoUser(JSONObject object) throws JSONException {
		LogoUser user = LogoUser.getInstance();
		user.setIp(basic_ip);
		user.setLinkman(object.getString("linkman"));
		user.setLogourl(object.getString("logourl"));
		user.setOrgname(object.getString("orgname"));
		user.setTelnum(object.getString("telnum"));
		user.setWebappurl(object.getString("webappurl"));
		user.setOrgid(object.getString("orgid"));
		user.setIndexpage(object.getString("indexpage"));
		user.setNodeid(object.getString("userid"));
	}

	// 是否记住密码
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		isCheck = isChecked;
		shareXML();
	}

	/**
	 * 保存数据
	 */

	public void shareXML() {
		name = login_name.getText().toString().trim();
		pwd = login_pwd.getText().toString().trim();
		Editor editor = share.edit();
		editor.putString("name", name);
		editor.putString("pwd", pwd);
		editor.putBoolean("isCheck", isCheck);
		editor.commit();
	}

	/**
	 * 手机返回 键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			ExitMenu(LoginActivity.this);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void ExitMenu(Context context) {
		new AlertDialog.Builder(this).setTitle(R.string.ok_exit)
				.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						wifiStatus = open.setWifiApEnabled(manager, false);
						LoginActivity.this.finish();
					}
				}).setNegativeButton(R.string.no, null).show();
	}

	/**
	 * 语言选择
	 */
	private void selelanguage(Context context) {
		new AlertDialog.Builder(context).setTitle(R.string.language_change)
				.setItems(R.array.language, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Locale locale = getResources().getConfiguration().locale;
						if (which == 0) {
							isChinese = 0;
							sp_language.setText("中文");
							setLang(locale.SIMPLIFIED_CHINESE);
						} else if (which == 1) {
							isChinese = 1;
							sp_language.setText("English");
							setLang(locale.US);
						}
					}
				}).show();
	}

	public void setLang(Locale locale) {
		// 获得res资源对象
		Resources resources = getResources();
		// 获得设置对象
		Configuration config = resources.getConfiguration();
		// 获得屏幕参数：主要是分辨率，像素等。
		DisplayMetrics dm = resources.getDisplayMetrics();
		// 语言
		config.locale = locale;
		resources.updateConfiguration(config, dm);
		// 刷新activity才能马上奏效
		finish();
		startActivity(new Intent().setClass(LoginActivity.this, LoginActivity.class));
		// LoginActivity.this.finish();
	}
	/***
	 * 获取版本号 判断程序是否需要更新
	 */
	private void HttpGetVersion(String url, RequestParams params){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, null, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if(arg0==200){
					try {
						JSONObject object = new JSONObject(new String(arg2));
						String version = object.optString("version");
						//获取当前版本号 判断程序是否需要更新
						PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
						String versionName = pi.versionName;
						//更新的内容
						String message = object.optString("remark");
						//更新的文件大小
						String size = object.optString("fsize");
						//获得需要更新的地址
						String url = object.optString("apkurl");
						Log.e("tag", "最新版本为"+version+"  当前版本为"+versionName);
						if(Double.valueOf(version)>Double.valueOf(versionName))
						{
							ShowDialog(url,message);
						}
					} catch (Exception e) {
						
						e.printStackTrace();
					}
				}
			}
			private void ShowDialog(final String url,String message) {
				Log.e("tag", "程序需要更新");
				//弹出dialog是否需要更新
				String[] mess=message.split("。");
				String content = "";
				for (String string : mess) {
					content+=string+"\n";
				}
				new AlertDialog.Builder(context)
				.setTitle(R.string.update)
				.setMessage(content)
				.setPositiveButton(R.string.yes,new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(LoginActivity.this, LogoServer.class);
						//标记
						intent.putExtra("flage","1");
						intent.putExtra("apkurl", Constant.LOGIN_LOGO.concat(url));
						startService(intent);
					}
				})
				.setNegativeButton(R.string.no, null)
				.show();
			}
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				
			}
		});
	}
	@Override
	public View getcontent() {
		return View.inflate(LoginActivity.this, R.layout.activity_main, null);
	}
}
