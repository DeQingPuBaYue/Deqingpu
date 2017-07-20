package com.bayue.live.deqingpu.ui.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.adapter.common.wrapper.LoadMoreWrapper;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.OrderDetailBean;
import com.bayue.live.deqingpu.entity.OrderStatusType;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.merchant.pay.ConfirmPayActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.EvaluationActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.RefundActivity;
import com.bayue.live.deqingpu.utils.EnumHelper;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
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
 * Created by BaYue on 2017/7/3 0003.
 * email : 2651742485@qq.com
 */

public class OrderDetailActivity extends BaseActivity {

    String TAG = "OrderDetail";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.tvOrderShippingStatus)
    TextView tvOrderShippingStatus;
    @BindView(R.id.tvOrderRemainingTime)
    TextView tvOrderRemainingTime;
    @BindView(R.id.tvOrderRecieptName)
    TextView tvOrderRecieptName;
    @BindView(R.id.tvOrderPhone)
    TextView tvOrderPhone;
    @BindView(R.id.tvOrderAddress)
    TextView tvOrderAddress;
    @BindView(R.id.ivCartShopMark)
    ImageView ivCartShopMark;
    @BindView(R.id.tvNameCartitem)
    TextView tvNameCartitem;
    @BindView(R.id.rvOrderItem)
    RecyclerView rvOrderItem;
    @BindView(R.id.txtOrderTotalPrice)
    TextView txtOrderTotalPrice;
    @BindView(R.id.tvCustomer)
    TextView tvCustomer;
    @BindView(R.id.btnStart)
    Button btnStart;
    @BindView(R.id.btnMid)
    Button btnMid;
    @BindView(R.id.btnEnd)
    Button btnEnd;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.tvOrderID)
    TextView tvOrderID;
    @BindView(R.id.tvOrderAddTime)
    TextView tvOrderAddTime;
    @BindView(R.id.tvOrderPayTime)
    TextView tvOrderPayTime;
    @BindView(R.id.tvOrderDeliverTime)
    TextView tvOrderDeliverTime;

    @Override
    protected int getViewId() {
        return R.layout.ac_order_detail;
    }

    int orderId;
    CommonAdapter<OrderDetailBean.DataBean.OrderGoodsBean> adapter;
    List<OrderDetailBean.DataBean.OrderGoodsBean> orderDetailList = new ArrayList<>();
    OrderDetailBean orderDetailBean;
    int LOAD_DATA_CANCEL = 0x0001;
    int LOAD_DETAIL = 0x0002;
    int LOAD_REFRESH = 0x0003;
    int LOAD_MORE = 0x0004;
    private Novate novate;
    int page = 1;
    boolean firstLoad = true;
    List<Integer> orderState = new ArrayList<>();
    @Override
    protected void initViews() {
        orderId = getIntent().getIntExtra("order_id", 0);
        orderState = getIntent().getIntegerArrayListExtra("btn");
        topBar.setTitle(getString(R.string.title_order_detail));
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        rvOrderItem.setLayoutManager(new LinearLayoutManager(baseActivity));
        //分割线
        rvOrderItem.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));
        adapter = new CommonAdapter<OrderDetailBean.DataBean.OrderGoodsBean>(baseContext, R.layout.list_item_cart_settle, orderDetailList) {
            @Override
            protected void convert(ViewHolder holder, OrderDetailBean.DataBean.OrderGoodsBean orderGoodsBean, int position) {
                holder.setText(R.id.txtCartGoodsName, orderGoodsBean.getGoods_name());
                StringBuilder attrBuilder = new StringBuilder();
                for (int i = 0; i < orderGoodsBean.getAttr_name().size(); i++) {
                    attrBuilder.append(orderGoodsBean.getAttr_name().get(i).getAttr()).append("\t\t");
                }
                holder.setText(R.id.txtCartGoodsAttr, attrBuilder.toString());
                holder.setText(R.id.txtCartGoodsItemPrice, "￥："+ orderGoodsBean.getGoods_price());
                holder.setText(R.id.txtCartGoodsNumb, "x"+ orderGoodsBean.getGoods_number());
            }
        };
        rvOrderItem.setAdapter(adapter);

        Map<String, Object> map = Constants.getMap();
        map.put("order_id", orderId);
//        Constants.LogMap(map);
        beginGet(API.Order.Order_Detail, map, LOAD_REFRESH, 0);

    }

    private void beginGet(final String url, final Map<String, Object> map, final int loadStatus, final int status) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataFromNet(url, map, loadStatus, status);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int loadStatus, final int status) {
        novate.post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
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
                Tracer.e(TAG, jstr);
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                if (status == 0) {
                    orderDetailBean = (OrderDetailBean) GsonHelper.getInstanceByJson(OrderDetailBean.class, jstr);
                    if (orderDetailBean.getCode() == Constants.CODE_OK) {
                        if (loadStatus == LOAD_REFRESH) {
                            firstLoad = true;
                            page = 1;
                            orderDetailList.clear();
                        }
                        orderDetailList.addAll(orderDetailBean.getData().getOrder_goods());
                        adapter.notifyDataSetChanged();
                        tvNameCartitem.setText(orderDetailBean.getData().getStore_name());
                        txtOrderTotalPrice.setText("合计：￥\t"+orderDetailBean.getData().getGoods_amount()+"(含运费："
                                + orderDetailBean.getData().getGoods_amount()+")");
                        tvOrderShippingStatus.setText(orderDetailBean.getData().getShipping_name());
                        tvOrderRecieptName.setText("收货人："+orderDetailBean.getData().getConsignee());
                        tvOrderPhone.setText(orderDetailBean.getData().getMobile());
                        String address = orderDetailBean.getData().getProvince()
                                +orderDetailBean.getData().getCity()
                                + orderDetailBean.getData().getDistrict()
                                + orderDetailBean.getData().getAddress();
                        tvOrderAddress.setText("收货地址："+ address);
                        tvOrderID.setText(getString(R.string.tv_order_id_tip)+ orderDetailBean.getData().getOrder_sn());
                        tvOrderAddTime.setText(getString(R.string.tv_order_addtime_tip)+ orderDetailBean.getData().getAdd_time());
                        tvOrderPayTime.setText(getString(R.string.tv_order_paytime_tip)+ orderDetailBean.getData().getAdd_time());
                        tvOrderDeliverTime.setText(getString(R.string.tv_order_deliver_tip)+ orderDetailBean.getData().getAdd_time());
                        showBtn(orderState, btnStart, btnMid, btnEnd);

                    }
                } else if (status == LOAD_DATA_CANCEL) {
//                    storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, jstr);
//                    startActivity(new Intent(baseActivity, MerchantDetailActivity.class).putExtra("json", jstr).putExtra("storeId", storeId));
                    Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                    if (r.getCode() == Constants.CODE_OK) {
                        ToastUtils.showLongToast(r.getData());
                    } else {
                        ToastUtils.showLongToast(r.getMsg());
                    }
                }
            }
        });

    }

    @OnClick({R.id.btnStart, R.id.btnMid, R.id.btnEnd})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.btnStart:
//                startActivity(new Intent(baseActivity, OrderLogisticActivity.class).putExtra("invoice_no",orderDetailBean.getData().getInvoice_no()));
//                break;
            case R.id.btnMid:
//                ShowDialog(orderId, 1);
//                break;
            case R.id.btnEnd:
                int OrderType = (int) view.getTag();
//                ShowDialog(orderId, 2);

                listener(OrderType, orderDetailBean.getData().getOrder_id(),
                        orderDetailBean.getData().getStore_id(), orderDetailBean.getData().getGoods_amount(),
                        orderDetailBean.getData().getOrder_sn());
                break;
        }
    }

    private void ShowDialog(final int orderId, final int status){
        String url = "";
        final Map<String, Object>  map = Constants.getMap(baseContext);
        String title = "", content= "";
        switch (status){
            case 1:
                map.put("order_id",orderId);
                title = "取消订单";
                content = "确认取消订单？";
                url = API.Order.Order_Cancel;
                break;
            case 2:
                map.put("order_id",orderId);
                title = "确认收货";
                content = "确认收货？";
                url = API.Order.Order_Confirm;
                break;
        }
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(baseActivity);
        // 设置参数
        final String finalUrl = url;
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton(getString(R.string.btn_confirm), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        beginGet(finalUrl, map, LOAD_REFRESH, LOAD_DATA_CANCEL);

                    }
                })
                .setNegativeButton(getString(R.string.tv_Cancel), new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void showBtn(List<Integer> list, Button btn1, Button btn2, Button btn3){
        switch (list.size()){
            case 3:
                btn1.setTag(list.get(0));
                btn1.setText(EnumHelper.getCnName(list.get(0), OrderStatusType.class));
                btn2.setTag(list.get(1));
                btn2.setText(EnumHelper.getCnName(list.get(1), OrderStatusType.class));
                btn3.setTag(list.get(2));
                btn3.setText(EnumHelper.getCnName(list.get(2), OrderStatusType.class));
                break;
            case 2:
                btn1.setTag(list.get(0));
                btn1.setText(EnumHelper.getCnName(list.get(0), OrderStatusType.class));
                btn2.setTag(list.get(1));
                btn2.setText(EnumHelper.getCnName(list.get(1), OrderStatusType.class));
                btn3.setVisibility(View.GONE);
                break;
            case 1:
                btn1.setTag(list.get(0));
                btn1.setText(EnumHelper.getCnName(list.get(0), OrderStatusType.class));
                btn2.setVisibility(View.GONE);
                btn3.setVisibility(View.GONE);
                break;
        }
    }

    private void OnClickListener(int OrderType, int OrderId, String StoreId, String amount, String order_sn){
        switch (OrderType){
            case 1://取消订单
                ShowDialog(OrderId, 1);
                break;
            case 2://立即评价
                startActivity(new Intent(baseContext, EvaluationActivity.class)
                        .putExtra("action", "order")
                        .putExtra("comment_type", 2)
                        .putExtra("id_value", StoreId)
                );
                break;
            case 3://删除订单
                break;
            case 4://查看物流
                Map<String, Object> map = Constants.getMap();
                map.put("order_id", OrderId);
                beginGet(API.Order.Order_Logistics, map, LOAD_REFRESH, 3);
                break;
            case 5:// 立即付款
                startActivity(new Intent(baseContext, ConfirmPayActivity.class)
                        .putExtra("goods_amount", amount)
                        .putExtra("order_sn", order_sn)
                );
                break;
            case 6://确认收货
                ShowDialog(OrderId, 2);
                break;
            case 7://退货退款
                startActivity(new Intent(baseContext, RefundActivity.class)
                        .putExtra("goods_amount", amount)
                        .putExtra("order_sn", order_sn));
                break;
        }
    }

    private void listener(int OrderType, int OrderId, String StoreId, String amount, String order_sn){
        OnClickListener(OrderType, OrderId,
                StoreId, amount,
                order_sn);
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
