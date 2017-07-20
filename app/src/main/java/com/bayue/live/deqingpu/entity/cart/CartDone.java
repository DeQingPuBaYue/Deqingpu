package com.bayue.live.deqingpu.entity.cart;

import java.io.Serializable;

/**
 * Created by BaYue on 2017/7/12 0012.
 * email : 2651742485@qq.com
 */

public class CartDone implements Serializable {


    /**
     * code : 200
     * data : {"order_amount":25,"order_id":146,"order_sn":"2017071143961"}
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

    public static class DataBean implements Serializable{
        /**
         * order_amount : 25
         * order_id : 146
         * order_sn : 2017071143961
         */

        private int order_amount;
        private int order_id;
        private String order_sn;

        public int getOrder_amount() {
            return order_amount;
        }

        public void setOrder_amount(int order_amount) {
            this.order_amount = order_amount;
        }

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
    }
}
