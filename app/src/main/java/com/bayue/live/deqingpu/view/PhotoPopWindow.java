package com.bayue.live.deqingpu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.data.Code;
import com.bayue.live.deqingpu.data.ParamKey;
import com.bayue.live.deqingpu.ui.certification.CertificationActivity;
import com.bayue.live.deqingpu.utils.Envir;
import com.bayue.live.deqingpu.utils.ImageByAndroid;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.crop.Crop;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

import static com.bayue.live.deqingpu.ui.certification.CertificationActivity.PHOTO_FILE_NAME;
import static com.bayue.live.deqingpu.ui.certification.CertificationActivity.PHOTO_NAME;

/**
 * Created by WuHaoyu on 2015/7/21.
 */
public class PhotoPopWindow extends PopupWindow {

    int height= 1280;
    int width= 720;
    public PhotoPopWindow(final Activity mContext, View parent, final TakePhoto takePhoto) {
//        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        File file = ImageByAndroid.getFile(ImageByAndroid.avatorpath, PHOTO_NAME + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        final Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        View view = View.inflate(mContext, R.layout.item_popupwindows, null);
//            view.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_ins));
//            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
        // R.anim.push_bottom_in_2));
        setAnimationStyle(R.style.PopupWindowAnimation);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setOutsideTouchable(true);
        setContentView(view);
        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        update();
        
        Button bt1 = (Button) view
                .findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view
                .findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view
                .findViewById(R.id.item_popupwindows_cancel);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(isCrop){
                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                }else {
                    takePhoto.onPickFromCapture(imageUri);
                }
                dismiss();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                    Crop.pickImage(mContext);
//                takePhoto.onPickMultiple(1);
                takePhoto.onPickFromGallery();
                dismiss();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });

    }
    boolean isTakePhoto = true; // 是否使用TakePhoto自带相册
    boolean isCorrect = true; // 是否纠正旋转角度
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        if(isTakePhoto){
            builder.setWithOwnGallery(true);
        }
        if(isCorrect){
            builder.setCorrectImage(true);
        }
        takePhoto.setTakePhotoOptions(builder.create());

    }
    boolean isLuban = true; // 是否用Luban压缩工具
    boolean isCompress = true; // 是否压缩
    boolean isShowProgress = true; // 是否显示压缩进度条
    boolean isRawFile = false; // 压缩如果是否保存原图
    private void configCompress(TakePhoto takePhoto){
        if(!isCompress){
            takePhoto.onEnableCompress(null,false);
            return ;
        }
        int maxSize= 102400; //文件大小
//        int width= Integer.parseInt(etCropWidth.getText().toString());
//        int height= Integer.parseInt(etHeightPx.getText().toString());
        boolean showProgressBar=isShowProgress;
        boolean enableRawFile = isRawFile;
        CompressConfig config;
        if(!isLuban){
            config=new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width>=height? width:height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        }else {
            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config=CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config,showProgressBar);


    }
    boolean isCrop = false; //是否裁剪
    boolean isFixed = true; // 是否固定高宽
    boolean isTakeCrop = true; // 是否Takephoto自带裁剪工具
    private CropOptions getCropOptions(){
        if(isCrop)return null;
        int height= 1280;
        int width= 720;

        CropOptions.Builder builder=new CropOptions.Builder();

        if(isFixed){
            builder.setAspectX(width).setAspectY(height);
        }else {
            builder.setOutputX(width).setOutputY(height);
        }
        builder.setWithOwnCrop(isTakeCrop);
        return builder.create();
    }
}