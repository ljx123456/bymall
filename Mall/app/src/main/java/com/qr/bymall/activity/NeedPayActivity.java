package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.NeedPayAdapter;
import com.qr.bymall.bean.OrderList;
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
 * Created by alu on 2018/7/20.
 */

public class NeedPayActivity extends BaseActivity {

    private RelativeLayout back;
    private RecyclerView recy;
    private NeedPayAdapter adapter;
    private Intent intent;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_need_pay_order);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.need_pay_back);
        recy= (RecyclerView) findViewById(R.id.recy_need_pay);
        adapter=new NeedPayAdapter(NeedPayActivity.this);
        intent=getIntent();
        getOrderList("0", SharedUtil.getToken(getApplicationContext()));
        adapter.setOnItemClickListener(new NeedPayAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, OrderList.DataBean data) {
                gotoActivity(NeedPayOrderDetailsActivity.class,"id",data.getOrder_id());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (intent!=null&&intent.getStringExtra("pay")!=null){
//                    gotoActivity(MainActivity.class,"name","1",1);
//                }else {
//                    finish();
//                }
                gotoActivity(MainActivity.class,"name","1",1);
            }
        });

    }

    public void getOrderList(String status,String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/list/{status}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getOrderListService service = retrofit.create(getOrderListService.class);
        //4.通过回调获得结果
        Call<OrderList> call = service.getBean(status,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<OrderList>() {
            @Override
            public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData().size()+"进来了");
                        if (response.body().getData().size()!=0) {
                            adapter.updateList(response.body().getData());
                            recy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recy.setAdapter(adapter);
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(NeedPayActivity.this)
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
                    Toast.makeText(getApplicationContext(), "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderList> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getOrderListService{
        @GET("list/{status}")
        Call<OrderList> getBean(@Path("status") String status, @Query("token") String token);
    }
}
