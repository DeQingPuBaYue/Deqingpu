package com.bayue.live.deqingpu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by BaYue on 2017/6/20.
 * email : 2651742485@qq.com
 */

public class WaterTextView extends TextView {
    public WaterTextView(Context context) {
        super(context);
    }

    public WaterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    public WaterTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}