package com.bayue.live.deqingpu.entity.denglu;

/**
 * Created by Administrator on 2017/6/6.
 */

public class DengLuBean {

    /**
     * data : 登录成功
     * code : 200
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyNSwibmlrX25hbWUiOm51bGx9.hMdh-u7smXKodYB6YSjHOfM-ypmSJtjp2FZ_pfDj6u8
     * userinfo : {"nik_name":"","user_num":"270494","money":0,"pic":"","sign":"","birthday":""}
     */
    private String msg;
    private String data;
    private int code;
    private String token;
    private UserinfoBean userinfo;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public static class UserinfoBean {
        /**
         * nik_name :
         * user_num : 270494
         * money : 0
         * pic :
         * sign :
         * birthday :
         */

        private String nik_name;
        private String user_num;
        private int money;
        private String pic;
        private String sign;
        private String birthday;

        public String getNik_name() {
            return nik_name;
        }

        public void setNik_name(String nik_name) {
            this.nik_name = nik_name;
        }

        public String getUser_num() {
            return user_num;
        }

        public void setUser_num(String user_num) {
            this.user_num = user_num;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }
}
