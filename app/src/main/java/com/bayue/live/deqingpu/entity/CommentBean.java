package com.bayue.live.deqingpu.entity;

import java.util.List;

/**
 * Created by BaYue on 2017/6/15.
 * email : 2651742485@qq.com
 */

public class CommentBean {


    /**
     * data : [{"add_time":"2017-06-07 14:21:01","comment_img":["https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png"],"comment_id":1,"comment_rank":5,"content":"挺不错的，好评","id_value":1,"nik_name":"jskon","pic":"http://dqp.bayuenet.com/dist/img/avatar.png","reply_comment":[{"add_time":"2017-06-12 03:35:30","content":"商家回复内容"}]}]
     * code : 200
     * count : 1
     * favorable : 200
     * count_page : 1
     * page : 1
     */

    private int code;
    private int count;
    private int favorable;
    private int count_page;
    private String page;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getFavorable() {
        return favorable;
    }

    public void setFavorable(int favorable) {
        this.favorable = favorable;
    }

    public int getCount_page() {
        return count_page;
    }

    public void setCount_page(int count_page) {
        this.count_page = count_page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * add_time : 2017-06-07 14:21:01
         * comment_img : ["https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png","https://www.baidu.com/img/bd_logo1.png"]
         * comment_id : 1
         * comment_rank : 5
         * content : 挺不错的，好评
         * id_value : 1
         * nik_name : jskon
         * pic : http://dqp.bayuenet.com/dist/img/avatar.png
         * reply_comment : [{"add_time":"2017-06-12 03:35:30","content":"商家回复内容"}]
         */

        private String add_time;
        private int comment_id;
        private int comment_rank;
        private String content;
        private int id_value;
        private String nik_name;
        private String pic;
        private List<String> comment_img;
        private List<ReplyCommentBean> reply_comment;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public int getComment_rank() {
            return comment_rank;
        }

        public void setComment_rank(int comment_rank) {
            this.comment_rank = comment_rank;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getId_value() {
            return id_value;
        }

        public void setId_value(int id_value) {
            this.id_value = id_value;
        }

        public String getNik_name() {
            return nik_name;
        }

        public void setNik_name(String nik_name) {
            this.nik_name = nik_name;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public List<String> getComment_img() {
            return comment_img;
        }

        public void setComment_img(List<String> comment_img) {
            this.comment_img = comment_img;
        }

        public List<ReplyCommentBean> getReply_comment() {
            return reply_comment;
        }

        public void setReply_comment(List<ReplyCommentBean> reply_comment) {
            this.reply_comment = reply_comment;
        }

        public static class ReplyCommentBean {
            /**
             * add_time : 2017-06-12 03:35:30
             * content : 商家回复内容
             */

            private String add_time;
            private String content;

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
