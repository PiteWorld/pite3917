package com.pite.r;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

public abstract class BaseActivity extends Activity {

	private ImageButton base_image;
	private TextView base_title;
	private LinearLayout base_content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
		initData();
	}
	private void initData() {
		base_image = (ImageButton) findViewById(R.id.base_image);
		base_title = (TextView) findViewById(R.id.base_title);
		base_content = (LinearLayout) findViewById(R.id.base_content);
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		base_content.addView(getcontent(), params);
		base_image.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	public abstract View getcontent();
	/**
	 * 更换标题的方法
	 */
	public void setTitle(String title){
		base_title.setText(title);
	}
	/**
	 * 返回图标显示和隐藏的方法
	 */
	public void setVisibility(int visibility){
		base_image.setVisibility(visibility);
	}
	/**
	 * set图标
	 * @param title
	 */
	public void setBitmap(Bitmap map){
		base_image.setImageBitmap(map);
		base_image.setClickable(false);
	}
	public void showToast(int title){
		Toast.makeText(BaseActivity.this, title, Toast.LENGTH_SHORT).show();
	}
}
