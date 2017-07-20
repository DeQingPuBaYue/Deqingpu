package com.bayue.live.deqingpu.ui.order;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.adapter.common.wrapper.HeaderAndFooterWrapper;
import com.bayue.live.deqingpu.adapter.common.wrapper.LoadMoreWrapper;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.MerchantFood;
import com.bayue.live.deqingpu.entity.OrderListBean;
import com.bayue.live.deqingpu.entity.OrderStatusType;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.WebViewActivity;
import com.bayue.live.deqingpu.ui.merchant.MerchantGoodsDetailActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.ConfirmPayActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.EvaluationActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.RefundActivity;
import com.bayue.live.deqingpu.utils.EnumHelper;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bayue.live.deqingpu.weidget.WrapContentLinearLayoutManager;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/30 0030.
 * email : 2651742485@qq.com
 * 码农注意，订单列表会报原生的下标越界错误，官方并没有给出什么好的解决办法，建议换LISTVIEW
 */

public class OrderActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    String TAG = "OrderActivity";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.rv_merchant)
    RecyclerView rvMerchant;
    @BindView(R.id.swipeLayout_merchant)
    SwipeRefreshLayout swipeLayoutMerchant;

    CommonAdapter<OrderListBean.DataBean> adapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    List<OrderListBean.DataBean> orderList = new ArrayList<>();
    OrderListBean orderListBean;
    int LOAD_DATA_CANCEL = 0x0001;
    int LOAD_DETAIL = 0x0002;
    int LOAD_REFRESH = 0x0003;
    int LOAD_MORE = 0x0004;
    int page = 1, counte_page;
    boolean firstLoad = true;
    @Override
    protected int getViewId() {
        return R.layout.ac_refresh;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.title_order));

        swipeLayoutMerchant.setOnRefreshListener(this);
        swipeLayoutMerchant.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvMerchant.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));
        rvMerchant.setLayoutManager(new WrapContentLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new CommonAdapter<OrderListBean.DataBean>(baseContext, R.layout.list_item_order, orderList) {
            @Override
            protected void convert(ViewHolder holder, final OrderListBean.DataBean dataBean, int position) {
                final List<Integer> orderState = handlerBtnData(dataBean);
                holder.setText(R.id.tvNameCartitem, dataBean.getStore_name());
                holder.setText(R.id.txtOrderTotalPrice, "合计：￥\t"+dataBean.getGoods_amount()+"(含运费："+ dataBean.getGoods_amount()+")");
                holder.setVisible(R.id.tvCustomer, false);
                holder.setOnClickListener(R.id.btnStart, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int OrderType = (int) v.getTag();
                        OnClickListener(OrderType, dataBean.getOrder_id(),
                                dataBean.getStore(), dataBean.getGoods_amount(),
                                dataBean.getOrder_sn());
                    }
                });
                holder.setOnClickListener(R.id.btnMid, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int OrderType = (int) v.getTag();
                        OnClickListener(OrderType, dataBean.getOrder_id(),
                                dataBean.getStore(), dataBean.getGoods_amount(),
                                dataBean.getOrder_sn());
                    }
                });
                holder.setOnClickListener(R.id.btnEnd, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int OrderType = (int) v.getTag();
                        OnClickListener(OrderType, dataBean.getOrder_id(),
                                dataBean.getStore(), dataBean.getGoods_amount(),
                                dataBean.getOrder_sn());
                    }
                });
                int shipping_status;// 0 未配送 1已配送
                int order_status;//0 订单已取消  1 已经确认该订单  2 已完成该订单
                int pay_status;// 0 未支付  1 已支付

                showBtn(orderState, (Button) holder.getView(R.id.btnStart),
                        (Button) holder.getView(R.id.btnMid),
                        (Button) holder.getView(R.id.btnEnd));
                RecyclerView rvOrderItem = holder.getView(R.id.rvOrderItem);
                rvOrderItem.setLayoutManager(new WrapContentLinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false));
                //分割线
                rvOrderItem.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));
                CommonAdapter<OrderListBean.DataBean.GoodsBean> goodsAdapter = new CommonAdapter<OrderListBean.DataBean.GoodsBean>(baseContext,R.layout.list_item_cart_settle, dataBean.getGoods()) {
                    @Override
                    protected void convert(ViewHolder holder, OrderListBean.DataBean.GoodsBean goodsBean, int position) {
                        holder.setText(R.id.txtCartGoodsName, goodsBean.getGoods_name());
                        StringBuilder attrBuilder = new StringBuilder();
                        for (int i = 0; i < goodsBean.getGoods_attr_id().size(); i++) {
                            attrBuilder.append(goodsBean.getGoods_attr_id().get(i).getAttr()).append("\t\t");
                        }
                        holder.setText(R.id.txtCartGoodsAttr, attrBuilder.toString());
                        holder.setText(R.id.txtCartGoodsItemPrice, "￥："+ goodsBean.getGoods_price());
                        holder.setText(R.id.txtCartGoodsNumb, "x"+ goodsBean.getGoods_number());
                    }
                };
                goodsAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                        startActivity(new Intent(baseActivity, OrderDetailActivity.class)
                                .putExtra("order_id",dataBean.getOrder_id())
                                .putIntegerArrayListExtra("btn", new ArrayList<Integer>(orderState))
                        );
                    }

                    @Override
                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                        return false;
                    }
                });
                rvOrderItem.setAdapter(goodsAdapter);

            }
        };


//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mLoadMoreWrapper = new LoadMoreWrapper(adapter);
        //LayoutInflater.from(this).inflate(R.layout.default_loading, rvMerchant, false)
        mLoadMoreWrapper.setLoadMoreView(new View(baseContext));
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {

                if (page<counte_page) {
                    page++;
                    Map<String, Object> map = Constants.getMap();
                    map.put("page", page);
                    beginGet(API.Order.Order_List, map, LOAD_MORE, 0);
                }
                else {
                    mLoadMoreWrapper.setRefreshing(false);
//                    ToastUtils.showLongToast("到底啦");
                }
//                if (count_page > 1 && count_page > pages ) {
//                    pages++;
//                    beginGet(pages, LoadMore);
//                    firstLoad = false;
//                }else {
//                    if (firstLoad)return;
//                    ToastUtils.showLongToast("到底啦");
//                }
            }
        });
        rvMerchant.setAdapter(mLoadMoreWrapper);
        //swipeLayoutMerchant刷新会拉高recyclerview item的高度  //TODO 待解决
//        swipeLayoutMerchant.setOnChildScrollUpCallback(new SwipeRefreshLayout.OnChildScrollUpCallback() {
//            @Override
//            public boolean canChildScrollUp(SwipeRefreshLayout swipeRefreshLayout, @Nullable View view) {
//                ViewCompat.canScrollVertically(swipeLayoutMerchant, -1);
//                return false;
//            }
//        });
        adapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position)
            {
//                mAdapter.notifyItemRemoved(position);
                startActivity(new Intent(baseActivity, OrderDetailActivity.class)
                        .putExtra("order_id",orderList.get(position).getOrder_id())
                        .putIntegerArrayListExtra("btn", new ArrayList<Integer>( handlerBtnData(orderList.get(position))))
                );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });


        Map<String, Object> map = Constants.getMap();
        map.put("page", page);
//        Constants.LogMap(map);
        beginGet(API.Order.Order_List, map, LOAD_REFRESH, 0);
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
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

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
                    orderListBean = (OrderListBean) GsonHelper.getInstanceByJson(OrderListBean.class, jstr);
                    if (orderListBean.getCode() == Constants.CODE_OK) {
                        counte_page = orderListBean.getCount_page();
                        if (loadStatus == LOAD_REFRESH){
                            firstLoad = true;
                            swipeLayoutMerchant.setRefreshing(false);
                            mLoadMoreWrapper.setRefreshing(true);
                            page = 1;
                            orderList.clear();
                        }else {

                        }
                        orderList.addAll(orderListBean.getData());
                        mLoadMoreWrapper.notifyDataSetChanged();
//                        notifyData();
                    }
                } else if (status == LOAD_DATA_CANCEL) {
//                    storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, jstr);
//                    startActivity(new Intent(baseActivity, MerchantDetailActivity.class).putExtra("json", jstr).putExtra("storeId", storeId));
                    Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                    if (r.getCode() == Constants.CODE_OK){
                        ToastUtils.showLongToast(r.getData());
                    }else {
                        ToastUtils.showLongToast(r.getMsg());
                    }
                }else if (status == 3){
                    Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                    if (r.getCode() == Constants.CODE_OK){
                        startActivity(new Intent(baseActivity, WebViewActivity.class)
                                .putExtra("url", r.getData())
                        );
                    }
                }
            }
        });

    }
    public void notifyData() {
        try {
            if (orderList != null) {
                int previousSize = orderList.size();
                mLoadMoreWrapper.notifyItemRangeRemoved(0, previousSize);
                mLoadMoreWrapper.notifyItemRangeInserted(0, orderList.size());
            }
        }catch (Exception e){
            e.printStackTrace();
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

    @Override
    public void onRefresh() {
        page = 1;firstLoad = true;
        Map<String, Object> map = Constants.getMap();
        map.put("page", page);
        beginGet(API.Order.Order_List, map, LOAD_REFRESH, 0);
    }

    private List<Integer> handlerBtnData(OrderListBean.DataBean dataBean){
        List<Integer> orderState = new ArrayList<>();
        //获取对象属性key-values 根据值截取想要的字段
        Class cls = dataBean.getBtn().getClass();
        Field[] fields = cls.getDeclaredFields();
        try {
            for(int i=0; i<fields.length; i++){
                Field f = fields[i];
                f.setAccessible(true);
//                        System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(dataBean.getBtn()));
                Tracer.e(TAG, f.getName()+ " values:"+f.get(dataBean.getBtn()));
                Object o = f.get(dataBean.getBtn());//需强转为Object
                if (!Guard.isNull(o)) {
                    if (o instanceof Integer){ //是否为int 类型
                        if ((int)o == 1) {
                            orderState.add(Constants.getShippingStatus(f.getName()));
                        }
                    }

                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return orderState;
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

    private void OnClickListener(int OrderType, int OrderId, int StoreId, String amount, String order_sn){
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
