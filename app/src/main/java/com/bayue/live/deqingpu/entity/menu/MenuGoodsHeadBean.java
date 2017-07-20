package com.bayue.live.deqingpu.entity.menu;

import java.util.List;

/**
 * Created by BaYue on 2017/7/7 0007.
 * email : 2651742485@qq.com
 */

public class MenuGoodsHeadBean {
    String json = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": {\n" +
            "        \"content\": [\n" +
            "            {\n" +
            "                \"cat_id\": 1,\n" +
            "                \"cat_name\": \"休闲美食\",\n" +
            "                \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg\",\n" +
            "                \"parent_id\": 0\n" +
            "            }\n" +
            "        ],\n" +
            "        \"img\": \"https://p.upyun.com/demo/tmp/eLMWY3Mj.png\"\n" +
            "    }\n" +
            "}";

    /**
     * code : 200
     * data : {"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/eLMWY3Mj.png"}
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
         * content : [{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}]
         * img : https://p.upyun.com/demo/tmp/eLMWY3Mj.png
         */

        private String img;
        private List<ContentBean> content;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * cat_id : 1
             * cat_name : 休闲美食
             * img : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg
             * parent_id : 0
             */

            private int cat_id;
            private String cat_name;
            private String img;
            private int parent_id;

            public int getCat_id() {
                return cat_id;
            }

            public void setCat_id(int cat_id) {
                this.cat_id = cat_id;
            }

            public String getCat_name() {
                return cat_name;
            }

            public void setCat_name(String cat_name) {
                this.cat_name = cat_name;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public int getParent_id() {
                return parent_id;
            }

            public void setParent_id(int parent_id) {
                this.parent_id = parent_id;
            }
        }
    }
}
