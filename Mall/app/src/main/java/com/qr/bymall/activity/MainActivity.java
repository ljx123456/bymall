package com.qr.bymall.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.fragment.ClassifyFragment;
import com.qr.bymall.fragment.HomePageFragment;
import com.qr.bymall.fragment.NoticeFragment;
import com.qr.bymall.fragment.PersonalFragment;
import com.qr.bymall.fragment.ShoppingCartFragment;
import com.qr.bymall.util.SharedUtil;

public class MainActivity extends BaseActivity{

    private FragmentManager fragmentManager;//fragment的管理者
    private FragmentTransaction transaction;//fragment管理事物
    private TextView[] textView=new TextView[5];
    private RelativeLayout[] layout=new RelativeLayout[5];
    private int now_count;
    private Fragment[] fragments = new Fragment[5];
    private Intent intent;

    @Override
    public void addLayout() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);//全屏
        setContentView(R.layout.activity_main);
//        View decorView = getWindow().getDecorView();
//        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        decorView.setSystemUiVisibility(option);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
    }

    @Override
    public void initView() {
        textView[0]= (TextView) findViewById(R.id.tv_homepage);
        textView[1]= (TextView) findViewById(R.id.tv_classify);
        textView[2]= (TextView) findViewById(R.id.tv_notice);
        textView[3]= (TextView) findViewById(R.id.tv_shopping_cart);
        textView[4]= (TextView) findViewById(R.id.tv_personal);
        layout[0]= (RelativeLayout) findViewById(R.id.layout_homepage);
        layout[1]= (RelativeLayout) findViewById(R.id.layout_classify);
        layout[2]= (RelativeLayout) findViewById(R.id.layout_notice);
        layout[3]= (RelativeLayout) findViewById(R.id.layout_shopping_cart);
        layout[4]= (RelativeLayout) findViewById(R.id.layout_personal);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        intent=getIntent();
        if (intent!=null&&intent.getStringExtra("name")!=null&&intent.getStringExtra("name").equals("0")){
            if (SharedUtil.getToken(getApplicationContext()).equals("")){
                gotoActivity(LoginActivity.class,1);
            }else {
                fragments[3] = new ShoppingCartFragment();
                transaction.add(R.id.main_layout, fragments[3]);
                Drawable img = getResources().getDrawable(R.drawable.shoppingcart_ic);
                // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                textView[3].setCompoundDrawables(null, img, null, null); //设置上图标
                textView[3].setTextColor(0xffc7962c);
                transaction.commit();
                now_count = 3;
            }
        }else if(intent!=null&&intent.getStringExtra("name")!=null&&intent.getStringExtra("name").equals("1")){
            if (SharedUtil.getToken(getApplicationContext()).equals("")){
                gotoActivity(LoginActivity.class,1);
            }else {
                fragments[4] = new PersonalFragment();
                transaction.add(R.id.main_layout, fragments[4]);
                Drawable img = getResources().getDrawable(R.drawable.personal_ic);
                // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                textView[4].setCompoundDrawables(null, img, null, null); //设置上图标
                textView[4].setTextColor(0xffc7962c);
                transaction.commit();
                now_count = 4;
            }
        }
        else {
            fragments[0] = new HomePageFragment();
            transaction.add(R.id.main_layout,fragments[0]);
            Drawable img = getResources().getDrawable(R.drawable.homepage_ic);
            // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
            img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
            textView[0].setCompoundDrawables(null, img, null, null); //设置上图标
            textView[0].setTextColor(0xffc7962c);
            transaction.commit();
            now_count=0;
        }
        layout[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice(0);
                initialize(0);
                choiceFragment(0);
            }
        });
        layout[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice(1);
                initialize(1);
                choiceFragment(1);
            }
        });
        layout[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedUtil.getToken(getApplicationContext()).equals("")){
                    Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    choice(2);
                    initialize(2);
                    choiceFragment(2);
                }
            }
        });
        layout[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedUtil.getToken(getApplicationContext()).equals("")){
                    Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    choice(3);
                    initialize(3);
                    choiceFragment(3);
                }
            }
        });
        layout[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SharedUtil.getToken(getApplicationContext()).equals("")){
                    Toast.makeText(MainActivity.this, "请登录", Toast.LENGTH_SHORT).show();
                    gotoActivity(LoginActivity.class,1);
                }else {
                    choice(4);
                    initialize(4);
                    choiceFragment(4);
                }
            }
        });

    }


    //恢复默认
    public void  initialize(int textView_index){
        for(int i=0;i<textView.length;i++){
            if (i!=textView_index){
                Drawable img=null;
                switch (i){
                    case 0:
                        img = getResources().getDrawable(R.drawable.homepage_default_ic);
                        break;
                    case 1:
                        img = getResources().getDrawable(R.drawable.classify_default_ic);
                        break;
                    case 2:
                        img = getResources().getDrawable(R.drawable.notice_default_ic);
                        break;
                    case 3:
                        img = getResources().getDrawable(R.drawable.shoppingcart_default_ic);
                        break;
                    case 4:
                        img = getResources().getDrawable(R.drawable.personal_default_ic);
                        break;
                }
                // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
                img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
                textView[i].setCompoundDrawables(null, img, null, null); //设置上图标
                textView[i].setTextColor(0xff454545);
            }
        }
    }
    //切换效果
    public void choice(int textView_index){
        Drawable img=null;
        switch (textView_index){
            case 0:
                img = getResources().getDrawable(R.drawable.homepage_ic);
                break;
            case 1:
                img = getResources().getDrawable(R.drawable.classify_ic);
                break;
            case 2:
                img = getResources().getDrawable(R.drawable.notice_ic);
                break;
            case 3:
                img = getResources().getDrawable(R.drawable.shoppingcart_ic);
                break;
            case 4:
                img = getResources().getDrawable(R.drawable.personal_ic);
                break;
        }
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        textView[textView_index].setCompoundDrawables(null, img, null, null); //设置上图标
        textView[textView_index].setTextColor(0xffc7962c);

    }

    //判断方法
    public void choiceFragment(int textView_index){
        if(now_count!=textView_index){
            transaction = fragmentManager.beginTransaction();
            if(fragments[textView_index]==null){
                fragments[textView_index] =newFragment(textView_index);
                transaction.add(R.id.main_layout,fragments[textView_index]);
            }else{
                transaction.show(fragments[textView_index]);
            }
            transaction.hide(fragments[now_count]);
            transaction.commit();
            now_count=textView_index;
        }
    }
    public Fragment newFragment(int textView_index){
        switch (textView_index){
            case  0:
                return  new HomePageFragment();
            case  1:
                return  new ClassifyFragment();
            case  2:
                return  new NoticeFragment();
            case 3:
                return new ShoppingCartFragment();
            case 4:
                return new PersonalFragment();
        }
        return  null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        return;
    }
}
