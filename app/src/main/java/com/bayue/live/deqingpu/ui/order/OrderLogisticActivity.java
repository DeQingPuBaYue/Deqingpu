package com.bayue.live.deqingpu.ui.order;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.OrderListBean;
import com.bayue.live.deqingpu.entity.OrderLogisticBean;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
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
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/7/3 0003.
 * email : 2651742485@qq.com
 */

public class OrderLogisticActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    String TAG = "OrderLogistic";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.ivOrderGoodsPic)
    ImageView ivOrderGoodsPic;
    @BindView(R.id.tvOrderShippingStatus)
    TextView tvOrderShippingStatus;
    @BindView(R.id.tvOrderShippingFrom)
    TextView tvOrderShippingFrom;
    @BindView(R.id.tvOrderID)
    TextView tvOrderID;
    @BindView(R.id.rv_merchant)
    RecyclerView rvMerchant;
    @BindView(R.id.swipeLayout_merchant)
    SwipeRefreshLayout swipeLayoutMerchant;

    int LOAD_DATA_CANCEL = 0x0001;
    int LOAD_DETAIL = 0x0002;
    int LOAD_REFRESH = 0x0003;
    int LOAD_MORE = 0x0004;
    private Novate novate;
    int page = 1;
    boolean firstLoad = true;
    CommonAdapter<OrderLogisticBean.DataBean> adapter;
    OrderLogisticBean logisticBean;
    List<OrderLogisticBean.DataBean> logistList = new ArrayList<>();
    String logisticId;
    @Override
    protected int getViewId() {
        return R.layout.ac_order_logistic;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.tv_logistics));
        logisticId = getIntent().getStringExtra("invoice_no");
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        swipeLayoutMerchant.setOnRefreshListener(this);
        swipeLayoutMerchant.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvMerchant.setLayoutManager(new LinearLayoutManager(baseActivity));
        rvMerchant.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));

        adapter = new CommonAdapter<OrderLogisticBean.DataBean>(baseContext, R.layout.list_item_order_logist, logistList) {
            @Override
            protected void convert(ViewHolder holder, OrderLogisticBean.DataBean dataBean, int position) {
                holder.setText(R.id.tvOrderExpress, dataBean.getContext());
                holder.setText(R.id.tvOrderExpressTime, dataBean.getTime());
            }
        };

        rvMerchant.setAdapter(adapter);
        Map<String, Object> map = Constants.getMap();
        map.put("page", page);
        map.put("page", page);//logisticId
//        Constants.LogMap(map);
        beginGet(API.Order.Order_List, map, LOAD_REFRESH, 0);

    }


    @Override
    public void onRefresh() {
        page = 1;
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
                    logisticBean = (OrderLogisticBean) GsonHelper.getInstanceByJson(OrderLogisticBean.class, OrderLogisticBean.LogisticData);
                    if (Integer.parseInt(logisticBean.getStatus()) == Constants.CODE_OK) {
                        if (loadStatus == LOAD_REFRESH){
                            firstLoad = true;
                            swipeLayoutMerchant.setRefreshing(false);
                            page = 1;
                            logistList.clear();
                        }
                        logistList.addAll(logisticBean.getData());
                        adapter.notifyDataSetChanged();
                        tvOrderShippingStatus.setText(String.format(getString(R.string.tv_order_deliver_status_edt),logisticBean.getState()));
                        tvOrderShippingFrom.setText(String.format(getString(R.string.tv_order_addtime_tip_edt),logisticBean.getCom()));
                        tvOrderID.setText(String.format(getString(R.string.tv_order_id_tip_edt),logisticBean.getNu()));
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
                }
            }
        });

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
