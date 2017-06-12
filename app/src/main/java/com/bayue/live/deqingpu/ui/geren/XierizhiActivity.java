package com.bayue.live.deqingpu.ui.geren;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.entity.denglu.DengLuBean;
import com.bayue.live.deqingpu.entity.geren.FabuBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.google.gson.Gson;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

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

    String desc="";//消息
    String pic="";//图片
    String type="";//可见类型
    String value="";//好友id；



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
                empty();
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);
                type="1";
            }
        });
        rlBukejianXie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                empty();
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
                type="2";

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
                tiJiao();
                break;
            case R.id.iv_tupian_xie:
                setHpoto();
                break;
            case R.id.iv_gongkai_xie:
                empty();
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);
                type="1";

                break;
            case R.id.iv_bukejian_xie:
                empty();
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
                type="2";
                break;
            case R.id.rl_sheikejian_xie:
                empty();
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
                startActivity(new Intent(this,KejianActivity.class));
                break;
        }
    }

    private void setHpoto(){

        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_hpoto, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(XierizhiActivity.this).create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes() ;
        Display display =getWindowManager().getDefaultDisplay();
        params.width =(int) (display.getWidth()*0.5);

        //使用这种方式更改了dialog的框宽
        dialog.getWindow().setAttributes(params);
        dialog.getWindow().setContentView(layout);

        LinearLayout pai= (LinearLayout) layout.findViewById(R.id.ll_paizhao_diadog);
        pai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);
                dialog.dismiss();
            }
        });
        LinearLayout ce= (LinearLayout) layout.findViewById(R.id.ll_xiangce_diadog);
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(XierizhiActivity.this, true,2);
                dialog.dismiss();
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.e("resultCode===>>>>",resultCode+"");
        Log.e("requestCode===>>>>",requestCode+"");
        if(requestCode==1){
            Bundle bundle=data.getExtras();
            Bitmap bitmap= (Bitmap) bundle.get("data");
            ivTupianXie.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
            pic = new String(encode);
            Log.e("base64",pic);

        }else {

        List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
        opts.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0), opts);
        ivTupianXie.setImageBitmap(bitmap);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
            pic = new String(encode);
            Log.e("base64",pic);
//            mTvShow.setText(encodeString);
        }




//        Log.e(">>>>>>data>>>>>",data+"");

    }
    private  void tiJiao(){
        if(!getValue()){
            return;
        }

        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("desc",desc)
                .add("pic",pic)
                .add("type",type)
                .add("value",value)
                .add("token",Preferences.getString(this,Preferences.TOKEN))
                .build();
        Request request = new Request.Builder()
                .url(API.baseUrl+API.QUANZI_FABU)
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
                    final FabuBean fabuBean= gson.fromJson(msg,FabuBean.class);

                    Log.e(">>>>",fabuBean.getCode()+"");
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (fabuBean.getCode()==200){
                                Log.e(">>>>","发布——————");
                                DensityUtil.showToast(XierizhiActivity.this,fabuBean.getData());
                                finish();



                            }else {
                                DensityUtil.showToast(XierizhiActivity.this,fabuBean.getMsg());
                                Log.e(">>>>",fabuBean.getMsg());
                            }

                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(XierizhiActivity.this,response.message());
                        }
                    });
                }
            }
        });

    }


    private boolean getValue(){
        desc=edShuoshuoXie.getText().toString().trim();

        if(desc==null||desc.isEmpty()&&pic==null||pic.isEmpty()){
            Toast.makeText(this,"说点什么或发张图片吧！",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(type==null){
            Toast.makeText(this,"是否公开？",Toast.LENGTH_SHORT).show();
            return false;

        }

        return true;
    }
    private void empty(){
        type="";



    }

}
