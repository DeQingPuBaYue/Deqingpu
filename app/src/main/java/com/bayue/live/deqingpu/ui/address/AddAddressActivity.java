package com.bayue.live.deqingpu.ui.address;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.data.GetJsonDataUtil;
import com.bayue.live.deqingpu.entity.JsonBean;
import com.bayue.live.deqingpu.entity.ResultModel;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.http.API.baseUrl;

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

    String addressPCode = "";
    String addressCCode = "";
    String addressDCode = "";
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public boolean isLoaded = false;
    private Novate novate;
    @Override
    protected int getViewId() {
        return R.layout.ac_add_address;
    }
    @Override
    protected void initViews() {
        String action = baseActivity.getIntent().getStringExtra("action");
        topBar.setTitle(getString(R.string.title_edit_address));
        topBar.setMenuTitle(getString(R.string.tv_save));
        topBar.hideMenuIcon();
        topBar.setMenuClickListener(new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {
                ToastUtils.showLongToast(R.string.tv_save);
                String addressName = edtConsignee.getText().toString();
                String addressTell = edtTell.getText().toString();
                String addressDetail = edtDetail.getText().toString();

            }
        });

        novate = new Novate.Builder(this)
                //.addParameters(parameters)
                .connectTimeout(20)
                .writeTimeout(15)
                .baseUrl(baseUrl)
//                .addHeader(headers)//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        BaseApiService api = novate.create(BaseApiService.class);
    }

    @OnClick({R.id.linDel, R.id.linSelectArea})
    public void setOnClick(View view){
        switch (view.getId()){
            case R.id.linSelectArea:

                mHandler.sendEmptyMessage(MSG_LOAD_DATA);

                break;
            case R.id.linDel:
                break;
        }
    }
    private Handler mHandler = new  Handler(){

        public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_LOAD_DATA:
//                            thread = new Thread(new Runnable() {
//                                @Override
//                                public void run() {
                                    // 写子线程中的操作,解析省市区数据
                                    Map<String, Object> map = Constants.getMap();
                                    map.put("region_type","1");
                                    getDataFromNet(map);
                        break;

                    case MSG_LOAD_SUCCESS:
                        isLoaded = true;
                        ShowPickerView();
                        break;

                    case MSG_LOAD_FAILED:
                        break;

                }
        }
    };

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

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

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
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }
    private void getDataFromNet(Map<String, Object> hashMap) {
        novate.post("api/address/get_region", hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                }

            }

            @Override
            public void onNext(ResponseBody responseBody) {

                try {
                    String jstr = new String(responseBody.bytes());
                    initJsonData();

//                    Type type = new TypeToken<NovateResponse<ResultModel>>() {}.getType();
//
//                    NovateResponse<ResultModel> response = new Gson().fromJson(jstr, type);
//                    String result = response.getData().toString();
//                    edtDetail.setText(jstr);
                } catch (IOException e) {
                    e.printStackTrace();
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
        mHandler.removeCallbacksAndMessages(null);
    }
}
