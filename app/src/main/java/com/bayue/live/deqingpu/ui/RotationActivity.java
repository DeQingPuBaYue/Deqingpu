package com.bayue.live.deqingpu.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.utils.ImageByAndroid;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/8/26.
 */
public class RotationActivity extends BaseActivity {

    Bitmap bitmap, temBitmap;
    @BindView(R.id.img_rotation)
    ImageView imgRotation;
    @BindView(R.id.btnLeftRota)
    ImageView btnLeftRota;
    @BindView(R.id.btnRightRota)
    ImageView btnRightRota;
    @BindView(R.id.btnConfirm)
    Button btnConfirm;
    @BindView(R.id.Lin_rotation)
    LinearLayout LinRotation;

    @Override
    protected int getViewId() {
        return R.layout.ac_rotation;
    }

    @Override
    protected void initViews() {
        bitmap = ImageByAndroid.getmInsertPicture();
        if (bitmap != null) {
            temBitmap = bitmap;
            imgRotation.setImageBitmap(bitmap);
        }
    }

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btnLeftRota)
    void setBtnLeftRota() {
        temBitmap = ImageByAndroid.rotateBitmapByDegree(temBitmap, 90);
        imgRotation.setImageBitmap(temBitmap);
    }

    @OnClick(R.id.btnRightRota)
    void setBtnRightRota() {
        temBitmap = ImageByAndroid.rotateBitmapByDegree(temBitmap, -90);
        imgRotation.setImageBitmap(temBitmap);
    }

    @OnClick(R.id.btnConfirm)
    void setBtnConfirm() {
        Intent intent = new Intent();
        ImageByAndroid.setmInsertPicture(temBitmap);
        setResult(RESULT_OK, intent);
        finish();
    }

}
