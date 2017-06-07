package com.bayue.live.deqingpu.ui.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.denglu.YanZhengMa;
import com.bayue.live.deqingpu.entity.denglu.ZhuCeBean;
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

public class ZhuCe extends BaseActivity {
    @BindView(R.id.tv_xiayibu_zhuce)
    TextView tvXiayibuZhuce;
    @BindView(R.id.ed_shoujihao_zhuce)
    EditText edShoujihaoZhuce;
    @BindView(R.id.ed_yanzhengma_zhuce)
    EditText edYanzhengmaZhuce;
    @BindView(R.id.tv_fasong_zhuce)
    TextView tvFasongZhuce;
    @BindView(R.id.ed_mima_zhuce)
    EditText edMimaZhuce;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ed_mima2_zhuce)
    EditText edMima2Zhuce;
    @BindView(R.id.ll_denglu_zhuce)
    LinearLayout llDengluZhuce;

    Handler handler;

    @Override
    protected int getViewId() {
        return R.layout.denglu_activity_zhuce;
    }

    @Override
    protected void initViews() {
        handler=new Handler(){

            @Override
            public void handleMessage(Message msg) {
                if(msg.what==2){

                    i= (int) msg.obj;
                    tvFasongZhuce.setText(i+" 秒");
                    if(i==60){
                        tvFasongZhuce.setText("点击发送");
                        tvFasongZhuce.setClickable(true);
                    }
                }
                if(msg.what==1){

                    Intent intent=new Intent(ZhuCe.this,DengLu.class);
                    intent.putExtra("dianhua",edShoujihaoZhuce.getText().toString());
                    intent.putExtra("mima",edMimaZhuce.getText().toString());
                    startActivity(intent);
                }
            }
        };
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

    @OnClick({R.id.tv_xiayibu_zhuce, R.id.ed_shoujihao_zhuce, R.id.ed_yanzhengma_zhuce, R.id.tv_fasong_zhuce, R.id.ed_mima_zhuce, R.id.imageView, R.id.ed_mima2_zhuce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_xiayibu_zhuce:
                break;
            case R.id.ed_shoujihao_zhuce:
                break;
            case R.id.ed_yanzhengma_zhuce:
                break;
            case R.id.tv_fasong_zhuce:
                tvFasongZhuce.setClickable(false);
                dumiao();
                fasong();

                break;
            case R.id.ed_mima_zhuce:
                break;
            case R.id.imageView:
                break;
            case R.id.ed_mima2_zhuce:
                break;
        }
    }
    String sms_token;
    String shoujihao;
    protected void fasong(){

        shoujihao =edShoujihaoZhuce.getText().toString();
        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("phone", shoujihao)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.120/bayue/deqingpu/public/api/sms/get_code")
                .post(body)
                .build();

        OKHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                String msg = response.body().string();
                if (response.code() == 200){
                    Gson gson = new Gson();
                    final YanZhengMa yanZhengMa= gson.fromJson(response.body().string(),YanZhengMa.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (yanZhengMa.getCode()==200){

                                sms_token=yanZhengMa.getSms_token();

                            }else {
//                                DensityUtil.showToast(UserInfoActivity.this,userInfoAvatarBean.getInfo());
                            }
                        }
                    });
                }else {
//                    ToolKit.runOnMainThreadSync(new Runnable() {
//                        @Override
//                        public void run() {
//                            DensityUtil.showToast(UserInfoActivity.this,response.message());
//                        }
//                    });
                }
            }
        });


    }
    int i=0;
    private void dumiao(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (i<60){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message m=new Message();
                    m.what=2;
                    m.obj=i;
                    handler.sendMessage(m);
                }
            }
        }).start();
    }
    private void xiaoYiBu(){
        shoujihao =edShoujihaoZhuce.getText().toString();
        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("phone", shoujihao)
                .add("password",edMimaZhuce.getText().toString())
                .add("reppassword",edMima2Zhuce.getText().toString())
                .add("code",edYanzhengmaZhuce.getText().toString())
                .add("sms_token",sms_token)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.1.120/bayue/deqingpu/public/api/login/register")
                .post(body)
                .build();

        OKHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                String msg = response.body().string();
                if (response.code() == 200){
                    Gson gson = new Gson();
                    final ZhuCeBean zhuCeBean= gson.fromJson(response.body().string(),ZhuCeBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (zhuCeBean.getCode()==200){

                                Message mm=new Message();
                                mm.what=1;
                                mm.obj=zhuCeBean;
                                handler.sendMessage(mm);


                            }else {
//                                DensityUtil.showToast(UserInfoActivity.this,userInfoAvatarBean.getInfo());
                            }
                        }
                    });
                }else {
//                    ToolKit.runOnMainThreadSync(new Runnable() {
//                        @Override
//                        public void run() {
//                            DensityUtil.showToast(UserInfoActivity.this,response.message());
//                        }
//                    });
                }
            }
        });





    }
}
