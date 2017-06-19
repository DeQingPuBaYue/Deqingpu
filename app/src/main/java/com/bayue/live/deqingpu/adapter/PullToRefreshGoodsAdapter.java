package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.GoodsBean;
import com.bayue.live.deqingpu.entity.MerchantFood;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Utils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class PullToRefreshGoodsAdapter extends BaseQuickAdapter<GoodsBean.DataBean, BaseViewHolder>{
    List<GoodsBean.DataBean> data = new ArrayList<>();
    Context context;
    public PullToRefreshGoodsAdapter(Context context, List<GoodsBean.DataBean> data) {
        super( R.layout.item_merchant_food, data);
        this.data = data;
        this.context = context;
    }
    //默认第几项
    private int mPosition = -1;

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsBean.DataBean item) {
//        if (mPosition == helper.getLayoutPosition() || item.getDefaultX() == 1){
//            helper.setImageResource(R.id.imgDefault,R.mipmap.icon_52);
//        } else {
//            helper.setImageResource(R.id.imgDefault,R.mipmap.icon_51);
//        }
//        helper.addOnClickListener(R.id.layDefault);
//        helper.addOnClickListener(R.id.txtEdit);
//        helper.addOnClickListener(R.id.txtDel);
//        ((TextView)helper.getView(R.id.txtStoreName)).setText(item.getStore_name());
//        ((TextView)helper.getView(R.id.txtBusiness)).setText(item.getBusiness());
//        ((TextView)helper.getView(R.id.txtRegionName)).setText(item.getRegion_name());
//        ((TextView)helper.getView(R.id.txtDistance)).setText(item.getDistance()+"m");
        int sales = 0;
        if (item.getSales()>0){
            sales = item.getSales();
        }
        ((TextView)helper.getView(R.id.txtCount)).setText(sales + "人消费");
//        ((TextView)helper.getView(R.id.txtDiscount)).setText(item.getActivity());
//        Glide.with(context)
//                .load(item.getStore_avatar())
//                .asBitmap()
//                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
//                .into((ImageView) helper.getView(R.id.imgMerchantShop));
//        String msg="\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
//        ( (TextView)helper.getView(R.id.txtAddressDetail)).setText(SpannableStringUtils.getBuilder(msg)
//                .append("landscapes and nedes").setClickSpan(clickableSpan).create());
//        ( (TextView)helper.getView(R.id.txtAddressDetail)).setMovementMethod(LinkMovementMethod.getInstance());

    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);
        }
    };

}
