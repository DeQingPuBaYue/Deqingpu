package com.bayue.live.deqingpu.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 实际项目中你可以把这个类删除
 * Created by Tamic on 2016-06-03.
 */
public class ResultModel implements Serializable {

    /**
     * data : [{"address_id":5,"consignee":"老王","mobile":"15121511285","address":"来看你knows","province":"安徽","city":"安庆","district":"迎江区","default":false},{"address_id":6,"consignee":"老王","mobile":"15121511285","address":"来看你knows","province":"安徽","city":"安庆","district":"迎江区","default":false},{"address_id":7,"consignee":"老李","mobile":"15212122548","address":"你妹","province":"安徽","city":"安庆","district":"迎江区","default":false},{"address_id":8,"consignee":"老李","mobile":"15212122548","address":"你妹","province":"安徽","city":"安庆","district":"迎江区","default":false},{"address_id":9,"consignee":"老李","mobile":"15212122548","address":"你妹","province":"安徽","city":"安庆","district":"迎江区","default":false}]
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
         * address : 中国区银河系
         * address_id : 2
         * city : 36
         * city_name : 安庆
         * consignee : 老哥
         * default : 0
         * district : 398
         * district_name : 迎江区
         * mobile : 13521213656
         * province : 3
         * province_name : 安徽
         */

        private String address;
        private int address_id;
        private int city;
        private String city_name;
        private String consignee;
        @SerializedName("default")
        private int defaultX;
        private int district;
        private String district_name;
        private String mobile;
        private int province;
        private String province_name;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getAddress_id() {
            return address_id;
        }

        public void setAddress_id(int address_id) {
            this.address_id = address_id;
        }

        public int getCity() {
            return city;
        }

        public void setCity(int city) {
            this.city = city;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getConsignee() {
            return consignee;
        }

        public void setConsignee(String consignee) {
            this.consignee = consignee;
        }

        public int getDefaultX() {
            return defaultX;
        }

        public void setDefaultX(int defaultX) {
            this.defaultX = defaultX;
        }

        public int getDistrict() {
            return district;
        }

        public void setDistrict(int district) {
            this.district = district;
        }

        public String getDistrict_name() {
            return district_name;
        }

        public void setDistrict_name(String district_name) {
            this.district_name = district_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getProvince() {
            return province;
        }

        public void setProvince(int province) {
            this.province = province;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }
    }
}
