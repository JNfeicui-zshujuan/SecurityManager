package com.example.zhengshujuan.securitymanager.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zhengshujuan.securitymanager.R;
import com.example.zhengshujuan.securitymanager.Utils.Constants;
import com.example.zhengshujuan.securitymanager.Utils.SpUtils;

/**
 * Created by zhengshujuan on 2016/5/18.
 */
public class sjfd_setup2 extends BaseSetupButton {
    private Button BtnBoundMis;
    private ImageView mIvLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.sjfd_setup2);
        BtnBoundMis = (Button) findViewById(R.id.btn_boundsim);

        mIvLock = (ImageView) findViewById(R.id.iv_lock);

        if (TextUtils.isEmpty(SpUtils.getString(getApplicationContext(), Constants.SIM))) {
            //SIM卡没有被绑定
            mIvLock.setImageResource(R.mipmap.ic_launcher);
            Log.d("tag","从未绑定到绑定");
        } else {
            Toast.makeText(sjfd_setup2.this, "SIM卡信息已绑定", Toast.LENGTH_SHORT).show();
            mIvLock.setImageResource(R.mipmap.ic_lock);
            Log.d("tag","从绑定到解绑");
        }
    }

    @Override
    public void initEvent() {

        BtnBoundMis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //未绑定
                if (SpUtils.getString(getApplicationContext(), Constants.SIM) == null) {
                    TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    String simNumber = telManager.getSimSerialNumber();
                    //保存SIM卡的信息
                    SpUtils.putString(getApplicationContext(), Constants.SIM, simNumber);
                    Toast.makeText(sjfd_setup2.this, "SIM卡信息已保存", Toast.LENGTH_SHORT).show();
                    mIvLock.setImageResource(R.mipmap.ic_lock);
                    Log.d("tag","绑定");
                }
                //已绑定
                else {
                    SpUtils.putString(getApplicationContext(), Constants.SIM, null);
                    mIvLock.setImageResource(R.mipmap.ic_launcher);
                    Log.d("tag","未绑定");
                }
            }
        });
        super.initEvent();
    }


    @Override
    protected void backActivity() {
        startBack(sjfd_setup1.class);
    }

    @Override
    public void nextActivity() {
        startNext(sjfd_setup3.class);

    }
}
