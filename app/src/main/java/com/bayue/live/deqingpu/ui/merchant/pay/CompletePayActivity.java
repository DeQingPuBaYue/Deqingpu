package com.bayue.live.deqingpu.ui.merchant.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.ui.geren.CartConfirmActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/16.
 */

public class CompletePayActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.iv_bianji_title)
    ImageView ivBianjiTitle;
    @BindView(R.id.tv_merchantsname_complete)
    TextView tvMerchantsnameComplete;
    @BindView(R.id.tv_money_complete)
    TextView tvMoneyComplete;
    @BindView(R.id.but_complete_complete)
    Button butCompleteComplete;
    @BindView(R.id.but_evaluation_complete)
    Button butEvaluationComplete;
    @BindView(R.id.tv_problem_complete)
    TextView tvProblemComplete;

    int id_value,comment_type;
    String goods_amount;
    @Override
    protected int getViewId() {
        return R.layout.pay_complete_activity;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("完成支付");
        ivBianjiTitle.setVisibility(View.INVISIBLE);
        goods_amount = getIntent().getStringExtra("goods_amount");
        tvMoneyComplete.setText(String.format(getString(R.string.tv_order_payed),goods_amount));
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

    @OnClick({R.id.ll_back_title, R.id.but_complete_complete, R.id.but_evaluation_complete, R.id.tv_problem_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.but_complete_complete:
                finish();
                if (ConfirmPayActivity.instance!=null){
                    if (!ConfirmPayActivity.instance.isFinishing()) {
                        ConfirmPayActivity.instance.finish();
                    }
                }
                if (CartConfirmActivity.instance != null){
                    if (!CartConfirmActivity.instance.isFinishing()) {
                        CartConfirmActivity.instance.finish();
                    }
                }
                if (PayActivity.instance!=null) {
                    if (!PayActivity.instance.isFinishing()) {
                        PayActivity.instance.finish();
                    }
                }
                break;
            case R.id.but_evaluation_complete:
                startActivity(new Intent(CompletePayActivity.this,EvaluationActivity.class)
                        .putExtra("comment_type", "")
                        .putExtra("id_value", "")
                );
                break;
            case R.id.tv_problem_complete:
                break;
        }
    }
}
