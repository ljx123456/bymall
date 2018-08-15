package com.qr.bymall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.OrderDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by alu on 2018/7/23.
 */

public class OrderDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<OrderDetails.DataBean.ProductsBean> goods;
    public OrderDetailsAdapter(Context context,List<OrderDetails.DataBean.ProductsBean> goods) {
        this.context=context;
        this.goods=goods;
    }

    @Override
    public int getCount() {
        return goods.size();
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
        TextView tv_name,tv_taste,tv_price,tv_num;
        ImageView iv_goods;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_new_order_item,null);
            viewHolder.iv_goods= (ImageView) convertView.findViewById(R.id.iv_new_order_item_goods);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_new_order_item_name);
            viewHolder.tv_taste= (TextView) convertView.findViewById(R.id.tv_new_order_item_taste);
            viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_new_order_item_price);
            viewHolder.tv_num= (TextView) convertView.findViewById(R.id.tv_new_order_item_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(context).load(goods.get(position).getImg()).into(viewHolder.iv_goods);
        viewHolder.tv_price.setText("¥"+goods.get(position).getPrice());
        viewHolder.tv_name.setText(goods.get(position).getTitle());
        viewHolder.tv_taste.setText(goods.get(position).getName());
        viewHolder.tv_num.setText("x"+goods.get(position).getNum());
        return convertView;
    }
}
