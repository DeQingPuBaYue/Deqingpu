package com.bayue.live.deqingpu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bayue.live.deqingpu.Constants;
import com.bayue.live.deqingpu.R;


/**
 * Created by Kevin on 2016/11/28.
 * Blog:http://blog.csdn.net/student9128
 * Description: Bottom Navigation Bar by BottomNavigationBar
 */

public class NavigationFragment extends Fragment implements BottomNavigationBar.OnTabSelectedListener {


    private BottomNavigationBar mBottomNavigationBar;
    private MainShangjiaFragment mMerchantFrag;
    private MainZhiboFragment mLocationFragment;
    private MainShenghuoFragment mLiveFrag;
    private MainTongxunFragment mMailFrag;
    private MainGerenFragment mPersonFrag;
    private TextView mTextView;

    public static NavigationFragment newInstance(String s) {
        NavigationFragment navigationFragment = new NavigationFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        navigationFragment.setArguments(bundle);
        return navigationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation_bar, container, false);
        mTextView = (TextView) view.findViewById(R.id.activity_text_view);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String s = bundle.getString(Constants.ARGS);
            if (!TextUtils.isEmpty(s)) {
                mTextView.setText(s);
            }
        }
        mBottomNavigationBar = (BottomNavigationBar) view.findViewById(R.id.bottom_navigation_bar);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.icon_09_1, getString(R.string.item_sj)).setInactiveIconResource(R.mipmap.icon_09).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.mipmap.icon_03_1_1, getString(R.string.item_zb)).setInactiveIconResource(R.mipmap.icon_03_1).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.mipmap.icon_01_1_1, getString(R.string.item_txl)).setInactiveIconResource(R.mipmap.icon_01_1).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .addItem(new BottomNavigationItem(R.mipmap.icon_05_1, getString(R.string.item_gr)).setInactiveIconResource(R.mipmap.icon_05).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.black_1))
                .setFirstSelectedPosition(0)
                .initialise();

        mBottomNavigationBar.setTabSelectedListener(this);

        setDefaultFragment();
        return view;
    }

    /**
     * set the default fagment
     * <p>
     * the content id should not be same with the parent content id
     */
    private void setDefaultFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        MainShangjiaFragment homeFragment = mMerchantFrag.newInstance(getString(R.string.item_sj));
        transaction.replace(R.id.sub_content, homeFragment).commit();

    }

    @Override
    public void onTabSelected(int position) {
        FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                if (mMerchantFrag == null) {
                    mMerchantFrag = MainShangjiaFragment.newInstance(getString(R.string.item_sj));
                }
                beginTransaction.replace(R.id.sub_content, mMerchantFrag);
                break;
            case 1:
                if (mLocationFragment == null) {
                    mLocationFragment = MainZhiboFragment.newInstance(getString(R.string.item_zb));
                }
                beginTransaction.replace(R.id.sub_content, mLocationFragment);
                break;
            case 2:
                if (mMailFrag == null) {
                    mMailFrag = MainTongxunFragment.newInstance(getString(R.string.item_txl));
                }
                beginTransaction.replace(R.id.sub_content, mMailFrag);
                break;
            case 3:
                if (mPersonFrag == null) {
                    mPersonFrag = MainGerenFragment.newInstance(getString(R.string.item_gr));
                }
                beginTransaction.replace(R.id.sub_content, mPersonFrag);
                break;
        }
        beginTransaction.commit();

    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
