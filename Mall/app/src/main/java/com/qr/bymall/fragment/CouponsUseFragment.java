package com.qr.bymall.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.activity.MainActivity;
import com.qr.bymall.adapter.CouponsUseAdapter;
import com.qr.bymall.bean.Coupons;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

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

public class CouponsUseFragment extends Fragment {

    private ListView lv;
    private CouponsUseAdapter adapter;
    private List<Coupons.DataBean.GetBean> list;
    private boolean isFlag=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_coupons_use,null);
        lv= (ListView) view.findViewById(R.id.lv_coupons_use);
        list=new ArrayList<>();
        isFlag=true;
        getCoupons(SharedUtil.getToken(getContext()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                ((Activity)getContext()).finish();
            }
        });
        return view;
    }

    public void getCoupons(String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/coupon/center/list
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/coupon/center/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getCouponsService service = retrofit.create(getCouponsService.class);
        //4.通过回调获得结果
        Call<Coupons> call = service.getBean(token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Coupons>() {
            @Override
            public void onResponse(Call<Coupons> call, Response<Coupons> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试",response.body().getData().getGet().size()+"");
                        list=response.body().getData().getGet();
                        Log.e("测试2",list.size()+"");
                        adapter=new CouponsUseAdapter(getContext(),list);
                        lv.setAdapter(adapter);
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
            public void onFailure(Call<Coupons> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getCouponsService{
        @GET("list")
        Call<Coupons> getBean(@Query("token") String token);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden&&isFlag){
            getCoupons(SharedUtil.getToken(getContext()));
        }
    }
}
