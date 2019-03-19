package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;

import com.myapp.adapter.YaopinListAdapter;

import com.myapp.common.HttpUtil;

import com.myweb.domain.Keshi;
import com.myweb.domain.Yaoping;
import com.myweb.domain.Yuyue;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class YaopingListActivity extends FinalActivity {

	 
	List<Yaoping> yaopinglist1;

	@ViewInject(id = R.id.yuyuelist, itemClick = "ItemClick")
	ListView listView;

	private YaopinListAdapter adapter;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_yaoping_list);

		yaopinglist1 = new ArrayList<Yaoping>();

		adapter = new YaopinListAdapter(YaopingListActivity.this, yaopinglist1);

		listView.setAdapter(adapter);

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp

		initData();

	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "list");

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YaopingService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							yaopinglist1.clear();

							if (result.length() > 0) {

								List<Yaoping> yaopingList = new ArrayList<Yaoping>();

								yaopingList = JSONObject.parseArray(result,
										Yaoping.class);

								yaopinglist1.addAll(yaopingList);

							}

							adapter.notifyDataSetChanged();

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
