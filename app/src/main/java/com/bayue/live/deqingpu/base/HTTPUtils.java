package com.bayue.live.deqingpu.base;

import android.content.Context;

import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.UserInfo;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.http.IAddService;
import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.tamic.novate.Novate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
//            Tracer.e("AddAddressActivity", entry.getKey() +":"+String.valueOf(entry.getValue()));
        }
        body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request,callback);
    }
    public static Novate getNovate(Context context){
        Novate novate = new Novate.Builder(context)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        return novate;
    }

    public static void getDateFormRetrofit(Context context, Map<String, Object> map, retrofit2.Callback<Return> callback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IAddService service = retrofit.create(IAddService.class);
        Call<Return> repos = service.listRepos(map);
        repos.enqueue(callback);
    }
}
