package com.bayue.live.deqingpu.adapter.common.wrapper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.adapter.common.utils.WrapperUtils;

/**
 * Created by BaYue on 2017/6/17.
 * email : 2651742485@qq.com
 */

public class LoadMoreWrapper<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    public static final int ITEM_TYPE_LOAD_MORE = Integer.MAX_VALUE - 2;
    // 这个是定义的是否加载完成的boolean变量
    private boolean mIsLoadOver = true;
    // 给一个set方法，用于在外部控制是否隐藏‘加载更多’
    public void setLoadOver(boolean loadOver) {
        mIsLoadOver = loadOver;
    }
    int LAYOUT_DEFAULT = R.layout.default_loading;
    private RecyclerView.Adapter mInnerAdapter;
    private View mLoadMoreView;
    private int mLoadMoreLayoutId;

    public LoadMoreWrapper(RecyclerView.Adapter adapter)
    {
        mInnerAdapter = adapter;
    }

    private boolean hasLoadMore()
    {
        return mLoadMoreView != null || mLoadMoreLayoutId != 0;
    }


    private boolean isShowLoadMore(int position)
    {
        return hasLoadMore() && (position >= mInnerAdapter.getItemCount());
    }

    @Override
    public int getItemViewType(int position)
    {
        if (isShowLoadMore(position))
        {
            return ITEM_TYPE_LOAD_MORE;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        if (viewType == ITEM_TYPE_LOAD_MORE)
        {
            ViewHolder holder;
            if (mLoadMoreView != null)
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), mLoadMoreView);
            } else
            {
                holder = ViewHolder.createViewHolder(parent.getContext(), parent, mLoadMoreLayoutId);
            }
            return holder;
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        if (isShowLoadMore(position))
        {
            if (mOnLoadMoreListener != null)
            {
                mOnLoadMoreListener.onLoadMoreRequested();
            }
            return;
        }
        mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback()
        {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position)
            {
                if (isShowLoadMore(position))
                {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null)
                {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder)
    {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (isShowLoadMore(holder.getLayoutPosition()))
        {
            setFullSpan(holder);
        }
    }

    private void setFullSpan(RecyclerView.ViewHolder holder)
    {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams)
        {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

            p.setFullSpan(true);
        }
    }

    @Override
    public int getItemCount()
    {
        // 这里将mIsLoadOver 和 hasLoadMore()一起判断
        return mInnerAdapter.getItemCount() + ((hasLoadMore() && mIsLoadOver) ? 1 : 0);
//        return mInnerAdapter.getItemCount() + (hasLoadMore() ? 1 : 0);
    }


    public interface OnLoadMoreListener
    {
        void onLoadMoreRequested();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreWrapper setOnLoadMoreListener(OnLoadMoreListener loadMoreListener)
    {
        if (loadMoreListener != null)
        {
            mOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(View loadMoreView)
    {
        mLoadMoreView = loadMoreView;
        return this;
    }

    public LoadMoreWrapper setLoadMoreView(int layoutId)
    {
        mLoadMoreLayoutId = layoutId;
        return this;
    }
    public void setRefreshing(boolean refreshing){
        if(refreshing){
            setLoadMoreView(LAYOUT_DEFAULT);
        }else{
            setLoadMoreView(null);
            setLoadMoreView(0);
        }
    }
}