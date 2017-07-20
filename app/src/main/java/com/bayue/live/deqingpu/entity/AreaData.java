package com.bayue.live.deqingpu.entity;

import com.bayue.live.deqingpu.entity.city.AreaBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BaYue on 2017/7/14 0014.
 * email : 2651742485@qq.com
 */

public class AreaData implements Serializable {

    String provinceStr;
    String cityStr;
    String areaStr;
    List<AreaBean> beansItem1 = new ArrayList<>();
    List<List<AreaBean>> beansItem2 = new ArrayList<>();
    List<List<List<AreaBean>>> beansItem3 = new ArrayList<>();

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }

    public String getCityStr() {
        return cityStr;
    }

    public void setCityStr(String cityStr) {
        this.cityStr = cityStr;
    }

    public String getAreaStr() {
        return areaStr;
    }

    public void setAreaStr(String areaStr) {
        this.areaStr = areaStr;
    }

    public List<AreaBean> getBeansItem1() {
        return beansItem1;
    }

    public void setBeansItem1(List<AreaBean> beansItem1) {
        this.beansItem1 = beansItem1;
    }

    public List<List<AreaBean>> getBeansItem2() {
        return beansItem2;
    }

    public void setBeansItem2(List<List<AreaBean>> beansItem2) {
        this.beansItem2 = beansItem2;
    }

    public List<List<List<AreaBean>>> getBeansItem3() {
        return beansItem3;
    }

    public void setBeansItem3(List<List<List<AreaBean>>> beansItem3) {
        this.beansItem3 = beansItem3;
    }
}
