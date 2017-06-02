package com.bayue.live.deqingpu.base;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/31.
 */

public abstract class BaseFragment extends Fragment {
    Context baseActivity;
    Unbinder unbinder;
//    AlertDialog dlg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(getViewId(),null);
        unbinder= ButterKnife.bind(this,view);
//        dlg = new AlertDialog.Builder(baseActivity).create();
        this.baseActivity = getActivity();
        init();
        return view;
    }
    protected abstract int getViewId();
    /**
     * 内容初始化，必须重写
     */
    public abstract void init();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    /**慢3秒*/
    protected void toastSlow(int stringId) {
        //3秒
//        Toast.makeText(baseActivity,getResources().getString(stringId),Toast.LENGTH_LONG).show();
        showToast(stringId, Toast.LENGTH_LONG);
    }

    protected void toastSlow(String msg) {
        //3秒
//        Toast.makeText(baseActivity,msg,Toast.LENGTH_LONG).show();
        showToast(msg, Toast.LENGTH_LONG);
    }

    /**快1秒*/
    protected  void toastFast(int stringId) {
        //1秒
//        Toast.makeText(baseActivity,getResources().getString(stringId), Toast.LENGTH_SHORT).show();
        showToast(stringId, Toast.LENGTH_SHORT);
    }

    protected  void toastFast(String msg) {
        //1秒
        showToast(msg, Toast.LENGTH_SHORT);
//        Toast.makeText(baseActivity,msg, Toast.LENGTH_SHORT).show();
    }

    private static String oldMsg;
    protected static Toast toast   = null;
    private static long oneTime=0;
    private static long twoTime=0;
    public void showToast(String s, int duration){
        if (toast == null) {
            toast = Toast.makeText(baseActivity, s, duration);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > duration) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }
    public void showToast(int resId, int duration){
        if(baseActivity == null) return;
        showToast(baseActivity.getString(resId), duration);
    }

    /**创建一个模态对话框*/
    protected ProgressDialog createDialog(String msg) {
        ProgressDialog dialog = ProgressDialog.show(baseActivity, null, msg, true, true);
        //dialog.setView(); //TODO:重设显示UI
        dialog.setCanceledOnTouchOutside(false); //禁止触碰取消
        // dialog.getWindow().setContentView(R.layout.refresh);
        return dialog;
    }
    /**创建一个模态对话框*/
    protected ProgressDialog createDialog(int stringId){
        return createDialog(getResources().getString(stringId));
    }



}
