package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.tsz.afinal.FinalActivity;

import com.alibaba.fastjson.JSON;
import com.myapp.activity.R;
import com.myapp.activity.YsZhengduanAddActivity;
import com.myweb.domain.Goumaiyaoping;
import com.myweb.domain.Yaoping;
import com.myweb.domain.Yuyue;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GoumaiyaopinListAdapter extends BaseAdapter {

	private List<Goumaiyaoping> mData;

	private LayoutInflater mInflater;

	private YsZhengduanAddActivity context;// 用于接收传递过来的Context对象

	public GoumaiyaopinListAdapter(YsZhengduanAddActivity context, List<Goumaiyaoping> mData) {

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
					R.layout.activity_ys_zhengduanlishi_yaoping_list_item, null);

			FinalActivity.initInjectedView(this, convertView);

			holder.yaopingid = (TextView) convertView.findViewById(R.id.yaopingid);

			holder.yaopingid.setVisibility(View.GONE);

			holder.yaopingmingcheng = (TextView) convertView
					.findViewById(R.id.yaopingmingcheng);

			holder.shuliang = (TextView) convertView.findViewById(R.id.shuliang);

			holder.zongjia = (TextView) convertView.findViewById(R.id.zongjia);
			
			holder.delBtn = (Button) convertView
					.findViewById(R.id.delBtn);

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();

		}

		holder.yaopingid.setText(String.valueOf(mData.get(position).getId()));

		holder.yaopingmingcheng.setText(mData.get(position)
				.getYaopingmingcheng());

		holder.shuliang.setText( String.valueOf(mData.get(position).getGoumaishuliang()));

		holder.zongjia.setText(String.valueOf((mData.get(position).getDanjia())*mData.get(position).getGoumaishuliang()));

		holder.delBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				context.DeleteItem(mData.get(position).getId());

			}
		});
		return convertView;
	}

	public final class ViewHolder {

		public TextView yaopingid;

		public TextView yaopingmingcheng;

		public TextView shuliang;

		public TextView zongjia;
		
		public Button delBtn;
		

	}
}
