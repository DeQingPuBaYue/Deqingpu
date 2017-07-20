package com.bayue.live.deqingpu.ui.geren;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.AountInfo;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.geren.pouch.PouchRechangeActivity;
import com.bayue.live.deqingpu.ui.geren.pouch.PouchTransRecordActivity;
import com.bayue.live.deqingpu.ui.geren.pouch.PouchWithdrawActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.CommomDialog;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/26 0026.
 * email : 2651742485@qq.com
 */

public class MyPouchActivity extends BaseActivity {

    String TAG = "MyPouchActivity";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.txtMyBlance)
    TextView txtMyBlance;
    @BindView(R.id.txtPouchRechange)
    TextView txtPouchRechange;
    @BindView(R.id.txtPouchWithDraw)
    TextView txtPouchWithDraw;
    @BindView(R.id.linSpecBtn)
    LinearLayout linSpecBtn;
    @BindView(R.id.linBindAlipay)
    LinearLayout linBindAlipay;
    @BindView(R.id.linBindWeChat)
    LinearLayout linBindWeChat;
    @BindView(R.id.linTransRecord)
    LinearLayout linTransRecord;
//    private Novate novate;
    SaveObjectUtils utils;
    @Override
    protected int getViewId() {
        return R.layout.ac_mypouch;
    }

    @Override
    protected void initViews() {
        utils=new SaveObjectUtils(baseContext,"Info");
        topBar.setTitle(getString(R.string.title_mypouch));
//        AountInfo info = utils.getObject("info", AountInfo.class);
//        if (!Guard.isNull(info)) {
//            Tracer.e(TAG, info.getData().getPhone() + " phone");
//        }
//        novate = new Novate.Builder(baseActivity)
//                //.addParameters(parameters)//公共参数
//                .connectTimeout(5)
//                .writeTimeout(10)
//                .baseUrl(API.baseUrl)
////                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
//                .addLog(true)
//                .build();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Map<String, Object> map = Constants.getMap(baseContext);
        Constants.LogMap(map);
        beginGet(API.User.POUCH_INFO, map, 1);
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
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

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
                        AountInfo info = (AountInfo) GsonHelper.getInstanceByJson(AountInfo.class, jstr);
                        if (info.getCode()== Constants.CODE_OK){
                            utils.setObject("Info", info);
                        }
                        String money = "0";
                        if (!Guard.isNullOrEmpty(info.getData().getMoney())) {
                            money = info.getData().getMoney();
                        }
                        txtMyBlance.setText(money);
                    break;
                    case 2:
                    case 3:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == Constants.CODE_OK){
                            ToastUtils.showLongToast(r.getData());
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

    @OnClick({R.id.txtPouchRechange, R.id.txtPouchWithDraw, R.id.linBindAlipay, R.id.linBindWeChat, R.id.linTransRecord})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.txtPouchRechange:
                startActivity(new Intent(baseContext, PouchRechangeActivity.class));
                break;
            case R.id.txtPouchWithDraw:
                startActivity(new Intent(baseContext, PouchWithdrawActivity.class));
                break;
            case R.id.linBindAlipay:
                new CommomDialog(baseActivity, R.style.dialog, getString(R.string.hint_alipay_acount), false, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, EditText edtBindAlipay) {
                        if (confirm){
                            if (Guard.isNullOrEmpty(edtBindAlipay.getText().toString())){
                                ToastUtils.showLongToast(getString(R.string.hint_alipay_acount));
                                return;
                            }

                            Map<String, Object> map = Constants.getMap(baseContext);
                            map.put("type","alipay");
                            map.put("account",edtBindAlipay.getText().toString());
                            beginGet(API.User.POUCH_BIND, map, 2);
                            dialog.dismiss();
                        }
                    }
                })
                .setTitle(getString(R.string.txt_bind_alipay)).show();
                break;
            case R.id.linBindWeChat:
                new CommomDialog(baseActivity, R.style.dialog, getString(R.string.hint_wechat_acount), false, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm, EditText edtBindWeChat) {
                        if (confirm){
                            if (Guard.isNullOrEmpty(edtBindWeChat.getText().toString())){
                                ToastUtils.showLongToast(getString(R.string.hint_wechat_acount));
                                return;
                            }
                            Map<String, Object> map = Constants.getMap(baseContext);
                            map.put("type","wechat");
                            map.put("account",edtBindWeChat.getText().toString());
                            beginGet(API.User.POUCH_BIND, map, 3);
                            dialog.dismiss();
                        }
                    }
                })
                        .setTitle(getString(R.string.txt_bind_wechat)).show();
                break;
            case R.id.linTransRecord:
                startActivity(new Intent(baseContext, PouchTransRecordActivity.class));
                break;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
