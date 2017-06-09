package com.bayue.live.deqingpu.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bayue.live.deqingpu.base.BaseFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/9.
 */

public class QuanzhiAdapter extends FragmentPagerAdapter {


    ArrayList<BaseFragment> fragments;

    public QuanzhiAdapter(FragmentManager fm, ArrayList<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
