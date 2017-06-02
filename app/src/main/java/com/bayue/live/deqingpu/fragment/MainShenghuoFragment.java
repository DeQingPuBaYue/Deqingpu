package com.bayue.live.deqingpu.fragment;


import android.os.Bundle;

import com.bayue.live.deqingpu.Constants;
import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.utils.Tracer;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MainShenghuoFragment extends BaseFragment {
    String TAG = "MainShenghuoFragment";
    public static MainShenghuoFragment newInstance(String s){
        MainShenghuoFragment homeFragment = new MainShenghuoFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS,s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }
    @Override
    protected int getViewId() {
        return R.layout.main_fragm_shenghuo;
    }

    @Override
    public void init() {
        Tracer.d(TAG,"");
    }
}
