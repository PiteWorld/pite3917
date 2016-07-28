package com.pite.r;

import java.util.List;

import org.apache.http.Header;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.JsonData.BatteryGroupUtils;
import com.pite.jsontool.JsonTools;
import com.pite.r.adapter.GroupAdapter;
import com.pite.r.constant.Constant;
import com.pite.r.http.HttpReustClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class GroupActivity extends BaseActivity implements OnItemClickListener {
	private ListView group_lv;
	private List<BatteryGroupUtils> list;
	private GroupAdapter adapter;
	public static String groupID ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.string20));
		group_lv = (ListView) findViewById(R.id.group_lv);
		group_lv.setOnItemClickListener(this);
		String str = null;
		if (LoginActivity.isChinese == 0) {
			str = "chinese";
		} else {
			str = "english";
		}
		HttpGetData(Constant.BATTERY_PACKET+"/" + StationActivity.nodeid + "/" + str, null); // ÍøÂçÇëÇó
	}

	/**
	 * ÍøÂçGet ÇëÇó
	 * 
	 * @param url
	 * @param params
	 */
	public void HttpGetData(final String url, final RequestParams params) {
		HttpReustClient.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				if (arg0 == 200) {
					if (arg2 != null && arg2.length > 1) {
						list = JsonTools.getListJson(new String(arg2), BatteryGroupUtils.class);
						adapter = new GroupAdapter(GroupActivity.this, list);
						group_lv.setAdapter(adapter);
					} else {
						Toast.makeText(GroupActivity.this, R.string.resqustFails, Toast.LENGTH_SHORT).show();
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
			}
		});
	}

	@Override
	public View getcontent() {
		return View.inflate(GroupActivity.this, R.layout.activity_group, null);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		groupID = list.get(position).getGroupID()+"";
		startActivity(new Intent(GroupActivity.this, MainRActivity.class));
	}

}
