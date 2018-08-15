package com.qr.bymall.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.qr.bymall.R;
import com.qr.bymall.adapter.CouponsFragmentAdapter;
import com.qr.bymall.fragment.FinishedRefundFragment;
import com.qr.bymall.fragment.UnfinishedRefundFragment;

import java.util.ArrayList;

/**
 * Created by alu on 2018/7/26.
 */

public class RefundActivity extends BaseActivity {

    private RelativeLayout back;
    private ViewPager vp;
    private TabLayout tb;
    private CouponsFragmentAdapter adapter;
    private String[] names={"未完成","已完成"};

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_refund);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.refund_back);
        vp= (ViewPager) findViewById(R.id.vp_refund);
        tb= (TabLayout) findViewById(R.id.tab_refund);
        adapter=new CouponsFragmentAdapter(getSupportFragmentManager(),getData(),names);
        vp.setAdapter(adapter);
        tb.setupWithViewPager(vp);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public ArrayList<Fragment> getData(){
        ArrayList<Fragment> list = new ArrayList<>();
        UnfinishedRefundFragment unfinishedRefundFragment=new UnfinishedRefundFragment();
        FinishedRefundFragment finishedRefundFragment=new FinishedRefundFragment();
        list.add(unfinishedRefundFragment);
        list.add(finishedRefundFragment);
        return list;
    }
}

