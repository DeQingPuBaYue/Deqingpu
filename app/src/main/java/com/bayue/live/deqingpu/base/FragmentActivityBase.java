package com.bayue.live.deqingpu.base;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.bayue.live.deqingpu.App;

import static com.zhy.autolayout.utils.ScreenUtils.getStatusBarHeight;


/**
 * Created by Yu on 2015/5/29.
 * FragmentActivity 基类
 */
@SuppressLint("NewApi")
public class FragmentActivityBase extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    /**
     * 当前Activity
     */
    protected FragmentActivityBase baseActivity;

    /**
     * 当前上下文
     */
    protected Context baseContext;
    boolean isOvered;
    AlertDialog dlg;
    SharedPreferences  preces;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); //取消顶部ActionBar
        //getActionBar().setDisplayShowHomeEnabled(false); //隐顶部的App_Icon
        baseActivity = this;
        baseContext = this.getBaseContext();
        App.getInstance().addActivity(this);
        quanping();
        dlg = new AlertDialog.Builder(baseActivity).create();
        preces = baseActivity.getSharedPreferences("isOverled", baseActivity.MODE_PRIVATE);
        isOvered = preces.getBoolean("isOverled", false);
        SharedPreferences preference = getSharedPreferences("InitJpush",MODE_PRIVATE);
        String JpushId = preference.getString("JpushId","");
    }

    public void quanping(){
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup)findViewById(Window.ID_ANDROID_CONTENT);
        View statusBarView = mContentView.getChildAt(0);
        //移除假的 View
        if (statusBarView != null && statusBarView.getLayoutParams() != null && statusBarView.getLayoutParams().height ==getStatusBarHeight(this)) {
            mContentView.removeView(statusBarView);
        }
        //不预留空间
        if (mContentView.getChildAt(0) != null) {
            ViewCompat.setFitsSystemWindows(mContentView.getChildAt(0), false);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (isOvered){
//            DialogUtil.showExitAlert(listener, dlg);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    /**
     * 必须要在子类中重写才能使用反射
     */
    protected FragmentActivity getThis() {
        return this;
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

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
        showToast(baseActivity.getString(resId), duration);
    }
    /**
     * 创建一个模态对话框
     */
    protected ProgressDialog createDialog(String msg) {
        ProgressDialog dialog = ProgressDialog.show(baseActivity, null, msg, true, true);
        dialog.setCanceledOnTouchOutside(false); //禁止触碰取消
        // dialog.getWindow().setContentView(R.layout.refresh);
        return dialog;
    }


    protected void delayedDismiss(final Dialog dialog,int delayMillis){
        if (dialog == null) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayMillis);
    }

    public void delayedFinish(int delayMillis) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, delayMillis);
    }


    //TODO：以下皆待整理

    /**
     * 创建一个模态对话框
     */
    protected ProgressDialog createDialog(int stringId) {
        return createDialog(getResources().getString(stringId));
    }


    public <T extends View> T getView(int resId) {
        View view = findViewById(resId);
        return (T) view;
    }


//    /**
//     * 显示头尾内容
//     */
//    protected void showCompleteDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
//                                      OnClickListener clickListener, OnItemClickListener itemClickListener, OnDismissListener dismissListener, OnCancelListener cancelListener) {
//        final DialogPlus dialog = new DialogPlus.Builder(baseActivity)
//                .setContentHolder(holder)
//                .setHeader(R.layout.dialog_header_res)
//                .setFooter(R.layout.dialog_footer)
//                .setCancelable(true)
//                .setGravity(gravity)
//                .setAdapter(adapter)
//                .setOnClickListener(clickListener)
//                .setOnItemClickListener(itemClickListener)
//                .setOnDismissListener(dismissListener)
//                .setOnCancelListener(cancelListener)
//                .setOutMostMargin(0, 100, 0, 0)
//                .create();
//        dialog.show();
//    }
//
//    /**
//     * 显示无尾内容
//     */
//    protected void showNoFooterDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
//                                      OnClickListener clickListener, OnItemClickListener itemClickListener, OnDismissListener dismissListener, OnCancelListener cancelListener) {
//        final DialogPlus dialog = new DialogPlus.Builder(baseActivity)
//                .setContentHolder(holder)
//                .setHeader(R.layout.dialog_header_res)
//                .setCancelable(true)
//                .setGravity(gravity)
//                .setAdapter(adapter)
//                .setOnClickListener(clickListener)
//                .setOnItemClickListener(itemClickListener)
//                .setOnDismissListener(dismissListener)
//                .setOnCancelListener(cancelListener)
//                .create();
//        dialog.show();
//    }
//
//    /**
//     * 显示无头内容
//     */
//    protected void showNoHeaderDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
//                                      OnClickListener clickListener, OnItemClickListener itemClickListener, OnDismissListener dismissListener, OnCancelListener cancelListener) {
//        final DialogPlus dialog = new DialogPlus.Builder(baseActivity)
//                .setContentHolder(holder)
//                .setFooter(R.layout.dialog_footer)
//                .setCancelable(true)
//                .setGravity(gravity)
//                .setAdapter(adapter)
//                .setOnClickListener(clickListener)
//                .setOnItemClickListener(itemClickListener)
//                .setOnDismissListener(dismissListener)
//                .setOnCancelListener(cancelListener)
//                .create();
//        dialog.show();
//    }
//
//
//    /**
//     * 仅显示内容
//     */
//    protected void showOnlyContentDialog(Holder holder, DialogPlus.Gravity gravity, BaseAdapter adapter,
//                                         OnClickListener clickListener, OnItemClickListener itemClickListener, OnDismissListener dismissListener, OnCancelListener cancelListener) {
//        final DialogPlus dialog = new DialogPlus.Builder(baseActivity)
//                .setContentHolder(holder)
//                .setCancelable(false) //点击对话框外部不关闭
//                .setGravity(gravity)
//                .setAdapter(adapter)
//                .setOnClickListener(clickListener)
//                .setOnItemClickListener(itemClickListener)
//                .setOnDismissListener(dismissListener)
//                .setOnCancelListener(cancelListener)
//                .create();
//
//
//        dialog.show();
//    }
//
//
//    protected void showDialog(int layoutResId, OnClickListener clickListener) {
//        //Holder holder  = new ViewHolder(layoutResId);
//        final DialogPlus dialog = new DialogPlus.Builder(baseActivity)
//                .setContentHolder(new ViewHolder(layoutResId))
//                .setCancelable(false) //点击对话框外部不关闭
//                .setGravity(DialogPlus.Gravity.CENTER)
//                .setOnClickListener(clickListener)
//                .setBackgroundColorResourceId(R.color.trans)
//                .create();
//        dialog.show();
//    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
