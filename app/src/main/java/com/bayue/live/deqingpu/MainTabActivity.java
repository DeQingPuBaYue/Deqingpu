package com.bayue.live.deqingpu;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.fragment.NavigationFragment;
import com.bayue.live.deqingpu.fragment.TextTabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/2.
 */

public class MainTabActivity extends BaseActivity {


    private TextTabFragment mTextTabFragment;

    @Override
    protected int getViewId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initViews() {
//        setCurrentFragment();
        if (mTextTabFragment == null) {
            mTextTabFragment = TextTabFragment.newInstance(getString(R.string.navigation_text_tab));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, mTextTabFragment);
        transaction.commit();
//        Snackbar.make(drawerLayout, "TextView + LinearLayout", Snackbar.LENGTH_SHORT).show();
    }



    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
