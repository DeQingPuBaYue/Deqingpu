package com.bayue.live.deqingpu.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.tamic.novate.util.NetworkUtil;

/**
 * Created by LIUYONGKUI726 on 2017-06-01.
 */

public abstract class BaseSubscriber<T>  extends com.tamic.novate.BaseSubscriber<T> {

//    private ProgressDialog progress;

    private Context context;

    public BaseSubscriber(Context context) {
        super(context);
        this.context = context;
//        progress = new ProgressDialog(context);
//        progress.setMessage("拼命加载中....");

    }


    @Override
    public void onStart() {
        super.onStart();

        if (!NetworkUtil.isNetworkAvailable(context)) {
            Toast.makeText( context, "似乎没网哦",Toast.LENGTH_SHORT).show();
            onCompleted();
            return;
        }
//        if (progress != null){
//            if (progress.isShowing()) {
//                progress.dismiss();
//            }
//            progress.show();
//        }


    }

    @Override
    public void onCompleted() {
        super.onCompleted();

//        if (progress != null && progress.isShowing()) {
//            progress.dismiss();
//        }
    }

}
