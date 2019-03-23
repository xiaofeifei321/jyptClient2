package com.myapp.adapter;
 

import com.myapp.activity.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class YsMainActivityAdapter extends BaseAdapter {

	private static final String TAG = "MainGridViewAdapter";

	private String[] names = {"ԤԼ�б�","�����ʷ","Զ�̻���","������Ϣ" };
	private int[] icons = { R.drawable.wodeyuyue,R.drawable.yao, R.drawable.zhengduanlishi,R.drawable.grxx};
	private Context context;
	LayoutInflater infalter;

	public YsMainActivityAdapter(Context context) {
		this.context = context;
		// ����1 ͨ��ϵͳ��service ��ȡ�� ��ͼ�����
		// infalter = (LayoutInflater)
		// context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// ����2 ͨ��layoutinflater�ľ�̬������ȡ�� ��ͼ�����
		infalter = LayoutInflater.from(context);
	}

	// ����gridview�����ж��ٸ���Ŀ
	@Override
	public int getCount() {
		return names.length;
	}

	// ����ĳ��position��Ӧ��id
	@Override
	public Object getItem(int position) {
		return position;
	}

	// ����ĳ��position��Ӧ��id
	@Override
	public long getItemId(int position) {
		return position;
	}

	// ����ĳ��λ�ö�Ӧ����ͼ
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.i(TAG, "GETVIEW " + position);
		// ��һ�������ļ�ת������ͼ
		View view = infalter.inflate(R.layout.activity_main_item, null);
		ImageView iv = (ImageView) view.findViewById(R.id.main_gv_iv);
		TextView tv = (TextView) view.findViewById(R.id.main_gv_tv);
		// ����ÿһ��item�����ֺ�ͼ��
		iv.setImageResource(icons[position]);
		tv.setText(names[position]);
		return view;
	}

}
