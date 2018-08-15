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
import com.qr.bymall.adapter.AddressAdapter;
import com.qr.bymall.bean.AddressList;
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
 * Created by alu on 2018/7/12.
 */

public class AddressActivity extends BaseActivity {

    private RelativeLayout back,add;
    private RecyclerView recy;
    private AddressAdapter adapter;
    private Intent intent;


    @Override
    public void addLayout() {
        setContentView(R.layout.activity_address);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.address_back);
        add= (RelativeLayout) findViewById(R.id.layout_address_add);
        recy= (RecyclerView) findViewById(R.id.recy_address);
        intent=getIntent();
        adapter=new AddressAdapter(AddressActivity.this);
        if (SharedUtil.getToken(getApplicationContext()).equals("")){
            gotoActivity(LoginActivity.class,1);
        }else {
            getAddressList(SharedUtil.getSchool_id(getApplicationContext()),SharedUtil.getToken(getApplicationContext()));
        }
        recy.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recy.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent!=null&&intent.getStringExtra("flag")!=null&&intent.getStringExtra("flag").equals("0")) {
                    gotoActivity(AddAddressActivity.class, "flag",intent.getStringExtra("flag"),1);
                }else {
                    gotoActivity(AddAddressActivity.class,1);
                }
            }
        });
        if (intent!=null&&intent.getStringExtra("flag")!=null&&intent.getStringExtra("flag").equals("0")){
            adapter.setOnItemClickListener(new AddressAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, AddressList.DataBean data) {
                    gotoActivity(NewOrderActivity.class,"address_id",data.getAddress_id(),1);
                }
            });
        }

    }

    public void getAddressList(String id,String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/address/list
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAddressListService service = retrofit.create(getAddressListService.class);
        //4.通过回调获得结果
        Call<AddressList> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<AddressList>() {
            @Override
            public void onResponse(Call<AddressList> call, Response<AddressList> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData().size()+"进来了");
                        adapter.updateList(response.body().getData());
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(AddressActivity.this)
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
            public void onFailure(Call<AddressList> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getAddressListService{
        @GET("list/{school}")
        Call<AddressList> getBean(@Path("school") String id, @Query("token") String token);
    }
}
