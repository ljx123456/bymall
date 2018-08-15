package com.qr.bymall.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.qr.bymall.R;
import com.qr.bymall.bean.AliPay;
import com.qr.bymall.bean.PayInfo;
import com.qr.bymall.bean.PayResult;
import com.qr.bymall.bean.WalletPay;
import com.qr.bymall.bean.WechatPay;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.OnMultiClickListener;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

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

public class PaymentActivity extends BaseActivity {

    private RelativeLayout back,layout_wallet,layout_wechat,layout_alipay;
    private TextView tv_money,tv_pay,tv_wallet;
    private CheckBox wallet,wechat,alipay;
    private int flag=0;
    private Intent intent;
    private IWXAPI api;
    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            Log.e("测试resultInfo",resultInfo);
            Log.e("测试resultStatus",resultStatus);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                Toast.makeText(PaymentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                gotoActivity(PayResultActivity.class,"result","支付成功",1);
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                Toast.makeText(PaymentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                gotoActivity(PayResultActivity.class,"result","支付失败",1);
            }

        }
    };

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_payment);
    }

    @Override
    public void initView() {
        api = WXAPIFactory.createWXAPI(getApplicationContext(), null);
        // 将该app注册到微信
        api.registerApp("wx6aea495997e32cc1");
        back= (RelativeLayout) findViewById(R.id.payment_back);
        layout_wallet= (RelativeLayout) findViewById(R.id.layout_payment_wallet);
        layout_wechat= (RelativeLayout) findViewById(R.id.layout_payment_wechat);
        layout_alipay= (RelativeLayout) findViewById(R.id.layout_payment_alipay);
        tv_money= (TextView) findViewById(R.id.tv_payment_total);
        tv_wallet= (TextView) findViewById(R.id.tv_payment_wallet_money);
        tv_pay= (TextView) findViewById(R.id.tv_payment_pay);
        wallet= (CheckBox) findViewById(R.id.checkbox_payment_wallet);
        alipay= (CheckBox) findViewById(R.id.checkbox_payment_alipay);
        wechat= (CheckBox) findViewById(R.id.checkbox_payment_wechat);
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("order_id")!=null){
            getPayInfo(intent.getStringExtra("order_id"),SharedUtil.getToken(getApplicationContext()));
        }
        layout_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag!=0){
                    wallet.setChecked(true);
                    alipay.setChecked(false);
                    wechat.setChecked(false);
                    flag=0;
                }
            }
        });
        layout_wechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag!=1){
                    wechat.setChecked(true);
                    alipay.setChecked(false);
                    wallet.setChecked(false);
                    flag=1;
                }
            }
        });
        layout_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag!=2){
                    alipay.setChecked(true);
                    wallet.setChecked(false);
                    wechat.setChecked(false);
                    flag=2;
                }
            }
        });

        tv_pay.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                switch (flag){
                    case 0:
                        Log.e("测试", BaseUtil.coupon);
                        getWalletPay(intent.getStringExtra("order_id"),SharedUtil.getToken(getApplicationContext()), "2","app");
                        break;
                    case 1:
                        getWechatPay(intent.getStringExtra("order_id"),SharedUtil.getToken(getApplicationContext()), "1","app");
                        break;
                    case 2:
                        getAliPay(intent.getStringExtra("order_id"),SharedUtil.getToken(getApplicationContext()), "0","app");
                        break;
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(NeedPayActivity.class,"pay","0",1);
            }
        });
    }
    public void getPayInfo(String id,String token){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/pay/info/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/pay/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getPayInfoService service = retrofit.create(getPayInfoService.class);
        //4.通过回调获得结果
        Call<PayInfo> call = service.getBean(id,token);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<PayInfo>() {
            @Override
            public void onResponse(Call<PayInfo> call, Response<PayInfo> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试",response.body().getData().getMoney());
                        tv_money.setText(response.body().getData().getMoney());
                        tv_wallet.setText("（可用余额：¥"+response.body().getData().getWallet()+"）");
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(PaymentActivity.this)
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
            public void onFailure(Call<PayInfo> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getPayInfoService{
        @GET("info/{id}")
        Call<PayInfo> getBean(@Path("id") String id, @Query("token") String token);
    }

    public void getWalletPay(String id,String token,String pay_type,String terminal){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/pay/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getWalletPayService service = retrofit.create(getWalletPayService.class);
        //4.通过回调获得结果
        Call<WalletPay> call = service.getBean(id,token,pay_type,terminal);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<WalletPay>() {
            @Override
            public void onResponse(Call<WalletPay> call, final Response<WalletPay> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        Log.e("订单",response.body().getData().getOut_trade_no());
                        AndroidJS.out_trade_no=response.body().getData().getOut_trade_no();
                        if (response.body().getData().getPay_status()==1){
                            BaseUtil.coupon="";
                            Toast.makeText(PaymentActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            // TODO: 2018/7/19 跳转成功页
                            gotoActivity(PayResultActivity.class,"result","支付成功",1);

                        }else {
                            Toast.makeText(PaymentActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                            gotoActivity(PayResultActivity.class,"result","支付失败",1);
                            // TODO: 2018/7/19 跳转失败页
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(PaymentActivity.this)
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
                    Toast.makeText(PaymentActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(PaymentActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WalletPay> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getWalletPayService{
        @FormUrlEncoded
        @POST("pay/{id}")
        Call<WalletPay> getBean(@Path("id") String id,@Field("token") String token, @Field("pay_type") String pay_type, @Field("terminal") String terminal);
    }
    public void getWechatPay(String id,String token,String pay_type,String terminal){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/pay/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"/v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getWechatPayService service = retrofit.create(getWechatPayService.class);
        //4.通过回调获得结果
        Call<WechatPay> call = service.getBean(id,token,pay_type,terminal);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<WechatPay>() {
            @Override
            public void onResponse(Call<WechatPay> call, final Response<WechatPay> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        AndroidJS.out_trade_no=response.body().getData().getOut_trade_no();
                        Log.e("订单",response.body().getData().getOut_trade_no());
                        PayReq request = new PayReq();
                        request.appId = response.body().getData().getParams().getAppid();
                        request.partnerId = response.body().getData().getParams().getPartnerid();
                        request.prepayId= response.body().getData().getParams().getPrepayid();
                        request.packageValue = response.body().getData().getParams().getPackageX();
                        request.nonceStr= response.body().getData().getParams().getNoncestr();
                        request.timeStamp= response.body().getData().getParams().getTimestamp()+"";
                        request.sign= response.body().getData().getParams().getPaySign();
                        Log.e("测试",response.body().getData().getParams().getTimestamp()+"");
                        api.sendReq(request);
                        finish();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(PaymentActivity.this)
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
                    Toast.makeText(PaymentActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(PaymentActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WechatPay> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getWechatPayService{
        @FormUrlEncoded
        @POST("pay/{id}")
        Call<WechatPay> getBean(@Path("id") String id, @Field("token") String token, @Field("pay_type") String pay_type, @Field("terminal") String terminal);
    }

    public void getAliPay(String id,String token,String pay_type,String terminal){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        // /v2/order/pay/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"/v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getAliPayService service = retrofit.create(getAliPayService.class);
        //4.通过回调获得结果
        Call<AliPay> call = service.getBean(id,token,pay_type,terminal);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<AliPay>() {
            @Override
            public void onResponse(Call<AliPay> call, final Response<AliPay> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1){
                        AndroidJS.out_trade_no=response.body().getData().getOut_trade_no();
                        Log.e("订单",response.body().getData().getOut_trade_no());
                        final String orderInfo = response.body().getData().getScript();   // 订单信息
//                        final String orderInfo = "alipay_sdk=alipay-sdk-php-20161101&app_id=2016072800109035&biz_content=%7B%22out_trade_no%22%3A%22201710090844153072%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22app%E6%B5%8B%E8%AF%95%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fwww.alipay.com&sign_type=RSA2&timestamp=2017-10-09+08%3A44%3A15&version=1.0&sign=J30r6WaTEYVCBEEhaky266%2BLBrWOCBSmIaE4OMeRdyKnYF0ph3gX3DJY%2BKQ9t7QpDzeW%2FpA3Hx7zl8XDpwKDG4Gxypfq4oi1ptFPzQBrotXN4%2FJpLriNEpwVnrn42s3JnqthfPFJNjY4h1EgiySNLWX4gFxHXwCGq8s3zFpJlbH6zhRuM4aQYUPZuFe%2BPYR8VVJjRsvQAIIkRVytG1iRcunxH2vFqEvusLB%2BIUm83Q85lwXzEwTHIjRHyRtIY2MkmZw0O0edyzFzuGQ56dO6lIxyV%2FWu2iPQ0vTSV3PGN04jF9%2F3vmmifs1roKO8wa%2BoATCcOhfTKDDD%2F3Ooyi1GIw%3D%3D";
                        Log.e("支付宝orderInfo",response.body().getData().getScript());
                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(PaymentActivity.this);
                                Map<String, String> result = alipay.payV2(orderInfo,true);

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(PaymentActivity.this)
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
                    Toast.makeText(PaymentActivity.this, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,2);
                }else {
                    Toast.makeText(PaymentActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AliPay> call, Throwable t) {
                Log.e("aa", "加载失败");
                Log.e("测试",t.toString());

            }
        });
    }
    //定义接口
    public interface getAliPayService{
        @FormUrlEncoded
        @POST("pay/{id}")
        Call<AliPay> getBean(@Path("id") String id, @Field("token") String token, @Field("pay_type") String pay_type, @Field("terminal") String terminal);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
