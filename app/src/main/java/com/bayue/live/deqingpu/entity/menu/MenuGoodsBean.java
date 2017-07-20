package com.bayue.live.deqingpu.entity.menu;

/**
 * Created by BaYue on 2017/7/7 0007.
 * email : 2651742485@qq.com
 */

public class MenuGoodsBean {

    String json = "{\n" +
            "    \"code\": 200,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"cat_id\": 1,\n" +
            "            \"cat_name\": \"休闲美食\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637446483&di=368aeba7518b064680fbd29fd90cc088&imgtype=0&src=http%3A%2F%2F2t.5068.com%2Fuploads%2Fallimg%2F151024%2F48-151024111511-50.jpg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 2,\n" +
            "            \"cat_name\": \"粮油副食\",\n" +
            "            \"img\": \"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1954322824,3290394817&fm=26&gp=0.jpg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 3,\n" +
            "            \"cat_name\": \"酒水饮料\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637660857&di=924351e91562c4acb8da4082605f17dd&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fca1349540923dd5499b95d0ad509b3de9d82488e.jpg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 4,\n" +
            "            \"cat_name\": \"工厂直销\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498637799975&di=11acea8d8157a60adb502e78e452866b&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F05e5e1554af04100000115a8236351.jpg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 5,\n" +
            "            \"cat_name\": \"美妆个护\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1498638049574&di=754a10c8bf9861621fc8a2a1f89f6137&imgtype=0&src=http%3A%2F%2Fimg8.zol.com.cn%2Fbbs%2Fupload%2F19747%2F19746066.gif\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 6,\n" +
            "            \"cat_name\": \"进口食品\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1498628062&di=6554ce719a7497c710edfdcf3a7677cf&src=http://cdn.duitang.com/uploads/item/201302/16/20130216002359_tUjrL.jpeg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 7,\n" +
            "            \"cat_name\": \"家庭清洁\",\n" +
            "            \"img\": \"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2618721067,3764643790&fm=21&gp=0.jpg\",\n" +
            "            \"parent_id\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"cat_id\": 8,\n" +
            "            \"cat_name\": \"官网咨询\",\n" +
            "            \"img\": \"https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1498628101&di=56678698589a19d6861dacbb3004205c&src=http://img4.duitang.com/uploads/item/201303/13/20130313102007_jAaiV.jpeg\",\n" +
            "            \"parent_id\": 0\n" +
            "        }\n" +
            "    ]\n" +
            "}";


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
