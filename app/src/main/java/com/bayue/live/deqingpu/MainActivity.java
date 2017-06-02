package com.bayue.live.deqingpu;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fragm_shangjia)
    LinearLayout fragmShangjia;
    @BindView(R.id.fragm_zhibo)
    LinearLayout fragmZhibo;
    @BindView(R.id.fragm_shenghuo)
    LinearLayout fragmShenghuo;
    @BindView(R.id.fragm_tongxun)
    LinearLayout fragmTongxun;
    @BindView(R.id.fragm_geren)
    LinearLayout fragmGeren;
    @BindView(R.id.vp_myactivity)
    FrameLayout vpMyactivity;
    @BindView(R.id.iv_shangjia_bottom)
    ImageView ivShangjiaBottom;
    @BindView(R.id.tv_shangjia_bottom)
    TextView tvShangjiaBottom;
    @BindView(R.id.ll_shangjia_bottm)
    LinearLayout llShangjiaBottm;
    @BindView(R.id.iv_zhibo_bottom)
    ImageView ivZhiboBottom;
    @BindView(R.id.tv_zhibo_bottom)
    TextView tvZhiboBottom;
    @BindView(R.id.ll_zhibo_bottm)
    LinearLayout llZhiboBottm;
    @BindView(R.id.iv_shenghuo_bottom)
    ImageView ivShenghuoBottom;
    @BindView(R.id.ll_shenghuo_bottm)
    LinearLayout llShenghuoBottm;
    @BindView(R.id.iv_tongxun_bottom)
    ImageView ivTongxunBottom;
    @BindView(R.id.tv_tongxun_bottom)
    TextView tvTongxunBottom;
    @BindView(R.id.ll_tongxun_bottm)
    LinearLayout llTongxunBottm;
    @BindView(R.id.iv_geren_bottom)
    ImageView ivGerenBottom;
    @BindView(R.id.tv_geren_bottom)
    TextView tvGerenBottom;
    @BindView(R.id.ll_geren_bottm)
    LinearLayout llGerenBottm;

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);

        ivGerenBottom.setImageResource(R.mipmap.icon_06_1);
        tvGerenBottom.setTextColor(this.getResources().getColor(R.color.white));
        fragmGeren.setVisibility(View.VISIBLE);

    }

    @OnClick({R.id.ll_shangjia_bottm, R.id.ll_zhibo_bottm, R.id.ll_shenghuo_bottm,
            R.id.ll_tongxun_bottm, R.id.ll_geren_bottm})
    public void onViewClicked(View view) {
        hueifu();
        switch (view.getId()) {
            case R.id.ll_shangjia_bottm:
                ivShangjiaBottom.setImageResource(R.mipmap.icon_09_1);
                tvShangjiaBottom.setTextColor(this.getResources().getColor(R.color.white));
                fragmShangjia.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_zhibo_bottm:
                ivZhiboBottom.setImageResource(R.mipmap.icon_03_1_1);
                tvZhiboBottom.setTextColor(this.getResources().getColor(R.color.white));
                fragmZhibo.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_shenghuo_bottm:
                ivShenghuoBottom.setImageResource(R.mipmap.icon_01_1_1);
                fragmShenghuo.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_tongxun_bottm:
                ivTongxunBottom.setImageResource(R.mipmap.icon_05_1);
                tvTongxunBottom.setTextColor(this.getResources().getColor(R.color.white));
                fragmTongxun.setVisibility(View.VISIBLE);

                break;
            case R.id.ll_geren_bottm:
                ivGerenBottom.setImageResource(R.mipmap.icon_06_1);
                tvGerenBottom.setTextColor(this.getResources().getColor(R.color.white));
                fragmGeren.setVisibility(View.VISIBLE);

                break;
        }
    }

    //恢复原图片
    private void hueifu() {
        ivShangjiaBottom.setImageResource(R.mipmap.icon_09);
        tvShangjiaBottom.setTextColor(this.getResources().getColor(R.color.bottomtextcolor));

        ivZhiboBottom.setImageResource(R.mipmap.icon_03_1);
        tvZhiboBottom.setTextColor(this.getResources().getColor(R.color.bottomtextcolor));

        ivShenghuoBottom.setImageResource(R.mipmap.icon_01_1);

        ivTongxunBottom.setImageResource(R.mipmap.icon_05);
        tvTongxunBottom.setTextColor(this.getResources().getColor(R.color.bottomtextcolor));

        ivGerenBottom.setImageResource(R.mipmap.icon_06);
        tvGerenBottom.setTextColor(this.getResources().getColor(R.color.bottomtextcolor));

        fragmShangjia.setVisibility(View.GONE);
        fragmZhibo.setVisibility(View.GONE);
        fragmShenghuo.setVisibility(View.GONE);
        fragmTongxun.setVisibility(View.GONE);
        fragmGeren.setVisibility(View.GONE);

    }


}
