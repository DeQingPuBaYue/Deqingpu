package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.bayue.live.deqingpu.view.ViewListHolder;

import java.util.List;

/**
 * Created by 2015/9/8.
 * 通用集合适配器
 */
public abstract class CommonListAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> _listData;
    protected final int mItemLayoutId;

    public CommonListAdapter(Context context, List<T> listData, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this._listData = listData;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return _listData.size();
    }

    @Override
    public T getItem(int position) {
        return _listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewListHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position), position);
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewListHolder helper, T item, int position);

    private ViewListHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return ViewListHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

}