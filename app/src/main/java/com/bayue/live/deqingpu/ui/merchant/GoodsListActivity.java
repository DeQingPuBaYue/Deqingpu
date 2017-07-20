package com.bayue.live.deqingpu.ui.merchant;

import android.app.Activity;
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
    FragViewPager fragViewPager;
    private int cat_id, storeId, actionType;
    private String keyword;
    public static Activity instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        instance = this;
        cat_id = getIntent().getIntExtra("cat_id", 0);
        storeId = getIntent().getIntExtra("store", 0);
        actionType = getIntent().getIntExtra("actionType", 0);
        keyword = getIntent().getStringExtra("keyword");
        if (fragFoodList == null) {
            Bundle bundle = new Bundle();
            bundle.putInt("cat_id", cat_id);
            bundle.putInt("store", storeId);
            bundle.putInt("actionType", actionType);
            bundle.putString("keyword", keyword);
            fragFoodList = FragFoodList.newInstance(bundle);
        }
//         if (fragViewPager == null) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("cat_id", cat_id);
//                    bundle.putInt("store", storeId);
//                    bundle.putInt("actionType", actionType);
//                    bundle.putString("keyword", keyword);
//             fragViewPager = FragViewPager.newInstance(bundle);
//         }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, fragFoodList);
        transaction.commit();

    }
}
