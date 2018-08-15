package com.qr.bymall.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qr.bymall.R;
import com.qr.bymall.adapter.ViewPagerAdapter;
import com.qr.bymall.util.SharedUtil;

import java.util.ArrayList;

/**
 * Created by alu on 2018/8/8.
 */

public class LeadActivity extends BaseActivity {
    private ViewPager viewPager;
    private Button btn_lead;
    private int [] imgs = {R.drawable.p1,R.drawable.p2,R.drawable.p3};//要显示的图片资源
    private ArrayList<ImageView> imageViews;//用于包含引导页要显示的图片
    private Bitmap[] bitmaps=new Bitmap[3];
    //    private ImageView[] dotViews;//用于包含底部小圆点的图片，只要设置每个imageview的图片资源为刚刚写的dot_selector，选择和没选中就会有不同的效果，实现导航的功能。
    private int page;
    private Animation animation;


    @Override
    public void addLayout() {
        setContentView(R.layout.activity_lead);
    }
    @Override
    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        btn_lead = (Button) findViewById(R.id.btn_lead);
        btn_lead.setVisibility(View.GONE);
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        initImages();
//        initDots();
        viewPager.setAdapter(new ViewPagerAdapter(imageViews));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                page = position;
                if (page == imgs.length - 1) {
                    btn_lead.setVisibility(View.VISIBLE);
                    btn_lead.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedUtil.saveTag(LeadActivity.this);
                            gotoActivity(LaunchActivity.class,1);
                        }
                    });
                }else {
                    btn_lead.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {
//                page = position;
////                for (int i = 0; i < dotViews.length; i++) {
////                    if (position == i) {
////                        dotViews[i].setSelected(true);
////                    } else {
////                        dotViews[i].setSelected(false);
////                    }
////                }
//                Log.e("lead",position+"");
//                if (position == imgs.length - 1) {
////                    btn_lead.setVisibility(View.VISIBLE);
//                    btn_lead.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            SharedUtil.saveTag(LeadActivity.this);
//                            gotoActivity(LaunchActivity.class,1);
//                        }
//                    });
//                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (page == imgs.length - 1 && state == 0) {
                    animation.start();
                }
            }
        });

        //最后一页滑动时进入Login界面
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            float startX;
//            float endX;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        startX=event.getX();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        endX=event.getX();
//                        DisplayMetrics metric = new DisplayMetrics();
//                        getWindowManager().getDefaultDisplay().getMetrics(metric);
//                        int width=metric.widthPixels;
//                        if (page == imgs.length - 1) {
//                            btn_lead.setVisibility(View.VISIBLE);
//                            btn_lead.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    SharedUtil.saveTag(LeadActivity.this);
//                                    gotoActivity(LaunchActivity.class,1);
//                                }
//                            });
//                        }else {
//                            btn_lead.setVisibility(View.GONE);
//                        }
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//                return false;
//            }
//        });

    }
    //最后一页滑动时进入Login界面
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        break;
//                    case MotionEvent.ACTION_UP:
//
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                }
//                return false;
//            }
//        });
//    }
    /**
     * 把引导页要显示的图片添加到集合中，以传递给适配器，用来显示图片。
     */

    private void initImages()
    {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);//设置每一张图片都填充窗口
        imageViews = new ArrayList<ImageView>();

        for(int i = 0; i < imgs.length; i++)
        {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);//设置布局
            //放缩图片
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),imgs[i]);
            //矩阵对象
            Matrix matrix = new Matrix();
            //缩放原图
            matrix.postScale(1f, 1f);
            bitmaps[i] = Bitmap.createBitmap(bitmaps[i], 0, 0, bitmaps[i].getWidth(), bitmaps[i].getHeight(), matrix, true);

            iv.setImageBitmap(bitmaps[i]);//为Imageview添加图片资源
            iv.setScaleType(ImageView.ScaleType.FIT_XY);//设置图片拉伸效果
            imageViews.add(iv);

        }
    }

    /**
     * 根据引导页的数量，动态生成相应数量的导航小圆点，并添加到LinearLayout中显示。
     */
//    private void initDots()
//    {
//        LinearLayout layout = (LinearLayout)findViewById(R.id.dot_layout);
//        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        mParams.setMargins(10, 0, 10, 0);//设置小圆点左右之间的间隔
//
//        dotViews = new ImageView[imgs.length];
//
//        for(int i = 0; i < imageViews.size(); i++)
//        {
//            ImageView imageView = new ImageView(this);
//            imageView.setLayoutParams(mParams);
//            imageView.setImageResource(R.drawable.dot_selector);
//            if(i== 0)
//            {
//                imageView.setSelected(true);//默认启动时，选中第一个小圆点
//            }
//            else {
//                imageView.setSelected(false);
//            }
//            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
//            layout.addView(imageView);//添加到布局里面显示
//        }
//
//    }
}

