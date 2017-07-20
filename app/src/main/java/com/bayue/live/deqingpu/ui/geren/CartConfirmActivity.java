package com.bayue.live.deqingpu.ui.geren;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.cart.CartConfirmAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.cart.CartDone;
import com.bayue.live.deqingpu.entity.cart.CartSettlement;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.AddressActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.ConfirmPayActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/22.
 */

public class CartConfirmActivity extends BaseActivity {
    String TAG = "CartConfirm";
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
    //    @BindView(R.id.card_view)
//    CardView cardView;
//    @BindView(R.id.rlv_out_order)
//    RecyclerView rlvOutOrder;
    @BindView(R.id.iv_quanxuan_cart)
    ImageView ivQuanxuanCart;
    @BindView(R.id.ll_quanxuan_cart)
    LinearLayout llQuanxuanCart;
    @BindView(R.id.ll_lijigoumai_cart)
    LinearLayout llLijigoumaiCart;

    RecyclerView.LayoutManager manager;
    @BindView(R.id.txtCartSellerMsg)
    EditText txtCartSellerMsg;
    @BindView(R.id.tv_zongjine_cart)
    TextView tvZongjineCart;
    @BindView(R.id.txtCartGoodsPrice)
    TextView txtCartGoodsPrice;
    List<CartSettlement.DataBean.GoodsBean> goodsBeanList = new ArrayList<>();
    @BindView(R.id.rlv_out_order)
    RecyclerView rlvOutOrder;
    //    @BindView(R.id.linDefaultArea)
    CardView linDefaultArea;
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.ivCartGoodsPic)
    ImageView ivCartGoodsPic;
    @BindView(R.id.txtCartGoodsName)
    TextView txtCartGoodsName;
    @BindView(R.id.txtCartGoodsAttr)
    TextView txtCartGoodsAttr;
    @BindView(R.id.txtCartGoodsNumb)
    TextView txtCartGoodsNumb;
    @BindView(R.id.txtCartGoodsItemPrice)
    TextView txtCartGoodsItemPrice;
    LinearLayout linGoodsCart;

    CartConfirmAdapter adapter;
    CartSettlement settlement;
    RequestManager glideRequest;
    public static Activity instance;

    @Override
    protected int getViewId() {
        return R.layout.cart_order_activity;
    }

    String recId, sellerMsg, attrIds;
    int addressId, actionType = 0;//actionType :0 -》 商家 ？ 1 -》 生活馆 ------ 2 购物车
    int goods_id, number, store_id;

    @Override
    protected void initViews() {
        instance = this;
        tvZongjineCart.setVisibility(View.GONE);
        topBar.setTitle(getString(R.string.title_order_confirm));
        glideRequest =  Glide.with(baseContext);
        linDefaultArea = (CardView) findViewById(R.id.linDefaultArea);
        linGoodsCart = (LinearLayout) findViewById(R.id.linGoodsCart);
        recId = getIntent().getStringExtra("rec_id");
        attrIds = getIntent().getStringExtra("spec");
        actionType = getIntent().getIntExtra("actionType", 0);
        number = getIntent().getIntExtra("number", 0);
        goods_id = getIntent().getIntExtra("goods_id", 0);
        store_id = getIntent().getIntExtra("store_id", 0);

        switch (actionType) {
            case 0:
                linDefaultArea.setVisibility(View.GONE);
            case 1:
//                rlvOutOrder.setVisibility(View.GONE);
//                linGoodsCart.setVisibility(View.VISIBLE);
//                checkOut();
//                break;
                rlvOutOrder.setVisibility(View.GONE);
                linGoodsCart.setVisibility(View.VISIBLE);
                checkOut();
                break;
            case 2:
                break;
        }

//        if (Guard.isNullOrEmpty(recId)) {
//            ToastUtils.showLongToast("没有选择物品");
//            return;
//        }

        txtDel.setVisibility(View.INVISIBLE);
        llQuanxuanCart.setVisibility(View.GONE);
        txtCartGoodsPrice.setVisibility(View.VISIBLE);
        imgDefault.setImageResource(R.mipmap.icon_52);
        if (rlvOutOrder.getVisibility() == View.VISIBLE) {
            initAdapter();
        }
    }

    private void checkOut(){
        Map<String, Object> map = Constants.getMap(baseContext);
        map.put("goods_id", goods_id);
        map.put("number", number);
        map.put("spec", attrIds);
        map.put("store_id", store_id);
//        Constants.LogMap(map);
        beginGet(API.Merchant.GOODS_CHECK, map, 1);
    }

    private void initAdapter() {
        adapter = new CartConfirmAdapter(baseContext, goodsBeanList);
        manager = new LinearLayoutManager(this);
        rlvOutOrder.setLayoutManager(manager);
        //优化性能
        rlvOutOrder.setHasFixedSize(true);
        //分割线
//        rlvOutOrder.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));
        rlvOutOrder.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (actionType == 2 && !Guard.isNullOrEmpty(recId)) {
            Map<String, Object> map = Constants.getMap(baseContext);
            map.put("rec_id", recId);
            beginGet(API.Cart.CHECKOUT, map, 2);
        }else if ((actionType == 1||actionType == 0) && goods_id > 0){
            checkOut();
        }
    }

    private void beginGet(final String url, final Map<String, Object> map, final int type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(url, map, type);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int type) {
        HTTPUtils.getNovate(baseContext).post(url,
                hashMap,
                new BaseSubscriber<ResponseBody>(baseActivity) {
                    @Override
                    public void onError(Throwable e) {
                        if (e.getMessage() != null) {
                            Tracer.e("OkHttp", e.getMessage());
                            ToastUtils.showShortToast(e.getMessage());
                        }
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        String jstr = null;
                        try {
                            jstr = new String(responseBody.bytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Tracer.e(TAG + type, jstr);
                        if (!jstr.contains("code")) {
                            ToastUtils.showLongToast(getString(R.string.net_user_error));
                            return;
                        }
                        switch (type) {
                            case 5:
                                CartDone r = (CartDone) GsonHelper.getInstanceByJson(CartDone.class, jstr);
                                if (Guard.isNull(r)) {
                                    return;
                                }
                                startActivity(new Intent(baseContext, ConfirmPayActivity.class)
                                        .putExtra("goods_amount", r.getData().getOrder_amount()+"")
                                        .putExtra("order_sn", r.getData().getOrder_sn())
                                        .putExtra("order_id", r.getData().getOrder_id())
                                        .putExtra("actionType", actionType)
                                );
                                break;
                            case 1:
                                settlement = (CartSettlement) GsonHelper.getInstanceByJson(CartSettlement.class, jstr);
                                if (Guard.isNull(settlement)) {
                                    return;
                                }
                                if (settlement.getCode() == Constants.CODE_OK) {
                                    CartSettlement.DataBean.UserAddressBean addressBean = settlement.getData().getUser_address();
                                    if (!Guard.isNull(addressBean)) {
                                        setDefaultArea(addressBean);
                                    }
                                    glideRequest.load(settlement.getData().getGoods().get(0).getGoods_info().get(0).getGoods_thumb())
                                            .placeholder(R.mipmap.ic_launcher_round)
                                            .error(R.mipmap.ic_launcher_round)
                                            .transform(new GlideRoundTransform(baseContext, 0))
//                .transform(new GlideCircleTransform(context))
                                            .into(ivCartGoodsPic);
                                    CartSettlement.DataBean.GoodsBean.GoodsInfoBean infoBean = settlement.getData().getGoods().get(0).getGoods_info().get(0);
                                    //商品规格
                                    List<CartSettlement.DataBean.GoodsBean.GoodsInfoBean.GoodsAttrBean> attrs = infoBean.getGoods_attr();
                                    StringBuffer attr = new StringBuffer();
                                    for (int i = 0; i < attrs.size(); i++) {
                                        attr.append(attrs.get(i).getAttr() + " ");
                                    }
                                    txtCartGoodsAttr.setText("规格：" + attr.toString());
                                    txtCartGoodsName.setText(infoBean.getGoods_name());
                                    txtCartGoodsItemPrice.setText(infoBean.getShop_price());
                                    txtCartGoodsNumb.setText("×" + infoBean.getGoods_number());
                                }else {
                                    String errorMsg = GsonHelper.getStrFromJson(jstr, "msg");
                                    ToastUtils.showShortToast(errorMsg);
                                }
                                break;
                            case 2:
                                settlement = (CartSettlement) GsonHelper.getInstanceByJson(CartSettlement.class, jstr);
                                if (Guard.isNull(settlement)) {
                                    return;
                                }
                                if (settlement.getCode() == Constants.CODE_OK) {
                                    CartSettlement.DataBean.UserAddressBean addressListBean = settlement.getData().getUser_address();
                                    if (!Guard.isNull(addressListBean)) {
                                        setDefaultArea(addressListBean);
                                    }
                                    goodsBeanList.clear();
                                    goodsBeanList.addAll(settlement.getData().getGoods());
                                    adapter.notifyDataSetChanged();
                                }else {
                                    String errorMsg = GsonHelper.getStrFromJson(jstr, "msg");
                                    ToastUtils.showShortToast(errorMsg);
                                }
                                break;
                        }
                    }
                });
    }

    private void setDefaultArea(CartSettlement.DataBean.UserAddressBean addressBean){

        addressId = addressBean.getAddress_id();
        txtConsignee.setText(addressBean.getConsignee());
        txtTell.setText(addressBean.getMobile());
        String address = addressBean.getProvince_name() + addressBean.getCity_name() + addressBean.getDistrict_name() + addressBean.getAddress();
        txtAddressDetail.setText("收货地址：" + address);
        int price = 0;
        if (settlement.getData().getPay_price() > 0) {
            price = settlement.getData().getPay_price();
        }
        Tracer.e(TAG, "cart price" + price);
        txtCartGoodsPrice.setText(price + "");
    }

    @OnClick({R.id.txtEdit, R.id.txtDel, R.id.ll_lijigoumai_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtEdit:
                startActivity(new Intent(baseContext, AddressActivity.class).putExtra("form", "cart_confirm"));
                break;
            case R.id.ll_lijigoumai_cart:
                sellerMsg = txtCartSellerMsg.getText().toString();
                if (actionType == 2){
                    cartBuy();
                }else {
                    goodsBuy();
                }
                break;
        }
    }

    //商家、生活馆商品购买
    private void goodsBuy(){

        Map<String, Object> map = Constants.getMap(baseContext);
        map.put("number", number);
        map.put("spec", attrIds);
        map.put("store_id", store_id);
        map.put("goods_id", goods_id);
        map.put("address_id ", addressId);
        map.put("note", sellerMsg);
//                Constants.LogMap(map);
        beginGet(API.Merchant.GOODS_BUY, map, 5);
    }

    //购物车购买
    private void cartBuy(){

        Map<String, Object> map = Constants.getMap(baseContext);
        map.put("rec_id", recId);
        map.put("address_id", addressId);
        map.put("note", sellerMsg);
                Constants.LogMap(map);
        beginGet(API.Cart.CART_BUY, map, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.CODE_OK) {
            if (requestCode == 100) {
                int tempId = data.getIntExtra("address_id", 0);
                if (tempId > 0) {
                    addressId = tempId;
                }
            }
        }
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
