package com.bayue.live.deqingpu.http;

import com.bayue.live.deqingpu.utils.Tracer;

/**
 * Created by Administrator on 2017/6/5.
 */

public class API {
    public static boolean isInner() {
        return 1 == 2;
    }
    //http://192.168.1.120/bayue/deqingpu/public/
    private final static String baseUrl_inner = "http://192.168.1.120/bayue/deqingpu/public/";
    private final static String Domain_Inner = "http://192.168.1.120/bayue/deqingpu/public/";
    private final static String Domain_net = "http://dqp.bayuenet.com/";
    private final static String baseUrl_net = isInner() ? Domain_Inner  : Domain_net;
    public final static String baseUrl = Tracer.isUseDebugNet() ? baseUrl_inner : baseUrl_net;
    //获取所有地址
    public static String GETADDRESS= "api/address/get_region";
    //获取地址列表(token )  删除地址(address_id , token)
    public static String GETADDRESS_LIST = "api/address/getaddress";
    //更新地址(address_id ,consignee ,province,city,district,address,mobile,token)
    public static String UPDATE= "api/address/update";
    //增加地址(consignee ,province,city,district,address,mobile,token)
    public static String ADDADDRESS= "api/address/add";
    //设为默认（address_id，token ）
    public static String DEFAULT= "api/address/setdefault";
    //删除地址（address_id，token ）
    public static String DELECT= "api/address/delete";

    //登录
    public static String DENGLU="api/login/signin";
    //注册
    public static String ZHUCE="api/login/register";
    //验证码
    public static String YANZHENG="api/sms/get_code";
    //找回密码
    public static String ZHAOHUIE="api/login/get_pwd";


}
