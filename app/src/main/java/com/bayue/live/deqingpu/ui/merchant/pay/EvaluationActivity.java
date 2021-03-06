package com.bayue.live.deqingpu.ui.merchant.pay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.BaseSubscriber;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.geren.AddEvaluationBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.geren.XierizhiActivity;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.bayue.live.deqingpu.utils.Tracer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tamic.novate.Throwable;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/16.
 */

public class EvaluationActivity extends BaseActivity {
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.tv_rigthtext_title)
    TextView tvRigthtextTitle;
    @BindView(R.id.ll_rigthtext_title)
    LinearLayout llRigthtextTitle;
    @BindView(R.id.rateComment)
    RatingBar rateComment;
    @BindView(R.id.rl_yinliang_confirm)
    RelativeLayout rlYinliangConfirm;
    @BindView(R.id.ed_content_eval)
    EditText edContentEval;
    @BindView(R.id.iv_photo_eval1)
    ImageView ivPhotoEval1;
    @BindView(R.id.iv_photo_eval2)
    ImageView ivPhotoEval2;
    @BindView(R.id.iv_photo_eval3)
    ImageView ivPhotoEval3;
    @BindView(R.id.iv_photo_eval)
    ImageView ivPhotoEval;
    String s="1";
    ArrayList<String > lists=new ArrayList<>();

    String img1="",img2="",img3="";
    int comment_type, id_value, f;
    String action, content;
    private String TAG = "EvaluationActivity";

    @Override
    protected int getViewId() {
        return R.layout.pay_evaluation_activity;
    }

    @Override
    protected void initViews(){
        tvTitletextTitle.setText("评价");
        tvRigthtextTitle.setText("下一步");
        action = getIntent().getStringExtra("action");
        comment_type = getIntent().getIntExtra("comment_type", -1);
        id_value = getIntent().getIntExtra("id_value", -1);
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

    @OnClick({R.id.ll_back_title, R.id.tv_titletext_title, R.id.tv_rigthtext_title, R.id.ll_rigthtext_title, R.id.iv_photo_eval})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.tv_titletext_title:
                break;
            case R.id.ll_rigthtext_title:
                f= (int) rateComment.getRating();
                content=edContentEval.getText().toString();
                if(f<=0){
                    ToastUtils.showShortToast("请给予星级评价");
                    return;
                }
                if(content.isEmpty()||content.equals(null)){
                    ToastUtils.showShortToast("说点什么吧！！");
                    return;
                }
                    Map<String ,Object> map = Constants.getMap(baseContext);
                    map.put("comment_type",comment_type);
                    map.put("content",content);
                    map.put("comment_rank",f+"");
                    map.put("id_value",id_value+"");
                    map.put("comment_img1",img1);
                    map.put("comment_img2",img2);
                    map.put("comment_img3",img3);
                    beginGet(API.Merchant.COMMENT_ADD, map, 0);
                break;
            case R.id.iv_photo_eval:
                setGong();
                setHpoto();
                break;
        }
    }
    private void setHpoto(){

        LayoutInflater inflaterDl = LayoutInflater.from(this);
        LinearLayout layout = (LinearLayout)inflaterDl.inflate(R.layout.dialog_hpoto, null );

        //对话框
        final Dialog dialog = new AlertDialog.Builder(EvaluationActivity.this).create();
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
                GalleryActivity.openActivity(EvaluationActivity.this, false,3,2);
                dialog.dismiss();
            }
        });
    }
    private void setGong(){
        ivPhotoEval1.setVisibility(View.GONE);
        ivPhotoEval2.setVisibility(View.GONE);
        ivPhotoEval3.setVisibility(View.GONE);


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("resultCode===>>>>",resultCode+"");
        Log.e("requestCode===>>>>",requestCode+"");
        if(requestCode==1){
            if(data==null){
                return;
            }
            Bundle bundle=data.getExtras();
            if(bundle==null){
                return;
            }
            Bitmap bitmap= (Bitmap) bundle.get("data");
            ivPhotoEval1.setImageBitmap(bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] bytes = baos.toByteArray();

            //base64 encode
            byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
           lists.add(new String(encode));
            Log.e("base64",lists.get(0));
//            JsonObject jsonObj=new JsonObject();


        }else {
            if(data==null){
                return;
            }
            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            List<Bitmap> bitmaps=new ArrayList<>();
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
            opts.inSampleSize = 4;
            Log.e("几张图片？？",photos.size()+"");



            for (int i = 0; i < photos.size(); i++) {

                switch (i){
                    case 0:
                        Bitmap bitmap1=BitmapFactory.decodeFile(photos.get(i),opts);
                        bitmaps.add(bitmap1);

                        ivPhotoEval1.setImageBitmap(bitmap1);
                        ivPhotoEval1.setVisibility(View.VISIBLE);

                        Log.e("头片的大小===",bitmap1.getRowBytes() * bitmap1.getHeight()+"");

                        String pic1;
                        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
                        bitmap1.compress(Bitmap.CompressFormat.PNG, 100, baos1);
                        byte[] bytes1 = baos1.toByteArray();

                        //base64 encode
                        byte[] encode1 = Base64.encode(bytes1, Base64.DEFAULT);
                        img1 = new String(encode1);
                        Log.e("base64",img1);


                        break;
                    case 1:
                        Bitmap bitmap2=BitmapFactory.decodeFile(photos.get(i),opts);
                        bitmaps.add(bitmap2);
                        ivPhotoEval2.setImageBitmap(bitmap2);
                        ivPhotoEval2.setVisibility(View.VISIBLE);

                        String pic2;
                        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, baos2);
                        byte[] bytes2 = baos2.toByteArray();

                        //base64 encode
                        byte[] encode2 = Base64.encode(bytes2, Base64.DEFAULT);
                        img2 = new String(encode2);
                        Log.e("base64",img2);

                        break;
                    case 2:
                        Bitmap bitmap3=BitmapFactory.decodeFile(photos.get(i),opts);
                        bitmaps.add(bitmap3);
                        ivPhotoEval3.setImageBitmap(bitmap3);
                        ivPhotoEval3.setVisibility(View.VISIBLE);

                        String pic3;
                        ByteArrayOutputStream baos3 = new ByteArrayOutputStream();
                        bitmap3.compress(Bitmap.CompressFormat.PNG, 100, baos3);
                        byte[] bytes3 = baos3.toByteArray();

                        //base64 encode
                        byte[] encode3 = Base64.encode(bytes3, Base64.DEFAULT);
                        img3 = new String(encode3);
                        Log.e("base64",img3);

                        break;
                }

            }

        }


    }

    private void beginGet(final String url, final Map<String, Object> map, final int status) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataFromNet(url, map, status);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int status) {
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new com.tamic.novate.BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String jstr = null;
                try {
                    jstr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tracer.e(TAG, jstr);
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                if (status == 0) {
                    Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                    if (r.getCode() == Constants.CODE_OK){
                        ToastUtils.showLongToast(r.getData());
                    }else {
                        ToastUtils.showLongToast(r.getMsg());
                    }

                } else if (status == 1) {


                }
            }
        });

    }


}
