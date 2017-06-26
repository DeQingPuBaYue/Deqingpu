package com.bayue.live.deqingpu.adapter.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.ui.geren.CartActivity;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.glide.GlideCircleTransform;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.AmountView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/21.
 */

public class recyclerAdapter1 extends RecyclerView.Adapter<recyclerAdapter1.MyHolder> {

    List<CartOutBean.DataBean.GoodsInfoBean> list;
    Context context;
    RequestManager glideRequest;

    public recyclerAdapter1(Context context, List<CartOutBean.DataBean.GoodsInfoBean> list) {
        this.list = list;
        this.context=context;
        glideRequest= Glide.with(context);
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        View view_xian_item_cart;
        ImageView ivXuanzeItemCartin;
        ImageView ivGoodsItemCartin;
        ImageView ivTypeItemCartin;
        TextView tvGoodsnameItemCartin;
        TextView tvGoodsattrItemCartin;
        TextView tvIsmailItemCartin;
        TextView tvGoodspriceItemCartin;
        TextView tvGoodsnumberItemCartin;
        AutoLinearLayout llXxItemCart;
        AmountView avNumberItemCart;
        TextView tvAttrItemCart;
        AutoRelativeLayout rlAttrItemCart;
        TextView tvDelItemCart;
        AutoLinearLayout llBianjiItemCart;
        LinearLayout ll_xuanze_item_cartin;
        AmountView amountView;



        public MyHolder(View itemView) {
            super(itemView);
            ivXuanzeItemCartin= (ImageView) itemView.findViewById(R.id.iv_xuanze_item_cartin);
            ivGoodsItemCartin= (ImageView) itemView.findViewById(R.id.iv_goods_item_cartin);
            ivTypeItemCartin= (ImageView) itemView.findViewById(R.id.iv_type_item_cartin);
            tvGoodsnameItemCartin= (TextView) itemView.findViewById(R.id.tv_goodsname_item_cartin);
            tvGoodsattrItemCartin= (TextView) itemView.findViewById(R.id.tv_goodsattr_item_cartin);
            tvIsmailItemCartin= (TextView) itemView.findViewById(R.id.tv_ismail_item_cartin);
            tvGoodspriceItemCartin= (TextView) itemView.findViewById(R.id.tv_goodsprice_item_cartin);
            tvGoodsnumberItemCartin= (TextView) itemView.findViewById(R.id.tv_goodsnumber_item_cartin);
            llXxItemCart= (AutoLinearLayout) itemView.findViewById(R.id.ll_xx_item_cart);
            avNumberItemCart= (AmountView) itemView.findViewById(R.id.av_number_item_cart);
            tvAttrItemCart= (TextView) itemView.findViewById(R.id.tv_attr_item_cart);
            rlAttrItemCart= (AutoRelativeLayout) itemView.findViewById(R.id.rl_attr_item_cart);
            tvDelItemCart= (TextView) itemView.findViewById(R.id.tv_del_item_cart);
            llBianjiItemCart= (AutoLinearLayout) itemView.findViewById(R.id.ll_bianji_item_cart);
            view_xian_item_cart=itemView.findViewById(R.id.view_xian_item_cart);
            ll_xuanze_item_cartin= (LinearLayout) itemView.findViewById(R.id.ll_xuanze_item_cartin);
            amountView= (AmountView) itemView.findViewById(R.id.av_number_item_cart);

        }
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_in, null);//解决显示不全
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        final CartOutBean.DataBean.GoodsInfoBean  goodsInfoBean=list.get(position);

            //是否选中
            if(goodsInfoBean.isSubselected()){
                holder.ivXuanzeItemCartin.setImageResource(R.drawable.icon_38);
//                list.get(position).setSubselected(!list.get(position).isSubselected());
            }else {
                holder.ivXuanzeItemCartin.setImageResource(R.drawable.icon_39);
//                list.get(position).setSubselected(!list.get(position).isSubselected());
            }
            //选中监听
        holder.ll_xuanze_item_cartin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Selected!=null)
                Selected.Selected(position);
            }
        });
        //editor 是否show
            if(!goodsInfoBean.isSubeditor()){
                holder.llXxItemCart.setVisibility(View.VISIBLE);
                holder.llBianjiItemCart.setVisibility(View.GONE);

            }else {
                holder.llXxItemCart.setVisibility(View.GONE);
                holder.llBianjiItemCart.setVisibility(View.VISIBLE);
            }
            //数量监听
        holder.amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                Selected.setAmountView(amount,position);
            }
        });
        //控件赋值；

        //商品图片
        glideRequest.load(goodsInfoBean.getGoods_thumb())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .transform(new GlideRoundTransform(context,0))
//                .transform(new GlideCircleTransform(context))
                .into(holder.ivGoodsItemCartin);
        //商品名
        holder.tvGoodsnameItemCartin.setText(goodsInfoBean.getGoods_name());

        //商品规格
        List<CartOutBean.DataBean.GoodsInfoBean.GoodsAttrBean> attrs=goodsInfoBean.getGoods_attr();
        StringBuffer attr=new StringBuffer();
        for (int i = 0; i < attrs.size(); i++) {
            attr.append(attrs.get(i).getAttr()+" ");
        }
        holder.tvGoodsattrItemCartin.setText(attr.toString());
        holder.tvAttrItemCart.setText(attr.toString());


        //商品价格
        holder.tvGoodspriceItemCartin.setText("￥"+goodsInfoBean.getShop_price());

        //商品数量
        holder.tvGoodsnumberItemCartin.setText("×"+goodsInfoBean.getGoods_number());
        holder.amountView.setDefault(goodsInfoBean.getGoods_number());
        //弹出规格
        holder.rlAttrItemCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CartActivity)context).showPop(Integer.parseInt(goodsInfoBean.getGoods_id()), position);

            }
        });
    }

    @Override
    public int getItemCount(){
        return list!=null? list.size():3;
    }

    private recyclerAdapter1.Selected Selected;
    public void setSelected( recyclerAdapter1.Selected Selected){
        this.Selected=Selected;

    }
    public interface Selected{

        void Selected(int subposition);

        void deitor(int subposition);
        void setAmountView(int amount,int subPosition);
        void delItem();
        void setAttr();

    }



}
