package com.bayue.live.deqingpu.entity;

/**
 * Created by BaYue on 2017/6/23 0023.
 * email : 2651742485@qq.com
 */

public class RecordBean {


    /**
     * data : {"nik_name":"","add_time":"","phone":"","goods_number":0}
     * code : 200
     * goods_number : 0
     * count : 0
     */

    private DataBean data;
    private int code;
    private int goods_number;
    private int count;

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

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class DataBean {
        /**
         * nik_name :
         * add_time :
         * phone :
         * goods_number : 0
         */

        private String nik_name;
        private String add_time;
        private String phone;
        private int goods_number;

        public String getNik_name() {
            return nik_name;
        }

        public void setNik_name(String nik_name) {
            this.nik_name = nik_name;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }
    }
}
