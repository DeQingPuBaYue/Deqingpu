package com.bayue.live.deqingpu;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.fragment.NavigationFragment;
import com.bayue.live.deqingpu.fragment.TextTabFragment;
import com.bayue.live.deqingpu.utils.Tracer;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/2.
 */

public class MyActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.tool_bar)
    Toolbar toolBar;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private NightModeHelper mNightModeHelper;

    private NavigationFragment mNavigationFragment;
    private TextTabFragment mTextTabFragment;
//    private TabLayoutFragment mTabLayoutFragment;
//    private TabLayoutFragment2 mTabLayoutFragment2;

    @Override
    protected int getViewId() {
        return R.layout.activity_my;
    }

    @Override
    protected void setTheme() {
//        mNightModeHelper = new NightModeHelper(this, R.style.BaseTheme);
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.drawer_layout_open, R.string.drawer_layout_close);
        mDrawerToggle.syncState();
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        View headerView = navigationView.getHeaderView(0);
//        headerView.setOnClickListener(this);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ContextCompat.getColorStateList(this, R.color.bg_drawer_navigation));
        navigationView.setItemIconTintList(ContextCompat.getColorStateList(this, R.color.bg_drawer_navigation));
        setNavigationViewChecked(0);
        setCurrentFragment();
    }
    private void setNavigationViewChecked(int position) {
        navigationView.getMenu().getItem(position).setChecked(true);
        Tracer.i("Kevin", "the count of menu item is--->" + navigationView.getMenu().size() + "");
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            if (i != position) {
                navigationView.getMenu().getItem(i).setChecked(false);
            }
        }
    }

    private void setCurrentFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        mNavigationFragment = NavigationFragment.newInstance(getString(R.string.navigation_navigation_bar));
        transaction.replace(R.id.frame_content, mNavigationFragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (menuItem.getItemId()) {
            case R.id.menu_bottom_navigation_bar:
                if (mNavigationFragment == null) {
                    mNavigationFragment = NavigationFragment.newInstance(getString(R.string.navigation_navigation_bar));
                }
                transaction.replace(R.id.frame_content, mNavigationFragment);
                Snackbar.make(drawerLayout, "NavigationBar", Snackbar.LENGTH_SHORT).show();
                setNavigationViewChecked(0);
                break;
//            case R.id.menu_radio_group:
//                if (mRadioFragment == null) {
//                    mRadioFragment = RadioFragment.newInstance(getString(R.string.navigation_radio_bar));
//                }
//                transaction.replace(R.id.frame_content, mRadioFragment);
////                Snackbar.make(mDrawerLayout, "RadioGroup", Snackbar.LENGTH_SHORT).show();
//                SnackBarUtils.showSnackBar(mDrawerLayout, getString(R.string.navigation_radio_bar), this);
//                setNavigationViewChecked(1);
//                break;
            case R.id.menu_text_view:
                if (mTextTabFragment == null) {
                    mTextTabFragment = TextTabFragment.newInstance(getString(R.string.navigation_text_tab));
                }
                transaction.replace(R.id.frame_content, mTextTabFragment);
                Snackbar.make(drawerLayout, "TextView + LinearLayout", Snackbar.LENGTH_SHORT).show();
                setNavigationViewChecked(2);
                break;
//            case R.id.menu_tab_layout:
//                if(mTabLayoutFragment == null){
//                    mTabLayoutFragment = TabLayoutFragment.newInstance(getString(R.string.navigation_tab_layout));
//                }
//                transaction.replace(R.id.frame_content, mTabLayoutFragment);
//                setNavigationViewChecked(3);
//                Snackbar.make(mDrawerLayout, "TabLayout + ViewPager", Snackbar.LENGTH_SHORT).show();
//                break;
//            case R.id.menu_tab_layout2:
//                if (mTabLayoutFragment2 == null) {
//                    mTabLayoutFragment2 = TabLayoutFragment2.newInstance(getString(R.string.navigation_tab_layout2));
//                }
//                transaction.replace(R.id.frame_content, mTabLayoutFragment2);
//                setNavigationViewChecked(4);
//                Snackbar.make(mDrawerLayout, "TabLayout + ViewPager 2", Snackbar.LENGTH_SHORT).show();
//                break;


        }
        drawerLayout.closeDrawers();
        transaction.commit();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.settings:
                Snackbar.make(drawerLayout, "Settings", Snackbar.LENGTH_SHORT).show();
                return true;
            case R.id.share:
//                mNightModeHelper.toggle();
//                Configuration newConfig = new Configuration(getResources().getConfiguration());
//                newConfig.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
//                newConfig.uiMode |= uiNightMode;
//                getResources().updateConfiguration(newConfig, null);
//                recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
