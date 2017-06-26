package com.bayue.live.deqingpu.ui.geren;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.adapter.CommentAdapter;
import com.bayue.live.deqingpu.adapter.cart.recyclerAdapter;
import com.bayue.live.deqingpu.adapter.common.CommonAdapter;
import com.bayue.live.deqingpu.adapter.common.base.ViewHolder;
import com.bayue.live.deqingpu.base.BaseActivity;
import com.bayue.live.deqingpu.base.HTTPUtils;
import com.bayue.live.deqingpu.data.Constants;
import com.bayue.live.deqingpu.entity.CommentBean;
import com.bayue.live.deqingpu.entity.GoodsDetail;
import com.bayue.live.deqingpu.entity.Return;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.http.API;
import com.bayue.live.deqingpu.preferences.Preferences;
import com.bayue.live.deqingpu.ui.denglu.DengLu;
import com.bayue.live.deqingpu.ui.merchant.MerchantGoodsDetailActivity;
import com.bayue.live.deqingpu.ui.merchant.pay.PayActivity;
import com.bayue.live.deqingpu.utils.GsonHelper;
import com.bayue.live.deqingpu.utils.Guard;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.ToolKit;
import com.bayue.live.deqingpu.utils.Tracer;
import com.bayue.live.deqingpu.utils.Utils;
import com.bayue.live.deqingpu.view.AmountView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tamic.novate.BaseSubscriber;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.bayue.live.deqingpu.utils.Utils.getContext;

/**
 * Created by Administrator on 2017/6/20.
 */

public class CartActivity extends BaseActivity {

    String TAG = "CartActivity";
    @BindView(R.id.ll_back_title)
    LinearLayout llBackTitle;
    @BindView(R.id.tv_titletext_title)
    TextView tvTitletextTitle;
    @BindView(R.id.tv_rigthtext_title)
    TextView tvRigthtextTitle;
    @BindView(R.id.ll_rigthtext_title)
    LinearLayout llRigthtextTitle;
    @BindView(R.id.ll_quanxuan_cart)
    LinearLayout llQuanxuanCart;
    @BindView(R.id.tv_zongjine_cart)
    TextView tvZongjineCart;
    @BindView(R.id.ll_lijigoumai_cart)
    LinearLayout llLijigoumaiCart;
    @BindView(R.id.rlv_out_cart)
    RecyclerView rlvOutCart;
    @BindView(R.id.iv_quanxuan_cart)
    ImageView ivQuanxuanCart;
    @BindView(R.id.ll_jine_cart)
    LinearLayout llJineCart;
    @BindView(R.id.tv_lijigoumai_cart)
    TextView tvLijigoumaiCart;
    private Novate novate;

    private recyclerAdapter adapter;
    private RecyclerView.LayoutManager manager;

    ArrayList<CartOutBean.DataBean> outList = new ArrayList<>();

    private void setOutList(ArrayList<CartOutBean.DataBean> outList) {
        for (int i = 0; i < outList.size(); i++) {

            outList.get(i).setSelected(true);
            outList.get(i).setEditor(false);

            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
                outList.get(i).getGoods_info().get(j).setSubselected(true);
                outList.get(i).getGoods_info().get(j).setSubeditor(false);
            }
        }
    }

    private void reverseQuanxue(boolean b) {
        for (int i = 0; i < outList.size(); i++) {
            outList.get(i).setSelected(b);
            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
                outList.get(i).getGoods_info().get(j).setSubselected(b);
            }
        }
        UpdateRecyclerView();
    }

    private void reverseBanji(boolean b) {
        for (int i = 0; i < outList.size(); i++) {
            outList.get(i).setEditor(b);
            for (int j = 0; j < outList.get(i).getGoods_info().size(); j++) {
                outList.get(i).getGoods_info().get(j).setSubeditor(b);
            }
        }
        UpdateRecyclerView();
    }


    public static boolean selected = true;
    public static boolean editor = false;

    @Override
    protected int getViewId() {
        return R.layout.cart_activity;
    }

    @Override
    protected void initViews() {
        tvTitletextTitle.setText("我的购物车");
        tvRigthtextTitle.setText("编辑");
        novate = new Novate.Builder(baseActivity)
                //.addParameters(parameters)//公共参数
                .connectTimeout(5)
                .writeTimeout(10)
                .baseUrl(API.baseUrl)
//                .addHeader(headers)//添加公共请求头//.addApiManager(ApiManager.class)
                .addLog(true)
                .build();
        Map<String,Object> map=Constants.getMap(baseContext);
//        map.put("token", token);
        map.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoyMCwibmlrX25hbWUiOm51bGx9.2QDDeG63O4SDRJ6_jqu2iGIB9D9VWZOrq18bhajUIrA");
        beginGet(API.Cart.CART_LIST, map, 1);
        InitializationPopWindow();
        manager = new LinearLayoutManager(this);
        rlvOutCart.setLayoutManager(manager);
        //优化性能
        rlvOutCart.setHasFixedSize(true);
//        addOutList();
        adapter = new recyclerAdapter(this,outList);
        rlvOutCart.setItemAnimator(new DefaultItemAnimator());
        rlvOutCart.setAdapter(adapter);
        adapter.setAllSelected(new recyclerAdapter.AllSelected() {
            @Override
            public void allSelected(int position) {
                if (outList.get(position).isSelected()) {

                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubselected(outList.get(position).isSelected());
                    }
                    UpdateRecyclerView();
                } else {
                    outList.get(position).setSelected(!outList.get(position).isSelected());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubselected(outList.get(position).isSelected());
                    }
                    UpdateRecyclerView();
                }

            }

            @Override
            public void deitor(int position) {
                if (outList.get(position).isEditor()) {
                    Log.e("是否11111===", outList.get(position).isEditor() + "");
                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubeditor(outList.get(position).isEditor());
                        Log.e("子item是否==22=", outList.get(position).getGoods_info().get(i).isSubeditor() + "");
                    }
                    UpdateRecyclerView();
                } else {
                    Log.e("是否22222===", outList.get(position).isEditor() + "");
                    outList.get(position).setEditor(!outList.get(position).isEditor());
                    int s = outList.get(position).getGoods_info().size();
                    for (int i = 0; i < s; i++) {
                        outList.get(position).getGoods_info().get(i).setSubeditor(outList.get(position).isEditor());
                        Log.e("子item是否==22=", outList.get(position).getGoods_info().get(i).isSubeditor() + "");
                    }
                    UpdateRecyclerView();
                }
            }
        });


    }

    public void UpdateRecyclerView() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
        DecimalFormat format = new DecimalFormat("#.##");
        tvZongjineCart.setText(format.format(getMoney()));

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

    @OnClick({R.id.ll_back_title, R.id.ll_rigthtext_title, R.id.ll_quanxuan_cart, R.id.ll_lijigoumai_cart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back_title:
                finish();
                break;
            case R.id.ll_rigthtext_title:
                setBianji();
                break;
            case R.id.ll_quanxuan_cart:
                setQuanXuan();

                break;
            case R.id.ll_lijigoumai_cart:
                startActivity(new Intent(CartActivity.this, CartConfirmActivity.class));

                break;
        }
    }

    private void setQuanXuan() {
        if (selected) {
            ivQuanxuanCart.setImageResource(R.drawable.icon_39);
            selected = false;
            reverseQuanxue(selected);
        } else {
            ivQuanxuanCart.setImageResource(R.drawable.icon_38);
            selected = true;
            reverseQuanxue(selected);
        }
    }

    private void setBianji() {
        if (editor) {

            editor = false;
            tvRigthtextTitle.setText("编辑");
//            reverseBanji(editor);
            llJineCart.setVisibility(View.VISIBLE);
            tvLijigoumaiCart.setText("立即购买");
        } else {
            tvRigthtextTitle.setText("完成");
            editor=true;
            reverseBanji(!editor);
            llJineCart.setVisibility(View.INVISIBLE);
            tvLijigoumaiCart.setText("删除");


        }
    }
    double tatolMoney=0.0;
    //总金额
    public double getMoney(){
        tatolMoney=0.0;
        for (int i = 0; i <outList.size(); i++) {
           List<CartOutBean.DataBean.GoodsInfoBean> goodsInfoBean= outList.get(i).getGoods_info();
            double money=0;
            for (int j = 0; j < goodsInfoBean.size(); j++) {
                if(goodsInfoBean.get(j).isSubselected()){

                 Double d=Double.parseDouble(goodsInfoBean.get(j).getShop_price())*goodsInfoBean.get(j).getGoods_number();
                    money+=d;
                }
            }
            outList.get(i).setMoney(money);
            tatolMoney+=outList.get(i).getMoney();
        }

        return tatolMoney;

    }
    private void setPopData(int goods_id) {


    }
    private static GoodsDetail speValue;
    public void setSpeValue(GoodsDetail value){
        speValue = value;
    }
    public GoodsDetail getSpeValue(){
        return speValue;
    }

    private void getPrice(){
        String attrId = stringBuilder.toString();
        if (attrId.endsWith(",")){
            attrId = attrId.substring(0,attrId.length()-1);
        }
        Map<String, Object> mapPrice = Constants.getMap();
        mapPrice.put("rec_id", attrId);
        beginGet(API.Cart.CART_PRICE, mapPrice, 3);
    }
    private PopupWindow popupWindows;
    private View contentView;
    LinearLayout linSpecBtn;
    TextView txtSpecSelectValue, txtSpecSelectPrice, txtSpecSelectAdd, txtSpecSelectBuy, txtSpecSelectConfirm;
    ImageView imgSpecSelectCancel, ivSpecHeadPic;
    RecyclerView rvSpecSelect;
    AmountView amountView;
    CommonAdapter<GoodsDetail.DataBean.SpeBean> popAdapter;
    StringBuilder stringBuilder = new StringBuilder("");
    List<GoodsDetail.DataBean.SpeBean> speBeanList = new ArrayList<>();
    List<GoodsDetail.DataBean.SpeBean> multiList = new ArrayList<>();
    List<GoodsDetail.DataBean.SpeBean> radioList = new ArrayList<>();
    int tempSelectId, tempTagId, addNumber = 1 ,rvPosition;
    double price, tempSelectPrice;
    void InitializationPopWindow(){
        //加载弹出框的布局
        contentView = LayoutInflater.from(baseActivity).inflate(
                R.layout.pop_spec_select, null);
        //绑定控件
        linSpecBtn = (LinearLayout) contentView.findViewById(R.id.linSpecBtn);
        txtSpecSelectValue = (TextView) contentView.findViewById(R.id.txtSpecSelectValue);
        txtSpecSelectPrice = (TextView) contentView.findViewById(R.id.txtSpecSelectPrice);
        amountView = (AmountView) contentView.findViewById(R.id.amount_view);
        txtSpecSelectAdd = (TextView) contentView.findViewById(R.id.txtSpecSelectAdd);
        txtSpecSelectBuy = (TextView) contentView.findViewById(R.id.txtSpecSelectBuy);
        txtSpecSelectConfirm = (TextView) contentView.findViewById(R.id.txtSpecSelectConfirm);
        imgSpecSelectCancel = (ImageView) contentView.findViewById(R.id.imgSpecSelectCancel);
        ivSpecHeadPic = (ImageView) contentView.findViewById(R.id.ivSpecHeadPic);
        rvSpecSelect = (RecyclerView) contentView.findViewById(R.id.rvSpecSelect);
        rvSpecSelect.setLayoutManager(new LinearLayoutManager(baseActivity));
        txtSpecSelectConfirm.setVisibility(View.VISIBLE);
        linSpecBtn.setVisibility(View.INVISIBLE);
        popAdapter = new CommonAdapter<GoodsDetail.DataBean.SpeBean>(baseContext, R.layout.pop_spec_item_rv_spec, speBeanList) {
            @Override
            protected void convert(ViewHolder holder, final GoodsDetail.DataBean.SpeBean bean, int position) {
                holder.setText(R.id.txtSpecProName,bean.getName());
                final TagFlowLayout mFlowLayout = holder.getView(R.id.flowlayout);
                int count; boolean isMulti = false;
                if (bean.getAttr_type()==1){
                    count = 1;
                    isMulti = false;
                }else {
                    count = -1;
                    isMulti = true;
                }
                mFlowLayout.setMaxSelectCount(count);
                final List<GoodsDetail.DataBean.SpeBean.ValuesBean> valuesList = bean.getValues();
                mFlowLayout.setAdapter(new TagAdapter<GoodsDetail.DataBean.SpeBean.ValuesBean>(valuesList)
                {
                    @Override
                    public View getView(FlowLayout parent, int position, GoodsDetail.DataBean.SpeBean.ValuesBean valuesBean)
                    {
                        TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                                mFlowLayout, false);
                        tv.setText(valuesBean.getLabel());
                        return tv;
                    }
                });
                final boolean finalIsMulti = isMulti;
                if (finalIsMulti){
                    mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener()
                    {
                        @Override
                        public void onSelected(Set<Integer> selectPosSet)
                        {
                            multiList.clear();
                            StringBuilder tempBuilder = new StringBuilder();
                            Iterator iterator=selectPosSet.iterator();
                            while (iterator.hasNext()){
                                String spe =  iterator.next().toString();
                                int pos = Integer.parseInt(spe);
                                tempSelectId = valuesList.get(pos).getId();
//                                tempPrice += Double.parseDouble(valuesList.get(Integer.parseInt(spe)).getPrice());
                                tempBuilder.append(tempSelectId+",");
                                GoodsDetail.DataBean.SpeBean speBean = new GoodsDetail.DataBean.SpeBean();
                                speBean.setAttr_type(bean.getAttr_type());
                                speBean.setName(bean.getName());
                                List<GoodsDetail.DataBean.SpeBean.ValuesBean> valuesBeanList = new ArrayList<>();
                                valuesBeanList.add(bean.getValues().get(pos));
                                speBean.setValues(valuesBeanList);
                                multiList.add(speBean);
                            }
                            stringBuilder = tempBuilder;
                            tempTagId = 0;
                            getPrice();
                        }
                    });
                }else {
                    mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                        @Override
                        public boolean onTagClick(View view, int position, FlowLayout parent) {
//                            txtSpecSelectPrice.setText("￥" + price);
                            amountView.setDefault(1);
                            radioList.clear();
                            if (stringBuilder.length()>0) {
                                if (tempTagId > 0) {
                                    String tempStr = tempTagId + "";
                                    Tracer.e(TAG, "length:" + stringBuilder.length() + " delete:" + (stringBuilder.length() - tempStr.length()) + " -> " + (stringBuilder.length() - 1));
                                    stringBuilder.delete((stringBuilder.length() - tempStr.length()), stringBuilder.length());
                                }

                                tempTagId = valuesList.get(position).getId();
                                stringBuilder.append(tempTagId);
                                GoodsDetail.DataBean.SpeBean speBean = new GoodsDetail.DataBean.SpeBean();
                                speBean.setAttr_type(bean.getAttr_type());
                                speBean.setName(bean.getName());
                                List<GoodsDetail.DataBean.SpeBean.ValuesBean> valuesBeanList = new ArrayList<>();
                                valuesBeanList.add(bean.getValues().get(position));
                                speBean.setValues(valuesBeanList);
                                radioList.add(speBean);
                                getPrice();
                            }else {
                                ToastUtils.showLongToast("请选择至少一种"+bean.getName());
                            }
                            Tracer.e(TAG, price +" price");
                            return true;
                        }
                    });
                }
            }
        };
        rvSpecSelect.setAdapter(popAdapter);
        //设置弹出框的宽度和高度
        popupWindows = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindows.setFocusable(true);// 取得焦点
//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        //点击外部消失
//        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindows.setTouchable(true);
        //进入退出的动画
        popupWindows.setAnimationStyle(R.style.PopupWindowAnimation);
        // 按下android回退物理键 PopipWindow消失解决
        ivSpecHeadPic.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    hidePop();
                    return true;
                }
                return false;
            }
        });
        imgSpecSelectCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
                stringBuilder = new StringBuilder();
            }
        });

        txtSpecSelectConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hidePop();
                List<CartOutBean.DataBean.GoodsInfoBean.GoodsAttrBean> goodsAttrBeans=new ArrayList<CartOutBean.DataBean.GoodsInfoBean.GoodsAttrBean>();

                List<GoodsDetail.DataBean.SpeBean> speBeanList = new ArrayList<>();
                speBeanList.addAll(multiList);
                speBeanList.addAll(radioList);
                for (int i = 0; i < speBeanList.size(); i++) {
//                    if(i==0){
                        speBeanList.get(i).getName();
                        for (int j = 0; j <speBeanList.get(i).getValues().size() ; j++) {
                            String attrname=speBeanList.get(i).getValues().get(j).getLabel();
                            CartOutBean.DataBean.GoodsInfoBean.GoodsAttrBean goodsAttrBean=new CartOutBean.DataBean.GoodsInfoBean.GoodsAttrBean();

                            int attrId=speBeanList.get(i).getValues().get(j).getId();
                            goodsAttrBean.setGoods_attr_id(attrId);
                            goodsAttrBean.setAttr(attrname);
                            goodsAttrBeans.add(goodsAttrBean);
                        }
//                    }
                }

                outList.get(postion).getGoods_info().get(subpostion).getGoods_attr().clear();
                outList.get(postion).getGoods_info().get(subpostion).getGoods_attr().addAll(goodsAttrBeans);

                adapter.notifyItemChanged(rvPosition);
            }
        });
    }
    int postion;
    int subpostion;

    public void showPop(int goods_id,int postion, int subpostion){
        this.postion=postion;
        this.subpostion=subpostion;

        speBeanList.clear();
        Map<String, Object> map = Constants.getMap();
        map.put("goods_id", goods_id);
        beginGet(API.Cart.CART_ATTR, map, 2);
        popupWindows.showAtLocation(rlvOutCart, Gravity.BOTTOM, 0, 0);
    }

    private void hidePop(){
        if (popupWindows != null && popupWindows.isShowing()) {
            popupWindows.dismiss();
        }
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
        novate.post(url, hashMap, new BaseSubscriber<ResponseBody>(baseActivity) {
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
                switch (type){
                    case 1:
                        CartOutBean bean= (CartOutBean) GsonHelper.getInstanceByJson(CartOutBean.class, jstr);
                        outList.addAll(bean.getData());
                        setOutList(outList);
                        CartActivity.this.UpdateRecyclerView();
                        break;
                    case 2:
                        String json = GsonHelper.getStrFromJson(jstr, "data");
                        speBeanList.addAll(GsonHelper.jsonToArrayList(json, GoodsDetail.DataBean.SpeBean.class));
                        popAdapter.notifyDataSetChanged();
                        final GoodsDetail detail = getSpeValue();
                        if (Guard.isNull(detail)){
                            return;
                        }
                        txtSpecSelectValue.setText(detail.getData().getGoods_name());
                        if (!Guard.isNull(detail.getData().getMerchant_price())) {
                            price = Double.parseDouble(detail.getData().getMerchant_price());
                            txtSpecSelectPrice.setText("￥"+detail.getData().getMerchant_price());
                        }
                        Glide.with(baseContext).load(detail.getData().getGoods_thumb())
                                .placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round)
                                .into(ivSpecHeadPic);
                        amountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
                            @Override
                            public void onAmountChange(View view, int amount) {
                                addNumber = amount;
                                if (amount>0) {
//                    txtSpecSelectPrice.setText("￥"+ amount * price);
//                    getPrice();
                                }
                            }
                        });
//                        Return o = (Return) GsonHelper.getInstanceByJson(Return.class, jstr);
//                        if (o.getCode() == Constants.CODE_OK){
//                            ToastUtils.showLongToast(o.getData());
//                            hidePop();
//                        }
                        break;
                }
            }
        });
    }
}
