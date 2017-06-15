package com.bayue.live.deqingpu.entity;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class Return {

    /**
     * data : 添加成功
     * code : 200
     * msg : 参数错误
     */

    private String data;
    private int code;
    private String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
