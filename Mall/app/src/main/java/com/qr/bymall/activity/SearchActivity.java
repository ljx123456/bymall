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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.util.AndroidJS;

/**
 * Created by alu on 2018/7/24.
 */

public class SearchActivity extends BaseActivity {

    private RelativeLayout back,search;
    private EditText edt;
    private WebView wv;
    private String url="https://mall.qingrongby.com/home/search.html";
//    private String url="https://www.baidu.com";
    private AndroidJS androidJS;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.search_back);
        search= (RelativeLayout) findViewById(R.id.layout_search);
        edt= (EditText) findViewById(R.id.edt_search);
        wv= (WebView) findViewById(R.id.search_wv);
        androidJS=new AndroidJS(SearchActivity.this);
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


//         禁止 file 协议加载 JavaScript
        if (url.startsWith("file://")){
            wv.getSettings().setJavaScriptEnabled(false);
        }else{
            wv.getSettings().setJavaScriptEnabled(true);
        }
        wv.loadUrl(url);
        wv.addJavascriptInterface(androidJS, "bymall");
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(SearchActivity.this);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt.getText()!=null&&!edt.getText().toString().equals("")){

                    wv.post(new Runnable() {
                        @Override
                        public void run() {
                            String search_txt=edt.getText().toString();
//                            String search_txt="哈哈哈";
                            wv.loadUrl("javascript:goSearch('" + search_txt + "')");
//                            wv.loadUrl("javascript:yy("+search_txt+")");
//                            wv.loadUrl("javascript:showInfoFromJava('" + search_txt + "')");
//                            Log.e("测试","哈哈哈哈"+edt.getText().toString());
                            Log.e("测试","哈哈哈哈"+search_txt);
                        }
                    });
                }else {
                    Toast.makeText(SearchActivity.this, "请输入搜索", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
