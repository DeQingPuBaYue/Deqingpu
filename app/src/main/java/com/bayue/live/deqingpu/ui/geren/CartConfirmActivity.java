package com.bayue.live.deqingpu.ui.geren;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.cart.CartConfirmAdapter;
import com.bayue.live.deqingpu.adapter.cart.recyclerAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/22.
 */

public class CartConfirmActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.tv_rigthtext_title)
    TextView tvRigthtextTitle;
    @BindView(R.id.ll_rigthtext_title)
    LinearLayout llRigthtextTitle;
    @BindView(R.id.txtConsignee)
    TextView txtConsignee;
    @BindView(R.id.txtTell)
    TextView txtTell;
    @BindView(R.id.txtAddressDetail)
    TextView txtAddressDetail;
    @BindView(R.id.imgDefault)
    ImageView imgDefault;
    @BindView(R.id.layDefault)
    LinearLayout layDefault;
    @BindView(R.id.txtEdit)
    TextView txtEdit;
    @BindView(R.id.txtDel)
    TextView txtDel;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.rlv_out_order)
    RecyclerView rlvOutOrder;
    @BindView(R.id.iv_quanxuan_cart)
    ImageView ivQuanxuanCart;
    @BindView(R.id.ll_quanxuan_cart)
    LinearLayout llQuanxuanCart;
    @BindView(R.id.tv_zongjine_cart)
    TextView tvZongjineCart;
    @BindView(R.id.ll_lijigoumai_cart)
    LinearLayout llLijigoumaiCart;

    CartConfirmAdapter adapter;
    RecyclerView.LayoutManager manager;


    @Override
    protected int getViewId() {
        return R.layout.cart_order_activity;
    }

    @Override
    protected void initViews() {
        adapter=new CartConfirmAdapter(null);

        manager= new LinearLayoutManager(this);

        rlvOutOrder.setLayoutManager(manager);
        //优化性能
        rlvOutOrder.setHasFixedSize(true);
        rlvOutOrder.setAdapter(adapter);


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

    @OnClick({R.id.ll_back_title, R.id.ll_rigthtext_title, R.id.txtEdit, R.id.txtDel, R.id.ll_lijigoumai_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.ll_rigthtext_title:
                break;
            case R.id.txtEdit:
                break;
            case R.id.txtDel:
                break;
            case R.id.ll_lijigoumai_cart:
                break;
        }
    }
}
