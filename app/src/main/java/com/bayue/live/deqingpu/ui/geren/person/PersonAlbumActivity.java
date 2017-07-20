package com.bayue.live.deqingpu.ui.geren.person;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.MultiItemTypeAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.UserInfo;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.FileUtils;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.SaveObjectUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
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
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by BaYue on 2017/7/17 0017.
 * email : 2651742485@qq.com
 */

public class PersonAlbumActivity extends BaseActivity implements TakePhoto.TakeResultListener, InvokeListener {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.rvPersonAlbum)
    RecyclerView rvPersonAlbum;
    @BindView(R.id.ivTestShowPhoto)
    ImageView ivTestShowPhoto;
    @BindView(R.id.btnTestUpdate)
    Button btnTestUpdate;
    private TakePhoto takePhoto;
    private InvokeParam invokeParam;
    private String baseFile = "";
    Handler mHandler = new Handler();
    boolean isShowDel = false;

    @Override
    protected int getViewId() {
        return R.layout.ac_person_album;
    }

    SaveObjectUtils utils;
    UserInfo info;
    String TAG = "PersonAlbum";
    List<UserInfo.DataBean.UserPhoto> albumList = new ArrayList<>();
    CommonAdapter<UserInfo.DataBean.UserPhoto> albumAdapter;

    @Override
    protected void initViews() {
        topBar.setTitle(getString(R.string.tv_person_album));
        topBar.setMenuIcon(R.mipmap.icon_plus);
        utils = new SaveObjectUtils(baseContext, "Info");
        info = utils.getObject("UserInfo", UserInfo.class);
//        if (info.getData().getUser_photo() != null) {
//            albumList.addAll(info.getData().getUser_photo());
//        }
        beginGet(API.User.User_Photo, Constants.getMap(baseContext), 3, 0);
        initPopWindows();
        topBar.setMenuClickListener(new TopActionBar.MenuClickListener() {
            @Override
            public void menuClick() {
                if (isShowDel) {
                    hideDelDate();
                }
                //以某个控件的x和y的偏移量位置开始显示窗口
                popupWindow.showAsDropDown(topBar, Utils.getScreenSize(baseContext)[0] - 90, 0);
                //如果窗口存在，则更新
                popupWindow.update();
            }
        });
        //设置布局管理器
        rvPersonAlbum.setLayoutManager(new GridLayoutManager(baseContext, 3));
        albumAdapter = new CommonAdapter<UserInfo.DataBean.UserPhoto>(baseContext, R.layout.list_item_person_myalbum, albumList) {
            @Override
            protected void convert(ViewHolder holder, final UserInfo.DataBean.UserPhoto dataBean, final int position) {
                Glide.with(mContext).load(dataBean.getImg())
                        .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                        .into((ImageView) holder.getView(R.id.ivPersonItemAlbum));
                if (dataBean.isDel()) {
                    holder.setVisible(R.id.ivPersonItemDel, true);
                } else {
                    holder.setVisible(R.id.ivPersonItemDel, false);
                }
                holder.setOnClickListener(R.id.ivPersonItemDel, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String, Object> map = Constants.getMap(baseContext);
                        map.put("id", dataBean.getId());
                        beginGet(API.User.DEL_Photo, map, 1, position);
                    }
                });
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
//        albumAdapter.setListCount(albumList.size());
//        rvPersonAlbum.addItemDecoration(new SimpleDividerDecoration(baseActivity, 10,10, 0));
        rvPersonAlbum.setAdapter(albumAdapter);
    }

    private View mainView;
    PopupWindow popupWindow;
    TextView txtPopRechange, txtPopWithdraw, txtPopAll;

    void initPopWindows() {
        mainView = LayoutInflater.from(baseContext).inflate(R.layout.pop_share, null);
        txtPopRechange = (TextView) mainView.findViewById(R.id.txtPopRechange);
        txtPopWithdraw = (TextView) mainView.findViewById(R.id.txtPopWithdraw);
        txtPopAll = (TextView) mainView.findViewById(R.id.txtPopAll);
        txtPopAll.setVisibility(View.GONE);
        txtPopRechange.setText(getString(R.string.tv_person_album_update));
        txtPopWithdraw.setText(getString(R.string.tv_person_album_del));
        popupWindow = new PopupWindow(mainView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置显示隐藏动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //设置背景透明
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置默认获取焦点
        popupWindow.setFocusable(true);
        txtPopRechange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
                new PhotoPopWindow(baseActivity, v, takePhoto);
            }
        });
        txtPopWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        getDelDate();
                    }
                });
            }
        });

        popupWindow.getContentView().setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    private void hidePop() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    private void getDelDate() {
        isShowDel = true;
        List<UserInfo.DataBean.UserPhoto> tempList = new ArrayList<>();
        for (int i = 0; i < albumList.size(); i++) {
            UserInfo.DataBean.UserPhoto userPhoto = albumList.get(i);
            userPhoto.setDel(true);
            tempList.add(userPhoto);
        }
        albumList.clear();
        albumList.addAll(tempList);
        notifyData();
    }

    private void hideDelDate() {
        if (!(albumList.size() > 0)) {
            return;
        }
        isShowDel = false;
        List<UserInfo.DataBean.UserPhoto> tempList = new ArrayList<>();
        for (int i = 0; i < albumList.size(); i++) {
            UserInfo.DataBean.UserPhoto userPhoto = albumList.get(i);
            userPhoto.setDel(false);
            tempList.add(userPhoto);
        }
        albumList.clear();
        albumList.addAll(tempList);
        notifyData();
    }

    public void notifyData() {
        if (albumList != null) {
            int previousSize = albumList.size();
            albumAdapter.notifyItemRangeRemoved(0, previousSize);
            albumAdapter.notifyItemRangeInserted(0, albumList.size());
        }
    }
    public void notifyAddData() {
        if (albumList != null) {
            int previousSize = albumList.size();
            albumAdapter.notifyItemRangeInserted(0, albumList.size());
            albumAdapter.notifyItemRangeChanged(0,albumList.size());
        }
    }
    public void removeDate(int position) {
        if (albumList != null) {
            if (albumList.size() > position) {
                albumList.remove(position);
                albumAdapter.notifyItemRemoved(position);
                albumAdapter.notifyItemRangeChanged(0, albumList.size());
//                albumAdapter.notifyItemRangeRemoved(position, albumList.size());
//                albumAdapter.notifyItemRangeInserted(position, albumList.size());
            }
        }
    }

    private void beginGet(final String url, final Map<String, Object> map, final int LoadStatus, final int postion) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getDataFromNet(url, map, LoadStatus, postion);
            }
        }, 300);

    }

    private void getDataFromNet(String url, Map<String, Object> hashMap, final int LoadStatus, final int postion) {
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

            @Override
            public void onError(Throwable e) {
                if (e.getMessage() != null) {
                    Tracer.e("OkHttp", e.getMessage());
                    ToastUtils.showShortToast(e.getMessage());
                    return;
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
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                switch (LoadStatus) {
                    case 1:
                        Return r = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (r.getCode() == Constants.CODE_OK) {
                            ToastUtils.showLongToast(r.getData());
                            removeDate(postion);
                        } else {
                            ToastUtils.showLongToast(r.getMsg());
                        }
                        break;
                    case 2:
//                    case 3:
                        Return u = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
                        if (u.getCode() == Constants.CODE_OK) {
                            ToastUtils.showLongToast(u.getData());
//                            getDataFromNet(API.User.User_Photo, Constants.getMap(baseContext), 3, 0);
                            beginGet(API.User.User_Photo, Constants.getMap(baseContext), 3, 0);
                        } else {
                            ToastUtils.showLongToast(u.getMsg());
                        }
                        break;
                    case 3:
                        String data = GsonHelper.getStrFromJson(jstr, "data");
                        List<UserInfo.DataBean.UserPhoto> tempList = GsonHelper.jsonToArrayList(data, UserInfo.DataBean.UserPhoto.class);
                        Tracer.e(TAG, tempList.size() +" tempList");
                        if (tempList!=null){
                            if (tempList.size()>0){
                                albumList.clear();
                                albumList.addAll(tempList);
                            }
                        }
                        albumAdapter.notifyDataSetChanged();
//                        notifyAddData();
//                        notifyData();
                        break;
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

    @OnClick({R.id.btnTestUpdate})
    void setOnClick(View view){
        Map<String, Object> map = Constants.getMap(baseContext);
        map.put("img", baseFile);
        Constants.LogMap(map);
        beginGet(API.User.ADD_Photo, map, 2, 0);
    }

    private void showImg(final ArrayList<TImage> images) {
        if (images.size() > 0) {
            Tracer.e(TAG, images.size() + " path:" + images.get(0).getOriginalPath());
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    baseFile = FileUtils.fileToBase64(new File(images.get(0).getCompressPath()));
                    Map<String, Object> map = Constants.getMap(baseContext);
                    map.put("img", baseFile);
                    Constants.LogMap(map);
                    beginGet(API.User.ADD_Photo, map, 2, 0);
//                    HTTPUtils.getDateFormRetrofit(baseContext, map, new Callback<Return>() {
//                        @Override
//                        public void onResponse(Call<Return> call, Response<Return> response) {
//                            Tracer.e(TAG, "response:" + response.body().getCode()+" data:"+ response.body().getData()+" msg:"+ response.body().getMsg());
//                        }
//
//                        @Override
//                        public void onFailure(Call<Return> call, java.lang.Throwable t) {
//                            Tracer.e(TAG, "error:" + t.toString());
//                        }
//                    });
//                    Glide.with(baseActivity).load(new File(images.get(0).getCompressPath()))
//                            .placeholder(R.mipmap.upload_ex).error(R.mipmap.ic_launcher).into(ivTestShowPhoto);
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
