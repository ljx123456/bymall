package com.qr.bymall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.Refund;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alu on 2018/7/26.
 */

public class FinishedRefundItemAdapter extends BaseAdapter {
    private Context context;
    private List<Refund.DataBean.ProductBean> products;
    public FinishedRefundItemAdapter(Context context,List<Refund.DataBean.ProductBean> products) {
        this.context=context;
        this.products=products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    class ViewHolder{
        TextView tv_name,tv_price,tv_num,tv_taste;
        ImageView iv;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_need_pay_item,null);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_list_need_pay_item_name);
            viewHolder.tv_num= (TextView) convertView.findViewById(R.id.tv_list_need_pay_item_num);
            viewHolder.tv_taste= (TextView) convertView.findViewById(R.id.tv_list_need_pay_item_taste);
            viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_list_need_pay_item_price);
            viewHolder.iv= (ImageView) convertView.findViewById(R.id.iv_list_need_pay_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_name.setText(products.get(position).getTitle());
        viewHolder.tv_price.setText("¥"+products.get(position).getPrice());
        viewHolder.tv_taste.setText(products.get(position).getName());
        viewHolder.tv_num.setText("x"+products.get(position).getNum());
        Picasso.with(context).load(products.get(position).getImg()).into(viewHolder.iv);
        return convertView;
    }
}

