package com.bayue.live.deqingpu.ui.denglu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.denglu.YanZhengMa;
import com.bayue.live.deqingpu.entity.denglu.ZhuCeBean;
import com.bayue.live.deqingpu.http.API;
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
 * Created by Administrator on 2017/6/7.
 */

public class Zhaohuei extends BaseActivity {
    @BindView(R.id.ed_xiayibu_zhaohuei)
    TextView edXiayibuZhaohuei;
    @BindView(R.id.ed_shoujihao_zhaohuei)
    EditText edShoujihaoZhaohuei;
    @BindView(R.id.ed_yanzheng_zhaohuei)
    EditText edYanzhengZhaohuei;
    @BindView(R.id.ed_fasong_zhaohuei)
    TextView edFasongZhaohuei;
    @BindView(R.id.ed_mima_zhaohuei)
    EditText edMimaZhaohuei;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.ed_mima2_zhaohuei)
    EditText edMima2Zhaohuei;
    @BindView(R.id.ll_back_zhaohuei)
    LinearLayout llBackZhaohuei;

    @Override
    protected int getViewId() {
        return R.layout.denglu_activity_zhaohuei;
    }

    @Override
    protected void initViews() {
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 2) {

                    int i = (int) msg.obj;

                    if (edFasongZhaohuei != null) {
                        edFasongZhaohuei.setText(i + " 秒");
                    }
                    if (i == 1) {
                        if (edFasongZhaohuei != null) {

                            edFasongZhaohuei.setText("点击发送");
                            edFasongZhaohuei.setClickable(true);
                        }
                    }
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

    @OnClick({R.id.ed_xiayibu_zhaohuei, R.id.ed_fasong_zhaohuei})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ed_xiayibu_zhaohuei:
                xiaoYiBu();
                break;
            case R.id.ed_fasong_zhaohuei:
                String shouji = edShoujihaoZhaohuei.getText().toString();

                if (shouji.isEmpty() || shouji.length() <= 10 || shouji == null) {
                    Toast.makeText(getApplicationContext(), "手机号错误", Toast.LENGTH_SHORT).show();
                    return;
                }
                edFasongZhaohuei.setClickable(false);
                dumiao();
                fasong();
                break;
        }
    }

    Handler handler;
    String sms_token;

    void dumiao() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int j = 60; j > 0; j--) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message m = new Message();
                    m.what = 2;
                    m.obj = j;
                    handler.sendMessage(m);
                }
            }

        }).start();
    }

    protected void fasong() {

        String shoujihao = edShoujihaoZhaohuei.getText().toString();
        RequestBody body = new FormBody.Builder()
                .add("apiversion", "v.1.0")
                .add("safecode", "BaYue.deqingpu")
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
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final YanZhengMa yanZhengMa = gson.fromJson(response.body().string(), YanZhengMa.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (yanZhengMa.getCode() == 200) {
                                Toast.makeText(Zhaohuei.this, "已发送验证码到你的手机", Toast.LENGTH_SHORT).show();

                                sms_token = yanZhengMa.getSms_token();
                                Log.e("验证码", yanZhengMa.getSms_code() + "");

                            } else {
                                DensityUtil.showToast(Zhaohuei.this, yanZhengMa.getData());
                            }
                        }
                    });
                } else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(Zhaohuei.this, response.message());
                        }
                    });
                }
            }
        });


    }

    private void xiaoYiBu() {
        String shoujihao = edShoujihaoZhaohuei.getText().toString();
        if (shoujihao.isEmpty()) {
            Toast.makeText(getApplicationContext(), "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shoujihao.isEmpty()) {
            Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (shoujihao.isEmpty()) {
            Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!edMimaZhaohuei.getText().toString().equals(edMima2Zhaohuei.getText()
                .toString())) {
            Toast.makeText(getApplicationContext(), "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("apiversion", "v.1.0")
                .add("safecode", "BaYue.deqingpu")
                .add("phone", shoujihao)
                .add("password", edMimaZhaohuei.getText().toString())
                .add("reppassword", edMima2Zhaohuei.getText().toString())
                .add("code", edYanzhengZhaohuei.getText().toString())
                .add("sms_token", sms_token)
                .build();
        Request request = new Request.Builder()
                .url(API.baseUrl+API.ZHAOHUIE)
                .post(body)
                .build();

        OKHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
//                String msg = response.body().string();
                if (response.code() == 200) {
                    Gson gson = new Gson();
                    final ZhuCeBean zhuCeBean = gson.fromJson(response.body().string(), ZhuCeBean.class);
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (zhuCeBean.getCode() == 200) {
                                DensityUtil.showToast(Zhaohuei.this,zhuCeBean.getData());
                                Log.e("@@@@@@", zhuCeBean.getData());
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(Zhaohuei.this, DengLu.class);
                                intent.putExtra("dianhua", edShoujihaoZhaohuei.getText().toString());
                                intent.putExtra("mima", edMimaZhaohuei.getText().toString());
                                startActivity(intent);

                                Zhaohuei.this.finish();


                            } else {
                                DensityUtil.showToast(Zhaohuei.this, zhuCeBean.getData());
                            }
                        }
                    });
                } else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(Zhaohuei.this, response.message());
                        }
                    });
                }
            }
        });


    }

    @OnClick(R.id.ll_back_zhaohuei)
    public void onViewClicked() {
//        startActivity(new Intent(this,DengLu.class));
        finish();

    }
}
