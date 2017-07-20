package com.bayue.live.deqingpu.adapter.cart;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.entity.cart.CartSettlement;
import com.bayue.live.deqingpu.utils.Guard;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CartConfirmAdapter extends RecyclerView.Adapter<CartConfirmAdapter.MyHolder> {

    List<CartSettlement.DataBean.GoodsBean> list=new ArrayList<>();
    Context context;

    public CartConfirmAdapter(Context context, List<CartSettlement.DataBean.GoodsBean> outList) {
        this.list = outList;
        this.context = context;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        RecyclerView rlv_in_order;
        CartConfirmAdapter1 adapter1;
        TextView tv_name_cartitem,tv_jine_order, txtCartGoodsNumb;
        ImageView ivCartShopMark;
        RecyclerView.LayoutManager manager;


        public MyHolder(View itemView) {
            super(itemView);
            rlv_in_order= (RecyclerView) itemView.findViewById(R.id.rlv_in_order);
            tv_name_cartitem= (TextView) itemView.findViewById(R.id.tv_name_cartitem);
            tv_jine_order= (TextView) itemView.findViewById(R.id.tv_jine_order);
            txtCartGoodsNumb= (TextView) itemView.findViewById(R.id.txtCartGoodsNumb);
            ivCartShopMark= (ImageView) itemView.findViewById(R.id.ivCartShopMark);
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
        int goodsNumb = list.get(position).getGoods_count_number();
        String price = "";
        if (!Guard.isNullOrEmpty(list.get(position).getGoods_count_price()+"")){
            price = list.get(position).getGoods_count_price()+"";
        }
        String merchantName = list.get(position).getMerchant_name();
        if (merchantName.equals(context.getString(R.string.hint_cart_merchant_name_manager))){
            holder.ivCartShopMark.setImageResource(R.mipmap.icon_merchant_manager);
        }else {
            holder.ivCartShopMark.setImageResource(R.mipmap.icon_merchant_platform);
        }
        holder.txtCartGoodsNumb.setText("共"+goodsNumb+"件商品 小计 ￥");
        holder.tv_jine_order.setText(price);
        holder.tv_name_cartitem.setText(merchantName);
        List<CartSettlement.DataBean.GoodsBean.GoodsInfoBean> subList= list.get(position).getGoods_info();
        holder.adapter1=new CartConfirmAdapter1(context, subList);
        holder.rlv_in_order.setAdapter(holder.adapter1);

    }

    @Override
    public int getItemCount(){
        return list!=null?list.size():3;
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
