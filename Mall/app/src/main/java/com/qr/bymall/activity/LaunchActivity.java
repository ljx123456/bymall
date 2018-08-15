package com.qr.bymall.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.qr.bymall.R;
import com.qr.bymall.util.SharedUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by alu on 2018/8/8.
 */

public class LaunchActivity extends AppCompatActivity  {

    private final String mPageName = "LaunchActivity";
    private PackageInfo pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!SharedUtil.getTag(this)){//默认是false
            Intent intent=new Intent(LaunchActivity.this,LeadActivity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_launch);
            //设置 U-Dplus场景
            MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_DUM_NORMAL);
            PackageManager pm = getPackageManager();
            try {
                pi= pm.getPackageInfo(getPackageName(),
                        PackageManager.GET_CONFIGURATIONS);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            Map<String, String> map_value = new HashMap<String, String>();
            map_value.put("app_version", pi.versionName );
            MobclickAgent.onEvent(this, "app_version" , map_value);
            init();
        }
    }

    private void init(){
        Integer time = 2000;    //设置等待时间，单位为毫秒
        Handler handler = new Handler();
        //当计时结束时，跳转至主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        }, time);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this); // BaseActivity中已经统一调用，此处无需再调用
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this); // BaseActivity中已经统一调用，此处无需再调用
    }

    //    @Override
//    public void addLayout() {
//        Log.e("测试",SharedUtil.getTag(this)+"");

//    }
//
//    @Override
//    public void initView() {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //耗时任务，比如加载网络数据
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        //跳转至 MainActivity
//                        if (SharedUtil.getToken(getApplicationContext()).equals("")){
//                            gotoActivity(LoginActivity.class,1);
//                        }else {
//                            gotoActivity(MainActivity.class,1);
//                        }
//                    }
//                });
//            }
//        }).start();
//
//    }
}

