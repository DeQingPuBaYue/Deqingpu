package com.bayue.live.deqingpu.entity.menu;

import java.util.List;

/**
 * Created by BaYue on 2017/7/7 0007.
 * email : 2651742485@qq.com
 */

public class MenuBannerBean {
    String json = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"pic\": \"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg\",\n" +
            "            \"type\": \"cat\",\n" +
            "            \"value\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"pic\": \"http://img0.ph.126.net/GPSkoQoa6lJze_6NhWchtg==/1150388229834327051.jpg\",\n" +
            "            \"type\": \"cat\",\n" +
            "            \"value\": 1\n" +
            "        },\n" +
            "        {\n" +
            "            \"pic\": \"http://www.wed114.cn/jiehun/uploads/allimg/160909/57_160909161244_2.jpg\",\n" +
            "            \"type\": \"cat\",\n" +
            "            \"value\": 1\n" +
            "        }\n" +
            "    ]\n" +
            "}";


    /**
     * code : 200
     * data : [{"pic":"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg","type":"cat","value":1},{"pic":"http://img0.ph.126.net/GPSkoQoa6lJze_6NhWchtg==/1150388229834327051.jpg","type":"cat","value":1},{"pic":"http://www.wed114.cn/jiehun/uploads/allimg/160909/57_160909161244_2.jpg","type":"cat","value":1}]
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
         * pic : http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg
         * type : cat
         * value : 1
         */

        private String pic;
        private String type;
        private int value;

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
