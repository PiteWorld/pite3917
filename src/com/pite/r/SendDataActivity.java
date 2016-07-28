package com.pite.r;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.JsonData.BatteryExterior;
import com.pite.JsonData.BatteryType;
import com.pite.JsonData.Batterys;
import com.pite.JsonData.JsonData;
import com.pite.jsontool.JsonTools;
import com.pite.r.adapter.Myadapter;
import com.pite.r.constant.Constant;
import com.pite.r.http.HttpReustClient;
import com.pite.r.prol.APiteData;
import com.pite.r.prol.TestRProl;
import com.pite.r.util.Data;
import com.pite.r.util.Data3919Utils;
import com.pite.r.util.HostUtils;
import com.pite.r.util.Pite3915Up2923;
import com.pite.r.util.Pite3919Up;
import com.pite.r.util.ProUtils;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class SendDataActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	private TextView send_ID, send_batteryNum, send_Rated_voltage, send_Rated_capacity, send_Warehouse_number,
			send_Zone_number, send_Shelf_number, send_long, send_width, send_height;
	private Button send;
	private List<JsonData> jsonData = null;
	private List<BatteryExterior> batteryExterior = null;
	private TestRProl prol = null;
	private Myadapter adapter;
	private byte[] bt;
	private ListView send_lv;
	private String str;
	private List<String[]> data = null;
	private Pite3915Up2923 up2923 = null;
	private Pite3919Up up3919 = null;
	private List<Pite3915Up2923> upList;
	private List<Pite3919Up> up3919Llist;
	private List<Data> listData;
	private boolean upStatus = false; // 上传状态
	private List<Data3919Utils> data3919 = null;
	private int flags = -1;
	private String datajson = null;
	private List<String> listAdapter;// sp适配器
	private List<String> listAdapterType;// spType适配器
	public List<Batterys> batterys = null;// 电池厂家
	private List<BatteryType> batteryType = null;
	private String typeid, orgid;// 电池类型id 和工厂ID
	private Spinner send_spType, send_sp;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				is3915or3919();
				break;
			}

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.string0));
		upList = new ArrayList<Pite3915Up2923>();
		listData = new ArrayList<Data>();
		up2923 = new Pite3915Up2923();
		data3919 = new ArrayList<Data3919Utils>();
		up3919Llist = new ArrayList<Pite3919Up>();
		up3919 = new Pite3919Up();
		listAdapter = new ArrayList<String>();
		listAdapterType = new ArrayList<String>();
		getBatterys(Constant.GETFACTORYINFO, null);
		HostUtils.testData = this;
		// 绑定窗口
		init();
		send_lv.setOnItemClickListener(this);
		data = new ArrayList<String[]>();
	}

	private void init() {
		send_ID = (TextView) findViewById(R.id.send_ID);
		send_batteryNum = (TextView) findViewById(R.id.send_batteryNum);
		send_Rated_voltage = (TextView) findViewById(R.id.send_Rated_voltage);
		send_Rated_capacity = (TextView) findViewById(R.id.send_Rated_capacity);
		send_Warehouse_number = (TextView) findViewById(R.id.send_Warehouse_number);
		send_Zone_number = (TextView) findViewById(R.id.send_Zone_number);
		send_Shelf_number = (TextView) findViewById(R.id.send_Shelf_number);
		send_long = (TextView) findViewById(R.id.send_long);
		send_width = (TextView) findViewById(R.id.send_width);
		send_height = (TextView) findViewById(R.id.send_height);
		send_lv = (ListView) findViewById(R.id.send_lv);
		send = (Button) findViewById(R.id.send);
		send_spType = (Spinner) findViewById(R.id.send_spType);
		send_sp = (Spinner) findViewById(R.id.send_sp);
		send.setOnClickListener(this);
		HostUtils.testData = (SendDataActivity) this;
		jsonData = MainRActivity.jsonData;
		batteryExterior = MainRActivity.batteryExterior;
		if (jsonData != null && batteryExterior != null) {
			setText();
		}
		send_sp.setOnItemSelectedListener(new OnItemSelectedListener() { // 电池厂家

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView v1 = (TextView) view;
				v1.setTextColor(Color.BLACK); // 可以随意设置自己要的颜色值
				typeid = batterys.get(position).getOrgid() + "";
				Log.e("tag", typeid + "  typeid");
				getBatterytype(Constant.GETBATTERYFACTORYTYPE + "/" + typeid, null);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		send_spType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				TextView v1 = (TextView) view;
				v1.setTextColor(Color.BLACK); // 可以随意设置自己要的颜色值
				orgid = batteryType.get(position).getTypeid() + "";
				Log.e("tag", orgid + "  orgid");
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
	}

	/**
	 * 获取基本数据
	 */
	private void setText() {
		send_ID.setText(jsonData.get(0).getTypeid() + "");
		send_batteryNum.setText("");
		send_Rated_voltage.setText(jsonData.get(0).getNominalv() + "");
		send_Rated_capacity.setText(jsonData.get(0).getNominalca() + "");
		send_Warehouse_number.setText(batteryExterior.get(0).getWid() + "");
		send_Zone_number.setText(batteryExterior.get(0).getWaid() + "");
		send_Shelf_number.setText(batteryExterior.get(0).getLocatorid() + "");
		send_long.setText(batteryExterior.get(0).getWLength() + "");
		send_width.setText(batteryExterior.get(0).getWWidth() + "");
		send_height.setText(batteryExterior.get(0).getWHeigth() + "");
	}

	public void setHandUpData(int index, byte[] bt, TestRProl prol) {
		this.bt = bt;
		this.prol = prol;
		handler.sendEmptyMessage(index);
	}

	@Override
	public View getcontent() {
		return View.inflate(SendDataActivity.this, R.layout.activity_senddata, null);
	}

	// 发送按钮点击
	@Override
	public void onClick(View v) {
		if (flags == -1 || upStatus) {
			showToast(R.string.string25);
			return;
		}
		if (flags == 3915) {
			byte[] testTime = prol.get3915Header().CreateTime;
			up2923.setTestTime(ProUtils.getTestTime(testTime));
			up2923.setSucess(Boolean.TRUE);
			up2923.setFlags(flags + "");
			up2923.setStation(StationActivity.nodeid + "");
			up2923.setGroup(GroupActivity.groupID);
			up2923.setBttyType(orgid);
			up2923.setLocatorid(send_Shelf_number.getText().toString().trim());
			up2923.setOrgid(typeid);
			up2923.setApptype("0");
			up2923.setData(listData);
			upList.add(up2923);
			Gson gson = new Gson();
			Type type = new TypeToken<List<Pite3915Up2923>>() {
			}.getType();
			datajson = gson.toJson(upList, type);
		} else {
			byte[] testTime = prol.get3915Header().CreateTime;
			up3919.setTestTime(ProUtils.getTestTime(testTime));
			up3919.setSucess(Boolean.TRUE);
			up3919.setFlags(flags + "");
			up3919.setStation(StationActivity.nodeid + "");
			up3919.setGroup(GroupActivity.groupID);
			up3919.setBttyType(orgid);
			up3919.setLocatorid(send_Shelf_number.getText().toString().trim());
			up3919.setOrgid(typeid);
			up3919.setApptype("1");
			up3919.setData(data3919);
			up3919Llist.add(up3919);
			Gson gson = new Gson();
			Type type = new TypeToken<List<Pite3919Up>>() {
			}.getType();
			datajson = gson.toJson(up3919Llist, type);
		}
		RequestParams params = new RequestParams();
		params.put("data", datajson);
		upLoadData(Constant.BATTERY_UPLOAD_DATA, params);
		Log.e("5", "上传的数据为：" + datajson);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	// 判断数据结构
	private void is3915or3919() {
		data.clear();
		flags = prol.get3915Header().Equipment;
		if (flags == 3915) {
			listData.clear();
			for (int i = 0; i < prol.getPite3915Data().size(); i++) {
				for (int j = 0; j < prol.getPite3915Data().get(i).size(); j++) {
					listData.add(((APiteData) prol.getPite3915Data().get(i).get(j)).getData());
				}
			}
			for (int i = 0; i < listData.size(); i++) {
				String[] str = new String[] { listData.get(i).getBatteryNum(), listData.get(i).getBatteryV(),
						listData.get(i).getBatteryR1(), listData.get(i).getStandVolt(), listData.get(i).getStandR(),
						listData.get(i).getBatteryCap(), listData.get(i).getStatuss() };
				data.add(str);
			}
			upStatus = false;
			adapter = new Myadapter(SendDataActivity.this, data);
			send_lv.setAdapter(adapter);
		} else {
			data3919.clear();
			for (int i = 0; i < prol.getPite3915Data().size(); i++) {
				for (int j = 0; j < prol.getPite3915Data().get(i).size(); j++) {
					data3919.add(((APiteData) prol.getPite3915Data().get(i).get(j)).getData3919());
				}
			}
			for (int j = 0; j < data3919.size(); j++) {
				String[] str = new String[] { data3919.get(j).getBatteryNum(), data3919.get(j).getBatteryV(),
						data3919.get(j).getBatteryR(), data3919.get(j).getBatteryCap(), data3919.get(j).getBatteryR2(),
						data3919.get(j).getBatteryC2(), data3919.get(j).getStatuss() };
				data.add(str);
				Log.e("2", "R2的值：：" + data3919.get(j).getBatteryR2());
			}
			upStatus = false;
			adapter = new Myadapter(SendDataActivity.this, data);
			send_lv.setAdapter(adapter);
		}
	}

	/**
	 * getBatteryExterior 电池厂家
	 */
	private void getBatterys(String url, RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg2.length > 5) {
					batterys = JsonTools.getListJson(new String(arg2), Batterys.class);
					listAdapter.clear();

					for (int i = 0; i < batterys.size(); i++) {
						listAdapter.add(batterys.get(i).getCn_name());
					}
					ArrayAdapter<String> adap = new ArrayAdapter<String>(SendDataActivity.this,
							android.R.layout.simple_list_item_1, listAdapter);
					adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					send_sp.setAdapter(adap);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {

			}
		});
	}

	/***
	 * 电池类型getBatterytype
	 */
	private void getBatterytype(String url, RequestParams params) {
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(url, params, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("4", "电池型号：" + arg0 + new String(arg2));
				if (arg2.length > 2) {
					batteryType = JsonTools.getListJson(new String(arg2), BatteryType.class);
					listAdapterType.clear();
					Log.e("4", "电池型号：" + batteryType.size());
					for (int i = 0; i < batteryType.size(); i++) {
						listAdapterType.add(batteryType.get(i).getTypename());
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(SendDataActivity.this,
							android.R.layout.simple_list_item_1, listAdapterType);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
					send_spType.setAdapter(adapter);
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Log.e("4", "电池型号请求失败：");
			}
		});
	}

	/***
	 * 上传数据
	 */
	private void upLoadData(String url, RequestParams params) {
		HttpReustClient.post(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				Log.e("5", "上传结果：" + arg0 + "   " + new String(arg2));
				if (arg0 == 200) {
					showToast(R.string.string26);
					upStatus = true;
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				Log.e("5", "上传失败：" + arg0 + arg3);
			}
		});
	}
}
