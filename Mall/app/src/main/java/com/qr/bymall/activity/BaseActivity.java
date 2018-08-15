package com.qr.bymall.activity;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.qr.bymall.R;
import com.umeng.analytics.MobclickAgent;
//import com.umeng.analytics.MobclickAgent;

/**
 * Created by alu on 2018/6/27.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addLayout();
        initView();
    }
    //加载布局的方法
    public abstract void addLayout();
    //findViewById初始化控件的方法
    public abstract void initView();

    //跳转方法
    public void gotoActivity(Class<?> activity,int num) {
        if(num==1){//进入子菜单
            Intent intent = new Intent(this,activity);
            startActivity(intent);
            overridePendingTransition(R.anim.leftin,R.anim.rightout);

        }else if (num==2){
            Intent intent = new Intent(this,activity);
            startActivity(intent);
            overridePendingTransition(R.anim.lefthuanyuan,R.anim.lefthuan);
            finish();
        } else{//退出子页面
            overridePendingTransition(R.anim.lefthuanyuan,R.anim.lefthuan);
            finish();
        }
    }

    //重写跳转方法1
    public void gotoActivity(Class<?> activity,String str1,String str2) {
        Intent intent = new Intent(this,activity);
        intent.putExtra(str1,str2);
        startActivity(intent);
        overridePendingTransition(R.anim.leftin,R.anim.rightout);
    }
    //重写跳转方法1
    public void gotoActivity(Class<?> activity,String str1,String str2,int i) {
        Intent intent = new Intent(this,activity);
        intent.putExtra(str1,str2);
        startActivity(intent);
        overridePendingTransition(R.anim.leftin,R.anim.rightout);
        finish();
    }
    //重写跳转方法1
    public void gotoActivity(Class<?> activity,String str1,String str2,String str3,String str4,String str5,String str6) {
        Intent intent = new Intent(this,activity);
        intent.putExtra(str1,str2);
        intent.putExtra(str3,str4);
        intent.putExtra(str5,str6);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.leftin,R.anim.rightout);
    }
    //重写跳转方法1
    public void gotoActivity(Class<?> activity,String str1,String str2,String str3,String str4) {
        Intent intent = new Intent(this,activity);
        intent.putExtra(str1,str2);
        intent.putExtra(str3,str4);
        startActivity(intent);
        overridePendingTransition(R.anim.leftin,R.anim.rightout);
    }
    // 子类中无需再调用
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this); // 基础指标统计，不能遗漏
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this); // 基础指标统计，不能遗漏
    }



}
