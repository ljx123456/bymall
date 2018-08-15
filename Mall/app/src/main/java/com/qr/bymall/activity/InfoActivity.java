package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.qr.bymall.R;
import com.qr.bymall.bean.Cart;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.view.MWebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/17.
 */

public class InfoActivity extends BaseActivity {

    private MWebView wv;
    private String url;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_info);
    }

    @Override
    public void initView() {
        wv= (MWebView) findViewById(R.id.info_wv);
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
        wv.getSettings().setAllowFileAccess(false);
        wv.getSettings().setAllowFileAccessFromFileURLs(false);
        wv.getSettings().setAllowUniversalAccessFromFileURLs(false);


        // 禁止 file 协议加载 JavaScript
        if (url.startsWith("file://")){
            wv.getSettings().setJavaScriptEnabled(false);
        }else{
            wv.getSettings().setJavaScriptEnabled(true);
        }
        wv.addJavascriptInterface(new AndroidJS(getApplicationContext()),"bymall");
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(InfoActivity.this);
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
}
