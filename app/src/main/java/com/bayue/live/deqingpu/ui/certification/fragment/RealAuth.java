package com.bayue.live.deqingpu.ui.certification.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
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
import com.bayue.live.deqingpu.ui.certification.CustomHelper;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.PhotoPopWindow;
import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/12.
 * email : 2651742485@qq.com
 */

public class RealAuth extends TakePhotoFragment {
    String TAG  =  "RealAuth";
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
    @BindView(R.id.imgAuthPhoto)
    ImageView imgAuthPhoto;
    @BindView(R.id.linUploadImg)
    LinearLayout linUploadImg;
    @BindView(R.id.txtTipUpImg)
    TextView txtTipUpImg;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    Unbinder unbinder;
    private TakePhoto takePhoto;
    private CustomHelper customHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_auth_real, null);
        unbinder = ButterKnife.bind(this, view);
        customHelper=CustomHelper.of(view);
        txtRealNameTip.setText(getString(R.string.tip_real_name));
        txtCardTip.setText(getString(R.string.tip_card));
        txtImgTip.setText(getString(R.string.tip_card_hand));
        txtTipUpImg.setText("请上传本人" + getString(R.string.tip_card_hand) + getString(R.string.tip_card_hand) + getString(R.string.tip_auth_reminder));
        takePhoto = getTakePhoto();
        return view;
    }

    @OnClick({R.id.linAuthTip, R.id.btnConmon, R.id.linUploadImg})
    public void setOnClick(View view) {
        switch (view.getId()) {
            case R.id.linAuthTip:
                ImageView exImage = new ImageView(getContext());
                exImage.setImageResource(R.mipmap.upload_ex);
                new AlertDialog.Builder(getActivity()).setView(exImage).show();
                break;
            case R.id.linUploadImg:
                customHelper.onClick(view,takePhoto);
                break;
            case R.id.btnSubmit:
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() > 0) {
            Tracer.e(TAG, images.size()+ " path:" + images.get(0).getCompressPath());
//            Glide.with(baseActivity).load(R.mipmap.upload_ex).into(imgAuthPhoto);
            Glide.with(this).load(new File(images.get(0).getCompressPath()))
                    .placeholder(R.mipmap.upload_ex).error(R.mipmap.ic_launcher).into(imgAuthPhoto);
//            Picasso.with(getContext()).load(new File(images.get(0).getCompressPath())).into(imgAuthPhoto);
//            imgAuthPhoto.setImageResource(R.mipmap.upload_ex);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
