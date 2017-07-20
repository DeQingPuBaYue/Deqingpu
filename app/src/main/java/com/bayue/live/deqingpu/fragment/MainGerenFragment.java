package com.bayue.live.deqingpu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.AountInfo;
import com.bayue.live.deqingpu.entity.UserInfo;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.AddressActivity;
import com.bayue.live.deqingpu.ui.certification.CertificationActivity;
import com.bayue.live.deqingpu.ui.denglu.DengLu;
import com.bayue.live.deqingpu.ui.geren.CartActivity;
import com.bayue.live.deqingpu.ui.geren.MyPouchActivity;
import com.bayue.live.deqingpu.ui.geren.QuanziActivity;
import com.bayue.live.deqingpu.ui.geren.person.PersonStoreActivity;
import com.bayue.live.deqingpu.ui.geren.person.PersonalActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.RefundActivity;
import com.bayue.live.deqingpu.ui.order.OrderActivity;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/5/31.
 */

public class MainGerenFragment extends BaseFragment {
    String TAG = "MainGerenFragment";
    @BindView(R.id.rl_zhuye_geren)
    RelativeLayout rlZhuyeGeren;
    @BindView(R.id.rl_quanzi_geren)
    RelativeLayout rlQuanziGeren;
    @BindView(R.id.rl_hebao_geren)
    RelativeLayout rlHebaoGeren;
    @BindView(R.id.rl_dizhi_geren)
    RelativeLayout rlDizhiGeren;
    @BindView(R.id.rl_dingdan_geren)
    RelativeLayout rlDingdanGeren;
    @BindView(R.id.rl_gouwuche_geren)
    RelativeLayout rlGouwucheGeren;
    @BindView(R.id.rl_renzheng_geren)
    RelativeLayout rlRenzhengGeren;
    @BindView(R.id.rl_dianpu_geren)
    RelativeLayout rlDianpuGeren;
    @BindView(R.id.rl_ruzhu_geren)
    RelativeLayout rlRuzhuGeren;
    @BindView(R.id.rl_jishi_geren)
    RelativeLayout rlJishiGeren;
    @BindView(R.id.rl_shezhi_geren)
    RelativeLayout rlShezhiGeren;
    Unbinder unbinder;
    @BindView(R.id.topBar)
    TopActionBar topBar;
    SaveObjectUtils utils;
    @BindView(R.id.iv_gr_touxiang)
    ImageView ivGrTouxiang;
    @BindView(R.id.iv_gr_quanzi)
    ImageView ivGrQuanzi;
    @BindView(R.id.iv_gr_hebao)
    ImageView ivGrHebao;
    @BindView(R.id.iv_gr_dizhi)
    ImageView ivGrDizhi;
    @BindView(R.id.iv_gr_dindan)
    ImageView ivGrDindan;
    @BindView(R.id.iv_gr_gouwuche)
    ImageView ivGrGouwuche;
    @BindView(R.id.iv_gr_renzheng)
    ImageView ivGrRenzheng;
    @BindView(R.id.iv_gr_dianpu)
    ImageView ivGrDianpu;
    @BindView(R.id.iv_gr_chanpin)
    ImageView ivGrChanpin;
    @BindView(R.id.iv_gr_huoyuan)
    ImageView ivGrHuoyuan;
    @BindView(R.id.iv_gr_shezhi)
    ImageView ivGrShezhi;
    @BindView(R.id.txtPersonUserName)
    TextView txtPersonUserName;
    @BindView(R.id.txtPersonUserID)
    TextView txtPersonUserID;
    private RequestManager glideRequest;

    public static MainGerenFragment newInstance(String s) {
        MainGerenFragment homeFragment = new MainGerenFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        homeFragment.setArguments(bundle);
        return homeFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.main_fragm_geren;
    }

    UserInfo info;
    @Override
    public void init() {
        Tracer.d(TAG, "");
        topBar.setTitle(getString(R.string.item_gr));
        glideRequest = Glide.with(baseActivity);
        utils = new SaveObjectUtils(baseActivity, "Info");
//        AountInfo info = utils.getObject("Info", AountInfo.class);
        info = utils.getObject("UserInfo", UserInfo.class);

        initData();
    }

    private void initData() {
        if (!Guard.isNull(info)) {
            glideRequest.load(info.getData().getUser_info().getPic())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .error(R.mipmap.vatarsample346)
                    .transform(new GlideRoundTransform(baseActivity, 5))
                    .into(ivGrTouxiang);
            txtPersonUserID.setText("客观号："+ info.getData().getUser_info().getNum());
            txtPersonUserName.setText(info.getData().getUser_info().getNik_name());
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

    @OnClick({R.id.rl_zhuye_geren, R.id.rl_quanzi_geren, R.id.rl_hebao_geren, R.id.rl_dizhi_geren, R.id.rl_dingdan_geren, R.id.rl_gouwuche_geren, R.id.rl_renzheng_geren, R.id.rl_dianpu_geren, R.id.rl_ruzhu_geren, R.id.rl_jishi_geren, R.id.rl_shezhi_geren})
    public void onViewClicked(View view) {
        if (Preferences.getString(getContext(), Preferences.TOKEN).equals("-1")) {
            startActivity(new Intent(getActivity(), DengLu.class));
            return;
        }
        switch (view.getId()) {
            case R.id.rl_zhuye_geren:
                startActivity(new Intent(baseActivity, PersonalActivity.class));
                break;
            case R.id.rl_quanzi_geren:
                startActivity(new Intent(baseActivity, QuanziActivity.class));

                break;
            case R.id.rl_hebao_geren:
                startActivity(new Intent(baseActivity, MyPouchActivity.class));
                break;
            case R.id.rl_dizhi_geren:
                startActivity(new Intent(baseActivity, AddressActivity.class));
                break;
            case R.id.rl_dingdan_geren:
                startActivity(new Intent(baseActivity, OrderActivity.class));
                break;
            case R.id.rl_gouwuche_geren:
                startActivity(new Intent(baseActivity, CartActivity.class));

                break;
            case R.id.rl_renzheng_geren:
                startActivity(new Intent(baseActivity, CertificationActivity.class));
                break;
            case R.id.rl_dianpu_geren:
//                startActivity(new Intent(baseActivity, RefundActivity.class));
                startActivity(new Intent(baseActivity, PersonStoreActivity.class));
                break;
            case R.id.rl_ruzhu_geren:
                break;
            case R.id.rl_jishi_geren:
                break;
            case R.id.rl_shezhi_geren:
                break;
        }
    }
}
