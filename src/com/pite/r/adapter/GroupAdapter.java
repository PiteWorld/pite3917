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

public class GroupAdapter extends BaseAdapter{
	private Context content;
	private List<BatteryGroupUtils> list;

	public GroupAdapter(Context content, List<BatteryGroupUtils> list) {
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
			convertView = View.inflate(content, R.layout.groupitem, null);
			hodler.group_itemTv = (TextView) convertView.findViewById(R.id.group_itemTv);
			hodler.group_itemTv2 = (TextView) convertView.findViewById(R.id.group_itemTv2);
			hodler.group_itemTv3 = (TextView) convertView.findViewById(R.id.group_itemTv3);
			hodler.group_itemTv4 = (TextView) convertView.findViewById(R.id.group_itemTv4);
			hodler.group_itemTv5 = (TextView) convertView.findViewById(R.id.group_itemTv5);
			hodler.group_itemTv6 = (TextView) convertView.findViewById(R.id.group_itemTv6);
			convertView.setTag(hodler);
		}
		else 
			hodler = (ViewHolder) convertView.getTag();
		 /* list.get(i).getNodename(), list.get(i).getGroupname(),
						list.get(i).getgSname() + "", list.get(i).getwSName(), list.get(i).getNetwork() + "",
						list.get(i).getDevice() + "" */
		hodler.group_itemTv.setText(list.get(position).getNodename());
		hodler.group_itemTv2.setText(list.get(position).getGroupname());
		hodler.group_itemTv3.setText(list.get(position).getgSname());
		hodler.group_itemTv3.setTextColor(Color.parseColor("#"+list.get(position).getgScolor()));
		hodler.group_itemTv4.setText(list.get(position).getwSName());
		hodler.group_itemTv4.setTextColor(Color.parseColor("#"+list.get(position).getgScolor()));
		hodler.group_itemTv5.setText(getState(list.get(position).getNetwork()));
		hodler.group_itemTv5.setTextColor(getColor(list.get(position).getNetwork()));
		hodler.group_itemTv6.setText(getState(list.get(position).getDevice()));
		hodler.group_itemTv6.setTextColor(getColor(list.get(position).getDevice()));
		return convertView;
	}

	class ViewHolder {
		private TextView group_itemTv, group_itemTv2, group_itemTv3, group_itemTv4, group_itemTv5,group_itemTv6;
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
