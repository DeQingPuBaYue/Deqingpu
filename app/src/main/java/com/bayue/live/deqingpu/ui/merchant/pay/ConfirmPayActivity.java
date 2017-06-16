package com.bayue.live.deqingpu.ui.merchant.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * Created by Administrator on 2017/6/15.
 */

public class ConfirmPayActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.iv_bianji_title)
    ImageView ivBianjiTitle;
    @BindView(R.id.tv_toupian_confirmpay)
    ImageView tvToupianConfirmpay;
    @BindView(R.id.tv_jine_confirmpay)
    TextView tvJineConfirmpay;
    @BindView(R.id.tv_dingdan_confirmpay)
    TextView tvDingdanConfirmpay;
    @BindView(R.id.iv_gr_hebao)
    ImageView ivGrHebao;
    @BindView(R.id.iv_yinliang_confirm)
    ImageView ivYinliangConfirm;
    @BindView(R.id.rl_yinliang_confirm)
    RelativeLayout rlYinliangConfirm;
    @BindView(R.id.iv_yl_confirm)
    ImageView ivYlConfirm;
    @BindView(R.id.iv_weixin_confirm)
    ImageView ivWeixinConfirm;
    @BindView(R.id.rl_weixin_confirm)
    RelativeLayout rlWeixinConfirm;
    @BindView(R.id.iv_gr_dizhi)
    ImageView ivGrDizhi;
    @BindView(R.id.iv_zhifubao_confirm)
    ImageView ivZhifubaoConfirm;
    @BindView(R.id.rl_zhifubao_confirm)
    RelativeLayout rlZhifubaoConfirm;
    @BindView(R.id.tv_zhifujine_confirm)
    TextView tvZhifujineConfirm;
    @BindView(R.id.ll_pay_confirm)
    LinearLayout llPayConfirm;

    @Override
    protected int getViewId() {
        return R.layout.pay_confirm_activity;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("确认支付");
        ivBianjiTitle.setVisibility(View.INVISIBLE);
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

    @OnClick({R.id.ll_back_title, R.id.rl_yinliang_confirm, R.id.rl_weixin_confirm, R.id.rl_zhifubao_confirm, R.id.ll_pay_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.rl_yinliang_confirm:
                setImage();
                ivYinliangConfirm.setImageResource(R.drawable.icon_52);

                break;
            case R.id.rl_weixin_confirm:
                setImage();
                ivWeixinConfirm.setImageResource(R.drawable.icon_52);

                break;
            case R.id.rl_zhifubao_confirm:
                setImage();
                ivZhifubaoConfirm.setImageResource(R.drawable.icon_52);

                break;
            case R.id.ll_pay_confirm:
                startActivity(new Intent(ConfirmPayActivity.this,CompletePayActivity.class));
                break;
        }
    }
    //圆点恢复
    private void setImage(){
        ivYinliangConfirm.setImageResource(R.drawable.icon_51);
        ivWeixinConfirm.setImageResource(R.drawable.icon_51);
        ivZhifubaoConfirm.setImageResource(R.drawable.icon_51);




    }
}
