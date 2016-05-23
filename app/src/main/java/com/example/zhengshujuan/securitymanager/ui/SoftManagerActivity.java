package com.example.zhengshujuan.securitymanager.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhengshujuan.securitymanager.Bean.SMAppBean;
import com.example.zhengshujuan.securitymanager.R;
import com.example.zhengshujuan.securitymanager.engin.SoftManager;

import java.util.ArrayList;
import java.util.List;

public class SoftManagerActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView mListView;
    List<SMAppBean> mData;
    private SMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soft_manager);

        mListView = (ListView) findViewById(R.id.lv_softm);

       // mData = SoftManager.getAppInfo(SoftManagerActivity.this);
        adapter = new SMAdapter();
        //  mListView.setAdapter(new SMAdapter());
        mListView.setOnItemClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.removeAllViewsInLayout();


        mData = SoftManager.getAppInfo(SoftManagerActivity.this);

        mListView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SMAppBean bean=mData.get(position);
        String name = bean.packageName;
        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.parse("package:" + name));
        startActivity(intent);
    }

    private class SMAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mData != null) {
                return mData.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mData != null) {
                return mData.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Viewhold hold;
            if (convertView == null) {
                hold = new Viewhold();

                convertView = View.inflate(getApplicationContext(), R.layout.softm_list_item, null);

                hold.name = (TextView) convertView.findViewById(R.id.tv_name_list);
                hold.packageName = (TextView) convertView.findViewById(R.id.tv_packageName_list);
                hold.icon = (ImageView) convertView.findViewById(R.id.iv_icon_list);

                convertView.setTag(hold);
            } else {
                hold = (Viewhold) convertView.getTag();
            }
            SMAppBean bean = (SMAppBean) getItem(position);

            hold.name.setText(bean.name);
            hold.packageName.setText(bean.packageName);
            hold.icon.setImageDrawable(bean.icon);
            return convertView;
        }

        private class Viewhold {
            TextView name;
            TextView packageName;
            ImageView icon;
        }
    }

}
