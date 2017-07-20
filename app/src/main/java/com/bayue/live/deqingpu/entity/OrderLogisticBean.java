package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/7/3 0003.
 * email : 2651742485@qq.com
 */

public class OrderLogisticBean {
    public static String LogisticData = "{\"message\":\"ok\",\"nu\":\"3971040624100\",\"ischeck\":\"1\",\"condition\":\"F00\",\"com\":\"yunda\",\"status\":\"200\",\"state\":\"3\",\"data\":[{\"time\":\"2017-06-26 17:58:25\",\"ftime\":\"2017-06-26 17:58:25\",\"context\":\"[广东广州花都区新华公司花东分部]快件已被 已签收 签收\",\"location\":\"广东广州花都区新华公司花东分部\"},{\"time\":\"2017-06-26 15:49:29\",\"ftime\":\"2017-06-26 15:49:29\",\"context\":\"[广东广州花都区新华公司花东分部]进行派件扫描；派送业务员：黄钊伟；联系电话：18620416797\",\"location\":\"广东广州花都区新华公司花东分部\"},{\"time\":\"2017-06-26 09:12:46\",\"ftime\":\"2017-06-26 09:12:46\",\"context\":\"[广东广州花都区新华公司花东分部]到达目的地网点，快件将很快进行派送\",\"location\":\"广东广州花都区新华公司花东分部\"},{\"time\":\"2017-06-26 04:29:25\",\"ftime\":\"2017-06-26 04:29:25\",\"context\":\"[广东广州花都区新华公司]进行快件扫描，将发往：广东广州花都区新华公司花东分部\",\"location\":\"广东广州花都区新华公司\"},{\"time\":\"2017-06-26 02:24:59\",\"ftime\":\"2017-06-26 02:24:59\",\"context\":\"[广东广州花都区新华公司]到达目的地网点，快件将很快进行派送\",\"location\":\"广东广州花都区新华公司\"},{\"time\":\"2017-06-26 01:52:09\",\"ftime\":\"2017-06-26 01:52:09\",\"context\":\"[广东广州分拨中心]从站点发出，本次转运目的地：广东广州花都区新华公司\",\"location\":\"广东广州分拨中心\"},{\"time\":\"2017-06-26 01:41:27\",\"ftime\":\"2017-06-26 01:41:27\",\"context\":\"[广东广州分拨中心]在分拨中心进行卸车扫描\",\"location\":\"广东广州分拨中心\"},{\"time\":\"2017-06-25 23:44:39\",\"ftime\":\"2017-06-25 23:44:39\",\"context\":\"[广东广州分拨中心黄埔分拨点]进行装车扫描，即将发往：广东广州分拨中心\",\"location\":\"广东广州分拨中心黄埔分拨点\"},{\"time\":\"2017-06-25 23:44:07\",\"ftime\":\"2017-06-25 23:44:07\",\"context\":\"[广东广州分拨中心黄埔分拨点]在分拨中心进行称重扫描\",\"location\":\"广东广州分拨中心黄埔分拨点\"},{\"time\":\"2017-06-25 21:54:41\",\"ftime\":\"2017-06-25 21:54:41\",\"context\":\"[广东广州黄埔区丰乐路公司]进行揽件扫描\",\"location\":\"广东广州黄埔区丰乐路公司\"},{\"time\":\"2017-06-25 20:16:55\",\"ftime\":\"2017-06-25 20:16:55\",\"context\":\"[广东广州黄埔区丰乐路公司]进行下级地点扫描，将发往：广东广州网点包\",\"location\":\"广东广州黄埔区丰乐路公司\"},{\"time\":\"2017-06-25 14:20:57\",\"ftime\":\"2017-06-25 14:20:57\",\"context\":\"[广东广州黄埔区丰乐路公司]进行揽件扫描\",\"location\":\"广东广州黄埔区丰乐路公司\"}]}";

    /**
     * message : ok
     * nu : 3971040624100
     * ischeck : 1
     * condition : F00
     * com : yunda
     * status : 200
     * state : 3
     * data : [{"time":"2017-06-26 17:58:25","ftime":"2017-06-26 17:58:25","context":"[广东广州花都区新华公司花东分部]快件已被 已签收 签收","location":"广东广州花都区新华公司花东分部"},{"time":"2017-06-26 15:49:29","ftime":"2017-06-26 15:49:29","context":"[广东广州花都区新华公司花东分部]进行派件扫描；派送业务员：黄钊伟；联系电话：18620416797","location":"广东广州花都区新华公司花东分部"},{"time":"2017-06-26 09:12:46","ftime":"2017-06-26 09:12:46","context":"[广东广州花都区新华公司花东分部]到达目的地网点，快件将很快进行派送","location":"广东广州花都区新华公司花东分部"},{"time":"2017-06-26 04:29:25","ftime":"2017-06-26 04:29:25","context":"[广东广州花都区新华公司]进行快件扫描，将发往：广东广州花都区新华公司花东分部","location":"广东广州花都区新华公司"},{"time":"2017-06-26 02:24:59","ftime":"2017-06-26 02:24:59","context":"[广东广州花都区新华公司]到达目的地网点，快件将很快进行派送","location":"广东广州花都区新华公司"},{"time":"2017-06-26 01:52:09","ftime":"2017-06-26 01:52:09","context":"[广东广州分拨中心]从站点发出，本次转运目的地：广东广州花都区新华公司","location":"广东广州分拨中心"},{"time":"2017-06-26 01:41:27","ftime":"2017-06-26 01:41:27","context":"[广东广州分拨中心]在分拨中心进行卸车扫描","location":"广东广州分拨中心"},{"time":"2017-06-25 23:44:39","ftime":"2017-06-25 23:44:39","context":"[广东广州分拨中心黄埔分拨点]进行装车扫描，即将发往：广东广州分拨中心","location":"广东广州分拨中心黄埔分拨点"},{"time":"2017-06-25 23:44:07","ftime":"2017-06-25 23:44:07","context":"[广东广州分拨中心黄埔分拨点]在分拨中心进行称重扫描","location":"广东广州分拨中心黄埔分拨点"},{"time":"2017-06-25 21:54:41","ftime":"2017-06-25 21:54:41","context":"[广东广州黄埔区丰乐路公司]进行揽件扫描","location":"广东广州黄埔区丰乐路公司"},{"time":"2017-06-25 20:16:55","ftime":"2017-06-25 20:16:55","context":"[广东广州黄埔区丰乐路公司]进行下级地点扫描，将发往：广东广州网点包","location":"广东广州黄埔区丰乐路公司"},{"time":"2017-06-25 14:20:57","ftime":"2017-06-25 14:20:57","context":"[广东广州黄埔区丰乐路公司]进行揽件扫描","location":"广东广州黄埔区丰乐路公司"}]
     */

    private String message;
    private String nu;
    private String ischeck;
    private String condition;
    private String com;
    private String status;
    private String state;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2017-06-26 17:58:25
         * ftime : 2017-06-26 17:58:25
         * context : [广东广州花都区新华公司花东分部]快件已被 已签收 签收
         * location : 广东广州花都区新华公司花东分部
         */

        private String time;
        private String ftime;
        private String context;
        private String location;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getFtime() {
            return ftime;
        }

        public void setFtime(String ftime) {
            this.ftime = ftime;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
