package com.qr.bymall.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.view.MWebView;
import com.qr.bymall.view.OnMultiClickListener;

/**
 * Created by alu on 2018/6/27.
 */

public class ShoppingCartFragment extends Fragment {

    private RelativeLayout manage;
    private MWebView wv;
    private String url="https://mall.qingrongby.com/home/cart.html";
    private TextView tv;
    private boolean flag=false;
    private boolean isFlag=false;
    private LinearLayout layout_cart;
    private RelativeLayout layout_net;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shoppingcart, container, false);
        manage= (RelativeLayout) view.findViewById(R.id.layout_cart_manage);
        tv= (TextView) view.findViewById(R.id.tv_cart_manage);
        wv= (MWebView) view.findViewById(R.id.shopping_cart_wv);
        layout_cart= (LinearLayout) view.findViewById(R.id.layout_cart);
        layout_net= (RelativeLayout) view.findViewById(R.id.layout_cart_net);
        btn= (Button) view.findViewById(R.id.btn_net);
        if (isConn(getContext())){
            layout_cart.setVisibility(View.VISIBLE);
            layout_net.setVisibility(View.GONE);
            initWv();
        }else {
            layout_cart.setVisibility(View.GONE);
            layout_net.setVisibility(View.VISIBLE);
        }
        isFlag=true;
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (isConn(getContext())){
                    layout_cart.setVisibility(View.VISIBLE);
                    layout_net.setVisibility(View.GONE);
                    initWv();
                }else {
                    layout_cart.setVisibility(View.GONE);
                    layout_net.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }

    private void initWv(){
        wv.getSettings().setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv.setVerticalScrollBarEnabled(false); //垂直不显示
        wv.setScrollContainer(false);
        wv.setScrollbarFadingEnabled(false);
        wv.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
//
        //自适应屏幕


        wv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wv.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放
        wv.getSettings().setLoadWithOverviewMode(true);

        wv.getSettings().setTextZoom(100);


        //设置可以支持缩放

        wv.getSettings().setSupportZoom(true);

        //扩大比例的缩放

        wv.getSettings().setUseWideViewPort(true);

        //设置是否出现缩放工具

        wv.getSettings().setBuiltInZoomControls(false);
        wv.setLayerType(View.LAYER_TYPE_HARDWARE,null);//开启硬件加速
        wv.loadUrl(url);
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }
        });
        wv.getSettings().setAllowFileAccess(false);
        wv.getSettings().setAllowFileAccessFromFileURLs(false);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(false);
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        // 禁止 file 协议加载 JavaScript
        if (url.startsWith("file://")){
            wv.getSettings().setJavaScriptEnabled(false);
        }else{
            wv.getSettings().setJavaScriptEnabled(true);
        }
        wv.addJavascriptInterface(new AndroidJS(getContext()),"bymall");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        wv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        Log.e("TAL", "触摸到我了！ACTION_DOWN");
                        break;

                    case MotionEvent.ACTION_MOVE:
                        Log.e("TAL", "触摸到我了ACTION_MOVE");

                        break;
                    case MotionEvent.ACTION_UP:
                        Log.e("TAL", "触摸到我了！ACTION_UP");
                        break;

                }
                return false;
            }
        });
        wv.setOnScrollChangeListener(new MWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {

            }
        });
//        //禁止滑动
//        wv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                return (event.getAction() == MotionEvent.ACTION_MOVE);
//            }
//        });
        manage.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (flag){
                    tv.setText("管理");
                    flag=false;
                }else {
                    tv.setText("完成");
                    flag=true;
                }
                wv.post(new Runnable() {
                    @Override
                    public void run() {

                        wv.loadUrl("javascript:cartSwitch()");
                        Log.e("测试","哈哈哈哈");
                    }
                });
//                if (Build.VERSION.SDK_INT>18) {
//                    wv.evaluateJavascript("javascript:cartSwitch()", new ValueCallback<String>() {
//                        @Override
//                        public void onReceiveValue(String s) {
//
//                        }
//                    });
//                }
            }
        });
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
                layout_cart.setVisibility(View.VISIBLE);
                layout_net.setVisibility(View.GONE);
                initWv();
            }else {
                layout_cart.setVisibility(View.GONE);
                layout_net.setVisibility(View.VISIBLE);
            }
        }
    }
}
