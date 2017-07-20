package com.bayue.live.deqingpu.ui.geren.pouch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.AountInfo;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class PouchWithdrawActivity extends BaseActivity {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.txtPouchAlipayUnBindTip)
    TextView txtPouchAlipayUnBindTip;
    @BindView(R.id.ivPouchSelectAlipay)
    ImageView ivPouchSelectAlipay;
    @BindView(R.id.linBindAlipay)
    LinearLayout linBindAlipay;
    @BindView(R.id.txtPouchWeChatUnBindTip)
    TextView txtPouchWeChatUnBindTip;
    @BindView(R.id.ivPouchSelectWeChat)
    ImageView ivPouchSelectWeChat;
    @BindView(R.id.linBindWeChat)
    LinearLayout linBindWeChat;
    @BindView(R.id.edtPouthRechangeMoney)
    EditText edtPouthRechangeMoney;
    @BindView(R.id.txtPouchWithDrawMoney)
    TextView txtPouchWithDrawMoney;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    SaveObjectUtils utils;
    String TAG = "PouchWithdraw";
    private Novate novate;
    String withdrawType = "alipay";
    @Override
    protected int getViewId() {
        return R.layout.ac_rechange;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.txt_withdrawals));
        txtPouchWithDrawMoney.setVisibility(View.VISIBLE);
        utils=new SaveObjectUtils(baseContext,"Info");
        AountInfo info = utils.getObject("Info", AountInfo.class);
        reset();
        if (!Guard.isNull(info)) {
            Tracer.e(TAG, info.getData().getPhone() + " phone");
//            String text = "<font color = '#707070'> 彩虹的颜色是：</font>"
//                    + "<font color = '#ff0000'>红</font>"
//                    + "<font color = '#FCCC2C'>橙</font>"
//                    + "<font color = '#FDFD00'>黄</font>"
//                    + "<font color = '#00ff00'>绿</font>"
//                    + "<font color = '#01FFFF'>青</font>"
//                    + "<font color = '#0000ff'>蓝</font>"
//                    + "<font color = '#A700FF'>紫</font>"
//                    + "<font color = '#00ff00'>。</font>";
//            String str1=String.format("可提现金额 ：<font color=\"#d40000\">%s",info.getData().getMoney(),"元");
//                    String.format("￥%1$.2f元", info.getData().getMoney()));
            String text = "<font color = '#808080'> 可提现金额：</font>"
                    +"<font color = '#000000'> "+ info.getData().getMoney() +"</font>"
                    +"<font color = '#808080'>元</font>";
            txtPouchWithDrawMoney.setText(Html.fromHtml(text));
            if (Guard.isNull(info.getData().getAlipay())){
                ivPouchSelectAlipay.setVisibility(View.GONE);
                txtPouchAlipayUnBindTip.setVisibility(View.VISIBLE);
            }else {
                ivPouchSelectAlipay.setImageResource(R.mipmap.icon_52);
            }
            if (Guard.isNull(info.getData().getWechat())){
                ivPouchSelectWeChat.setVisibility(View.GONE);
                txtPouchWeChatUnBindTip.setVisibility(View.VISIBLE);
            }
        }
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();

    }

    private void beginGet(final String url, final Map<String, Object> map, final int LoadStatus){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(url, map, LoadStatus);
            }
        }, 300);

    }
    private void getDataFromNet(String url, Map<String, Object> hashMap, final int LoadStatus) {
        novate.post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                }
            }

            @Override
            public void forceClose(ProgressDialog progress) {

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
                switch (LoadStatus) {
                    case 1:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == Constants.CODE_OK){
                            ToastUtils.showLongToast(r.getData());
                            finish();
                        }else {
                            ToastUtils.showLongToast(r.getMsg());
                        }
                        break;

                }
            }
        });

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
    @OnClick({R.id.linBindAlipay, R.id.linBindWeChat, R.id.btnConmon})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.linBindAlipay:
                reset();
                ivPouchSelectAlipay.setImageResource(R.mipmap.icon_52);
                withdrawType = "alipay";
                break;
            case R.id.linBindWeChat:
                reset();
                ivPouchSelectWeChat.setImageResource(R.mipmap.icon_52);
                withdrawType = "wechat";
                break;
            case R.id.btnConmon:
                String money = edtPouthRechangeMoney.getText().toString();
                if (Guard.isNullOrEmpty(money)){
                    ToastUtils.showLongToast(getString(R.string.hint_money));
                    return;
                }
                Map<String, Object> map = Constants.getMap(baseContext);
                map.put("type", withdrawType);
                map.put("amount", money);
//                Constants.LogMap(map);
                beginGet(API.User.POUCH_WITHDRAW, map, 1);
                break;
        }
    }

    void reset(){
        ivPouchSelectAlipay.setImageResource(R.mipmap.icon_51);
        ivPouchSelectWeChat.setImageResource(R.mipmap.icon_51);
    }
}
