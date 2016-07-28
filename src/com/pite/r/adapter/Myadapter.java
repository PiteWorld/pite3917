package com.pite.r.adapter;

import java.util.List;

import com.pite.r.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Myadapter extends BaseAdapter {
	/**
	 *   ≈‰∆˜
	 */
	private Context content;
	private List<String[]> listdata;

	public Myadapter(Context content, List<String[]> listdata) {
		this.content = content;
		this.listdata = listdata;
	}

	@Override
	public int getCount() {
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = View.inflate(content, R.layout.test_lv, null);
			holder.test_lv_number = (TextView) convertView.findViewById(R.id.test_lv_number);
			holder.test_voltage = (TextView) convertView.findViewById(R.id.test_voltage);
			holder.test_internal = (TextView) convertView.findViewById(R.id.test_internal);
			holder.test_C = (TextView) convertView.findViewById(R.id.test_C);
			holder.test_R2 = (TextView) convertView.findViewById(R.id.test_R2);
			holder.test_C2 = (TextView) convertView.findViewById(R.id.test_C2);
			holder.test_hege = (TextView) convertView.findViewById(R.id.test_hege);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.test_lv_number.setText(listdata.get(position)[0]);
		holder.test_voltage.setText(listdata.get(position)[1]);
		holder.test_internal.setText(listdata.get(position)[2]);
		holder.test_C.setText(listdata.get(position)[3]);
		holder.test_R2.setText(listdata.get(position)[4]);
		holder.test_C2.setText(listdata.get(position)[5]);
		holder.test_hege.setText((listdata.get(position)[6].equals("0") || equals("1") || equals("2")) ? " «" : "∑Ò");

		return convertView;
	}

	class ViewHolder {
		private TextView test_lv_number, test_voltage, test_internal, test_C, test_R2, test_C2, test_hege;
	}
}
