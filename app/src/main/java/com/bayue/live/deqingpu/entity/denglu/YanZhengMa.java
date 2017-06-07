package com.bayue.live.deqingpu.entity.denglu;

/**
 * Created by Administrator on 2017/6/6.
 */

public class YanZhengMa {


    /**
     * data : 发送成功
     * code : 200
     * sms_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0aW1lIjoxNDk2NzEzNjI4LCJjb2RlIjoxNzgyNTAsInBob25lIjoiMTU4MjcyMjE4MDgifQ.sNKtzzPzQ39iKRkJfGXO9LHfPIHXPiRa9j7mzbKIRPc
     * sms_code : 178250
     */

    private String data;
    private int code;
    private String sms_token;
    private int sms_code;

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

    public String getSms_token() {
        return sms_token;
    }

    public void setSms_token(String sms_token) {
        this.sms_token = sms_token;
    }

    public int getSms_code() {
        return sms_code;
    }

    public void setSms_code(int sms_code) {
        this.sms_code = sms_code;
    }
}
