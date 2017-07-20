package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/30 0030.
 * email : 2651742485@qq.com
 */

public class OrderListBean {


    /**
     * data : [{"store":0,"store_name":"德卿铺","order_id":1,"shipping_status":0,"order_status":1,"pay_status":0,"goods_amount":"1364.00","btn":{"cancel":1,"pay":1,"receipt":0,"refund":0,"delete":0,"comment":0,"logistics":0},"goods":[{"goods_id":54,"goods_name":"测试商品","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_number":2,"goods_price":"202.00","goods_attr_id":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}],"goods_tag":"联盟商家店铺"}]}]
     * code : 200
     * count_page : 0
     * page : 0
     */

    private int code;
    private int count_page;
    private int page;
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

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
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
         * store : 0
         * store_name : 德卿铺
         * order_id : 1
         * shipping_status : 0
         * order_status : 1
         * pay_status : 0
         * goods_amount : 1364.00
         * btn : {"cancel":1,"pay":1,"receipt":0,"refund":0,"delete":0,"comment":0,"logistics":0}
         * goods : [{"goods_id":54,"goods_name":"测试商品","goods_thumb":"https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg","goods_number":2,"goods_price":"202.00","goods_attr_id":[{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}],"goods_tag":"联盟商家店铺"}]
         */

        private int store;
        private String store_name;
        private int order_id;
        private int shipping_status;
        private int order_status;
        private int pay_status;
        private String goods_amount;
        private String order_sn;
        private BtnBean btn;
        private List<GoodsBean> goods;

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getStore() {
            return store;
        }

        public void setStore(int store) {
            this.store = store;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getOrder_id() {
            return order_id;
        }

        public void setOrder_id(int order_id) {
            this.order_id = order_id;
        }

        public int getShipping_status() {
            return shipping_status;
        }

        public void setShipping_status(int shipping_status) {
            this.shipping_status = shipping_status;
        }

        public int getOrder_status() {
            return order_status;
        }

        public void setOrder_status(int order_status) {
            this.order_status = order_status;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }

        public BtnBean getBtn() {
            return btn;
        }

        public void setBtn(BtnBean btn) {
            this.btn = btn;
        }

        public List<GoodsBean> getGoods() {
            return goods;
        }

        public void setGoods(List<GoodsBean> goods) {
            this.goods = goods;
        }

        public static class BtnBean {
            /**
             * cancel : 1
             * pay : 1
             * receipt : 0
             * refund : 0
             * delete : 0
             * comment : 0
             * logistics : 0
             */

            private int cancel;
            private int pay;
            private int receipt;
            private int refund;
            private int delete;
            private int comment;
            private int logistics;

            public int getCancel() {
                return cancel;
            }

            public void setCancel(int cancel) {
                this.cancel = cancel;
            }

            public int getPay() {
                return pay;
            }

            public void setPay(int pay) {
                this.pay = pay;
            }

            public int getReceipt() {
                return receipt;
            }

            public void setReceipt(int receipt) {
                this.receipt = receipt;
            }

            public int getRefund() {
                return refund;
            }

            public void setRefund(int refund) {
                this.refund = refund;
            }

            public int getDelete() {
                return delete;
            }

            public void setDelete(int delete) {
                this.delete = delete;
            }

            public int getComment() {
                return comment;
            }

            public void setComment(int comment) {
                this.comment = comment;
            }

            public int getLogistics() {
                return logistics;
            }

            public void setLogistics(int logistics) {
                this.logistics = logistics;
            }
        }

        public static class GoodsBean {
            /**
             * goods_id : 54
             * goods_name : 测试商品
             * goods_thumb : https://cityo2o.ecjia.com/content/uploads/images/201604/goods_img/792_G_1459827431738.jpg
             * goods_number : 2
             * goods_price : 202.00
             * goods_attr_id : [{"goods_attr_id":864,"attr":"款式:女"},{"goods_attr_id":866,"attr":"材质:白18k金"}]
             * goods_tag : 联盟商家店铺
             */

            private int goods_id;
            private String goods_name;
            private String goods_thumb;
            private int goods_number;
            private String goods_price;
            private String goods_tag;
            private List<GoodsAttrIdBean> goods_attr_id;

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

            public int getGoods_number() {
                return goods_number;
            }

            public void setGoods_number(int goods_number) {
                this.goods_number = goods_number;
            }

            public String getGoods_price() {
                return goods_price;
            }

            public void setGoods_price(String goods_price) {
                this.goods_price = goods_price;
            }

            public String getGoods_tag() {
                return goods_tag;
            }

            public void setGoods_tag(String goods_tag) {
                this.goods_tag = goods_tag;
            }

            public List<GoodsAttrIdBean> getGoods_attr_id() {
                return goods_attr_id;
            }

            public void setGoods_attr_id(List<GoodsAttrIdBean> goods_attr_id) {
                this.goods_attr_id = goods_attr_id;
            }

            public static class GoodsAttrIdBean {
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
