package com.qr.bymall.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.activity.EditAddressActivity;
import com.qr.bymall.bean.Address;
import com.qr.bymall.bean.AddressList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/13.
 */

public class AddressAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<AddressList.DataBean> list;


    public AddressAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    //添加数据
    public void addList(List<AddressList.DataBean> list) {
        if (this.list.containsAll(list)) {
            return;
        }
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    //更新数据
    public void updateList(List<AddressList.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
    public void clear() {
        this.list.clear();
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
            mOnItemClickListener.onItemClick(v, (AddressList.DataBean) v.getTag());
        }
    }


    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, AddressList.DataBean data);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_address_item, parent,false);
        ViewHodler vh = new ViewHodler(view);
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHodler mHodler = (ViewHodler) holder;
        mHodler.tv_name.setText(list.get(position).getRec_name());
        mHodler.tv_phone.setText(list.get(position).getRec_phone());
        if (list.get(position).getDefault_flag().equals("0")){
            mHodler.tv_flag.setVisibility(View.GONE);
        }else {
            mHodler.tv_flag.setVisibility(View.VISIBLE);
        }
        mHodler.tv_address.setText(list.get(position).getSchool()+list.get(position).getRoom()+list.get(position).getRec_detail());

        mHodler.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, EditAddressActivity.class);
                intent.putExtra("address_id",list.get(position).getAddress_id());
                context.startActivity(intent);
            }
        });
        mHodler.itemView.setTag(list.get(position));

    }



    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView tv_name,tv_phone,tv_address,tv_flag,tv_edit;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_address_item_consignee);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_address_item_phone);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address_item_address);
            tv_flag = (TextView) itemView.findViewById(R.id.tv_address_item_flag);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_address_item_edit);

        }
    }
}
