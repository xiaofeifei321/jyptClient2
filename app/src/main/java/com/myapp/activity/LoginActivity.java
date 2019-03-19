package com.myapp.activity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
import com.myapp.activity.R;
import com.myapp.common.HttpUtil;
import com.myweb.domain.User;
import com.myweb.domain.Yisheng;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Build;

public class LoginActivity  extends FinalActivity  {

	@ViewInject(id = R.id.loginbtn, click = "loginBtnClick")
	private Button loginBtn;

	@ViewInject(id = R.id.registbtn, click = "registBtnClick")
	private Button registbutton;

	@ViewInject(id = R.id.etUsername)
	private EditText loginname;

	@ViewInject(id = R.id.etPwd)
	private EditText loginpsw;

	MyApplication myApp;
 	

	@ViewInject(id = R.id.radioGroup1)
	RadioGroup rg;

	@ViewInject(id = R.id.radio0)
	RadioButton radio0;

	@ViewInject(id = R.id.radio1)
	RadioButton radio1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		 
		setContentView(R.layout.activity_login);

		myApp = (MyApplication) getApplication();
		
		myApp.setUser(new User());
		
		myApp.setYisheng(new Yisheng());
	}


	public void registBtnClick(View v) {

		Intent intent = new Intent();

		intent.setClass(LoginActivity.this, RegActivity.class);

		startActivity(intent);
	}
	
	public void loginBtnClick(View v) {
		
		String type = "病人";

		if (radio1.isChecked()) {
			type = "医生";
		}
		
		  
		if (loginname.getText().toString().length() < 1) {

			Toast.makeText(LoginActivity.this, "登陆账号不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if (loginpsw.getText().toString().length() < 1) {
			Toast.makeText(LoginActivity.this, "登陆密码不能为空!", Toast.LENGTH_LONG)
					.show();
			return;
		}

		if(type.endsWith("病人"))
		{
			User user = new User();

			user.setLoginname(loginname.getText().toString());

			user.setLoginpsw(loginpsw.getText().toString());

			FinalHttp finalHttp = new FinalHttp();

			AjaxParams ajaxParams = new AjaxParams();

			ajaxParams.put("action", "login");

			ajaxParams.put("user", JSONObject.toJSONString(user));

			try {
				finalHttp.get(HttpUtil.BASE_URL + "UserService", ajaxParams,
						new AjaxCallBack<Object>() {

							@Override
							public void onSuccess(Object t) {

								String result = (String) t;

								if (result.length() > 0) {

									User user1 = JSONObject.parseObject(result,
											User.class);
									
									myApp.setUsertype("病人");
									
									myApp.setUser(user1);

									Intent intent = new Intent();

									intent.setClass(LoginActivity.this,
											BrMainActivity.class);

									startActivity(intent);

								} else {

									Toast.makeText(LoginActivity.this,
											"请检查用户名和密码是否正确!", Toast.LENGTH_LONG)
											.show();
									return;
								}

							}
						});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			Yisheng yisheng = new Yisheng();

			yisheng.setLoginname(loginname.getText().toString());

			yisheng.setLoginpsw(loginpsw.getText().toString());

			FinalHttp finalHttp = new FinalHttp();

			AjaxParams ajaxParams = new AjaxParams();

			ajaxParams.put("action", "login");

			ajaxParams.put("yisheng", JSONObject.toJSONString(yisheng));

			try {
				finalHttp.get(HttpUtil.BASE_URL + "YishengLoginService", ajaxParams,
						new AjaxCallBack<Object>() {

							@Override
							public void onSuccess(Object t) {

								String result = (String) t;

								if (result.length() > 0) {

									Yisheng yisheng = JSONObject.parseObject(result,
											Yisheng.class);
									
									myApp.setUsertype("医生");

									myApp.setYisheng(yisheng);

									Intent intent = new Intent();

									intent.setClass(LoginActivity.this,
											YsMainActivity.class);

									startActivity(intent);

								} else {

									Toast.makeText(LoginActivity.this,
											"请检查用户名和密码是否正确!", Toast.LENGTH_LONG)
											.show();
									return;
								}

							}
						});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		

	}
}
