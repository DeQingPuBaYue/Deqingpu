package com.bayue.live.deqingpu.ui.geren.pouch;

import android.os.Bundle;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.view.TopActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class PouchRecordDetailActivity extends BaseActivity {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.txtMyBlance)
    TextView txtMyBlance;
    @BindView(R.id.txtPouchOrderId)
    TextView txtPouchOrderId;
    @BindView(R.id.txtPouchPaymentDetail)
    TextView txtPouchPaymentDetail;
    @BindView(R.id.txtPouchPayMethod)
    TextView txtPouchPayMethod;

    @Override
    protected int getViewId() {
        return R.layout.ac_record_detail;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.title_trans_detail));
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
}
