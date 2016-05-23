package com.example.zhengshujuan.securitymanager.ui;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.zhengshujuan.securitymanager.R;

import java.util.List;

public class sjfd_setup4 extends BaseSetupButton {

    private static final String TAG = "LocationChanged";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sjfd_setup4);
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);

        List<String> allProviders=lm.getAllProviders();
        for (String allProvider:allProviders){

        }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = lm.getBestProvider(criteria, true);
      //  lm.requestLocationUpdates(bestProvider,0,5000,listener);

        LocationListener listener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //获取位置变化的结果
                float accracy = location.getAccuracy();
                double altitude = location.getAltitude();
                double latitude = location.getLatitude();
                float speed = location.getSpeed();
                long time = location.getTime();


                Log.d(TAG, "onLocationChanged: " + accracy + "  " + latitude + "  " + altitude + "  "
                        + speed + "  " + time);


            }


            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        lm.requestLocationUpdates("gps", 0, 0, listener);
    }

    @Override
    protected void backActivity() {
        startBack(sjfd_setup3.class);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void nextActivity() {


    }
}
