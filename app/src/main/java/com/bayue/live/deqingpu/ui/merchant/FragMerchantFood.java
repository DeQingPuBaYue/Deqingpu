package com.bayue.live.deqingpu.ui.merchant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.FullyLinearLayoutManager;
import com.bayue.live.deqingpu.adapter.PullToRefreshMerchantAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.MerchantFood;
import com.bayue.live.deqingpu.entity.ResultModel;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.StoreDetail;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.address.AddAddressActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.CustomLoadMoreView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/14.
 * email : 2651742485@qq.com
 */

public class FragMerchantFood extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener ,BaseQuickAdapter.RequestLoadMoreListener{
    String TAG = "FragMerchantFood";
    @BindView(R.id.rv_merchant)
    RecyclerView rvMerchant;
    @BindView(R.id.swipeLayout_merchant)
    SwipeRefreshLayout swipeLayoutMerchant;
    Unbinder unbinder;

    double longtitude = 121.50653;
    double latitude = 31.281119;
    PullToRefreshMerchantAdapter merchantAdapter;
    private static final int PAGE_SIZE = 15;
    private Novate novate;
    private List<MerchantFood.DataBean> foodList = new ArrayList<>();
    int LOAD_LIST = 0x0001;
    int LOAD_DETAIL = 0x0002;
    int LOAD_REFRESH = 0x0003;
    int LOAD_MORE = 0x0004;
    MerchantFood merchantFood;
    StoreDetail storeDetail;
    String store_type = "";
    int page = 1, count_page;
    @Override
    protected int getViewId() {
        return R.layout.frag_merchant_food;
    }

    @Override
    public void init() {
        swipeLayoutMerchant.setOnRefreshListener(this);
        swipeLayoutMerchant.setColorSchemeColors(Color.rgb(47, 223, 189));
        rvMerchant.setLayoutManager(new LinearLayoutManager(baseActivity));
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        initAdapter();
        beginGet(LOAD_REFRESH, page);

    }
    private void beginGet(final int loadStatus, final int pages){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = Constants.getMap();
                map.put("store_type", store_type);
                map.put("lat", latitude);
                map.put("long", longtitude);
                map.put("page", pages);
                getDataFromNet(API.Merchant.STORE_LIST, map, LOAD_LIST, loadStatus);
            }
        }, 300);

    }
    private void initAdapter() {
        //兼容scrollview
//        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(baseActivity);
//        rvMerchant.setNestedScrollingEnabled(false);
//        //设置布局管理器
//        rvMerchant.setLayoutManager(linearLayoutManager);
        merchantAdapter = new PullToRefreshMerchantAdapter(baseActivity, foodList);
        merchantAdapter.setOnLoadMoreListener(this, rvMerchant);
        merchantAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        merchantAdapter.setLoadMoreView(new CustomLoadMoreView());
//        merchantAdapter.setPreLoadNumber(3);
        rvMerchant.setAdapter(merchantAdapter);

        rvMerchant.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                ToastUtils.showShortToast(Integer.toString(position));
                Map<String, Object> map = Constants.getMap();
//                map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
                MerchantFood.DataBean bean = (MerchantFood.DataBean) baseQuickAdapter.getData().get(position);
                Tracer.e(TAG, bean.getStore_id() + "");
                map.put("store_id",bean.getStore_id());//
                getDataFromNet(API.Merchant.STORE_DETAIL, map, LOAD_DETAIL, 0);
            }
        });
    }
    private void getDataFromNet(String url, Map<String, Object> hashMap, final int status, final int loadStatus) {
        novate.post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void forceClose(ProgressDialog progress) {
                if (progress != null){
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                }
            }

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
                if (!jstr.contains("code")){
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                if (status == LOAD_LIST) {
                    merchantFood = (MerchantFood) GsonHelper.getInstanceByJson(MerchantFood.class, jstr);
                    if (merchantFood.getCode() == Constants.CODE_OK){
                        count_page = merchantFood.getCount_page();
                        if (loadStatus == LOAD_REFRESH) {
                            swipeLayoutMerchant.setRefreshing(false);
                            foodList.clear();
                            foodList.addAll(merchantFood.getData());
                            merchantAdapter.notifyDataSetChanged();
                        }else {
                            foodList.addAll(merchantFood.getData());
                            merchantAdapter.notifyDataSetChanged();
                            merchantAdapter.loadMoreComplete();
                        }
                    }
                }else if (status == LOAD_DETAIL){
//                    storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, jstr);
                    startActivity(new Intent(baseActivity, MerchantDetailActivity.class).putExtra("json", jstr));
                }
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        page = 1;
        beginGet(LOAD_REFRESH, page);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayoutMerchant.setEnabled(false);
        if (merchantAdapter.getData().size() < PAGE_SIZE) {

            merchantAdapter.loadMoreEnd(true);
        } else {
//            if (isErr) {
//                merchantAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//                mCurrentCounter = merchantAdapter.getData().size();
//                merchantAdapter.loadMoreComplete();
//            } else {
//                isErr = true;
//                ToastUtils.showLongToast(getString(R.string.network_err));
//                merchantAdapter.loadMoreFail();
//
//            }
            swipeLayoutMerchant.setEnabled(true);
            page++;
            if (page <= count_page) {
                beginGet(LOAD_MORE, page);
            }else {
                merchantAdapter.loadMoreEnd(true);
            }
        }
    }
}
