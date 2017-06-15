package com.bayue.live.deqingpu.data;

import com.bayue.live.deqingpu.preferences.Preferences;

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
    private static Map<String, Object> map = new HashMap<>();
    public static final int CODE_OK = 200;
    public static final int CODE_ERROR = -300;
    public static Map<String, Object> getMap(){
        map.put("safecode","BaYue.deqingpu");
        map.put("apiversion","v.1.0");
        map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
        return map;
    }
}
