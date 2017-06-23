package com.bayue.live.deqingpu.adapter;


import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.data.DataServer;
import com.bayue.live.deqingpu.entity.RecordBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * https://github.com/CymChad/BaseRecyclerViewAdapterHelper
 */
public class HeaderAndFooterAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {

    public HeaderAndFooterAdapter(int dataSize) {
        super(R.layout.item_header_and_footer);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
//        helper.setText(R.id.txtSpecCustomer)
    }


}
