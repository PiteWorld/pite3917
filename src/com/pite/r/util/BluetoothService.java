package com.pite.r.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.pite.r.R;
import com.pite.r.SendDataActivity;
import com.pite.r.TestDataActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class BluetoothService {
	private Context context = null;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter // 蓝牙适配器
			.getDefaultAdapter();
	private ArrayList<BluetoothDevice> unbondDevices = null; // 用于存放未配对蓝牙设备
	private ArrayList<BluetoothDevice> bondDevices = null;// 用于存放已配对蓝牙设备
	private ListView unbondDevicesListView = null; // 未配对设备
	private ListView bondDevicesListView = null; // 已配对设备
	private String address = null;
	public BluetoothDevice device = null;

	/**
	 * 添加已绑定蓝牙设备到ListView
	 */
	private void addBondDevicesToListView() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int count = this.bondDevices.size();
		System.out.println("已绑定设备数量：" + count);
		for (int i = 0; i < count; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceName", this.bondDevices.get(i).getName());
			data.add(map);// 把item项的数据加到data中
		}
		String[] from = { "deviceName" };
		int[] to = { R.id.device_name };
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, data, R.layout.bonddevice_item, from, to);
		// 把适配器装载到listView中
		this.bondDevicesListView.setAdapter(simpleAdapter);

		this.bondDevicesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				device = bondDevices.get(arg2);
				// Intent intent = new Intent();
				// intent.setClassName(context,
				// "com.example.dayin.PrintDataActivity");
				// intent.putExtra("deviceAddress", device.getAddress());
				// context.startActivity(intent);
				connect();
				// address = device.getAddress();
				// device = bluetoothAdapter.getRemoteDevice(address);
				try {
					outputStream.write(new byte[] { 0x1d, 0x21, 0x11 });
					outputStream.write(new byte[] { 0x1b, 0x45, 0x01 });
					send(TestDataActivity.sb.toString() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * 发送数据
	 */
	private boolean isConnection = false;
	private static BluetoothSocket bluetoothSocket = null;
	private static OutputStream outputStream = null;
	private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	public void send(String sendData) {
		if (this.isConnection) {
			try {
				Log.e("4", "开始打印！！");
				byte[] data = sendData.getBytes("gbk");
				outputStream.write(data, 0, data.length);
				outputStream.flush();
			} catch (IOException e) {
				Log.e("4", e.getMessage());
				searchDevices();
				// Toast.makeText(this.context, "发送失败！",
				// Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this.context, "设备未连接，请重新连接！", Toast.LENGTH_SHORT).show();

		}
	}

	/**
	 * 连接蓝牙设备
	 */
	public boolean connect() {
		if (!this.isConnection) {
			try {
				bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				outputStream = bluetoothSocket.getOutputStream();
				this.isConnection = true;
				if (this.bluetoothAdapter.isDiscovering()) {
					System.out.println("关闭适配器！");
					this.bluetoothAdapter.isDiscovering();
				}
			} catch (Exception e) {
				// Toast.makeText(this.context, "连接失败！", 1).show();
				return false;
			}
			Toast.makeText(this.context, device.getName() + "连接成功！", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return true;
		}
	}

	/**
	 * 添加未绑定蓝牙设备到ListView
	 */
	private void addUnbondDevicesToListView() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int count = this.unbondDevices.size();
		System.out.println("未绑定设备数量：" + count);
		for (int i = 0; i < count; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceName", this.unbondDevices.get(i).getName());
			data.add(map);// 把item项的数据加到data中
		}
		String[] from = { "deviceName" };
		int[] to = { R.id.device_name };
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, data, R.layout.unbonddevice_item, from, to);

		// 把适配器装载到listView中
		this.unbondDevicesListView.setAdapter(simpleAdapter);

		// 为每个item绑定监听，用于设备间的配对
		this.unbondDevicesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				try {
					Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
					createBondMethod.invoke(unbondDevices.get(arg2));
					// 将绑定好的设备添加的已绑定list集合
					bondDevices.add(unbondDevices.get(arg2));
					// 将绑定好的设备从未绑定list集合中移除
					unbondDevices.remove(arg2);
					addBondDevicesToListView();
					addUnbondDevicesToListView();
				} catch (Exception e) {
					Toast.makeText(context, "配对失败！", Toast.LENGTH_SHORT).show();
				}

			}
		});
	}

	public BluetoothService(Context context, ListView unbondDevicesListView, ListView bondDevicesListView) {
		this.context = context;
		this.unbondDevicesListView = unbondDevicesListView;
		this.bondDevicesListView = bondDevicesListView;
		// this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		this.unbondDevices = new ArrayList<BluetoothDevice>();
		this.bondDevices = new ArrayList<BluetoothDevice>();
		this.initIntentFilter();

	}

	private void initIntentFilter() {
		// 设置广播信息过滤
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// 注册广播接收器，接收并处理搜索结果
		context.registerReceiver(receiver, intentFilter);

	}

	/**
	 * 打开蓝牙
	 */
	public void openBluetooth(Activity activity) {
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(enableBtIntent, 1);
		searchDevices();

	}

	/**
	 * 关闭蓝牙
	 */
	public void closeBluetooth() {
		this.bluetoothAdapter.disable();
	}

	/**
	 * 判断蓝牙是否打开
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		return this.bluetoothAdapter.isEnabled();

	}

	/**
	 * 搜索蓝牙设备
	 */
	public void searchDevices() {
		this.bondDevices.clear();
		this.unbondDevices.clear();
		Log.e("tag", "搜寻蓝牙");
		// 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
		this.bluetoothAdapter.startDiscovery();
	}

	/**
	 * 添加未绑定蓝牙设备到list集合
	 * 
	 * @param device
	 */
	public void addUnbondDevices(BluetoothDevice device) {
		System.out.println("未绑定设备名称：" + device.getName());
		if (!this.unbondDevices.contains(device)) {
			this.unbondDevices.add(device);
		}
	}

	/**
	 * 添加已绑定蓝牙设备到list集合
	 * 
	 * @param device
	 */
	public void addBandDevices(BluetoothDevice device) {
		Log.e("tag", "已绑定设备名称：" + device.getName());
		if (!this.bondDevices.contains(device)) {
			this.bondDevices.add(device);
		}
	}

	/**
	 * 蓝牙广播接收器
	 */
	private BroadcastReceiver receiver = new BroadcastReceiver() {

		ProgressDialog progressDialog = null;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Log.e("tag", "action" + action);
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
					addBandDevices(device);
				} else {
					addUnbondDevices(device);
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				progressDialog = ProgressDialog.show(context, "请稍等...", "搜索蓝牙设备中...", true);

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				System.out.println("设备搜索完毕");
				progressDialog.dismiss();

				addUnbondDevicesToListView();
				addBondDevicesToListView();
				// bluetoothAdapter.cancelDiscovery();
			}
			/*
			 * if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) { if
			 * (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
			 * System.out.println("--------打开蓝牙-----------");
			 * bondDevicesListView.setEnabled(true);
			 * unbondDevicesListView.setEnabled(true); } else if
			 * (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
			 * System.out.println("--------关闭蓝牙-----------");
			 * bondDevicesListView.setEnabled(false);
			 * unbondDevicesListView.setEnabled(false); } }
			 */

		}

	};

}