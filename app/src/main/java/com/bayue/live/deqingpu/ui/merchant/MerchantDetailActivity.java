package com.bayue.live.deqingpu.ui.merchant;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.StoreDetail;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BaYue on 2017/6/14.
 * email : 2651742485@qq.com
 */

public class MerchantDetailActivity extends BaseActivity {

    String TAG = "MerchantDetail";
    String json = "";
    StoreDetail storeDetail;
    @BindView(R.id.imgMerchantDetailBanner)
    ImageView imgMerchantDetailBanner;
    @BindView(R.id.txtMerchantDetailStoreName)
    TextView txtMerchantDetailStoreName;
    @BindView(R.id.txtMerchantDetailCount)
    TextView txtMerchantDetailCount;
    @BindView(R.id.btnMerchantCheck)
    Button btnMerchantCheck;
    @BindView(R.id.txtMerchantDetailLocation)
    TextView txtMerchantDetailLocation;
    @BindView(R.id.txtMerchantCall)
    TextView txtMerchantCall;
    @BindView(R.id.txtJumpCall)
    TextView txtJumpCall;
    @BindView(R.id.imgMerchantShopAvator)
    ImageView imgMerchantShopAvator;
    @BindView(R.id.txtMerchantAvatorShop)
    TextView txtMerchantAvatorShop;
    @BindView(R.id.txtMerchantAvatorBusi)
    TextView txtMerchantAvatorBusi;
    @BindView(R.id.txtMerchantGoodsCount)
    TextView txtMerchantGoodsCount;
    @BindView(R.id.linMerchantGoods)
    LinearLayout linMerchantGoods;
    @BindView(R.id.txtNotice)
    TextView txtNotice;
    @BindView(R.id.txtShowCommentSlide)
    TextView txtShowCommentSlide;
    @BindView(R.id.txtFavorableRate)
    TextView txtFavorableRate;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    @BindView(R.id.txtCommentAll)
    TextView txtCommentAll;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String storePhone = "";
    @Override
    protected int getViewId() {
        return R.layout.ac_merchant;
    }

    @Override
    protected void initViews() {
        json = getIntent().getStringExtra("json");
        Tracer.e(TAG, json);
        storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, json);
        if (!Guard.isNull(storeDetail)) {
            StoreDetail.DataBean bean = storeDetail.getData();
            storePhone = bean.getPhone();
            Glide.with(baseActivity).load(bean.getStore_avatar())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round).into(imgMerchantDetailBanner);
            Glide.with(baseActivity).load(bean.getStore_avatar())
                    .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).into(imgMerchantShopAvator);
            txtMerchantDetailStoreName.setText(bean.getStore_name());
            txtMerchantDetailCount.setText(bean.getSales()+"人消费");
            txtMerchantDetailLocation.setText(bean.getStore_address());
            txtMerchantAvatorShop.setText(bean.getStore_name());
            txtMerchantAvatorBusi.setText(bean.getName());
            txtMerchantGoodsCount.setText(bean.getNumber()+"\n" +"全部商品");
            txtNotice.setText(bean.getNote());
        }
        initComment();
    }

    private void initComment() {

    }

    @OnClick({R.id.btnMerchantCheck, R.id.txtMerchantCall, R.id.txtJumpCall, R.id.linMerchantGoods, R.id.txtCommentAll})
    void setOnClick(View view){

    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
