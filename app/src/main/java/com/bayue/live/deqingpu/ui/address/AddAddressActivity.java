package com.bayue.live.deqingpu.ui.address;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.MyAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.data.GetJsonDataUtil;
import com.bayue.live.deqingpu.entity.AreaData;
import com.bayue.live.deqingpu.entity.JsonBean;
import com.bayue.live.deqingpu.entity.ProvinceBean;
import com.bayue.live.deqingpu.entity.ResultModel;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.city.AreaBean;
import com.bayue.live.deqingpu.entity.city.CityBean;
import com.bayue.live.deqingpu.entity.city.ProBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.tamic.novate.BaseApiService;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.utils.Utils.getContext;

/**
 * Created by Administrator on 2017/6/6.
 */

public class AddAddressActivity extends BaseActivity {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.edtConsignee)
    EditText edtConsignee;
    @BindView(R.id.edtTell)
    EditText edtTell;
    @BindView(R.id.txtGetAddress)
    TextView txtGetAddress;
    @BindView(R.id.edtDetail)
    EditText edtDetail;
    @BindView(R.id.linDel)
    LinearLayout linDel;

    String addressPName = "", addressCName = "", addressDName = "" ,addressName = "", action = "";
    int proId, cityId, distId, addressId;
    public static final int MSG_LOAD_DATA = 0x0001;
    public static final int MSG_LOAD_SUCCESS = 0x0002;
    public static final int MSG_LOAD_FAILED = 0x0003;
    public static final int MSG_LOAD_ADD = 0x0004;
    public static final int MSG_LOAD_DEL = 0x0005;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    int isProvince;
    int areaState;
    public static final int MSG_PROVINCE = 0x0010; //当前列表为省的数据
    public static final int MSG_CITY = 0x0011;  //当前列表为市的数据
    public static final int MSG_DIST = 0x0012;  //当前列表为区的数据
    private String TAG = "AddAddressActivity";
    private PopupWindow popupWindows;
    private View contentView;
    String token= "";
    public static final int MSG_LOAD_ALL = 0x0013;
    public static final int MSG_GET_DATA = 0x0014;
    public static final int MSG_FINAL = 0x0020;
    SaveObjectUtils utils;
    private boolean isLoaded = false;
    @Override
    protected int getViewId() {
        return R.layout.ac_add_address;
    }
    @Override
    protected void initViews() {
        progress = new ProgressDialog(baseContext);
        progress.setMessage("拼命加载中....");
        action = baseActivity.getIntent().getStringExtra("action");
        if (action.equals("edit")){
            String value  = baseActivity.getIntent().getStringExtra("bean");
            Tracer.e(TAG, value);
            ResultModel.DataBean bean = (ResultModel.DataBean) GsonHelper.getInstanceByJson(ResultModel.DataBean.class, value);
            if (!Guard.isNull(bean)){
                edtConsignee.setText(bean.getConsignee());
                edtTell.setText(bean.getMobile());
                edtDetail.setText(bean.getAddress());
                proId = bean.getProvince();
                cityId = bean.getCity();
                distId = bean.getDistrict();
                txtGetAddress.setText(bean.getProvince_name()+ "\t\t" +bean.getCity_name() + "\t\t" + bean.getDistrict_name());
//                distId = bean.getAddress_id();
                addressId = bean.getAddress_id();
            }
        }
        topBar.setTitle(getString(R.string.title_edit_address));
        topBar.setMenuTitle(getString(R.string.tv_save));
        topBar.hideMenuIcon();
        token = Preferences.getString(getContext(), Preferences.TOKEN);
        showPopwindow();
        topBar.setMenuClickListener(new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {
                String edtName = edtConsignee.getText().toString();
                String edtPhone = edtTell.getText().toString();
                String edtDist = edtDetail.getText().toString();
                Map<String, Object> map = Constants.getMap();
                map.put("province", proId);
                map.put("city", cityId);
                map.put("district", distId);
//                map.put("token", token);
                map.put("consignee", edtName);
                map.put("mobile", edtPhone);
                map.put("address", edtDist);
                map.put("address_id", addressId);
                if (action.equals("edit")){
                    getDataFromNet(API.UPDATE, map, null, MSG_LOAD_ADD);
                }else {
                    getDataFromNet(API.ADDADDRESS, map, null, MSG_LOAD_ADD);
                }
            }
        });
        utils = new SaveObjectUtils(baseActivity, "Info");
        AreaData data = utils.getObject("data", AreaData.class);
        if (!Guard.isNull(data)){
            beansItem1 = data.getBeansItem1();
            beansItem2 = data.getBeansItem2();
            beansItem3 = data.getBeansItem3();
        }
//        handler.sendEmptyMessage(MSG_LOAD_DATA);
//        topBar.setBackClickListener(new TopActionBar.BackClickListener() {
//            @Override
//            public void backClick() {
//                if (popupWindows!= null && popupWindows.isShowing()){
//                    popupWindows.dismiss();
//                }
//            }
//        });
    }

    private ProgressDialog progress;
    @OnClick({R.id.linDel, R.id.linSelectArea})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.linSelectArea:
                ShowPickerView();
//                handler.sendEmptyMessage(MSG_FINAL);

//                Map<String, Object> map = Constants.getMap();
//                map.put("region_type","1");
//                isProvince = MSG_LOAD_DATA;
//                areaState = MSG_PROVINCE;
//                getDataFromNet(API.GETADDRESS, map, view, MSG_LOAD_DATA);
//                Map<String, Object> hashMap = new HashMap<>();
//                hashMap.put("region_type","1");
//                getDataFromOKHttp(API.baseUrl+ API.GETADDRESS ,hashMap, view, MSG_LOAD_DATA);
                break;
            case R.id.linDel:
                if (distId>0 && action.equals("edit")){
                    Map<String, Object> delMap = Constants.getMap();
                    delMap.put("address_id",addressId);
//                    delMap.put("token",token);
                    getDataFromNet(API.DELECT, delMap, view, MSG_LOAD_DEL);
                }else {
//                    baseActivity.finish();
                }
                break;
        }
    }


    /**
     * 显示popupWindow
     */
    private List<ProvinceBean> list;
    private List<ProvinceBean> listPro;
    private List<ProvinceBean> listCity;
    private List<ProvinceBean> listDist;
    ListView fuzzyList;
    MyAdapter adapter;
    TextView txtProvince, txtCity, txtDisc, txtCancel;
    private void showPopwindow() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(baseActivity).inflate(
                R.layout.ac_address_picker, null);
        //绑定控件
        fuzzyList = (ListView) contentView.findViewById(R.id.listViewPicker);
        txtProvince = (TextView) contentView.findViewById(R.id.txtProvince);
        txtCity = (TextView) contentView.findViewById(R.id.txtCity);
        txtDisc = (TextView) contentView.findViewById(R.id.txtDisc);
        txtCancel = (TextView) contentView.findViewById(R.id.txtCancel);
        //创建集合  模拟数据
        list = new ArrayList<>();
        tempList = new ArrayList<>();
        listPro = new ArrayList<>();
        listCity = new ArrayList<>();
        listDist = new ArrayList<>();
        //初始化适配器
        adapter = new MyAdapter(baseActivity, list);
        //添加适配器
        fuzzyList.setAdapter(adapter);
        //gridview点击的监听
        fuzzyList.setOnItemClickListener(new ItemClickListener());
        //设置弹出框的宽度和高度
        popupWindows = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                Utils.getScreenSize(baseContext)[1] / 3);
        popupWindows.setFocusable(true);// 取得焦点
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindows.setTouchable(true);
        //进入退出的动画
        popupWindows.setAnimationStyle(R.style.PopupWindowAnimation);
        // 按下android回退物理键 PopipWindow消失解决
        fuzzyList.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindows != null && popupWindows.isShowing()) {
                        popupWindows.dismiss();
                        isProvince = MSG_LOAD_DATA;
                        list.clear();
                        listPro.clear();
                        listPro.clear();
                        listPro.clear();
                        tempList.clear();
                        return true;
                    }
                }
                return false;
            }
        });
        txtProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(listPro);
                resetView();
                txtProvince.setTextColor(ContextCompat.getColor(baseContext,R.color.red));
                txtCity.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                isProvince = MSG_LOAD_DATA;
            }
        });
        txtCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(listCity);
                resetView();
                txtCity.setTextColor(ContextCompat.getColor(baseContext,R.color.red));
                txtDisc.setVisibility(View.VISIBLE);
                adapter.notifyDataSetChanged();
                isProvince = MSG_LOAD_SUCCESS;
            }
        });
        txtDisc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.clear();
                list.addAll(listDist);
                resetView();
                txtDisc.setTextColor(ContextCompat.getColor(baseContext,R.color.red));
//                txtDisc.setVisibility(View.INVISIBLE);
//                txtCity.setVisibility(View.INVISIBLE);
//                txtProvince.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
                isProvince = MSG_LOAD_FAILED;
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindows!=null && popupWindows.isShowing()){
                    popupWindows.dismiss();
                    isProvince = MSG_LOAD_DATA;
                    list.clear();
                    listPro.clear();
                    listPro.clear();
                    listPro.clear();
                }
            }
        });

    }
    private void resetView(){
        txtProvince.setTextColor(ContextCompat.getColor(baseContext,R.color.black));
        txtCity.setTextColor(ContextCompat.getColor(baseContext,R.color.black));
        txtDisc.setTextColor(ContextCompat.getColor(baseContext,R.color.black));
    }
    public class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {
            //背景选择
//            adapter.setSeclection(position);
            ToastUtils.showLongToast(position +" status:"+ isProvince +" regionID:" + list.get(position).getRegion_id());
            switch (areaState){
                case MSG_PROVINCE:
                    proId = list.get(position).getRegion_id();
                    addressPName = list.get(position).getRegion_name();
                    Map<String, Object> msgProMap = Constants.getMap();
                    msgProMap.put("region_type","2");
                    msgProMap.put("region_id ",proId +"");
                    txtProvince.setVisibility(View.VISIBLE);
                    areaState = MSG_CITY;
                    txtProvince.setVisibility(View.VISIBLE);
                    getDataFromNet(API.GETADDRESS, msgProMap, arg1, MSG_LOAD_DATA);
                    break;
                case MSG_CITY:
                    cityId = list.get(position).getRegion_id();
                    addressCName = list.get(position).getRegion_name();
                    Map<String, Object> msgCityMap = Constants.getMap();
                    msgCityMap.put("region_type","3");
                    msgCityMap.put("region_id ",cityId+"");
                    areaState = MSG_DIST;
                    getDataFromNet(API.GETADDRESS, msgCityMap, arg1, MSG_LOAD_DATA);
                    break;
                case MSG_DIST:
                    distId = list.get(position).getRegion_id();
                    addressDName = list.get(position).getRegion_name();
                    addressName = addressPName + "\t\t" + addressCName + "\t\t" + addressDName ;
                    txtGetAddress.setText(addressName);
                    if (popupWindows!=null && popupWindows .isShowing()){
                        popupWindows.dismiss();
                    }
                    break;
            }

        }
    }
    /**
     * 显示PickerView
     */
    private void ShowPickerView() {// 弹出选择器
        if (beansItem1.size()==0 || beansItem2.size() == 0 || beansItem3.size() == 0){
            ToastUtils.showLongToast("正在加载数据中，请稍候");
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String px = beansItem1.get(options1).getPickerViewText()+ "\t\t" +
                        beansItem2.get(options1).get(options2).getPickerViewText()+ "\t\t" +
                        beansItem3.get(options1).get(options2).get(options3).getPickerViewText();
//                ToastUtils.showLongToast(px);
                proId = beansItem1.get(options1).getRegion_id();
                cityId = beansItem2.get(options1).get(options2).getRegion_id();
                distId = beansItem3.get(options1).get(options2).get(options3).getRegion_id();
                Tracer.e(TAG, "proId:" + proId + " cityId:" + cityId + " distId:"+ distId);
                txtGetAddress.setText(px);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(beansItem1, beansItem2,beansItem3);//三级选择器
//        pvOptions.setNPicker(proList, cityItemList, areaItemList);
        pvOptions.show();
    }

    List<ProvinceBean> tempList;

    private void getDataFromNet(String url, Map<String, Object> hashMap, final View view, final int status) {
        Constants.LogMap(hashMap);
        Tracer.e(TAG, url);
        if (status == MSG_LOAD_ALL) {
            Looper.prepare();
        }
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                }
            }

            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onNext(ResponseBody responseBody) {
                String jstr = null;
                try {
                    jstr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tracer.e(TAG,jstr + " status:" + status);
                switch (status){
                    case MSG_LOAD_ADD:
                    case MSG_LOAD_DEL:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == 200){
                            ToastUtils.showLongToast(r.getData());
                            baseActivity.finish();
                        }else {
                            ToastUtils.showLongToast(r.getMsg());
                        }
                        break;
                    case MSG_LOAD_DATA:// 获取省数据
                    case MSG_LOAD_SUCCESS:
                    case MSG_LOAD_FAILED:
                        final List<ProvinceBean> cityList = GsonHelper.jsonToArrayList(jstr,ProvinceBean.class);
                        list.clear();
                        list.addAll(cityList);
                        switch (areaState){
                            case MSG_PROVINCE:
                                listPro.clear();
                                listPro.addAll(cityList);
                                isProvince = MSG_LOAD_SUCCESS;
                                break;
                            case MSG_CITY:
                                isProvince = MSG_LOAD_FAILED;
                                listCity.clear();
                                listCity.addAll(cityList);
                                break;
                            case MSG_DIST:
                                listDist.clear();
                                listDist.addAll(cityList);
                                break;
                        }
                        adapter.notifyDataSetChanged();
                        popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        break;
                    case MSG_LOAD_ALL:
                        Message message = new Message();
                        message.what = MSG_GET_DATA;
                        message.obj = jstr;
                        handler.sendMessage(message);
                        break;
                }
            }

        });
    }
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
    List<ProBean> proList = new ArrayList<>();
    List<AreaBean> cityList = new ArrayList<>();
    List<AreaBean> areaList = new ArrayList<>();
    public static final int MSG_HANDLER_AREA = 0x0015;
    public static final int MSG_HANDLER_CITY = 0x0016;
    public static final int MSG_HANDLER_PROVINCE = 0x0017;
    private String provinceStr, cityStr, areaStr;
    private void parseAllData(String jstr) {
        provinceStr = GsonHelper.getStrFromJson(jstr, "province");
        cityStr = GsonHelper.getStrFromJson(jstr, "city");
        areaStr = GsonHelper.getStrFromJson(jstr, "area");
        Message message = new Message();
        message.what = MSG_HANDLER_AREA;
        message.obj = areaStr;
        handler.sendMessage(message);
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
        handler.sendEmptyMessage(MSG_HANDLER_PROVINCE);
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
        handler.sendEmptyMessage(MSG_HANDLER_CITY);
    }

    private Handler handler = new MyHandler(this);
    private Thread thread;
    private static class MyHandler extends Handler {
        private WeakReference<AddAddressActivity> mWeakReference;

        public MyHandler(AddAddressActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            final AddAddressActivity activity = mWeakReference.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_LOAD_DATA:
                            activity.thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // 写子线程中的操作,解析省市区数据
                                    activity.getDataFromNet(API.Address.GETALL, Constants.getMap(), null, activity.MSG_LOAD_ALL);
                                }
                            });
                            activity.thread.start();
                        break;
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
//                        final String city = (String) msg.obj;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                activity.parseCity(activity.cityStr);
                            }
                        }).start();
                        break;
                    case MSG_HANDLER_PROVINCE:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                activity.handlerData();
                            }
                        }).start();
                        break;
                    case MSG_LOAD_SUCCESS:
                        activity.isLoaded = true;
                        break;
                    case MSG_FINAL:
                        activity.handler.postDelayed(activity.mAction, 100);
                        break;
                }
            }
        }
    }

    private Runnable mAction = new Runnable() {

        @Override
        public void run() {
            if (isLoaded) {
                ShowPickerView();
            }else {
                // 在run()方法内调用自身，这样就可以实现循环
                handler.postDelayed(mAction, 100);
            }
        }
    };
    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tracer.e(TAG, "onDestroy");
        handler.removeCallbacksAndMessages(null);
    }
}
