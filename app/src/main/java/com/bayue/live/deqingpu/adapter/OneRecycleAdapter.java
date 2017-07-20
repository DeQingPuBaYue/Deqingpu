package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.MenuBean;
import com.bayue.live.deqingpu.ui.merchant.MerchantGoodsDetailActivity;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.view.AutoVerticalScrollTextView;
import com.bumptech.glide.Glide;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class OneRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    MenuBean menuBean;
    //type
    public static final int TYPE_BANNER = 0xff01;
    public static final int TYPE_GRID = 0xff02;
    public static final int TYPE_NOTE = 0xff03;
    public static final int TYPE_GRID_HEADER = 0xff04;
    public static final int TYPE_GRID_HEADER_LESS = 0xff05;
    public static final int TYPE_TYPE4 = 0xff06;
    public static final int TYPE_TYPE5 = 0xff07;
    public static final int TYPE_GRID_IV = 0xff08;
    public static final int TYPE_GRID_IV_Title = 0xff09;
    public static final int TYPE_GRID_GOODS = 0xff10;
    public static final int TYPE_GRID_GOODS_TITLE = 0xff11;
    public static final int TYPE_GRID_HEALTH = 0xff12;
    public static final int TYPE_GRID_HEALTH_TITLE = 0xff13;
    public static final int TYPE_GRID_WASH = 0xff14;
    public static final int TYPE_GRID_WASH_TITLE = 0xff15;
    public static final int TYPE_GRID_SKINCARE = 0xff16;
    public static final int TYPE_GRID_SKINCARE_TITLE = 0xff17;

    public OneRecycleAdapter(Context context, MenuBean menuBean) {
        this.context = context;
        this.menuBean = menuBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_GRID:
                return new HolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_grid, parent, false));
            case TYPE_NOTE:
                return new HolderNote(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_note, parent, false));
            case TYPE_GRID_IV:
                return new HolderGridIV(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_iv_grid, parent, false));
            case TYPE_BANNER:
                return new HolderBanner(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_banner, parent, false));
            case TYPE_GRID_GOODS_TITLE:
                return new HolderTitleIV(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_iv_title, parent, false));
            case TYPE_GRID_GOODS:
                return new HolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_grid, parent, false));
            case TYPE_GRID_HEALTH_TITLE:
                return new HolderGridIV(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_iv_title, parent, false));
            case TYPE_GRID_HEALTH:
                return new HolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_grid, parent, false));
            case TYPE_GRID_WASH_TITLE:
                return new HolderGridIV(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_iv_title, parent, false));
            case TYPE_GRID_WASH:
                return new HolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_grid, parent, false));
            case TYPE_GRID_SKINCARE_TITLE:
                return new HolderGridIV(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_iv_title, parent, false));
            case TYPE_GRID_SKINCARE:
                return new HolderGrid(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_common_grid, parent, false));
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderBanner){
            bindTypeBanner((HolderBanner) holder, position);
        }else if (holder instanceof HolderGrid){
            bindTypeBrid((HolderGrid) holder, position);
        }else if (holder instanceof HolderNote){
            bindTypeNote((HolderNote) holder, position);
        }else if (holder instanceof HolderGridIV){
            bindTypeGridIV((HolderGridIV) holder, position);
        }else if (holder instanceof HolderTitleIV){
            bindTypeTitleIV((HolderTitleIV) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_GRID;
        }else if (position == 1){
            return TYPE_NOTE;
        }else if (2==position){
            return TYPE_GRID_IV;
        }
        else if (position ==3){
            return TYPE_BANNER;
        }else if (position == 4){
            return TYPE_GRID_GOODS_TITLE;
        }
        else if (position == 5){
            return TYPE_GRID;
        }
        else if (position == 6){
            return TYPE_GRID_GOODS_TITLE;
        }
        else if (position == 7){
            return TYPE_GRID_HEALTH;
        }
        else if (position == 8){
            return TYPE_GRID_GOODS_TITLE;
        }
        else if (position == 9){
            return TYPE_GRID_HEALTH;
        }
        else if (position == 10){
            return TYPE_GRID_GOODS_TITLE;
        }
        else if (11==position){
            return TYPE_GRID_HEALTH;
        }else {
            return TYPE_GRID_GOODS_TITLE;
        }
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
//                        case TYPE_SLIDER:
//                        case TYPE_TYPE2_HEAD:
//                        case TYPE_TYPE3_HEAD:
//                        case TYPE_TYPE4:
//                            return gridManager.getSpanCount();
//                        case TYPE_TYPE2:
//                            return 3;
//                        case TYPE_TYPE3:
//                            return 2;
//                        default:
//                            return 3;
                        case TYPE_GRID:
                            return 1;
                        case TYPE_BANNER:
                        case TYPE_NOTE:
                        case TYPE_GRID_GOODS_TITLE:
                        case TYPE_GRID_HEALTH:
                            return 4;
                        case TYPE_GRID_IV:
                            return 2;
                        default:
                            return 3;
                    }
                }
            });
        }
    }

    /////////////////////////////

    private void bindTypeBrid(HolderGrid holder, int position) {
        String pic  = menuBean.getData().getMenu_1().get(position).getImg();
        String Cat_name  = menuBean.getData().getMenu_1().get(position).getCat_name();
        if (!Guard.isNullOrEmpty(pic) && !Guard.isNullOrEmpty(Cat_name)) {
            Glide.with(context).load(pic)
                    .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                    .into(holder.ivGoods);
            holder.tvMainGridGoods.setText(Cat_name);
        }
    }

    private void bindTypeBanner(HolderBanner holder, int position){
        holder.banner.setDelayedTime(3000);
        holder.banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                ToastUtils.showLongToast("click page:" + position);
            }
        });
        List<String> imgList = new ArrayList<>();
        if (menuBean.getData().getMenu_3().size() > 0) {
            for (int i = 0; i < menuBean.getData().getMenu_3().size(); i++) {
                String imgBaner = menuBean.getData().getMenu_3().get(i).getPic();
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

    private void bindTypeNote(HolderNote holder, int position) {
        List<String> noticeList = new ArrayList<>();
        noticeList.addAll(menuBean.getData().getMenu_2().getNote());
        holder.txtNotice.setTvBgColor(ContextCompat.getColor(context,R.color.title_bar_divide_line));
        holder.txtNotice.getResource(noticeList);
    }

    private void bindTypeGridIV(HolderGridIV holder, int position) {
        Glide.with(context).load(menuBean.getData().getMenu_1().get(position).getImg())
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                .into(holder.ivGrid);
    }
    private void bindTypeTitleIV(HolderTitleIV holder, int position) {
        Glide.with(context).load(menuBean.getData().getMenu_4().getImg())
                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                .into(holder.ivGrid);
    }


    /////////////////////////////

    public class HolderBanner extends  RecyclerView.ViewHolder {
        public MZBannerView banner;
        public HolderBanner(View itemView) {
            super(itemView);
            banner = (MZBannerView) itemView.findViewById(R.id.banner);
        }
    }
    public class HolderGrid extends  RecyclerView.ViewHolder {
        public TextView tvMainGridGoods;
        public ImageView ivGoods;
        public HolderGrid(View itemView) {
            super(itemView);
            tvMainGridGoods = (TextView) itemView.findViewById(R.id.tvMainGridGoods);
            ivGoods = (ImageView) itemView.findViewById(R.id.ivGoods);
        }
    }

    private class HolderNote extends RecyclerView.ViewHolder {
        public AutoVerticalScrollTextView txtNotice;
        public HolderNote(View itemView) {
            super(itemView);
            txtNotice = (AutoVerticalScrollTextView) itemView.findViewById(R.id.txtNotice);
        }
    }

    private class HolderGridIV extends RecyclerView.ViewHolder {
        public ImageView ivGrid;
        public HolderGridIV(View itemView) {
            super(itemView);
            ivGrid = (ImageView) itemView.findViewById(R.id.ivGridIV);
        }
    }
    private class HolderTitleIV extends RecyclerView.ViewHolder {
        public ImageView ivGrid;
        public HolderTitleIV(View itemView) {
            super(itemView);
            ivGrid = (ImageView) itemView.findViewById(R.id.ivGridIV);
        }
    }

}
