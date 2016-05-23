package com.example.zhengshujuan.securitymanager.engin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.example.zhengshujuan.securitymanager.Bean.SMAppBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengshujuan on 2016/5/11.
 */
public class SoftManager {
    public static List<SMAppBean> getAppInfo(Context context){

        PackageManager pm=context.getPackageManager();
        List<PackageInfo> packages=pm.getInstalledPackages(0);

        List<SMAppBean> mData=new ArrayList<>();

        for (PackageInfo info:packages){

            Drawable icon=info.applicationInfo.loadIcon(pm);
            String name=info.applicationInfo.loadLabel(pm).toString();
            String packageName=info.applicationInfo.packageName;

            SMAppBean bean=new SMAppBean();
            bean.name=name;
            bean.icon=icon;
            bean.packageName=packageName;
            mData.add(bean);
        }
        return mData;
    }
}
