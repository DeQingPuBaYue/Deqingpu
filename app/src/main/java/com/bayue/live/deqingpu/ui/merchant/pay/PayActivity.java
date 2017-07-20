package com.bayue.live.deqingpu.ui.merchant.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/6/15.
 */

public class PayActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.iv_bianji_title)
    ImageView ivBianjiTitle;
    @BindView(R.id.ed_jine_pay)
    EditText edJinePay;
    @BindView(R.id.iv_gr_quanzi)
    ImageView ivGrQuanzi;
    @BindView(R.id.rl_quanzi_geren)
    RelativeLayout rlQuanziGeren;
    @BindView(R.id.tv_shifu_pay)
    TextView tvShifuPay;
    @BindView(R.id.bt_pay_pay)
    Button btPayPay;

    public static Activity instance;
    @Override
    protected int getViewId() {
        return R.layout.pay_activity;
    }

    @Override
    protected void initViews() {
        instance = this;
        tvTitletextTitle.setText("买单");
        ivBianjiTitle.setVisibility(View.INVISIBLE);

        edJinePay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double d;
                String ss=s.toString();
                        if(ss.isEmpty()){
                            d=0.0;
                            tvShifuPay.setText("0");
                            return;
                        }


                 d=Double.valueOf(s.toString());
                DecimalFormat format = new DecimalFormat("#.##");

                Log.e(">>>>>>",d+"");
                tvShifuPay.setText(format.format(d));

            }

            @Override
            public void afterTextChanged(Editable s) {

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

    @OnClick({R.id.ll_back_title, R.id.ed_jine_pay, R.id.bt_pay_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.ed_jine_pay:
                break;
            case R.id.bt_pay_pay:
                String amount = edJinePay.getText().toString();
                if (Guard.isNullOrEmpty(amount)){
                    ToastUtils.showShortToast("请输入消费金额");
                    return;
                }
                startActivity(new Intent(PayActivity.this,ConfirmPayActivity.class)
                        .putExtra("goods_amount", amount)
                        .putExtra("order_sn", "")
                );
                break;
        }
    }
}
