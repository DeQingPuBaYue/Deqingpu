package com.bayue.live.deqingpu.entity;

import java.io.Serializable;

/**
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class AountInfo implements Serializable {

    /**
     * code : 200
     * data : {"address_id":"","alipay":"","birthday":"","money":"","nik_name":"","phone":"15121019915","pic":"http://dqp.bayuenet.com/dist/img/avatar.png","sex":1,"sign":"","user_preaudit":{"company_state":1,"direct":0,"ident_state":1,"name_state":1,"store_state":1},"wechat":""}
     */
//    private static final long serialVersionUID = 2421263553592651152L;
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
         * address_id :
         * alipay :
         * birthday :
         * money :
         * nik_name :
         * phone : 15121019915
         * pic : http://dqp.bayuenet.com/dist/img/avatar.png
         * sex : 1
         * sign :
         * user_preaudit : {"company_state":1,"direct":0,"ident_state":1,"name_state":1,"store_state":1}
         * wechat :
         */

        private String address_id;
        private String alipay;
        private String birthday;
        private String money;
        private String nik_name;
        private String phone;
        private String pic;
        private int sex;
        private String sign;
        private UserPreauditBean user_preaudit;
        private String wechat;

        public String getAddress_id() {
            return address_id;
        }

        public void setAddress_id(String address_id) {
            this.address_id = address_id;
        }

        public String getAlipay() {
            return alipay;
        }

        public void setAlipay(String alipay) {
            this.alipay = alipay;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getNik_name() {
            return nik_name;
        }

        public void setNik_name(String nik_name) {
            this.nik_name = nik_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public UserPreauditBean getUser_preaudit() {
            return user_preaudit;
        }

        public void setUser_preaudit(UserPreauditBean user_preaudit) {
            this.user_preaudit = user_preaudit;
        }

        public String getWechat() {
            return wechat;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public static class UserPreauditBean implements Serializable{
            /**
             * company_state : 1
             * direct : 0
             * ident_state : 1
             * name_state : 1
             * store_state : 1
             */

            private int company_state;
            private int direct;
            private int ident_state;
            private int name_state;
            private int store_state;

            public int getCompany_state() {
                return company_state;
            }

            public void setCompany_state(int company_state) {
                this.company_state = company_state;
            }

            public int getDirect() {
                return direct;
            }

            public void setDirect(int direct) {
                this.direct = direct;
            }

            public int getIdent_state() {
                return ident_state;
            }

            public void setIdent_state(int ident_state) {
                this.ident_state = ident_state;
            }

            public int getName_state() {
                return name_state;
            }

            public void setName_state(int name_state) {
                this.name_state = name_state;
            }

            public int getStore_state() {
                return store_state;
            }

            public void setStore_state(int store_state) {
                this.store_state = store_state;
            }
        }
    }
}
