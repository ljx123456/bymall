package com.qr.bymall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.NearBySchool;

import java.util.List;

/**
 * Created by alu on 2018/6/29.
 */

public class NearBySchoolAdapter extends BaseAdapter {
    private Context context;
    private List<NearBySchool.DataBean.NearBean> schools;
    public NearBySchoolAdapter(Context context,List<NearBySchool.DataBean.NearBean> schools) {
        this.context=context;
        this.schools=schools;
    }

    @Override
    public int getCount() {
        return schools.size();
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
        TextView tv_school;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.list_change_school_item,null);
            viewHolder.tv_school= (TextView) convertView.findViewById(R.id.tv_list_change_school_item);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_school.setText(schools.get(position).getSchool_name());
        return convertView;
    }
}
