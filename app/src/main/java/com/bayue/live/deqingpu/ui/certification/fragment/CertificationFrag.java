package com.bayue.live.deqingpu.ui.certification.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.ViewPagerAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.TabBean;
import com.bayue.live.deqingpu.view.TopActionBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class CertificationFrag extends BaseFragment implements ViewPager.OnPageChangeListener {
    @BindView(R.id.grAuth)
    RadioGroup grAuth;
    @BindView(R.id.hvAuth)
    HorizontalScrollView hvAuth;
    @BindView(R.id.viewAuth)
    ViewPager viewAuth;
    Unbinder unbinder;
    ViewPagerAdapter adapter;
    @BindView(R.id.topBar)
    TopActionBar topBar;

    public static CertificationFrag newInstance(String s) {
        CertificationFrag viewPagerFragment = new CertificationFrag();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.ac_certification;
    }

    @Override
    public void init() {
        topBar.setTitle(getString(R.string.title_auth));
        grAuth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int id) {
                viewAuth.setCurrentItem(id);
            }
        });


    }

    /***
     * 初始化viewpager
     */
    private void initView() {

        RealAuthFrag realAuthFrag = new RealAuthFrag();
//        RealAuth realAuth = new RealAuth();
        EnterpriseAuthFrag enterpriseAuthFrag = new EnterpriseAuthFrag();
        MerchantAuthFrag merchantAuthFrag = new MerchantAuthFrag();
        StarAuthFrag starAuthFrag = new StarAuthFrag();
        newsList.add(realAuthFrag);
        newsList.add(merchantAuthFrag);
        newsList.add(enterpriseAuthFrag);
        newsList.add(starAuthFrag);
        //设置viewpager适配器
        adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), newsList);
        viewAuth.setAdapter(adapter);
        //两个viewpager切换不重新加载
        viewAuth.setOffscreenPageLimit(2);
        //设置默认
        viewAuth.setCurrentItem(0);
        //设置viewpager监听事件
//        viewAuth.setOnPageChangeListener(this);
        viewAuth.addOnPageChangeListener(this);
    }

    /***
     * 初始化头部导航栏
     * @param inflater
     */
    List<TabBean> Selected = new ArrayList<>();
    private List<Fragment> newsList = new ArrayList<>();

    private void initTab(LayoutInflater inflater) {

        for (int i = 0; i < Selected.size(); i++) {
            //设置头部项布局初始化数据
            RadioButton rbButton = (RadioButton) inflater.inflate(R.layout.tab_rb, null);
            rbButton.setId(i);
            rbButton.setText(Selected.get(i).getName());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT);
            //加入RadioGroup
            grAuth.addView(rbButton, params);
        }
        //默认点击
        grAuth.check(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        Selected.add(new TabBean("实名认证"));
        Selected.add(new TabBean("商家认证"));
        Selected.add(new TabBean("企业认证"));
        Selected.add(new TabBean("大咖认证"));
        //初始化顶部导航栏
        //初始化viewpager
        initView();
        initTab(inflater);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        setTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
    /***
     * 页面跳转切换头部偏移设置
     * @param id
     */
    private void setTab(int id) {
        RadioButton rbButton = (RadioButton) grAuth.getChildAt(id);
        //设置标题被点击
        rbButton.setChecked(true);
        //偏移设置
        int left = rbButton.getLeft();
        int width = rbButton.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        //移动距离= 左边的位置 + button宽度的一半 - 屏幕宽度的一半
        int len = left + width / 2 - screenWidth / 2;
        //移动
        hvAuth.smoothScrollTo(len, 0);
    }
}
