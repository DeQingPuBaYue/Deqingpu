package com.bayue.live.deqingpu.ui.merchant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.CommentAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.BaseSubscriber;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.MerchantFood;
import com.bayue.live.deqingpu.entity.StoreDetail;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.merchant.pay.PayActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.bumptech.glide.Glide;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/14.
 * email : 2651742485@qq.com
 */

public class MerchantDetailActivity extends BaseActivity {

    String TAG = "MerchantDetail";
    String json = "";
    StoreDetail storeDetail;
    @BindView(R.id.imgMerchantDetailBanner)
    ImageView imgMerchantDetailBanner;
    @BindView(R.id.txtMerchantDetailStoreName)
    TextView txtMerchantDetailStoreName;
    @BindView(R.id.txtMerchantDetailCount)
    TextView txtMerchantDetailCount;
    @BindView(R.id.btnMerchantCheck)
    Button btnMerchantCheck;
    @BindView(R.id.txtMerchantDetailLocation)
    TextView txtMerchantDetailLocation;
    @BindView(R.id.txtMerchantCall)
    TextView txtMerchantCall;
    @BindView(R.id.txtJumpCall)
    TextView txtJumpCall;
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
    @BindView(R.id.txtNotice)
    TextView txtNotice;
    @BindView(R.id.txtShowCommentSlide)
    TextView txtShowCommentSlide;
    @BindView(R.id.txtFavorableRate)
    TextView txtFavorableRate;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    @BindView(R.id.txtCommentAll)
    TextView txtCommentAll;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private Novate novate;
    String storePhone = "", storeId;
    int goodsCount = 0;
    CommentAdapter commentAdapter;
    @Override
    protected int getViewId() {
        return R.layout.ac_merchant;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String msg = "";
                switch (menuItem.getItemId()) {
                    case R.id.action_edit:
                        msg += "Click edit";
                        break;
                    case R.id.action_share:
                        msg += "Click share";
                        break;
                }
                ToastUtils.showLongToast(msg);
                return true;
            }
        });
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        json = getIntent().getStringExtra("json");
        storeId = getIntent().getStringExtra("storeId");
        Tracer.e(TAG, json);
        storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, json);
        if (!Guard.isNull(storeDetail)) {
            StoreDetail.DataBean bean = storeDetail.getData();
            storePhone = bean.getPhone();
//            storeId = bean.
            Glide.with(baseActivity).load(bean.getStore_avatar())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.ic_launcher_round).into(imgMerchantDetailBanner);
            Glide.with(baseActivity).load(bean.getStore_avatar())
                    .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round).into(imgMerchantShopAvator);
            txtMerchantDetailStoreName.setText(bean.getStore_name());
            txtMerchantDetailCount.setText(bean.getSales()+"人消费");
            txtMerchantDetailLocation.setText(bean.getStore_address());
            txtMerchantAvatorShop.setText(bean.getStore_name());
            txtMerchantAvatorBusi.setText(bean.getName());
            goodsCount = bean.getNumber();
            txtMerchantGoodsCount.setText(goodsCount+"\n" +"全部商品");
//            txtNotice.setText(bean.getNote());
        }
        initComment();
    }

    private void initComment() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Map<String, Object> map = Constants.getMap();
                map.put("comment_type", "2");
                map.put("id_value", storeId);
                map.put("page", "1");
                Tracer.e(TAG, "storeId:" + storeId);
                getDataFromNet(API.Merchant.COMMENT_LIST, map, 0, 0);
            }
        }, 300);
    }
    private void getDataFromNet(String url, Map<String, Object> hashMap, final int status, final int loadStatus) {
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
                CommentBean commentBean = (CommentBean) GsonHelper.getInstanceByJson(CommentBean.class, jstr);
                int count = 0, favorable = 100;
                if (commentBean.getCount()>0){
                    count = commentBean.getCount();
                }
                    favorable = commentBean.getFavorable();
                txtShowCommentSlide.setText("评论晒图（"+ count + "）");
                txtFavorableRate.setText("好评率："+ favorable + "%");
                List<CommentBean.DataBean> dataBeans = new ArrayList<>();
                if (!Guard.isNull(commentBean.getData())){
                    if (commentBean.getData().size() > 0){
                        for (int i=0;i<commentBean.getData().size();i++){
                            if (!Guard.isNull(commentBean.getData().get(i).getComment_img())){
                                dataBeans.add(commentBean.getData().get(i));
                                break;
                            }
                        }

                    }
                }
                Tracer.e(TAG, dataBeans.size() +" dataBeans");
                commentAdapter = new CommentAdapter(baseContext, dataBeans);
                listViewOneComment.setAdapter(commentAdapter);

            }
        });

    }
    @OnClick({R.id.btnMerchantCheck, R.id.txtMerchantCall, R.id.txtJumpCall, R.id.linMerchantGoods, R.id.txtCommentAll})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.btnMerchantCheck:
                startActivity(new Intent(MerchantDetailActivity.this, PayActivity.class));
                break;
            case R.id.txtMerchantCall:
                break;
            case R.id.txtJumpCall:
                if (!Guard.isNullOrEmpty(storePhone)) {
                    Intent nIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + storePhone));
                    startActivity(nIntent);
                }
                break;
            case R.id.linMerchantGoods:
//                if (goodsCount>0){
                    startActivity(new Intent(baseContext,GoodsListActivity.class));
//                }
                break;
            case R.id.txtCommentAll:
                break;
        }
    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
