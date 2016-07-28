package com.pite.r.http;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.pite.r.constant.Constant;

import android.util.Log;

/**
 * android-async-http框架 client reust
 */
public class HttpReustClient {

	private static AsyncHttpClient client = new AsyncHttpClient();

	public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(5000);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}
	public static void getLogin(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(5000);
		client.get(url, params, responseHandler);
	}

	public static void gets(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(5000);
		client.get(getAbsoluteUrl(url), params, responseHandler);
	}

	public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
		client.setTimeout(10000);
		client.post(getAbsoluteUrl(url), params, responseHandler);
	}

	private static String getAbsoluteUrl(String relativeUrl) {
		Log.e("2", "返回基本地址："+ Constant.BATTERY_BASIC_ADDRESS_LOGINs+"rest/"+relativeUrl);
		return Constant.BATTERY_BASIC_ADDRESS_LOGINs+"rest/"+ relativeUrl;
	}
}
