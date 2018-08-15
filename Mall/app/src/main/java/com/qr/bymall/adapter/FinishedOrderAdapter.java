package com.qr.bymall.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.bean.Code;
import com.qr.bymall.bean.OrderList;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/7/24.
 */

public class FinishedOrderAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    private Context context;
    private List<OrderList.DataBean> list;

    public FinishedOrderAdapter(Context context) {
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
        View view = LayoutInflater.from(context).inflate(R.layout.list_finished_order, parent,false);
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
                getDel(list.get(position).getOrder_id(), SharedUtil.getToken(context),position);
            }

        });

        mHodler.itemView.setTag(list.get(position));

    }



    class ViewHodler extends RecyclerView.ViewHolder {


        private TextView tv_store,tv_total_price,tv_total_num,tv_cancel,tv_fee;
        private MyListView lv;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_store = (TextView) itemView.findViewById(R.id.tv_list_finished_store);
            tv_total_price = (TextView) itemView.findViewById(R.id.tv_list_finished_total);
            tv_total_num = (TextView) itemView.findViewById(R.id.tv_list_finished_total_num);
            tv_cancel = (TextView) itemView.findViewById(R.id.tv_list_finished_cancel);
            tv_fee= (TextView) itemView.findViewById(R.id.tv_list_finished_fee);
            lv= (MyListView) itemView.findViewById(R.id.lv_finished);
        }
    }


    public void getDel(String id, String token, final int i){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/del/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getDelService service = retrofit.create(getDelService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(id, token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        list.remove(i);
                        notifyDataSetChanged();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(context)
                                .setTitle("系统提示")
                                .setMessage(response.body().getMessage())
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                }else if (response.code()==401){
                    Toast.makeText(context, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(context,LoginActivity.class);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getDelService{
        @GET("del/{id}")
        Call<Code> getBean(@Path("id") String id,@Query("token") String token);
    }
}
