package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bayue.live.deqingpu.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/9.
 */

public class RizhiAdapter extends BaseAdapter {
    Context context;
    ArrayList arrayList;

    public RizhiAdapter(Context context, ArrayList arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return 4;
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

        view= LayoutInflater.from(context).inflate(R.layout.geren_itme_rizhi,null);



        return view;
    }
}
