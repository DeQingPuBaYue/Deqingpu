package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/23 0023.
 * email : 2651742485@qq.com
 */

public class RecordBean {

    /**
     * data : [{"nik_name":"老王","add_time":"2017-06-22 19:00:00","phone":"15142421313","goods_number":15}]
     * code : 200
     * goods_num : 0
     * count : 0
     */

    private int code;
    private int goods_num;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(int goods_num) {
        this.goods_num = goods_num;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nik_name : 老王
         * add_time : 2017-06-22 19:00:00
         * phone : 15142421313
         * goods_number : 15
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
