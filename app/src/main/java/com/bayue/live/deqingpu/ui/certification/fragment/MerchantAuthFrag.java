package com.bayue.live.deqingpu.ui.certification.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.MyAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.ProvinceBean;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.FileUtils;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.PhotoPopWindow;
import com.bayue.live.deqingpu.view.PopupWindowListView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.squareup.picasso.Picasso;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.ui.address.AddAddressActivity.MSG_LOAD_DATA;
import static com.bayue.live.deqingpu.ui.address.AddAddressActivity.MSG_LOAD_FAILED;
import static com.bayue.live.deqingpu.ui.address.AddAddressActivity.MSG_LOAD_SUCCESS;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class MerchantAuthFrag extends BaseFragment implements TakePhoto.TakeResultListener, InvokeListener {

    String TAG = "MerchantAuthFrag";
    @BindView(R.id.txtRealNameTip)
    TextView txtRealNameTip;
    @BindView(R.id.edtAuthName)
    EditText edtAuthName;
    @BindView(R.id.txtCardTip)
    TextView txtCardTip;
    @BindView(R.id.edtCardID)
    TextView edtCardID;
    @BindView(R.id.linStoreType)
    LinearLayout linStoreType;
    @BindView(R.id.txtImgTip)
    TextView txtImgTip;
    @BindView(R.id.linAuthTip)
    LinearLayout linAuthTip;
    @BindView(R.id.txtTipUpImg)
    TextView txtTipUpImg;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    Unbinder unbinder;
    @BindView(R.id.imgAuthPhoto)
    ImageView imgAuthPhoto;
    @BindView(R.id.linUploadImg)
    LinearLayout linUploadImg;
    @BindView(R.id.txtStoreType)
    TextView txtStoreType;
    @BindView(R.id.linSelectArea)
    LinearLayout linSelectArea;
    private String name = "", id_num = "";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Novate novate;
    private String baseFile = "";
    Handler mHandler = new Handler();
    MyAdapter myAdapter;
    List<ProvinceBean> typeList = new ArrayList<>();
    String[] typeArray = {"享美食", "住酒店", "爱玩乐", "爱旅游"};
    ListPopupWindow listPopupWindow;
    PopupWindowListView popupWindow;
    int storeType, isProvince;

    @Override
    protected int getViewId() {
        return R.layout.frag_auth_merchant;
    }

    @Override
    public void setArguments(Bundle args) {
//        name = args.getString("name");
    }

    @Override
    public void init() {
        txtRealNameTip.setText(getString(R.string.tip_store_name));
        txtCardTip.setText(getString(R.string.tip_store_address));
        txtImgTip.setText(getString(R.string.tip_store_buslicense));
        txtTipUpImg.setText("请上传实体店营业执照/三证合一" + getString(R.string.tip_auth_reminder));
        linAuthTip.setVisibility(View.INVISIBLE);
        linSelectArea.setVisibility(View.VISIBLE);
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        setDate();
        myAdapter = new MyAdapter(baseActivity, typeList);
        cityAdapter = new MyAdapter(baseActivity, list);
//        showCityWindow();
//        listPopupWindow = new ListPopupWindow(baseActivity);
//        listPopupWindow.setAdapter(new ArrayAdapter(baseActivity,R.layout.item_city_select, typeArray));
//        listPopupWindow.setAnchorView(view);//设置ListPopupWindow的锚点，即关联PopupWindow的显示位置和这个锚点
//        listPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
//        listPopupWindow.setHeight(Utils.getScreenSize(baseActivity)[1] / 3);
//        listPopupWindow.setModal(true);
//        listPopupWindow.setOnItemClickListener(itemClickListener);

    }

    private void setDate() {
        for (int i = 0; i < typeArray.length; i++) {
            ProvinceBean bean = new ProvinceBean();
            bean.setRegion_name(typeArray[i]);
            bean.setRegion_id(i);
            typeList.add(bean);
        }
    }

    /**
     * 显示popupWindow
     */
    private List<ProvinceBean> list = new ArrayList<>();
    private List<ProvinceBean> listPro = new ArrayList<>();
    private List<ProvinceBean> listCity = new ArrayList<>();
    private List<ProvinceBean> listDist = new ArrayList<>();
    MyAdapter cityAdapter;
    String addressPName = "", addressCName = "", addressDName = "", addressName = "", action = "";
    int proId = 3, cityId = 36, distId = 398;



    View.OnClickListener viewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((TextView)v).setTextColor(ContextCompat.getColor(baseActivity, R.color.red));
            switch (v.getId()){
                case R.id.txtProvince:
                    list.clear();
                    list.addAll(listPro);
                    cityAdapter.notifyDataSetChanged();
                    isProvince = MSG_LOAD_DATA;
                    break;
                case R.id.txtCity:
                    list.clear();
                    list.addAll(listCity);
                    cityAdapter.notifyDataSetChanged();
                    isProvince = MSG_LOAD_SUCCESS;
                    break;
                case R.id.txtDisc:
                    list.clear();
                    list.addAll(listDist);
                    cityAdapter.notifyDataSetChanged();
                    isProvince = MSG_LOAD_FAILED;
                    break;
                case R.id.txtCancel:
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        isProvince = MSG_LOAD_DATA;
                        list.clear();
                        listPro.clear();
                        listPro.clear();
                        listPro.clear();
                    }
                    break;

            }
        }
    };

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Tracer.e(TAG, position + " ");
//            listPopupWindow.dismiss();
            storeType = position + 1;
            txtStoreType.setText(typeList.get(position).getRegion_name());
            if (popupWindow != null && popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    };
    AdapterView.OnItemClickListener cityClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (isProvince) {
                case MSG_LOAD_DATA:
//                    proId = list.get(position).getRegion_id();
//                    addressPName = list.get(position).getRegion_name();
//                    Map<String, Object> proMap = Constants.getMap();
//                    proMap.put("region_type", "1");
//                    proMap.put("region_id ", proId + "");
//                    isProvince = MSG_LOAD_DATA;
//                    getDataFromNet(API.baseUrl + API.GETADDRESS, proMap, linSelectArea, MSG_LOAD_DATA);
                        addressCName = list.get(position).getRegion_name();
                        addressName = addressPName + "\t\t" + addressCName + "\t\t" + addressDName;
                        edtCardID.setText(addressName);
                        if (popupWindow != null && popupWindow.isShowing()) {
                            popupWindow.dismiss();
                        }
                    break;
                case MSG_LOAD_SUCCESS:
                    proId = list.get(position).getRegion_id();
                    addressPName = list.get(position).getRegion_name();
                    Map<String, Object> proMap = Constants.getMap();
//                    proMap.put("region_id ", proId + "");
//                    proMap.put("test ", proId + "");
//                    proMap.put("region_type", "2");
                    proMap.put("type","2");
                    proMap.put("id",proId);
                    Tracer.e(TAG, " isPro:" + isProvince +" proId:"+ proId);
//                    getDataFromNet(API.GETADDRESS, proMap, linSelectArea, MSG_LOAD_FAILED);
                    getDataFromNet("api/address/get_city", proMap, linSelectArea, MSG_LOAD_FAILED);
                    break;
                case MSG_LOAD_FAILED:
                    isProvince = MSG_LOAD_DATA;
                    cityId = list.get(position).getRegion_id();
                    Map<String, Object> cityMap = Constants.getMap();
                    cityMap.put("region_type", "3");
                    cityMap.put("region_id ", cityId + "");
                    getDataFromNet(API.GETADDRESS, cityMap, linSelectArea, MSG_LOAD_DATA);
                    break;
            }
        }
    };

    @OnClick({R.id.btnConmon, R.id.linUploadImg, R.id.linStoreType, R.id.linSelectArea})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.linSelectArea:
//                listPopupWindow.show();
                Map<String, Object> hashMap = Constants.getMap();
                hashMap.put("region_type", "1");
                isProvince = MSG_LOAD_DATA;
                popupWindow = new PopupWindowListView(getActivity(), cityClickListener,
                        cityAdapter, true, Utils.getScreenSize(baseActivity)[1] / 3, viewClick);
                getDataFromNet(API.GETADDRESS, hashMap, view, MSG_LOAD_DATA);
//                cityAdapter.notifyDataSetChanged();
//                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.linStoreType:
//                listPopupWindow.show();
                popupWindow = new PopupWindowListView(getActivity(), itemClickListener, myAdapter, false, ViewGroup.LayoutParams.WRAP_CONTENT, null);
                myAdapter.notifyDataSetChanged();
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.linUploadImg:
                new PhotoPopWindow(getActivity(), view, takePhoto);
                break;
            case R.id.btnConmon:
                name = edtAuthName.getText().toString();
                id_num = edtCardID.getText().toString();
                Map<String, Object> map = Constants.getMap();
//                map.put("token", Preferences.getString(getContext(), Preferences.TOKEN));
                map.put("store_name", name);
                map.put("store_address", id_num);
                map.put("province", proId);
                map.put("city", cityId);
                map.put("area", distId);
                map.put("store_type", storeType);
                map.put("pic", baseFile);
                getDataFromNet(API.AUTH.STORE, map, null, 0);
                break;
        }
    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final View view, final int status) {
        for (String key : hashMap.keySet()) {
            Tracer.e(TAG, "key= " + key + " and value= " + hashMap.get(key));
        }
        novate.post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void forceClose(ProgressDialog progress) {
                if (progress != null){
                    if (progress.isShowing()) {
                        progress.dismiss();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Tracer.e("OkHttp", " is ERROR");
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
                Tracer.e(TAG,jstr + " status:" + status);
                switch (status) {
                    case 0:
                        result(jstr);
                        break;
                    case MSG_LOAD_DATA:
                        if (jstr.contains("region_id")) {
                            final List<ProvinceBean> cityList = GsonHelper.jsonToArrayList(jstr, ProvinceBean.class);
                            list.clear();
                            list.addAll(cityList);
                            listPro.clear();
                            listPro.addAll(cityList);
                            isProvince = MSG_LOAD_SUCCESS;
                            cityAdapter.notifyDataSetChanged();
                            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        }else {
                            result(jstr);
                        }
                        break;
                    case MSG_LOAD_SUCCESS:
                        if (jstr.contains("region_id")) {
                            final List<ProvinceBean> cityList = GsonHelper.jsonToArrayList(jstr, ProvinceBean.class);
                            list.clear();
                            list.addAll(cityList);
                            isProvince = MSG_LOAD_FAILED;
                            listCity.clear();
                            listCity.addAll(cityList);
                            cityAdapter.notifyDataSetChanged();
                            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                        }else {
                            result(jstr);
                        }
                        break;
                    case MSG_LOAD_FAILED:
                        result(jstr);
                        break;
                }
            }
        });

    }

    void result(String jstr){
        Return rs = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
        if (rs.getCode() == 200) {
            ToastUtils.showLongToast(rs.getData());
        } else {
            ToastUtils.showLongToast(rs.getMsg());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void takeSuccess(TResult tResult) {
        showImg(tResult.getImages());
    }

    @Override
    public void takeFail(TResult tResult, String s) {

    }

    @Override
    public void takeCancel() {

    }

    private void showImg(final ArrayList<TImage> images) {
        if (images.size() > 0) {
            Tracer.e(TAG, images.size() + " path:" + images.get(0).getOriginalPath());
//            Glide.with(baseActivity).load(R.mipmap.upload_ex).into(imgAuthPhoto);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    baseFile = FileUtils.fileToBase64(new File(images.get(0).getCompressPath()));
                }
            });
//            Glide.with(this).load(new File(images.get(0).getCompressPath()))
//                    .placeholder(R.mipmap.upload_ex).error(R.mipmap.ic_launcher).into(imgAuthPhoto);
            Picasso.with(baseActivity).load(R.mipmap.upload_ex).into(imgAuthPhoto);
//            imgAuthPhoto.setImageResource(R.mipmap.upload_ex);
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

}
