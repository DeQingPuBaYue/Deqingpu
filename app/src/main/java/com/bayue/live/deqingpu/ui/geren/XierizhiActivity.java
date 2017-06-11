package com.bayue.live.deqingpu.ui.geren;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.tangxiaolv.telegramgallery.GalleryActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);
            }
        });
        rlBukejianXie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
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
                break;
            case R.id.iv_tupian_xie:
                setHpoto();
                break;
            case R.id.iv_gongkai_xie:
                ivGongkaiXie.setImageResource(R.drawable.icon_38);
                ivBukejianXie.setImageResource(R.drawable.icon_39);

                break;
            case R.id.iv_bukejian_xie:
                ivGongkaiXie.setImageResource(R.drawable.icon_39);
                ivBukejianXie.setImageResource(R.drawable.icon_38);
                break;
            case R.id.rl_sheikejian_xie:
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

            }
        });
        LinearLayout ce= (LinearLayout) layout.findViewById(R.id.ll_xiangce_diadog);
        ce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryActivity.openActivity(XierizhiActivity.this, true,12);
                dialog.dismiss();
            }
        });




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       /*if(resultCode!=12){
        Log.e(">>>>>>data>>>>>",data+"");
           return;

       };*/
        List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inPreferredConfig = Bitmap.Config.ARGB_4444;
        opts.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0), opts);
        ivTupianXie.setImageBitmap(bitmap);




//        Log.e(">>>>>>data>>>>>",data+"");

    }


}
