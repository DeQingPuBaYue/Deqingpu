package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/14.
 * email : 2651742485@qq.com
 */

public class MerchantFood {


    /**
     * data : [{"region_name":"东城区","business":"水果店","sales":2600,"store_id":15,"store_name":"联盟商家店铺15","store_avatar":"https://www.juhe.cn/themes/images/data/new/a15.png","distance":724,"activity":"商家活动5折"}]
     * code : 200
     * count_page : 0
     * page : 1
     */

    private int code;
    private int count_page;
    private String page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount_page() {
        return count_page;
    }

    public void setCount_page(int count_page) {
        this.count_page = count_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * region_name : 东城区
         * business : 水果店
         * sales : 2600
         * store_id : 15
         * store_name : 联盟商家店铺15
         * store_avatar : https://www.juhe.cn/themes/images/data/new/a15.png
         * distance : 724
         * activity : 商家活动5折
         */

        private String region_name;
        private String business;
        private int sales;
        private int store_id;
        private String store_name;
        private String store_avatar;
        private int distance;
        private String activity;

        public String getRegion_name() {
            return region_name;
        }

        public void setRegion_name(String region_name) {
            this.region_name = region_name;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public String getActivity() {
            return activity;
        }

        public void setActivity(String activity) {
            this.activity = activity;
        }
    }
}
