package com.qr.bymall.activity;

import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.adapter.NewOrderGoodsAdapter;
import com.qr.bymall.adapter.OrderDetailsAdapter;
import com.qr.bymall.bean.OrderDetails;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.CancelOrderDetailsPopupWindow;
import com.qr.bymall.view.MyListView;
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
 * Created by alu on 2018/7/23.
 */

public class NeedPayOrderDetailsActivity extends BaseActivity {

    private RelativeLayout back,layout_need_pay_title,layout_need_pay_bottom,layout_pay_title,
            layout_bottom,layout_unfinished_title,layout_unfinished_bottom,layout_finished_title;
    private TextView tv_address_name,tv_address_phone,tv_address_address,tv_store,
            tv_price,tv_fee,tv_coupons,tv_money,tv_pay,tv_cancel,tv_call;
    private MyListView lv;
    private OrderDetailsAdapter adapter;
    private CancelOrderDetailsPopupWindow pw;
    private Intent intent;
    private static final int REQUEST_CODE_ASK_CALL_PHONE=1;
    private String phone;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_need_pay_oder_details);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.need_pay_order_details_back);
        layout_need_pay_title= (RelativeLayout) findViewById(R.id.layout_need_pay_order_details_title);
        layout_need_pay_bottom= (RelativeLayout) findViewById(R.id.layout_need_pay_order_details);
        layout_pay_title= (RelativeLayout) findViewById(R.id.layout_pay_order_details_title);
        layout_bottom= (RelativeLayout) findViewById(R.id.layout_need_pay_order_details_bottom);
        layout_unfinished_title= (RelativeLayout) findViewById(R.id.layout_unfinished_order_details_title);
        layout_unfinished_bottom= (RelativeLayout) findViewById(R.id.layout_unfinished_order_details);
        layout_finished_title= (RelativeLayout) findViewById(R.id.layout_finished_order_details_title);
        tv_address_name= (TextView) findViewById(R.id.tv_need_pay_details_address_consignee);
        tv_address_phone= (TextView) findViewById(R.id.tv_need_pay_details_address_phone);
        tv_address_address= (TextView) findViewById(R.id.tv_need_pay_details_address_address);
        tv_store= (TextView) findViewById(R.id.tv_need_pay_order_details_store);
        tv_price= (TextView) findViewById(R.id.tv_need_pay_order_details_price);
        tv_fee= (TextView) findViewById(R.id.tv_need_pay_order_details_fee);
        tv_coupons= (TextView) findViewById(R.id.tv_need_pay_order_details_coupon);
        tv_money= (TextView) findViewById(R.id.tv_need_pay_order_details_money);
        tv_pay= (TextView) findViewById(R.id.tv_need_pay_order_details_pay);
        tv_cancel= (TextView) findViewById(R.id.tv_need_pay_order_details_cancel);
        tv_call= (TextView) findViewById(R.id.tv_unfinished_order_details_call);

        lv= (MyListView) findViewById(R.id.lv_need_pay_order_details);
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("id")!=null) {
            getOrderDetails(intent.getStringExtra("id"), SharedUtil.getToken(getApplicationContext()));
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent!=null&&intent.getStringExtra("pay")!=null){
                    gotoActivity(NeedPayActivity.class,2);
                }else {
                    gotoActivity(NeedPayActivity.class,0);
                }
            }
        });
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
//        Log.e("学校", "设置了背景色"+bgAlpha);
    }
    private void testCall() {
        /** 1. 检测ContextCompat.checkSelfPermission(),返回值
         * PackageManager.PERMISSION_GRANTED 授权已成功
         * PackageManager.PERMISSION_DENIED 授权被拒绝
         */
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 清单里面授权被拒绝，向用户申请
            // 2. 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_CODE_ASK_CALL_PHONE);
        } else {
            //获取位置
            CallPhone();
            Log.e("MainActivity", "1");
        }
    }
    private void CallPhone() {
        Log.e("拨打",phone);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    //     3. 处理回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUEST_CODE_ASK_CALL_PHONE){
            // 判断权限是不是授权了
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                // 用户真正同意了授权
                CallPhone();
                Log.e("MainActivity", "2");
            }else {
                // 用户给拒绝了
                Toast.makeText(this, "权限被拒绝", Toast.LENGTH_SHORT).show();
                // 能不能在用户彻底拒绝权限之后，可以友好的提示他一下
                // 4. 彻底拒绝的时候
                //获取相关权限，再次和用户进行交互，沟通，提示用户一旦拒绝之后就将无法实现
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
                    showDialog();
                }
            }
        }
    }
    // 提示用户去设置
    public void showDialog(){
        new AlertDialog.Builder(this)
                .setMessage("拨号权限被彻底拒绝，请到设置里面打开，才可使用此功能！")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到本应用的设置页面，可以打开权限
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", NeedPayOrderDetailsActivity.this.getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
    }
    public void showCall(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NeedPayOrderDetailsActivity.this);
        View view = View
                .inflate(getApplicationContext(), R.layout.dialog_order_call, null);
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
                testCall();
            }
        });
        dialog.show();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();  //为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.height = 240;   //高度设置为屏幕的0.3
        p.width = 552;    //宽度设置为屏幕的0.5
        dialog.getWindow().setAttributes(p);     //设置生效


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
            public void onResponse(Call<OrderDetails> call, final Response<OrderDetails> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData().getShop_name()+"进来了");
                        switch (response.body().getData().getOrder_status()){
                            case "0":
                                layout_bottom.setVisibility(View.VISIBLE);
                                layout_need_pay_title.setVisibility(View.VISIBLE);
                                layout_need_pay_bottom.setVisibility(View.VISIBLE);
                                layout_pay_title.setVisibility(View.GONE);
                                layout_unfinished_title.setVisibility(View.GONE);
                                layout_unfinished_bottom.setVisibility(View.GONE);
                                layout_finished_title.setVisibility(View.GONE);
                                tv_pay.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        gotoActivity(PaymentActivity.class,"order_id",id,1);
                                    }
                                });
                                tv_cancel.setOnClickListener(new OnMultiClickListener() {
                                    @Override
                                    public void onMultiClick(View v) {
                                        if (pw == null) {
                                            pw=new CancelOrderDetailsPopupWindow(NeedPayOrderDetailsActivity.this,id);
                                        }
                                        if (pw.isShowing()) {
                                            pw.dismiss();
                                            setBackgroundAlpha(NeedPayOrderDetailsActivity.this, 1.0f);
                                            return;
                                        }
                                        pw.show();

                                    }
                                });
                                break;
                            case "1":
                                layout_bottom.setVisibility(View.GONE);
                                layout_need_pay_title.setVisibility(View.GONE);
                                layout_need_pay_bottom.setVisibility(View.GONE);
                                layout_pay_title.setVisibility(View.VISIBLE);
                                layout_unfinished_title.setVisibility(View.GONE);
                                layout_unfinished_bottom.setVisibility(View.GONE);
                                layout_finished_title.setVisibility(View.GONE);
                                break;
                            case "2":
                                layout_bottom.setVisibility(View.VISIBLE);
                                layout_need_pay_title.setVisibility(View.GONE);
                                layout_need_pay_bottom.setVisibility(View.GONE);
                                layout_pay_title.setVisibility(View.GONE);
                                layout_unfinished_title.setVisibility(View.VISIBLE);
                                layout_unfinished_bottom.setVisibility(View.VISIBLE);
                                layout_finished_title.setVisibility(View.GONE);
                                phone=response.body().getData().getAgent_phone();
                                tv_call.setOnClickListener(new OnMultiClickListener() {
                                    @Override
                                    public void onMultiClick(View v) {
                                        showCall();
                                    }
                                });

                                break;
                            case "3":
                                layout_bottom.setVisibility(View.GONE);
                                layout_need_pay_title.setVisibility(View.GONE);
                                layout_need_pay_bottom.setVisibility(View.GONE);
                                layout_pay_title.setVisibility(View.GONE);
                                layout_unfinished_title.setVisibility(View.GONE);
                                layout_unfinished_bottom.setVisibility(View.GONE);
                                layout_finished_title.setVisibility(View.VISIBLE);
                                break;
                        }
                        tv_address_name.setText(response.body().getData().getAddress().getRec_name());
                        tv_address_phone.setText(response.body().getData().getAddress().getRec_phone());
                        tv_address_address.setText(response.body().getData().getAddress().getSchool()+
                                response.body().getData().getAddress().getRoom()+response.body().getData().getAddress().getRec_detail());
                        tv_store.setText(response.body().getData().getShop_name());
                        adapter=new OrderDetailsAdapter(NeedPayOrderDetailsActivity.this,response.body().getData().getProducts());
                        lv.setAdapter(adapter);
                        tv_price.setText("¥ "+response.body().getData().getProduct_price());
                        tv_fee.setText("¥ "+response.body().getData().getExpress_fee());
                        tv_coupons.setText("-¥ "+response.body().getData().getCoupon_fee());
                        tv_money.setText("¥ "+response.body().getData().getPay_money());

                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(NeedPayOrderDetailsActivity.this)
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
}
