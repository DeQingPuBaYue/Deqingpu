package com.bayue.live.deqingpu.adapter.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;

/**
 * Created by Administrator on 2017/6/21.
 */

public class recyclerAdapter1 extends RecyclerView.Adapter<recyclerAdapter1.MyHolder> {


    public class MyHolder extends RecyclerView.ViewHolder {


        public MyHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public recyclerAdapter1.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_in,null);//解决显示不全
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(recyclerAdapter1.MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
