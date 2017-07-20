package com.bayue.live.deqingpu.ui.merchant;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.QuanzhiAdapter;
import com.bayue.live.deqingpu.adapter.ViewAdapter;
import com.bayue.live.deqingpu.adapter.ViewPagerAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.TopActionBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/16.
 * email : 2651742485@qq.com
 */

public class FragFoodList extends BaseFragment {

    String TAG = "FragFoodList";
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
    @BindView(R.id.viewLine)
    View viewLine;
    @BindView(R.id.vpMerchant)
    ViewPager vpMerchant;
    Unbinder unbinder;
    @BindView(R.id.linGoodsMenu)
    LinearLayout linGoodsMenu;
    @BindView(R.id.linGoodsDefault)
    LinearLayout linGoodsDefault;
    @BindView(R.id.imgGoodsSortTipValume)
    ImageView imgGoodsSortTipValume;
    @BindView(R.id.linGoodsValume)
    LinearLayout linGoodsValume;
    @BindView(R.id.imgGoodsSortTipPrice)
    ImageView imgGoodsSortTipPrice;
    @BindView(R.id.linGoodsPrice)
    LinearLayout linGoodsPrice;
    @BindView(R.id.imgGoodsSortTipAct)
    ImageView imgGoodsSortTipAct;
    @BindView(R.id.linGoodsAct)
    LinearLayout linGoodsAct;
    private int currentIndex;
    private int screenWidth, screenHeight;
    int valumeStatus = 1, priceStatus = 1, actStatus = 1, actionType = 0;// actionType 0 :merchant ? deqingpu
    ArrayList<LazyLoadFragment> fragments = new ArrayList<>();
    private Handler mHandler = new Handler();
    private int cat_id, storeId;
    private String keyword;

    public static FragFoodList newInstance(Bundle bundle) {
        FragFoodList viewPagerFragment = new FragFoodList();
//        Bundle bundle = new Bundle();
//        bundle.putString(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    private final Runnable mLoopRunnable = new Runnable() {
        @Override
        public void run() {
            if (vpMerchant != null) {
                vpMerchant.setCurrentItem(0);
            }
        }
    };

    @Override
    protected int getViewId() {
        return R.layout.frag_goodlist_viewpager;
    }

    FragGoodlist defaultFrag;
    FragGoodlist valumeFrag;
    FragGoodlist priceFrag;
    FragGoodlist actFrag;
    @Override
    public void init() {
        topBar.setTitle(getString(R.string.title_goods));
        topBar.setBackClickListener(new TopActionBar.BackClickListener() {
            @Override
            public void backClick() {
                getActivity().finish();
            }
        });
        screenWidth = Utils.getScreenSize(baseActivity)[0];
        screenHeight = Utils.getScreenSize(baseActivity)[1];
        cat_id = getArguments().getInt("cat_id");
        storeId = getArguments().getInt("store");
        actionType = getArguments().getInt("actionType");
        keyword = getArguments().getString("keyword");
        Bundle defaultBundle = getBundle();
        defaultBundle.putString("order", "goods_id");
        defaultBundle.putString("sort", "ASC");
        defaultFrag = FragGoodlist.newInstance(defaultBundle);
        Bundle valumeBundle = getBundle();
        valumeBundle.putString("order", "sales");
        valumeBundle.putString("sort", "ASC");
        valumeFrag = FragGoodlist.newInstance(valumeBundle);
        Bundle priceBundle = getBundle();
        priceBundle.putString("order", "shop_price");
        priceBundle.putString("sort", "ASC");
        priceFrag = FragGoodlist.newInstance(priceBundle);
//        Bundle actBundle = getBundle();
//        actBundle.putString("order", "goods_id");
//        actBundle.putString("sort", "ASC");
        actFrag = FragGoodlist.newInstance(defaultBundle);
        fragments.add(defaultFrag);
        fragments.add(valumeFrag);
        fragments.add(priceFrag);
        fragments.add(actFrag);
        ViewAdapter adapter = new ViewAdapter(getChildFragmentManager(), fragments);
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
                int vLineRight = (int) (offset * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
                int vLineLeft = (int) (-(1 - offset) * (screenWidth * 1.0 / 4) + currentIndex * (screenWidth / 4));
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
                } else if (currentIndex == 3 && position == 2) // 3->2
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
        vpMerchant.setOffscreenPageLimit(0);
    }

    private void setItemSelect(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.black));
    }
    @OnClick({R.id.linGoodsDefault, R.id.linGoodsValume, R.id.linGoodsPrice, R.id.linGoodsAct})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.linGoodsDefault:
                vpMerchant.setCurrentItem(0);
                break;
            case R.id.linGoodsValume:
                if (valumeStatus == 1){valumeStatus = 2;}
                else{valumeStatus = 1;}
                vpMerchant.setCurrentItem(1);
                break;
            case R.id.linGoodsPrice:
                if (priceStatus == 1){priceStatus = 2;}
                else{priceStatus = 1;}
                vpMerchant.setCurrentItem(2);
                break;
            case R.id.linGoodsAct:
                if (actStatus == 1){actStatus = 2;}
                else{actStatus = 1;}
                vpMerchant.setCurrentItem(3);
                break;
        }
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        txtMerchantFood.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantHotel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantPlay.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
        txtMerchantTravel.setTextColor(getResources().getColor(R.color.zilanmu_textcolor));
    }

    public Bundle getBundle(){
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ARGS, 0);
        bundle.putInt("cat_id", cat_id);
        bundle.putInt("store", storeId);
        bundle.putInt("actionType", actionType);
        bundle.putString("keyword", keyword);
        return bundle;
    }

    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     * Utils.getScreenSize(baseContext) 0 :width, 1 : height
     */
    private void initTabLineWidth() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewLine
                .getLayoutParams();
        lp.width = screenWidth / 4;
        viewLine.setLayoutParams(lp);
        Tracer.e(TAG, linGoodsMenu.getHeight() + " menu height");
//        LinearLayout.LayoutParams lh = (LinearLayout.LayoutParams) linGoodsMenu
//                .getLayoutParams();
//        lh.height = screenWidth / 10;
//        linGoodsMenu.setLayoutParams(lh);
//        Drawable drawable= getResources().getDrawable(R.mipmap.icon_asc);
        // 这一步必须要做,否则不会显示.
//        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//        txtMerchantHotel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
//        txtMerchantHotel.setCompoundDrawablePadding(-10);
//        txtMerchantPlay.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
//        txtMerchantTravel.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
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
