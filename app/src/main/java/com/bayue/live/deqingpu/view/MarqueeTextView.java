package com.bayue.live.deqingpu.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by BaYue on 2017/6/20.
 * email : 2651742485@qq.com
 */

public class MarqueeTextView extends android.support.v7.widget.AppCompatTextView {
    public MarqueeTextView(Context con) {
        super(con);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        // TODO Auto-generated method stub
//        if(getEditableText().equals(TextUtils.TruncateAt.MARQUEE)){
            return true;
//        }
//        return super.isFocused();
    }
}
