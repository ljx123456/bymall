package com.qr.bymall.view;

import android.app.Activity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.adapter.CouponsAdapter;
import com.qr.bymall.bean.NewOrder;
import com.qr.bymall.util.BaseUtil;

import java.util.List;

/**
 * Created by alu on 2018/7/16.
 */

public class CouponsChoicePopupWindow extends PopupWindow {

    private Button cancel;
    private Activity mActivity;
    private TextView tv_dec,tv_price,tv_money;

    private ListView list;
    private String price,money;

    private CouponsAdapter adapter;
    private List<NewOrder.DataBean.CouponsBean> coupons;


    public CouponsChoicePopupWindow(final Activity mActivity, final TextView tv_dec , final TextView tv_money ,  final CouponsAdapter adapter, final List<NewOrder.DataBean.CouponsBean> coupons) {
        super(mActivity.getLayoutInflater().inflate(R.layout.coupons_choice_popupwindow,null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mActivity = mActivity;
        this.tv_dec=tv_dec;
        this.tv_money=tv_money;

        this.adapter=adapter;
        this.coupons=coupons;

        money=tv_money.getText().toString();

        list = (ListView) getContentView().findViewById(R.id.lv_popup_coupons);
        cancel = (Button) getContentView().findViewById(R.id.btn_popup_coupons);
        list.setAdapter(adapter);
        //取消
        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });
        //设置按钮监听
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                tv_dec.setText(coupons.get(i).getName());
                BaseUtil.coupon=coupons.get(i).getCoupon_use_id();
                Float m=Float.parseFloat(money)-Float.parseFloat(coupons.get(i).getMoney());
                tv_money.setText(m+"");
                dismiss();
            }
        });

        setFocusable(true);// 获得焦点
        this.setTouchable(true);
        this.setOutsideTouchable(true);
//        setBackgroundDrawable(new BitmapDrawable());
        setBackgroundAlpha(mActivity,1.0f);//设置屏幕透明度
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        getContentView().setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int height = getContentView().findViewById(R.id.pop_coupon_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                        setBackgroundAlpha(mActivity,1.0f);
                    }
                }
                return true;
            }
        });


    }
    // 展示的方法
    public void show(){
        showAtLocation(mActivity.getWindow().getDecorView(),
                Gravity.BOTTOM,0,0);
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
//        Log.e("学校", "设置了背景色"+bgAlpha);
    }
}