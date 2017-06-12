package com.bayue.live.deqingpu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.fragment.NavigationFragment;
import com.bayue.live.deqingpu.fragment.TextTabFragment;
import com.bayue.live.deqingpu.preferences.Preferences;

import java.util.ArrayList;
import java.util.List;

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
        checkPublishPermission();
//        setCurrentFragment();
        if (mTextTabFragment == null) {
            mTextTabFragment = TextTabFragment.newInstance(getString(R.string.navigation_text_tab));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, mTextTabFragment);
        transaction.commit();
//        Snackbar.make(drawerLayout, "TextView + LinearLayout", Snackbar.LENGTH_SHORT).show();
        Log.e("token=====》》》》》", Preferences.getString(this,Preferences.TOKEN));

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
    /**
     * 6.0权限处理
     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;

    private boolean checkPublishPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissiions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissiions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.CAMERA)) {
                permissiions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissiions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissiions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (permissiions.size() != 0) {
                ActivityCompat.requestPermissions(MainTabActivity.this,
                        (String[]) permissiions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }
}
