package com.myapp.activity;

import com.myweb.domain.User;
import com.myweb.domain.Yisheng;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {

	private User _user;

	private Yisheng _yisheng;

	private String _usertype;

	public void onCreate() {
		super.onCreate();
		// 创建默认的ImageLoader配置参数
		// 创建默认的ImageLoader配置参数
		ImageLoaderConfiguration configuration = ImageLoaderConfiguration
				.createDefault(this);

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(configuration);

		setUser(new User());

		setYisheng(new Yisheng());

		setUsertype("");
	}

	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		_user = user;
	}

	public Yisheng getYisheng() {
		return _yisheng;
	}

	public void setYisheng(Yisheng yisheng) {
		this._yisheng = yisheng;
	}

	public String getUsertype() {
		return _usertype;
	}

	public void setUsertype(String usertype) {
		this._usertype = usertype;
	}

}
