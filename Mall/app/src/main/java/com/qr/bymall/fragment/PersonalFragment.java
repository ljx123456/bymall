package com.qr.bymall.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.AddressActivity;
import com.qr.bymall.activity.CouponsActivity;
import com.qr.bymall.activity.CustomerServiceActivity;
import com.qr.bymall.activity.FinishedOrderActivity;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.activity.NeedPayActivity;
import com.qr.bymall.activity.PayOrderActivity;
import com.qr.bymall.activity.RefundActivity;
import com.qr.bymall.activity.SetActivity;
import com.qr.bymall.activity.SuggestActivity;
import com.qr.bymall.activity.UnfinishedOrderActivity;
import com.qr.bymall.bean.PersonalInfo;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.OnMultiClickListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/6/27.
 */


public class PersonalFragment extends Fragment {

    private RelativeLayout set,need_pay,pay,unfinished_order,finished_order,
            address,coupons,service,suggest,refund,layout_net;
    private LinearLayout layout_personal;
    private TextView tv_name,tv_wallet,tv_point;
    private Button btn;
    private boolean isFlag=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_personal, container, false);
        set= (RelativeLayout) view.findViewById(R.id.layout_set);
        need_pay= (RelativeLayout) view.findViewById(R.id.layout_personal_need_pay);
        pay= (RelativeLayout) view.findViewById(R.id.layout_personal_pay);
        unfinished_order= (RelativeLayout) view.findViewById(R.id.layout_personal_unfinished_order);
        finished_order= (RelativeLayout) view.findViewById(R.id.layout_personal_finished_order);
        address= (RelativeLayout) view.findViewById(R.id.layout_personal_address);
        coupons= (RelativeLayout) view.findViewById(R.id.layout_personal_coupons);
        service= (RelativeLayout) view.findViewById(R.id.layout_personal_service);
        suggest= (RelativeLayout) view.findViewById(R.id.layout_personal_suggest);
        refund= (RelativeLayout) view.findViewById(R.id.layout_personal_refund);
        tv_name= (TextView) view.findViewById(R.id.tv_personal_name);
        tv_wallet= (TextView) view.findViewById(R.id.tv_personal_wallet);
        tv_point= (TextView) view.findViewById(R.id.tv_personal_point);
        layout_personal= (LinearLayout) view.findViewById(R.id.layout_personal_fragment);
        layout_net= (RelativeLayout) view.findViewById(R.id.layout_personal_net);
        btn= (Button) view.findViewById(R.id.btn_net);
        getPersonalInfo(SharedUtil.getToken(getContext()));
        if (isConn(getContext())){
            layout_personal.setVisibility(View.VISIBLE);
            layout_net.setVisibility(View.GONE);
            getPersonalInfo(SharedUtil.getToken(getContext()));
        }else {
            layout_personal.setVisibility(View.GONE);
            layout_net.setVisibility(View.VISIBLE);
        }
        isFlag=true;
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (isConn(getContext())){
                    layout_personal.setVisibility(View.VISIBLE);
                    layout_net.setVisibility(View.GONE);
                    getPersonalInfo(SharedUtil.getToken(getContext()));
                }else {
                    layout_personal.setVisibility(View.GONE);
                    layout_net.setVisibility(View.VISIBLE);
                }
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SetActivity.class);
                startActivity(intent);
            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
        need_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), NeedPayActivity.class);
                startActivity(intent);
            }
        });
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), PayOrderActivity.class);
                startActivity(intent);
            }
        });
        unfinished_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), UnfinishedOrderActivity.class);
                startActivity(intent);
            }
        });
        finished_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), FinishedOrderActivity.class);
                startActivity(intent);
            }
        });
        refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RefundActivity.class);
                startActivity(intent);
            }
        });
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CustomerServiceActivity.class);
                startActivity(intent);
            }
        });
        coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), CouponsActivity.class);
                startActivity(intent);
            }
        });
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SuggestActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void getPersonalInfo(String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/customer/center
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/customer/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAddressListService service = retrofit.create(getAddressListService.class);
        //4.通过回调获得结果
        Call<PersonalInfo> call = service.getBean(token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<PersonalInfo>() {
            @Override
            public void onResponse(Call<PersonalInfo> call, Response<PersonalInfo> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        tv_name.setText(response.body().getData().getName());
                        tv_wallet.setText("¥"+response.body().getData().getMoney());
                        tv_point.setText(response.body().getData().getScore());
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
            public void onFailure(Call<PersonalInfo> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getAddressListService{
        @GET("center")
        Call<PersonalInfo> getBean( @Query("token") String token);
    }

    public  boolean isConn(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
            searchNetwork(context);//弹出提示对话框
        }
        return false;
    }

    /**
     * 判断网络是否连接成功，连接成功不做任何操作
     * 未连接则弹出对话框提示用户设置网络连接
     */
    public  void searchNetwork(final Context context) {
        //提示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage("网络连接不可用,是否进行设置?").setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if (android.os.Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                context.startActivity(intent);

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
//                layout_center.setVisibility(View.GONE);
//                layout_nearby.setVisibility(View.GONE);
//                layout_net.setVisibility(View.VISIBLE);
            }
        }).show();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden&&isFlag){
            if (isConn(getContext())){
                layout_personal.setVisibility(View.VISIBLE);
                layout_net.setVisibility(View.GONE);
                getPersonalInfo(SharedUtil.getToken(getContext()));
            }else {
                layout_personal.setVisibility(View.GONE);
                layout_net.setVisibility(View.VISIBLE);
            }
        }
    }
}
