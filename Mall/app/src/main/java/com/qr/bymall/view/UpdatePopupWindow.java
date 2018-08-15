package com.qr.bymall.view;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qr.bymall.R;
import com.qr.bymall.bean.Version;

import static android.R.string.cancel;

/**
 * Created by alu on 2018/8/9.
 */

public class UpdatePopupWindow extends PopupWindow {

    private Listener mListener;
    private Activity mActivity;
    private TextView tv_version,tv_size,tv_content_tv1,tv_content_tv2,tv_content_tv3,
            tv_cancel,tv_update;
    private Version.DataBean dataBean;


    // 跳转的接口
    public interface Listener{
        void toUpdate();// 更新
    }
    public UpdatePopupWindow(final Activity mActivity, final Listener mListener,Version.DataBean data) {
        super(mActivity.getLayoutInflater().inflate(R.layout.update_popupwindow,null),
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        this.mActivity = mActivity;
        this.mListener = mListener;
        this.dataBean=data;



        tv_version = (TextView) getContentView().findViewById(R.id.tv_update_pop_version);
        tv_size = (TextView) getContentView().findViewById(R.id.tv_update_pop_size);
        tv_content_tv1= (TextView) getContentView().findViewById(R.id.tv_update_pop_content_tv1);
        tv_content_tv2= (TextView) getContentView().findViewById(R.id.tv_update_pop_content_tv2);
        tv_content_tv3= (TextView) getContentView().findViewById(R.id.tv_update_pop_content_tv3);
        tv_cancel= (TextView) getContentView().findViewById(R.id.tv_update_pop_cancel);
        tv_update= (TextView) getContentView().findViewById(R.id.tv_update_pop_update);


        tv_version.setText("最新版本："+dataBean.getVersion());
        tv_size.setText("新版本大小："+dataBean.getSize());
        if (dataBean.getContent()!=null&&dataBean.getContent().size()==1){
            tv_content_tv1.setVisibility(View.VISIBLE);
            tv_content_tv2.setVisibility(View.GONE);
            tv_content_tv3.setVisibility(View.GONE);
            Log.e("测试size",dataBean.getContent().size()+"");
            Log.e("测试content",dataBean.getContent().get(0));
            tv_content_tv1.setText("1、"+dataBean.getContent().get(0));
        }else if (dataBean.getContent()!=null&&dataBean.getContent().size()==2){
            tv_content_tv1.setVisibility(View.VISIBLE);
            tv_content_tv2.setVisibility(View.VISIBLE);
            tv_content_tv3.setVisibility(View.GONE);
            Log.e("测试size",dataBean.getContent().size()+"");
            Log.e("测试content1",dataBean.getContent().get(0));
            Log.e("测试content2",dataBean.getContent().get(1));
            tv_content_tv1.setText("1、"+dataBean.getContent().get(0)+"，");
            tv_content_tv2.setText("2、"+dataBean.getContent().get(1));
        }else if (dataBean.getContent()!=null&&dataBean.getContent().size()==3){
            tv_content_tv1.setVisibility(View.VISIBLE);
            tv_content_tv2.setVisibility(View.VISIBLE);
            tv_content_tv3.setVisibility(View.VISIBLE);
            Log.e("测试size",dataBean.getContent().size()+"");
            Log.e("测试content1",dataBean.getContent().get(0));
            Log.e("测试content2",dataBean.getContent().get(1));
            Log.e("测试content3",dataBean.getContent().get(2));
            tv_content_tv1.setText("1、"+dataBean.getContent().get(0)+"，");
            tv_content_tv2.setText("2、"+dataBean.getContent().get(1)+"，");
            tv_content_tv3.setText("3、"+dataBean.getContent().get(2));
        }
        //取消
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });
        //设置按钮监听
        tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 更新
                mListener.toUpdate();
                dismiss();
                setBackgroundAlpha(mActivity,1.0f);
            }
        });

        setFocusable(true);// 获得焦点
        this.setTouchable(true);
        this.setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
//        setBackgroundAlpha(mActivity,0.3f);//设置屏幕透明度
//        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.AnimBottom);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        this.setBackgroundDrawable(dw);
        getContentView().setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = getContentView().findViewById(R.id.layout_update_pop_top).getTop();
                int y=(int) event.getY();
                Log.e("点击","y:"+y+"height:"+height);
                if(event.getAction()==MotionEvent.ACTION_UP){
                    Log.e("点击","y:"+y+"height:"+height);
                    if(y<height){
                        Log.e("点击","y:"+y+"height:"+height);
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
                Gravity.CENTER,0,0);
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
