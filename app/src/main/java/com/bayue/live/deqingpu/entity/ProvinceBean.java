package com.bayue.live.deqingpu.entity;

/**
 * Created by Administrator on 2017/6/7.
 */

public class ProvinceBean {

    /**
     * parent_id : 1
     * region_id : 2
     * region_name : 北京
     * region_type : 1
     */

    private int parent_id;
    private int region_id;
    private String region_name;
    private int region_type;

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getRegion_id() {
        return region_id;
    }

    public void setRegion_id(int region_id) {
        this.region_id = region_id;
    }

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    public int getRegion_type() {
        return region_type;
    }

    public void setRegion_type(int region_type) {
        this.region_type = region_type;
    }
}
