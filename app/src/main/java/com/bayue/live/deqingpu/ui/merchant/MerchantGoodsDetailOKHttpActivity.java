package com.bayue.live.deqingpu.ui.merchant;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.CommentAdapter;
import com.bayue.live.deqingpu.adapter.ViewAdapter;
import com.bayue.live.deqingpu.base.FragmentActivityBase;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.merchant.detail.FragGoodsShow;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.AutoVerticalScrollTextView;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/17.
 * email : 2651742485@qq.com
 * 此类测试用，无效
 */

public class MerchantGoodsDetailOKHttpActivity extends FragmentActivityBase {

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
//    @BindView(R.id.autoTXTNotice)
//    AutoVerticalScrollTextView autoTXTNotice;
//    private Novate novate;
    List<String> imgList = new ArrayList<>();
    ArrayList<LazyLoadFragment> fragments = new ArrayList<>();
    CommentAdapter commentAdapter;
    String goods_desc = "";
    private int screenWidth, screenHeight;
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
        screenWidth = Utils.getScreenSize(baseActivity)[0];
        screenHeight = Utils.getScreenSize(baseActivity)[1];
        goods_id = getIntent().getIntExtra("goods_id", -1);
        if (goods_id < 0) {
            return;
        }
//        novate = new Novate.Builder(baseActivity)
//                //.addParameters(parameters)//公共参数
//                .connectTimeout(5)
//                .writeTimeout(10)
//                .baseUrl(API.baseUrl)
////                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
//                .addLog(true)
//                .build();
        //设置BannerView 的切换时间间隔
        banner.setDelayedTime(3000);
        banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                ToastUtils.showLongToast("click page:" + position);
            }
        });
//        banner.setIndicatorVisible(false);//取消指示器
        //详情
        String detailUrl = API.Merchant.GOODS_DETAIL;
        Map<String, Object> map = Constants.getMap();
        map.put("goods_id", goods_id);//54
        beginGet(detailUrl, map, 1);
        initViews();
        String commentUrl = API.Merchant.COMMENT_LIST;
        //评论
//        Map<String, Object> commentmap = Constants.getMap();
//        map.put("comment_type", "1");
//        map.put("id_value", goods_id);
//        map.put("page", "1");
//        beginGet(commentUrl, commentmap, 2);
    }

    private void initData(GoodsDetail goodsDetail) {
        GoodsDetail.DataBean bean = goodsDetail.getData();
        txtStoreName.setText(bean.getStore_name());
        txtGoodsDetailPrice.setText(bean.getShop_price());
        txtGoodsMarketPrice.setText("市场价：" + bean.getMarket_price());
        txtGoodsMarketPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线;
        txtGoodsDetailSalesVolume.setText("销量：" + bean.getSales() + "件");
        txtMerchantAvatorShop.setText(bean.getStore_name());
        txtMerchantAvatorBusi.setText(bean.getNik_name());
        txtMerchantGoodsCount.setText(bean.getStore_goods_num() + "\n" + "全部商品");
        noticeList.addAll(bean.getNotice());
//        autoTXTNotice.setText(noticeList.get(0));
        Glide.with(baseActivity).load(bean.getPic())
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).into(imgMerchantShopAvator);
        new Thread(){
            @Override
            public void run() {
                while (true){
                    SystemClock.sleep(2000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }
    private int number =0;
    private Handler handler = new MyHandler(this);
    private static class MyHandler extends Handler {
        private WeakReference<MerchantGoodsDetailOKHttpActivity> mWeakReference;
        public MyHandler(MerchantGoodsDetailOKHttpActivity activity) {
            mWeakReference = new WeakReference<MerchantGoodsDetailOKHttpActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            MerchantGoodsDetailOKHttpActivity activity = mWeakReference.get();
            if (activity != null)
            {
//                activity.autoTXTNotice.next();
                activity.number++;
//                Tracer.e(activity.TAG, "value:"+ activity.noticeList.get(activity.number % activity.noticeList.size()));
//                activity.autoTXTNotice.setText(activity.noticeList.get(activity.number % activity.noticeList.size()));
            }
        }
    }
    private void initViews() {
        txtMerchantFood.setText(R.string.txt_goods_graphic_details);
        txtMerchantHotel.setText(R.string.txt_goods_spec_params);
        txtMerchantPlay.setText(R.string.txt_goods_recom_pro);
        txtMerchantTravel.setText(R.string.txt_goods_closing_record);
        imgGoodsSortTipValume.setVisibility(View.GONE);
        imgGoodsSortTipPrice.setVisibility(View.GONE);
        imgGoodsSortTipAct.setVisibility(View.GONE);
        FragGoodsShow goodsShow = FragGoodsShow.newInstance(goods_desc);
        FragGoodsShow goodsSpec = FragGoodsShow.newInstance(goods_desc);
        FragGoodsShow goodsRecom = FragGoodsShow.newInstance(goods_desc);
        FragGoodsShow goodsRecod = FragGoodsShow.newInstance(goods_desc);
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
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                getDataFromNet(url, map, type);
//            }
//        }, 300);

        HTTPUtils.getNetDATA(API.baseUrl+url, map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                Tracer.e(TAG, msg);
                if (response.code() == 200){
                    final GoodsDetail goodsDetail = (GoodsDetail) GsonHelper.getInstanceByJson(GoodsDetail.class, msg);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (goodsDetail.getCode()==200){
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
                                initData(goodsDetail);
                            }else {
                            }

                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils.showLongToast(getString(R.string.network_err));
                        }
                    });
                }
            }
        });
    }

//    private void getDataFromNet(String url, Map<String, Object> hashMap, final int type) {
//        novate.post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {
//
//            @Override
//            public void onError(Throwable e) {
//                if (e.getMessage() != null) {
//                    Tracer.e("OkHttp", e.getMessage());
//                }
//            }
//
//            @Override
//            public void onNext(ResponseBody responseBody) {
//                String jstr = null;
//                try {
//                    jstr = new String(responseBody.bytes());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Tracer.e(TAG, jstr);
//                if (!jstr.contains("code")) {
//                    ToastUtils.showLongToast(getString(R.string.net_user_error));
//                    return;
//                }
//                if (type == 1) {
//                    goodsDetail = (GoodsDetail) GsonHelper.getInstanceByJson(GoodsDetail.class, jstr);
//                    if (Guard.isNull(goodsDetail)) {
//                        return;
//                    }
//                    if (goodsDetail.getData().getGoods_gally().size() > 0) {
//                        for (int i = 0; i < goodsDetail.getData().getGoods_gally().size(); i++) {
//                            String imgBaner = goodsDetail.getData().getGoods_gally().get(i).getImg_url();
//                            imgList.add(imgBaner);
//                        }
//                        if (imgList.size() > 0) {
//                            banner.setPages(imgList, new MZHolderCreator<BannerViewHolder>() {
//                                @Override
//                                public BannerViewHolder createViewHolder() {
//                                    return new BannerViewHolder();
//                                }
//                            });
//                            banner.start();
//                        }
//                    }
//                    initData(goodsDetail);
//                } else {
//                    CommentBean commentBean = (CommentBean) GsonHelper.getInstanceByJson(CommentBean.class, jstr);
//                    int count = 0, favorable = 100;
//                    if (commentBean.getCount() > 0) {
//                        count = commentBean.getCount();
//                    }
//                    favorable = commentBean.getFavorable();
//                    txtShowCommentSlide.setText("评论晒图（" + count + "）");
//                    txtFavorableRate.setText("好评率：" + favorable + "%");
//                    List<CommentBean.DataBean> dataBeans = new ArrayList<>();
//                    if (!Guard.isNull(commentBean.getData())) {
//                        if (commentBean.getData().size() > 0) {
//                            for (int i = 0; i < commentBean.getData().size(); i++) {
//                                if (!Guard.isNull(commentBean.getData().get(i).getComment_img())) {
//                                    dataBeans.add(commentBean.getData().get(i));
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                    Tracer.e(TAG, dataBeans.size() + " dataBeans");
//                    commentAdapter = new CommentAdapter(baseContext, dataBeans);
//                    listViewOneComment.setAdapter(commentAdapter);
//                }
//            }
//        });
//    }

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
        if (handler != null) handler.removeCallbacksAndMessages(null);
    }
}
