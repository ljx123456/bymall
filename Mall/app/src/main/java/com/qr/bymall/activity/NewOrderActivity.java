package com.qr.bymall.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.CouponsAdapter;
import com.qr.bymall.adapter.NewOrderGoodsAdapter;
import com.qr.bymall.bean.Address;
import com.qr.bymall.bean.NewOrder;
import com.qr.bymall.bean.SubmitOrder;
import com.qr.bymall.bean.WalletPay;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.CouponsChoicePopupWindow;
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
 * Created by alu on 2018/7/12.
 */

public class NewOrderActivity extends BaseActivity {

    private RelativeLayout back,layout_address_none,layout_address,layout_coupon;
    private TextView tv_address_consignee,tv_address_address,tv_address_phone,tv_store,tv_coupon,
            tv_fee,tv_num,tv_total,tv_money,tv_buy;
    private MyListView lv;
    private NewOrderGoodsAdapter goodsAdapter;
    private CouponsAdapter couponsAdapter;
    private CouponsChoicePopupWindow pw;

    private Intent intent;
    private String address_id;


    @Override
    public void addLayout() {
        setContentView(R.layout.activity_new_order);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.new_order_back);
        layout_address_none= (RelativeLayout) findViewById(R.id.layout_new_order_address_none);
        layout_address= (RelativeLayout) findViewById(R.id.layout_new_order_address);
        layout_coupon= (RelativeLayout) findViewById(R.id.layout_new_order_coupon);
        tv_address_consignee= (TextView) findViewById(R.id.tv_new_order_address_consignee);
        tv_address_address= (TextView) findViewById(R.id.tv_new_order_address_address);
        tv_address_phone= (TextView) findViewById(R.id.tv_new_order_address_phone);
        tv_store= (TextView) findViewById(R.id.tv_new_order_store);
        tv_coupon= (TextView) findViewById(R.id.tv_new_order_coupon_title);
        tv_fee= (TextView) findViewById(R.id.tv_new_order_fee);
        tv_num= (TextView) findViewById(R.id.tv_new_order_num);
        tv_total= (TextView) findViewById(R.id.tv_new_order_total);
        tv_money= (TextView) findViewById(R.id.tv_new_order_money);
        tv_buy= (TextView) findViewById(R.id.tv_new_order_buy);
        lv= (MyListView) findViewById(R.id.lv_new_order);
        intent=getIntent();
        if (SharedUtil.getToken(getApplicationContext()).equals("")){
            gotoActivity(LoginActivity.class,1);
        }
        if (!SharedUtil.getProducts(getApplicationContext()).equals("")&&!SharedUtil.getAgent_id(getApplicationContext()).equals("")){
            getNewOrder(SharedUtil.getProducts(getApplicationContext()),SharedUtil.getAgent_id(getApplicationContext()),SharedUtil.getToken(getApplicationContext()));
        }
        if (intent!=null&&intent.getStringExtra("address_id")!=null) {
            address_id=intent.getStringExtra("address_id");
            getAddress(SharedUtil.getToken(getApplicationContext()),intent.getStringExtra("address_id"));
        }
//        getNewOrder();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layout_address_none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(AddressActivity.class,"flag","0");
            }
        });
        layout_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(AddressActivity.class,"flag","0");
            }
        });
        tv_buy.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                getSubmitOrder(SharedUtil.getToken(getApplicationContext()),SharedUtil.getProducts(getApplicationContext()),address_id,
                        BaseUtil.coupon,SharedUtil.getAgent_id(getApplicationContext()),SharedUtil.getSchool_id(getApplicationContext()));
            }
        });


    }

    public void getNewOrder(String products,String agent_id,String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/confirm
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getNewOrderService service = retrofit.create(getNewOrderService.class);
        //4.通过回调获得结果
        Call<NewOrder> call = service.getBean(products,agent_id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<NewOrder>() {
            @Override
            public void onResponse(Call<NewOrder> call, final Response<NewOrder> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        if (intent!=null&&intent.getStringExtra("address_id")==null) {
                            if (response.body().getData().getAddress()==null||response.body().getData().getAddress().getAddress_id()==null) {
                                layout_address_none.setVisibility(View.VISIBLE);
                                layout_address.setVisibility(View.GONE);
                            } else {
                                address_id=response.body().getData().getAddress().getAddress_id();
                                layout_address_none.setVisibility(View.GONE);
                                layout_address.setVisibility(View.VISIBLE);
                                tv_address_consignee.setText(response.body().getData().getAddress().getRec_name());
                                tv_address_phone.setText(response.body().getData().getAddress().getRec_phone());
                                tv_address_address.setText(response.body().getData().getAddress().getSchool() + response.body().getData().getAddress().getRoom() + response.body().getData().getAddress().getRec_detail());
                            }
                        }
                        tv_store.setText(response.body().getData().getShop_name());
                        goodsAdapter=new NewOrderGoodsAdapter(getApplicationContext(),response.body().getData().getProducts());
                        lv.setAdapter(goodsAdapter);
                        tv_fee.setText("¥"+response.body().getData().getExpress_fee());
                        tv_num.setText("共"+response.body().getData().getProducts().size()+"件商品");
                        tv_total.setText(response.body().getData().getProductsPrice()+"");
                        tv_money.setText((Float.parseFloat(response.body().getData().getProductsPrice())+2)+"");
//                        Log.e("测试",response.body().getData().getCoupons().size()+"");
                        if (response.body().getData().getCoupons().size()==0){
                            tv_coupon.setText("暂无优惠券");
                        }else {
                            layout_coupon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (pw == null) {
                                        couponsAdapter=new CouponsAdapter(getApplicationContext(),response.body().getData().getCoupons());
                                        pw = new CouponsChoicePopupWindow(NewOrderActivity.this,tv_coupon,tv_money,couponsAdapter,response.body().getData().getCoupons());
                                    }if (pw.isShowing()) {
                                        pw.dismiss();
                                        setBackgroundAlpha(NewOrderActivity.this,1.0f);
                                        return;
                                    }
                                    pw.show();
                                }
                            });
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(NewOrderActivity.this)
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
                    Toast.makeText(NewOrderActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(NewOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewOrder> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getNewOrderService{
        @FormUrlEncoded
        @POST("confirm")
        Call<NewOrder> getBean(@Field("products") String products,@Field("agent_id") String agent_id,@Field("token") String token);
    }

    public void getAddress(String token, final String id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        //  /v2/address/detail/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/address/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAddressService service = retrofit.create(getAddressService.class);
        //4.通过回调获得结果
        Call<Address> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Address>() {
            @Override
            public void onResponse(Call<Address> call, Response<Address> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        layout_address_none.setVisibility(View.GONE);
                        layout_address.setVisibility(View.VISIBLE);
                        tv_address_consignee.setText(response.body().getData().getRec_name());
                        tv_address_phone.setText(response.body().getData().getRec_phone());
                        tv_address_address.setText(response.body().getData().getSchool() + response.body().getData().getRoom() + response.body().getData().getRec_detail());
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(NewOrderActivity.this)
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
                    Toast.makeText(NewOrderActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    Toast.makeText(NewOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Address> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getAddressService{
        @GET("detail/{id}")
        Call<Address> getBean(@Path("id") String id, @Query("token") String token);
    }
    //   设置添加屏幕的背景透明度
    public  void setBackgroundAlpha(Activity activity, float bgAlpha) {

        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
//        Log.e("主界面", "设置了背景色"+bgAlpha);
    }


    public void getSubmitOrder(String token,String products,String address_id,String coupon_id,String agent_id,String school_id){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/confirm
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"/v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getWalletPayService service = retrofit.create(getWalletPayService.class);
        //4.通过回调获得结果
        Call<SubmitOrder> call = service.getBean(token,products,address_id,coupon_id,agent_id,school_id);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<SubmitOrder>() {
            @Override
            public void onResponse(Call<SubmitOrder> call, final Response<SubmitOrder> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        gotoActivity(PaymentActivity.class,"order_id",response.body().getData().getOrder_id(),1);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(NewOrderActivity.this)
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
                    Toast.makeText(NewOrderActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(NewOrderActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubmitOrder> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getWalletPayService{
        @FormUrlEncoded
        @POST("submit")
        Call<SubmitOrder> getBean(@Field("token") String token, @Field("products") String products, @Field("address_id") String address_id, @Field("coupon_use_ids") String coupon_use_ids ,
                                  @Field("agent_id") String agent_id, @Field("school_id") String school_id );
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
