package com.bayue.live.deqingpu.adapter.cart;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CartConfirmAdapter1 extends RecyclerView.Adapter<CartConfirmAdapter1.MyHolder> {


    public class MyHolder extends RecyclerView.ViewHolder {


        public MyHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public CartConfirmAdapter1.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_orderin,null);//解决显示不全
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CartConfirmAdapter1.MyHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
