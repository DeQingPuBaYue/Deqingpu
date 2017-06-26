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
            context.startActivity(new Intent(context, DengLu.class));
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
}
