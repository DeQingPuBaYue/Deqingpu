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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/9.
 */

public class RizhiAdapter extends BaseAdapter {
    Context context;
    List<RizhiBean.DataBean> arrayList;

    public RizhiAdapter(Context context, List<RizhiBean.DataBean> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList!=null?arrayList.size():0;
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
            view = LayoutInflater.from(context).inflate(R.layout.geren_itme_rizhi, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        RizhiBean.DataBean dataBean=arrayList.get(position);
        String time=dataBean.getAdd_time();

        String ri=time.substring(8,10);
        holder.tvRizhiRi.setText(ri);
        String nianyue=time.substring(0,4)+"."+time.substring(5,7);

        return view;
    }

    class ViewHolder {
        @BindView(R.id.tv_rizhi_ri)
        TextView tvRizhiRi;
        @BindView(R.id.tv_rizhi_nianyue)
        TextView tvRizhiNianyue;
        @BindView(R.id.tv_rizhi_desc)
        TextView tvRizhiDesc;
        @BindView(R.id.tv_rizhi_pic)
        ImageView tvRizhiPic;
        @BindView(R.id.tv_rizhi_time)
        TextView tvRizhiTime;
        @BindView(R.id.iv_xing)
        ImageView ivXing;
        @BindView(R.id.tv_shu)
        TextView tvShu;
        @BindView(R.id.iv_zhuanzai)
        ImageView ivZhuanzai;
        @BindView(R.id.tv_yijian)
        TextView tvYijian;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
