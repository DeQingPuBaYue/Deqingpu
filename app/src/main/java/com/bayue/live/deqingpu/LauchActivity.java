package com.bayue.live.deqingpu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.bayue.live.deqingpu.base.BasePermissActivity;

/**
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class LauchActivity extends BasePermissActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        //判断系统版本是否是6.0
        if (isSystemVersionLarge23()){
            checkPermission();
        }else {
            prepare();
        }
    }
    private void prepare(){
        //操作
    }
    private void checkPermission(){

        if (checkMorePermission(permissions)){
            prepare();
        }else {
            requestOneOrMorePermissions(permissions,11,this);//一次请求多个权限
//            requestSDcardPermission(this,111);
//            requestSensorPermission(this,112);
//            requestPositionPermission(this,113);
        }
    }

    //申请权限回调  Android6.0以上才会调用
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 11:
                if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //所有权限通过才会
                    prepare();
                }else {
                    //拒绝一个就会这出现
                    showHintDialog("获取权限失败！");
                }
                break;
            case 111:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //权限通过
                }else {
                    //拒绝一个就会这出现

                }
                break;
            case 112:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //权限通过

                }else {
                    //拒绝一个就会这出现

                }
                break;
            case 113:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //所有权限通过才会
                }else {
                    //拒绝一个就会这出现

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
    }

    /**
     * 拒绝权限弹出提示
     * @param messege
     */
    protected void showHintDialog(String messege){
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setMessage(messege);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "授权", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkPermission();
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
