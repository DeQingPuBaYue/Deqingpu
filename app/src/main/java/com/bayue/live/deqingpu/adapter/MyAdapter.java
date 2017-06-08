package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.ProvinceBean;

import java.util.List;


public class MyAdapter extends BaseAdapter {

	private Context mContext;
	private List<ProvinceBean> mlist = null;
	private boolean delete = false;
	private boolean isShowCard = false;
//	areaHelper = new ChinaAreaHelper(mContext);

	// private Button curDel_btn = null;
	// private UpdateDate mUpdateDate = null;

	public MyAdapter(Context mContext, List<ProvinceBean> mlist) {
		this.mContext = mContext;
		this.mlist = mlist;
		this.isShowCard = isShowCard;

	}

	public int getCount() {

		return mlist.size();
	}

	public Object getItem(int pos) {
		return mlist.get(pos);
	}

	public long getItemId(int pos) {
		return pos;
	}

	public View getView(final int pos, View convertView, ViewGroup p) {

		final ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_city_select, null);
			viewHolder = new ViewHolder();
			viewHolder.txtSelectAddress = (TextView) convertView.findViewById(R.id.txtSelectAddress);
			convertView.setTag(viewHolder);

		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}


		viewHolder.txtSelectAddress.setText(mlist.get(pos).getRegion_name());
//		viewHolder.ivIcon.setImageResource(mlist.get(pos).getBankResId());
		return convertView;
	}

	public static class ViewHolder {
		public TextView txtSelectAddress,labCarNo,labStyle;
//		public TextView delete_action;
//		public ImageView ivIcon;
	}

}
