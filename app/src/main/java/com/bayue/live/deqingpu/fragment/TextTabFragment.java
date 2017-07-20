package com.bayue.live.deqingpu.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.utils.Tracer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by Kevin on 2016/11/29.
 * Blog:http://blog.csdn.net/student9128
 * Description: Bottom Navigation Bar by TextView + LinearLayout.
 */

public class TextTabFragment extends BaseFragment{
    String TAG = "TextTabFragment";
    @BindView(R.id.activity_text_view)
    TextView activityTextView;
    @BindView(R.id.sub_content)
    FrameLayout subContent;
    @BindView(R.id.iv_shangjia_bottom)
    ImageView ivShangjiaBottom;
    @BindView(R.id.tv_shangjia_bottom)
    TextView tvShangjiaBottom;
    @BindView(R.id.ll_shangjia_bottm)
    LinearLayout llShangjiaBottm;
    @BindView(R.id.iv_zhibo_bottom)
    ImageView ivZhiboBottom;
    @BindView(R.id.tv_zhibo_bottom)
    TextView tvZhiboBottom;
    @BindView(R.id.ll_zhibo_bottm)
    LinearLayout llZhiboBottm;
    @BindView(R.id.iv_shenghuo_bottom)
    ImageView ivShenghuoBottom;
    @BindView(R.id.ll_shenghuo_bottm)
    LinearLayout llShenghuoBottm;
    @BindView(R.id.iv_tongxun_bottom)
    ImageView ivTongxunBottom;
    @BindView(R.id.tv_tongxun_bottom)
    TextView tvTongxunBottom;
    @BindView(R.id.ll_tongxun_bottm)
    LinearLayout llTongxunBottm;
    @BindView(R.id.iv_geren_bottom)
    ImageView ivGerenBottom;
    @BindView(R.id.tv_geren_bottom)
    TextView tvGerenBottom;
    @BindView(R.id.ll_geren_bottm)
    LinearLayout llGerenBottm;
    Unbinder unbinder;
    @BindView(R.id.tv_sh_bottom)
    TextView tvShBottom;
    private MainShangjiaFragment mSjFragment;
    private MainZhiboFragment mZbFragment;
    private MainFragment mShFragment;
    private MainTongxunFragment mTxFragment;
    private MainGerenFragment mPersonFragment;

    public static TextTabFragment newInstance(String s) {
        TextTabFragment viewPagerFragment = new TextTabFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_tab, container, false);

        unbinder = ButterKnife.bind(this, view);

        setDefaultFragment();
        return view;
    }

    @Override
    protected int getViewId() {
        return R.layout.fragment_text_tab;
    }

    @Override
    public void init() {
//        setDefaultFragment();
        Tracer.d(TAG, "");
    }

    /**
     * set the default Fragment
     */
    private void setDefaultFragment() {
        switchFrgment(0);
        //set the defalut tab state
        setTabState(tvShangjiaBottom, ivShangjiaBottom, R.mipmap.icon_09_1, getColor(R.color.white));
    }

    @OnClick({R.id.ll_shangjia_bottm,R.id.ll_zhibo_bottm,R.id.ll_shenghuo_bottm,
            R.id.ll_tongxun_bottm,R.id.ll_geren_bottm})
    public void onClick(View view) {
        resetTabState();//reset the tab state
        switch (view.getId()) {
            case R.id.ll_shangjia_bottm:
                setTabState(tvShangjiaBottom, ivShangjiaBottom, R.mipmap.icon_09_1, getColor(R.color.white));
                switchFrgment(0);
                break;
            case R.id.ll_zhibo_bottm:
                setTabState(tvZhiboBottom, ivZhiboBottom, R.mipmap.icon_03_1_1, getColor(R.color.white));
                switchFrgment(1);
                break;
            case R.id.ll_shenghuo_bottm:
                setTabState(tvShBottom, ivShenghuoBottom, R.mipmap.icon_living, getColor(R.color.white));
                switchFrgment(2);
                break;
            case R.id.ll_tongxun_bottm:
                setTabState(tvTongxunBottom, ivTongxunBottom, R.mipmap.icon_05_1, getColor(R.color.white));
                switchFrgment(3);
                break;
            case R.id.ll_geren_bottm:
                setTabState(tvGerenBottom, ivGerenBottom, R.mipmap.icon_06_1, getColor(R.color.white));
                switchFrgment(4);
                break;
        }
    }

    /**
     * switch the fragment accordting to id
     *
     * @param i id
     */
    private void switchFrgment(int i) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        switch (i) {
            case 0:
                if (mSjFragment == null) {
                    mSjFragment = mSjFragment.newInstance(getString(R.string.item_sj));
                }
                transaction.replace(R.id.sub_content, mSjFragment);
                break;
            case 1:
                if (mZbFragment == null) {
                    mZbFragment = mZbFragment.newInstance(getString(R.string.item_location));
                }
                transaction.replace(R.id.sub_content, mZbFragment);
                break;
            case 2:
                if (mShFragment == null) {
                    mShFragment = mShFragment.newInstance(getString(R.string.item_sh));
                }
                transaction.replace(R.id.sub_content, mShFragment);
                break;
            case 3:
                if (mTxFragment == null) {
                    mTxFragment = mTxFragment.newInstance(getString(R.string.item_txl));
                }
                transaction.replace(R.id.sub_content, mTxFragment);
                break;
            case 4:
                if (mPersonFragment == null) {
                    mPersonFragment = mPersonFragment.newInstance(getString(R.string.item_gr));
                }
                transaction.replace(R.id.sub_content, mPersonFragment);
                break;
        }
        transaction.commit();
    }

    /**
     * set the tab state of bottom navigation bar
     *
     * @param textView the text to be shown
     * @param image    the image
     * @param color    the text color
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setTabState(TextView textView,ImageView imageView, int image, int color) {
//        textView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, image, 0, 0);//Call requires API level 17
        imageView.setImageResource(image);
        textView.setTextColor(color);
    }


    /**
     * revert the image color and text color to black
     */
    private void resetTabState() {
        setTabState(tvShangjiaBottom, ivShangjiaBottom, R.mipmap.icon_09, getColor(R.color.color_white));
        setTabState(tvZhiboBottom, ivZhiboBottom, R.mipmap.icon_03_1, getColor(R.color.color_white));
        setTabState(tvShBottom, ivShenghuoBottom, R.mipmap.icon_living_select, getColor(R.color.color_white));
        setTabState(tvTongxunBottom, ivTongxunBottom, R.mipmap.icon_05, getColor(R.color.color_white));
        setTabState(tvGerenBottom, ivGerenBottom, R.mipmap.icon_06, getColor(R.color.color_white));

    }

    /**
     * @param i the color id
     * @return color
     */
    private int getColor(int i) {
        return ContextCompat.getColor(getActivity(), i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
