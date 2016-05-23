package com.example.zhengshujuan.securitymanager.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhengshujuan.securitymanager.Bean.HomeBean;
import com.example.zhengshujuan.securitymanager.R;
import com.example.zhengshujuan.securitymanager.Utils.Constants;
import com.example.zhengshujuan.securitymanager.Utils.MD5;
import com.example.zhengshujuan.securitymanager.Utils.SpUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final String TAG = "tag";
    public static final int WJGL = 0;
    public static final int SJFD = 1;
    public static final int YJJS = 2;
    public static final int LJQL = 3;
    public static final int RJGL = 4;
    public static final int TXWS = 5;
    GridView mGridView;

    private ArrayList<HomeBean> mHomeList = new ArrayList<>();
    ;
    private String[] des = {"文件管理", "手机防盗", "一键加速",
            "垃圾清理", "软件管理", "通讯卫士"};
    private int[] pics = {R.drawable.icon_filemgr, R.drawable.icon_phonemgr,
            R.drawable.icon_rocket, R.drawable.icon_sdclean,
            R.drawable.icon_softmgr, R.drawable.icon_telmgr};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gv_home);
        innitData();
        mGridView.setAdapter(new HomeAdapter());
        mGridView.setOnItemClickListener(this);

    }

    //
    private void innitData() {
        for (int i = 0; i < des.length; i++) {
            HomeBean mBean = new HomeBean();
            mBean.decs = des[i];
            mBean.pic = pics[i];
            mHomeList.add(mBean);
            Log.d(TAG, "初始化数据" + mHomeList);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case WJGL:

                Toast.makeText(MainActivity.this, "你点击了文件管理", Toast.LENGTH_SHORT).show();
                break;
            case SJFD:
                clickSJFD();
                break;
            case YJJS:
                break;
            case LJQL:
                break;
            case RJGL:
                Intent intent = new Intent(this, SoftManagerActivity.class);
                startActivity(intent);
                break;
            case TXWS:
                break;
        }


    }

    private void clickSJFD() {
        String password = SpUtils.getString(this, Constants.KEY);
        Log.d("tag", "密码:" + password);
        // TextUtils.isEmpty
        if (TextUtils.isEmpty(password)) {
            showSetupDialog();
        } else {
            showEnterDialog();
        }
    }

    private void showEnterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.sjfd_dialog, null);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_enter_pwd);
        Button btnLogin = (Button) view.findViewById(R.id.btn_login);
        Button btnDismiss = (Button) view.findViewById(R.id.btn_dismiss);

        builder.setView(view);
        final AlertDialog dialog = builder.show();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //输入的密码,需要验证
                String password = etPwd.getText()
                        .toString()
                        .trim();

                // 非空校验
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "输入的密码不能为空", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

               //已保存的密码
                String savedPwd = SpUtils.getString(getApplicationContext(), Constants.KEY);
                MD5 md = new MD5();
                String string = md.encode(password);
                Log.d("tag", "比对加密后密码:" + string);


                // 正确校验
                if (!savedPwd.equals(string)) {
                    Toast.makeText(getApplicationContext(), "输入密码有误", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT)
                        .show();

                Intent intent = new Intent(MainActivity.this, sjfd_setup1.class);
                startActivity(intent);
            }
        });

        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    private void showSetupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(getApplicationContext(), R.layout.sjfd_setup, null);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd1);
        final EditText etConfirmPwd = (EditText) view.findViewById(R.id.et_pwd2);

        Button btnSubmit = (Button) view.findViewById(R.id.btn_submit);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);


        builder.setView(view);
        final AlertDialog dialog = builder.show();


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击以后要校验文本框的内容

                String pwd = etPwd.getText().toString().trim();

                String confirmPwd = etConfirmPwd.getText()
                        .toString()
                        .trim();

                // 非空判断TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd
                if (pwd.isEmpty() || confirmPwd.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "密码不能为空", Toast.LENGTH_SHORT)
                            .show();

                    return;
                }

                // 相等判断
                if (!pwd.equals(confirmPwd)) {
                    Toast.makeText(getApplicationContext(), "密码不相等", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                // 保存edittext里面的内容
                MD5 md = new MD5();
                String md5 = md.encode(pwd);
                Log.d("tag", "一次简单加密后:" + md5);
                SpUtils.putString(getApplicationContext(), Constants.KEY, md5);

                Toast.makeText(MainActivity.this, "密码保存成功", Toast.LENGTH_SHORT)
                        .show();

                dialog.dismiss();

                Intent intent = new Intent(MainActivity.this, sjfd_setup1.class);
                startActivity(intent);
            }

        });
    }

    private class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mHomeList != null) {
                return mHomeList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mHomeList != null) {
                return mHomeList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.view_list_item, null);
            }
            ImageView mImage = (ImageView) convertView.findViewById(R.id.iv_list_item);
            TextView mText = (TextView) convertView.findViewById(R.id.tv_list_item);
            HomeBean mBean = mHomeList.get(position);
            mImage.setImageResource(mBean.pic);
            mText.setText(mBean.decs);
            return convertView;
        }
    }
}