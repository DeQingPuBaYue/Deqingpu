package com.bayue.live.deqingpu.ui.certification.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.base.MyBaseSubscriber;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;

import java.io.IOException;
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

public class StarAuthFrag extends BaseFragment {

    String TAG = "StarAuthFrag";
    @BindView(R.id.txtRealNameTip)
    TextView txtRealNameTip;
    @BindView(R.id.edtAuthName)
    EditText edtAuthName;
    @BindView(R.id.txtCardTip)
    TextView txtCardTip;
    @BindView(R.id.edtCardID)
    EditText edtCardID;
    @BindView(R.id.btnConmon)
    Button btnConmon;
    Unbinder unbinder;
    private String baseFile = "", id_num = "";
    private String name;
    private Novate novate;

    @Override
    protected int getViewId() {
        return R.layout.frag_auth_star;
    }

    @Override
    public void setArguments(Bundle args) {
//        name = args.getString("name");
    }

    @Override
    public void init() {
        txtRealNameTip.setText(getString(R.string.tip_real_name));
        txtCardTip.setText(getString(R.string.tip_star_id));
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
    }
    @OnClick({R.id.btnConmon})
    public void setOnClick(View view){
        name = edtAuthName.getText().toString();
        id_num = edtCardID.getText().toString();
        Map<String, Object> map = Constants.getMap();
        map.put("company", name);
        map.put("address", id_num);
        getDataFromNet(API.AUTH.IDENTITY, map, null, 0);
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
}
