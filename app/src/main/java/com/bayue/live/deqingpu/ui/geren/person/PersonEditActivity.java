package com.bayue.live.deqingpu.ui.geren.person;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.FileUtils;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.PhotoPopWindow;
import com.bayue.live.deqingpu.view.TopActionBar;
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
import com.tamic.novate.Throwable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/7/17 0017.
 * email : 2651742485@qq.com
 */

public class PersonEditActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.linSelectHeadPic)
    LinearLayout linSelectHeadPic;
    @BindView(R.id.etPersonSignature)
    EditText etPersonSignature;
    @BindView(R.id.etPersonSex)
    EditText etPersonSex;
    @BindView(R.id.etPersonName)
    EditText etPersonName;
    @BindView(R.id.etPersonBirth)
    EditText etPersonBirth;
    @BindView(R.id.etPersonConstellation)
    EditText etPersonConstellation;
    String TAG = "PersonEdit";
    @BindView(R.id.ivPersonEditHead)
    ImageView ivPersonEditHead;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String baseFile = "";
    Handler mHandler = new Handler();

    @Override
    protected int getViewId() {
        return R.layout.ac_person_edit;
    }

    @Override
    protected void initViews() {
        topBar.setTitle("编辑资料");
        topBar.setMenuTitle(getString(R.string.tv_person_finish));
        topBar.hideMenuIcon();
        topBar.setMenuClickListener(new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {
//                ToastUtils.showShortToast(getString(R.string.tv_person_finish));
                String sign = etPersonSignature.getText().toString().trim();
                String birthday = etPersonBirth.getText().toString().trim();
                String sex = etPersonSex.getText().toString().trim();
                String nik_name = etPersonName.getText().toString().trim();
                String zodiac = etPersonConstellation.getText().toString().trim();
                Map<String, Object> map = Constants.getMap(baseContext);
                map.put("img", baseFile);
                map.put("sign", sign);
                map.put("birthday", birthday);
                map.put("sex", sex);
                map.put("nik_name", nik_name);
                map.put("zodiac", zodiac);
                getDataFromNet(API.User.Update, map);
            }
        });
    }

    @OnClick({R.id.linSelectHeadPic})
    void setOnClick(View view){
        new PhotoPopWindow(baseActivity, view, takePhoto);
    }

    private void getDataFromNet(String url, Map<String, Object> hashMap) {
        HTTPUtils.getNovate(baseActivity).post(url, hashMap, new MyBaseSubscriber<ResponseBody>(baseActivity) {

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
                Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                if (r.getCode() == 200) {
                    ToastUtils.showLongToast(r.getData());
                    finish();
                } else {
                    ToastUtils.showLongToast(r.getMsg());
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(baseActivity, type, invokeParam, this);
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
                            .placeholder(R.mipmap.upload_ex).error(R.mipmap.ic_launcher).into(ivPersonEditHead);
//                    imgAuthPhoto.setImageURI(Uri.fromFile(new File(images.get(0).getCompressPath())));
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

    @Override
    protected void setTheme() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getTakePhoto().onCreate(savedInstanceState);
    }
}
