package com.bayue.live.deqingpu.view;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.bayue.live.deqingpu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自动垂直滚动的TextView
 */
public class AutoVerticalScrollTextView extends TextSwitcher implements ViewSwitcher.ViewFactory {

    private static final int UPDATA_TEXTSWITCHER = 1;
    private Context mContext;
    private int index = 0;
    private int tvColor = Color.parseColor("#000000");
    private int tvBgColor = Color.parseColor("#FFFFFF");
    private List<String> mReArrayList = new ArrayList<>();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = UPDATA_TEXTSWITCHER;
            handler.sendMessage(message);
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATA_TEXTSWITCHER:
                    updateTextSwitcher();
                    break;
            }
        }
    };


    public void getResource(List<String> reArrayList) {
        this.mReArrayList = reArrayList;
    }

    public void setTvColor(int tvColor){
        this.tvColor = tvColor;
    }

    public void setTvBgColor(int tvBgColor){
        this.tvBgColor = tvBgColor;
    }

    private void updateTextSwitcher() {
        if (mReArrayList != null && mReArrayList.size() > 0) {

            this.setText(mReArrayList.get(index++));
            //实现归零
            if (index > mReArrayList.size() - 1) {
                index = 0;
            }
        }
    }

    public AutoVerticalScrollTextView(Context context) {
        super(context, null);
    }

    public AutoVerticalScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    private void init() {
        this.setFactory(this);
        this.setInAnimation(getContext(), R.anim.vcertical_in);
        this.setOutAnimation(getContext(), R.anim.vcertical_out);
        Timer timer = new Timer();
        timer.schedule(timerTask, 1, 3000);
        this.setTvColor(tvColor);
        this.setTvBgColor(tvBgColor);
//        mReArrayList.add("一段文字");
//        mReArrayList.add("一段简短文字");
//
//        mReArrayList.add("一段很长很长很长很长很长很长很长很长很长的文字");

    }


    @Override
    public View makeView() {
        //如果想实现跑马灯 可以这么做 不然直接用TextView也是没有关系的
        //设置自己的TextView样式 继承TextView 重写isFoused方法为true
        WaterTextView tv = new WaterTextView(getContext());
/*        Android:ellipsize = "end"　　  省略号在结尾
        android:ellipsize = "start" 　　省略号在开头
        android:ellipsize = "middle"     省略号在中间
        android:ellipsize = "marquee"  跑马灯
        最好加一个约束android:singleline = "true"*/
        tv.setSingleLine(true);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        //设置重复次数 -1则无线循环
        tv.setMarqueeRepeatLimit(1);
        tv.setTextColor(tvColor);
//        tv.setBackgroundColor(tvBgColor);
        tv.setTextSize(14);
        tv.setPadding(20,15,20,15);

        return tv;
    }

}
