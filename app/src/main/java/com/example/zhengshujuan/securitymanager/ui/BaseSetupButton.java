package com.example.zhengshujuan.securitymanager.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by zhengshujuan on 2016/5/18.
 */
public abstract class BaseSetupButton extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        //初始化数据
        initData();
        //初始化事件
        initEvent();
    }
    //抽取按钮的点击事件
    public void next(View view){

        nextActivity();
       // nextAnimation();

    }

    public void initEvent() {

    }

    public void initData() {

    }


    protected abstract void initView();

    //抽取按钮的点击事件
    public void back(View view){
        backActivity();
    }
    protected void startNext(Class activity){
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        finish();
    }
    protected void startBack(Class activity){
        Intent intent=new Intent(this,activity);
        startActivity(intent);
        finish();
    }

    protected abstract void backActivity();

    public abstract void nextActivity();

//转换动画用系统的动画
public void nextAnimation(){
    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
}
}
