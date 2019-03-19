package com.myapp.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.myapp.common.HttpUtil;
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
import android.widget.TextView;
import android.widget.Toast;

public class YsYuyueDetailActivity extends FinalActivity {

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

	@ViewInject(id = R.id.zhengduanBtn, click = "zhengduanBtnClick")
	private Button zhengduanBtn;

	MyApplication myApp;

	String yuyueid = "";

	
	public void zhengduanBtnClick(View v) {
		Intent intent = new Intent();
		
		intent.putExtra("yuyueid", yuyueid);
		
		intent.putExtra("guahaofei", guahaofei.getText().toString());

		intent.setClass(YsYuyueDetailActivity.this, YsZhengduanAddActivity.class);

		// 启动Activity
		startActivity(intent);

	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_yuyue_detail);

		Intent intent = getIntent();

		yuyueid = intent.getStringExtra("yuyueid");

		myApp = (MyApplication) getApplication();

		 
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

						bingrenxinming.setText(yuyue.getUsername());

						lianxidianhua.setText(yuyue.getTel());

						guahaofei.setText(String.valueOf(yuyue.getGuahaofei()));

						jiuzhengshijian.setText(String.valueOf(yuyue
								.getJiuzhengshijian()));
						
						dangqianzhuangtai.setText(yuyue.getState());
						
						if(yuyue.getState().equals("已挂号"))
						{
							zhengduanBtn.setVisibility(View.VISIBLE);
						}
						else
						{
							zhengduanBtn.setVisibility(View.GONE);
						}
					

					}
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
