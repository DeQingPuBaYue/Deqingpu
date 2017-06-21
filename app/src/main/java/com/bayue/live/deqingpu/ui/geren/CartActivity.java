package com.bayue.live.deqingpu.ui.geren;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.cart.recyclerAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/20.
 */

public class CartActivity extends BaseActivity {


    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.tv_rigthtext_title)
    TextView tvRigthtextTitle;
    @BindView(R.id.ll_rigthtext_title)
    LinearLayout llRigthtextTitle;
    @BindView(R.id.ll_quanxuan_cart)
    LinearLayout llQuanxuanCart;
    @BindView(R.id.tv_zongjine_cart)
    TextView tvZongjineCart;
    @BindView(R.id.ll_lijigoumai_cart)
    LinearLayout llLijigoumaiCart;
    @BindView(R.id.rlv_out_cart)
    RecyclerView rlvOutCart;
    @BindView(R.id.iv_quanxuan_cart)
    ImageView ivQuanxuanCart;


    private recyclerAdapter adapter;
    private RecyclerView.LayoutManager manager;

    ArrayList<CartOutBean> outList=new ArrayList<>();

    private void addOutList(){

        for (int i = 0; i <5 ; i++) {
            CartOutBean cartOutBean=new CartOutBean();
            cartOutBean.setSelected(true);
            cartOutBean.setEditor(false);
            outList.add(cartOutBean);
        }

    }


    public static boolean selected=true;
    @Override
    protected int getViewId() {
        return R.layout.cart_activity;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("我的购物车");
        tvRigthtextTitle.setText("编辑");

        manager = new LinearLayoutManager(this);
        rlvOutCart.setLayoutManager(manager);
        //优化性能
        rlvOutCart.setHasFixedSize(true);
        addOutList();
        adapter = new recyclerAdapter(outList);
        rlvOutCart.setAdapter(adapter);
        adapter.setAllSelected(new recyclerAdapter.AllSelected() {
            @Override
            public void allSelected(int position) {
                if(outList.get(position).isSelected()){

                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    UpdateRecyclerView();
                }else {
                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    UpdateRecyclerView();
                }

            }

            @Override
            public void deitor(int position) {
                if(outList.get(position).isEditor()){

                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    UpdateRecyclerView();
                }else {
                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    UpdateRecyclerView();
                }
            }
        });


    }

    private void UpdateRecyclerView() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
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

    @OnClick({R.id.ll_back_title, R.id.ll_rigthtext_title, R.id.ll_quanxuan_cart, R.id.ll_lijigoumai_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.ll_rigthtext_title:
                break;
            case R.id.ll_quanxuan_cart:
                setQuanXuan();

                break;
            case R.id.ll_lijigoumai_cart:
                break;
        }
    }

    private void setQuanXuan() {
        if(selected){
            ivQuanxuanCart.setImageResource(R.drawable.icon_39);
            selected=false;
        }else {
            ivQuanxuanCart.setImageResource(R.drawable.icon_38);
            selected=true;

        }

    }


}
