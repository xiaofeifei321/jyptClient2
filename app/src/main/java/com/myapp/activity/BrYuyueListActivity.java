package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.annotation.view.ViewInject;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.alibaba.fastjson.JSONObject;
  
import com.myapp.adapter.YuyueListAdapter;
import com.myapp.adapter.YuyueListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
 



import com.myweb.domain.Keshi;
import com.myweb.domain.Yuyue;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrYuyueListActivity  extends FinalActivity {

	List<Yuyue> mListViewData = new ArrayList<Yuyue>();

	List<Yuyue> yuyuelist1;

	@ViewInject(id = R.id.yuyuelist, itemClick = "ItemClick")
	ListView listView;

	private YuyueListAdapter adapter;

	MyApplication myApp;
	
	private String[] opr = new String[] { "删除" };
	
	String delid="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_br_yuyue_list);
	 
		yuyuelist1 = new ArrayList<Yuyue>();

		adapter = new YuyueListAdapter(BrYuyueListActivity.this, yuyuelist1);

		listView.setAdapter(adapter);

		myApp = (MyApplication) getApplication(); // 获得自定义的应用程序MyApp
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewHolder vHollder = (ViewHolder) view.getTag();

				Intent intent = new Intent();

				intent.putExtra("id", vHollder.id.getText());

				intent.setClass(BrYuyueListActivity.this, BrYuyueDetailActivity.class);

				startActivity(intent);
			}
		});
		

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				if(vHollder.state.getText().toString().endsWith("等待挂号"))
				{
					showOprDialog(String.valueOf(vHollder.id.getText()));
					
					
				}
				

				return true;

			}
		});
		initData();

	}

	
	private void showOprDialog(String id) {

		final String _id = id;

		AlertDialog.Builder builder = new AlertDialog.Builder(
				BrYuyueListActivity.this);

		builder.setTitle("请选择操作:");

		builder.setItems(opr, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				if (which == 0) {

					FinalHttp finalHttp = new FinalHttp();

					AjaxParams ajaxParams = new AjaxParams();

					ajaxParams.put("action", "delete");
					
					ajaxParams.put("yuyueid", _id);

					try {
						finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,
								new AjaxCallBack<Object>() {

									@Override
									public void onSuccess(Object t) {

										String result = (String) t;

										if (result.equals("ok")) {

											Toast.makeText(getApplicationContext(), "操作成功!",
													Toast.LENGTH_SHORT).show();
											initData();
										}

									}
								});

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		builder.create().show();
	}
	
	 
	public void initData() {

		FinalHttp finalHttp = new FinalHttp();

		AjaxParams ajaxParams = new AjaxParams();

		ajaxParams.put("action", "br_yuyuelist");
		
		ajaxParams.put("userid", String.valueOf(myApp.getUser().getId()));

		try {
			finalHttp.get(HttpUtil.BASE_URL + "YuyueService", ajaxParams,
					new AjaxCallBack<Object>() {

						@Override
						public void onSuccess(Object t) {

							String result = (String) t;

							if (result.length() > 0) {

								List<Yuyue> yuyueList = new ArrayList<Yuyue>();

								yuyueList = JSONObject.parseArray(result,
										Yuyue.class);

								yuyuelist1.clear();

								yuyuelist1.addAll(yuyueList);

								adapter.notifyDataSetChanged();

							}

						}
					});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
