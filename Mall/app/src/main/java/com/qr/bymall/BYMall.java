package com.qr.bymall;

import android.app.Application;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by alu on 2018/7/5.
 */

public class BYMall extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5b3c6927a40fa3707d000061"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        PlatformConfig.setWeixin("wx6aea495997e32cc1", "f9e414c3f901a23cc28132a19aa42ac8");
//        PlatformConfig.setQQZone();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
