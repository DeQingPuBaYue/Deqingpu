package com.bayue.live.deqingpu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
//import com.tencent.mm.opensdk.constants.ConstantsAPI;
//import com.tencent.mm.opensdk.modelbase.BaseReq;
//import com.tencent.mm.opensdk.modelbase.BaseResp;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * Created by BaYue on 2017/7/4 0004.
 * email : 2651742485@qq.com
 */

public class WXEntryActivity extends Activity {
//        implements IWXAPIEventHandler {
//    private IWXAPI api;
    private String TAG = "WXEntryActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        api = WeChatInfo.getWX();
//        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
//        api.handleIntent(intent, this);
    }

//    @Override
//    public void onReq(BaseReq baseReq) {
//
//    }

//    @Override
//    public void onResp(BaseResp resp) {
//        Tracer.e(TAG, "onPayFinish, errCode = " + resp.errCode);
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//            int code = resp.errCode;
//            String msg = "";
//            switch (code) {
//                case 0:
//                    msg = "支付成功！";
//                    break;
//                case -1:
//                    msg = "支付失败！";
//                    break;
//                case -2:
//                    msg = "您取消了支付！";
//                    break;
//                default:
//                    msg = " 支付失败！";
//                    break;
//            }
//            ToastUtils.showShortToast(msg);
////            SharedPreferences preferences = getSharedPreferences("isReqWeChat", MODE_PRIVATE);
////            SharedPreferences.Editor editor = preferences.edit();
////            editor.clear().commit();
//            this.finish();
//        }
//    }
}
