package com.bayue.live.deqingpu.adapter.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CartConfirmAdapter extends RecyclerView.Adapter<CartConfirmAdapter.MyHolder> {

    ArrayList<CartOutBean> outList=new ArrayList<>();

    public CartConfirmAdapter(ArrayList<CartOutBean> outList) {
        this.outList = outList;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        RecyclerView rlv_in_order;
        CartConfirmAdapter1 adapter1;

        RecyclerView.LayoutManager manager;


        public MyHolder(View itemView) {
            super(itemView);
            rlv_in_order= (RecyclerView) itemView.findViewById(R.id.rlv_in_order);
            manager = new LinearLayoutManager(itemView.getContext());
            rlv_in_order.setLayoutManager(manager);


        }
    }



    @Override
    public CartConfirmAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_orderout,null);

        MyHolder holder=new MyHolder(v);




        return holder;
    }

    @Override
    public void onBindViewHolder(CartConfirmAdapter.MyHolder holder, final int position) {

        holder.adapter1=new CartConfirmAdapter1();
        holder.rlv_in_order.setAdapter(holder.adapter1);


    }

    @Override
    public int getItemCount(){
        return outList!=null?outList.size():3;
    }

    private AllSelected allSelected;
    public void setAllSelected( AllSelected allSelected){
        this.allSelected=allSelected;

    }
    public interface AllSelected{

        void allSelected(int position);

        void deitor(int position);

    }

}
