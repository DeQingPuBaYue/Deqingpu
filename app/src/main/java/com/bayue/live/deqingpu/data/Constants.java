package com.bayue.live.deqingpu.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description:
 */

public class Constants {
    public static final String ARGS = "args";
    private static Map<String, Object> map = new HashMap<>();
    public static Map<String, Object> getMap(){
        map.put("safecode","BaYue.deqingpu");
        map.put("apiversion","v.1.0");
        return map;
    }
}
