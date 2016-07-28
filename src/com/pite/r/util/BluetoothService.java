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
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter // ����������
			.getDefaultAdapter();
	private ArrayList<BluetoothDevice> unbondDevices = null; // ���ڴ��δ��������豸
	private ArrayList<BluetoothDevice> bondDevices = null;// ���ڴ������������豸
	private ListView unbondDevicesListView = null; // δ����豸
	private ListView bondDevicesListView = null; // ������豸
	private String address = null;
	public BluetoothDevice device = null;

	/**
	 * �����Ѱ������豸��ListView
	 */
	private void addBondDevicesToListView() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int count = this.bondDevices.size();
		System.out.println("�Ѱ��豸������" + count);
		for (int i = 0; i < count; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceName", this.bondDevices.get(i).getName());
			data.add(map);// ��item������ݼӵ�data��
		}
		String[] from = { "deviceName" };
		int[] to = { R.id.device_name };
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, data, R.layout.bonddevice_item, from, to);
		// ��������װ�ص�listView��
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
	 * ��������
	 */
	private boolean isConnection = false;
	private static BluetoothSocket bluetoothSocket = null;
	private static OutputStream outputStream = null;
	private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

	public void send(String sendData) {
		if (this.isConnection) {
			try {
				Log.e("4", "��ʼ��ӡ����");
				byte[] data = sendData.getBytes("gbk");
				outputStream.write(data, 0, data.length);
				outputStream.flush();
			} catch (IOException e) {
				Log.e("4", e.getMessage());
				searchDevices();
				// Toast.makeText(this.context, "����ʧ�ܣ�",
				// Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this.context, "�豸δ���ӣ����������ӣ�", Toast.LENGTH_SHORT).show();

		}
	}

	/**
	 * ���������豸
	 */
	public boolean connect() {
		if (!this.isConnection) {
			try {
				bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid);
				bluetoothSocket.connect();
				outputStream = bluetoothSocket.getOutputStream();
				this.isConnection = true;
				if (this.bluetoothAdapter.isDiscovering()) {
					System.out.println("�ر���������");
					this.bluetoothAdapter.isDiscovering();
				}
			} catch (Exception e) {
				// Toast.makeText(this.context, "����ʧ�ܣ�", 1).show();
				return false;
			}
			Toast.makeText(this.context, device.getName() + "���ӳɹ���", Toast.LENGTH_SHORT).show();
			return true;
		} else {
			return true;
		}
	}

	/**
	 * ����δ�������豸��ListView
	 */
	private void addUnbondDevicesToListView() {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		int count = this.unbondDevices.size();
		System.out.println("δ���豸������" + count);
		for (int i = 0; i < count; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deviceName", this.unbondDevices.get(i).getName());
			data.add(map);// ��item������ݼӵ�data��
		}
		String[] from = { "deviceName" };
		int[] to = { R.id.device_name };
		SimpleAdapter simpleAdapter = new SimpleAdapter(this.context, data, R.layout.unbonddevice_item, from, to);

		// ��������װ�ص�listView��
		this.unbondDevicesListView.setAdapter(simpleAdapter);

		// Ϊÿ��item�󶨼����������豸������
		this.unbondDevicesListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				try {
					Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
					createBondMethod.invoke(unbondDevices.get(arg2));
					// ���󶨺õ��豸���ӵ��Ѱ�list����
					bondDevices.add(unbondDevices.get(arg2));
					// ���󶨺õ��豸��δ��list�������Ƴ�
					unbondDevices.remove(arg2);
					addBondDevicesToListView();
					addUnbondDevicesToListView();
				} catch (Exception e) {
					Toast.makeText(context, "���ʧ�ܣ�", Toast.LENGTH_SHORT).show();
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
		// ���ù㲥��Ϣ����
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// ע��㲥�����������ղ������������
		context.registerReceiver(receiver, intentFilter);

	}

	/**
	 * ������
	 */
	public void openBluetooth(Activity activity) {
		Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		activity.startActivityForResult(enableBtIntent, 1);
		searchDevices();

	}

	/**
	 * �ر�����
	 */
	public void closeBluetooth() {
		this.bluetoothAdapter.disable();
	}

	/**
	 * �ж������Ƿ��
	 * 
	 * @return boolean
	 */
	public boolean isOpen() {
		return this.bluetoothAdapter.isEnabled();

	}

	/**
	 * ���������豸
	 */
	public void searchDevices() {
		this.bondDevices.clear();
		this.unbondDevices.clear();
		Log.e("tag", "��Ѱ����");
		// Ѱ�������豸��android�Ὣ���ҵ����豸�Թ㲥��ʽ����ȥ
		this.bluetoothAdapter.startDiscovery();
	}

	/**
	 * ����δ�������豸��list����
	 * 
	 * @param device
	 */
	public void addUnbondDevices(BluetoothDevice device) {
		System.out.println("δ���豸���ƣ�" + device.getName());
		if (!this.unbondDevices.contains(device)) {
			this.unbondDevices.add(device);
		}
	}

	/**
	 * �����Ѱ������豸��list����
	 * 
	 * @param device
	 */
	public void addBandDevices(BluetoothDevice device) {
		Log.e("tag", "�Ѱ��豸���ƣ�" + device.getName());
		if (!this.bondDevices.contains(device)) {
			this.bondDevices.add(device);
		}
	}

	/**
	 * �����㲥������
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
				progressDialog = ProgressDialog.show(context, "���Ե�...", "���������豸��...", true);

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				System.out.println("�豸�������");
				progressDialog.dismiss();

				addUnbondDevicesToListView();
				addBondDevicesToListView();
				// bluetoothAdapter.cancelDiscovery();
			}
			/*
			 * if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) { if
			 * (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
			 * System.out.println("--------������-----------");
			 * bondDevicesListView.setEnabled(true);
			 * unbondDevicesListView.setEnabled(true); } else if
			 * (bluetoothAdapter.getState() == BluetoothAdapter.STATE_OFF) {
			 * System.out.println("--------�ر�����-----------");
			 * bondDevicesListView.setEnabled(false);
			 * unbondDevicesListView.setEnabled(false); } }
			 */

		}

	};

}