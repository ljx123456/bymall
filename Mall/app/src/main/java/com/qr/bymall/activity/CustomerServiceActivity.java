package com.qr.bymall.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.qr.bymall.R;

/**
 * Created by alu on 2018/7/26.
 */

public class CustomerServiceActivity extends BaseActivity {

    private RelativeLayout back;

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_customer_service);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.customer_service_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
