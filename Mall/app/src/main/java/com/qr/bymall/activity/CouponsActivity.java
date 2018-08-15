package com.qr.bymall.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;

import com.qr.bymall.R;
import com.qr.bymall.adapter.CouponsFragmentAdapter;
import com.qr.bymall.fragment.CouponsNeedFragment;
import com.qr.bymall.fragment.CouponsUseFragment;

import java.util.ArrayList;

/**
 * Created by alu on 2018/7/26.
 */

public class CouponsActivity extends BaseActivity {

    private RelativeLayout back;
    private ViewPager vp;
    private TabLayout tb;
    private CouponsFragmentAdapter adapter;
    private String[] names={"未领取","已领取"};

    @Override
    public void addLayout() {
        setContentView(R.layout.activity_coupons);
    }

    @Override
    public void initView() {
        back= (RelativeLayout) findViewById(R.id.coupons_back);
        vp= (ViewPager) findViewById(R.id.vp_coupons);
        tb= (TabLayout) findViewById(R.id.tab_coupons);
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
        CouponsNeedFragment couponsNeedFragment=new CouponsNeedFragment();
        CouponsUseFragment couponsUseFragment=new CouponsUseFragment();
        list.add(couponsNeedFragment);
        list.add(couponsUseFragment);
        return list;
    }
}
