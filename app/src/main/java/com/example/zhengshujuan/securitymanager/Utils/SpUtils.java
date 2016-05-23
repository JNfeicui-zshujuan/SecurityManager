package com.example.zhengshujuan.securitymanager.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.zhengshujuan.securitymanager.ui.MainActivity;

import java.util.jar.Attributes;

/**
 * Created by zhengshujuan on 2016/5/17.
 */
public class SpUtils {
    public static final String NAME="saftyapp";
    private static SharedPreferences mPreference;

    public static void putString(Context context, String key, String value) {

        SharedPreferences sp = getPreferences(context);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key,value);
        edit.apply();
    }

    private static SharedPreferences getPreferences(Context context) {
        if (mPreference==null){
            mPreference=context.getSharedPreferences(NAME,context.MODE_PRIVATE);
        }
        return mPreference;
    }

    public static String getString(Context context, String key) {
        return getString(context,key,null);
    }

    private static String getString(Context context, String key, String  defValue) {
        SharedPreferences sp=getPreferences(context);
        return sp.getString(key,defValue);
    }
}
