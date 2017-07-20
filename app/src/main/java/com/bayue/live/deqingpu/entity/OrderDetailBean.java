package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/7/3 0003.
 * email : 2651742485@qq.com
 */

public class OrderDetailBean {


    /**
     * data : {"order_id":1,"order_sn":"2017062797473","store_id":"","order_status":1,"shipping_status":"","pay_status":"","consignee":"卡通","province":"安徽","city":"","district":"迎江区","address":"5和1365","mobile":"15121019945","pay_name":"","goods_amount":"1364.00","add_time":"2017-06-27 18:54:13","postscript":"","shipping_name":"","invoice_no":"","pay_time":"","shipping_time":"","favourable":[],"order_goods":[{"goods_id":54,"goods_name":"测试商品","market_price":"120.00","goods_price":"202.00","store_id":1,"goods_attr_id":"864,866","goods_number":2,"type":"store","attr_name":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}]}],"store_name":"德卿铺"}
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
         * order_id : 1
         * order_sn : 2017062797473
         * store_id :
         * order_status : 1
         * shipping_status :
         * pay_status :
         * consignee : 卡通
         * province : 安徽
         * city :
         * district : 迎江区
         * address : 5和1365
         * mobile : 15121019945
         * pay_name :
         * goods_amount : 1364.00
         * add_time : 2017-06-27 18:54:13
         * postscript :
         * shipping_name :
         * invoice_no :
         * pay_time :
         * shipping_time :
         * favourable : []
         * order_goods : [{"goods_id":54,"goods_name":"测试商品","market_price":"120.00","goods_price":"202.00","store_id":1,"goods_attr_id":"864,866","goods_number":2,"type":"store","attr_name":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}]}]
         * store_name : 德卿铺
         */

        private int order_id;
        private String order_sn;
        private String store_id;
        private int order_status;
        private String shipping_status;
        private String pay_status;
        private String consignee;
        private String province;
        private String city;
        private String district;
        private String address;
        private String mobile;
        private String pay_name;
        private String goods_amount;
        private String add_time;
        private String postscript;
        private String shipping_name;
        private String invoice_no;
        private String pay_time;
        private String shipping_time;
        private String store_name;
        private List<?> favourable;
        private List<OrderGoodsBean> order_goods;

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getStore_id() {
            return store_id;
        }

        public void setStore_id(String store_id) {
            this.store_id = store_id;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public String getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(String shipping_status) {
            this.shipping_status = shipping_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPostscript() {
            return postscript;
        }

        public void setPostscript(String postscript) {
            this.postscript = postscript;
        }

        public String getShipping_name() {
            return shipping_name;
        }

        public void setShipping_name(String shipping_name) {
            this.shipping_name = shipping_name;
        }

        public String getInvoice_no() {
            return invoice_no;
        }

        public void setInvoice_no(String invoice_no) {
            this.invoice_no = invoice_no;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getShipping_time() {
            return shipping_time;
        }

        public void setShipping_time(String shipping_time) {
            this.shipping_time = shipping_time;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public List<?> getFavourable() {
            return favourable;
        }

        public void setFavourable(List<?> favourable) {
            this.favourable = favourable;
        }

        public List<OrderGoodsBean> getOrder_goods() {
            return order_goods;
        }

        public void setOrder_goods(List<OrderGoodsBean> order_goods) {
            this.order_goods = order_goods;
        }

        public static class OrderGoodsBean {
            /**
             * goods_id : 54
             * goods_name : 测试商品
             * market_price : 120.00
             * goods_price : 202.00
             * store_id : 1
             * goods_attr_id : 864,866
             * goods_number : 2
             * type : store
             * attr_name : [{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}]
             */

            private int goods_id;
            private String goods_name;
            private String market_price;
            private String goods_price;
            private int store_id;
            private String goods_attr_id;
            private int goods_number;
            private String type;
            private List<AttrNameBean> attr_name;

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

            public String getMarket_price() {
                return market_price;
            }

            public void setMarket_price(String market_price) {
                this.market_price = market_price;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public int getStore_id() {
                return store_id;
            }

            public void setStore_id(int store_id) {
                this.store_id = store_id;
            }

            public String getGoods_attr_id() {
                return goods_attr_id;
            }

            public void setGoods_attr_id(String goods_attr_id) {
                this.goods_attr_id = goods_attr_id;
            }

            public int getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<AttrNameBean> getAttr_name() {
                return attr_name;
            }

            public void setAttr_name(List<AttrNameBean> attr_name) {
                this.attr_name = attr_name;
            }

            public static class AttrNameBean {
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
