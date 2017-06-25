package com.bayue.live.deqingpu.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bayue.live.deqingpu.App;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.zhy.autolayout.utils.ScreenUtils.getStatusBarHeight;

/**
 * Created by Administrator on 2017/5/31.
 */

public abstract class BaseActivity extends AutoLayoutActivity {


    protected abstract int getViewId();
    protected abstract void initViews();
    protected abstract void setTheme();
    /**当前Activity*/
    protected Activity baseActivity;

    /**当前上下文*/
    protected Context baseContext;
    Unbinder unbinder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseActivity = this;
        baseContext = this.getBaseContext();
        App.getInstance().addActivity(this);
        quanping();
        setTheme();
        setContentView(getViewId());
        unbinder= ButterKnife.bind(this);
        initViews();

    }
    public void quanping(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height ==getStatusBarHeight(this)) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        //
        //
    }
}
