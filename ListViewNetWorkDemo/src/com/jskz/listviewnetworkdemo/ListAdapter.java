package com.jskz.listviewnetworkdemo;

import java.util.List;
import com.jskz.listviewnetworkdemo.model.DataBean;
import android.content.Context;
import android.text.Html;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private List<DataBean> data;
	private Context context;
	private LayoutInflater mInflater;
	private int mCurrentItem = 10000;
	public static final int TYPE_1 = 0;
	public static final int TYPE_2 = 1;
	private String Joke = "<font color=red>大哥！</font><font color=blue>不要点我啊！</font>";
	private String Boy = "我是聪明的好孩子";

	public ListAdapter(Context context, List<DataBean> data) {
		this.context = context;
		this.data = data;
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data==null) return 0;
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void setCurrentItem(int currentItem) {
		this.mCurrentItem = currentItem;
	}

	/* 存放控件 */
	public static final class ViewHolderType1 {
		public TextView saying;
	}
	
	public static final class ViewHolderType2 {
		public TextView speaking;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// int type = getItemViewType(position);
		ViewHolderType1 holderType1 = null;
		ViewHolderType2 holderType2 = null;
		// 观察convertView随ListView滚动情况
		Log.v("ListAdapter", "getView " + position + " " + convertView);
		final DataBean item = data.get(position);
		int type = item.getType();
		// 判断是否缓存
		if (convertView == null) {
			switch (type) {
			case TYPE_1:
				// 通过LayoutInflater实例化布局
				convertView = mInflater.inflate(R.layout.item, null);
				holderType1 = new ViewHolderType1();
				/* 得到各个控件的对象 */
				holderType1.saying = (TextView) convertView
						.findViewById(R.id.aci_item_itemtext);
				convertView.setTag(holderType1);// 绑定ViewHolder对象
				break;
			case TYPE_2:
				// 通过LayoutInflater实例化布局
				convertView = mInflater.inflate(R.layout.item1, null);
				holderType2 = new ViewHolderType2();
				/* 得到各个控件的对象 */
				holderType2.speaking = (TextView) convertView
						.findViewById(R.id.aci_item1_itemtext);
				convertView.setTag(holderType2);// 绑定ViewHolder对象
				break;
			}
		}else {
			switch (type) {
			case TYPE_1:
				holderType1=(ViewHolderType1)convertView.getTag();
				break;
			case TYPE_2:
				holderType2=(ViewHolderType2)convertView.getTag();
				break;
			}
		}

		/* 设置TextView显示的内容，即我们存放在动态数组中的数据 */
		if (mCurrentItem == position) {
			switch (type) {
			case TYPE_1:
				holderType1.saying.setText(Html.fromHtml(Joke));
				holderType1.saying.setGravity(Gravity.CENTER);
				break;
			case TYPE_2:
				holderType2.speaking.setText(Html.fromHtml(Joke));
				holderType2.speaking.setGravity(Gravity.CENTER);
				break;
			}
		} else {
			switch (type) {
			case TYPE_1:
				holderType1.saying.setText(data.get(position).getName().toString()
										   + " (" + data.get(position).getPronounce().toString() + ")"
										   + "\n" + data.get(position).getContent().toString());
				holderType1.saying.setGravity(Gravity.LEFT);
				holderType1.saying.setLines(2);
				holderType1.saying.setEllipsize(TruncateAt.END);
				break;
			case TYPE_2:
				holderType2.speaking.setText(Boy);
				holderType2.speaking.setGravity(Gravity.LEFT);
				break;
			}
		}
		
		return convertView;
	}
	
	@Override
	public int getItemViewType(int position) {

		DataBean dataBean = data.get(position);
		int type = dataBean.getType();
		return type;
	}

	/**
	 * 返回所有的layout的数量
	 * 
	 * */
	@Override
	public int getViewTypeCount() {
		return 2;
	}

}
