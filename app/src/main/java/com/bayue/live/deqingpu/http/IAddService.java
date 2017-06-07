package com.bayue.live.deqingpu.http;

import com.bayue.live.deqingpu.entity.Status;
import com.bayue.live.deqingpu.entity.Video;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Administrator on 2017/6/5.
 */

public interface IAddService {

    @POST("api/address/getaddress")
    Call<List<Status>> GetAddress(@QueryMap Map<String, String> options);

}
