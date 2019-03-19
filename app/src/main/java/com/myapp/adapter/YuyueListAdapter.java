package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;

import com.alibaba.fastjson.JSON;
import com.myapp.activity.R;
import com.myweb.domain.Yuyue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YuyueListAdapter extends BaseAdapter {

	private List<Yuyue> mData;

	private LayoutInflater mInflater;

	private Context context;// 用于接收传递过来的Context对象

	public YuyueListAdapter(Context context, List<Yuyue> mData) {

		this.context = context;

		mInflater = LayoutInflater.from(context);

		this.mData = mData;

	}

	@Override
	public int getCount() {
		System.out.print("mData.size()=" + mData.size());
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		// convertView为null的时候初始化convertView。
		if (convertView == null) {

			holder = new ViewHolder();

			convertView = mInflater.inflate(
					R.layout.activity_br_yuyue_list_item, null);

			FinalActivity.initInjectedView(this, convertView);

			holder.id = (TextView) convertView.findViewById(R.id.id);

			holder.id.setVisibility(View.GONE);

			holder.keshimingcheng = (TextView) convertView
					.findViewById(R.id.keshimingcheng);

			holder.yishengxingming = (TextView) convertView
					.findViewById(R.id.yishengxingming);

			holder.state = (TextView) convertView.findViewById(R.id.state);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.id.setText(String.valueOf(mData.get(position).getId()));

		holder.keshimingcheng.setText("科室:"
				+ mData.get(position).getKeshimingcheng());

		holder.yishengxingming.setText("医生:"
				+ mData.get(position).getYishengxingming());

		holder.state.setText(mData.get(position).getState());

		return convertView;
	}

	public final class ViewHolder {

		public TextView id;

		public TextView keshimingcheng;

		public TextView yishengxingming;

		public TextView state;

	}
}
