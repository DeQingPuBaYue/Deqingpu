package com.bayue.live.deqingpu.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.PullToRefreshAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by Administrator on 2017/6/5.
 */

public class AddressActivity extends BaseActivity implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private PullToRefreshAdapter pullToRefreshAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

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

    }

    @Override
    protected void setTheme() {

    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMoreRequested() {

    }
}
