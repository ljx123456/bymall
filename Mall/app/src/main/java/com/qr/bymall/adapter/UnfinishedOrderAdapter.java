package com.qr.bymall.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.DrawbackActivity;
import com.qr.bymall.activity.NeedPayOrderDetailsActivity;
import com.qr.bymall.bean.OrderList;
import com.qr.bymall.view.MyListView;
import com.qr.bymall.view.OnMultiClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/24.
 */

public class UnfinishedOrderAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<OrderList.DataBean> list;



    public UnfinishedOrderAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.list_unfinished_order_item, parent,false);
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
                showCall(list.get(position).getAgent_phone());
            }

        });

        mHodler.itemView.setTag(list.get(position));

    }



    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView tv_store,tv_total_price,tv_total_num,tv_cancel,tv_fee;
        private MyListView lv;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_store = (TextView) itemView.findViewById(R.id.tv_list_unfinished_store);
            tv_total_price = (TextView) itemView.findViewById(R.id.tv_list_unfinished_total);
            tv_total_num = (TextView) itemView.findViewById(R.id.tv_list_unfinished_total_num);
            tv_cancel = (TextView) itemView.findViewById(R.id.tv_list_unfinished_cancel);
            tv_fee= (TextView) itemView.findViewById(R.id.tv_list_unfinished_fee);
            lv= (MyListView) itemView.findViewById(R.id.lv_unfinished);
        }
    }
    private void testCall(String phone) {
        /** 1. 检测ContextCompat.checkSelfPermission(),返回值
         * PackageManager.PERMISSION_GRANTED 授权已成功
         * PackageManager.PERMISSION_DENIED 授权被拒绝
         */
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 清单里面授权被拒绝，向用户申请
            // 2. 申请权限
            showDialog();
        } else {
            //获取位置
            CallPhone(phone);
            Log.e("MainActivity", "1");
        }
    }
    private void CallPhone(String phone) {
        Log.e("拨打",phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    // 提示用户去设置
    public void showDialog(){
        new AlertDialog.Builder(context)
                .setMessage("拨号权限被拒绝，请到设置里面打开，才可使用此功能！")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到本应用的设置页面，可以打开权限
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
    }
    public void showCall(final String phone){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View
                .inflate(context, R.layout.dialog_order_call, null);
        builder.setView(view);
//        builder.setCancelable(true);
        TextView title= (TextView) view.findViewById(R.id.tv_dialog_call_phone_title);//设置标题
        TextView tv_nav= (TextView) view.findViewById(R.id.tv_dialog_call_phone_nav);
        TextView tv_pos= (TextView) view.findViewById(R.id.tv_dialog_call_phone_pos);
        title.setText("联系派送员："+phone);
        //取消或确定按钮监听事件处理
        final AlertDialog dialog = builder.create();
        Window window=dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tv_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tv_pos.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                testCall(phone);
            }
        });
        dialog.show();
        WindowManager m = ((Activity)context).getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = 240;   //高度设置为屏幕的0.3
        p.width = 552;    //宽度设置为屏幕的0.5
        dialog.getWindow().setAttributes(p);     //设置生效


    }

}
