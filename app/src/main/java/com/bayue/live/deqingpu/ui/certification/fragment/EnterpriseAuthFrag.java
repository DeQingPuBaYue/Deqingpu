package com.bayue.live.deqingpu.ui.certification.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.utils.FileUtils;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.PhotoPopWindow;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/6/8.
 * email : 2651742485@qq.com
 */

public class EnterpriseAuthFrag extends BaseFragment implements TakePhoto.TakeResultListener, InvokeListener {

    String TAG = "EnterpriseAuthFrag";
    @BindView(R.id.txtRealNameTip)
    TextView txtRealNameTip;
    @BindView(R.id.edtAuthName)
    EditText edtAuthName;
    @BindView(R.id.txtCardTip)
    TextView txtCardTip;
    @BindView(R.id.edtCardID)
    EditText edtCardID;
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
    private String name;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private Novate novate;
    Handler mHandler = new Handler();
    private String baseFile = "", id_num = "";

    @Override
    protected int getViewId() {
        return R.layout.frag_auth_enterprice;
    }

    @Override
    public void setArguments(Bundle args) {
//        name = args.getString("name");
    }

    @Override
    public void init() {
        txtRealNameTip.setText(getString(R.string.tip_company_name));
        txtCardTip.setText(getString(R.string.tip_company_address));
        txtImgTip.setText(getString(R.string.tip_company_merge));
        txtTipUpImg.setText("请上传" + getString(R.string.tip_company_merge) + getString(R.string.tip_auth_reminder));
        linAuthTip.setVisibility(View.INVISIBLE);
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
    }

    @OnClick({R.id.btnConmon, R.id.linUploadImg})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.linUploadImg:
                new PhotoPopWindow(getActivity(), view, takePhoto);
                break;
            case R.id.btnConmon:
                name = edtAuthName.getText().toString();
                id_num = edtCardID.getText().toString();
                Map<String, Object> map = Constants.getMap();
                map.put("company", name);
                map.put("address", id_num);
                map.put("pic", baseFile);
                getDataFromNet(API.AUTH.COMPANY, map, null, 0);
                break;
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
                    Glide.with(baseActivity).load(new File(images.get(0).getCompressPath()))
                            .placeholder(R.mipmap.upload_ex).error(R.mipmap.ic_launcher).into(imgAuthPhoto);
                }
            });
//            Picasso.with(baseActivity).load(R.mipmap.upload_ex).into(imgAuthPhoto);
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


    private void getDataFromNet(String url, Map<String, Object> hashMap, final View view, final int status) {
        novate.post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void forceClose(ProgressDialog progress) {
                if (progress != null) {
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
                Tracer.e(TAG, jstr);
                switch (status) {
                    case 0:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == 200) {
                            ToastUtils.showLongToast(r.getData());
                        } else {
                            ToastUtils.showLongToast(r.getMsg());
                        }
                        break;

                }
            }
        });

    }

}
