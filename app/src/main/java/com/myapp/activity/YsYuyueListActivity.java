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
import com.myapp.adapter.YsYuyueListAdapter;
import com.myapp.adapter.YsYuyueListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Keshi;
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

public class YsYuyueListActivity extends FinalActivity {

	List<Yuyue> mListViewData = new ArrayList<Yuyue>();

	List<Yuyue> yuyuelist1;

	@ViewInject(id = R.id.yuyuelist, itemClick = "ItemClick")
	ListView listView;

	private YsYuyueListAdapter adapter;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_yuyue_list);

		yuyuelist1 = new ArrayList<Yuyue>();

		adapter = new YsYuyueListAdapter(YsYuyueListActivity.this, yuyuelist1);

		listView.setAdapter(adapter);

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				Intent intent = new Intent();

				intent.putExtra("yuyueid", vHollder.id.getText());

				intent.setClass(YsYuyueListActivity.this,
						YsYuyueDetailActivity.class);

				startActivity(intent);
			}
		});

	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "ys_yiquerenlist");

		ajaxParams.put("ysid", String.valueOf(myApp.getYisheng().getId()));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "/YuyueService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							yuyuelist1.clear();

							if (result.length() > 0) {

								List<Yuyue> yuyueList = new ArrayList<Yuyue>();

								yuyueList = JSONObject.parseArray(result,
										Yuyue.class);

								yuyuelist1.addAll(yuyueList);

							}
							adapter.notifyDataSetChanged();
						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 子页面关闭时调用
	@Override
	protected void onResume() {

		super.onResume();

		handler1.post(runnable);
	}

	private Runnable runnable = new Runnable() {

		public void run() {
			// 做操作
			handler1.sendEmptyMessage(1);
		}
	};

	private Handler handler1 = new Handler() {

		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 1:

				initData();

				break;
			}
		};
	};
}
