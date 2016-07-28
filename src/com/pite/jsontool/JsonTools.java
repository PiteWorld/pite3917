package com.pite.jsontool;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import android.util.Log;
/**
 * Json Gson���ݽ���
 */
public class JsonTools {
	public JsonTools() {
	}

	/**
	 * 
	 * @param json
	 *            String ���� ��ʽ����
	 * @param cls
	 * @return
	 */
	public static <T> T getStringJson(String json, Class<T> cls) {
		T t = null;
		try {
			Gson gson = new Gson();
			t = gson.fromJson(json, cls);
		} catch (Exception e) {
			Log.e("2", "���ݽ����쳣��" + e);
		}
		return t;
	}

	/**
	 * 
	 * @param json
	 *            list<T> ���ݸ�ʽ ����
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getListJson(String json, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			JsonArray array = new JsonParser().parse(json).getAsJsonArray();
			for (final JsonElement elem : array) {
				list.add(new Gson().fromJson(elem, cls));
			}
		} catch (Exception e) {
			Log.e("2", "���� �쳣��" + e);
		}
		return list;
	}

	public static <T> List<T> getlist(String json, Class<T> cls) {
		List<T> t = new ArrayList<>();
		try {
			Gson gson = new Gson();
			t = gson.fromJson(json, new TypeToken<List<T>>() {
			}.getType());
		} catch (Exception e) {
			Log.e("2", "���� �쳣��" + e);
		}
		return t;
	}
}
