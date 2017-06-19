package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/16.
 * email : 2651742485@qq.com
 */

public class GoodsBean {

    /**
     * code : 200
     * count_page : 1
     * data : [{"goods_id":59,"goods_name":"测试商品6","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","market_price":"120.00","sales":0,"shop_price":"90.00"}]
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
         * goods_id : 59
         * goods_name : 测试商品6
         * goods_thumb : https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg
         * market_price : 120.00
         * sales : 0
         * shop_price : 90.00
         */

        private int goods_id;
        private String goods_name;
        private String goods_thumb;
        private String market_price;
        private int sales;
        private String shop_price;

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getShop_price() {
            return shop_price;
        }

        public void setShop_price(String shop_price) {
            this.shop_price = shop_price;
        }
    }
}
