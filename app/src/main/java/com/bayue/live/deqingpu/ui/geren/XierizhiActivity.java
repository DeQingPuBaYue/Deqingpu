package com.bayue.live.deqingpu.ui.geren;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/9.
 */

public class XierizhiActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.iv_bianji_title)
    ImageView ivBianjiTitle;
    @BindView(R.id.ed_shuoshuo_xie)
    EditText edShuoshuoXie;
    @BindView(R.id.iv_tupian_xie)
    ImageView ivTupianXie;
    @BindView(R.id.iv_gongkai_xie)
    ImageView ivGongkaiXie;
    @BindView(R.id.iv_bukejian_xie)
    ImageView ivBukejianXie;
    @BindView(R.id.rl_sheikejian_xie)
    RelativeLayout rlSheikejianXie;
    @BindView(R.id.rl_gongkai_xie)
    RelativeLayout rlGongkaiXie;
    @BindView(R.id.rl_bukejian_xie)
    RelativeLayout rlBukejianXie;

    @Override
    protected int getViewId() {
        return R.layout.geren_activity_xierizhi;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("写日志");
        ivBianjiTitle.setImageResource(R.drawable.icon_122);
        rlGongkaiXie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);
            }
        });
        rlBukejianXie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
            }
        });

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

    @OnClick({R.id.ll_back_title, R.id.iv_bianji_title, R.id.iv_tupian_xie, R.id.iv_gongkai_xie, R.id.iv_bukejian_xie, R.id.rl_sheikejian_xie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.iv_bianji_title:
                break;
            case R.id.iv_tupian_xie:
                break;
            case R.id.iv_gongkai_xie:
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);

                break;
            case R.id.iv_bukejian_xie:
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
                break;
            case R.id.rl_sheikejian_xie:
                break;
        }
    }

    private void dfs() {
    }


}
