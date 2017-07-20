package com.bayue.live.deqingpu.ui.geren.person;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.MultiItemTypeAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.UserInfo;
import com.bayue.live.deqingpu.entity.menu.MenuNoteBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.ui.geren.QuanziActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.glide.GlideCircleTransform;
import com.bayue.live.deqingpu.utils.glide.GlideRoundTransform;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/7/14 0014.
 * email : 2651742485@qq.com
 */

public class PersonalActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.ivPersonBG)
    ImageView ivPersonBG;
    @BindView(R.id.ivPersonAudio)
    ImageView ivPersonAudio;
    @BindView(R.id.tvPersonEdit)
    TextView tvPersonEdit;
    @BindView(R.id.tvPersonFollow)
    TextView tvPersonFollow;
    @BindView(R.id.tvPersonFans)
    TextView tvPersonFans;
    @BindView(R.id.tvPersonCamp)
    TextView tvPersonCamp;
    @BindView(R.id.ivSpecHeadPic)
    ImageView ivSpecHeadPic;
    @BindView(R.id.tvPersonSignature)
    TextView tvPersonSignature;
    @BindView(R.id.tvPersonBirth)
    TextView tvPersonBirth;
    @BindView(R.id.tvPersonConstellation)
    TextView tvPersonConstellation;
    @BindView(R.id.ivPersonIdMerchant)
    ImageView ivPersonIdMerchant;
    @BindView(R.id.ivPersonIdAnchor)
    ImageView ivPersonIdAnchor;
    @BindView(R.id.ivPersonIdPatron)
    ImageView ivPersonIdPatron;
    @BindView(R.id.ivPersonIdBigShot)
    ImageView ivPersonIdBigShot;
    @BindView(R.id.ivPersonIdReal)
    ImageView ivPersonIdReal;
    @BindView(R.id.ivPersonIdEnterprise)
    ImageView ivPersonIdEnterprise;
    @BindView(R.id.ivPersonIdService)
    ImageView ivPersonIdService;
    @BindView(R.id.linPersonReputation)
    LinearLayout linPersonReputation;
    @BindView(R.id.rvPersonAlbum)
    RecyclerView rvPersonAlbum;
    @BindView(R.id.tvPersonLiveState)
    TextView tvPersonLiveState;
    @BindView(R.id.linPersonState)
    LinearLayout linPersonState;
    @BindView(R.id.linPersonDiary)
    LinearLayout linPersonDiary;
    @BindView(R.id.tvPersonStore)
    TextView tvPersonStore;
    @BindView(R.id.linPersonStore)
    LinearLayout linPersonStore;
    @BindView(R.id.linPersonGoods)
    LinearLayout linPersonGoods;
    @BindView(R.id.linPersonWish)
    LinearLayout linPersonWish;
    @BindView(R.id.linPersonGift)
    LinearLayout linPersonGift;
    @BindView(R.id.srPerson)
    SwipeRefreshLayout srPerson;
    SaveObjectUtils utils;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    LinearLayout linPersonAlbum;
    String TAG = "PersonalActivity";
    private RequestManager glideRequest;
    String type;
    int user_id;

    @Override
    protected int getViewId() {
        return R.layout.ac_personal;
    }

    @Override
    protected void initViews() {
        linPersonAlbum = (LinearLayout) findViewById(R.id.linPersonAlbum);
        glideRequest = Glide.with(baseActivity);
        utils = new SaveObjectUtils(baseContext, "Info");
        Map<String, Object> map = Constants.getMap(baseContext);
//        map.put("type", type);
//        map.put("user_id", user_id);
        beginGet(API.User.INIT, map, 1);
        srPerson.setOnRefreshListener(this);
        srPerson.setColorSchemeColors(Color.rgb(47, 223, 189));
        initRececler();
    }

    List<UserInfo.DataBean.UserPhoto> userPhotos = new ArrayList<>();
    CommonAdapter<UserInfo.DataBean.UserPhoto> albumAdapter;
    int pSize = 9;
    private void initRececler() {
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(baseContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvPersonAlbum.setLayoutManager(linearLayoutManager);
        albumAdapter = new CommonAdapter<UserInfo.DataBean.UserPhoto>(baseContext, R.layout.list_item_person_album, userPhotos) {
            @Override
            protected void convert(ViewHolder holder, UserInfo.DataBean.UserPhoto dataBean, int position) {
                Glide.with(mContext).load(dataBean.getImg())
                        .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                        .into((ImageView) holder.getView(R.id.ivPersonItemAlbum));
            }
        };
        albumAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(new Intent(baseContext, PersonAlbumActivity.class));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rvPersonAlbum.addItemDecoration(new DividerItemDecoration(baseActivity, DividerItemDecoration.HORIZONTAL));
        rvPersonAlbum.setAdapter(albumAdapter);
    }

    @OnClick({R.id.tvPersonEdit, R.id.ivPersonAudio, R.id.tvPersonFollow, R.id.tvPersonFans, R.id.tvPersonCamp,
        R.id.linPersonReputation, R.id.linPersonAlbum, R.id.linPersonState, R.id.linPersonDiary,
            R.id.linPersonStore, R.id.linPersonGoods, R.id.linPersonWish, R.id.linPersonGift
    })
    void setOnClick(View view){
        switch (view.getId()){
            case R.id.tvPersonEdit:
                startActivity(new Intent(baseContext, PersonEditActivity.class));
                break;
            case R.id.linPersonAlbum:
                startActivity(new Intent(baseContext, PersonAlbumActivity.class));
                break;
            case R.id.tvPersonFollow:
                ToastUtils.showShortToast(getString(R.string.tv_person_follow));
                break;
            case R.id.tvPersonFans:
                ToastUtils.showShortToast(getString(R.string.tv_person_fans));
                break;
            case R.id.tvPersonCamp:
                ToastUtils.showShortToast(getString(R.string.tv_person_camp));
                break;
                case R.id.ivPersonAudio:
                ToastUtils.showShortToast("播放音乐");
                break;
                case R.id.linPersonDiary:
                startActivity(new Intent(baseContext, QuanziActivity.class));
                break;
            case R.id.linPersonStore:
                startActivity(new Intent(baseContext, PersonStoreActivity.class));
                break;
            case R.id.linPersonGoods:
                break;
        }
    }

    private void beginGet(final String url, final Map<String, Object> map, final int LoadStatus) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(url, map, LoadStatus);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int LoadStatus) {
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                    ToastUtils.showShortToast(e.getMessage());
                    srPerson.setRefreshing(false);
                    return;
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                srPerson.setRefreshing(false);
                String jstr = null;
                try {
                    jstr = new String(responseBody.bytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Tracer.e(TAG, jstr);
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                switch (LoadStatus) {
                    case 1:
                        UserInfo info = (UserInfo) GsonHelper.getInstanceByJson(UserInfo.class, jstr);
                        if (info.getCode() == Constants.CODE_OK) {
                            //缓存用户对象
                            utils.setObject("UserInfo", info);
                            UserInfo.DataBean.UserInfoBean infoBean = new UserInfo.DataBean.UserInfoBean();
                            UserInfo.DataBean.IdentityBean IdBean = new UserInfo.DataBean.IdentityBean();
                            UserInfo.DataBean.Reputation reputation = new UserInfo.DataBean.Reputation();
                            if (info.getData().getUser_info() != null) {
                                infoBean = info.getData().getUser_info();
                                IdBean = info.getData().getIdentity();
                                reputation = info.getData().getReputation();
                                glideRequest.load(infoBean.getPhoto_wall())
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.ic_launcher_round)
//                                        .transform(new GlideRoundTransform(baseActivity, 0))
                                        .into(ivPersonBG);
                                glideRequest.load(infoBean.getPic())
                                        .placeholder(R.mipmap.ic_launcher_round)
                                        .error(R.mipmap.vatarsample346)
                                        .transform(new GlideCircleTransform(baseActivity))
                                        .into(ivSpecHeadPic);
                                tvUserName.setText(infoBean.getNik_name());
                                tvPersonFollow.setText(String.format(getString(R.string.tv_person_follow), info.getData().getAttention()+""));
                                tvPersonFans.setText(String.format(getString(R.string.tv_person_fans), info.getData().getFans()+""));
                                tvPersonCamp.setText(String.format(getString(R.string.tv_person_camp), info.getData().getCamp()+""));
                                tvPersonSignature.setText(infoBean.getSign());
                                tvPersonBirth.setText(infoBean.getBirthday());
                                tvPersonStore.setText(info.getData().getMerchant());
//                                tvPersonConstellation.setText(infoBean.get());星座
                                if (IdBean.getStore_state() == 1){
                                    setIVResource(ivPersonIdMerchant, R.mipmap.icon_id_merchant_auth);
                                }else {
                                    setIVResource(ivPersonIdMerchant, R.mipmap.icon_id_merchant_un);
                                }
                                if (IdBean.getDirect() == 1){
                                    setIVResource(ivPersonIdAnchor, R.mipmap.icon_id_anchor_auth);
                                }else {
                                    setIVResource(ivPersonIdAnchor, R.mipmap.icon_id_anchor_un);
                                }
                                if (IdBean.getStatus_user() == 1){
                                    setIVResource(ivPersonIdPatron, R.mipmap.icon_id_patron_auth);
                                }else {
                                    setIVResource(ivPersonIdPatron, R.mipmap.icon_id_patron_un);
                                }
                                if (IdBean.getIdent_state() == 1){
                                    setIVResource(ivPersonIdBigShot, R.mipmap.icon_id_bigshot_auth);
                                }else {
                                    setIVResource(ivPersonIdBigShot, R.mipmap.icon_id_bigshot_un);
                                }
                                if (reputation.getName_state() == 1){
                                    setIVResource(ivPersonIdReal, R.mipmap.icon_id_real_auth);
                                }else {
                                    setIVResource(ivPersonIdReal, R.mipmap.icon_id_real_un);
                                }
                                if (reputation.getCompany_state() == 1){
                                    setIVResource(ivPersonIdEnterprise, R.mipmap.icon_id_enterprise_auth);
                                }else {
                                    setIVResource(ivPersonIdEnterprise, R.mipmap.icon_id_enterprise_un);
                                }
                                if (reputation.getService() == 1){
                                    setIVResource(ivPersonIdService, R.mipmap.icon_id_service_auth);
                                }else {
                                    setIVResource(ivPersonIdService, R.mipmap.icon_id_service_un);
                                }
                            }

                            if (info.getData().getUser_photo()!=null){
                                userPhotos.addAll(info.getData().getUser_photo());
                            }
                            if (info.getData().getUser_photo().size()<=9){
                                pSize = userPhotos.size();
                            }
                            albumAdapter.setListCount(pSize);
                            albumAdapter.notifyDataSetChanged();
                        }
                        break;
//                    case 2:
//                    case 3:
//                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
//                        if (r.getCode() == Constants.CODE_OK){
//                            ToastUtils.showLongToast(r.getData());
//                        }else {
//                            ToastUtils.showLongToast(r.getMsg());
//                        }
//                        break;

                }
            }
        });

    }

    private void setIVResource(ImageView imageView, int resource){
        glideRequest.load(resource)
                .into(imageView);
    }

    @Override
    public void onRefresh() {
        Map<String, Object> map = Constants.getMap(baseContext);
//        map.put("type", type);
//        map.put("user_id", user_id);
        beginGet(API.User.INIT, map, 1);
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

}
