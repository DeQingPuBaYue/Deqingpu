package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.MultiItemTypeAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.entity.StoreMenu;
import com.bayue.live.deqingpu.entity.menu.MenuBannerBean;
import com.bayue.live.deqingpu.entity.menu.MenuBottomBean;
import com.bayue.live.deqingpu.entity.menu.MenuGoodsBean;
import com.bayue.live.deqingpu.entity.menu.MenuGoodsHeadBean;
import com.bayue.live.deqingpu.entity.menu.MenuNoteBean;
import com.bayue.live.deqingpu.ui.merchant.GoodsListActivity;
import com.bayue.live.deqingpu.ui.merchant.MerchantGoodsDetailActivity;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.AutoVerticalScrollTextView;
import com.bayue.live.deqingpu.weidget.MyStaggerGrildLayoutManger;
import com.bumptech.glide.Glide;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Arche on 2017/2/2.
 */
public class StoreRecycleAdapter extends RecyclerView.Adapter {

    private List<MenuGoodsBean> goodList;
    private List<StoreMenu.DataBean.CouponBean> couponList;
    private List<MenuNoteBean.DataBean.AdBean> headerList;
    private List<String> noteList;
    private List<StoreMenu.DataBean.BannerBean> bannerList;
//    private List<MenuGoodsHeadBean.DataBean> goodsHeaderList;
    MenuGoodsHeadBean gridHeader;
//    StoreMenu storeMenu;
    StoreMenu.DataBean.MerchantInfoBean merchantInfoBean;
    List<MenuBottomBean> bottomList;
    private final Context mContext;
//    private List<Headerbean.DataBean> headerData;
    private int count = 6;
//    private List<RefreshBean.DataBean> refreshbean;
//    private List<RefreshBean.DataBean> centerBean;
//    private ArrayList<HomeCategory> mHomeCategories;
    public static final int TYPE_STORE_COUPON = 0xff08; //头部GridView
    public static final int TYPE_STORE_MENU = 0xff09; //头部GridView
    public static final int TYPE_STORE_INFO = 0xff10; //头部GridView
//    public static final int TYPE_GRID_GOODS = 0xff10; //头部GridView
    public static final int TYPE_NOTE = 0xff011; //notice公告部分
    public static final int TYPE_GRID_IV = 0xff12; //广告招募入口
    public static final int TYPE_BANNER = 0xff13; //Banner栏
    public static final int TYPE_GRID_GOODS_TITLE = 0xff14; //有header的GridView
    private int TYPE_TOP = 1;//头部布局
    private int TYPE_CENTER = 2;//
    private int TYPE_CATEGORY = 3;//中间的四个快速入口
    private int TYPE_HEADER = 4;//每个分类的head
    private int REFRESHPOSITION = 5;//下部head的位置
    private int CENTERPOSITION;//中间head的位置
    private int TYPE_REFRESH = 6;//最下面的布局
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private MyStaggerGrildLayoutManger mystager;

    public StoreRecycleAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        //初始化各我数据源
        goodList = new ArrayList<>();
        headerList = new ArrayList<>();
        noteList = new ArrayList<>();
        bannerList = new ArrayList<>();
        bottomList = new ArrayList<>();
        couponList = new ArrayList<>();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_STORE_INFO:
//                return new HolderGrid(LayoutInflater.from(mContext).inflate(R.layout.item_common_grid, parent, false));
                View view = inflater.inflate(R.layout.list_item_store_info, parent, false);
                StaggeredGridLayoutManager.LayoutParams params2 =
                        (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
                params2.setFullSpan(true);
                view.setLayoutParams(params2);
                return new HolderStoreInfo(view);
            case TYPE_STORE_MENU:
                View viewCoupon = inflater.inflate(R.layout.list_item_store_menu, parent, false);
                StaggeredGridLayoutManager.LayoutParams paramsCoupon =
                        (StaggeredGridLayoutManager.LayoutParams) viewCoupon.getLayoutParams();
                paramsCoupon.setFullSpan(true);
                viewCoupon.setLayoutParams(paramsCoupon);
                return new HolderStoreCoupon(viewCoupon);
            case TYPE_STORE_COUPON:
                View viewCoupon1 = inflater.inflate(R.layout.item_common_recycler, parent, false);
                StaggeredGridLayoutManager.LayoutParams paramsCoupon1 =
                        (StaggeredGridLayoutManager.LayoutParams) viewCoupon1.getLayoutParams();
                paramsCoupon1.setFullSpan(true);
                paramsCoupon1.bottomMargin = 20;
                viewCoupon1.setLayoutParams(paramsCoupon1);
                return new HolderCoupon(viewCoupon1);
            case TYPE_NOTE://公告
                View viewNote = inflater.inflate(R.layout.item_common_note, parent, false);
                StaggeredGridLayoutManager.LayoutParams params =
                        (StaggeredGridLayoutManager.LayoutParams) viewNote.getLayoutParams();
                params.setFullSpan(true);
                viewNote.setLayoutParams(params);
                return new HolderNote(viewNote);
//            case TYPE_GRID_IV://广告招募
//                View viewIV = inflater.inflate(R.layout.item_common_recycler, parent, false);
//                StaggeredGridLayoutManager.LayoutParams paramsIV =
//                        (StaggeredGridLayoutManager.LayoutParams) viewIV.getLayoutParams();
//                paramsIV.setFullSpan(true);
//                viewIV.setLayoutParams(paramsIV);
//                return new HolderGridIV(viewIV);
            case TYPE_BANNER:
                View viewBanner = inflater.inflate(R.layout.item_common_banner, parent, false);
                StaggeredGridLayoutManager.LayoutParams paramsBanner =
                        (StaggeredGridLayoutManager.LayoutParams) viewBanner.getLayoutParams();
                paramsBanner.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
                viewBanner.setLayoutParams(paramsBanner);
                return new HolderBanner(viewBanner);
            case TYPE_GRID_GOODS_TITLE:
                View viewTitle = inflater.inflate(R.layout.item_common_recycler, parent, false);
                StaggeredGridLayoutManager.LayoutParams paramsTitle =
                        (StaggeredGridLayoutManager.LayoutParams) viewTitle.getLayoutParams();
                paramsTitle.setFullSpan(true);//最为重要的一个方法，占满全屏,以下同理
                viewTitle.setLayoutParams(paramsTitle);
                return new HolderGridTitle(viewTitle);
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof TypeTopsliderHolder && headerData.size() != 0 && ((TypeTopsliderHolder) holder).linearLayout.getChildCount() == 0) {
//            //如果是TypeTopsliderHolder， 并且header有数据，并且TypeTopsliderHolder的linearLayout没有子view（因为这个布局只出现一次，如果他没有子view，
//            // 也就是他是第一次加载，才加载数据）
//            initslider(((TypeTopsliderHolder) holder), headerData);//加载头部数据源
//        }
        if (holder instanceof HolderStoreInfo) {
            initStoreInfo(((HolderStoreInfo) holder));//加载顶部的数据源
        }
        else if (holder instanceof HolderStoreCoupon){
            initStoreCoupon(((HolderStoreCoupon) holder));
        }
        else if (holder instanceof HolderCoupon){
            initCoupon(((HolderCoupon) holder));
        }
        else if (holder instanceof HolderNote) {
            initNote(((HolderNote) holder));//加载heade数据源（其实这里可以每个head单独设置，因为有的需求head去各式各样）
        }
        else if (holder instanceof HolderGridIV  && headerList.size()>0){
            initGridIV(((HolderGridIV) holder));
        }
        else if (holder instanceof HolderBanner && bannerList.size()>0){
            initBanner(((HolderBanner) holder));
        }
        else if (holder instanceof HolderGridTitle && bottomList.size()>0 ){
            initGridTitle(((HolderGridTitle) holder));
        }
//        else if (holder instanceof TypeRefresh && refreshbean.size() != 0) {
//            initrefreshdata(((TypeRefresh) holder), position - REFRESHPOSITION - 1);//加载瀑布流数据源
//        }

    }

//    private void initrefreshdata(TypeRefresh holder, int position) {
//        Log.e("position", "initrefreshdata: " + position);
//        if (mHeights.size() <= getItemCount() + 2) {
//            //这里只是随机数模拟瀑布流， 实际过程中， 应该根据图片高度来实现瀑布流
//            mHeights.add((int) (500 + Math.random() * 400));
//        }
//
//        ViewGroup.LayoutParams layoutParams = holder.homeReadPivIv.getLayoutParams();
//        if (mHeights.size() > position)
//            //此处取得随机数，如果mheight里面有数则取， 没有则邹走else
//            layoutParams.height = mHeights.get(position);
//        else layoutParams.height = 589;
//        holder.homeReadPivIv.setLayoutParams(layoutParams);
//
//        holder.homeReadPivIv.setScaleType(ImageView.ScaleType.FIT_XY);
//        if (refreshbean.size() > position) {
//            ImageUtils.load(mContext, refreshbean.get(position).getCpOne().getImgUrl(), holder.homeReadPivIv);
//        } else {
//            ImageUtils.load(mContext, refreshbean.get(0).getCpTwo().getImgUrl(), holder.homeReadPivIv);
//        }
//
//    }
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.linPersonStoreAllGoods:
                    ToastUtils.showShortToast("1");
                    break;
                case R.id.linPersonStoreHotGoods:
                    ToastUtils.showShortToast("2");
                    break;
                case R.id.linPersonStoreCustomer:
                    ToastUtils.showShortToast("3");
                    break;
            }
        }
    };
    private void initStoreInfo(HolderStoreInfo holder) {
        if (merchantInfoBean!=null) {
            Glide.with(mContext).load(merchantInfoBean.getMerchant_banner())
                    .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                    .into(holder.ivPersonStoreBG);
            Glide.with(mContext).load(merchantInfoBean.getMerchant_pic())
                    .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                    .into(holder.ivPersonStoreHead);
            holder.tvPersonStoreName.setText(merchantInfoBean.getMerchant_name());
        }
    }
    private void initStoreCoupon(HolderStoreCoupon holder) {
        holder.linPersonStoreAllGoods.setOnClickListener(listener);
        holder.linPersonStoreHotGoods.setOnClickListener(listener);
        holder.linPersonStoreCustomer.setOnClickListener(listener);
    }
    private void initCoupon(HolderCoupon holder) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        holder.rvMenuTop.setLayoutManager(linearLayoutManager);
        holder.rvMenuTop.setLayoutManager(new GridLayoutManager(mContext, couponList.size()));
//        holder.rvMenuTop.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.HORIZONTAL));
        CommonAdapter<StoreMenu.DataBean.CouponBean> goodAdapter = new CommonAdapter<StoreMenu.DataBean.CouponBean>(
                mContext, R.layout.item_common_coupon, couponList) {
            @Override
            protected void convert(ViewHolder holder, StoreMenu.DataBean.CouponBean dataBean, int position) {
                String money = dataBean.getMoney();
                double dMoney = 0;
                if (!Guard.isNullOrEmpty(money)){
                    try {
                        dMoney = Double.parseDouble(money);
                    }catch (NumberFormatException e){
                        Tracer.e("parseDouble", e.toString());
                    }
                }
                holder.setText(R.id.tvCouponMoney, (int)dMoney+"");
                String amount = dataBean.getMin_amount();
                double dAmount = 0;
                if (!Guard.isNullOrEmpty(amount)){
                    try {
                        dAmount = Double.parseDouble(amount);
                    }catch (NumberFormatException e){
                        Tracer.e("parseDouble", e.toString());
                    }
                }
                int minA = (int) dAmount;
                String minStr = "";
                if (minA ==0){
                    minStr = mContext.getString(R.string.tv_person_coupon);
                }else {
                    minStr = String.format(mContext.getString(R.string.tv_person_coupon_money),minA);
                }
                holder.setText(R.id.tvCouponNum, minStr);
            }
        };
        holder.rvMenuTop.setAdapter(goodAdapter);
    }
    private void initNote(HolderNote holder) {
        holder.txtNotice.setTvBgColor(ContextCompat.getColor(mContext,R.color.title_bar_divide_line));
        holder.txtNotice.getResource(noteList);
    }

    private void initGridIV(HolderGridIV holder) {
        holder.rvMenuTop.setLayoutManager(new GridLayoutManager(mContext, 2));
        CommonAdapter<MenuNoteBean.DataBean.AdBean> goodAdapter = new CommonAdapter<MenuNoteBean.DataBean.AdBean>(mContext, R.layout.item_common_iv_grid, headerList) {
            @Override
            protected void convert(ViewHolder holder, MenuNoteBean.DataBean.AdBean dataBean, int position) {
                Glide.with(mContext).load(dataBean.getImg())
                        .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                        .into((ImageView) holder.getView(R.id.ivGridIV));
            }
        };
        holder.rvMenuTop.setAdapter(goodAdapter);
    }


    private void initBanner(HolderBanner holder) {
        holder.banner.setDelayedTime(3000);
        holder.banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                ToastUtils.showLongToast("click page:" + position);
            }
        });
        List<String> imgList = new ArrayList<>();
        if (bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                String imgBaner = bannerList.get(i).getPic();
                imgList.add(imgBaner);
            }
            if (imgList.size() > 0) {
                holder.banner.setPages(imgList, new MZHolderCreator<MerchantGoodsDetailActivity.BannerViewHolder>() {
                    @Override
                    public MerchantGoodsDetailActivity.BannerViewHolder createViewHolder() {
                        return new MerchantGoodsDetailActivity.BannerViewHolder();
                    }
                });
                holder.banner.start();
            }
        }
    }
    private void initGridTitle(HolderGridTitle holder) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);// No adapter attached; skipping layout
        holder.rvMenuTop.setLayoutManager(layoutManager);
        CommonAdapter<MenuBottomBean> goodAdapter = new CommonAdapter<MenuBottomBean>(mContext, R.layout.item_common_recycler_header, bottomList) {
            @Override
            protected void convert(ViewHolder holder, MenuBottomBean dataBean, int position) {
                Glide.with(mContext).load(dataBean.getImg())
                        .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                        .into((ImageView) holder.getView(R.id.ivGridIV));
                RecyclerView rvGridTitle = holder.getView(R.id.rvMenuCenter);
                GridLayoutManager itemManage = new GridLayoutManager(mContext, 4);
                itemManage.setOrientation(LinearLayoutManager.VERTICAL);
                rvGridTitle.setLayoutManager(itemManage);
                CommonAdapter<MenuBottomBean.ContentBean> goodAdapter = new CommonAdapter<MenuBottomBean.ContentBean>(mContext, R.layout.item_common_grid, dataBean.getContent()) {
                    @Override
                    protected void convert(ViewHolder holder, MenuBottomBean.ContentBean dataBean, int position) {
                        holder.setText(R.id.tvMainGridGoods, dataBean.getCat_name());
                        Glide.with(mContext).load(dataBean.getImg())
                                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                                .into((ImageView) holder.getView(R.id.ivGoods));
                    }
                };
                rvGridTitle.setAdapter(goodAdapter);
            }
        };
        holder.rvMenuTop.setAdapter(goodAdapter);

    }
    @Override
    public int getItemViewType(int position) {
        //此处是根据数据源有无数据来判定分类条的位置；可自行拓展，自由发挥
//        CENTERPOSITION = mHomeCategories.size() == 0 ? 1 : 2;
//        REFRESHPOSITION = centerBean.size() == 0 ? 3 : 4;

//        Log.e("getItemViewType", "getItemViewType: " + CENTERPOSITION + ",:" + REFRESHPOSITION);

        if (position == 0) return TYPE_STORE_INFO;
        else if (position == 3){
            return TYPE_NOTE;
        }
        else if (position == 1){
            return TYPE_STORE_MENU;
        }
        else if (position == 2){
            return TYPE_STORE_COUPON;
        }
        else if (2==position){
            return TYPE_GRID_IV;
        }
        else if (position ==4){
            return TYPE_BANNER;
        }
        else if (position == 5){
            return TYPE_GRID_GOODS_TITLE;
        }

        else return TYPE_STORE_INFO;
    }

    @Override
    public int getItemCount() {
        return count;
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof MyStaggerGrildLayoutManger) {
            mystager = ((MyStaggerGrildLayoutManger) layoutManager);

        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                //如果快速滑动， 不加载图片
                if (newState == 2) {
                    Glide.with(mContext).pauseRequests();
                } else {
                    Glide.with(mContext).resumeRequests();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            }
        });
    }
//   List<MenuGoodsBean.DataBean> goodList;
//   List<MenuNoteBean.DataBean.AdBean> headerList;
//   List<String> noteList;
//   List<MenuBannerBean.DataBean> bannerList;
//   List<MenuGoodsHeadBean.DataBean> goodsHeaderList;
//    MenuGoodsHeadBean gridHeader;
    public void setStoreInfo(StoreMenu.DataBean.MerchantInfoBean menu){
        merchantInfoBean = menu;
    }

    public void setCouponList(List<StoreMenu.DataBean.CouponBean> list){
        couponList.clear();
        couponList.addAll(list);
        notifyDataSetChanged();
    }

    public void setGoodList(List<MenuGoodsBean> list){
        goodList.clear();
        goodList.addAll(list);
        notifyDataSetChanged();
    }
    public void setNoteList(List<String> list){
        noteList.clear();
        noteList.addAll(list);
        notifyDataSetChanged();
    }

    public void setNoteHeaderList(List<MenuNoteBean.DataBean.AdBean> list){
        headerList.clear();
        headerList.addAll(list);
        notifyDataSetChanged();
    }

    public void setBannerList(List<StoreMenu.DataBean.BannerBean> list){
        bannerList.clear();
        bannerList.addAll(list);
        notifyDataSetChanged();
    }

    public void setGridHeader(MenuGoodsHeadBean bean){
        gridHeader = bean;
        notifyDataSetChanged();
    }

    public void setBottomBean(List<MenuBottomBean> list){
        bottomList.clear();
        bottomList.addAll(list);
        notifyDataSetChanged();
    }

//    public void setheaderbean(Headerbean headerbean) {
//        headerData = headerbean.getData();
//        notifyDataSetChanged();
//    }
//
//    public void setRefreshBean(RefreshBean refreshBean, boolean flagFirst) {
//        refreshbean.addAll(refreshBean.getData());
//        int count1 = this.count;
//        this.count += refreshBean.getData().size();
//        notifyDataSetChanged();
//        if (!flagFirst) {
//            recyclerView.smoothScrollToPosition(count1 + 2);//加载完以后向上滚动3个条目
//        }
//
//
//    }
//
//    public void setCenterBean(RefreshBean refreshBean) {
//        centerBean = refreshBean.getData();
//        count++;
//        notifyDataSetChanged();
//    }
//
//    public void setCategoryBean(ArrayList<HomeCategory> homeCategories) {
//        mHomeCategories = homeCategories;
//        count++;
//        notifyDataSetChanged();
//
//    }

    public class HolderStoreCoupon extends  RecyclerView.ViewHolder {
        @BindView(R.id.linPersonStoreAllGoods)
        LinearLayout linPersonStoreAllGoods;
        @BindView(R.id.linPersonStoreHotGoods)
        LinearLayout linPersonStoreHotGoods;
        @BindView(R.id.linPersonStoreCustomer)
        LinearLayout linPersonStoreCustomer;

        public HolderStoreCoupon(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private class HolderNote extends RecyclerView.ViewHolder {
        public AutoVerticalScrollTextView txtNotice;
        public HolderNote(View itemView) {
            super(itemView);
            txtNotice = (AutoVerticalScrollTextView) itemView.findViewById(R.id.txtNotice);
        }
    }
    public class HolderStoreInfo extends  RecyclerView.ViewHolder {
        @BindView(R.id.ivPersonStoreBG)
        ImageView ivPersonStoreBG;
        @BindView(R.id.ivPersonStoreHead)
        ImageView ivPersonStoreHead;
        @BindView(R.id.tvPersonStoreName)
        TextView tvPersonStoreName;

        public HolderStoreInfo(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    private class HolderCoupon extends RecyclerView.ViewHolder {
        RecyclerView rvMenuTop;
        public HolderCoupon(View view) {
            super(view);
            rvMenuTop = (RecyclerView) view.findViewById(R.id.rvMenuTop);
        }
    }
    private class HolderGridIV extends RecyclerView.ViewHolder {
        RecyclerView rvMenuTop;
        public HolderGridIV(View view) {
            super(view);
            rvMenuTop = (RecyclerView) view.findViewById(R.id.rvMenuTop);
        }
    }
    public class HolderBanner extends  RecyclerView.ViewHolder {
        public MZBannerView banner;
        public HolderBanner(View itemView) {
            super(itemView);
            banner = (MZBannerView) itemView.findViewById(R.id.banner);
        }
    }
    private class HolderGridTitle extends RecyclerView.ViewHolder {
        RecyclerView rvMenuTop;
//        ImageView ivGridIV;
        public HolderGridTitle(View view) {
            super(view);
//            ivGridIV = (ImageView) itemView.findViewById(R.id.ivGridIV);
            rvMenuTop = (RecyclerView) itemView.findViewById(R.id.rvMenuTop);
        }
    }
}
