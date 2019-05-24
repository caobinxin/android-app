package com.myntai.slightech.testreadwifiapssidpwd;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadWifiApSsidPwd {

    private static final String WIFI_AP_CONFIG_SSID = Environment.getDataDirectory() + "/misc/wifi/softap.ssid";

    private static final String WIFI_AP_CONFIG_PSK = Environment.getDataDirectory() + "/misc/wifi/softap.psk";

    private static final String READ_SSID_FILE = "/sdcard/softap.ssid";
    private static final String READ_PSK_FILE = "/sdcard/softap.psk";

    private static final String TAG = "ReadWifiApSsidPwd";

    public String readSsid() {
        String ret = "";
        cpWifiApItem(WIFI_AP_CONFIG_SSID);
        ret = readItem(READ_SSID_FILE);
        Log.d(TAG, "WifiApSSID = " + ret) ;
        return ret;
    }

    public String readPsk() {
        String ret = "";
        cpWifiApItem(WIFI_AP_CONFIG_PSK);
        ret = readItem(READ_PSK_FILE);
        Log.d(TAG, "WifiApPSK = " + ret) ;
        return ret;
    }

    private void shellExec(String command) throws Exception {
        Process process2 = null;
        DataOutputStream dataOutputStream2 = null;
        try {
            process2 = Runtime.getRuntime().exec("su");
            dataOutputStream2 = new DataOutputStream(process2.getOutputStream());
            dataOutputStream2.writeBytes(command);
            dataOutputStream2.writeBytes("exit\n");
            dataOutputStream2.flush();
            process2.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream2 != null) {
                    dataOutputStream2.close();
                }
                process2.destroy();
            } catch (Exception e) {
                throw e;
            }
        }
    }


    private void cpWifiApItem(String filename) {
        try {
            shellExec("cp " + filename + " /sdcard/ \n");
        } catch (Exception e) {
            Log.e(TAG, "" + e);
        }
    }

    private String readItem(String filename) {
        String ret = "";
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));
            ret = in.readUTF();
        } catch (IOException e) {
            Log.e(TAG, "Error reading " + filename + " e=" + e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(TAG, "Error closing " +filename+ " during read e=" + e);
                }
            }
        }
        return ret;
    }
}
