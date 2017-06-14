package com.bayue.live.deqingpu.fragment.geren;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.HaoyouAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/6/9.
 */

public class QuanziFragmentHaoyou extends BaseFragment {


    @BindView(R.id.lv_haoyou)
    ListView lvhaoyou;
    Unbinder unbinder;

    HaoyouAdapter adapter;

    @Override
    protected int getViewId() {
        return R.layout.geren_fragm_quanzi_haoyou;

    }

    @Override
    public void init() {
        adapter=new HaoyouAdapter(getContext(),null);
        lvhaoyou.setAdapter(adapter);

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
