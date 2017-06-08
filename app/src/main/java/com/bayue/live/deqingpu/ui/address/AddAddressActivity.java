package com.bayue.live.deqingpu.ui.address;

import android.annotation.TargetApi;
import android.app.Activity;
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
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.data.GetJsonDataUtil;
import com.bayue.live.deqingpu.entity.JsonBean;
import com.bayue.live.deqingpu.entity.ProvinceBean;
import com.bayue.live.deqingpu.entity.ResultModel;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.PopupWindowListView;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tamic.novate.BaseApiService;
import com.tamic.novate.Novate;
import com.tamic.novate.NovateResponse;
import com.tamic.novate.Throwable;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.http.API.baseUrl;
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
    int proId, cityId, distId;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static final int MSG_LOAD_ADD = 0x0004;
    private static final int MSG_LOAD_DEL = 0x0005;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Map<String, String> headers = new HashMap<>();
    int isProvince;
    private Novate novate;
    private String TAG = "AddAddressActivity";
    private PopupWindow popupWindows;
    private View contentView;
    private String[] kinds = { "休闲食品", "生鲜果蔬", "办公/家居", "其它", "鲜花", "蛋糕",
            "大件物品" };
    @Override
    protected int getViewId() {
        return R.layout.ac_add_address;
    }
    @Override
    protected void initViews() {
        action = baseActivity.getIntent().getStringExtra("action");
        topBar.setTitle(getString(R.string.title_edit_address));
        topBar.setMenuTitle(getString(R.string.tv_save));
        topBar.hideMenuIcon();
        topBar.setMenuClickListener(new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {
                String edtName = edtConsignee.getText().toString();
                String edtPhone = edtTell.getText().toString();
                String edtDist = edtDetail.getText().toString();
                String token = Preferences.getString(getContext(), Preferences.TOKEN);
                Map<String, Object> map = Constants.getMap();
                map.put("province",proId);
                map.put("city",cityId);
                map.put("district",distId);
                map.put("token",token);
                map.put("consignee",edtName);
                map.put("mobile",edtPhone);
                map.put("address",edtDist);
                getDataFromNet(API.ADDADDRESS, map, null, MSG_LOAD_ADD);

            }
        });
        Tracer.e(TAG,API.baseUrl);
        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application");
        novate = new Novate.Builder(this)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        BaseApiService api = novate.create(BaseApiService.class);
        showPopwindow();
    }

    @OnClick({R.id.linDel, R.id.linSelectArea})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.linSelectArea:
                Map<String, Object> map = Constants.getMap();
                map.put("region_type","1");
                isProvince = MSG_LOAD_DATA;
                getDataFromNet(API.GETADDRESS, map, view, MSG_LOAD_DATA);
                break;
            case R.id.linDel:
                if (distId>0){

                }else {
                    baseActivity.finish();
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
            switch (isProvince){
                case MSG_LOAD_DATA:
                    proId = list.get(position).getRegion_id();
                    addressPName = list.get(position).getRegion_name();
                    Map<String, Object> proMap = Constants.getMap();
                    proMap.put("region_type","2");
                    proMap.put("region_id ",proId);
                    isProvince = MSG_LOAD_DATA;
                    txtProvince.setVisibility(View.VISIBLE);
                    getDataFromNet(API.GETADDRESS, proMap, arg1, MSG_LOAD_SUCCESS);
                    break;
                case MSG_LOAD_SUCCESS:
                    cityId = list.get(position).getRegion_id();
                    addressCName = list.get(position).getRegion_name();
                    Map<String, Object> cityMap = Constants.getMap();
                    cityMap.put("region_type","3");
                    cityMap.put("region_id ",cityId);
                    break;
                case MSG_LOAD_FAILED:
                    isProvince = MSG_LOAD_DATA;
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

        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                ToastUtils.showLongToast(tx);
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
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }
    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }


    }
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
    List<ProvinceBean> tempList;
    private void getDataFromNet(String url, Map<String, Object> hashMap, final View view, final int status) {
        novate.post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

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
                Tracer.e(TAG,jstr);
                switch (status){
                    case MSG_LOAD_ADD:

                        break;
                    case MSG_LOAD_DEL:
                        break;
                    case MSG_LOAD_DATA:
                    case MSG_LOAD_SUCCESS:
                    case MSG_LOAD_FAILED:
                            final List<ProvinceBean> cityList = GsonHelper.jsonToArrayList(jstr,ProvinceBean.class);
                            list.clear();
                            list.addAll(cityList);
                            switch (status){
                                case MSG_LOAD_DATA:
                                    listPro.clear();
                                    listPro.addAll(cityList);
                                    break;
                                case MSG_LOAD_SUCCESS:
                                    listCity.clear();
                                    listCity.addAll(cityList);
                                    break;
                                case MSG_LOAD_FAILED:
                                    listDist.clear();
                                    listDist.addAll(cityList);
                                    break;
                            }
                            adapter.notifyDataSetChanged();
                            popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        break;
                }
            }
        });

    }

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
//        mHandler.removeCallbacksAndMessages(null);
    }
}
