package com.example.zhengshujuan.securitymanager.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;

import com.example.zhengshujuan.securitymanager.R;

/**
 * Created by zhengshujuan on 2016/5/18.
 */
public class sjfd_setup1 extends BaseSetupButton {
    private Button BtnBoundMis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjfd_setup1);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void backActivity() {


    }

    @Override
    public void nextActivity() {

        startNext(sjfd_setup2.class);
    }
}
