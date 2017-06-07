package com.bayue.live.deqingpu.adapter;

import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.data.DataServer;
import com.bayue.live.deqingpu.entity.Status;
import com.bayue.live.deqingpu.utils.SpannableStringUtils;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 文 件 名: PullToRefreshAdapter
 * 创 建 人: Allen
 * 创建日期: 16/12/24 19:55
 * 邮   箱: AllenCoder@126.com
 * 修改时间：
 * 修改备注：
 */
public class PullToRefreshAdapter extends BaseQuickAdapter<Status, BaseViewHolder>{
    List<Status> data = new ArrayList<>();
    public PullToRefreshAdapter(List<Status> data) {
        super( R.layout.layout_animation, data);
        this.data = data;
    }
    //默认第几项
    private int mPosition = 0;

    public void setPosition(int position) {
        mPosition = position;
    }

    @Override
    protected void convert(BaseViewHolder helper, Status item) {
        if (mPosition == helper.getLayoutPosition()){
            helper.setImageResource(R.id.imgDefault,R.mipmap.icon_52);
        } else {
            helper.setImageResource(R.id.imgDefault,R.mipmap.icon_51);
        }
        helper.addOnClickListener(R.id.layDefault);
        helper.addOnClickListener(R.id.txtEdit);
        helper.addOnClickListener(R.id.txtDel);
        ((TextView)helper.getView(R.id.txtTell)).setText(helper.getLayoutPosition()+" position");
        String msg="\"He was one of Australia's most of distinguished artistes, renowned for his portraits\"";
        ( (TextView)helper.getView(R.id.txtAddressDetail)).setText(SpannableStringUtils.getBuilder(msg)
                .append("landscapes and nedes").setClickSpan(clickableSpan).create());
        ( (TextView)helper.getView(R.id.txtAddressDetail)).setMovementMethod(LinkMovementMethod.getInstance());

    }

    ClickableSpan clickableSpan = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            ToastUtils.showShortToast("事件触发了 landscapes and nedes");
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(Utils.getContext().getResources().getColor(R.color.clickspan_color));
            ds.setUnderlineText(true);
        }
    };

}
