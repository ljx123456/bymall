package com.qr.bymall.view;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.adapter.NeedPayAdapter;
import com.qr.bymall.bean.Code;
import com.qr.bymall.bean.OrderList;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;

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
 * Created by alu on 2018/7/20.
 */

public class CancelOrderPopupWindow extends PopupWindow {


    private Activity mActivity;

    private TextView tv1,tv2,tv3;
    private OrderList.DataBean dataBean;
    private NeedPayAdapter adapter;

    public CancelOrderPopupWindow(final Activity mActivity, final NeedPayAdapter adapter, final OrderList.DataBean dataBean) {
        super(mActivity.getLayoutInflater().inflate(R.layout.cancel_order_popupwindow,null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mActivity = mActivity;
        this.adapter=adapter;
        this.dataBean=dataBean;

        tv1 = (TextView) getContentView().findViewById(R.id.tv_cancel_order_tv1);
        tv2 = (TextView) getContentView().findViewById(R.id.tv_cancel_order_tv2);
        tv3 = (TextView) getContentView().findViewById(R.id.tv_cancel_order_tv3);

//        cancel.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View v) {
//                //销毁弹出框
//                dismiss();
//                setBackgroundAlpha(mActivity,1.0f);
//            }
//        });
        //设置按钮监听
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "tv1", Toast.LENGTH_SHORT).show();
                getCancel(dataBean.getOrder_id(), SharedUtil.getToken(mActivity),tv1.getText().toString());

                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "tv2", Toast.LENGTH_SHORT).show();
                getCancel(dataBean.getOrder_id(), SharedUtil.getToken(mActivity),tv1.getText().toString());
                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, "tv3", Toast.LENGTH_SHORT).show();
                getCancel(dataBean.getOrder_id(), SharedUtil.getToken(mActivity),tv1.getText().toString());
                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });

        setFocusable(true);// 获得焦点
        this.setTouchable(true);
        this.setOutsideTouchable(true);
//        setBackgroundDrawable(new BitmapDrawable());
        setBackgroundAlpha(mActivity,1.0f);//设置屏幕透明度
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        getContentView().setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = getContentView().findViewById(R.id.cancel_order_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                        setBackgroundAlpha(mActivity,1.0f);
                    }
                }
                return true;
            }
        });


    }
    // 展示的方法
    public void show(){
        showAtLocation(mActivity.getWindow().getDecorView(),
                Gravity.BOTTOM,0,0);
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

    public void getCancel(String id,String token,String reason){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        //  /v2/order/cancel/{id}
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/order/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getCancelService service = retrofit.create(getCancelService.class);
        //4.通过回调获得结果
        Call<Code> call = service.getBean(id,token,reason);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Code>() {
            @Override
            public void onResponse(Call<Code> call, Response<Code> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        Log.e("测试哈哈哈",response.body().getData()+"进来了");
                        adapter.remove(dataBean);
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(mActivity, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(mActivity)
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
                    Toast.makeText(mActivity, "登录过期，请重新登录", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(mActivity,LoginActivity.class);
                    mActivity.startActivity(intent);
                }else {
                    Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Code> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getCancelService{
        @GET("cancel/{id}")
        Call<Code> getBean(@Path("id") String id, @Query("token") String token, @Query("reason") String reason);
    }
}