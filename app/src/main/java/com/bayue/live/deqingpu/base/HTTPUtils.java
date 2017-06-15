package com.bayue.live.deqingpu.base;

import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.Tracer;

import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class HTTPUtils {
    public static void getNetDATA(String url, Map<String, Object> map, Callback callback){
        Tracer.e("AddAddressActivity", url);
        map.put("safecode", "BaYue.deqingpu");
        map.put("apiversion", "v.1.0");
        RequestBody body ;
        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : map.entrySet()){
            formBody.add(entry.getKey(), String.valueOf(entry.getValue()));
            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }
        body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request,callback);
    }
}
