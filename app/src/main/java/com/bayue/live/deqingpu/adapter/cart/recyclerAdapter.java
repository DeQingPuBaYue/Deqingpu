package com.bayue.live.deqingpu.adapter.cart;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;
import com.bayue.live.deqingpu.ui.geren.CartActivity;
import com.bayue.live.deqingpu.utils.ToastUtils;
import com.bayue.live.deqingpu.utils.Tracer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyHolder> {

    ArrayList<CartOutBean.DataBean> list;

    CartActivity context;

    public recyclerAdapter(CartActivity context,ArrayList<CartOutBean.DataBean> list) {
        this.list = list;
        this.context=context;
    }

    public class MyHolder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name,bianji;
        RecyclerView rlv_in;

        private recyclerAdapter1 adapter;
        private RecyclerView.LayoutManager manager;



        public MyHolder(View itemView) {
            super(itemView);
            img= (ImageView) itemView.findViewById(R.id.iv_ziyingquan_cart);
            name= (TextView) itemView.findViewById(R.id.tv_name_cartitem);
            bianji= (TextView) itemView.findViewById(R.id.tv_ziyingbianji_cart);
            rlv_in= (RecyclerView) itemView.findViewById(R.id.rlv_in_cart);
            manager = new LinearLayoutManager(itemView.getContext());
            rlv_in.setLayoutManager(manager);
        }
    }



    @Override
    public recyclerAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_out,null);
        MyHolder holder=new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final recyclerAdapter.MyHolder holder, final int position) {
        final CartOutBean.DataBean databean=list.get(position);

        final List<CartOutBean.DataBean.GoodsInfoBean> subList= databean.getGoods_info();


        holder.name.setText(databean.getMerchant_name());


        holder.adapter = new recyclerAdapter1(context,subList);
        holder.rlv_in.setItemAnimator(new DefaultItemAnimator());
        holder.rlv_in.setAdapter(holder.adapter);
        holder.adapter.setSelected(new recyclerAdapter1.Selected() {
            @Override
            public void Selected(int subposition) {
                if(subList.get(subposition).isSubselected()){
                    subList.get(subposition).setSubselected(!subList.get(subposition).isSubselected());
                    int b=0;
                    for (int i = 0; i <subList.size() ; i++){
                        if(!subList.get(i).isSubselected()){
                            b++;
                        }
                    }
                    Log.e("b1111====",b+"");
                    if(b==subList.size()){
                        list.get(position).setSelected(subList.get(subposition).isSubselected());
                        Log.e("父项1111====",list.get(position).isSelected()+"");
                    }

                }else {
                    subList.get(subposition).setSubselected(!subList.get(subposition).isSubselected());
                    int b=0;
                    for (int i = 0; i <subList.size() ; i++){
                        if(subList.get(i).isSubselected()){
                            b++;
                        }
                    }
                    Log.e("b2222====",b+"");
                    if(b==subList.size()){
                        list.get(position).setSelected(subList.get(subposition).isSubselected());
                        Log.e("父项2222====",list.get(position).isSelected()+"");
                    }


                }
                //跟新数据
//                recyclerAdapter.this.notifyDataSetChanged();

               context.UpdateRecyclerView();
            }

            @Override
            public void deitor(int position) {
                Tracer.e("recyclerA", position +" position");
            }

            @Override
            public void setAmountView(int amount,int subposition) {
                databean.getGoods_info().get(subposition).setGoods_number(amount);
//                context.UpdateRecyclerView();

            }

            @Override
            public void delItem(int itemPosition, String recId) {
                Tracer.e("CartAdapter", "A:"+itemPosition + " size:"+ subList.size());
                subList.remove(itemPosition);
                holder.adapter.notifyItemRemoved(itemPosition);
                //必须调用这行代码 否则下标越界
//                notifyItemRangeChanged(itemPosition, subList.size());
                if(position != subList.size()){ // 如果移除的是最后一个，忽略
                    notifyItemRangeChanged(position, subList.size() - position);
                }
                context.delCartById(itemPosition, recId, true);
            }

            @Override
            public void setAttr(int subposition,String goodsId) {
                Tracer.e("recyclerA", goodsId +" goodsId");
                context.showPop(Integer.parseInt(goodsId.trim()),position,subposition);

            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allSelected!=null){

                    allSelected.allSelected(position);
                }
            }
        });
        holder.bianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(allSelected!=null){

                    allSelected.deitor(position);
                }
            }
        });

        if(list.get(position).isSelected()){
            holder.img.setImageResource(R.drawable.icon_38);

        }else {
            holder.img.setImageResource(R.drawable.icon_39);
        }

        if(!list.get(position).isEditor()){
            holder.bianji.setText("编辑");
        }else {
            holder.bianji.setText("完成");

        }
    }

    @Override
    public int getItemCount(){
        return list!=null?list.size():0;
    }

    private AllSelected allSelected;
    public void setAllSelected( AllSelected allSelected){
        this.allSelected=allSelected;

    }

    public void addData(int position) {
//        list.add(position, "Insert One");
        notifyItemInserted(position);  //插入

    }

    public void removeData(int position) {
        list.remove(position);
        notifyItemRemoved(position);  //删除
    }

    public interface AllSelected{

        void allSelected(int position);

        void deitor(int position);

    }


}
