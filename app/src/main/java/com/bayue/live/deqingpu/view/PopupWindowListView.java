package com.bayue.live.deqingpu.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


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
    public PopupWindowListView(Activity context,AdapterView.OnItemClickListener itemsOnClick, MyAdapter aAdapter) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.ac_address_picker, null);
        fuzzyList = (ListView) mMenuView.findViewById(R.id.listViewPicker);
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
        this.setHeight(Utils.getScreenSize(context)[1] / 3);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupWindowAnimation);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.rob_pop));
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
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

}