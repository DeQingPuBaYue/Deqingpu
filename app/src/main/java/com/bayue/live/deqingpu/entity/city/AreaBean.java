package com.bayue.live.deqingpu.entity.city;


import com.bigkoo.pickerview.model.IPickerViewData;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/7.
 */

public class AreaBean implements Serializable, IPickerViewData {

    /**
     * region_id : 36
     * region_name : 安庆
     * parent_id : 3
     */

    private int region_id;
    private String region_name;
    private int parent_id;

    @Override
    public String getPickerViewText() {
        return this.region_name;
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

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
