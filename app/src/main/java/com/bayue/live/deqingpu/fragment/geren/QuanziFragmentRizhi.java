package com.bayue.live.deqingpu.fragment.geren;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.RizhiAdapter;
import com.bayue.live.deqingpu.base.BaseFragment;
import com.bayue.live.deqingpu.entity.geren.FabuBean;
import com.bayue.live.deqingpu.entity.geren.RizhiBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.geren.QuanziActivity;
import com.bayue.live.deqingpu.utils.DensityUtil;
import com.bayue.live.deqingpu.utils.OKHttpUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/9.
 */

public class QuanziFragmentRizhi extends BaseFragment {


    @BindView(R.id.lv_rizhi)
    ListView lvRizhi;
    Unbinder unbinder;
    List<RizhiBean.DataBean> data;
    RizhiAdapter adapter;
    @Override
    protected int getViewId() {
        return R.layout.geren_fragm_quanzi_rizhi;
    }

    @Override
    public void init() {

         adapter=new RizhiAdapter(getContext(),data);
        lvRizhi.setAdapter(adapter);
        getRizhi();
        Log.e("onCreateView====","onCreateView()");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("start====","onStart()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    private  void getRizhi(){


        RequestBody body = new FormBody.Builder()
                .add("apiversion","v.1.0")
                .add("safecode","BaYue.deqingpu")
                .add("page","")
                .add("token", Preferences.getString(getContext(),Preferences.TOKEN))
                .build();
        Request request = new Request.Builder()
                .url(API.baseUrl+API.QUANZI_LIEBIAO)
                .post(body)
                .build();
        OKHttpUtils.enqueue(request, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String msg = response.body().string();
                if (response.code() == 200){
                    Gson gson = new Gson();
                    final RizhiBean rizhiBean= gson.fromJson(msg,RizhiBean.class);

                    Log.e(">>>>",rizhiBean.getCode()+"");
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            if (rizhiBean.getCode()==200){
                                Log.e(">>>>","日志——————");
                                data=rizhiBean.getData();
                                adapter.notifyDataSetChanged();
//                                DensityUtil.showToast(getContext(),fabuBean.getData());




                            }else {
                                DensityUtil.showToast(getContext(),rizhiBean.getMsg());
                                Log.e(">>>>",rizhiBean.getMsg());
                            }

                        }
                    });
                }else {
                    ToolKit.runOnMainThreadSync(new Runnable() {
                        @Override
                        public void run() {
                            DensityUtil.showToast(getContext(),response.message());
                        }
                    });
                }
            }
        });

    }
}
