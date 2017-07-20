package com.bayue.live.deqingpu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.HomepagerRecycleAdapter;
import com.bayue.live.deqingpu.adapter.OneRecycleAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.MenuBean;
import com.bayue.live.deqingpu.entity.menu.MenuBannerBean;
import com.bayue.live.deqingpu.entity.menu.MenuBottomBean;
import com.bayue.live.deqingpu.entity.menu.MenuGoodsBean;
import com.bayue.live.deqingpu.entity.menu.MenuNoteBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bayue.live.deqingpu.weidget.MyStaggerGrildLayoutManger;
import com.tamic.novate.BaseSubscriber;
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
 * Created by BaYue on 2017/7/6 0006.
 * email : 2651742485@qq.com
 */

public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    String TAG = "MainFragment";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.swipeLayoutMain)
    SwipeRefreshLayout swipeLayoutMain;
    Unbinder unbinder;

    OneRecycleAdapter oneRecycleAdapter;
    MenuBean menuBean = new MenuBean();
    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    private HomepagerRecycleAdapter homepagerRecycleAdapter;
    public static MainFragment newInstance(String s) {
        MainFragment homeFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_main;
    }

    @Override
    public void init() {
        swipeLayoutMain.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeLayoutMain.setOnRefreshListener(this);

        Map<String, Object> map = Constants.getMap();
        beginGet(API.MENU, map, 0);
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = Constants.getMap();
        beginGet(API.MENU, map, 0);
    }

    private void beginGet(final String url, final Map<String, Object> map, final int status) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataFromNet(url, map, status);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int status) {
        HTTPUtils.getNovate(baseActivity).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

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
                if (status == 0) {
                    menuBean = (MenuBean) GsonHelper.getInstanceByJson(MenuBean.class, jstr);
                    if (menuBean.getCode() == Constants.CODE_OK) {
                        if (Guard.isNull(menuBean)) {
                            return;
                        }
                        initdata(jstr);
                        swipeLayoutMain.setRefreshing(false);
                    }
                }
            }
        });

    }

    private void initdata(String jstr) {
        //初始化recycleview 的适配器 以及布局管理器， 这里的管理器用的是流式布局，至于为何是流式布局，
        // 博客以及github上面有比较详细的讲解，欢迎查看，
        homepagerRecycleAdapter = new HomepagerRecycleAdapter(baseActivity);
        rvMain.setAdapter(homepagerRecycleAdapter);
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        rvMain.setLayoutManager(new MyStaggerGrildLayoutManger(baseActivity, 4, StaggeredGridLayoutManager.VERTICAL));
        //顶部list数据
        String dataStr = GsonHelper.getStrFromJson(jstr, "data");
        String goodsStr = GsonHelper.getStrFromJson(dataStr, "menu_1");
        List<MenuGoodsBean> goodsBeans = GsonHelper.jsonToArrayList(goodsStr, MenuGoodsBean.class);
        homepagerRecycleAdapter.setGoodList(goodsBeans);

        //note & 广告招募 数据
        String noteHeaderStr = GsonHelper.getStrFromJson(dataStr, "menu_2");
        MenuNoteBean.DataBean noteDataBean = (MenuNoteBean.DataBean) GsonHelper.getInstanceByJson(MenuNoteBean.DataBean.class, noteHeaderStr);
        List<MenuNoteBean.DataBean.AdBean> noteHeaderList =  noteDataBean.getAd();
        List<String> noteList =  noteDataBean.getNote();
        homepagerRecycleAdapter.setNoteList(noteList);
        homepagerRecycleAdapter.setNoteHeaderList(noteHeaderList);

        //banner 数据
        String bannerStr = GsonHelper.getStrFromJson(dataStr, "menu_3");
        List<MenuBannerBean.DataBean> bannerList = GsonHelper.jsonToArrayList(bannerStr, MenuBannerBean.DataBean.class);
        homepagerRecycleAdapter.setBannerList(bannerList);

        //底部数据
        List<MenuBottomBean> bottomList = new ArrayList<>();
        for (int i = 4; i < 8; i++) {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append("menu_").append(i);
            String key = GsonHelper.getStrFromJson(dataStr, keyBuilder.toString());
            MenuBottomBean bottomBean = (MenuBottomBean) GsonHelper.getInstanceByJson(MenuBottomBean.class, key);
            bottomList.add(bottomBean);
        }
        homepagerRecycleAdapter.setBottomBean(bottomList);
        Tracer.e(TAG, "note:" + noteList.size()+" bottom:"+ bottomList.size());
        //头部数据源
//        getHeaderData();

        //获得分类数据源
//        getcategoryData();

        //获取中间部分的数据源
//        getCenterBean();

        //获取底部数据
//        getRefreshData();

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
