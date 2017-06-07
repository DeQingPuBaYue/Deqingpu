package com.bayue.live.deqingpu.ui.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.denglu.DengLuBean;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;

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
 * Created by Administrator on 2017/6/6.
 */

public class DengLu extends BaseActivity {
    @BindView(R.id.et_shoujihao_denglu)
    EditText etShoujihaoDenglu;
    @BindView(R.id.rl_shoujihao_denglu)
    RelativeLayout rlShoujihaoDenglu;
    @BindView(R.id.et_mima_denglu)
    EditText etMimaDenglu;
    @BindView(R.id.rl_mima_denglu)
    RelativeLayout rlMimaDenglu;
    @BindView(R.id.tv_wangji_denglu)
    TextView tvWangjiDenglu;
    @BindView(R.id.rl_denglu_denglu)
    RelativeLayout rlDengluDenglu;
    @BindView(R.id.ll_zhuce_denglu)
    LinearLayout llZhuceDenglu;

    @Override
    protected int getViewId() {
        return R.layout.denglu_activity_denglu;
    }

    String dianhua;
    String mima;
    Handler handler;
    @Override
    protected void initViews() {
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                DengLuBean dengLuBean= (DengLuBean) msg.obj;
                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,dengLuBean.getToken());
                finish();
            }
        };
        Intent intent=getIntent();
        dianhua=intent.getStringExtra("dianhua");
        mima =intent.getStringExtra("mima");
        Log.e("$$$$$$$$$",dianhua+"____"+mima);
        if(dianhua!=null&&mima!=null){
            etShoujihaoDenglu.setText(dianhua);
            etMimaDenglu.setText(mima);
        }
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

    @OnClick({R.id.rl_shoujihao_denglu, R.id.rl_mima_denglu, R.id.tv_wangji_denglu, R.id.rl_denglu_denglu, R.id.ll_zhuce_denglu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_shoujihao_denglu:
                break;
            case R.id.rl_mima_denglu:
                break;
            case R.id.tv_wangji_denglu:

                wangJi();

                break;
            case R.id.rl_denglu_denglu:

                dengLu();
                break;
            case R.id.ll_zhuce_denglu:
                startActivity(new Intent(this,ZhuCe.class));
                finish();
                break;
        }
    }
    private void dengLu(){
        String dianhua=etShoujihaoDenglu.getText().toString();
        String mima=etMimaDenglu.getText().toString();
        if(dianhua.isEmpty() || mima.isEmpty()){
            Toast.makeText(getApplicationContext(),"电话和密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(dianhua.length()!=11){
            Toast.makeText(getApplicationContext(),"电话号码位数错误",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mima.length()<=5){
            Toast.makeText(getApplicationContext(),"密码长度最少6位",Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("phone", dianhua)
                .add("password",mima)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.120/bayue/deqingpu/public/api/login/signin")
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
                    final DengLuBean dengLuBean= gson.fromJson(msg,DengLuBean.class);
                    Log.e(">>>>","denglu登录11");
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (dengLuBean.getCode()==200){
                                Log.e(">>>>","denglu登录22");
//                                DensityUtil.showToast(DengLu.this,dengLuBean.getData());
                                Preferences.saveString(getApplicationContext(),Preferences.TOKEN,dengLuBean.getToken());
                                finish();



                            }else {
//                                DensityUtil.showToast(UserInfoActivity.this,userInfoAvatarBean.getInfo());
                            }
                            DensityUtil.showToast(DengLu.this,dengLuBean.getData());
                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(DengLu.this,response.message());
                        }
                    });
                }
            }
        });
    }
    private  void wangJi(){


        startActivity(new Intent(this,Zhaohuei.class));
        finish();



    }

}
