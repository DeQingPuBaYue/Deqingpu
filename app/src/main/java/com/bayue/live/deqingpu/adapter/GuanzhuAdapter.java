package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.geren.RizhiBean;
import com.bayue.live.deqingpu.fragment.geren.QuanziFragmentGuanzhu;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/9.
 */

public class GuanzhuAdapter extends BaseAdapter {
    Context context;
    List<RizhiBean.DataBean> arrayList;

    public GuanzhuAdapter(Context context, List<RizhiBean.DataBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList!=null?arrayList.size():4;
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.geren_item_guanzhu, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        return view;
    }

    class ViewHolder {


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
