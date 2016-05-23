package com.example.zhengshujuan.securitymanager.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zhengshujuan.securitymanager.R;

import java.util.List;

public class Main2Activity extends AppCompatActivity {
    Context mContext;
    PackageManager pm=mContext.getPackageManager();
    List<PackageInfo> pa=pm.getInstalledPackages(0);

    public void my(){
        Log.d("tag","application"+pa.toString());
        for (int i=0;i<pa.size();i++){
            PackageInfo pak=pa.get(i);
            if ((pak.applicationInfo.flags&pak.applicationInfo.FLAG_SYSTEM)<=0);
            Log.d("tag","PackageInfo:"+pak);
        }
    }
}
