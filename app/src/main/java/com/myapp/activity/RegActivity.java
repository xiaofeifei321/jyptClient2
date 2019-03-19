package com.myapp.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.R;
 
import com.myapp.common.HttpUtil;
import com.myweb.domain.User;

public class RegActivity extends FinalActivity  {


	@ViewInject(id = R.id.regist_btn, click = "registBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.loginname)
	private EditText loginname;

	@ViewInject(id = R.id.loginpsw)
	private EditText loginpsw;

	@ViewInject(id = R.id.username)
	private EditText username;

	@ViewInject(id = R.id.tel)
	private EditText tel;

	@ViewInject(id = R.id.email)
	private EditText email;

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_reg);

	}

	public void registBtnClick(View v) {

		if (loginname.getText().toString().length() < 1) {

			Toast.makeText(RegActivity.this, "登陆账号不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(RegActivity.this, "登陆密码不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (username.getText().toString().length() < 1) {
			Toast.makeText(RegActivity.this, "姓名不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (tel.getText().toString().length() < 1) {
			Toast.makeText(RegActivity.this, "电话不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

	    user = new User();

		user.setLoginname(loginname.getText().toString());

		user.setLoginpsw(loginpsw.getText().toString());

		user.setUsername(username.getText().toString());

		user.setTel(tel.getText().toString());

		user.setEmail(email.getText().toString());

	 

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = new Date();

		user.setCreatetime(f.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "vali");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("true")) {

								Toast.makeText(RegActivity.this, "用户名已存在!",
										Toast.LENGTH_LONG).show();
								return;

							} else {
								regest();
							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void regest() {
		
		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams = new AjaxParams();

		ajaxParams.put("action", "reg");

		ajaxParams.put("user", JSONObject.toJSONString(user));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,

			new AjaxCallBack<Object>() {

				@Override
				public void onSuccess(Object t) {

					String result = (String) t;

					if (result.equals("ok")) {

						Toast.makeText(RegActivity.this, "注册成功!",
								Toast.LENGTH_LONG).show();

						RegActivity.this.finish();

					}

				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	 

}
