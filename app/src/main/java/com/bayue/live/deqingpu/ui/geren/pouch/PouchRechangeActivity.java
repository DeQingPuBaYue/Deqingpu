package com.bayue.live.deqingpu.ui.geren.pouch;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.view.TopActionBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class PouchRechangeActivity extends BaseActivity {
    String TAG = "PouchRechange";
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.linBindAlipay)
    LinearLayout linBindAlipay;
    @BindView(R.id.linBindWeChat)
    LinearLayout linBindWeChat;
    @BindView(R.id.edtPouthRechangeMoney)
    EditText edtPouthRechangeMoney;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    @BindView(R.id.txtPouchAlipayUnBindTip)
    TextView txtPouchAlipayUnBindTip;
    @BindView(R.id.ivPouchSelectAlipay)
    ImageView ivPouchSelectAlipay;
    @BindView(R.id.txtPouchWeChatUnBindTip)
    TextView txtPouchWeChatUnBindTip;
    @BindView(R.id.ivPouchSelectWeChat)
    ImageView ivPouchSelectWeChat;
    @BindView(R.id.txtPouchWithDrawMoney)
    TextView txtPouchWithDrawMoney;

    @Override
    protected int getViewId() {
        return R.layout.ac_rechange;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.txt_rechange));
        reset();
    }

    @OnClick({R.id.linBindAlipay, R.id.linBindWeChat, R.id.btnConmon})
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.linBindAlipay:
                ivPouchSelectAlipay.setImageResource(R.mipmap.icon_52);
                ivPouchSelectWeChat.setImageResource(R.mipmap.icon_51);
                break;
            case R.id.linBindWeChat:
                ivPouchSelectAlipay.setImageResource(R.mipmap.icon_51);
                ivPouchSelectWeChat.setImageResource(R.mipmap.icon_52);
                break;
            case R.id.btnConmon:
                if (Guard.isNullOrEmpty(edtPouthRechangeMoney.getText().toString())){
                    ToastUtils.showLongToast(getString(R.string.hint_money));
                    return;
                }
                break;
        }
    }

    @Override
    protected void setTheme() {

    }

    void reset(){
        ivPouchSelectAlipay.setImageResource(R.mipmap.icon_51);
        ivPouchSelectWeChat.setImageResource(R.mipmap.icon_51);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
