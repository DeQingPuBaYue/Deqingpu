package com.bayue.live.deqingpu.entity.geren;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class RizhiBean {


    /**
     * data : [{"id":2,"add_time":"2017-06-11 15:29","desc":"我的圈子","pic":""},{"id":3,"add_time":"2017-06-11 15:32","desc":"你好！欢迎光临","pic":""},{"id":5,"add_time":"2017-06-11 15:56","desc":"你好！欢迎光临2222","pic":""},{"id":6,"add_time":"2017-06-11 15:56","desc":"你好！欢迎光临2222","pic":""},{"id":7,"add_time":"2017-06-11 15:56","desc":"我的圈子","pic":"http://dqp.bayuenet.com/upload/active/society/1497196594.jpeg"},{"id":13,"add_time":"2017-06-11 17:29","desc":"你好！欢迎光临2222","pic":""},{"id":14,"add_time":"2017-06-11 18:02","desc":"12345764","pic":""}]
     * code : 200
     */
    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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
         * id : 2
         * add_time : 2017-06-11 15:29
         * desc : 我的圈子
         * pic :
         */

        private int id;
        private String add_time;
        private String desc;
        private String pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }
}
