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
import com.myapp.adapter.GoumaiyaopinlishiListAdapter;
import com.myapp.common.HttpUtil;
import com.myweb.domain.Goumaiyaoping;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BrYuyueDetailActivity extends FinalActivity {

	@ViewInject(id = R.id.quedingBtn, click = "quedingBtnClick")
	private Button quedingBtn;

	
	@ViewInject(id = R.id.zffssp)
	private Spinner zffssp;
	
	@ViewInject(id = R.id.keshi)
	private TextView keshi;

	@ViewInject(id = R.id.yishengxingming)
	private TextView yishengxingming;

	@ViewInject(id = R.id.state)
	private TextView state;

	@ViewInject(id = R.id.bingrenxinming)
	private TextView bingrenxinming;

	@ViewInject(id = R.id.lianxidianhua)
	private TextView lianxidianhua;

	@ViewInject(id = R.id.guahaofei)
	private TextView guahaofei;

	@ViewInject(id = R.id.dangqianzhuangtai)
	private TextView dangqianzhuangtai;

	@ViewInject(id = R.id.jiuzhengshijian)
	private TextView jiuzhengshijian;

	@ViewInject(id = R.id.kanbingfei)
	private TextView kanbingfei;

	@ViewInject(id = R.id.yaofei)
	private TextView yaofei;

	@ViewInject(id = R.id.jiuzhenjieguo)
	private TextView jiuzhenjieguo;

	@ViewInject(id = R.id.hejifeiyong)
	private TextView hejifeiyong;

	MyApplication myApp;

	String id = "";

	List<Goumaiyaoping> goumaiyaopinglist1;

	private GoumaiyaopinlishiListAdapter adapter;

	@ViewInject(id = R.id.yaoping_list)
	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_br_yuyue_detail);

		Intent intent = getIntent();

		id = intent.getStringExtra("id");

		myApp = (MyApplication) getApplication();

		goumaiyaopinglist1 = new ArrayList<Goumaiyaoping>();

		adapter = new GoumaiyaopinlishiListAdapter(
				BrYuyueDetailActivity.this, goumaiyaopinglist1);

		listView.setAdapter(adapter);

		initData();
	}

	public void quedingBtnClick(View v) {

		 
		Yuyue yuyue = new Yuyue();

		yuyue.setId(Integer.valueOf(id));

		yuyue.setState("已结算");

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "fukuan");

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

	
	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "view");

		ajaxParams.put("yuyueid", id);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					if (result.length() > 0) {

						Yuyue yuyue = JSONObject.parseObject(result,
								Yuyue.class);

						keshi.setText(yuyue.getKeshimingcheng());

						yishengxingming.setText(yuyue.getYishengxingming());

						state.setText(yuyue.getState());
						
						if(yuyue.getState().equals("已结算")||yuyue.getState().equals("等待挂号"))
						{
							quedingBtn.setVisibility(View.GONE);
							
							zffssp.setVisibility(View.GONE);
						}
						else
						{
							quedingBtn.setVisibility(View.VISIBLE);
							
							zffssp.setVisibility(View.VISIBLE);
						}

						bingrenxinming.setText(yuyue.getUsername());

						lianxidianhua.setText(yuyue.getTel());

						guahaofei.setText(String.valueOf(yuyue.getGuahaofei()));

						jiuzhengshijian.setText(String.valueOf(yuyue
								.getJiuzhengshijian()));

						dangqianzhuangtai.setText(yuyue.getState());

						kanbingfei.setText(String.valueOf(yuyue.getKanbingfei()));

						yaofei.setText(String.valueOf(yuyue.getYaofei()));

						hejifeiyong.setText(String.valueOf(Double.valueOf(yuyue
								.getGuahaofei())
								+ Double.valueOf(yuyue.getKanbingfei())
								+ Double.valueOf(yuyue.getYaofei())));

						jiuzhenjieguo.setText(yuyue.getJiuzhengjieguo());

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

		ajaxParams.put("yuyueid", id);

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
}
