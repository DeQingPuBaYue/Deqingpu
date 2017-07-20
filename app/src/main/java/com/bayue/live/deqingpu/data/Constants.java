package com.bayue.live.deqingpu.data;

import android.content.Context;
import android.content.Intent;

import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.denglu.DengLu;
import com.bayue.live.deqingpu.utils.Tracer;

import java.util.HashMap;
import java.util.Map;

import static com.bayue.live.deqingpu.utils.Utils.getContext;

/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class Constants {
    public static final String ARGS = "args";
//    private static Map<String, Object> map = new HashMap<>();
    public static final int CODE_OK = 200;
    public static final int CODE_ERROR = -300;
    public static Map<String, Object> getMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("safecode","BaYue.deqingpu");
        map.put("apiversion","v.1.0");
        String token = Preferences.getString(getContext(), Preferences.TOKEN);
        if (token.equals("-1")){
            token = "";
        }
        map.put("token", token);
        return map;
    }
    public static Map<String, Object> getMap(Context context){
        Map<String, Object> map = new HashMap<>();
        map.put("safecode","BaYue.deqingpu");
        map.put("apiversion","v.1.0");
        String token = Preferences.getString(getContext(), Preferences.TOKEN);
        if (token.equals("-1")){
            context.startActivity(new Intent(context, DengLu.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//            finish();
            token = "";
        }
        map.put("token", token);
        return map;
    }
    public static void LogMap(Map<String, Object> map){
        for (Map.Entry<String, Object> entry : map.entrySet()){
            Tracer.e("Constants_MapValue", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }
    }

    public static int getShippingStatus(String btn){
        int shipping = -1;
        switch (btn){
            case "cancel"://取消
                shipping = 1;
                break;
            case "comment":// 评论
                shipping = 2;
                break;
            case "delete"://删除
                shipping = 3;
                break;
            case "logistics"://查看物流
                shipping = 4;
                break;
            case "pay":// 支付
                shipping = 5;
                break;
            case "receipt"://确认收货
                shipping = 6;
                break;
            case "refund"://退货退款
                shipping = 7;
                break;
        }
        return shipping;
    }

}
