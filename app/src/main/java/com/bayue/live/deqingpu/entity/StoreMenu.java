package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/7/19 0019.
 * email : 2651742485@qq.com
 */

public class StoreMenu {


    /**
     * code : 200
     * data : {"banner":[{"pic":"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg","type":"cat","value":1},{"pic":"http://img0.ph.126.net/GPSkoQoa6lJze_6NhWchtg==/1150388229834327051.jpg","type":"cat","value":1},{"pic":"http://www.wed114.cn/jiehun/uploads/allimg/160909/57_160909161244_2.jpg","type":"cat","value":1}],"coupon":[{"max_amount":"0.00","min_amount":"1.00","money":"10.00","name":"10元优惠券"},{"max_amount":"0.00","min_amount":"1.00","money":"10.00","name":"10元优惠券"}],"merchant_info":{"Notice":["第一条公告","第二条公告","第三条公告"],"merchant_address":"上海天祝路818号","merchant_banner":"http://photos.tuchong.com/346722/m/6331171.jpg","merchant_name":"我的掌柜","merchant_pic":"http://v1.qzone.cc/avatar/201303/18/17/14/5146daf3","name":"季先生","support":"0137425"}}
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
         * banner : [{"pic":"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg","type":"cat","value":1},{"pic":"http://img0.ph.126.net/GPSkoQoa6lJze_6NhWchtg==/1150388229834327051.jpg","type":"cat","value":1},{"pic":"http://www.wed114.cn/jiehun/uploads/allimg/160909/57_160909161244_2.jpg","type":"cat","value":1}]
         * coupon : [{"max_amount":"0.00","min_amount":"1.00","money":"10.00","name":"10元优惠券"},{"max_amount":"0.00","min_amount":"1.00","money":"10.00","name":"10元优惠券"}]
         * merchant_info : {"Notice":["第一条公告","第二条公告","第三条公告"],"merchant_address":"上海天祝路818号","merchant_banner":"http://photos.tuchong.com/346722/m/6331171.jpg","merchant_name":"我的掌柜","merchant_pic":"http://v1.qzone.cc/avatar/201303/18/17/14/5146daf3","name":"季先生","support":"0137425"}
         */

        private MerchantInfoBean merchant_info;
        private List<BannerBean> banner;
        private List<CouponBean> coupon;

        public MerchantInfoBean getMerchant_info() {
            return merchant_info;
        }

        public void setMerchant_info(MerchantInfoBean merchant_info) {
            this.merchant_info = merchant_info;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public static class MerchantInfoBean {
            /**
             * Notice : ["第一条公告","第二条公告","第三条公告"]
             * merchant_address : 上海天祝路818号
             * merchant_banner : http://photos.tuchong.com/346722/m/6331171.jpg
             * merchant_name : 我的掌柜
             * merchant_pic : http://v1.qzone.cc/avatar/201303/18/17/14/5146daf3
             * name : 季先生
             * support : 0137425
             */

            private String merchant_address;
            private String merchant_banner;
            private String merchant_name;
            private String merchant_pic;
            private String name;
            private String support;
            private List<String> Notice;

            public String getMerchant_address() {
                return merchant_address;
            }

            public void setMerchant_address(String merchant_address) {
                this.merchant_address = merchant_address;
            }

            public String getMerchant_banner() {
                return merchant_banner;
            }

            public void setMerchant_banner(String merchant_banner) {
                this.merchant_banner = merchant_banner;
            }

            public String getMerchant_name() {
                return merchant_name;
            }

            public void setMerchant_name(String merchant_name) {
                this.merchant_name = merchant_name;
            }

            public String getMerchant_pic() {
                return merchant_pic;
            }

            public void setMerchant_pic(String merchant_pic) {
                this.merchant_pic = merchant_pic;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSupport() {
                return support;
            }

            public void setSupport(String support) {
                this.support = support;
            }

            public List<String> getNotice() {
                return Notice;
            }

            public void setNotice(List<String> Notice) {
                this.Notice = Notice;
            }
        }

        public static class BannerBean {
            /**
             * pic : http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg
             * type : cat
             * value : 1
             */

            private String pic;
            private String type;
            private int value;

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }

        public static class CouponBean {
            /**
             * max_amount : 0.00
             * min_amount : 1.00
             * money : 10.00
             * name : 10元优惠券
             */

            private String max_amount;
            private String min_amount;
            private String money;
            private String name;

            public String getMax_amount() {
                return max_amount;
            }

            public void setMax_amount(String max_amount) {
                this.max_amount = max_amount;
            }

            public String getMin_amount() {
                return min_amount;
            }

            public void setMin_amount(String min_amount) {
                this.min_amount = min_amount;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
