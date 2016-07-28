package com.pite.r.adapter;

import java.util.List;

import com.pite.JsonData.BatteryGroupUtils;
import com.pite.r.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StationAdapter extends BaseAdapter {
	private Context content;
	private List<BatteryGroupUtils> list;

	public StationAdapter(Context content, List<BatteryGroupUtils> list) {
		this.content = content;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder hodler = null;
		if(convertView == null){
			hodler = new ViewHolder();
			convertView = View.inflate(content, R.layout.stationitem, null);
			hodler.station_itemTv = (TextView) convertView.findViewById(R.id.station_itemTv);
			hodler.station_itemTv2 = (TextView) convertView.findViewById(R.id.station_itemTv2);
			hodler.station_itemTv3 = (TextView) convertView.findViewById(R.id.station_itemTv3);
			hodler.station_itemTv4 = (TextView) convertView.findViewById(R.id.station_itemTv4);
			hodler.station_itemTv5 = (TextView) convertView.findViewById(R.id.station_itemTv5);
			convertView.setTag(hodler);
		}
		/*list.get(i).getNodename(),
		list.get(i).getgSname() + "", list.get(i).getwSName(), list.get(i).getNetwork() + "",
		list.get(i).getDevice() + "" */
		else 
			hodler = (ViewHolder) convertView.getTag();
		hodler.station_itemTv.setText(list.get(position).getNodename());
		hodler.station_itemTv2.setText(list.get(position).getgSname());
		hodler.station_itemTv2.setTextColor(Color.parseColor("#"+list.get(position).getgScolor()));
		hodler.station_itemTv3.setText(list.get(position).getwSName());
		hodler.station_itemTv3.setTextColor(Color.parseColor("#"+list.get(position).getwSColor()));
		hodler.station_itemTv4.setText(getState(list.get(position).getNetwork()));
		hodler.station_itemTv4.setTextColor(getColor(list.get(position).getNetwork()));
		hodler.station_itemTv5.setText(getState(list.get(position).getDevice()));
		hodler.station_itemTv5.setTextColor(getColor(list.get(position).getDevice()));
		return convertView;
	}

	class ViewHolder {
		private TextView station_itemTv, station_itemTv2, station_itemTv3, station_itemTv4, station_itemTv5;
	}
	private String getState(int i){
		return i == 0?content.getResources().getString(R.string.cross):content.getResources().getString(R.string.checkmark);
	}
	/**
	 * ÅÐ¶ÏÑÕÉ«
	 * 
	 */
	private int getColor(int i){
		return i ==0?content.getResources().getColor(R.color.lvse):Color.RED;
	}
}
