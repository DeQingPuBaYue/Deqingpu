package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.view.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VULCAN on 2017/6/11.
 */

public class KejianYonghuAdapter extends BaseAdapter {
    Context context;
    ArrayList list;

    public KejianYonghuAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.geren_itme_kejian, null);
            holder=new ViewHolder(convertView);
            convertView.setTag(holder);


        }else{
            holder= (ViewHolder) convertView.getTag();
        }


        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.iv_touxiang_itme)
        CircleImageView ivTouxiangItme;
        @BindView(R.id.iv_name_itme)
        TextView ivNameItme;
        @BindView(R.id.iv_xuanze_itme)
        ImageView ivXuanzeItme;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
