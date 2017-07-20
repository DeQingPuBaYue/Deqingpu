package com.bayue.live.deqingpu.entity;

/**
 * Created by BaYue on 2017/7/4 0004.
 * email : 2651742485@qq.com
 */

public class WeChatBean {


    /**
     * code : 200
     * data : {"appid":"wx78bd0448d5b47bf8","attach":"26&2017062797473","body":"德卿铺--订单支付","mchid":"1373320102","nonce_str":"ts3r8mtmsduf5kce9rfiijo202ghbbyi","notify_url":"http://dqp.bayuenet.com/wechat/payment/notify","out_trade_no":"2017062797473","sign":"40E62FE74BBDE13DDE15A131D0630B3D","spbill_create_ip":"116.225.72.45","total_fee":1,"trade_type":"APP"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * appid : wx78bd0448d5b47bf8
         * attach : 26&2017062797473
         * body : 德卿铺--订单支付
         * mchid : 1373320102
         * nonce_str : ts3r8mtmsduf5kce9rfiijo202ghbbyi
         * notify_url : http://dqp.bayuenet.com/wechat/payment/notify
         * out_trade_no : 2017062797473
         * sign : 40E62FE74BBDE13DDE15A131D0630B3D
         * spbill_create_ip : 116.225.72.45
         * total_fee : 1
         * trade_type : APP
         */

        private String appid;
        private String attach;
        private String body;
        private String mchid;
        private String nonce_str;
        private String notify_url;
        private String out_trade_no;
        private String sign;
        private String spbill_create_ip;
        private int total_fee;
        private String trade_type;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getAttach() {
            return attach;
        }

        public void setAttach(String attach) {
            this.attach = attach;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getMchid() {
            return mchid;
        }

        public void setMchid(String mchid) {
            this.mchid = mchid;
        }

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getSpbill_create_ip() {
            return spbill_create_ip;
        }

        public void setSpbill_create_ip(String spbill_create_ip) {
            this.spbill_create_ip = spbill_create_ip;
        }

        public int getTotal_fee() {
            return total_fee;
        }

        public void setTotal_fee(int total_fee) {
            this.total_fee = total_fee;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }
    }
}
