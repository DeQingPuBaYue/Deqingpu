package com.bayue.live.deqingpu.ui.merchant.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tamic.novate.Novate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class FragGoodRecod extends LazyLoadFragment {

    Unbinder unbinder;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    CommonAdapter<GoodsDetail.DataBean.ProBean> proAdapter;
    List<GoodsDetail.DataBean.ProBean> proBeanList = new ArrayList<>();
    @BindView(R.id.rvSpecPro)
    RecyclerView rvSpecPro;
    int goods_id;
    private Novate novate;
//    private HeaderAndFooterAdapter headerAndFooterAdapter;
    public static FragGoodRecod newInstance(int s) {
        FragGoodRecod viewPagerFragment = new FragGoodRecod();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.item_scroll_list;
    }

    @Override
    protected void lazyLoad() {
        goods_id = getArguments().getInt(Constants.ARGS);
        listViewOneComment.setVisibility(View.GONE);
        rvSpecPro.setVisibility(View.VISIBLE);
        rvSpecPro.setLayoutManager(new LinearLayoutManager(baseActivity));
//        String proValue = getArguments().getString(Constants.ARGS);
//        proBeanList.addAll(GsonHelper.jsonToArrayList(proValue, GoodsDetail.DataBean.ProBean.class));
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
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
