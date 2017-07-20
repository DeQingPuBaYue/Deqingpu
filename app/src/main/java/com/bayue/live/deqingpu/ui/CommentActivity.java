package com.bayue.live.deqingpu.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.CommentAdapter;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.view.ScrollViewForListView;
import com.bayue.live.deqingpu.view.TopActionBar;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Throwable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by BaYue on 2017/7/10 0010.
 * email : 2651742485@qq.com
 */

public class CommentActivity extends BaseActivity {
    @BindView(R.id.topBar)
    TopActionBar topBar;
    @BindView(R.id.listViewOneComment)
    ScrollViewForListView listViewOneComment;
    @BindView(R.id.rvSpecPro)
    RecyclerView rvSpecPro;

    CommentAdapter commentAdapter;
    int goods_id;
    private String TAG = "CommentActivity";

    @Override
    protected int getViewId() {
        return R.layout.ac_comment;
    }

    @Override
    protected void initViews() {
        topBar.setTitle("评论");
        goods_id = getIntent().getIntExtra("goods_id", -1);
        String commentUrl = API.Merchant.COMMENT_LIST;
        //评论
        Map<String, Object> commentmap = Constants.getMap();
        commentmap.put("comment_type", "1");
        commentmap.put("id_value", goods_id);
        commentmap.put("page", "1");
        beginGet(commentUrl, commentmap, 2);
    }

    private void beginGet(final String url, final Map<String, Object> map, final int type) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getDataFromNet(url, map, type);
            }
        }, 300);

    }
    private void getDataFromNet(String url, Map<String, Object> hashMap, final int type) {
        HTTPUtils.getNovate(baseContext).post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {

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
                Tracer.e(TAG + type, jstr);
                if (!jstr.contains("code")) {
                    ToastUtils.showLongToast(getString(R.string.net_user_error));
                    return;
                }
                switch (type) {
                    case 2:
                        CommentBean commentBean = (CommentBean) GsonHelper.getInstanceByJson(CommentBean.class, jstr);
                        int count = 0;
                        if (commentBean.getCount() > 0) {
                            count = commentBean.getCount();
                        }
                        List<CommentBean.DataBean> dataBeans = new ArrayList<>();
                        if (!Guard.isNull(commentBean.getData())) {
                            if (commentBean.getData().size() > 0) {
                                for (int i = 0; i < commentBean.getData().size(); i++) {
//                                    if (!Guard.isNull(commentBean.getData().get(i).getComment_img())) {
                                        dataBeans.add(commentBean.getData().get(i));
                                        break;
//                                    }
                                }
                            }
                        }
                        Tracer.e(TAG, dataBeans.size() + " dataBeans");
                        commentAdapter = new CommentAdapter(baseContext, dataBeans);
                        listViewOneComment.setAdapter(commentAdapter);

                        break;
                }
            }
        });
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
