package com.bayue.live.deqingpu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bayue.live.deqingpu.base.LazyLoadFragment;

import java.util.List;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class ViewAdapter extends FragmentPagerAdapter {

    private List<LazyLoadFragment> fmList;
    private FragmentManager fm;

    public ViewAdapter(FragmentManager fm, List<LazyLoadFragment> fmList) {
        super(fm);
        this.fm = fm;
        this.fmList = fmList;
    }

    @Override
    public Fragment getItem(int arg0) {
        return fmList.get(arg0%fmList.size());
    }

    @Override
    public int getCount() {
        return fmList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        //没有找到child  要求重新加载
        return POSITION_NONE;
    }
}
