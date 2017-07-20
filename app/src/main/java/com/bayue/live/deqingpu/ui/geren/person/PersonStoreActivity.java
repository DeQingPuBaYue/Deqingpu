package com.bayue.live.deqingpu.ui.geren.person;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.HomepagerRecycleAdapter;
import com.bayue.live.deqingpu.adapter.StoreRecycleAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.MenuBean;
import com.bayue.live.deqingpu.entity.StoreMenu;
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
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/7/19 0019.
 * email : 2651742485@qq.com
 */

public class PersonStoreActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.rvMain)
    RecyclerView rvMain;
    @BindView(R.id.swipeLayoutMain)
    SwipeRefreshLayout swipeLayoutMain;

    String TAG = "PersonStore";
    StoreMenu menuBean;
    private StoreRecycleAdapter homepagerRecycleAdapter;
    @Override
    protected int getViewId() {
        return R.layout.ac_person_store;
    }

    @Override
    protected void initViews() {
        swipeLayoutMain.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeLayoutMain.setOnRefreshListener(this);
        Map<String, Object> map = Constants.getMap();
        beginGet(API.User.Merchant, map, 0);
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
                    swipeLayoutMain.setRefreshing(false);
                    menuBean = (StoreMenu) GsonHelper.getInstanceByJson(StoreMenu.class, jstr);
                    if (menuBean.getCode() == Constants.CODE_OK) {
                        if (Guard.isNull(menuBean)) {
                            return;
                        }
                        initdata(jstr);

                    }
                }
            }
        });

    }

    private void initdata(String jstr) {
        //初始化recycleview 的适配器 以及布局管理器， 这里的管理器用的是流式布局，至于为何是流式布局，
        // 博客以及github上面有比较详细的讲解，欢迎查看，
        homepagerRecycleAdapter = new StoreRecycleAdapter(baseActivity);
        rvMain.setAdapter(homepagerRecycleAdapter);
        //这里不用自定义的流式布局也是可以的，这里这是根据特定需要简单自定义了一个
        rvMain.setLayoutManager(new MyStaggerGrildLayoutManger(baseActivity, 1, StaggeredGridLayoutManager.VERTICAL));
        //顶部list数据
        String dataStr = GsonHelper.getStrFromJson(jstr, "data");
//        String goodsStr = GsonHelper.getStrFromJson(dataStr, "menu_1");
//        List<MenuGoodsBean> goodsBeans = GsonHelper.jsonToArrayList(goodsStr, MenuGoodsBean.class);
//        homepagerRecycleAdapter.setGoodList(goodsBeans);
        //note & 广告招募 数据
//        String noteHeaderStr = GsonHelper.getStrFromJson(dataStr, "merchant_info");
//        String noteStr = GsonHelper.getStrFromJson(noteHeaderStr, "merchant_info");
//        MenuNoteBean.DataBean noteDataBean = (MenuNoteBean.DataBean) GsonHelper.getInstanceByJson(MenuNoteBean.DataBean.class, noteHeaderStr);
//        List<MenuNoteBean.DataBean.AdBean> noteHeaderList =  noteDataBean.getAd();
//        List<String> noteList =GsonHelper.jsonToArrayList(noteHeaderStr,String.class);
        if (menuBean.getData().getCoupon()!=null){
            homepagerRecycleAdapter.setCouponList(menuBean.getData().getCoupon());
        }
        StoreMenu.DataBean.MerchantInfoBean merchantInfoBean =menuBean.getData().getMerchant_info() ;
        if (merchantInfoBean!=null) {
            if (merchantInfoBean.getNotice() != null) {
                homepagerRecycleAdapter.setNoteList(merchantInfoBean.getNotice());
            }
            homepagerRecycleAdapter.setStoreInfo(menuBean.getData().getMerchant_info());
        }
//        homepagerRecycleAdapter.setNoteHeaderList(noteHeaderList);

        //banner 数据
//        String bannerStr = GsonHelper.getStrFromJson(dataStr, "menu_3");
        if (menuBean.getData().getBanner()!=null) {
            List<StoreMenu.DataBean.BannerBean> bannerList = menuBean.getData().getBanner();
            homepagerRecycleAdapter.setBannerList(bannerList);
        }
//        //底部数据
        List<MenuBottomBean> bottomList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            StringBuilder keyBuilder = new StringBuilder();
            keyBuilder.append("menu_").append(i);
            String key = GsonHelper.getStrFromJson(dataStr, keyBuilder.toString());
            MenuBottomBean bottomBean = (MenuBottomBean) GsonHelper.getInstanceByJson(MenuBottomBean.class, key);
            bottomList.add(bottomBean);
        }
        homepagerRecycleAdapter.setBottomBean(bottomList);

    }

    @Override
    protected void setTheme() {

    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = Constants.getMap();
        beginGet(API.User.Merchant, map, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
