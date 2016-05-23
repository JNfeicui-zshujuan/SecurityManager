package com.example.zhengshujuan.securitymanager.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhengshujuan.securitymanager.R;
import com.example.zhengshujuan.securitymanager.Utils.Constants;
import com.example.zhengshujuan.securitymanager.Utils.SpUtils;

public class sjfd_setup3 extends BaseSetupButton {
    private EditText etSafeTel;
    private Button btnBindSafeTel;
    private Button btnCPerson;
    private String username;
    private String usernumber;
    private static final String TAG = "sjfd_setup3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    protected void initView() {
        setContentView(R.layout.sjfd_setup3);
        etSafeTel= (EditText) findViewById(R.id.et_safetytel);
    }

    @Override
    public void initEvent() {
        btnBindSafeTel= (Button) findViewById(R.id.btn_bindsafetel);
        btnBindSafeTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的号码
                String tel = etSafeTel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    Toast.makeText(sjfd_setup3.this, "请输入安全号码", Toast.LENGTH_SHORT).show();

                } else {
                    //保存文本框安全号码
                    SpUtils.putString(getApplicationContext(), Constants.SAFETEL, tel);
                }
            }
        });
        btnCPerson= (Button) findViewById(R.id.btn_getContactsPerson);
        btnCPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI),0);
                //onActivityResult();
            }
        });

        super.initEvent();
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            // ContentProvider展示数据类似一个单个数据库表
            // ContentResolver实例带的方法可实现找到
            // 指定的ContentProvider并获取到ContentProvider的数据
            ContentResolver reContentResolverol = getContentResolver();
            // URI,每个ContentProvider定义一个唯一的公开的URI,
            // 用于指定到它的数据集
            Uri contactData = data.getData();
            // 查询就是输入URI等参数,其中URI是必须的,其他是可选的,
            // 如果系统能找到URI对应的ContentProvider将返回一个Cursor对象.
            Cursor cursor = managedQuery(contactData,
                    null, null, null, null);
            cursor.moveToFirst();
            // 获得DATA表中的名字
            username = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            // 条件为联系人ID
            String contactId = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            // 获得DATA表中的电话号码，条件为联系人ID,因为手机号码可能会有多个
            Cursor phone = reContentResolverol.query(
                    ContactsContract.
                            CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.
                            CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
            while (phone.moveToNext()) {
                usernumber = phone
                        .getString(phone
                                .getColumnIndex(ContactsContract.
                                        CommonDataKinds.Phone.NUMBER));
                etSafeTel.setText(usernumber);
                Log.d(TAG, "onActivityResult: "+usernumber);
            }

        }
    }

    @Override
    public void initData() {
        //初始化数据,拿到文本框的号码
        etSafeTel.setText(SpUtils.getString(getApplicationContext(),Constants.SAFETEL));
        super.initData();
    }

    @Override
    protected void backActivity() {
        startBack(sjfd_setup2.class);
    }

    @Override
    public void nextActivity() {
        startNext(sjfd_setup4.class);

    }
}
