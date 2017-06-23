package com.bayue.live.deqingpu.ui.geren;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.cart.recyclerAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    @BindView(R.id.ll_jine_cart)
    LinearLayout llJineCart;
    @BindView(R.id.tv_lijigoumai_cart)
    TextView tvLijigoumaiCart;


    private recyclerAdapter adapter;
    private RecyclerView.LayoutManager manager;

    ArrayList<CartOutBean.DataBean> outList = new ArrayList<>();

    private void addOutList() {
        for (int i = 0; i < 5; i++) {
            CartOutBean.DataBean data = new CartOutBean.DataBean();
            data.setSelected(true);
            data.setEditor(false);
            data.setGoods_info(new ArrayList<CartOutBean.DataBean.GoodsInfoBean>());
            for (int j = 0; j < 3; j++) {
                CartOutBean.DataBean.GoodsInfoBean bean = new CartOutBean.DataBean.GoodsInfoBean();
                bean.setSubselected(true);
                bean.setSubeditor(false);
                data.getGoods_info().add(bean);
            }
            outList.add(data);
        }
    }
    private void setOutList(ArrayList<CartOutBean.DataBean> outList) {
        for (int i = 0; i < outList.size(); i++) {

            outList.get(i).setSelected(true);
            outList.get(i).setEditor(false);

            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
                outList.get(i).getGoods_info().get(j).setSubselected(true);
                outList.get(i).getGoods_info().get(j).setSubeditor(false);
            }
        }
    }

    private void reverseQuanxue(boolean b) {
        for (int i = 0; i < outList.size(); i++) {
//            CartOutBean.DataBean data=new CartOutBean.DataBean();
            outList.get(i).setSelected(b);
//            data.setEditor(false);
//            outList.get(i).setGoods_info(new ArrayList<CartOutBean.DataBean.GoodsInfoBean>());
            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
//                CartOutBean.DataBean.GoodsInfoBean bean=new CartOutBean.DataBean.GoodsInfoBean();
                outList.get(i).getGoods_info().get(j).setSubselected(b);
//                bean.setSubeditor(false);
//                data.getGoods_info().add(bean);
            }
//            outList.add(data);

        }
        UpdateRecyclerView();
    }

    private void reverseBanji(boolean b) {
        for (int i = 0; i < outList.size(); i++) {
//            CartOutBean.DataBean data=new CartOutBean.DataBean();
            outList.get(i).setEditor(b);
//            data.setEditor(false);
//            outList.get(i).setGoods_info(new ArrayList<CartOutBean.DataBean.GoodsInfoBean>());
            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
//                CartOutBean.DataBean.GoodsInfoBean bean=new CartOutBean.DataBean.GoodsInfoBean();
                outList.get(i).getGoods_info().get(j).setSubeditor(b);
//                bean.setSubeditor(false);
//                data.getGoods_info().add(bean);
            }
//            outList.add(data);

        }
        UpdateRecyclerView();
    }


    public static boolean selected = true;
    public static boolean editor = false;

    @Override
    protected int getViewId() {
        return R.layout.cart_activity;
    }

    @Override
    protected void initViews() {
        getList();
        tvTitletextTitle.setText("我的购物车");
        tvRigthtextTitle.setText("编辑");

        manager = new LinearLayoutManager(this);
        rlvOutCart.setLayoutManager(manager);
        //优化性能
        rlvOutCart.setHasFixedSize(true);
//        addOutList();
        adapter = new recyclerAdapter(this,outList);
        rlvOutCart.setItemAnimator(new DefaultItemAnimator());
        rlvOutCart.setAdapter(adapter);
        adapter.setAllSelected(new recyclerAdapter.AllSelected() {
            @Override
            public void allSelected(int position) {
                if (outList.get(position).isSelected()) {

                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubselected(outList.get(position).isSelected());
                    }
                    UpdateRecyclerView();
                } else {
                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubselected(outList.get(position).isSelected());
                    }
                    UpdateRecyclerView();
                }

            }

            @Override
            public void deitor(int position) {
                if (outList.get(position).isEditor()) {
                    Log.e("是否11111===", outList.get(position).isEditor() + "");
                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubeditor(outList.get(position).isEditor());
                        Log.e("子item是否==22=", outList.get(position).getGoods_info().get(i).isSubeditor() + "");
                    }
                    UpdateRecyclerView();
                } else {
                    Log.e("是否22222===", outList.get(position).isEditor() + "");
                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubeditor(outList.get(position).isEditor());
                        Log.e("子item是否==22=", outList.get(position).getGoods_info().get(i).isSubeditor() + "");
                    }
                    UpdateRecyclerView();
                }
            }
        });


    }

    public void UpdateRecyclerView() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
        DecimalFormat format = new DecimalFormat("#.##");
        tvZongjineCart.setText(format.format(getMoney()));

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
                setBianji();
                break;
            case R.id.ll_quanxuan_cart:
                setQuanXuan();

                break;
            case R.id.ll_lijigoumai_cart:
                startActivity(new Intent(CartActivity.this, CartConfirmActivity.class));

                break;
        }
    }

    private void setQuanXuan() {
        if (selected) {
            ivQuanxuanCart.setImageResource(R.drawable.icon_39);
            selected = false;
            reverseQuanxue(selected);
        } else {
            ivQuanxuanCart.setImageResource(R.drawable.icon_38);
            selected = true;
            reverseQuanxue(selected);
        }
    }

    private void setBianji() {
        if (editor) {

            editor = false;
            tvRigthtextTitle.setText("编辑");
//            reverseBanji(editor);
            llJineCart.setVisibility(View.VISIBLE);
            tvLijigoumaiCart.setText("立即购买");
        } else {
            tvRigthtextTitle.setText("完成");
            editor=true;
            reverseBanji(!editor);
            llJineCart.setVisibility(View.INVISIBLE);
            tvLijigoumaiCart.setText("删除");


        }
    }
    private void getList(){
        Map<String,Object> map=new HashMap();
        map.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMCwibmlrX25hbWUiOm51bGx9.2QDDeG63O4SDRJ6_jqu2iGIB9D9VWZOrq18bhajUIrA");
        HTTPUtils.getNetDATA(API.baseUrl + API.Cart.CART_LIST, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String msg=response.body().string();
                    if(response.code()==200){

                        Gson gson=new Gson();
                        final CartOutBean bean=gson.fromJson(msg,CartOutBean.class);
                        ToolKit.runOnMainThreadSync(new Runnable() {
                            @Override
                            public void run() {
                                if(bean.getCode()==200){
                                    outList.addAll(bean.getData());
//                                    outList= (ArrayList<CartOutBean.DataBean>) bean.getData();
                                    setOutList(outList);
                                    /*DecimalFormat format = new DecimalFormat("#.##");
                                    tvZongjineCart.setText(format.format(getMoney()));
                                    adapter.notifyDataSetChanged();*/
                                    CartActivity.this.UpdateRecyclerView();

                                }else {





                                }

                            }
                        });







                    }

            }
        });





    }
    double tatolMoney=0.0;
    //总金额
    public double getMoney(){
        tatolMoney=0.0;
        for (int i = 0; i <outList.size(); i++) {
           List<CartOutBean.DataBean.GoodsInfoBean> goodsInfoBean= outList.get(i).getGoods_info();
            double money=0;
            for (int j = 0; j < goodsInfoBean.size(); j++) {
                if(goodsInfoBean.get(j).isSubselected()){

                 Double d=Double.parseDouble(goodsInfoBean.get(j).getShop_price())*goodsInfoBean.get(j).getGoods_number();
                    money+=d;
                }
            }
            outList.get(i).setMoney(money);
            tatolMoney+=outList.get(i).getMoney();
        }

        return tatolMoney;

    }

}
