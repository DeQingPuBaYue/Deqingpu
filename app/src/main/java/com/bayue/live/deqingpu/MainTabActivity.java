package com.bayue.live.deqingpu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;

import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.AountInfo;
import com.bayue.live.deqingpu.entity.AreaData;
import com.bayue.live.deqingpu.entity.UserInfo;
import com.bayue.live.deqingpu.entity.city.AreaBean;
import com.bayue.live.deqingpu.fragment.TextTabFragment;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bumptech.glide.RequestManager;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2017/6/2.
 */

public class MainTabActivity extends BaseActivity {


    private TextTabFragment mTextTabFragment;
    private Novate novate;
    SaveObjectUtils utils;
    private RequestManager glideRequest;
    public static final int MSG_LOAD_ALL = 0x0013;
    public static final int MSG_GET_DATA = 0x0014;
    public static final int MSG_FINAL = 0x0020;
    UserInfo info = new UserInfo();
    @Override
    protected int getViewId() {
        return R.layout.activity_tab;
    }

    @Override
    protected void initViews() {
        checkPublishPermission();
//        setCurrentFragment();
        if (mTextTabFragment == null) {
            mTextTabFragment = TextTabFragment.newInstance(getString(R.string.navigation_text_tab));
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, mTextTabFragment);
        transaction.commit();
//        Snackbar.make(drawerLayout, "TextView + LinearLayout", Snackbar.LENGTH_SHORT).show();
        Log.e("token=====》》》》》", Preferences.getString(this,Preferences.TOKEN));

        utils=new SaveObjectUtils(baseContext,"Info");
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
//        info = utils.getObject("UserInfo", UserInfo.class);
//        if (Guard.isNull(info)) {
            Map<String, Object> map = Constants.getMap();
            beginGet(API.User.POUCH_INFO, map, 1);
//        }else {
//            initData();
//        }
//        getDataFromNet(API.Address.GETALL, Constants.getMap(), null, activity.MSG_LOAD_ALL);
        AreaData areaData = utils.getObject("data", AreaData.class);
        if (Guard.isNull(areaData)){
            beginGet(API.Address.GETALL, Constants.getMap(), 2);
        }else {
            if (Guard.isNullOrEmpty(areaData.getProvinceStr())){
                beginGet(API.Address.GETALL, Constants.getMap(), 2);
            }
        }
    }

    private void initData() {

    }

    private void beginGet(final String url, final Map<String, Object> map, final int LoadStatus){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(url, map, LoadStatus);
            }
        }, 300);

    }
    private void getDataFromNet(String url, Map<String, Object> hashMap, final int LoadStatus) {
        novate.post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String jstr = null;
                try {
                    jstr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!jstr.contains("code") && LoadStatus == 1){
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                switch (LoadStatus) {
                    case 1:

                        AountInfo info = (AountInfo) GsonHelper.getInstanceByJson(AountInfo.class, jstr);
                        if (info.getCode()== Constants.CODE_OK){
                            //缓存用户对象
                            utils.setObject("Info", info);
                        }
                        beginGet(API.User.INIT, Constants.getMap(), 3);
                        break;
                    case 2:
                        Message message = new Message();
                        message.what = MSG_GET_DATA;
                        message.obj = jstr;
                        handler.sendMessage(message);
                        break;
                    case 3:
                        Tracer.e("MainTab", jstr);
                        UserInfo userInfo = (UserInfo) GsonHelper.getInstanceByJson(UserInfo.class, jstr);
                        if (userInfo.getCode() == Constants.CODE_OK) {
                            //缓存用户对象
                            utils.setObject("UserInfo", userInfo);
                        }
                        break;
                }
            }
        });

    }

    private Handler handler = new MyHandler(this);
    private static class MyHandler extends Handler {
        private WeakReference<MainTabActivity> mWeakReference;

        public MyHandler(MainTabActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final MainTabActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_GET_DATA:
                        final String jstr = (String) msg.obj;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                activity.parseAllData(jstr);
                            }
                        }).start();
                        break;
                    case MSG_HANDLER_AREA:
                        final String area = (String) msg.obj;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                activity.parseArea(area);
                            }
                        }).start();
                        break;
                    case MSG_HANDLER_CITY:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                activity.parseCity(activity.cityStr);
                            }
                        }).start();
                        break;
                    case MSG_HANDLER_PROVINCE:
                        activity.handler.postDelayed(activity.mAction, 100);
                        break;
                    case MSG_LOAD_SUCCESS:
                        activity.isLoaded = true;
                        AreaData bean = new AreaData();
                        bean.setBeansItem1(activity.beansItem1);
                        bean.setBeansItem2(activity.beansItem2);
                        bean.setBeansItem3(activity.beansItem3);
                        bean.setProvinceStr(activity.provinceStr);
                        bean.setCityStr(activity.cityStr);
                        bean.setAreaStr(activity.areaStr);
                        activity.utils.setObject("data", bean);
                        Tracer.e("MainTab", "Address data is load finish "+ activity.isLoaded);
                        break;
                }
            }
        }
    }

    private Runnable mAction = new Runnable() {

        @Override
        public void run() {
            if (isCity && isArea) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        handlerData();
                    }
                }).start();
            }else {
                // 在run()方法内调用自身，这样就可以实现循环
                handler.postDelayed(mAction, 100);
            }
        }
    };

    public static final int MSG_LOAD_SUCCESS = 0x0002;
    public static final int MSG_HANDLER_AREA = 0x0015;
    public static final int MSG_HANDLER_CITY = 0x0016;
    public static final int MSG_HANDLER_PROVINCE = 0x0017;
    private boolean isLoaded = false, isCity = false, isArea = false;
    private String provinceStr, cityStr, areaStr;
    List<AreaBean> cityList = new ArrayList<>();
    List<AreaBean> areaList = new ArrayList<>();
    List<AreaBean> beansItem1 = new ArrayList<>();
    List<List<AreaBean>> beansItem2 = new ArrayList<>();
    List<List<List<AreaBean>>> beansItem3 = new ArrayList<>();
    private void handlerData(){
        try {
            JSONObject jsonObj = new JSONObject(provinceStr);
            Iterator it = jsonObj.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                String value = jsonObj.getString(key);
                AreaBean bean = (AreaBean) GsonHelper.getInstanceByJson(AreaBean.class, value);
                beansItem1.add(bean);
            }
            List<List<List<AreaBean>>> areaBeanList = new ArrayList<>();
            for (int i = 0; i < beansItem1.size(); i++) {
                List<AreaBean> iBeanList = new ArrayList<>();//该省的城市列表（第二级）
                List<List<AreaBean>> cityBeanList = new ArrayList<>();//该省的所有地区列表（第三极）
                for (int j = 0; j < cityList.size(); j++) {//遍历所有城市
                    if (cityList.get(j).getParent_id() == beansItem1.get(i).getRegion_id()){//根据parentId获取当前省的城市
                        iBeanList.add(cityList.get(j));
                        List<AreaBean> tempAreaList = new ArrayList<>();
                        for (int k = 0; k < areaList.size(); k++) {
                            if (areaList.get(k).getParent_id() == cityList.get(j).getRegion_id()){//根据parentId获取当前城市的所有区
                                tempAreaList.add(areaList.get(k));
                            }
                        }
                        cityBeanList.add(tempAreaList);
                    }
                }
                areaBeanList.add(cityBeanList);
                beansItem2.add(iBeanList);
                beansItem3.add(cityBeanList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        handler.sendEmptyMessage(MSG_LOAD_SUCCESS);
    }
    private void parseAllData(String jstr) {
        provinceStr = GsonHelper.getStrFromJson(jstr, "province");
        cityStr = GsonHelper.getStrFromJson(jstr, "city");
        areaStr = GsonHelper.getStrFromJson(jstr, "area");
        Message message = new Message();
        message.what = MSG_HANDLER_AREA;
        message.obj = areaStr;
        handler.sendMessage(message);
        handler.sendEmptyMessage(MSG_HANDLER_CITY);
        handler.sendEmptyMessage(MSG_HANDLER_PROVINCE);
    }

    private void parseCity(String city){
        try {
            JSONObject jsonObj = new JSONObject(city);
            Iterator it = jsonObj.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                String value = jsonObj.getString(key);
                AreaBean bean = (AreaBean) GsonHelper.getInstanceByJson(AreaBean.class, value);
                cityList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isCity = true;
//        handler.sendEmptyMessage(MSG_HANDLER_PROVINCE);
    }
    private void parseArea(String area){
        try {
            JSONObject jsonObj = new JSONObject(area);
            Iterator it = jsonObj.keys();
            while(it.hasNext()){
                String key = it.next().toString();
                String value = jsonObj.getString(key);
                AreaBean bean = (AreaBean) GsonHelper.getInstanceByJson(AreaBean.class, value);
                areaList.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        isArea = true;
//        handler.sendEmptyMessage(MSG_HANDLER_CITY);
    }

    @Override
    protected void setTheme() {

    }

    long oldTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        long currentTime = System.currentTimeMillis();

        if (keyCode == KeyEvent.KEYCODE_BACK && currentTime - oldTime > 3 * 1000) {
            ToastUtils.showShortToast("再按一次退出应用");
            oldTime = System.currentTimeMillis();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_BACK && currentTime - oldTime < 3 * 1000) {
            oldTime = 0;
            finish();
            App.getInstance().exit();
            return true;
        }

        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    /**
     * 6.0权限处理
     **/
    private boolean bPermission = false;
    private final int WRITE_PERMISSION_REQ_CODE = 100;

    private boolean checkPublishPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissiions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissiions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.CAMERA)) {
                permissiions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.RECORD_AUDIO)) {
                permissiions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissiions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissiions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(MainTabActivity.this, Manifest.permission.READ_PHONE_STATE)) {
                permissiions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissiions.size() != 0) {
                ActivityCompat.requestPermissions(MainTabActivity.this,
                        (String[]) permissiions.toArray(new String[0]),
                        WRITE_PERMISSION_REQ_CODE);
                return false;
            }
        }
        return true;
    }
}
