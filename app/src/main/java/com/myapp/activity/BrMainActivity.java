package com.myapp.activity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.myapp.adapter.MainActivityAdapter;
import com.myweb.domain.User;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class BrMainActivity extends FinalActivity {

	GridView maingv;

	MyApplication myApp;

	@ViewInject(id = R.id.logininfo)
	private TextView logininfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myApp = (MyApplication) getApplication();

		setContentView(R.layout.activity_main_in);
		// 获取到GridView
		maingv = (GridView) findViewById(R.id.gv_all);
		// 给gridview设置数据适配器
		maingv.setAdapter(new MainActivityAdapter(this));
		// 点击事件
		maingv.setOnItemClickListener(new MainItemClickListener());
		
		User user=myApp.getUser();

		logininfo.setText("病人["+user.getUsername().toString() +"]你好!");

	}

	private class MainItemClickListener implements OnItemClickListener {
		/**
		 * @param parent
		 *            代表当前的gridview
		 * @param view
		 *            代表点击的item
		 * @param position
		 *            当前点击的item在适配中的位置
		 * @param id
		 *            当前点击的item在哪一行
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {
			case 0:
				Intent intent = new Intent(BrMainActivity.this,
						YishengListActivity.class);
				// 启动Activity
				startActivity(intent);

				break;
			case 1:

				Intent intent1 = new Intent(BrMainActivity.this,
						BrYuyueListActivity.class);
				// 启动Activity
				startActivity(intent1);

				break;
			case 2:

				Intent intent3 = new Intent(BrMainActivity.this,
						BoardListActivity.class);
				// 启动Activity
				startActivity(intent3);

				break;
			case 3:

				Intent intent4 = new Intent(BrMainActivity.this,
						UserInfoActivity.class);
				// 启动Activity
				startActivity(intent4);

				break;

			}
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
