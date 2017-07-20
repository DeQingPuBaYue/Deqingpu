package com.bayue.live.deqingpu.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by BaYue on 2017/7/14 0014.
 * email : 2651742485@qq.com
 */

public class UserInfo implements Serializable{

    /**
     * code : 200
     * data : {"attention":0,"camp":0,"fans":0,"identity":{"company_state":1,"direct":0,"ident_state":1,"name_state":1,"service":0,"status_user":1,"store_state":1},"merchant":"","user_info":{"birthday":"","nik_name":"吴浩宇","num":"35986","phone":"15121019915","photo_wall":"http://photos.tuchong.com/346722/m/6331171.jpg","pic":"http://dqp.bayuenet.com/dist/img/avatar.png","sign":""},"user_photo":[]}
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
         * attention : 0
         * camp : 0
         * fans : 0
         * identity : {"company_state":1,"direct":0,"ident_state":1,"name_state":1,"service":0,"status_user":1,"store_state":1}
         * merchant :
         * user_info : {"birthday":"","nik_name":"吴浩宇","num":"35986","phone":"15121019915","photo_wall":"http://photos.tuchong.com/346722/m/6331171.jpg","pic":"http://dqp.bayuenet.com/dist/img/avatar.png","sign":""}
         * user_photo : []
         */

        private int attention;
        private int camp;
        private int fans;
        private IdentityBean identity;
        private String merchant;
        private UserInfoBean user_info;
        private List<UserPhoto> user_photo;
        private Reputation reputation;

        public Reputation getReputation() {
            return reputation;
        }

        public void setReputation(Reputation reputation) {
            this.reputation = reputation;
        }

        public int getAttention() {
            return attention;
        }

        public void setAttention(int attention) {
            this.attention = attention;
        }

        public int getCamp() {
            return camp;
        }

        public void setCamp(int camp) {
            this.camp = camp;
        }

        public int getFans() {
            return fans;
        }

        public void setFans(int fans) {
            this.fans = fans;
        }

        public IdentityBean getIdentity() {
            return identity;
        }

        public void setIdentity(IdentityBean identity) {
            this.identity = identity;
        }

        public String getMerchant() {
            return merchant;
        }

        public void setMerchant(String merchant) {
            this.merchant = merchant;
        }

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public List<UserPhoto> getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(List<UserPhoto> user_photo) {
            this.user_photo = user_photo;
        }

        public static class IdentityBean implements Serializable{
            /**
             * company_state : 1
             * direct : 0
             * ident_state : 1
             * name_state : 1
             * service : 0
             * status_user : 1
             * store_state : 1
             */

            private int company_state;
            private int direct;
            private int ident_state;
            private int name_state;
            private int service;
            private int status_user;
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

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }

            public int getStatus_user() {
                return status_user;
            }

            public void setStatus_user(int status_user) {
                this.status_user = status_user;
            }

            public int getStore_state() {
                return store_state;
            }

            public void setStore_state(int store_state) {
                this.store_state = store_state;
            }
        }
        public static class UserPhoto implements Serializable{
            /**
             "id": 1,
             "img": "http://e.hiphotos.baidu.com/image/h%3D300/sign=27321240f81fbe09035ec5145b610c30/00e93901213fb80e27154ce13cd12f2eb938943b.jpg",
             "user_id": 26
             */
            private int id;
            private String img;
            private boolean isDel = false;

            public boolean isDel() {
                return isDel;
            }

            public void setDel(boolean del) {
                isDel = del;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
        public static class Reputation implements Serializable{
            /**
               company_state: 1,
               name_state: 1,
               service: 0
             */
            private int company_state;
            private int name_state;
            private int service;

            public int getCompany_state() {
                return company_state;
            }

            public void setCompany_state(int company_state) {
                this.company_state = company_state;
            }

            public int getName_state() {
                return name_state;
            }

            public void setName_state(int name_state) {
                this.name_state = name_state;
            }

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }
        }

        public static class UserInfoBean implements Serializable{
            /**
             * birthday :
             * nik_name : 吴浩宇
             * num : 35986
             * phone : 15121019915
             * photo_wall : http://photos.tuchong.com/346722/m/6331171.jpg
             * pic : http://dqp.bayuenet.com/dist/img/avatar.png
             * sign :
             */

            private String birthday;
            private String nik_name;
            private String num;
            private String phone;
            private String photo_wall;
            private String pic;
            private String sign;

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getNik_name() {
                return nik_name;
            }

            public void setNik_name(String nik_name) {
                this.nik_name = nik_name;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhoto_wall() {
                return photo_wall;
            }

            public void setPhoto_wall(String photo_wall) {
                this.photo_wall = photo_wall;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }
    }
}
