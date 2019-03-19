package com.myapp.activity;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import com.myapp.adapter.MainActivityAdapter;
import com.myapp.adapter.YsMainActivityAdapter;
import com.myweb.domain.User;
import com.myweb.domain.Yisheng;

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

public class YsMainActivity extends FinalActivity {

	GridView maingv;

	MyApplication myApp;

	@ViewInject(id = R.id.logininfo)
	private TextView logininfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myApp = (MyApplication) getApplication();

		setContentView(R.layout.activity_ys_main);
		// ��ȡ��GridView
		maingv = (GridView) findViewById(R.id.gv_all);
		// ��gridview��������������
		maingv.setAdapter(new YsMainActivityAdapter(this));
		// ����¼�
		maingv.setOnItemClickListener(new MainItemClickListener());
		
		Yisheng yisheng=myApp.getYisheng();

		logininfo.setText("ҽ��["+yisheng.getName().toString() +"]���!");

	}

	private class MainItemClickListener implements OnItemClickListener {
		/**
		 * @param parent
		 *            ������ǰ��gridview
		 * @param view
		 *            ���������item
		 * @param position
		 *            ��ǰ�����item�������е�λ��
		 * @param id
		 *            ��ǰ�����item����һ��
		 */
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (position) {

			case 0:

				Intent intent = new Intent(YsMainActivity.this,
						YsYuyueListActivity.class);
				// ����Activity
				startActivity(intent);

				break;
			case 1:

				Intent intent1 = new Intent(YsMainActivity.this,
						YsYuyuelishiListActivity.class);
				// ����Activity
				startActivity(intent1);

				break;
				
			case 2:

				Intent intent2 = new Intent(YsMainActivity.this,
						BoardListActivity.class);
				// ����Activity
				startActivity(intent2);

				break;
			case 3:

				Intent intent3 = new Intent(YsMainActivity.this,
						YsUserInfoActivity.class);
				// ����Activity
				startActivity(intent3);

				break;

			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}