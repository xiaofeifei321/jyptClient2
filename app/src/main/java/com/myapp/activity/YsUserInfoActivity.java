package com.myapp.activity;

import java.util.HashMap;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.myapp.common.HttpUtil;
import com.myweb.domain.User;
import com.myweb.domain.Yisheng;

public class YsUserInfoActivity extends FinalActivity {

	@ViewInject(id = R.id.update_btn, click = "updateBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.xingming)
	private TextView xingming;

	@ViewInject(id = R.id.keshi)
	private TextView keshi;

	@ViewInject(id = R.id.dengluzhanghao)
	private TextView dengluzhanghao;

	@ViewInject(id = R.id.mima)
	private EditText mima;

	private Yisheng yisheng;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_ys_userinfo);

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp

		yisheng = myApp.getYisheng();

		xingming.setText(yisheng.getName());

		keshi.setText(yisheng.getKeshiname());

		dengluzhanghao.setText(yisheng.getLoginname());

		mima.setText(yisheng.getLoginpsw());

	}

	public void updateBtnClick(View v) {

		if (mima.getText().toString().length() < 1) {
			Toast.makeText(YsUserInfoActivity.this, "登陆密码不能为空!",
					Toast.LENGTH_LONG).show();
			return;
		}

		yisheng.setLoginpsw(mima.getText().toString());

		JSONObject paraObj = new JSONObject();

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "updatepsw");

		ajaxParams.put("yisheng", JSONObject.toJSONString(yisheng));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YishengLoginService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(YsUserInfoActivity.this,
										"修改成功,请重新登陆!", Toast.LENGTH_LONG)
										.show();

								YsUserInfoActivity.this.finish();

							} else {

								Toast.makeText(YsUserInfoActivity.this,
										"修改失败!", Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
