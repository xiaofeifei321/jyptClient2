package com.myapp.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.myapp.adapter.YishengListAdapter;
import com.myapp.adapter.YishengListAdapter.ViewHolder;
import com.myapp.common.HttpUtil;
 
import com.myweb.domain.Yisheng;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class YishengListFragment extends Fragment {

	 
	YishengListAdapter yishengListAdapter;

	private String _keshiid;

	private ProgressDialog pd;

	List<Map<String, Object>> yishengList;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_keshiid = getArguments().getString("keshiid");

	}

	public static final YishengListFragment newInstance(String keshiid) {
		YishengListFragment f = new YishengListFragment();

		Bundle bdl = new Bundle(2);

		bdl.putString("keshiid", keshiid);

		f.setArguments(bdl);

		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_yisheng_list, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		/* ��ʾProgressDialog */
		pd = ProgressDialog.show(getActivity(), "����", "�����У����Ժ󡭡�");

		ListView yishengListView = (ListView) getActivity().findViewById(
				R.id.yishenglist_lv);

		yishengList = new ArrayList<Map<String, Object>>();

		yishengListAdapter = new YishengListAdapter(getActivity(), yishengList);

		yishengListView.setAdapter(yishengListAdapter);

		yishengListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				ViewHolder vHollder = (ViewHolder) view.getTag();

				// Toast.makeText(getApplicationContext(),
				// vHollder.name.getText(), Toast.LENGTH_SHORT).show();

				if (vHollder.id != null) {// ֻ����������

					Intent intent = new Intent();

					intent.putExtra("id", vHollder.id.getText());

					intent.setClass(getActivity(), YishengDetailActivity.class);

					// ����Activity
					startActivity(intent);
				}
			}
		});

		Thread yishengListThread = new Thread(new YishengListThread());

		yishengListThread.start();

	}

	class YishengListThread implements Runnable {

		public void run() {
			try {

				JSONObject paraObj = new JSONObject();

				paraObj.put("action", "list");

				paraObj.put("keshiid", _keshiid);

				String result = HttpUtil.getJsonFromServlet(paraObj.toString(),
						"YishengService");

				List<Yisheng> yishengList1 = new ArrayList<Yisheng>();

				if (!result.equals("")) {

					yishengList1 = JSONObject.parseArray(result, Yisheng.class);// ��ȡ��Ʒ����
				}

				ArrayList<Map<String, Object>> goodslistMap1 = new ArrayList<Map<String, Object>>();

				for (Yisheng yisheng : yishengList1) {

					Map<String, Object> map1 = new HashMap<String, Object>();

					map1.put("id", yisheng.getId());

					map1.put("name", yisheng.getName());

					map1.put("createtime", yisheng.getCreatetime());
			 
					map1.put("num", yisheng.getYuyueminge());
					if (yisheng.getImgpath().length() > 0) {

						map1.put("imgpath",
								HttpUtil.BASE_URL + yisheng.getImgpath());
					} else {
						map1.put("imgpath", "");
					}

					goodslistMap1.add(map1);

				}

				Message msg = new Message();

				msg.what = 1;// ���ʳɹ����з���ֵ

				List para = new ArrayList();

				para.add(goodslistMap1);

				msg.obj = para;

				handler.sendMessage(msg);

			} catch (Exception ex) {

				System.out.println("ex: " + ex.getMessage());

				Message msg = new Message();

				msg.what = 0;// �����쳣

				handler.sendMessage(msg);

			}
		}
	}

	// Handler
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);

			switch (msg.what) {

			case 0:
				pd.dismiss();// �ر�ProgressDialog

				Toast.makeText(getActivity().getApplicationContext(),
						"��ѯʧ��,���������Ƿ�ͨ!", Toast.LENGTH_SHORT).show();
				break;

			case 1:
				pd.dismiss();// �ر�ProgressDialog

				try {

					List paraList = (ArrayList) msg.obj;

					ArrayList<Map<String, Object>> result = (ArrayList<Map<String, Object>>) paraList
							.get(0);

					List<String> result1 = (ArrayList) paraList.get(0);

					if (result1 != null) {

						yishengList.addAll(result);

						yishengListAdapter.notifyDataSetChanged();

						System.out.println(result.toString());
					}
				} catch (Exception ex) {

					System.out.println("ex: " + ex.getMessage());
				}
				break;
			}

		}
	};
}
