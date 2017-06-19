package com.bayue.live.deqingpu.ui.merchant;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.FragmentActivityBase;

/**
 * Created by BaYue on 2017/6/16.
 * email : 2651742485@qq.com
 */

public class GoodsListActivity extends FragmentActivityBase {
    FragFoodList fragFoodList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        if (fragFoodList == null) {
            fragFoodList = FragFoodList.newInstance(getString(R.string.navigation_text_tab));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragFoodList);
        transaction.commit();

    }
}
