package com.qr.bymall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.NewOrder;

import java.util.List;

/**
 * Created by alu on 2018/7/16.
 */

public class CouponsAdapter extends BaseAdapter {
    private Context context;
    private List<NewOrder.DataBean.CouponsBean> coupons;
    public CouponsAdapter(Context context,List<NewOrder.DataBean.CouponsBean> coupons) {
        this.context=context;
        this.coupons=coupons;
    }

    @Override
    public int getCount() {
        return coupons.size();
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
        TextView tv_price,tv_title,tv_time;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_coupons_item,null);
            viewHolder.tv_price= (TextView) convertView.findViewById(R.id.tv_list_popup_coupons_price);
            viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_list_popup_coupons_time);
            viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_list_popup_coupons_title);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_price.setText(coupons.get(position).getMoney());
        viewHolder.tv_title.setText(coupons.get(position).getName()+"，"+coupons.get(position).getDesc());
        viewHolder.tv_time.setText("有效期 "+coupons.get(position).getStart_date()+"-"+coupons.get(position).getEnd_date());
        return convertView;
    }
}
