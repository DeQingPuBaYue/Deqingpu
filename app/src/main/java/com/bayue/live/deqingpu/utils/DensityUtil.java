package com.bayue.live.deqingpu.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class DensityUtil {
	/** 
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素) 
     */  
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }  
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }

    public static String encodeToBase64(Bitmap image){
        Bitmap b = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] bt = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(bt, Base64.DEFAULT);
        return imageEncoded;
    }

    public static void showToast(Context context, String text){
        Toast.makeText(context,text, Toast.LENGTH_LONG).show();
    }
    public static void showToast(Context context, int resId){
        Toast.makeText(context,resId, Toast.LENGTH_LONG).show();
    }
}
