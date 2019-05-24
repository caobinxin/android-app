package com.myntai.slightech.testreadwifiapssidpwd;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class QueryWifiApInfo extends BroadcastReceiver {

    private static final String TAG = "QueryWifiApInfo";
    private static final String REQUEST_BR = "com.android.settings.wifiap.request.pass";
    private static final String OFFER_BR = "com.android.settings.wifiap.offer.pass";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (REQUEST_BR.equals(intent.getAction())) {
            Log.i(TAG, "onReceive action==" + intent.getAction());

            ReadWifiApSsidPwd readWifiApSsidPwd = new ReadWifiApSsidPwd();
            String ssid = readWifiApSsidPwd.readSsid();
            String password = readWifiApSsidPwd.readPsk();

            Intent intentReturn = new Intent(OFFER_BR);
            intentReturn.putExtra("SSID", ssid);
            intentReturn.putExtra("PASSWORD", password);
            context.sendBroadcast(intentReturn);

            Log.i(TAG, "查询wifiap:" + ssid + " 密码:" + password);
        }
    }
}
