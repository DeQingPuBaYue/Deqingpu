package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/14.
 * email : 2651742485@qq.com
 */

public class StoreDetail {


    /**
     * code : 200
     * data : {"name":"李先生","note":"店铺公告哦！！","include_number":0,"phone":"13524565152","sales":550,"store_address":"上海市嘉定区天祝路818号","store_avatar":"https://www.juhe.cn/themes/images/data/new/a15.png","store_banner":"","store_name":"联盟商家店铺2"}
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
         * name : 李先生
         * note : 店铺公告哦！！
         * include_number : 0
         * phone : 13524565152
         * sales : 550
         * store_address : 上海市嘉定区天祝路818号
         * store_avatar : https://www.juhe.cn/themes/images/data/new/a15.png
         * store_banner :
         * store_name : 联盟商家店铺2
         */

        private String name;
        private List<String> note;
        private int number;
        private String phone;
        private int sales;
        private String store_address;
        private String store_avatar;
        private String store_banner;
        private String store_name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getNote() {
            return note;
        }

        public void setNote(List<String> notice) {
            this.note = note;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public String getStore_address() {
            return store_address;
        }

        public void setStore_address(String store_address) {
            this.store_address = store_address;
        }

        public String getStore_avatar() {
            return store_avatar;
        }

        public void setStore_avatar(String store_avatar) {
            this.store_avatar = store_avatar;
        }

        public String getStore_banner() {
            return store_banner;
        }

        public void setStore_banner(String store_banner) {
            this.store_banner = store_banner;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }
    }
}
