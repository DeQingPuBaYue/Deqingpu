package com.bayue.live.deqingpu.entity.menu;

import java.util.List;

/**
 * Created by BaYue on 2017/7/7 0007.
 * email : 2651742485@qq.com
 */

public class MenuNoteBean {
    String json = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": {\n" +
            "        \"ad\": [\n" +
            "            {\n" +
            "                \"id\": 1,\n" +
            "                \"img\": \"https://p.upyun.com/demo/webp/jpg/0.jpg\",\n" +
            "                \"type\": \"cat\",\n" +
            "                \"value\": 1\n" +
            "            },\n" +
            "            {\n" +
            "                \"id\": 2,\n" +
            "                \"img\": \"https://img6.bdstatic.com/img/image/pcindex/weijulansehua.jpg\",\n" +
            "                \"type\": \"cat\",\n" +
            "                \"value\": 2\n" +
            "            }\n" +
            "        ],\n" +
            "        \"note\": [\n" +
            "            \"全场商品免费\",\n" +
            "            \"所有商品免费清仓处理\",\n" +
            "            \"降价赔本大甩卖\"\n" +
            "        ]\n" +
            "    }\n" +
            "}";


    /**
     * code : 200
     * data : {"ad":[{"id":1,"img":"https://p.upyun.com/demo/webp/jpg/0.jpg","type":"cat","value":1},{"id":2,"img":"https://img6.bdstatic.com/img/image/pcindex/weijulansehua.jpg","type":"cat","value":2}],"note":["全场商品免费","所有商品免费清仓处理","降价赔本大甩卖"]}
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
        private List<AdBean> ad;
        private List<String> note;

        public List<AdBean> getAd() {
            return ad;
        }

        public void setAd(List<AdBean> ad) {
            this.ad = ad;
        }

        public List<String> getNote() {
            return note;
        }

        public void setNote(List<String> note) {
            this.note = note;
        }

        public static class AdBean {
            /**
             * id : 1
             * img : https://p.upyun.com/demo/webp/jpg/0.jpg
             * type : cat
             * value : 1
             */

            private int id;
            private String img;
            private String type;
            private int value;

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
}
