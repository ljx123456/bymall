package com.qr.bymall.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qr.bymall.R;
import com.qr.bymall.bean.Cart;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.view.MWebView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alu on 2018/7/4.
 */

public class GoodsDetailsActivity extends BaseActivity {

    private RelativeLayout title,back,tab,share,bg;
    private ImageView iv_share;
    private TextView tv_goods,tv_details,tv_recommend;
    private MWebView wv;
    private int flag=1;
    private String url="https://mall.qingrongby.com/home/details.html";
    private Intent intent;
    private String pid;
    private AndroidJS androidJS;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_goods_details);
    }

    @Override
    public void initView() {
        title= (RelativeLayout) findViewById(R.id.layout_goods_details_title);
        bg= (RelativeLayout) findViewById(R.id.layout_goods_details_title_bg);
        back= (RelativeLayout) findViewById(R.id.goods_details_back);
        tab= (RelativeLayout) findViewById(R.id.layout_goods_details_tab);
        share= (RelativeLayout) findViewById(R.id.layout_goods_details_share);
        tv_goods= (TextView) findViewById(R.id.tv_goods_details_tab_goods);
        tv_details= (TextView) findViewById(R.id.tv_goods_details_tab_details);
        tv_recommend= (TextView) findViewById(R.id.tv_goods_details_tab_recommend);
        androidJS=new AndroidJS(GoodsDetailsActivity.this);
        wv= (MWebView) findViewById(R.id.wv_goods_details);
        intent=getIntent();
        if (intent!=null&&!intent.getStringExtra("pid").equals("")){
            Log.e("测试",intent.getStringExtra("pid"));
            pid=intent.getStringExtra("pid");
        }
        Log.e("测试",url);
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
        wv.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        // 禁止 file 协议加载 JavaScript
        if (url.startsWith("file://")){
            wv.getSettings().setJavaScriptEnabled(false);
        }else{
            wv.getSettings().setJavaScriptEnabled(true);
        }
        wv.addJavascriptInterface(androidJS,"bymall");
        wv.setWebChromeClient(new WebChromeClient(){
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(GoodsDetailsActivity.this);
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
//        wv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()) {
//
//                    case MotionEvent.ACTION_DOWN:
//                        Log.e("TAL", "触摸到我了！ACTION_DOWN");
//                        break;
//
//                    case MotionEvent.ACTION_MOVE:
//                        Log.e("TAL", "触摸到我了ACTION_MOVE");
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        Log.e("TAL", "触摸到我了！ACTION_UP");
//                        break;
//
//                }
//                return false;
//            }
//        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intent!=null&&intent.getStringExtra("flag")!=null) {
                    gotoActivity(MainActivity.class,2);
                }else {
                    finish();
                }
            }
        });
        tv_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_goods.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                tv_details.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                tv_recommend.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                //TODO 滑动距离
                wv.post(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadUrl("javascript:backTop()");

                        Log.e("测试","哈哈哈哈");
                    }
                });
            }
        });
        tv_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_goods.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                tv_details.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                tv_recommend.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                //TODO 滑动距离
               wv.post(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadUrl("javascript:LocationDetails()");

                        Log.e("测试","哈哈哈哈");
                    }
                });
            }
        });
        tv_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_goods.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                tv_details.setTextSize(TypedValue.COMPLEX_UNIT_SP,11);
                tv_recommend.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                //TODO 滑动距离
                wv.post(new Runnable() {
                    @Override
                    public void run() {
                        wv.loadUrl("javascript:LocationRecommend()");

                        Log.e("测试","哈哈哈哈");
                    }
                });
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Todo 分享
                new ShareAction(GoodsDetailsActivity.this).withText("hello")
                        .setDisplayList(SHARE_MEDIA.WEIXIN)
                        .setCallback(new UMShareListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {

                            }

                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Toast.makeText(GoodsDetailsActivity.this,"成功了",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable t) {
                                Toast.makeText(GoodsDetailsActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Toast.makeText(GoodsDetailsActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .open();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
