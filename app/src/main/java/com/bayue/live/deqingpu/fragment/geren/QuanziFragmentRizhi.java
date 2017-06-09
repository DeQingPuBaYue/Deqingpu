package com.bayue.live.deqingpu.fragment.geren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.RizhiAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/9.
 */

public class QuanziFragmentRizhi extends BaseFragment {


    @BindView(R.id.lv_rizhi)
    ListView lvRizhi;
    Unbinder unbinder;

    @Override
    protected int getViewId() {
        return R.layout.geren_fragm_quanzi_rizhi;
    }

    @Override
    public void init() {

        RizhiAdapter adapter=new RizhiAdapter(getContext(),null);
        lvRizhi.setAdapter(adapter);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
