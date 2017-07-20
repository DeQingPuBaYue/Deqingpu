package com.bayue.live.deqingpu.adapter.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.entity.cart.CartSettlement;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.AmountView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CartConfirmAdapter1 extends RecyclerView.Adapter<CartConfirmAdapter1.MyHolder> {

    List<CartSettlement.DataBean.GoodsBean.GoodsInfoBean> list;
    RequestManager glideRequest;
    Context context;
    public CartConfirmAdapter1(Context context, List<CartSettlement.DataBean.GoodsBean.GoodsInfoBean> list) {
        this.list = list;
        this.context=context;
        glideRequest= Glide.with(context);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivCartGoodsPic;
        TextView txtCartGoodsName, txtCartGoodsAttr, txtCartGoodsPrice, txtCartGoodsNumb;
        public MyHolder(View itemView) {
            super(itemView);
            ivCartGoodsPic= (ImageView) itemView.findViewById(R.id.ivCartGoodsPic);
            txtCartGoodsName= (TextView) itemView.findViewById(R.id.txtCartGoodsName);
            txtCartGoodsAttr= (TextView) itemView.findViewById(R.id.txtCartGoodsAttr);
            txtCartGoodsPrice= (TextView) itemView.findViewById(R.id.txtCartGoodsItemPrice);
            txtCartGoodsNumb= (TextView) itemView.findViewById(R.id.txtCartGoodsNumb);
        }
    }


    @Override
    public CartConfirmAdapter1.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cart_settle,null);//解决显示不全
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CartConfirmAdapter1.MyHolder holder, int position) {
        final CartSettlement.DataBean.GoodsBean.GoodsInfoBean  goodsInfoBean=list.get(position);
        //商品图片
        glideRequest.load(goodsInfoBean.getGoods_thumb())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new GlideRoundTransform(context,0))
//                .transform(new GlideCircleTransform(context))
                .into(holder.ivCartGoodsPic);
        //商品名
        holder.txtCartGoodsName.setText(goodsInfoBean.getGoods_name());
        //商品规格
        List<CartSettlement.DataBean.GoodsBean.GoodsInfoBean.GoodsAttrBean> attrs=goodsInfoBean.getGoods_attr();
        StringBuffer attr=new StringBuffer();
        for (int i = 0; i < attrs.size(); i++) {
            attr.append(attrs.get(i).getAttr()+" ");
        }
        holder.txtCartGoodsAttr.setText("规格："+ attr.toString());
        //商品价格
        holder.txtCartGoodsPrice.setText("￥"+goodsInfoBean.getShop_price());
        //商品数量
//        holder.tvGoodsnumberItemCartin.setText("×"+goodsInfoBean.getGoods_number());
        holder.txtCartGoodsNumb.setText("×"+goodsInfoBean.getGoods_number());
    }

    @Override
    public int getItemCount() {
        return list!=null? list.size():0;
    }
}
