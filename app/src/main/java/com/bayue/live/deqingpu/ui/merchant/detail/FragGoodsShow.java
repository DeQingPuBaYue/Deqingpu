package com.bayue.live.deqingpu.ui.merchant.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.base.LazyLoadFragment;
import com.bayue.live.deqingpu.data.Constants;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BaYue on 2017/6/19.
 * email : 2651742485@qq.com
 */

public class FragGoodsShow extends LazyLoadFragment {

    Unbinder unbinder;
    @BindView(R.id.html_text)
    HtmlTextView htmlText;

    public static FragGoodsShow newInstance(String s) {
        FragGoodsShow viewPagerFragment = new FragGoodsShow();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.ARGS, s);
        viewPagerFragment.setArguments(bundle);
        return viewPagerFragment;
    }

    @Override
    protected int getViewId() {
        return R.layout.frag_goods_html;
    }

    @Override
    protected void lazyLoad() {
//        String htmlData = getArguments().getString(Constants.ARGS);
//        webGoodsDetail.loadDataWithBaseURL(null, htmlData, "text/html", "utf-8", null);
        htmlText.setHtml(getArguments().getString(Constants.ARGS),
                new HtmlHttpImageGetter(htmlText));
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
