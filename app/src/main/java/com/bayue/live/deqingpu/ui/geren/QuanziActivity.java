package com.bayue.live.deqingpu.ui.geren;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.QuanzhiAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.entity.geren.FabuBean;
import com.bayue.live.deqingpu.fragment.geren.QuanziFragmentGuanzhu;
import com.bayue.live.deqingpu.fragment.geren.QuanziFragmentHaoyou;
import com.bayue.live.deqingpu.fragment.geren.QuanziFragmentRizhi;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/9.
 */

public class QuanziActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.ll_rigthimage_title)
    LinearLayout llRigthimageTitle;
    @BindView(R.id.tv_rizhi_quanzi)
    TextView tvRizhiQuanzi;
    @BindView(R.id.tv_haoyou_quanzi)
    TextView tvHaoyouQuanzi;
    @BindView(R.id.tv_guanzhu_quanzi)
    TextView tvGuanzhuQuanzi;
    @BindView(R.id.view_huaxian_quanzi)
    View viewHuaxianQuanzi;
    @BindView(R.id.vp_quanzi)
    ViewPager vpQuanzi;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    //s'd'f's'd'f's'd'f's'd'f's'd'f's'd'

    ArrayList<BaseFragment> fragments=new ArrayList<>();

    @Override
    protected int getViewId() {
        return R.layout.geren_activity_quanzi;
    }

    @Override
    protected void initViews() {

        fragments.add(new QuanziFragmentRizhi());
        fragments.add(new QuanziFragmentHaoyou());
        fragments.add(new QuanziFragmentGuanzhu());
        QuanzhiAdapter adapter=new QuanzhiAdapter(getSupportFragmentManager(),fragments);
        vpQuanzi.setAdapter(adapter);
        vpQuanzi.setCurrentItem(0);
        tvRizhiQuanzi.setTextColor(QuanziActivity.this.getResources().getColor(R.color.black));

        initTabLineWidth();

        vpQuanzi.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHuaxianQuanzi
                        .getLayoutParams();

                Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */

                if (currentIndex == 0 && position == 0)// 0->1
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                    Log.e(">>>>>滑动>>>",""+lp.leftMargin);

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    lp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }

                viewHuaxianQuanzi.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        tvRizhiQuanzi.setTextColor(QuanziActivity.this.getResources().getColor(R.color.black));
                        Log.e(">>>>>滑动>>>","日志");
                        break;
                    case 1:
                        Log.e(">>>>>滑动>>>","好友");
                        tvHaoyouQuanzi.setTextColor(QuanziActivity.this.getResources().getColor(R.color.black));
                        break;
                    case 2:
                        Log.e(">>>>>滑动>>>","关注");
                        tvGuanzhuQuanzi.setTextColor(QuanziActivity.this.getResources().getColor(R.color.black));
                        break;
                }
                currentIndex = position;
            }
        });

    }
    /**
     * 重置颜色
     */
    private void resetTextView() {
        tvRizhiQuanzi.setTextColor(this.getResources().getColor(R.color.zilanmu_textcolor));
        tvHaoyouQuanzi.setTextColor(this.getResources().getColor(R.color.zilanmu_textcolor));
        tvGuanzhuQuanzi.setTextColor(this.getResources().getColor(R.color.zilanmu_textcolor));
    }
    /**
     * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) viewHuaxianQuanzi
                .getLayoutParams();
        lp.width = screenWidth / 3;
        viewHuaxianQuanzi.setLayoutParams(lp);
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

    @OnClick({R.id.ll_back_title, R.id.ll_rigthimage_title, R.id.tv_rizhi_quanzi, R.id.tv_haoyou_quanzi, R.id.tv_guanzhu_quanzi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.ll_rigthimage_title:
                startActivity(new Intent(QuanziActivity.this,XierizhiActivity.class));
                break;
            case R.id.tv_rizhi_quanzi:
                vpQuanzi.setCurrentItem(0);
                break;
            case R.id.tv_haoyou_quanzi:
                vpQuanzi.setCurrentItem(1);
                break;
            case R.id.tv_guanzhu_quanzi:
                vpQuanzi.setCurrentItem(2);
                break;
        }
    }
    private  void getRizhi(){


        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("page","")
                .add("token", Preferences.getString(this,Preferences.TOKEN))
                .build();
        Request request = new Request.Builder()
                .url(API.baseUrl+API.QUANZI_LIEBIAO)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200){
                    Gson gson = new Gson();
                    final FabuBean fabuBean= gson.fromJson(msg,FabuBean.class);

                    Log.e(">>>>",fabuBean.getCode()+"");
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (fabuBean.getCode()==200){
                                Log.e(">>>>","发布——————");
                                DensityUtil.showToast(QuanziActivity.this,fabuBean.getData());
                                finish();



                            }else {
                                DensityUtil.showToast(QuanziActivity.this,fabuBean.getMsg());
                                Log.e(">>>>",fabuBean.getMsg());
                            }

                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(QuanziActivity.this,response.message());
                        }
                    });
                }
            }
        });

    }
}
