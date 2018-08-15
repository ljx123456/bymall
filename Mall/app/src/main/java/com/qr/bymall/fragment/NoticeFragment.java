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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.qr.bymall.R;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.view.MWebView;
import com.qr.bymall.view.OnMultiClickListener;

/**
 * Created by alu on 2018/6/27.
 */

public class NoticeFragment extends Fragment {


    private WebView wv;
    private AndroidJS androidJS;
    private String url = "https://mall.qingrongby.com/home/news.html";
    private LinearLayout layout_news;
    private RelativeLayout layout_net;
    private Button btn;
    private boolean isFlag=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news, container, false);
        wv = (WebView) view.findViewById(R.id.news_wv);
        layout_news= (LinearLayout) view.findViewById(R.id.layout_news);
        layout_net= (RelativeLayout) view.findViewById(R.id.layout_news_net);
        btn= (Button) view.findViewById(R.id.btn_net);
        if (isConn(getContext())){
            layout_news.setVisibility(View.VISIBLE);
            layout_net.setVisibility(View.GONE);
            initWv();
        }else {
            layout_news.setVisibility(View.GONE);
            layout_net.setVisibility(View.VISIBLE);
        }
        isFlag=true;
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (isConn(getContext())){
                    layout_news.setVisibility(View.VISIBLE);
                    layout_net.setVisibility(View.GONE);
                    initWv();
                }else {
                    layout_news.setVisibility(View.GONE);
                    layout_net.setVisibility(View.VISIBLE);
                }
            }
        });
        return view;
    }

    private void initWv(){
        androidJS=new AndroidJS(getContext());
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
        wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
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
        wv.loadUrl(url);
        wv.addJavascriptInterface(new AndroidJS(getContext()), "bymall");
        wv.setWebChromeClient(new WebChromeClient() {
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

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
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
                layout_news.setVisibility(View.VISIBLE);
                layout_net.setVisibility(View.GONE);
                initWv();
            }else {
                layout_news.setVisibility(View.GONE);
                layout_net.setVisibility(View.VISIBLE);
            }
        }
    }
}
