package com.qr.bymall.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.bean.Version;
import com.qr.bymall.util.BaseUtil;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.UpdatePopupWindow;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
 * Created by alu on 2018/7/26.
 */

public class AboutActivity extends BaseActivity {

    private RelativeLayout back,layout_version;
    private TextView tv_version_num,tv_version,tv_phone,tv_wechat;
    private String url;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };
    private UpdatePopupWindow window;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_about);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.about_back);
        layout_version= (RelativeLayout) findViewById(R.id.layout_about_version);
        tv_version_num= (TextView) findViewById(R.id.tv_about_version_num);
        tv_version= (TextView) findViewById(R.id.tv_version);
        tv_phone= (TextView) findViewById(R.id.tv_about_phone);
        tv_wechat= (TextView) findViewById(R.id.tv_about_wechat);
        if (SharedUtil.getToken(getApplicationContext()).equals("")){
            Toast.makeText(getApplicationContext(), "请登录", Toast.LENGTH_SHORT).show();
            gotoActivity(LoginActivity.class,2);
        }else {
            getVersion();
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        ProgressDialog dialog = new ProgressDialog(this);
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

            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),"com.qr.biaoye.fileProvider",file);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            intent.setDataAndType(contentUri,"application/vnd.android.package-archive");

        }else{

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");

        }

        startActivity(intent);
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
                        if (response.body().getData().getVersion().equals("1.0.0")){
                            tv_version.setText("最新版本");
                            tv_version.setTextColor(0xff939393);
                        }else {
                            url=response.body().getData().getLink();
                            tv_version.setText("更新版本");
                            tv_version.setTextColor(0xffca4553);
                            tv_version.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Log.e("测试","点击了下载");
                                    if (window == null) {
                                        window = new UpdatePopupWindow(AboutActivity.this, new UpdatePopupWindow.Listener() {
                                            @Override
                                            public void toUpdate() {
                                                verifyStoragePermissions(AboutActivity.this);
                                                downloadApk();
                                            }
                                        },response.body().getData() );

                                    }
                                    if (window.isShowing()) {
                                        window.dismiss();
                                        setBackgroundAlpha(AboutActivity.this, 1.0f);
                                        return;
                                    }
                                    window.show();

                                }
                            });
                        }
                    }else if (response.body().getCode()==1000){
                        Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else if (response.body().getCode()==1001){
                        new AlertDialog.Builder(AboutActivity.this)
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
                    Toast.makeText(AboutActivity.this, "登录超时，请重新登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,0);
                }else {

                    Toast.makeText(AboutActivity.this, response.message(), Toast.LENGTH_SHORT).show();
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
}
