package com.bayue.live.deqingpu.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.MyAdapter;
import com.bayue.live.deqingpu.utils.Utils;

import java.util.List;


/**
 * Created by user on 2014/11/11.
 */
public class PopupWindowListView extends PopupWindow {

    ListView fuzzyList;
    private View mMenuView;
    LinearLayout linCityTitle;
    TextView txtProvince, txtCity, txtDisc, txtCancel;
    Activity baseActivity;
    public PopupWindowListView(Activity context, AdapterView.OnItemClickListener itemsOnClick, MyAdapter aAdapter, boolean isCity, int height, View.OnClickListener viewClick) {
        super(context);
        baseActivity = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.ac_address_picker, null);
        fuzzyList = (ListView) mMenuView.findViewById(R.id.listViewPicker);
        linCityTitle = (LinearLayout) mMenuView.findViewById(R.id.linCityTitle);
        if (!isCity) {
            linCityTitle.setVisibility(View.GONE);
        }else {
            showCityWindow(viewClick);
        }
        //取消按钮
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//            }
//        });
        //设置按钮监听
        fuzzyList.setAdapter(aAdapter);
        fuzzyList.setOnItemClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(height);
//        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        this.setHeight(Utils.getScreenSize(context)[1] / 3);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置可以点击
        this.setTouchable(true);
        setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
//        showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.rob_pop));
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
//        setBackgroundDrawable(new BitmapDrawable());
        update();
        // 按下android回退物理键 PopipWindow消失解决
        fuzzyList.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        dismiss();
                        return true;
                }
                return false;
            }
        });
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
//        mMenuView.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
//                int y=(int) event.getY();
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    if(y<height){
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });

    }
    private void showCityWindow(View.OnClickListener viewClick) {
       
        //绑定控件
        txtProvince = (TextView) mMenuView.findViewById(R.id.txtProvince);
        txtCity = (TextView) mMenuView.findViewById(R.id.txtCity);
        txtDisc = (TextView) mMenuView.findViewById(R.id.txtDisc);
        txtCancel = (TextView) mMenuView.findViewById(R.id.txtCancel);
        txtProvince.setOnClickListener(viewClick);
        txtCity.setOnClickListener(viewClick);
        txtDisc.setOnClickListener(viewClick);
        txtCancel.setOnClickListener(viewClick);

//        txtProvince.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.clear();
//                list.addAll(listPro);
//                resetView();
//                txtProvince.setTextColor(ContextCompat.getColor(baseActivity, R.color.red));
//                txtCity.setVisibility(View.VISIBLE);
//                cityAdapter.notifyDataSetChanged();
//                isProvince = MSG_LOAD_DATA;
//            }
//        });
//        txtCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.clear();
//                list.addAll(listCity);
//                resetView();
//                txtCity.setTextColor(ContextCompat.getColor(baseActivity, R.color.red));
//                txtDisc.setVisibility(View.VISIBLE);
//                cityAdapter.notifyDataSetChanged();
//                isProvince = MSG_LOAD_SUCCESS;
//            }
//        });
//        txtDisc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.clear();
//                list.addAll(listDist);
//                resetView();
//                txtDisc.setTextColor(ContextCompat.getColor(baseActivity, R.color.red));
////                txtDisc.setVisibility(View.INVISIBLE);
////                txtCity.setVisibility(View.INVISIBLE);
////                txtProvince.setVisibility(View.INVISIBLE);
//                cityAdapter.notifyDataSetChanged();
//                isProvince = MSG_LOAD_FAILED;
//            }
//        });
//        txtCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (popupWindow != null && popupWindow.isShowing()) {
//                    popupWindow.dismiss();
//                    isProvince = MSG_LOAD_DATA;
//                    list.clear();
//                    listPro.clear();
//                    listPro.clear();
//                    listPro.clear();
//                }
//            }
//        });
    }
    private void resetView() {
        txtProvince.setTextColor(ContextCompat.getColor(baseActivity, R.color.black));
        txtCity.setTextColor(ContextCompat.getColor(baseActivity, R.color.black));
        txtDisc.setTextColor(ContextCompat.getColor(baseActivity, R.color.black));
    }
}