package com.bayue.live.deqingpu.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wx20184 on 2017/3/27.
 */

public class OKHttpUtils {
    private final static OkHttpClient M_OK_HTTP_CLIENT = new OkHttpClient();

    /**
     * 不开启异步线程
     *
     * @author wangsong 2015-10-9
     * @param request
     * @return
     * @throws IOException
     */
    public static Response execute(Request request) throws IOException {
        return M_OK_HTTP_CLIENT.newCall(request).execute();
    }

    /**
     * 开启异步线程访问，访问结果自行处理
     *
     * @author wangsong 2015-10-9
     * @param request
     * @param responseCallback
     */
    public static void enqueue(Request request, Callback responseCallback) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(responseCallback);
    }
    /**
     * 开启异步线程访问,不对访问结果进行处理
     *
     * @author wangsong 2015-10-9
     * @param request
     */
    public static void enqueue(Request request) {
        M_OK_HTTP_CLIENT.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    /**
     * 为HttpGet请求拼接一个参数
     *
     * @author wangsong 2015-10-9
     * @param url
     * @param name
     * @param value
     */
    public static String jointURL(String url, String name, String value) {
        return url + "?" + name + "=" + value;
    }

    /**
     * 为HttpGet请求拼接多个参数
     *
     * @author wangsong 2015-10-9
     * @param url
     * @param values
     */
    public static String jointURL(String url, Map<String, String> values) {
        StringBuffer result = new StringBuffer();
        result.append(url).append("?");
        Set<String> keys = values.keySet();
        for (String key : keys) {
            result.append(key).append("=").append(values.get(key)).append("&");
        }
        return result.toString().substring(0, result.toString().length()-1);
    }
}
