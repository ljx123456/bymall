package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.OrderDetailsAdapter;
import com.qr.bymall.bean.Code;
import com.qr.bymall.bean.OrderDetails;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.MyListView;
import com.qr.bymall.view.OnMultiClickListener;

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

public class DrawbackActivity extends BaseActivity {

    private RelativeLayout back;
    private TextView tv_store,tv_money;
    private EditText edt;
    private MyListView lv;
    private OrderDetailsAdapter adapter;
    private Intent intent;
    private Button tv_refer;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_drawback);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.drawback_back);
        tv_store= (TextView) findViewById(R.id.tv_drawback_store);
        tv_money= (TextView) findViewById(R.id.tv_drawback_money);
        tv_refer= (Button) findViewById(R.id.tv_drawback_refer);
        edt= (EditText) findViewById(R.id.edt_drawback);
        lv= (MyListView) findViewById(R.id.lv_drawback);
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("id")!=null) {
            getOrderDetails(intent.getStringExtra("id"), SharedUtil.getToken(getApplicationContext()));
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_refer.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (edt.getText()!=null&&!edt.getText().toString().equals("")){
                    getRefund(intent.getStringExtra("id"), SharedUtil.getToken(getApplicationContext()),edt.getText().toString());
                }else {
                    Toast.makeText(DrawbackActivity.this, "请填写退款说明", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getOrderDetails(final String id, String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/detail/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getOrderListService service = retrofit.create(getOrderListService.class);
        //4.通过回调获得结果
        Call<OrderDetails> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<OrderDetails>() {
            @Override
            public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData().getShop_name()+"进来了");
                        tv_store.setText(response.body().getData().getShop_name());
                        tv_money.setText("¥"+response.body().getData().getPay_money());
                        adapter=new OrderDetailsAdapter(getApplicationContext(),response.body().getData().getProducts());
                        lv.setAdapter(adapter);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(DrawbackActivity.this)
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
            public void onFailure(Call<OrderDetails> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getOrderListService{
        @GET("detail/{id}")
        Call<OrderDetails> getBean(@Path("id") String id, @Query("token") String token);
    }

    public void getRefund(final String id, String token,String reason){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/refund/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getRefundService service = retrofit.create(getRefundService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(id,token,reason);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试","提交成功");
                        Toast.makeText(DrawbackActivity.this, "退款成功", Toast.LENGTH_SHORT).show();
                        gotoActivity(PayOrderActivity.class,2);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(DrawbackActivity.this)
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
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getRefundService{
        @FormUrlEncoded
        @POST("refund/{id}")
        Call<Code> getBean(@Path("id") String id, @Field("token") String token,@Field("reason") String reason);
    }
}
