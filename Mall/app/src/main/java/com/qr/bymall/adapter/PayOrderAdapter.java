package com.qr.bymall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.activity.DrawbackActivity;
import com.qr.bymall.bean.OrderList;
import com.qr.bymall.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/23.
 */

public class PayOrderAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<OrderList.DataBean> list;



    public PayOrderAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    //添加数据
    public void addList(List<OrderList.DataBean> list) {
        if (this.list.containsAll(list)) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    //更新数据
    public void updateList(List<OrderList.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }
    public void remove(OrderList.DataBean dataBean) {
        this.list.remove(dataBean);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (OrderList.DataBean) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, OrderList.DataBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_pay_order, parent,false);
        ViewHodler vh = new ViewHodler(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler mHodler = (ViewHodler) holder;
        mHodler.lv.setClickable(false);
        mHodler.lv.setPressed(false);
        mHodler.lv.setEnabled(false);
        mHodler.tv_store.setText(list.get(position).getShop_name());
        mHodler.tv_total_price.setText(list.get(position).getTotal_price()+"");
        mHodler.tv_fee.setText("（含配送费 ¥"+list.get(position).getExpress_fee());
        mHodler.tv_total_num.setText("共计"+list.get(position).getProduct_num()+"件商品");
        NeedPayItemAdapter adapter=new NeedPayItemAdapter(context,list.get(position).getProduct());
        mHodler.lv.setAdapter(adapter);

        mHodler.tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DrawbackActivity.class);
                intent.putExtra("id",list.get(position).getOrder_id());
                context.startActivity(intent);
            }

        });

        mHodler.itemView.setTag(list.get(position));

    }



    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView tv_store,tv_total_price,tv_total_num,tv_cancel,tv_fee;
        private MyListView lv;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_store = (TextView) itemView.findViewById(R.id.tv_list_pay_store);
            tv_total_price = (TextView) itemView.findViewById(R.id.tv_list_pay_total);
            tv_total_num = (TextView) itemView.findViewById(R.id.tv_list_pay_total_num);
            tv_cancel = (TextView) itemView.findViewById(R.id.tv_list_pay_cancel);
            tv_fee= (TextView) itemView.findViewById(R.id.tv_list_pay_fee);
            lv= (MyListView) itemView.findViewById(R.id.lv_pay);
        }
    }

}