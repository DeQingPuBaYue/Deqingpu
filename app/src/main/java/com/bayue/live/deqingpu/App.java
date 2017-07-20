package com.bayue.live.deqingpu;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.bayue.live.deqingpu.wxapi.WeChatInfo;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;

import java.util.LinkedList;
import java.util.List;

//import us.costan.chrome.ChromeView;

/**
 * Created by Yu on 2015/6/5.
 * 全局初始化
 */
public class App extends Application {
    final static String TAG = "App";
    public static boolean isSaveCache = false;
    public static void setSaveCache(boolean isSave){
        isSaveCache = isSave;
    }
    public static boolean getSaveCache(){
        return isSaveCache;
    }

    public List<Activity> activityList = new LinkedList<Activity>();
    public static App instance;

    public App() {
    }

    // 单例模式中获取唯一的MyApplication实例
    public static App getInstance() {
        if (null == instance) {
            instance = new App();
        }
        return instance;

    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {

        for (Activity activity : activityList) {
            activity.finish();
        }

//        System.exit(0);

    }


    @Override
    public void onCreate() {
        super.onCreate();

        Tracer.w(TAG, "======================================" + Tracer.getDebugText() + "======================================");

//        bdmapInit();
        Utils.init(this);
        //regist wechat
        WeChatInfo.init(this);
    }

    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


    void bdmapInit() {


        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //SDKInitializer.initialize(this);
        TelephonyManager telmgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = telmgr.getDeviceId();
        boolean isEmulator = "000000000000000".equalsIgnoreCase(deviceID);
        if (isEmulator) {
            Tracer.w(TAG, "在虚拟机中运行..."+" :"+deviceID);
//            SDKInitializer.initialize(getApplicationContext());
        } else {
            Tracer.w(TAG, "在实体机中运行...");
            //在使用SDK各组件之前初始化context信息，传入ApplicationContext
            //注意该方法要再setContentView方法之前实现
//            SDKInitializer.initialize(this);
        }

    }



}
