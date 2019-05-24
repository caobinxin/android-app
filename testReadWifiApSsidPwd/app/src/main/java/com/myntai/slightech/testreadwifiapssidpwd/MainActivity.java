package com.myntai.slightech.testreadwifiapssidpwd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ReadWifiApSsidPwd readWifiApSsidPwd = new ReadWifiApSsidPwd() ;

        String ssid = readWifiApSsidPwd.readSsid();
        String pwd = readWifiApSsidPwd.readPsk() ;

        Log.d("MainActivity","ssid:" + ssid + "  pwd:" + pwd) ;
    }

}
