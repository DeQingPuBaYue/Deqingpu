package com.bayue.live.deqingpu.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.FullyLinearLayoutManager;
import com.bayue.live.deqingpu.adapter.PullToRefreshAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.BaseSubscriber;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.ResultModel;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.address.AddAddressActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.utils.Utils.getContext;

/**
 * Created by Administrator on 2017/6/5.
 */
//BaseQuickAdapter.RequestLoadMoreListener,
public class AddressActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    String TAG = "AddressActivity";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.rvList_ad)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_adress)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.linAddBtn)
    LinearLayout linAddBtn;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_EDIT = 0x0002;
    private static final int MSG_LOAD_DEL = 0x0003;
    private Map<String, String> headers = new HashMap<>();
    List<ResultModel.DataBean> beanList;
    private PullToRefreshAdapter pullToRefreshAdapter;

    private static final int TOTAL_COUNTER = 18;

    private static final int PAGE_SIZE = 5;

    private int delayMillis = 0;

    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;
    private Novate novate;

    @Override
    protected int getViewId() {
        return R.layout.ac_address;
    }

    @Override
    protected void initViews() {
        beanList = new ArrayList<>();
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        topBar.setTitle(getString(R.string.title_address));
        Tracer.e(TAG, Utils.getStatusBarHeight() + " token:" + Preferences.getString(getContext(), Preferences.TOKEN));

//        headers.put("Accept", "application/json");
//        headers.put("Content-Type", "application");
        novate = new Novate.Builder(this)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        initAdapter();
//        Map<String, Object> map = Constants.getMap();
//        map.put("region_type", "2");
//        map.put("region_id", "3");
//        getDataFromNet(API.GETADDRESS, map, 5);
    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int status) {
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
                linAddBtn.setVisibility(View.VISIBLE);
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
                ResultModel model = null;
                switch (status){
                    case MSG_LOAD_DATA:
                        model = (ResultModel) GsonHelper.getInstanceByJson(ResultModel.class, jstr);
                        update(model.getData());
                        break;
                    case MSG_LOAD_EDIT:
                    case MSG_LOAD_DEL:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == 200){
                            ToastUtils.showLongToast(r.getData());
                        }else {
                            ToastUtils.showLongToast(r.getMsg());
                        }
                        beginGet();
                        break;
                    case 5:

                        break;
                }

            }
        });

    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        beginGet();
    }

    @Override
    public void onRefresh() {
//        pullToRefreshAdapter.setEnableLoadMore(false);
        beginGet();
    }
    private void beginGet(){
        Map<String, Object> map = Constants.getMap();
//        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        getDataFromNet(API.GETADDRESS_LIST, map, MSG_LOAD_DATA);
    }
    private void update(final List<ResultModel.DataBean> list) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                beanList.clear();
                beanList.addAll(list);
                if (Guard.isNull(beanList)){
                    beanList = new ArrayList<>();
                }
                pullToRefreshAdapter.notifyDataSetChanged();
                Tracer.e(TAG, pullToRefreshAdapter.getData().size() + " beanList:" + beanList.size());
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                linAddBtn.setVisibility(View.VISIBLE);
//                pullToRefreshAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

//    @Override
//    public void onLoadMoreRequested() {
//        mSwipeRefreshLayout.setEnabled(false);
//        if (pullToRefreshAdapter.getData().size() < PAGE_SIZE) {
//            pullToRefreshAdapter.loadMoreEnd(true);
//        } else {
//            if (mCurrentCounter >= TOTAL_COUNTER) {
////                    pullToRefreshAdapter.loadMoreEnd();//default visible
//                pullToRefreshAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
//            } else {
//                if (isErr) {
//                    pullToRefreshAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//                    mCurrentCounter = pullToRefreshAdapter.getData().size();
//                    pullToRefreshAdapter.loadMoreComplete();
//                } else {
//                    isErr = true;
//                    ToastUtils.showShortToast(R.string.network_err);
//                    pullToRefreshAdapter.loadMoreFail();
//
//                }
//            }
//            mSwipeRefreshLayout.setEnabled(true);
//        }
//    }

    private void initAdapter() {
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(baseContext);
        mRecyclerView.setNestedScrollingEnabled(false);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);
        pullToRefreshAdapter = new PullToRefreshAdapter(beanList);
//        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(pullToRefreshAdapter);
        mCurrentCounter = pullToRefreshAdapter.getData().size();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
//                ToastUtils.showShortToast(Integer.toString(position));
            }
        });
        pullToRefreshAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Map<String, Object> map = Constants.getMap();
//                map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
                ResultModel.DataBean bean = (ResultModel.DataBean) baseQuickAdapter.getData().get(i);
                map.put("address_id",bean.getAddress_id());//
                switch (view.getId()) {
                    case R.id.layDefault:
                        pullToRefreshAdapter.setPosition(i); //传递当前的点击位置
                        getDataFromNet(API.DEFAULT, map, MSG_LOAD_EDIT);
                        break;
                    case R.id.txtEdit:
                        String value = GsonHelper.ObjectToString(bean);
                        Tracer.e(TAG, value);
                        startActivity(new Intent(baseActivity, AddAddressActivity.class)
                                .putExtra("action", "edit")
                                .putExtra("bean", value));
                        break;
                    case R.id.txtDel:
                        getDataFromNet(API.DELECT, map, MSG_LOAD_DEL);
                        break;
                }
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void setOnClick(View view) {
        startActivity(new Intent(baseActivity, AddAddressActivity.class).putExtra("action", "add"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
