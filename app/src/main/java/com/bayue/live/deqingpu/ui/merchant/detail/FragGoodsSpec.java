package com.bayue.live.deqingpu.ui.merchant.detail;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.FullyLinearLayoutManager;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.view.ScrollViewForListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class FragGoodsSpec extends LazyLoadFragment {

    Unbinder unbinder;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    CommonAdapter<GoodsDetail.DataBean.ProBean> proAdapter;
    List<GoodsDetail.DataBean.ProBean> proBeanList = new ArrayList<>();
    @BindView(R.id.rvSpecPro)
    RecyclerView rvSpecPro;

    public static FragGoodsSpec newInstance(String s) {
        FragGoodsSpec viewPagerFragment = new FragGoodsSpec();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.item_scroll_list;
    }

    @Override
    protected void lazyLoad() {
        listViewOneComment.setVisibility(View.GONE);
        rvSpecPro.setVisibility(View.VISIBLE);
        FullyLinearLayoutManager linearLayoutManager = new FullyLinearLayoutManager(baseActivity);
        rvSpecPro.setNestedScrollingEnabled(false);
        //设置布局管理器
        rvSpecPro.setLayoutManager(linearLayoutManager);
        String proValue = getArguments().getString(Constants.ARGS);
        proBeanList.addAll(GsonHelper.jsonToArrayList(proValue, GoodsDetail.DataBean.ProBean.class));
        //分割线
//        rvSpecPro.addItemDecoration(new DividerItemDecoration(baseActivity,DividerItemDecoration.VERTICAL));
        proAdapter = new CommonAdapter<GoodsDetail.DataBean.ProBean>(baseActivity, R.layout.list_item_goods_spec_pro, proBeanList) {
            @Override
            protected void convert(ViewHolder holder, GoodsDetail.DataBean.ProBean proBean, int position) {
                holder.setText(R.id.txtSpecProName, proBean.getAttr_name());
                holder.setText(R.id.txtSpecProValue, proBean.getAttr_value());
            }
        };
        rvSpecPro.setAdapter(proAdapter);
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
}
