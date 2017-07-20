package com.bayue.live.deqingpu.ui.merchant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.CommentAdapter;
import com.bayue.live.deqingpu.adapter.ViewAdapter;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.FragmentActivityBase;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.CommentActivity;
import com.bayue.live.deqingpu.ui.FeedBackActivity;
import com.bayue.live.deqingpu.ui.geren.CartActivity;
import com.bayue.live.deqingpu.ui.geren.CartConfirmActivity;
import com.bayue.live.deqingpu.ui.merchant.detail.FragGoodRecod;
import com.bayue.live.deqingpu.ui.merchant.detail.FragGoodRecom;
import com.bayue.live.deqingpu.ui.merchant.detail.FragGoodsShow;
import com.bayue.live.deqingpu.ui.merchant.detail.FragGoodsSpec;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.AmountView;
import com.bayue.live.deqingpu.view.AutoVerticalScrollTextView;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/17.
 * email : 2651742485@qq.com
 */

public class MerchantGoodsDetailActivity extends FragmentActivityBase {

    String TAG = "MerchantGoodsDetail";
    int goods_id;
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.banner)
    MZBannerView banner;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    GoodsDetail goodsDetail;
    @BindView(R.id.txtStoreName)
    TextView txtStoreName;
    @BindView(R.id.imgGoodsDetailShare)
    ImageView imgGoodsDetailShare;
    @BindView(R.id.imgGoodsDetailComplaint)
    ImageView imgGoodsDetailComplaint;
    @BindView(R.id.txtPriceTip)
    TextView txtPriceTip;
    @BindView(R.id.txtGoodsDetailPrice)
    TextView txtGoodsDetailPrice;
    @BindView(R.id.txtGoodsMarketPrice)
    TextView txtGoodsMarketPrice;
    @BindView(R.id.imgMerchantShop)
    ImageView imgMerchantShop;
    @BindView(R.id.txtGoodsDetailSalesVolume)
    TextView txtGoodsDetailSalesVolume;
    @BindView(R.id.txtGoodsDetailOffline)
    TextView txtGoodsDetailOffline;
    @BindView(R.id.linSelectSpecModel)
    LinearLayout linSelectSpecModel;
    @BindView(R.id.txtShowCommentSlide)
    TextView txtShowCommentSlide;
    @BindView(R.id.txtFavorableRate)
    TextView txtFavorableRate;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    @BindView(R.id.txtCommentAll)
    TextView txtCommentAll;
    @BindView(R.id.imgMerchantShopAvator)
    ImageView imgMerchantShopAvator;
    @BindView(R.id.txtMerchantAvatorShop)
    TextView txtMerchantAvatorShop;
    @BindView(R.id.txtMerchantAvatorBusi)
    TextView txtMerchantAvatorBusi;
    @BindView(R.id.txtMerchantGoodsCount)
    TextView txtMerchantGoodsCount;
    @BindView(R.id.linMerchantGoods)
    LinearLayout linMerchantGoods;
    @BindView(R.id.txtMerchantFood)
    TextView txtMerchantFood;
    @BindView(R.id.linGoodsDefault)
    LinearLayout linGoodsDefault;
    @BindView(R.id.txtMerchantHotel)
    TextView txtMerchantHotel;
    @BindView(R.id.imgGoodsSortTipValume)
    ImageView imgGoodsSortTipValume;
    @BindView(R.id.linGoodsValume)
    LinearLayout linGoodsValume;
    @BindView(R.id.txtMerchantPlay)
    TextView txtMerchantPlay;
    @BindView(R.id.imgGoodsSortTipPrice)
    ImageView imgGoodsSortTipPrice;
    @BindView(R.id.linGoodsPrice)
    LinearLayout linGoodsPrice;
    @BindView(R.id.txtMerchantTravel)
    TextView txtMerchantTravel;
    @BindView(R.id.imgGoodsSortTipAct)
    ImageView imgGoodsSortTipAct;
    @BindView(R.id.linGoodsAct)
    LinearLayout linGoodsAct;
    @BindView(R.id.linGoodsMenu)
    LinearLayout linGoodsMenu;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.vpMerchant)
    ViewPager vpMerchant;
    @BindView(R.id.txtNotice)
    AutoVerticalScrollTextView txtNotice;
    @BindView(R.id.txtSpecModel)
    TextView txtSpecModel;
    @BindView(R.id.linMerchantShop)
    LinearLayout linMerchantShop;
    @BindView(R.id.tvFreeShip)
    TextView tvFreeShip;
    @BindView(R.id.ivSell)
    ImageView ivSell;
    @BindView(R.id.tvCustomer)
    TextView tvCustomer;
    @BindView(R.id.tvAmountSave)
    TextView tvAmountSave;
    @BindView(R.id.tvAgentImmediate)
    TextView tvAgentImmediate;
    @BindView(R.id.linManagerState)
    LinearLayout linManagerState;
    @BindView(R.id.ivBottomShop)
    ImageView ivBottomShop;
    @BindView(R.id.ivBottomAddCart)
    ImageView ivBottomAddCart;
    @BindView(R.id.txtSpecSelectAdd)
    TextView tvSpecSelectAdd;
    @BindView(R.id.txtSpecSelectBuy)
    TextView tvSpecSelectBuy;
    @BindView(R.id.linAddCartBuy)
    LinearLayout linAddCartBuy;
    @BindView(R.id.tvGraphicDetail)
    TextView tvGraphicDetail;
    @BindView(R.id.rvSpecPro)
    RecyclerView rvSpecPro;
    List<String> imgList = new ArrayList<>();
    ArrayList<LazyLoadFragment> fragments = new ArrayList<>();
    CommentAdapter commentAdapter;
    private PopupWindow popupWindows;
    private View contentView;
    String goods_desc = "";
    private int screenWidth, screenHeight, store_id, actionType; //actionType 0 商家 ？ 1 生活馆
    private int currentIndex;
    private Handler mHandler = new Handler();
    List<String> noticeList = new ArrayList<>();
    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (vpMerchant != null) {
                vpMerchant.setCurrentItem(0);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_good_detail);
        ButterKnife.bind(this);
        topBar.setTitle(getString(R.string.txt_goods_detail));
        screenWidth = Utils.getScreenSize(baseActivity)[0];
        screenHeight = Utils.getScreenSize(baseActivity)[1];
        actionType = getIntent().getIntExtra("actionType", 0);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vpMerchant.getLayoutParams();
        params.height = screenHeight * 3 / 5;
        vpMerchant.setLayoutParams(params);
        txtSpecModel.setText(getString(R.string.tv_select) + getString(R.string.txt_goods_spec_model));
        goods_id = getIntent().getIntExtra("goods_id", -1);
        if (goods_id < 0) {
            return;
        }
        if (actionType == 1) {
            linAddCartBuy.setVisibility(View.VISIBLE);
            btnConmon.setVisibility(View.GONE);
        }
        //设置BannerView 的切换时间间隔
        banner.setDelayedTime(3000);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                ToastUtils.showShortToast("click page:" + position);
            }
        });
//        banner.setIndicatorVisible(false);//取消指示器
        //详情
        String detailUrl = API.Merchant.GOODS_DETAIL;
        Map<String, Object> map = Constants.getMap();
        map.put("goods_id", goods_id);//54
        beginGet(detailUrl, map, 1);
//        initViews();
        String commentUrl = API.Merchant.COMMENT_LIST;
        //评论
        Map<String, Object> commentmap = Constants.getMap();
        commentmap.put("comment_type", "1");
        commentmap.put("id_value", goods_id);
        commentmap.put("page", "1");
        beginGet(commentUrl, commentmap, 2);
    }

    String htmlStr = "";

    private void initData(GoodsDetail goodsDetail) {
        store_id = goodsDetail.getData().getStore_id();
        GoodsDetail.DataBean bean = goodsDetail.getData();
        txtStoreName.setText(bean.getStore_name());
        txtGoodsDetailPrice.setText(bean.getShop_price());
        txtGoodsMarketPrice.setText("市场价：" + bean.getMarket_price());
        txtGoodsMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线;
        txtGoodsDetailSalesVolume.setText("销量：" + bean.getSales() + "件");
        txtMerchantAvatorShop.setText(bean.getStore_name());
        txtMerchantAvatorBusi.setText(bean.getNik_name());
        txtMerchantGoodsCount.setText(bean.getStore_goods_num() + "\n" + "全部商品");
        if (!Guard.isNull(bean.getNotice())) {
            noticeList.addAll(bean.getNotice());
        }
        txtNotice.getResource(noticeList);
        txtNotice.setTvColor(ContextCompat.getColor(baseContext,R.color.black));
//        if (actionType == 1) {
//            tvFreeShip.setVisibility(View.VISIBLE);
//            ivSell.setVisibility(View.VISIBLE);
//            linManagerState.setVisibility(View.VISIBLE);
//            tvFreeShip.setText(String.format(getString(R.string.tv_free_shipping_amount), "88.88"));
//            txtGoodsDetailOffline.setText("全场" + String.format(getString(R.string.tv_free_shipping_amount), "88.88"));
//            //  tvAmountSave  = shop_price - merchant_price
//            double merchant_price, shop_price, fPrice = 0.0;
//            String mPrice = bean.getMerchant_price();
//            String sPrice = bean.getShop_price();
//            try {
//                merchant_price = Double.parseDouble(mPrice);
//                shop_price = Double.parseDouble(sPrice);
//                fPrice = shop_price - merchant_price;
//                tvAmountSave.setText(String.format(getString(R.string.tv_amount_save),fPrice+""));
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
        Glide.with(baseActivity).load(bean.getPic())
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).into(imgMerchantShopAvator);
//        new Thread() {
//            @Override
//            public void run() {
////                while (true){
////                    SystemClock.sleep(2000);
//                handler.sendEmptyMessage(199);
////                }
//            }
//        }.start();
        initViews(goodsDetail);
    }

//    private int number = 0;
//    private Handler handler = new MyHandler(this);

//    private static class MyHandler extends Handler {
//        private WeakReference<MerchantGoodsDetailActivity> mWeakReference;
//
//        public MyHandler(MerchantGoodsDetailActivity activity) {
//            mWeakReference = new WeakReference<>(activity);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            MerchantGoodsDetailActivity activity = mWeakReference.get();
//            if (activity != null) {
////                activity.autoTXTNotice.next();
//                activity.number++;
//                Tracer.e(activity.TAG, "value:" + activity.noticeList.get(activity.number % activity.noticeList.size()));
////                activity.autoTXTNotice.setText(activity.noticeList.get(activity.number % activity.noticeList.size()));
//            }
//        }
//    }

    private void initViews(GoodsDetail goodsDetail) {
        txtMerchantFood.setText(R.string.txt_goods_graphic_details);
        txtMerchantHotel.setText(R.string.txt_goods_spec_params);
        txtMerchantPlay.setText(R.string.txt_goods_recom_pro);
        txtMerchantTravel.setText(R.string.txt_goods_closing_record);
        imgGoodsSortTipValume.setVisibility(View.GONE);
        imgGoodsSortTipPrice.setVisibility(View.GONE);
        imgGoodsSortTipAct.setVisibility(View.GONE);
        FragGoodsShow goodsShow = FragGoodsShow.newInstance(htmlStr);
        String proValue = GsonHelper.ObjectToString(goodsDetail.getData().getPro());
        Tracer.e(TAG, "proValue:" + proValue);
        FragGoodsSpec goodsSpec = FragGoodsSpec.newInstance(proValue);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, goods_id);
        bundle.putInt("actionType", actionType);
        FragGoodRecom goodsRecom = FragGoodRecom.newInstance(bundle);
        FragGoodRecod goodsRecod = FragGoodRecod.newInstance(goods_id);
        fragments.add(goodsShow);
        fragments.add(goodsSpec);
        fragments.add(goodsRecom);
        fragments.add(goodsRecod);
        ViewAdapter adapter = new ViewAdapter(getSupportFragmentManager(), fragments);
        vpMerchant.setAdapter(adapter);
        mHandler.postDelayed(mLoopRunnable, 300);
        setItemSelect(txtMerchantFood);
        //加线
        initTabLineWidth();
        vpMerchant.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine.getLayoutParams();

                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 * 5
                 */
                int vLineRight = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
                int vLineLeft = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = vLineRight;

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = vLineLeft;

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = vLineRight;
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = vLineLeft;
                } else if (currentIndex == 2 && position == 2) //2->3
                {
                    lp.leftMargin = vLineRight;
                } else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = vLineLeft;
                }

                viewLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int i) {
                resetTextView();
                switch (i) {
                    case 0:
                        setItemSelect(txtMerchantFood);
                        break;
                    case 1:
                        setItemSelect(txtMerchantHotel);
                        break;
                    case 2:
                        setItemSelect(txtMerchantPlay);
                        break;
                    case 3:
                        setItemSelect(txtMerchantTravel);
                        break;
                }
                currentIndex = i;
            }

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vpMerchant.setOffscreenPageLimit(1);
    }

    boolean isFirstShowAttrs = true;
    @OnClick({R.id.linGoodsDefault, R.id.linGoodsValume, R.id.linGoodsPrice, R.id.linGoodsAct,
            R.id.tvCustomer, R.id.imgGoodsDetailShare, R.id.imgGoodsDetailComplaint,
            R.id.linSelectSpecModel, R.id.btnConmon, R.id.linMerchantShop, R.id.linMerchantGoods,
            R.id.tvGraphicDetail, R.id.tvAgentImmediate, R.id.txtGoodsDetailOffline,
            R.id.txtCommentAll, R.id.ivBottomShop, R.id.ivBottomAddCart,
            R.id.txtSpecSelectAdd, R.id.txtSpecSelectBuy
    })
    void setOnViewPagerClick(View view) {
        switch (view.getId()) {
            case R.id.tvCustomer:
                ToastUtils.showShortToast(getString(R.string.tv_customer));
                break;
            case R.id.imgGoodsDetailShare:
                ToastUtils.showShortToast("分享");
                break;
            case R.id.imgGoodsDetailComplaint:
                ToastUtils.showShortToast("投诉");
                startActivity(new Intent(baseContext, FeedBackActivity.class));
                break;
            case R.id.tvAgentImmediate:
                ToastUtils.showShortToast("没有是否注册掌柜的字段");
                break;
            case R.id.txtGoodsDetailOffline:
                ToastUtils.showShortToast("不包邮地区");
                break;
            case R.id.tvGraphicDetail:
                ToastUtils.showShortToast("图文详情");
                break;
            case R.id.txtCommentAll:
                startActivity(new Intent(baseContext, CommentActivity.class)
                        .putExtra("goods_id",goods_id)
                );
                ToastUtils.showShortToast("查看所有评论");
                break;
                case R.id.ivBottomShop:
                ToastUtils.showShortToast("ivBottomShop");
                break;
                case R.id.ivBottomAddCart:
                    startActivity(new Intent(baseActivity, CartActivity.class));
                break;
                case R.id.txtSpecSelectAdd:
//                    setAttrId();
                    if (attrIds!=null){
                        addToCart(attrIds);
                    }else {
                        show(view);
//                        ToastUtils.showShortToast("请选择规格");
                    }
                break;
            case R.id.txtSpecSelectBuy:
//                setAttrId();
                if (attrIds!=null){
                   CartComfirm();
                }else {
                    show(view);
                }
                break;
            case R.id.linSelectSpecModel:
                show(view);
                break;
            case R.id.btnConmon:
//                setAttrId();
                if (attrIds!=null){
                    CartComfirm();
                }else {
                    show(view);
                }
                break;
            case R.id.linMerchantShop:
                if (!GoodsListActivity.instance.isFinishing()) {
                    this.finish();
                    GoodsListActivity.instance.finish();
                }
                break;
            case R.id.linMerchantGoods:
                finish();
                break;
            case R.id.linGoodsDefault:
                vpMerchant.setCurrentItem(0);
                break;
            case R.id.linGoodsValume:
                vpMerchant.setCurrentItem(1);
                break;
            case R.id.linGoodsPrice:
                vpMerchant.setCurrentItem(2);
                break;
            case R.id.linGoodsAct:
                vpMerchant.setCurrentItem(3);
                break;
        }
    }

    private void show(View view){
        showPopwindow();
        popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     * Utils.getScreenSize(baseContext) 0 :width, 1 : height
     */
    private void initTabLineWidth() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine
                .getLayoutParams();
        lp.width = screenWidth / 4;
        viewLine.setLayoutParams(lp);
    }

    private void setItemSelect(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.black));
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        txtMerchantFood.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantHotel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantPlay.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantTravel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
    }

    private void beginGet(final String url, final Map<String, Object> map, final int type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataFromNet(url, map, type);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int type) {
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                    ToastUtils.showShortToast(getString(R.string.net_user_error)+ "\n"+ e.getMessage());
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
                Tracer.e(TAG + type, jstr);
                if (!jstr.contains("code")) {
                    ToastUtils.showShortToast(getString(R.string.net_user_error));
                    return;
                }
                switch (type) {
                    case 1:
                        goodsDetail = (GoodsDetail) GsonHelper.getInstanceByJson(GoodsDetail.class, jstr);
                        if (Guard.isNull(goodsDetail)) {
                            return;
                        }
                        if (goodsDetail.getData().getGoods_gally().size() > 0) {
                            for (int i = 0; i < goodsDetail.getData().getGoods_gally().size(); i++) {
                                String imgBaner = goodsDetail.getData().getGoods_gally().get(i).getImg_url();
                                imgList.add(imgBaner);
                            }
                            if (imgList.size() > 0) {
                                banner.setPages(imgList, new MZHolderCreator<BannerViewHolder>() {
                                    @Override
                                    public BannerViewHolder createViewHolder() {
                                        return new BannerViewHolder();
                                    }
                                });
                                banner.start();
                            }
                        }
//                    String dataJson = GsonHelper.getStrFromJson(jstr, "data");
//                    Tracer.e(TAG, "spe:" + GsonHelper.getStrFromJson(dataJson, "spe"));
//                    setSpeValue(GsonHelper.getStrFromJson(dataJson, "spe"));
                        setSpeValue(goodsDetail);
                        htmlStr = goodsDetail.getData().getGoods_desc();
                        initData(goodsDetail);
                        break;
                    case 2:
                        CommentBean commentBean = (CommentBean) GsonHelper.getInstanceByJson(CommentBean.class, jstr);
                        int count = 0;
                        if (commentBean.getCount() > 0) {
                            count = commentBean.getCount();
                        }
                        txtShowCommentSlide.setText("评论晒图（" + count + "）");
                        txtFavorableRate.setText("好评率：" + commentBean.getFavorable());
                        List<CommentBean.DataBean> dataBeans = new ArrayList<>();
                        if (!Guard.isNull(commentBean.getData())) {
                            if (commentBean.getData().size() > 0) {
                                for (int i = 0; i < commentBean.getData().size(); i++) {
                                    if (!Guard.isNull(commentBean.getData().get(i).getComment_img())) {
                                        dataBeans.add(commentBean.getData().get(i));
                                        break;
                                    }
                                }
                            }
                        }
                        Tracer.e(TAG, dataBeans.size() + " dataBeans");
                        commentAdapter = new CommentAdapter(baseContext, dataBeans);
                        listViewOneComment.setAdapter(commentAdapter);
                        if (commentBean.getData().size() < 1) {
                            txtCommentAll.setText("暂无评论");
                            txtCommentAll.setClickable(false);
                        }
                        break;
                    case 3:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        price = Double.parseDouble(r.getData());
                        txtSpecSelectPrice.setText("￥" + r.getData());
                        break;
                    case 4:
                        Return o = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (o.getCode() == Constants.CODE_OK) {
                            ToastUtils.showShortToast(o.getData());
                            hidePop();
                        } else {
                            ToastUtils.showShortToast(o.getMsg());
                        }
                        break;
                    case 5:
                        if (!jstr.contains("code")) {
                            ToastUtils.showLongToast(getString(R.string.net_user_error));
                            return;
                        }
                        startActivity(new Intent(baseContext, CartConfirmActivity.class)
                                .putExtra("actionType", actionType)
                                .putExtra("goods_id", goods_id)
                                .putExtra("spec", attrIds)
                                .putExtra("number", addNumber)
                                .putExtra("store_id", store_id)
                        );
                        break;
                }
            }
        });
    }

    TextView txtSpecSelectValue, txtSpecSelectPrice, txtSpecSelectAdd, txtSpecSelectBuy, txtSpecSelectConfirm;
    ImageView imgSpecSelectCancel, ivSpecHeadPic;
    RecyclerView rvSpecSelect;
    LinearLayout linSpecBtn;
    AmountView amountView;
    CommonAdapter<GoodsDetail.DataBean.SpeBean> popAdapter;
    StringBuilder stringBuilder = new StringBuilder("");
    Map<Integer, List<GoodsDetail.DataBean.SpeBean>> speHashMap = new HashMap<>();
    int tempSelectId, tempTagId, addNumber = 1;
    String attrIds = null, attrName = "";
    double price, tempSelectPrice;

    private void showPopwindow() {
        isFirstShowAttrs = false;
        final GoodsDetail detail = getSpeValue();
        if (Guard.isNull(detail)) {
            return;
        }
        //加载弹出框的布局
        contentView = LayoutInflater.from(baseActivity).inflate(
                R.layout.pop_spec_select, null);
        //绑定控件
        linSpecBtn = (LinearLayout) contentView.findViewById(R.id.linSpecBtn);
        txtSpecSelectValue = (TextView) contentView.findViewById(R.id.txtSpecSelectValue);
        txtSpecSelectPrice = (TextView) contentView.findViewById(R.id.txtSpecSelectPrice);
        amountView = (AmountView) contentView.findViewById(R.id.amount_view);
        txtSpecSelectAdd = (TextView) contentView.findViewById(R.id.txtSpecSelectAdd);
        txtSpecSelectBuy = (TextView) contentView.findViewById(R.id.txtSpecSelectBuy);
        txtSpecSelectConfirm = (TextView) contentView.findViewById(R.id.txtSpecSelectConfirm);
        imgSpecSelectCancel = (ImageView) contentView.findViewById(R.id.imgSpecSelectCancel);
        ivSpecHeadPic = (ImageView) contentView.findViewById(R.id.ivSpecHeadPic);
        rvSpecSelect = (RecyclerView) contentView.findViewById(R.id.rvSpecSelect);
        if (actionType == 0) {
            txtSpecSelectConfirm.setVisibility(View.VISIBLE);
            linSpecBtn.setVisibility(View.INVISIBLE);
        }
        rvSpecSelect.setLayoutManager(new LinearLayoutManager(baseActivity));
        txtSpecSelectValue.setText(detail.getData().getGoods_name());
        amountView.setDefault(1);
        if (!Guard.isNull(detail.getData().getMerchant_price())) {
            price = Double.parseDouble(detail.getData().getMerchant_price());
            txtSpecSelectPrice.setText("￥" + detail.getData().getMerchant_price());
        } else {
            txtSpecSelectPrice.setText("￥" + detail.getData().getShop_price());
        }
        Glide.with(baseContext).load(detail.getData().getGoods_thumb())
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                .into(ivSpecHeadPic);
        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {
                addNumber = amount;
                if (amount > 0) {
//                    txtSpecSelectPrice.setText("￥"+ amount * price);
                    getPrice();
                }
            }
        });

        popAdapter = new CommonAdapter<GoodsDetail.DataBean.SpeBean>(baseContext, R.layout.pop_spec_item_rv_spec, detail.getData().getSpe()) {
            @Override
            protected void convert(ViewHolder holder, final GoodsDetail.DataBean.SpeBean bean, final int position) {
                final String multiName = bean.getName();
                holder.setText(R.id.txtSpecProName, multiName);
                final TagFlowLayout mFlowLayout = holder.getView(R.id.flowlayout);
                int count;
                if (bean.getAttr_type() == 1) {
                    count = 1;
                } else {
                    count = -1;
                }
                mFlowLayout.setMaxSelectCount(count);
                final List<GoodsDetail.DataBean.SpeBean.ValuesBean> valuesList = bean.getValues();
                mFlowLayout.setAdapter(new TagAdapter<GoodsDetail.DataBean.SpeBean.ValuesBean>(valuesList) {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsDetail.DataBean.SpeBean.ValuesBean valuesBean) {
                        TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                                mFlowLayout, false);
                        tv.setText(valuesBean.getLabel());
                        return tv;
                    }
                });
                mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int tagPosition, FlowLayout parent) {
//                            txtSpecSelectPrice.setText("￥" + price);
                        List<GoodsDetail.DataBean.SpeBean> tempList = new ArrayList<>();
                        if (mFlowLayout.getSelectedList().size() > 0) {
                            GoodsDetail.DataBean.SpeBean speBean = new GoodsDetail.DataBean.SpeBean();
                            speBean.setAttr_type(bean.getAttr_type());
                            speBean.setName(multiName);
                            List<GoodsDetail.DataBean.SpeBean.ValuesBean> valuesBeanList = new ArrayList<>();
                            Iterator iterator = mFlowLayout.getSelectedList().iterator();
                            while (iterator.hasNext()) {
                                String spe = iterator.next().toString();
                                int pos = Integer.parseInt(spe);
                                valuesBeanList.add(bean.getValues().get(pos));
                            }
                            speBean.setValues(valuesBeanList);
                            tempList.add(speBean);
                        } else {

                        }
                        speHashMap.put(position, tempList);
                        setAttrId();
                        return true;
                    }
                });
            }
        };
        rvSpecSelect.setAdapter(popAdapter);
        //设置弹出框的宽度和高度
        popupWindows = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//                Utils.getScreenSize(baseContext)[1] / 3);
        popupWindows.setFocusable(true);// 取得焦点
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindows.setTouchable(true);
        //进入退出的动画
        popupWindows.setAnimationStyle(R.style.PopupWindowAnimation);
        // 按下android回退物理键 PopipWindow消失解决
        ivSpecHeadPic.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    hidePop();
                    return true;
                }
                return false;
            }
        });
        imgSpecSelectCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
                speHashMap.clear();
            }
        });
        txtSpecSelectAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttrId();
                addToCart(attrIds);
            }
        });
        txtSpecSelectBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttrId();
                hidePop();
               CartComfirm();
            }
        });
        txtSpecSelectConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAttrId();
                if (!Guard.isNullOrEmpty(attrName)) {
                    txtSpecModel.setText(attrName);
                }
                hidePop();
            }
        });
    }

    private void hidePop() {
        if (popupWindows != null && popupWindows.isShowing()) {
            popupWindows.dismiss();
        }
    }

    private void CartComfirm(){

        Map<String, Object> map = Constants.getMap(baseContext);
        map.put("goods_id", goods_id);
        map.put("number", addNumber);
        map.put("spec", attrIds);
        map.put("store_id", store_id);
//        Constants.LogMap(map);
        beginGet(API.Merchant.GOODS_CHECK, map, 5);
    }

    /**
     *  获取AttrId并全局赋值
     * */
    private String setAttrId(){
        String attrId = "";
        List<GoodsDetail.DataBean.SpeBean> speBeanList = new ArrayList<>();
        for (Map.Entry<Integer, List<GoodsDetail.DataBean.SpeBean>> entry : speHashMap.entrySet()) {
            speBeanList.addAll(entry.getValue());
        }
        StringBuilder tempIdBuilder = new StringBuilder();
        StringBuilder tempNameBuilder = new StringBuilder();
        StringBuilder tempLaberBuilder = new StringBuilder();
        String label;
        for (int i = 0; i < speBeanList.size(); i++) {
            for (int j = 0; j < speBeanList.get(i).getValues().size(); j++) {
                int id = speBeanList.get(i).getValues().get(j).getId();
                tempIdBuilder.append(id);
                tempIdBuilder.append(",");
                tempLaberBuilder.append(speBeanList.get(i).getValues().get(j).getLabel());
                tempLaberBuilder.append(",");
            }
            label = tempLaberBuilder.toString();
            if (label.endsWith(",")) {
                label = label.substring(0, label.length() - 1);
            }
            tempNameBuilder.append(speBeanList.get(i).getName());
            tempNameBuilder.append(":");
            tempNameBuilder.append(label + "\t\t");
        }
        attrId = tempIdBuilder.toString();
        if (attrId.endsWith(",")) {
            attrId = attrId.substring(0, attrId.length() - 1);
        }
        attrName = tempNameBuilder.toString();
        attrIds = attrId;
        return attrId;
    }

    private void addToCart(String attrId){
        Map<String, Object> mapPrice = Constants.getMap(baseContext);
        mapPrice.put("goods_id", goods_id);
        mapPrice.put("spec", attrId);
        mapPrice.put("store_id", store_id);
        mapPrice.put("number", addNumber);
//        Constants.LogMap(mapPrice);
        beginGet(API.Cart.ADD_TO_CART, mapPrice, 4);
    }

    private void getPrice() {
        String attrId = stringBuilder.toString();
        if (attrId.endsWith(",")) {
            attrId = attrId.substring(0, attrId.length() - 1);
        }
        Map<String, Object> mapPrice = Constants.getMap();
        mapPrice.put("goods_id", goods_id);
        mapPrice.put("goods_attr_id", attrId);
        mapPrice.put("number", addNumber);
//        Constants.LogMap(mapPrice);
        beginGet(API.Merchant.GOODS_PRICE, mapPrice, 3);
    }

    public static class BannerViewHolder implements MZViewHolder<String> {
        private ImageView mImageView;
        RequestManager glideRequest;

        @Override
        public View createView(Context context) {
            glideRequest = Glide.with(context);
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, String data) {
            // 数据绑定
//            mImageView.setImageResource(data);
            glideRequest.load(data)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round)
                    .transform(new GlideRoundTransform(context, 0))
                    .into(mImageView);
        }
    }

    private static GoodsDetail speValue;

    public void setSpeValue(GoodsDetail value) {
        speValue = value;
    }

    public GoodsDetail getSpeValue() {
        return speValue;
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
