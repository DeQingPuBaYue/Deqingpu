package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/7/6 0006.
 * email : 2651742485@qq.com
 */

public class MenuBean {

    /**
     * code : 200
     * data : {"menu_1":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"menu_2":{"ad":[{"id":1,"img":"https://p.upyun.com/demo/webp/jpg/0.jpg","type":"cat","value":1}],"note":["全场商品免费","所有商品免费清仓处理","降价赔本大甩卖"]},"menu_3":[{"pic":"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg","type":"cat","value":1}],"menu_4":{"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/eLMWY3Mj.png"},"menu_5":{"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/Zfg85c3J.png"},"menu_6":{"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/D4OYNU5r.png"},"menu_7":{"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/0KGZExeV.png"}}
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
         * menu_1 : [{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}]
         * menu_2 : {"ad":[{"id":1,"img":"https://p.upyun.com/demo/webp/jpg/0.jpg","type":"cat","value":1}],"note":["全场商品免费","所有商品免费清仓处理","降价赔本大甩卖"]}
         * menu_3 : [{"pic":"http://img7.ph.126.net/4n-QGL5oaKZMrU8yNrsYJA==/3104668993135650397.jpg","type":"cat","value":1}]
         * menu_4 : {"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/eLMWY3Mj.png"}
         * menu_5 : {"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/Zfg85c3J.png"}
         * menu_6 : {"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/D4OYNU5r.png"}
         * menu_7 : {"content":[{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}],"img":"https://p.upyun.com/demo/tmp/0KGZExeV.png"}
         */

        private Menu2Bean menu_2;
        private Menu4Bean menu_4;
        private Menu5Bean menu_5;
        private Menu6Bean menu_6;
        private Menu7Bean menu_7;
        private List<Menu4Bean.ContentBean> menu_1;
        private List<Menu3Bean> menu_3;

        public Menu2Bean getMenu_2() {
            return menu_2;
        }

        public void setMenu_2(Menu2Bean menu_2) {
            this.menu_2 = menu_2;
        }

        public Menu4Bean getMenu_4() {
            return menu_4;
        }

        public void setMenu_4(Menu4Bean menu_4) {
            this.menu_4 = menu_4;
        }

        public Menu5Bean getMenu_5() {
            return menu_5;
        }

        public void setMenu_5(Menu5Bean menu_5) {
            this.menu_5 = menu_5;
        }

        public Menu6Bean getMenu_6() {
            return menu_6;
        }

        public void setMenu_6(Menu6Bean menu_6) {
            this.menu_6 = menu_6;
        }

        public Menu7Bean getMenu_7() {
            return menu_7;
        }

        public void setMenu_7(Menu7Bean menu_7) {
            this.menu_7 = menu_7;
        }

        public List<Menu4Bean.ContentBean> getMenu_1() {
            return menu_1;
        }

        public void setMenu_1(List<Menu4Bean.ContentBean> menu_1) {
            this.menu_1 = menu_1;
        }

        public List<Menu3Bean> getMenu_3() {
            return menu_3;
        }

        public void setMenu_3(List<Menu3Bean> menu_3) {
            this.menu_3 = menu_3;
        }

        public static class Menu2Bean {
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

        public static class Menu4Bean {
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

        public static class Menu5Bean {
            /**
             * content : [{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}]
             * img : https://p.upyun.com/demo/tmp/Zfg85c3J.png
             */

            private String img;
            private List<Menu4Bean.ContentBean> content;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public List<Menu4Bean.ContentBean> getContent() {
                return content;
            }

            public void setContent(List<Menu4Bean.ContentBean> content) {
                this.content = content;
            }
        }

        public static class Menu6Bean {
            /**
             * content : [{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}]
             * img : https://p.upyun.com/demo/tmp/D4OYNU5r.png
             */

            private String img;
            private List<Menu4Bean.ContentBean> content;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public List<Menu4Bean.ContentBean> getContent() {
                return content;
            }

            public void setContent(List<Menu4Bean.ContentBean> content) {
                this.content = content;
            }
        }

        public static class Menu7Bean {
            /**
             * content : [{"cat_id":1,"cat_name":"休闲美食","img":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg","parent_id":0}]
             * img : https://p.upyun.com/demo/tmp/0KGZExeV.png
             */

            private String img;
            private List<Menu4Bean.ContentBean> content;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public List<Menu4Bean.ContentBean> getContent() {
                return content;
            }

            public void setContent(List<Menu4Bean.ContentBean> content) {
                this.content = content;
            }
        }

        public static class Menu3Bean {
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
}
