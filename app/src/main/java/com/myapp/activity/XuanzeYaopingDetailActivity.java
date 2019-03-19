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
import android.widget.TextView;
import android.widget.Toast;

public class XuanzeYaopingDetailActivity extends FinalActivity {

	@ViewInject(id = R.id.yaopingmingcheng)
	private TextView yaopingmingcheng;

	@ViewInject(id = R.id.kucun)
	private TextView kucun;

	@ViewInject(id = R.id.danjia)
	private TextView danjia;

	@ViewInject(id = R.id.shuoming)
	private TextView shuoming;

	@ViewInject(id = R.id.goumaishuliang)
	private EditText goumaishuliang;

	@ViewInject(id = R.id.quedingBtn, click = "quedingBtnClick")
	private Button quedingBtn;

	MyApplication myApp;

	String id = "";

	String yuyueid = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_xuanzeyaoping_detail);

		Intent intent = getIntent();

		id = intent.getStringExtra("id");

		yuyueid = intent.getStringExtra("yuyueid");

		myApp = (MyApplication) getApplication();

		initData();
	}

	public void quedingBtnClick(View v) {

		String a=goumaishuliang.getText().toString();
		if (goumaishuliang.getText()==null||goumaishuliang.getText().toString().length() < 1
				|| goumaishuliang.getText().equals("0")) {

			Toast.makeText(this, "请填写正确的购买数量!", Toast.LENGTH_LONG).show();

			return;
		}
		if (Integer.valueOf(goumaishuliang.getText().toString())>Integer.valueOf(kucun.getText().toString())) {

			Toast.makeText(this, "库存不足!", Toast.LENGTH_LONG).show();

			return;
		}

		Goumaiyaoping goumaiyaoping = new Goumaiyaoping();

		goumaiyaoping.setYuyueid(Integer.valueOf(yuyueid));
		
		goumaiyaoping.setYaopingid(Integer.valueOf(id));

		goumaiyaoping.setShuoming(shuoming.getText().toString());

		goumaiyaoping.setDanjia(Double.valueOf(danjia.getText().toString()));

		goumaiyaoping
				.setYaopingmingcheng(yaopingmingcheng.getText().toString());

		goumaiyaoping.setGoumaishuliang(Integer.valueOf(goumaishuliang.getText().toString()));

		Date date = new Date();

		goumaiyaoping.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "add");

		ajaxParams.put("goumaiyaoping", JSONObject.toJSONString(goumaiyaoping));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "GoumaiyaopingService",
					ajaxParams, new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(XuanzeYaopingDetailActivity.this,
										"购买成功!", Toast.LENGTH_LONG).show();
								
								goumaishuliang.setText("");
								
								initData();

							}

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

		ajaxParams.put("yaopingid", id);

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YaopingService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					if (result.length() > 0) {

						Yaoping yaoping = JSONObject.parseObject(result,
								Yaoping.class);

						yaopingmingcheng.setText(yaoping.getYaopingmingcheng());

						kucun.setText(String.valueOf(yaoping.getKucun()));

						danjia.setText(String.valueOf(yaoping.getJiage()));

						shuoming.setText(yaoping.getShuoming());
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
