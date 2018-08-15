package com.qr.bymall.activity;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qr.bymall.R;
import com.qr.bymall.util.SharedUtil;
import com.qr.bymall.view.OnMultiClickListener;

/**
 * Created by alu on 2018/7/26.
 */

public class SetActivity extends BaseActivity {

    private RelativeLayout back,password,about;
    private TextView tv;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_set);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.set_back);
        password= (RelativeLayout) findViewById(R.id.layout_set_password);
        about= (RelativeLayout) findViewById(R.id.layout_set_about);
        tv= (TextView) findViewById(R.id.tv_out);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(ForgetActivity.class,2);
            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoActivity(AboutActivity.class,1);
            }
        });
        tv.setOnClickListener(new OnMultiClickListener() {
            @Override
            public void onMultiClick(View v) {
                SharedUtil.saveToken(getApplicationContext(),"");
                Toast.makeText(SetActivity.this, "已退出", Toast.LENGTH_SHORT).show();
                gotoActivity(MainActivity.class,2);
            }
        });
    }
}
