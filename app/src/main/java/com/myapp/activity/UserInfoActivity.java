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

public class UserInfoActivity extends FinalActivity {

	@ViewInject(id = R.id.update_btn, click = "updateBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.loginname)
	private TextView loginname;

	@ViewInject(id = R.id.loginpsw)
	private EditText loginpsw;

	@ViewInject(id = R.id.username)
	private EditText username;

	@ViewInject(id = R.id.tel)
	private EditText tel;

	@ViewInject(id = R.id.email)
	private EditText email;

	private User user;

	MyApplication myApp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_userinfo);

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp

		User user = myApp.getUser();

		loginname.setText(user.getLoginname());

		loginpsw.setText(user.getLoginpsw());

		username.setText(user.getUsername());

		tel.setText(user.getTel());

		email.setText(user.getEmail());

	}

	public void updateBtnClick(View v) {

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(UserInfoActivity.this, "登陆密码不能为空!",
					Toast.LENGTH_LONG).show();
			return;
		}
		if (username.getText().toString().length() < 1) {
			Toast.makeText(UserInfoActivity.this, "姓名不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (tel.getText().toString().length() < 1) {
			Toast.makeText(UserInfoActivity.this, "电话不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		User user = myApp.getUser();

		user.setLoginpsw(loginpsw.getText().toString());

		user.setUsername(username.getText().toString());

		user.setTel(tel.getText().toString());

		user.setEmail(email.getText().toString());
		JSONObject paraObj = new JSONObject();

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "editsave");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(UserInfoActivity.this,
										"修改成功,请重新登陆!", Toast.LENGTH_LONG)
										.show();

								UserInfoActivity.this.finish();

							} else {

								Toast.makeText(UserInfoActivity.this, "修改失败!",
										Toast.LENGTH_LONG).show();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
