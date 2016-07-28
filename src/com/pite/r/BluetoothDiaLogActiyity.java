package com.pite.r;

import com.pite.r.util.BluetoothService;
import com.pite.r.util.HostUtils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class BluetoothDiaLogActiyity extends Activity {
	// δ����豸 ��������豸
	private ListView unbondDevices, bondDevices;
	private Context context = null;
	private boolean isConnect = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		setTitle("������ӡ");
		context = this;
		unbondDevices = (ListView) findViewById(R.id.unbondDevices);
		bondDevices = (ListView) findViewById(R.id.bondDevices);
		BluetoothService bs = new BluetoothService(context, unbondDevices, bondDevices);
		HostUtils.bs = bs;
		// �ж������Ƿ��
		// if (!bs.isOpen()) {
		bs.openBluetooth(BluetoothDiaLogActiyity.this);
		// bs.searchDevices();// ��Ѱ����
		// }

	}
}
