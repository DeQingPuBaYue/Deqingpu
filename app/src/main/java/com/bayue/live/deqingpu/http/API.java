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
    private final static String baseUrl_inner = "http://192.168.1.200/bayue/deqingpu/public/";
    private final static String Domain_Inner = "http://192.168.1.200/bayue/deqingpu/public/";
    private final static String Domain_net = "http://dqp.bayuenet.com/";
    private final static String baseUrl_net = isInner() ? Domain_Inner  : Domain_net;
    public final static String baseUrl = Tracer.isUseDebugNet() ? baseUrl_inner : baseUrl_net;
    //获取所有地址
    public static final String GETADDRESS= "api/address/get_region";
    //获取地址列表(token )  删除地址(address_id , token)
    public static final String GETADDRESS_LIST = "api/address/getaddress";
    //更新地址(address_id ,consignee ,province,city,district,address,mobile,token)
    public static final String UPDATE= "api/address/update";
    //增加地址(consignee ,province,city,district,address,mobile,token)
    public static final String ADDADDRESS= "api/address/add";
    //设为默认（address_id，token ）
    public static final String DEFAULT= "api/address/setdefault";
    //删除地址（address_id，token ）
    public static final String DELECT= "api/address/delete";

    /**
     * 认证
     * */
    public static class AUTH{
        public static final String REAL= "api/preaudit/real";
        public static final String STORE= "api/preaudit/store";
        public static final String COMPANY= "api/preaudit/company";
        public static final String IDENTITY= "api/preaudit/identity";
        public static final String STATE= "api/preaudit/state";
    }

    /**
     * 商家
     * */
    public static class Merchant{
        public static final String STORE_LIST = "api/goods/store_list";
        public static final String STORE_DETAIL = "api/goods/store_detail";
        public static final String COMMENT_LIST = "api/goods/comment_list";
        public static final String COMMENT_ADD = "api/goods/add_comment";
        public static final String GOODS_LIST = "api/goods/goods_list";
        public static final String GOODS_DETAIL = "api/goods/goods_detail";
    }

    //登录
    public static final String DENGLU="api/login/signin";
    //注册
    public static final String ZHUCE="api/login/register";
    //验证码
    public static final String YANZHENG="api/sms/get_code";
    //找回密码
    public static final String ZHAOHUIE="api/login/get_pwd";

    //圈子列表
    public static final String QUANZI_LIEBIAO="api/society/list";
    //发布圈子
    public static final String QUANZI_FABU="api/society/publish";
    //圈子好友
    public static final String QUANZI_HAOYOU="api/society/firend";
    //圈子关注
    public static final String QUANZI_GUANZHU="api/society/attention";


    /*
    购物车
     */
    public static class Cart{

        public static final String ADD_TO_CART = "api/cart/add_to_cart";
        public static final String CART_LIST = "api/cart/cart_list";
        public static final String CHECKOUT = "api/cart/checkout";
        public static final String CART_PRICE = "api/cart/cart_price";


    }



}
