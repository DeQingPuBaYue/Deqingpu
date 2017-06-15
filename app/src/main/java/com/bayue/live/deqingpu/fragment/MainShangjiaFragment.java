package com.bayue.live.deqingpu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.QuanzhiAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.ui.merchant.FragMerchantFood;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.TopActionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MainShangjiaFragment extends BaseFragment {
    String TAG = "MainShangjiaFragment";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.txtMerchantFood)
    TextView txtMerchantFood;
    @BindView(R.id.txtMerchantHotel)
    TextView txtMerchantHotel;
    @BindView(R.id.txtMerchantPlay)
    TextView txtMerchantPlay;
    @BindView(R.id.txtMerchantTravel)
    TextView txtMerchantTravel;
    @BindView(R.id.txtMerchantAll)
    TextView txtMerchantAll;
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.vpMerchant)
    ViewPager vpMerchant;
    Unbinder unbinder;
    private int currentIndex;
    private int screenWidth;
    ArrayList<BaseFragment> fragments=new ArrayList<>();
    private Handler mHandler = new Handler();
    public static MainShangjiaFragment newInstance(String s) {
        MainShangjiaFragment homeFragment = new MainShangjiaFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.main_fragm_shangjia;
    }

    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (vpMerchant!=null) {
                vpMerchant.setCurrentItem(0);
            }
        }
    };

    @Override
    public void init() {
        Tracer.d(TAG, "");
        screenWidth = Utils.getScreenSize(baseActivity)[0];
        fragments.add(new FragMerchantFood());
        fragments.add(new FragMerchantFood());
        fragments.add(new FragMerchantFood());
        fragments.add(new FragMerchantFood());
        fragments.add(new FragMerchantFood());
        QuanzhiAdapter adapter=new QuanzhiAdapter(getChildFragmentManager(),fragments);
        vpMerchant.setAdapter(adapter);
        mHandler.postDelayed(mLoopRunnable, 300);
//        vpMerchant.setCurrentItem(0);
        setItemSelect(txtMerchantFood);
        //加线
        initTabLineWidth();
        vpMerchant.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset, int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine.getLayoutParams();

                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 * 5
                 */
                int vLineRight = (int) (offset * (screenWidth * 1.0 / 5) + currentIndex * (screenWidth / 5));
                int vLineLeft = (int) (-(1 - offset) * (screenWidth * 1.0 / 5) + currentIndex * (screenWidth / 5));
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = vLineRight;

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = vLineLeft;

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = vLineRight;
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = vLineLeft;
                } else if (currentIndex == 2 && position == 2) //2->3
                {
                    lp.leftMargin = vLineRight;
                }else if (currentIndex == 3 && position == 2) // 3->2
                {
                    lp.leftMargin = vLineLeft;
                }else if (currentIndex == 3 && position == 3) //3->4
                {
                    lp.leftMargin = vLineRight;
                }else if (currentIndex == 4 && position == 3) //4->3
                {
                    lp.leftMargin = vLineLeft;
                }

                viewLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int i) {
                resetTextView();
                switch (i) {
                    case 0:
                        setItemSelect(txtMerchantFood);
                        break;
                    case 1:
                        setItemSelect(txtMerchantHotel);
                        break;
                    case 2:
                        setItemSelect(txtMerchantPlay);
                        break;
                    case 3:
                        setItemSelect(txtMerchantTravel);
                        break;
                    case 4:
                        setItemSelect(txtMerchantAll);
                        break;
                }
                currentIndex = i;
            }
            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vpMerchant.setOffscreenPageLimit(1);
    }

    @OnClick({R.id.txtMerchantFood, R.id.txtMerchantHotel, R.id.txtMerchantPlay, R.id.txtMerchantTravel,R.id.txtMerchantAll})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.txtMerchantFood:
                vpMerchant.setCurrentItem(0);
                break;
            case R.id.txtMerchantHotel:
                vpMerchant.setCurrentItem(1);
                break;
            case R.id.txtMerchantPlay:
                vpMerchant.setCurrentItem(2);
                break;
            case R.id.txtMerchantTravel:
                vpMerchant.setCurrentItem(3);
                break;
            case R.id.txtMerchantAll:
                vpMerchant.setCurrentItem(4);
                break;
        }
    }

    private void setItemSelect(TextView textView){
        textView.setTextColor(getResources().getColor(R.color.black));
    }
    /**
     * 重置颜色
     */
    private void resetTextView() {
        txtMerchantFood.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantHotel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantPlay.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantTravel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantAll.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
    }
    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     * Utils.getScreenSize(baseContext) 0 :width, 1 : height
     */
    private void initTabLineWidth() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine
                .getLayoutParams();
        lp.width = screenWidth / 5;
        viewLine.setLayoutParams(lp);
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
