package com.myapp.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.myapp.activity.R;

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

public class YishengListAdapter extends BaseAdapter {

	private List<Map<String, Object>> mData;

	private LayoutInflater mInflater;

	public static Map<Integer, Boolean> isSelected;

	private Context context;// 用于接收传递过来的Context对象

	public YishengListAdapter(Context context, List<Map<String, Object>> mData) {

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

		ViewHolder holder = null;// 数据项

		holder = new ViewHolder();

		convertView = LayoutInflater.from(context).inflate(
				R.layout.activity_yisheng_list_item, null);

		holder.img = (ImageView) convertView.findViewById(R.id.goodslistimage);

		holder.name = (TextView) convertView.findViewById(R.id.goodslistname);

		holder.num = (TextView) convertView.findViewById(R.id.goodslistnum);

		holder.id = (TextView) convertView.findViewById(R.id.goodslistid);

		holder.id.setVisibility(View.GONE);

		// holder.state = (TextView)
		// convertView.findViewById(R.id.state);

		holder.createtime = (TextView) convertView
				.findViewById(R.id.goodslistcreatetime);

		convertView.setTag(holder);

		holder.name.setText(mData.get(position).get("name").toString());

		holder.id.setText(mData.get(position).get("id").toString());

		holder.num.setText(mData.get(position).get("num").toString());

		String createtime = mData.get(position).get("createtime").toString();

		createtime = createtime.substring(0, createtime.indexOf(" ")).replace(
				"-", "");
		holder.createtime.setText(createtime);

		String imgpath = mData.get(position).get("imgpath").toString();
		if (imgpath.length() > 0) {
			// 显示图片的配置
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageOnLoading(R.drawable.logo)
					.showImageOnFail(R.drawable.logo).cacheInMemory(true)
					.cacheOnDisk(true).bitmapConfig(Bitmap.Config.RGB_565)
					.build();

			ImageLoader.getInstance()
					.displayImage(imgpath, holder.img, options);
		}

		return convertView;

	}

	public final class ViewHolder {

		public TextView id;

		public TextView name;

		public TextView num;

		public TextView createtime;

		public ImageView img;
	}
}
