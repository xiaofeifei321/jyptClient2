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

public class YishengDetailActivity extends FinalActivity {

	@ViewInject(id = R.id.yuyuebtn, click = "yuyuebtnClick")
	private Button yuyuebtn;

	@ViewInject(id = R.id.yishengzhaopian)
	private ImageView yishengzhaopian;

	@ViewInject(id = R.id.yishengxingming)
	private TextView yishengxingming;

	@ViewInject(id = R.id.yuyueminge)
	private TextView yuyueminge;

	@ViewInject(id = R.id.zuozhengshijian)
	private TextView zuozhengshijian;

	@ViewInject(id = R.id.yishengjianjie)
	private TextView yishengjianjie;

	MyApplication myApp;

	String id = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_yisheng_detail);

		Intent intent = getIntent();

		id = intent.getStringExtra("id");

		myApp = (MyApplication) getApplication();

		Thread yishengDetailThread = new Thread(new YishengDetailThread());

		yishengDetailThread.start();
	}

	class YishengDetailThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("id", id);

				paraObj.put("action", "view");

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"YishengService");

				Message msg = new Message();

				msg.what = 1;// ���ʳɹ����з���ֵ

				msg.obj = result;

				handler.sendMessage(msg);

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 0;// �����쳣

				handler.sendMessage(msg);

			}
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:

				Toast.makeText(getApplicationContext(), "����ʧ��,���������Ƿ�ͨ!",
						Toast.LENGTH_SHORT).show();

			case 1:

				try {

					String yishengStr = msg.obj.toString();
					if (yishengStr.length() > 0) {
						Yisheng yisheng = JSONObject.parseObject(yishengStr,
								Yisheng.class);

						FinalBitmap fb = FinalBitmap
								.create(YishengDetailActivity.this);// ��ʼ��FinalBitmapģ��

						fb.configLoadingImage(R.drawable.logo);// ���ü���ͼƬ

						fb.display(yishengzhaopian,
								HttpUtil.BASE_URL + yisheng.getImgpath());// ��һ������Ϊiamgeview������ڶ���Ϊ���ص�url��ַ

						yishengxingming.setText(yisheng.getName());

						zuozhengshijian.setText(yisheng.getZhibanshijian());

						yuyueminge.setText(String.valueOf(yisheng
								.getYuyueminge()));

						yishengjianjie.setText(yisheng.getDescription());

						yishengjianjie
								.setMovementMethod(ScrollingMovementMethod
										.getInstance());
					}
				} catch (Exception ex) {
					System.out.print(ex.getMessage());

				}
				break;
			case 3:

				break;
			}

		}
	};

	public void yuyuebtnClick(View v) {

		if (Integer.valueOf(yuyueminge.getText().toString()) < 1) {

			Toast.makeText(this, "û��������,����´�ԤԼ!", Toast.LENGTH_LONG).show();

			return;
		}

		Yuyue yuyue = new Yuyue();

		yuyue.setYishengid(id);

		yuyue.setUserid(String.valueOf(myApp.getUser().getId()));

		yuyue.setState("�ȴ��Һ�");

		Date date = new Date();

		yuyue.setCreatetime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(date));

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "add");

		ajaxParams.put("yuyue", JSONObject.toJSONString(yuyue));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.equals("ok")) {

								Toast.makeText(YishengDetailActivity.this,
										"ԤԼ�ɹ�!", Toast.LENGTH_LONG).show();
								
								Thread yishengDetailThread = new Thread(new YishengDetailThread());

								yishengDetailThread.start();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
