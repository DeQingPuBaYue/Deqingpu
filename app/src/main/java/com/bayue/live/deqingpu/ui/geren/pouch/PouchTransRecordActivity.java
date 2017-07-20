package com.bayue.live.deqingpu.ui.geren.pouch;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.adapter.common.wrapper.HeaderAndFooterWrapper;
import com.bayue.live.deqingpu.adapter.common.wrapper.LoadMoreWrapper;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.TransRecord;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
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
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class PouchTransRecordActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_merchant)
    RecyclerView rvMerchant;
    @BindView(R.id.swipeLayout_merchant)
    SwipeRefreshLayout swipeLayoutMerchant;
    @BindView(R.id.topBar)
    TopActionBar topBar;

    String TAG = "PouchTransRecord";
    int pages = 1;
    int LoadMore = 0x002, loadRefresh = 0x001;
    private Novate novate;
    private CommonAdapter<TransRecord.DataBean> mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    List<TransRecord.DataBean> commenList = new ArrayList<>();

    private View mainView;
    PopupWindow popupWindow;
    TextView txtPopRechange,txtPopWithdraw,txtPopAll;
    void initPopWindows(){
        mainView = LayoutInflater.from(baseContext).inflate(R.layout.pop_share, null);
        txtPopRechange = (TextView) mainView.findViewById(R.id.txtPopRechange);
        txtPopWithdraw = (TextView) mainView.findViewById(R.id.txtPopWithdraw);
        txtPopAll = (TextView) mainView.findViewById(R.id.txtPopAll);
        popupWindow = new PopupWindow(mainView,ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //设置背景透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置默认获取焦点
        popupWindow.setFocusable(true);
        txtPopRechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                txtPopRechange.setTextColor(ContextCompat.getColor(baseContext, R.color.black));
                beginGet(pages, loadRefresh);
            }
        });
        txtPopWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                txtPopWithdraw.setTextColor(ContextCompat.getColor(baseContext, R.color.black));
                beginGet(pages, loadRefresh);
            }
        });
        txtPopAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetView();
                txtPopAll.setTextColor(ContextCompat.getColor(baseContext, R.color.black));
                beginGet(pages, loadRefresh);
            }
        });
        popupWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    popupWindow.dismiss();
                }
            }
        });
    }
    void resetView(){
        txtPopRechange.setTextColor(ContextCompat.getColor(baseContext, R.color.grey));
        txtPopWithdraw.setTextColor(ContextCompat.getColor(baseContext, R.color.grey));
        txtPopAll.setTextColor(ContextCompat.getColor(baseContext, R.color.grey));
    }
    @Override
    protected int getViewId() {
        return R.layout.ac_refresh;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.txt_trans_record));
        initPopWindows();
        topBar.setMenu(R.mipmap.icon_plus, new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {

                //以某个控件的x和y的偏移量位置开始显示窗口
                popupWindow.showAsDropDown(topBar, Utils.getScreenSize(baseContext)[0] - 90, 0);
                //如果窗口存在，则更新
                popupWindow.update();
            }
        });
        swipeLayoutMerchant.setOnRefreshListener(this);
        swipeLayoutMerchant.setColorSchemeColors(Color.rgb(47, 223, 189));
        // StaggeredGridLayoutManager管理RecyclerView的布局。
//        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
//                2, StaggeredGridLayoutManager.VERTICAL);
        rvMerchant.setLayoutManager(new LinearLayoutManager(baseActivity));
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        //分割线
        rvMerchant.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));
//        rvMerchant.addItemDecoration(new RecyclerViewDivider(baseContext, LinearLayoutManager.VERTICAL));
        mAdapter = new CommonAdapter<TransRecord.DataBean>(baseActivity, R.layout.list_item_record, commenList) {
            @Override
            protected void convert(ViewHolder holder, TransRecord.DataBean dataBean, int position) {

                holder.setText(R.id.txtPouchTransMethod, dataBean.getPayment());
                holder.setText(R.id.txtPouchTransTime, dataBean.getAdd_time());
                holder.setText(R.id.txtPouchTransMoney, dataBean.getAmount());
            }
        };
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mLoadMoreWrapper = new LoadMoreWrapper(new HeaderAndFooterWrapper(mAdapter));
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                if (count_page > 1 && count_page > pages ) {
                pages++;
                beginGet(pages, LoadMore);
//                    firstLoad = false;
//                }else {
//                    if (firstLoad)return;
//                    ToastUtils.showLongToast("到底啦");
//                }
            }
        });
        rvMerchant.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                mAdapter.notifyItemRemoved(position);
                startActivity(new Intent(baseActivity, PouchRecordDetailActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        beginGet(pages, loadRefresh);

    }

    @Override
    protected void setTheme() {

    }

    @Override
    public void onRefresh() {
        pages = 1;
        beginGet(1, loadRefresh);
    }

    private void beginGet(final int pages, final int LoadStatus) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = Constants.getMap(baseContext);
                map.put("page", pages);
                getDataFromNet(API.User.POUCH_DRAWLIST, map, LoadStatus);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int LoadStatus) {
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
                TransRecord record = (TransRecord) GsonHelper.getInstanceByJson(TransRecord.class, jstr);
                if (Guard.isNull(record)){
                    return;
                }
                if (LoadStatus == loadRefresh) {
//                    firstLoad = true;
                    swipeLayoutMerchant.setRefreshing(false);
                    pages = 1;
                    commenList.clear();
                }

                commenList.addAll(record.getData());
                mLoadMoreWrapper.notifyDataSetChanged();
                if (popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                }
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
