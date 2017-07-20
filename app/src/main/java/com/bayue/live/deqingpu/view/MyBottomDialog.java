package com.bayue.live.deqingpu.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.utils.ToastUtils;

/**
 * Created by BaYue on 2017/6/27 0027.
 * email : 2651742485@qq.com
 */

public class MyBottomDialog extends Dialog {
    public MyBottomDialog(Context context) {
        this(context, R.style.PopupAnimation);
    }

    public MyBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        //加载布局并给布局的控件设置点击事件
        View contentView = getLayoutInflater().inflate(R.layout.dialog_custom, null);
        contentView.findViewById(R.id.tv_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLongToast("");
            }
        });
        super.setContentView(contentView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 预先设置Dialog的一些属性
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        getWindow().setAttributes(p);
        p.height = (int) (d.getHeight() * 0.6);
        p.width = d.getWidth();
        p.gravity = Gravity.LEFT | Gravity.BOTTOM;
        dialogWindow.setAttributes(p);
    }
}