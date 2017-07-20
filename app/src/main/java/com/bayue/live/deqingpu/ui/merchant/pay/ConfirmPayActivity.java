package com.bayue.live.deqingpu.ui.merchant.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.OrderListBean;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.WeChatBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bayue.live.deqingpu.wxapi.WeChatInfo;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
//import com.tencent.mm.opensdk.modelpay.PayReq;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/15.
 */

public class ConfirmPayActivity extends BaseActivity {

    String TAG = "ConfirmPay";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.tv_toupian_confirmpay)
    ImageView tvToupianConfirmpay;
    @BindView(R.id.tv_jine_confirmpay)
    TextView tvJineConfirmpay;
    @BindView(R.id.tv_dingdan_confirmpay)
    TextView tvDingdanConfirmpay;
    @BindView(R.id.iv_gr_hebao)
    ImageView ivGrHebao;
    @BindView(R.id.iv_yinliang_confirm)
    ImageView ivYinliangConfirm;
    @BindView(R.id.rl_yinliang_confirm)
    RelativeLayout rlYinliangConfirm;
    @BindView(R.id.iv_yl_confirm)
    ImageView ivYlConfirm;
    @BindView(R.id.iv_weixin_confirm)
    ImageView ivWeixinConfirm;
    @BindView(R.id.rl_weixin_confirm)
    RelativeLayout rlWeixinConfirm;
    @BindView(R.id.iv_gr_dizhi)
    ImageView ivGrDizhi;
    @BindView(R.id.iv_zhifubao_confirm)
    ImageView ivZhifubaoConfirm;
    @BindView(R.id.rl_zhifubao_confirm)
    RelativeLayout rlZhifubaoConfirm;
    @BindView(R.id.tv_zhifujine_confirm)
    TextView tvZhifujineConfirm;
    @BindView(R.id.ll_pay_confirm)
    LinearLayout llPayConfirm;
    @BindView(R.id.tvPayMoney)
    TextView tvPayMoney;

    private Novate novate;
    String amount, order_sn;
    WeChatBean weChatBean;
    int payment = 0;
    int actionType = 0;// 0 商家   ？  n 其他
    public static Activity instance;
    @Override
    protected int getViewId() {
        return R.layout.ac_pay_confirm;
    }

    @Override
    protected void initViews() {
        instance = this;
        topBar.setTitle(getString(R.string.title_pay_confirm));
        amount = getIntent().getStringExtra("goods_amount");
        order_sn = getIntent().getStringExtra("order_sn");
        actionType = getIntent().getIntExtra("actionType", 0);
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

        tvPayMoney.setText(String.format(getString(R.string.tv_order_pay),amount));
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

    @OnClick({R.id.rl_yinliang_confirm, R.id.rl_weixin_confirm, R.id.rl_zhifubao_confirm, R.id.ll_pay_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_yinliang_confirm:
                setImage();
                ivYinliangConfirm.setImageResource(R.drawable.icon_52);
                payment = 0;
                break;
            case R.id.rl_weixin_confirm:
                setImage();
                ivWeixinConfirm.setImageResource(R.drawable.icon_52);
                payment = 1;
                break;
            case R.id.rl_zhifubao_confirm:
                setImage();
                ivZhifubaoConfirm.setImageResource(R.drawable.icon_52);
                payment = 2;
                break;
            case R.id.ll_pay_confirm:

                startActivity(new Intent(ConfirmPayActivity.this, CompletePayActivity.class)
                        .putExtra("actionType",actionType)
                        .putExtra("goods_amount",amount)
                );
//                switch (payment) {
//                    case 0:
//                        break;
//                    case 1:
////                        ToastUtils.showShortToast(Utils.isWXAppInstalledAndSupported(baseContext)+" isWXApp");
//                        if (Utils.isWXAppInstalledAndSupported(baseContext)) {
//                            Map<String, Object> map = Constants.getMap();
//                            map.put("order_sn", order_sn);
//                            map.put("goods_amount", "0.01");
////        Constants.LogMap(map);
//                            beginGet(API.PayMent.WeChat, map, 0);
//                        }else {
//                            ToastUtils.showShortToast("未安装微信，提交失败。请尝试使用其他支付方式。");
//                        }
//                    break;
//                    case 2:
//                        break;
//                }
                break;
        }
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
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                if (status == 0) {
                    weChatBean = (WeChatBean) GsonHelper.getInstanceByJson(WeChatBean.class, jstr);
//                    if (orderListBean.getCode() == Constants.CODE_OK) {
//                    PayReq req = new PayReq();
                    //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
//                    req.appId			= weChatBean.getData().getAppid();
//                    req.partnerId		= weChatBean.getData().getMchid();
//                    req.prepayId		= json.getString("prepayid");
//                    req.nonceStr		= weChatBean.getData().getNonce_str();
//                    req.timeStamp		= json.getString("timestamp");
//                    req.packageValue	= "Sign=WXPay";
//                    req.sign			= weChatBean.getData().getSign();
//                    req.extData			= "app data"; // optional
                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
//                    WeChatInfo.getWX().sendReq(req);
//                    }
                } else if (status == 1) {
//                    storeDetail = (StoreDetail) GsonHelper.getInstanceByJson(StoreDetail.class, jstr);
//                    startActivity(new Intent(baseActivity, MerchantDetailActivity.class).putExtra("json", jstr).putExtra("storeId", storeId));
                    Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                    if (r.getCode() == Constants.CODE_OK){
                        ToastUtils.showLongToast(r.getData());
                    }else {
                        ToastUtils.showLongToast(r.getMsg());
                    }
                }
            }
        });

    }

    //圆点恢复
    private void setImage() {
        ivYinliangConfirm.setImageResource(R.drawable.icon_51);
        ivWeixinConfirm.setImageResource(R.drawable.icon_51);
        ivZhifubaoConfirm.setImageResource(R.drawable.icon_51);


    }
}
