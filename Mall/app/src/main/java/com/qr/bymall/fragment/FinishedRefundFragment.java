package com.qr.bymall.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.adapter.FinishedRefundAdapter;
import com.qr.bymall.bean.Refund;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/7/26.
 */

public class FinishedRefundFragment extends Fragment {

    private RecyclerView recy;
    private FinishedRefundAdapter adapter;
    private boolean isFlag=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_unfinished_refund,null);
        recy= (RecyclerView) view.findViewById(R.id.recy_unfinished_refund);
        adapter=new FinishedRefundAdapter(getContext());
        isFlag=true;
        getRefund( SharedUtil.getToken(getContext()));
        return view;
    }
    public void getRefund(String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/list/refund/complete
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/list/refund/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getRefundService service = retrofit.create(getRefundService.class);
        //4.通过回调获得结果
        Call<Refund> call = service.getBean(token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Refund>() {
            @Override
            public void onResponse(Call<Refund> call, Response<Refund> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData().size()+"进来了");
                        if (response.body().getData().size()!=0) {
                            adapter.updateList(response.body().getData());
                            recy.setLayoutManager(new LinearLayoutManager(getContext()));
                            recy.setAdapter(adapter);
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(getContext())
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
                    Toast.makeText(getContext(), "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getContext(),LoginActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Refund> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getRefundService{
        @GET("complete")
        Call<Refund> getBean(@Query("token") String token);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden&&isFlag){
            getRefund(SharedUtil.getToken(getContext()));
        }
    }
}
