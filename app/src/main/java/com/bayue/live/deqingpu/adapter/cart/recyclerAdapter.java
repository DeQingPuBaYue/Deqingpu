package com.bayue.live.deqingpu.adapter.cart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bayue.live.deqingpu.R;
import com.bayue.live.deqingpu.entity.cart.CartOutBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/21.
 */

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyHolder> {

    ArrayList<CartOutBean> outList=new ArrayList<>();

    public recyclerAdapter(ArrayList<CartOutBean> outList) {
        this.outList = outList;
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
    public void onBindViewHolder(recyclerAdapter.MyHolder holder, final int position) {

        holder.adapter = new recyclerAdapter1();
        holder.rlv_in.setAdapter(holder.adapter);
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
        if(!outList.get(position).isSelected()){
            holder.img.setImageResource(R.drawable.icon_38);

        }else {
            holder.img.setImageResource(R.drawable.icon_39);
        }
        if(!outList.get(position).isEditor()){
            holder.bianji.setText("编辑");
        }else {
            holder.bianji.setText("完成");

        }
    }

    @Override
    public int getItemCount(){
        return outList!=null?outList.size():0;
    }

    private AllSelected allSelected;
    public void setAllSelected( AllSelected allSelected){
        this.allSelected=allSelected;

    }
    public interface AllSelected{

        void allSelected(int position);

        void deitor(int position);

    }

}
