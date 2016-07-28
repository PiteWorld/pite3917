package com.pite.r;

import com.pite.r.util.BluetoothService;
import com.pite.r.util.HostUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class BluetoothDiaLogActiyity extends Activity {
	// 未配对设备 和已配对设备
	private ListView unbondDevices, bondDevices;
	private Context context = null;
	private boolean isConnect = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		setTitle("蓝牙打印");
		context = this;
		unbondDevices = (ListView) findViewById(R.id.unbondDevices);
		bondDevices = (ListView) findViewById(R.id.bondDevices);
		BluetoothService bs = new BluetoothService(context, unbondDevices, bondDevices);
		HostUtils.bs = bs;
		// 判断蓝牙是否打开
		// if (!bs.isOpen()) {
		bs.openBluetooth(BluetoothDiaLogActiyity.this);
		// bs.searchDevices();// 搜寻蓝牙
		// }

	}
}
