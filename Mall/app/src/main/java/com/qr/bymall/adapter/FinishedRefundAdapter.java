package com.qr.bymall.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.Refund;
import com.qr.bymall.view.MyListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/26.
 */

public class FinishedRefundAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<Refund.DataBean> list;



    public FinishedRefundAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    //添加数据
    public void addList(List<Refund.DataBean> list) {
        if (this.list.containsAll(list)) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    //更新数据
    public void updateList(List<Refund.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear() {
        this.list.clear();
        notifyDataSetChanged();
    }
    public void remove(Refund.DataBean dataBean) {
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
            mOnItemClickListener.onItemClick(v, (Refund.DataBean) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Refund.DataBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_finished_refund, parent,false);
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
        mHodler.tv_money.setText("¥"+list.get(position).getTotal_price());
        if (list.get(position).getStatus().equals("7")){
            mHodler.layout.setVisibility(View.GONE);
            mHodler.tv_result.setText("退款成功");
        }else {
            mHodler.layout.setVisibility(View.VISIBLE);
            mHodler.tv_reason.setText(list.get(position).getMemo());
            mHodler.tv_result.setText("退款失败");
        }

        FinishedRefundItemAdapter adapter=new FinishedRefundItemAdapter(context,list.get(position).getProduct());
        mHodler.lv.setAdapter(adapter);


        mHodler.itemView.setTag(list.get(position));

    }



    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView tv_store,tv_money,tv_reason,tv_result;
        private RelativeLayout layout;
        private MyListView lv;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_store = (TextView) itemView.findViewById(R.id.tv_finished_refund_store);
            tv_money = (TextView) itemView.findViewById(R.id.tv_finished_refund_money);
            tv_reason = (TextView) itemView.findViewById(R.id.tv_finished_refund_reason);
            tv_result = (TextView) itemView.findViewById(R.id.tv_finished_refund_result);
            layout= (RelativeLayout) itemView.findViewById(R.id.layout_finished_refund_reason);
            lv= (MyListView) itemView.findViewById(R.id.lv_finished_refund);
        }
    }

}
