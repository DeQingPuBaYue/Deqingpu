package com.bayue.live.deqingpu.ui.geren;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.KejianYonghuAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by VULCAN on 2017/6/11.
 */

public class KejianActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.tv_queding_title)
    TextView tvQuedingTitle;
    @BindView(R.id.ll_soso_quanzi)
    LinearLayout llSosoQuanzi;
    @BindView(R.id.ed_sosotext_quanzi)
    EditText edSosotextQuanzi;
    @BindView(R.id.lv_yonghu_quanzi)
    ListView lvYonghuQuanzi;
    KejianYonghuAdapter adapter;

    @Override
    protected int getViewId() {
        return R.layout.geren_activity_kejian;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("谁可以看");
        adapter=new KejianYonghuAdapter(this,null);

        lvYonghuQuanzi.setAdapter(adapter);



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

    @OnClick({R.id.ll_back_title, R.id.tv_queding_title, R.id.ll_soso_quanzi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.tv_queding_title:
                break;
            case R.id.ll_soso_quanzi:
                break;

        }
    }

}
