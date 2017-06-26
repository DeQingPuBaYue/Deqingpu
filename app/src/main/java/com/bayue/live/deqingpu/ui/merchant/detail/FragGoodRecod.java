package com.bayue.live.deqingpu.ui.merchant.detail;

import android.icu.text.CompactDecimalFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.RecordBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class FragGoodRecod extends LazyLoadFragment {

    Unbinder unbinder;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    List<RecordBean.DataBean> beanList = new ArrayList<>();
    @BindView(R.id.rvSpecPro)
    RecyclerView rvSpecPro;
    final String TAG = "FragGoodRecod";
    int goods_id;
    private Novate novate;
    private HeaderAndFooterAdapter headerAndFooterAdapter;
    String json = "{\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"nik_name\": \"老王\",\n" +
            "            \"add_time\": \"2017-06-22 19:00:00\",\n" +
            "            \"phone\": \"15142421313\",\n" +
            "            \"goods_number\": 15\n" +
            "            \n" +
            "        },\n" +
            "        {\n" +
            "            \"nik_name\": \"老李\",\n" +
            "            \"add_time\": \"2017-06-27 14:00:00\",\n" +
            "            \"phone\": \"15199999999\",\n" +
            "            \"goods_number\": 20\n" +
            "            \n" +
            "        },\n" +
            "        {\n" +
            "            \"nik_name\": \"老宁\",\n" +
            "            \"add_time\": \"2017-06-29 18:00:00\",\n" +
            "            \"phone\": \"15432321414\",\n" +
            "            \"goods_number\": 100\n" +
            "            \n" +
            "        }\n" +
            "    ],\n" +
            "    \"code\": 200,\n" +
            "    \"goods_num\": 0,\n" +
            "    \"count\": 0\n" +
            "}";
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
        initAdapter();
        View headerView = getHeaderView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                headerAndFooterAdapter.addHeaderView(getHeaderView(1, getRemoveHeaderListener()), 0);
            }
        });
        headerAndFooterAdapter.addHeaderView(headerView);
        View footerView = getFooterView(0, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                headerAndFooterAdapter.addFooterView(getFooterView(1, getRemoveFooterListener()), 0);
            }
        });
//        headerAndFooterAdapter.addFooterView(footerView, 0);

        rvSpecPro.setAdapter(headerAndFooterAdapter);
        beginGet();
    }
    private View getHeaderView(int type, View.OnClickListener listener) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_header_and_footer, (ViewGroup) rvSpecPro.getParent(), false);
        if (type == 1) {
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
//            imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
        view.setOnClickListener(listener);
        return view;
    }
    private View getFooterView(int type, View.OnClickListener listener) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.footer_view, (ViewGroup) rvSpecPro.getParent(), false);
        if (type == 1) {
//            ImageView imageView = (ImageView) view.findViewById(R.id.iv);
//            imageView.setImageResource(R.mipmap.ic_launcher);
        }
        view.setOnClickListener(listener);
        return view;
    }
    private View.OnClickListener getRemoveHeaderListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeHeaderView(v);
            }
        };
    }

    private void beginGet(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = Constants.getMap();
                map.put("goods_id", goods_id);
                getDataFromNet(API.Merchant.GOODS_ORDER, map);
            }
        }, 300);

    }
    private void getDataFromNet(String url, Map<String, Object> hashMap) {
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
                RecordBean recordBean = (RecordBean) GsonHelper.getInstanceByJson(RecordBean.class, json);
                if (Guard.isNull(recordBean)){
                    return;
                }
                beanList.clear();
                beanList.addAll(recordBean.getData());
                headerAndFooterAdapter.notifyDataSetChanged();
            }
        });

    }
    private View.OnClickListener getRemoveFooterListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                headerAndFooterAdapter.removeFooterView(v);
            }
        };
    }
    private void initAdapter() {
        headerAndFooterAdapter = new HeaderAndFooterAdapter(beanList);
        headerAndFooterAdapter.openLoadAnimation();
        rvSpecPro.setAdapter(headerAndFooterAdapter);
        headerAndFooterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                adapter.setNewData(DataServer.getSampleData(PAGE_SIZE));
                ToastUtils.showLongToast(position+"");
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
    class HeaderAndFooterAdapter extends BaseQuickAdapter<RecordBean.DataBean, BaseViewHolder> {

        public HeaderAndFooterAdapter(List<RecordBean.DataBean> recordList) {
            super(R.layout.item_header_and_footer,recordList);
        }

        @Override
        protected void convert(BaseViewHolder helper, RecordBean.DataBean item) {
            helper.setText(R.id.txtSpecCustomer, item.getNik_name());
            helper.setText(R.id.txtSpecTransaTime, item.getAdd_time());
            //直接获取goods_number会有为null情况，原因不详，待查
            String num = "";if (!Guard.isNull(item.getGoods_number()))num = item.getGoods_number()+"";
            helper.setText(R.id.txtSpecTransaGoodsNum, num);
            helper.setTextColor(R.id.txtSpecCustomer, ContextCompat.getColor(baseActivity, R.color.grey));
            helper.setTextColor(R.id.txtSpecTransaTime, ContextCompat.getColor(baseActivity, R.color.grey));
            helper.setTextColor(R.id.txtSpecTransaGoodsNum, ContextCompat.getColor(baseActivity, R.color.grey));
        }


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
