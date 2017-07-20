package com.bayue.live.deqingpu.ui.merchant.pay;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.utils.FileUtils;
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
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by BaYue on 2017/7/4 0004.
 * email : 2651742485@qq.com
 */

public class RefundActivity extends BaseActivity  implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.linSelectRefundService)
    LinearLayout linSelectRefundService;
    @BindView(R.id.edtRefundAmount)
    EditText edtRefundAmount;
    @BindView(R.id.tvRefundAmountTip)
    TextView tvRefundAmountTip;
    @BindView(R.id.edtRefundReason)
    EditText edtRefundReason;
    @BindView(R.id.ivRefundSelectOne)
    ImageView ivRefundSelectOne;
    @BindView(R.id.ivRefundSelectTwo)
    ImageView ivRefundSelectTwo;
    @BindView(R.id.ivRefundSelectThree)
    ImageView ivRefundSelectThree;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    @BindView(R.id.tvOrderRefundRes)
    TextView tvOrderRefundRes;

    Handler mHandler = new Handler();
    private String tempFile = "";
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private PopupWindow popupWindows;
    private View contentView;
    private String TAG = "RefundActivity";
//    String[] refArray = {getString(R.string.tv_refund_return), getString(R.string.tv_refund_only)};

    @Override
    protected int getViewId() {
        return R.layout.ac_refund;
    }

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.tv_apply_refund));
        tempState = reqCodeOne;
    }

    int reqCodeOne = 0x001,reqCodeTwo = 0x002, reqCodeThree = 0x003;
    int tempState;
    @OnClick({R.id.btnConmon, R.id.linSelectRefundService, R.id.ivRefundSelectOne, R.id.ivRefundSelectTwo, R.id.ivRefundSelectThree})
    void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.linSelectRefundService:
                RefundShow();
                popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.ivRefundSelectOne:
//                PhotoShow(reqCodeOne);
//                popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                tempState = reqCodeOne;
                new PhotoPopWindow(baseActivity, view, takePhoto);
                break;
            case R.id.ivRefundSelectTwo:
//                PhotoShow(reqCodeTwo);
//                popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                tempState = reqCodeTwo;
                new PhotoPopWindow(baseActivity, view, takePhoto);
                break;
            case R.id.ivRefundSelectThree:
//                PhotoShow(reqCodeThree);
//                popupWindows.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                tempState = reqCodeThree;
                new PhotoPopWindow(baseActivity, view, takePhoto);
                break;
            case R.id.btnConmon:
                break;
        }
    }

    private void PhotoShow(final int reqCode) {
        contentView = LayoutInflater.from(baseActivity).inflate(
                R.layout.item_popupwindows, null);
        Button btnCamera = (Button) contentView.findViewById(R.id.item_popupwindows_camera);
        Button btnPhoto = (Button) contentView.findViewById(R.id.item_popupwindows_Photo);
        Button btnCancel = (Button) contentView.findViewById(R.id.item_popupwindows_cancel);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindows.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindows.dismiss();
                GalleryConfig config = new GalleryConfig.Build()
                        .limitPickPhoto(3)
                        .singlePhoto(false)
                        .hintOfPick("this is pick hint")
                        .filterMimeTypes(new String[]{"image/jpeg"})
                        .build();
                GalleryActivity.openActivity(baseActivity, reqCode, config);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindows.dismiss();
            }
        });
        initPop(contentView);
    }

    String strRefund;
    int refundState;

    private void RefundShow() {
        contentView = LayoutInflater.from(baseActivity).inflate(
                R.layout.pop_select_reason, null);
        TextView tvCancel = (TextView) contentView.findViewById(R.id.tvCancel);
        TextView tvConfirm = (TextView) contentView.findViewById(R.id.tvConfirm);
        final TextView tvRefundAmount = (TextView) contentView.findViewById(R.id.tvRefundAmount);
        final TextView tvRefundOnly = (TextView) contentView.findViewById(R.id.tvRefundOnly);
        strRefund = tvRefundAmount.getText().toString();
        tvCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindows.dismiss();
                refundState = 1;
                tvOrderRefundRes.setText(strRefund);
            }
        });
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popupWindows.dismiss();
                refundState = 2;

                tvOrderRefundRes.setText(strRefund);
            }
        });
        tvRefundAmount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                strRefund = tvRefundAmount.getText().toString();
                tvRefundAmount.setTextColor(ContextCompat.getColor(baseContext, R.color.black));
                tvRefundOnly.setTextColor(ContextCompat.getColor(baseContext, R.color.grey));
            }
        });
        tvRefundOnly.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                tvRefundAmount.setTextColor(ContextCompat.getColor(baseContext, R.color.grey));
                tvRefundOnly.setTextColor(ContextCompat.getColor(baseContext, R.color.black));
                strRefund = tvRefundOnly.getText().toString();
            }
        });
        initPop(contentView);
    }

    private void initPop(View view) {
        //设置弹出框的宽度和高度
        popupWindows = new PopupWindow(view,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindows.setFocusable(true);// 取得焦点
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindows.setTouchable(true);
        //进入退出的动画
        popupWindows.setAnimationStyle(R.style.PopupWindowAnimation);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
//        if (!Guard.isNull(data)) {
//            List<String> photos = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
//            if (photos.size()>0) {
//                switch (requestCode) {
//                    case 0x001:
//                        Glide.with(baseContext)
//                                .load(new File(photos.get(0)))
//                                .asBitmap()
//                                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
//                                .into(ivRefundSelectOne);
//                        break;
//                    case 0x002:
//                        Glide.with(baseContext)
//                                .load(new File(photos.get(0)))
//                                .asBitmap()
//                                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
//                                .into(ivRefundSelectTwo);
//                        break;
//                    case 0x003:
//                        ivRefundSelectThree.setImageURI(Uri.fromFile(new File(photos.get(0))));
//                        Glide.with(baseContext)
//                                .load(new File(photos.get(0)))
//                                .asBitmap()
//                                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
//                                .into(ivRefundSelectThree);
//                        break;
//                }
//            }
//        }
        //list of videos of seleced
//        List<String> vides = (List<String>) data.getSerializableExtra(GalleryActivity.VIDEO);
    }

    @Override
    protected void setTheme() {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(baseActivity, type, invokeParam, this);
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
            Tracer.e(TAG, images.size()+ " path:" + images.get(0).getOriginalPath());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    tempFile = FileUtils.fileToBase64(new File(images.get(0).getCompressPath()));
                    ImageView tempView = null;
                    switch (tempState){
                        case 0x001:
                            tempView = ivRefundSelectOne;
                            break;
                        case 0x002:
                            tempView = ivRefundSelectTwo;
                            break;
                        case 0x003:
                            tempView = ivRefundSelectThree;
                            break;
                    }
                    Glide.with(baseActivity).load(new File(images.get(0).getCompressPath()))
                            .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher).into(tempView);
//                    ivRefundSelectOne.setImageURI(Uri.fromFile(new File(images.get(0).getCompressPath())));
                }
            });

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        getTakePhoto().onCreate(savedInstanceState);
    }
}
