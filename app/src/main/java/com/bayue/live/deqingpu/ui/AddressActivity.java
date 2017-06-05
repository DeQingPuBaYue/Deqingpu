package com.bayue.live.deqingpu.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.PullToRefreshAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.DataServer;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/5.
 */

public class AddressActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.rvList_ad)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeLayout_adress)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private PullToRefreshAdapter pullToRefreshAdapter;

    private static final int TOTAL_COUNTER = 18;

    private static final int PAGE_SIZE = 6;

    private int delayMillis = 1000;

    private int mCurrentCounter = 0;

    private boolean isErr;
    private boolean mLoadMoreEndGone = false;

    @Override
    protected int getViewId() {
        return R.layout.ac_address;
    }

    @Override
    protected void initViews() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(Color.rgb(47, 223, 189));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initAdapter();
    }

    @Override
    protected void setTheme() {

    }

    @Override
    public void onRefresh() {
        pullToRefreshAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullToRefreshAdapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                isErr = false;
                mCurrentCounter = PAGE_SIZE;
                mSwipeRefreshLayout.setRefreshing(false);
                pullToRefreshAdapter.setEnableLoadMore(true);
            }
        }, delayMillis);
    }

    @Override
    public void onLoadMoreRequested() {
        mSwipeRefreshLayout.setEnabled(false);
        if (pullToRefreshAdapter.getData().size() < PAGE_SIZE) {
            pullToRefreshAdapter.loadMoreEnd(true);
        } else {
            if (mCurrentCounter >= TOTAL_COUNTER) {
//                    pullToRefreshAdapter.loadMoreEnd();//default visible
                pullToRefreshAdapter.loadMoreEnd(mLoadMoreEndGone);//true is gone,false is visible
            } else {
                if (isErr) {
                    pullToRefreshAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
                    mCurrentCounter = pullToRefreshAdapter.getData().size();
                    pullToRefreshAdapter.loadMoreComplete();
                } else {
                    isErr = true;
                    ToastUtils.showShortToast(R.string.network_err);
                    pullToRefreshAdapter.loadMoreFail();

                }
            }
            mSwipeRefreshLayout.setEnabled(true);
        }
    }

    private void initAdapter() {
        pullToRefreshAdapter = new PullToRefreshAdapter();
        pullToRefreshAdapter.setOnLoadMoreListener(this, mRecyclerView);
        pullToRefreshAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        pullToRefreshAdapter.setPreLoadNumber(3);
        mRecyclerView.setAdapter(pullToRefreshAdapter);
        mCurrentCounter = pullToRefreshAdapter.getData().size();

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(final BaseQuickAdapter adapter, final View view, final int position) {
                ToastUtils.showShortToast(Integer.toString(position));
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
