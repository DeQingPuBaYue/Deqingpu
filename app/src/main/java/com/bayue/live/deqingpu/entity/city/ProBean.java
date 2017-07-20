package com.bayue.live.deqingpu.entity.city;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by BaYue on 2017/7/13 0013.
 * email : 2651742485@qq.com
 */

public class ProBean implements IPickerViewData {

    private int region_id;
    private String region_name;
    List<CityBean> cityBeans;

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

    public List<CityBean> getCityBeans() {
        return cityBeans;
    }

    public void setCityBeans(List<CityBean> cityBeans) {
        this.cityBeans = cityBeans;
    }

    @Override
    public String getPickerViewText() {
        return region_name;
    }
}
