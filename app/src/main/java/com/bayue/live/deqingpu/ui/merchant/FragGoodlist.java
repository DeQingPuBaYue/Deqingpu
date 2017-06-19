package com.bayue.live.deqingpu.ui.merchant;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.adapter.common.wrapper.HeaderAndFooterWrapper;
import com.bayue.live.deqingpu.adapter.common.wrapper.LoadMoreWrapper;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.GoodsBean;
import com.bayue.live.deqingpu.entity.MerchantFood;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.glide.GlideCircleTransform;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.CustomLoadMoreView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tamic.novate.BaseSubscriber;
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
 * Created by BaYue on 2017/6/16.
 * email : 2651742485@qq.com
 */

public class FragGoodlist extends LazyLoadFragment implements SwipeRefreshLayout.OnRefreshListener{

    String TAG = "FragGoodlist";
    int typeDefalut = 0, storeId = 1, pages = 1, count_page = 1;
    int LoadMore = 0x002, loadRefresh = 0x001;
    @BindView(R.id.rv_merchant)
    RecyclerView rvMerchant;
    @BindView(R.id.swipeLayout_merchant)
    SwipeRefreshLayout swipeLayoutMerchant;
    Unbinder unbinder;
    private CommonAdapter<GoodsBean.DataBean> mAdapter;
    private LoadMoreWrapper mLoadMoreWrapper;
    List<GoodsBean.DataBean> commenList = new ArrayList<>();
    GoodsBean goodsBean;
    private RequestManager glideRequest;
    private Novate novate;
    String sort = "ASC";
    String order = "goods_id";
    boolean firstLoad = true;

    public static FragGoodlist newInstance(int s) {
        FragGoodlist viewPagerFragment = new FragGoodlist();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_merchant_food;
    }

    @Override
    protected void lazyLoad() {
        init();
    }

//    @Override
    public void init() {
        glideRequest = Glide.with(baseActivity);
        typeDefalut = getArguments().getInt(Constants.ARGS);
        Tracer.e(TAG, typeDefalut+" typeDefaut");
//        rvMerchant.setLayoutManager(new LinearLayoutManager(baseActivity));
        swipeLayoutMerchant.setOnRefreshListener(this);
        swipeLayoutMerchant.setColorSchemeColors(Color.rgb(47, 223, 189));
        // StaggeredGridLayoutManager管理RecyclerView的布局。
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        rvMerchant.setLayoutManager(mLayoutManager);
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        //分割线
//        rvMerchant.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.VERTICAL));

        mAdapter = new CommonAdapter<GoodsBean.DataBean>(baseActivity, R.layout.list_item_goods, commenList) {
            @Override
            protected void convert(ViewHolder holder, GoodsBean.DataBean dataBean, int position) {
                glideRequest.load(dataBean.getGoods_thumb())
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .transform(new GlideRoundTransform(mContext, 0))
                        .into((ImageView) holder.getView(R.id.imgGoodsThumbnail));
                holder.setText(R.id.txtGoodsName, dataBean.getGoods_name());
                holder.setText(R.id.txtGoodsPrice, dataBean.getShop_price());
                holder.setText(R.id.txtGoodsMarketPrice, "市场价："+dataBean.getMarket_price());
                TextView textView = holder.getView(R.id.txtGoodsMarketPrice);
                textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
                holder.setText(R.id.txtGoodsSales, "销量："+dataBean.getSales());
            }
        };
//        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);
        mLoadMoreWrapper = new LoadMoreWrapper(new HeaderAndFooterWrapper(mAdapter));
//        mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
                if (count_page > 1 && count_page > pages ) {
                    pages++;
                    beginGet(pages, LoadMore);
                    firstLoad = false;
                }else {
                    if (firstLoad)return;
                    ToastUtils.showLongToast("到底啦");
                }
            }
        });
        rvMerchant.setAdapter(mLoadMoreWrapper);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,  int position)
            {
//                mAdapter.notifyItemRemoved(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position)
            {
                return false;
            }
        });

        beginGet(pages, loadRefresh);
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
        pages = 1;firstLoad = true;
        beginGet(1, loadRefresh);
    }
    private void beginGet(final int pages, final int LoadStatus){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = Constants.getMap();
                map.put("store_cat", "0");
                map.put("cat_id", "0");
                map.put("keyword", "0");
                map.put("store", storeId);
                map.put("sort", sort);
                map.put("order", order);
                map.put("page", pages);
                getDataFromNet(API.Merchant.GOODS_LIST, map, LoadStatus);
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
                if (!jstr.contains("code")){
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                goodsBean = (GoodsBean) GsonHelper.getInstanceByJson(GoodsBean.class, jstr);
                if (Guard.isNull(goodsBean)){
                    return;
                }
                count_page = goodsBean.getCount_page();
                if (LoadStatus == loadRefresh){
                    firstLoad = true;
                    swipeLayoutMerchant.setRefreshing(false);
                    pages = 1;
                    commenList.clear();
                }
                commenList.addAll(goodsBean.getData());
                mLoadMoreWrapper.notifyDataSetChanged();
            }
        });

    }

}
