package com.qr.bymall.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.qr.bymall.R;
import com.qr.bymall.activity.ChangeRoomActivity;
import com.qr.bymall.activity.ChangeSchoolActivity;
import com.qr.bymall.activity.GoodsDetailsActivity;
import com.qr.bymall.activity.LoginActivity;
import com.qr.bymall.activity.SearchActivity;
import com.qr.bymall.activity.SearchSchoolActivity;
import com.qr.bymall.bean.School;
import com.qr.bymall.bean.SearchSchool;
import com.qr.bymall.bean.Version;
import com.qr.bymall.util.AndroidJS;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.MWebView;
import com.qr.bymall.view.OnMultiClickListener;
import com.qr.bymall.view.UpdatePopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by alu on 2018/6/27.
 */

public class HomePageFragment extends Fragment{

    private MWebView wv;
    private LinearLayout layout_title;
    private RelativeLayout search,location,layout_net;
    private TextView tv,tv_search;
    private Button btn;
    private static final int ACCESS_COARSE_LOCATION_REQUESTCODE = 1;
    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    private AMapLocationListener mLocationListener = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String url="https://mall.qingrongby.com/";
    private boolean isFlag=false;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private UpdatePopupWindow window;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage, container, false);
        wv= (MWebView) view.findViewById(R.id.homepage_wv);
        layout_title= (LinearLayout) view.findViewById(R.id.homepage_title);
        layout_net= (RelativeLayout) view.findViewById(R.id.layout_homepage_net);
        search= (RelativeLayout) view.findViewById(R.id.homepage_search);
        location= (RelativeLayout) view.findViewById(R.id.homepage_location);
        tv= (TextView) view.findViewById(R.id.tv_homepage_school);
        tv_search= (TextView) view.findViewById(R.id.tv_homepage_search);
        btn= (Button) view.findViewById(R.id.btn_net);
        testCall();
        initGPS();
        if (isConn(getContext())){
            layout_title.setVisibility(View.VISIBLE);
            wv.setVisibility(View.VISIBLE);
            layout_net.setVisibility(View.GONE);
            initNet();
        }else {
            layout_title.setVisibility(View.GONE);
            wv.setVisibility(View.GONE);
            layout_net.setVisibility(View.VISIBLE);
        }
        isFlag=true;
        btn.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                if (isConn(getContext())){
                    layout_title.setVisibility(View.VISIBLE);
                    wv.setVisibility(View.VISIBLE);
                    layout_net.setVisibility(View.GONE);
                    initNet();
                }else {
                    layout_title.setVisibility(View.GONE);
                    wv.setVisibility(View.GONE);
                    layout_net.setVisibility(View.VISIBLE);
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), ChangeSchoolActivity.class);
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        Log.e("ces",SharedUtil.getToken(getContext()));

        return view;
    }
    private void initNet(){
        if (!SharedUtil.getSchool_Name(getContext()).equals("")){
            Log.e("测试",SharedUtil.getSchool_Name(getContext()));
            tv.setText(SharedUtil.getSchool_Name(getContext()));
        }
        if (!SharedUtil.getSearch_Text(getContext()).equals("")){
            tv_search.setText(SharedUtil.getSearch_Text(getContext()));
        }
        getVersion();
        initWv();
    }
    private void init(){
        //初始化定位
        mLocationClient = new AMapLocationClient(getContext());
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //可在其中解析amapLocation获取相应内容。
                        amapLocation.getLatitude();//获取纬度
                        amapLocation.getLongitude();//获取经度
                        getLocation(amapLocation.getLongitude(),amapLocation.getLatitude());
                        Log.e("测试",amapLocation.getLatitude()+"+"+amapLocation.getLongitude());
                    }else {
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        };
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
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
//        //禁止滑动
//        wv.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent event) {
//                return (event.getAction() == MotionEvent.ACTION_MOVE);
//            }
//        });
        wv.setOnScrollChangeListener(new MWebView.OnScrollChangeListener() {
            @Override
            public void onPageEnd(int l, int t, int oldl, int oldt) {

            }

            @Override
            public void onPageTop(int l, int t, int oldl, int oldt) {
                layout_title.setBackgroundColor(0x00000000);
                location.setBackgroundResource(R.drawable.layout_location_bg);
                Log.e("测试","top");
            }

            @Override
            public void onScrollChanged(int l, int y, int oldl, int oldt) {
                if (y <= 0) {
                    layout_title.setBackgroundColor(Color.argb((int) 0, 223, 69, 69));//AGB由相关工具获得，或者美工提供
                    location.setBackgroundResource(R.drawable.layout_location_bg);
                } else if (y > 0 && y <= 244) {
                    float scale = (float) y / 244;
                    float alpha = (255 * scale);
                    // 只是layout背景透明
                    layout_title.setBackgroundColor(Color.argb((int) alpha, 223, 69, 69));
                    location.setBackgroundResource(R.drawable.layout_location_bg);
                } else {
                    layout_title.setBackgroundColor(Color.argb((int) 255, 201, 69, 84));
                    location.setBackground(null);
                }
//                float f = (t + 0f) / 120;//滑动距离450px
//                if (f > 1) {
//                    f = 1f;
//                    findViewById(R.id.title).setBackgroundColor(0xff000000);
//                    findViewById(R.id.title).setA
//                    findViewById(R.id.title).setBackgroundColor(ColorUtils.changeAlpha(ContextCompat.getColor(YxServiceActivity.this, R.color.epblueyl),(int)(f * 1 * 0xff)));
//                    Log.e("测试","on");
//                }
//                if (f < 0) {
//                    f = 0;
//                }
            }
        });
    }

    // 1. 检测权限是否授权成功
    private void testCall() {
        /** 1. 检测ContextCompat.checkSelfPermission(),返回值
         * PackageManager.PERMISSION_GRANTED 授权已成功
         * PackageManager.PERMISSION_DENIED 授权被拒绝
         */
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 清单里面授权被拒绝，向用户申请
            // 2. 申请权限
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},ACCESS_COARSE_LOCATION_REQUESTCODE);
        } else {
            //获取位置
            if (SharedUtil.getSchool_id(getContext()).equals("")) {
                init();
            }
            Log.e("MainActivity", "1");
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ACCESS_COARSE_LOCATION_REQUESTCODE){
            // 判断权限是不是授权了
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                // 用户真正同意了授权
                if (SharedUtil.getSchool_id(getContext()).equals("")) {
                    init();
                }
                Log.e("MainActivity", "2");
            }else {
                // 用户给拒绝了
                Toast.makeText(getContext(), "权限被拒绝", Toast.LENGTH_SHORT).show();
                // 能不能在用户彻底拒绝权限之后，可以友好的提示他一下
                // 4. 彻底拒绝的时候
                //获取相关权限，再次和用户进行交互，沟通，提示用户一旦拒绝之后就将无法实现
                if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)){
                    showDialog();

                }
            }
        }
    }

    // 提示用户去设置
    public void showDialog(){
        new AlertDialog.Builder(getContext())
                .setMessage("获取位置权限被彻底拒绝，请到设置里面打开，才可使用此功能！")
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 跳转到本应用的设置页面，可以打开权限
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri data = Uri.fromParts("package",getContext().getPackageName(),null);
                        intent.setData(data);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("取消",null)
                .create()
                .show();
    }

    private void initGPS() {
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(getContext(), "请打开GPS", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为方便您更好的获取周边加油站，请先打开GPS");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(getContext(), "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        } else {
//            searchRouteResult(startPoint, endPoint);//路径规划
            // 弹出Toast
//          Toast.makeText(TrainDetailsActivity.this, "GPS is ready",Toast.LENGTH_LONG).show();
//          // 弹出对话框
//          new AlertDialog.Builder(this).setMessage("GPS is ready").setPositiveButton("OK", null).show();
        }
    }
    /**
     * 判断网络连接是否已开
     * true 已打开  false 未打开
     */
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

    public void getLocation(double lng,double lat){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        ///v2/school/location
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"v2/school/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getLocationService service = retrofit.create(getLocationService.class);
        //4.通过回调获得结果
        Call<School> call = service.getBean(lng,lat);
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<School>() {
            @Override
            public void onResponse(Call<School> call, Response<School> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1) {
                        tv_search.setText(response.body().getData().getSearch_text());
                        tv.setText(response.body().getData().getSchool_name());
                        SharedUtil.saveSchool_id(getContext(),response.body().getData().getSchool_id());
                        SharedUtil.saveSchool_Name(getContext(),response.body().getData().getSchool_name());
                        SharedUtil.saveSearch_Text(getContext(),response.body().getData().getSearch_text());
                        if (response.body().getData().getFlag()==1){
                            Intent intent =new Intent(getContext(),ChangeRoomActivity.class);
                            startActivity(intent);
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(getContext())
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
                    }else if (response.body().getCode()==1003){
                        Intent intent=new Intent(getContext(), SearchSchoolActivity.class);
                        startActivity(intent);
                    }
                }else {
                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<School> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getLocationService{
        @GET("location")
        Call<School> getBean(@Query("lng") double lng,@Query("lat") double lat);
    }

    public void getVersion(){
        //1.初始化Retrofit
        //网址、解析工具（gosn等）
        ///version/mall/new
        final Retrofit retrofit = new Retrofit.Builder().
                baseUrl(BaseUtil.url+"version/mall/").
                addConverterFactory(GsonConverterFactory.create())
                .build();
        //3.准备请求网络
        getVersionService service = retrofit.create(getVersionService.class);
        //4.通过回调获得结果
        Call<Version> call = service.getBean();
        //5.请求加入调度，正式排队去网络获取数据
        call.enqueue(new Callback<Version>() {
            @Override
            public void onResponse(Call<Version> call, final Response<Version> response) {
                if (response.code()==200){
                    if (response.body().getCode()==1 ){
                        if (!response.body().getData().getVersion().equals("1.0.0")){
                            if (window == null) {
                                window = new UpdatePopupWindow((Activity)getContext(), new UpdatePopupWindow.Listener() {
                                    @Override
                                    public void toUpdate() {
                                        verifyStoragePermissions((Activity)getContext());
                                        downloadApk();
                                    }
                                },response.body().getData() );
                            }
                            if (window.isShowing()) {
                                window.dismiss();
                                setBackgroundAlpha((Activity)getContext(), 1.0f);
                                return;
                            }
                            window.show();
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(getContext())
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
                }else {

                    Toast.makeText(getContext(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Version> call, Throwable t) {
                Log.e("aa", "加载失败");

            }
        });
    }
    //定义接口
    public interface getVersionService{
        @GET("new")
        Call<Version> getBean();
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    /**
     * 从服务器端下载最新apk
     */
    private void downloadApk() {
        //显示下载进度
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();

        //访问网络下载apk
        new Thread(new DownloadApk(dialog)).start();
    }

    /**
     * 访问网络下载apk
     */
    private class DownloadApk implements Runnable {
        private ProgressDialog dialog;
        InputStream is;
        FileOutputStream fos;

        public DownloadApk(ProgressDialog dialog) {
            this.dialog = dialog;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().get().url(url).build();
            try {
                okhttp3.Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Log.e("测试", "开始下载apk");
                    //获取内容总长度
                    long contentLength = response.body().contentLength();
                    Log.e("测试", "长度"+contentLength);
                    //设置最大值
                    dialog.setMax((int) contentLength);
                    //保存到sd卡
                    File apkFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".apk");
                    fos = new FileOutputStream(apkFile);
                    //获得输入流
                    is = response.body().byteStream();
                    //定义缓冲区大小
                    byte[] bys = new byte[1024];
                    int progress = 0;
                    int len = -1;
                    while ((len = is.read(bys)) != -1) {
                        try {
                            Thread.sleep(1);
                            fos.write(bys, 0, len);
                            fos.flush();
                            progress += len;
                            //设置进度
                            dialog.setProgress(progress);
                        } catch (InterruptedException e) {

                        }
                    }
                    //下载完成,提示用户安装
                    installApk(apkFile);
                }
            } catch (IOException e) {
                Log.e("测试1", "错误"+e);
            } finally {
                //关闭io流
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        Log.e("测试2", "错误"+e);
                    }
                    is = null;
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        Log.e("测试3", "错误"+e);
                    }
                    fos = null;
                }
            }
            dialog.dismiss();
        }
    }

    /**
     * 下载完成,提示用户安装
     */
    private void installApk(File file) {
        //调用系统安装程序
        Intent intent =new Intent(Intent.ACTION_VIEW);

//判断是否是AndroidN以及更高的版本

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N) {

            Uri contentUri = FileProvider.getUriForFile(getContext(),"com.qr.biaoye.fileProvider",file);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setDataAndType(contentUri,"application/vnd.android.package-archive");

        }else{

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");

        }

        startActivity(intent);
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
//        Log.e("性别", "设置了背景色"+bgAlpha);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden&&isFlag){
            if (isConn(getContext())){
                layout_title.setVisibility(View.VISIBLE);
                wv.setVisibility(View.VISIBLE);
                layout_net.setVisibility(View.GONE);
                initNet();
            }else {
                layout_title.setVisibility(View.GONE);
                wv.setVisibility(View.GONE);
                layout_net.setVisibility(View.VISIBLE);
            }
        }
    }
}
