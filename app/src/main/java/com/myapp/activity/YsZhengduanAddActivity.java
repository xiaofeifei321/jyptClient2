package com.myapp.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.GoumaiyaopinListAdapter;
import com.myapp.adapter.YaopinListAdapter;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goumaiyaoping;
import com.myweb.domain.Yaoping;
import com.myweb.domain.Yisheng;
import com.myweb.domain.Yuyue;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class YsZhengduanAddActivity extends FinalActivity {

	@ViewInject(id = R.id.guahaofei)
	private TextView guahaofei;

	@ViewInject(id = R.id.kanbinfei)
	private EditText kanbinfei;

	@ViewInject(id = R.id.yaofei)
	private TextView yaofei;

	@ViewInject(id = R.id.zhengduan)
	private EditText zhengduan;

	@ViewInject(id = R.id.quedingBtn, click = "quedingBtnClick")
	private Button quedingBtn;

	@ViewInject(id = R.id.xuanzeyaopingBtn, click = "xuanzeyaopingBtnClick")
	private Button xuanzeyaopingBtn;

	MyApplication myApp;

	String yuyueid = "";

	List<Goumaiyaoping> goumaiyaopinglist1;

	@ViewInject(id = R.id.yaoping_list)
	ListView listView;

	private GoumaiyaopinListAdapter adapter;

	public void xuanzeyaopingBtnClick(View v) {

		Intent intent = new Intent();

		intent.putExtra("yuyueid", yuyueid);

		intent.setClass(YsZhengduanAddActivity.this,
				XuanzeYaopingListActivity.class);

		// 启动Activity
		startActivity(intent);

	}

	public void quedingBtnClick(View v) {

		if (kanbinfei.getText().toString().length() < 1) {
			Toast.makeText(getApplicationContext(), "看病费不能为空!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		if (zhengduan.getText().toString().length() < 1) {
			Toast.makeText(getApplicationContext(), "诊断结果不能为空!",
					Toast.LENGTH_SHORT).show();
			return;
		}

		Yuyue yuyue = new Yuyue();

		yuyue.setId(Integer.valueOf(yuyueid));

		yuyue.setState("已诊断");

		yuyue.setKanbingfei(kanbinfei.getText().toString());

		yuyue.setYaofei(Double.parseDouble(yaofei.getText().toString()));

		yuyue.setJiuzhengjieguo(zhengduan.getText().toString());

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "zhengduan");

		ajaxParams.put("yuyue", JSONObject.toJSONString(yuyue));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					Toast.makeText(getApplicationContext(), "操作成功!",
							Toast.LENGTH_SHORT).show();
                      
					finish();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_zhengduan_add);

		Intent intent = getIntent();

		yuyueid = intent.getStringExtra("yuyueid");

		myApp = (MyApplication) getApplication();

		goumaiyaopinglist1 = new ArrayList<Goumaiyaoping>();

		adapter = new GoumaiyaopinListAdapter(YsZhengduanAddActivity.this,
				goumaiyaopinglist1);

		listView.setAdapter(adapter);

		// initData();

	}

	public void DeleteItem(int id) {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "delete");

		ajaxParams.put("goumaiyaopingid", String.valueOf(id));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoumaiyaopingService",
					ajaxParams,

					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							initData();
						}
					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "view");

		ajaxParams.put("yuyueid", yuyueid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					if (result.length() > 0) {

						Yuyue yuyue = JSONObject.parseObject(result,
								Yuyue.class);
						guahaofei.setText(yuyue.getGuahaofei());

						yaofei.setText(String.valueOf(yuyue.getYaofei()));

						getYaopingList();

					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getYaopingList() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "list");

		ajaxParams.put("yuyueid", yuyueid);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoumaiyaopingService",
					ajaxParams,

					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							goumaiyaopinglist1.clear();

							if (result.length() > 0) {

								List<Goumaiyaoping> goumaiyaopingList = new ArrayList<Goumaiyaoping>();

								goumaiyaopingList = JSONObject.parseArray(
										result, Goumaiyaoping.class);

								goumaiyaopinglist1.addAll(goumaiyaopingList);

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
