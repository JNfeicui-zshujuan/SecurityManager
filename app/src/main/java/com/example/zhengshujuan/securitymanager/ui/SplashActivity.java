package com.example.zhengshujuan.securitymanager.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.zhengshujuan.securitymanager.R;

import java.util.ArrayList;

import static com.example.zhengshujuan.securitymanager.R.id.icon1;

public class SplashActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private Button mbtn;
    private ArrayList<View> mList;
    int[] pics = {R.mipmap.adware_style_applist, R.mipmap.adware_style_banner,
            R.mipmap.adware_style_creditswall};
   private ImageView mImageView1,mImageView2,mImageView3;

    private static String TAG = "as";
    private static final String SPLSH="splah";
    private static final String IS_FRIST_RUN="frist";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断是否是第一次运行

        SharedPreferences preferences=getSharedPreferences(SPLSH,MODE_PRIVATE);
        boolean isFristRun=preferences.getBoolean(IS_FRIST_RUN,true);
        if (!isFristRun){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            setContentView(R.layout.activity_splash);
            initView();
        }


    }
    //保存第一次运行的sp
    private void savePreferences(){
        SharedPreferences preferences=getSharedPreferences(SPLSH, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putBoolean(IS_FRIST_RUN,false);
        editor.apply();
    }
    @Override
    public void onClick(View v) {
       savePreferences();
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void initView() {
        mbtn = (Button) findViewById(R.id.btn_turn);
        mbtn.setVisibility(View.INVISIBLE);
        mbtn.setOnClickListener(this);

        mList = new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        for (int i = 0; i < pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(pics[i]);
            mList.add(iv);}
            mImageView1= (ImageView) findViewById(R.id.icon1);
            mImageView2= (ImageView) findViewById(R.id.icon2);
            mImageView3= (ImageView) findViewById(R.id.icon3);

        mImageView1.setImageResource(R.drawable.adware_style_selected);
        mImageView2.setImageResource(R.drawable.adware_style_default);
        mImageView3.setImageResource(R.drawable.adware_style_default);
        mViewPager.setAdapter(new MyPageAdapter(mList));
        mViewPager.addOnPageChangeListener(this);
        //  mViewPager.setPageTransformer(true, new ZoomOutPageTransFormer());
        mViewPager.setPageTransformer(true, new DepthPage());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//正在的滚动的时候调用的方法,会反复调用
        Log.d(TAG, "onpageScrolled");
    }

    @Override
    public void onPageSelected(int position) {
//当viewpager在滚动的时候调用的第一个方法
//        if (position == 2) {
//            mbtn.setVisibility(View.VISIBLE);
//        } else {
//            mbtn.setVisibility(View.INVISIBLE);
//        }
        if (position == 0) {
            mbtn.setVisibility(View.INVISIBLE);
            mImageView1.setImageResource(R.drawable.adware_style_selected);
            mImageView2.setImageResource(R.drawable.adware_style_default);
            mImageView3.setImageResource(R.drawable.adware_style_default);
        }else if (position==1){
            mbtn.setVisibility(View.INVISIBLE);
            mImageView2.setImageResource(R.drawable.adware_style_selected);
            mImageView1.setImageResource(R.drawable.adware_style_default);
            mImageView3.setImageResource(R.drawable.adware_style_default);
        }else if (position==2){
            mbtn.setVisibility(View.VISIBLE);
            mImageView3.setImageResource(R.drawable.adware_style_selected);
            mImageView1.setImageResource(R.drawable.adware_style_default);
            mImageView2.setImageResource(R.drawable.adware_style_default);
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
//
        Log.d(TAG, "onPageScrollStateChanged");
    }



    private class MyPageAdapter extends PagerAdapter {
        private ArrayList<View> mList;

        public MyPageAdapter(ArrayList<View> list) {
            mList = list;
        }

        //初始化position 展现到界面,填充布局
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position), 0);
            return mList.get(position);
        }

        //移除PageAdapter 的内容
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        //判断是否是当前的界面
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private class DepthPage implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.76f;

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();
            if (position < -1) {
                page.setAlpha(0);
            } else if (position <= 0) {
                page.setAlpha(1);
                page.setTranslationX(0);
                page.setScaleX(1);
                page.setScaleY(1);
            } else if (position <= 1) {
                page.setAlpha(1 - position);
                page.setTranslationX(pageWidth * -position);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                        * (1 - Math.abs(position));
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
            } else {
                page.setAlpha(0);
            }
        }
    }
}
