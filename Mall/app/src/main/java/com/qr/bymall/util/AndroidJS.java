package com.qr.bymall.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;

import com.google.gson.Gson;
import com.qr.bymall.activity.ChangeRoomActivity;
import com.qr.bymall.activity.ChangeSchoolActivity;
import com.qr.bymall.activity.CustomerServiceActivity;
import com.qr.bymall.activity.GoodsDetailsActivity;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.activity.MainActivity;
import com.qr.bymall.activity.NeedPayActivity;
import com.qr.bymall.activity.NeedPayOrderDetailsActivity;
import com.qr.bymall.activity.NewOrderActivity;
import com.qr.bymall.bean.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/5.
 */

public  class AndroidJS extends Object {
    private Context context;
    public  static  String pid="";
    public static String out_trade_no="";
    public AndroidJS(Context context){
        this.context=context;
    }
    @JavascriptInterface
    public String getParams(String str){
        String s="";
        if (str.equals("school")){
            s= SharedUtil.getSchool_id(context);
            Log.e("测试school",s);
            return s;
        }else if(str.equals("room")){
            s= SharedUtil.getRoom_id(context);
            Log.e("测试room",s);
            return s;
        }else if (str.equals("token")){
            s= SharedUtil.getToken(context);
            Log.e("测试token",s);
            return s;
        }else if (str.equals("agent")){
            s= SharedUtil.getAgent_id(context);
            Log.e("测试agent",s);
            return s;
        }
        Log.e("测试6",s);
        return s;
    }
    @JavascriptInterface
    public String[] getParams(){
        String[] s={SharedUtil.getSchool_id(context),SharedUtil.getRoom_id(context),SharedUtil.getToken(context),SharedUtil.getAgent_id(context)};
        Log.e("测试6",s.toString());
        return s;
    }


    @JavascriptInterface
    public void choiceRoom(){
        if (SharedUtil.getSchool_id(context).equals("")){
            Intent intent = new Intent(context, ChangeSchoolActivity.class);
            context.startActivity(intent);
        }else {
            Intent intent = new Intent(context, ChangeRoomActivity.class);
            context.startActivity(intent);
        }
    }
    @JavascriptInterface
    public void goGoodsDetails(String id){
        Log.e("测试","跳转详情页");
        pid=id;
        Intent intent=new Intent(context,GoodsDetailsActivity.class);
        intent.putExtra("pid",id);
        context.startActivity(intent);
    }
    @JavascriptInterface
    public void goGoodsDetails_pay(String id){
        Log.e("测试","跳转详情页");
        pid=id;
        Intent intent=new Intent(context,GoodsDetailsActivity.class);
        intent.putExtra("pid",id);
        intent.putExtra("flag","0");
        context.startActivity(intent);
        ((Activity)context).finish();
    }


    @JavascriptInterface
    public  String getPid(){
        Log.e("测试","pid"+pid);
        return pid;
    }


    @JavascriptInterface
    public void gotoNewOrder(String json){
        Log.e("测试","确认订单");
        Log.e("测试","json"+json);
        Gson gson=new Gson();
        Cart cart=gson.fromJson(json,Cart.class);
        Log.e("测试",cart.getAgent_id());
        List<Cart.ListBean> list=new ArrayList<Cart.ListBean>();
        list=cart.getList();
        String products=gson.toJson(list);
        Log.e("测试",products);
        SharedUtil.saveAgent_id(context,cart.getAgent_id());
        SharedUtil.saveProducts(context,products);
        Intent intent=new Intent(context, NewOrderActivity.class);
//        intent.putExtra("products",products);
//        intent.putExtra("agent_id",cart.getAgent_id());
        context.startActivity(intent);
//                gotoActivity(NewOrderActivity.class,"products",products,"agent_id",cart.getAgent_id());
    }

    @JavascriptInterface
    public  void saveAgent(String agent){
        Log.e("测试agent",agent);
        SharedUtil.saveAgent_id(context,agent);
    }

    @JavascriptInterface
    public void gotoLogin(){
        Log.e("测试0.0","跳转登录页");
        Intent intent =new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @JavascriptInterface
    public  String getTradeNumber(){
        Log.e("测试","out_trade_no"+out_trade_no);
        return out_trade_no;
    }
    @JavascriptInterface
    public  void backOrder(){
        Log.e("测试","跳转待支付订单页");
        Intent intent =new Intent(context, NeedPayActivity.class);
        intent.putExtra("pay","0");
        context.startActivity(intent);
        ((Activity)context).finish();
    }
    @JavascriptInterface
    public  void backHome(){
        Log.e("测试","跳转首页");
        Intent intent =new Intent(context, MainActivity.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
    @JavascriptInterface
    public  void goOrder(String id){
        Log.e("测试","跳转订单页");
        Intent intent =new Intent(context, NeedPayOrderDetailsActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("pay","0");
        context.startActivity(intent);
        ((Activity)context).finish();
    }
    @JavascriptInterface
    public  void goShoppingCart(){
        Log.e("测试","跳转购物车");
        Intent intent =new Intent(context, MainActivity.class);
        intent.putExtra("name","0");
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    @JavascriptInterface
    public  void goCustomerService(){
        Log.e("测试","跳转客服页");
        Intent intent =new Intent(context, CustomerServiceActivity.class);
        context.startActivity(intent);
    }
    @JavascriptInterface
    public float getH(){
        Log.e("测试","获取高度");
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);
//        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
//        float h=44*density;
        float h=44;
        Log.e("测试高度",""+h);
        return h;
    }
}
