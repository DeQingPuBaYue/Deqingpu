package com.bayue.live.deqingpu.entity.cart;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class CartOutBean {


    /**
     * data : [{"merchant_name":"我是掌柜","store_id":1,"goods_info":[{"rec_id":5,"store_id":1,"goods_id":"54","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品","market_price":"120.00","shop_price":"202.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}],"goods_attr_id":"864,866"},{"rec_id":6,"store_id":1,"goods_id":"55","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品2","market_price":"120.00","shop_price":"200.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":856,"attr":"材质:白18K金"},{"goods_attr_id":857,"attr":"钻石颜色:H"}],"goods_attr_id":"856,857\n"},{"rec_id":7,"store_id":1,"goods_id":"56","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品3","market_price":"120.00","shop_price":"90.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":844,"attr":"材质:红18K金"},{"goods_attr_id":845,"attr":"钻石颜色:G-I"}],"goods_attr_id":"844,845"},{"rec_id":11,"store_id":1,"goods_id":"56\n","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品3","market_price":"120.00","shop_price":"90.00","merchant_price":null,"goods_number":1,"goods_attr":[],"goods_attr_id":"123"}]},{"merchant_name":"平台自营","store_id":0,"goods_info":[{"rec_id":8,"store_id":0,"goods_id":"1","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"薯片","market_price":"33.00","shop_price":"25.00","merchant_price":null,"goods_number":1,"goods_attr":[],"goods_attr_id":""},{"rec_id":9,"store_id":0,"goods_id":"2","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"饼干","market_price":"43.00","shop_price":"20.00","merchant_price":null,"goods_number":1,"goods_attr":[],"goods_attr_id":""},{"rec_id":10,"store_id":0,"goods_id":"3","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"饭团","market_price":"44.00","shop_price":"55.00","merchant_price":null,"goods_number":1,"goods_attr":[],"goods_attr_id":""}]}]
     * code : 200
     */

    private int code;
    private List<DataBean> data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * merchant_name : 我是掌柜
         * store_id : 1
         * goods_info : [{"rec_id":5,"store_id":1,"goods_id":"54","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品","market_price":"120.00","shop_price":"202.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}],"goods_attr_id":"864,866"},{"rec_id":6,"store_id":1,"goods_id":"55","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品2","market_price":"120.00","shop_price":"200.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":856,"attr":"材质:白18K金"},{"goods_attr_id":857,"attr":"钻石颜色:H"}],"goods_attr_id":"856,857\n"},{"rec_id":7,"store_id":1,"goods_id":"56","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品3","market_price":"120.00","shop_price":"90.00","merchant_price":null,"goods_number":1,"goods_attr":[{"goods_attr_id":844,"attr":"材质:红18K金"},{"goods_attr_id":845,"attr":"钻石颜色:G-I"}],"goods_attr_id":"844,845"},{"rec_id":11,"store_id":1,"goods_id":"56\n","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_name":"测试商品3","market_price":"120.00","shop_price":"90.00","merchant_price":null,"goods_number":1,"goods_attr":[],"goods_attr_id":"123"}]
         */

        private boolean selected;
        private boolean editor;
        private String merchant_name;
        private int store_id;
        private List<GoodsInfoBean> goods_info;

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        public boolean isEditor() {
            return editor;
        }

        public void setEditor(boolean editor) {
            this.editor = editor;
        }

        public String getMerchant_name() {
            return merchant_name;
        }

        public void setMerchant_name(String merchant_name) {
            this.merchant_name = merchant_name;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public List<GoodsInfoBean> getGoods_info() {
            return goods_info;
        }

        public void setGoods_info(List<GoodsInfoBean> goods_info) {
            this.goods_info = goods_info;
        }

        public static class GoodsInfoBean {
            /**
             * rec_id : 5
             * store_id : 1
             * goods_id : 54
             * goods_thumb : https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg
             * goods_name : 测试商品
             * market_price : 120.00
             * shop_price : 202.00
             * merchant_price : null
             * goods_number : 1
             * goods_attr : [{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}]
             * goods_attr_id : 864,866
             */

            private boolean subselected;
            private boolean subeditor;
            private int rec_id;
            private int store_id;
            private String goods_id;
            private String goods_thumb;
            private String goods_name;
            private String market_price;
            private String shop_price;
            private Object merchant_price;
            private int goods_number;
            private String goods_attr_id;
            private List<GoodsAttrBean> goods_attr;

            public boolean isSubselected() {
                return subselected;
            }

            public void setSubselected(boolean subselected) {
                this.subselected = subselected;
            }

            public boolean isSubeditor() {
                return subeditor;
            }

            public void setSubeditor(boolean subeditor) {
                this.subeditor = subeditor;
            }

            public int getRec_id() {
                return rec_id;
            }

            public void setRec_id(int rec_id) {
                this.rec_id = rec_id;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(String goods_id) {
                this.goods_id = goods_id;
            }

            public String getGoods_thumb() {
                return goods_thumb;
            }

            public void setGoods_thumb(String goods_thumb) {
                this.goods_thumb = goods_thumb;
            }

            public String getGoods_name() {
                return goods_name;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getShop_price() {
                return shop_price;
            }

            public void setShop_price(String shop_price) {
                this.shop_price = shop_price;
            }

            public Object getMerchant_price() {
                return merchant_price;
            }

            public void setMerchant_price(Object merchant_price) {
                this.merchant_price = merchant_price;
            }

            public int getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }

            public String getGoods_attr_id() {
                return goods_attr_id;
            }

            public void setGoods_attr_id(String goods_attr_id) {
                this.goods_attr_id = goods_attr_id;
            }

            public List<GoodsAttrBean> getGoods_attr() {
                return goods_attr;
            }

            public void setGoods_attr(List<GoodsAttrBean> goods_attr) {
                this.goods_attr = goods_attr;
            }

            public static class GoodsAttrBean {
                /**
                 * goods_attr_id : 864
                 * attr : 款式:女
                 */

                private int goods_attr_id;
                private String attr;

                public int getGoods_attr_id() {
                    return goods_attr_id;
                }

                public void setGoods_attr_id(int goods_attr_id) {
                    this.goods_attr_id = goods_attr_id;
                }

                public String getAttr() {
                    return attr;
                }

                public void setAttr(String attr) {
                    this.attr = attr;
                }
            }
        }
    }
}
