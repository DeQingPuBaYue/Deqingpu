package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class GoodsDetail {

    /**
     * data : {"goods_name":"测试商品","goods_id":54,"sales":0,"shop_price":"100.00","market_price":"120.00","merchant_price":"90.00","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","store_id":1,"goods_desc":"","type":"store","goods_gally":[{"img_id":2988,"goods_id":54,"img_url":"http://dqp.bayuenet.com/dist/img/avatar.png"}],"spe":[{"attr_type":2,"name":"款式","values":[{"label":"女","price":"2","format_price":2,"id":864}]}],"pro":[{"attr_name":"手寸","attr_value":"18K金钻石戒指"}],"tag":"联盟商家商品","store_name":"联盟商家店铺","notice":["店铺公告"],"pic":"http://dqp.bayuenet.com/dist/img/avatar.png","nik_name":"jskon","store_goods_num":5}
     * code : 200
     */

    private DataBean data;
    private int code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * goods_name : 测试商品
         * goods_id : 54
         * sales : 0
         * shop_price : 100.00
         * market_price : 120.00
         * merchant_price : 90.00
         * goods_thumb : https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg
         * store_id : 1
         * goods_desc :
         * type : store
         * goods_gally : [{"img_id":2988,"goods_id":54,"img_url":"http://dqp.bayuenet.com/dist/img/avatar.png"}]
         * spe : [{"attr_type":2,"name":"款式","values":[{"label":"女","price":"2","format_price":2,"id":864}]}]
         * pro : [{"attr_name":"手寸","attr_value":"18K金钻石戒指"}]
         * tag : 联盟商家商品
         * store_name : 联盟商家店铺
         * notice : ["店铺公告"]
         * pic : http://dqp.bayuenet.com/dist/img/avatar.png
         * nik_name : jskon
         * store_goods_num : 5
         */

        private String goods_name;
        private int goods_id;
        private int sales;
        private String shop_price;
        private String market_price;
        private String merchant_price;
        private String goods_thumb;
        private int store_id;
        private String goods_desc;
        private String type;
        private String tag;
        private String store_name;
        private String pic;
        private String nik_name;
        private int store_goods_num;
        private List<GoodsGallyBean> goods_gally;
        private List<SpeBean> spe;
        private List<ProBean> pro;
        private List<String> notice;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(int goods_id) {
            this.goods_id = goods_id;
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

        public String getMarket_price() {
            return market_price;
        }

        public void setMarket_price(String market_price) {
            this.market_price = market_price;
        }

        public String getMerchant_price() {
            return merchant_price;
        }

        public void setMerchant_price(String merchant_price) {
            this.merchant_price = merchant_price;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public int getStore_id() {
            return store_id;
        }

        public void setStore_id(int store_id) {
            this.store_id = store_id;
        }

        public String getGoods_desc() {
            return goods_desc;
        }

        public void setGoods_desc(String goods_desc) {
            this.goods_desc = goods_desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getNik_name() {
            return nik_name;
        }

        public void setNik_name(String nik_name) {
            this.nik_name = nik_name;
        }

        public int getStore_goods_num() {
            return store_goods_num;
        }

        public void setStore_goods_num(int store_goods_num) {
            this.store_goods_num = store_goods_num;
        }

        public List<GoodsGallyBean> getGoods_gally() {
            return goods_gally;
        }

        public void setGoods_gally(List<GoodsGallyBean> goods_gally) {
            this.goods_gally = goods_gally;
        }

        public List<SpeBean> getSpe() {
            return spe;
        }

        public void setSpe(List<SpeBean> spe) {
            this.spe = spe;
        }

        public List<ProBean> getPro() {
            return pro;
        }

        public void setPro(List<ProBean> pro) {
            this.pro = pro;
        }

        public List<String> getNotice() {
            return notice;
        }

        public void setNotice(List<String> notice) {
            this.notice = notice;
        }

        public static class GoodsGallyBean {
            /**
             * img_id : 2988
             * goods_id : 54
             * img_url : http://dqp.bayuenet.com/dist/img/avatar.png
             */

            private int img_id;
            private int goods_id;
            private String img_url;

            public int getImg_id() {
                return img_id;
            }

            public void setImg_id(int img_id) {
                this.img_id = img_id;
            }

            public int getGoods_id() {
                return goods_id;
            }

            public void setGoods_id(int goods_id) {
                this.goods_id = goods_id;
            }

            public String getImg_url() {
                return img_url;
            }

            public void setImg_url(String img_url) {
                this.img_url = img_url;
            }
        }

        public static class SpeBean {
            /**
             * attr_type : 2
             * name : 款式
             * values : [{"label":"女","price":"2","format_price":2,"id":864}]
             */

            private int attr_type;
            private String name;
            private List<ValuesBean> values;

            public int getAttr_type() {
                return attr_type;
            }

            public void setAttr_type(int attr_type) {
                this.attr_type = attr_type;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<ValuesBean> getValues() {
                return values;
            }

            public void setValues(List<ValuesBean> values) {
                this.values = values;
            }

            public static class ValuesBean {
                /**
                 * label : 女
                 * price : 2
                 * format_price : 2
                 * id : 864
                 */

                private String label;
                private String price;
                private int format_price;
                private int id;

                public String getLabel() {
                    return label;
                }

                public void setLabel(String label) {
                    this.label = label;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

                public int getFormat_price() {
                    return format_price;
                }

                public void setFormat_price(int format_price) {
                    this.format_price = format_price;
                }

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }
            }
        }

        public static class ProBean {
            /**
             * attr_name : 手寸
             * attr_value : 18K金钻石戒指
             */

            private String attr_name;
            private String attr_value;

            public String getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(String attr_name) {
                this.attr_name = attr_name;
            }

            public String getAttr_value() {
                return attr_value;
            }

            public void setAttr_value(String attr_value) {
                this.attr_value = attr_value;
            }
        }
    }
}
