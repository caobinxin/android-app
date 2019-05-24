package com.myntai.slightech.test4ghardware;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class CheckWwan {
    static final String TAG = "CheckWwan" ;
    static boolean isWwanExistence() throws Exception {
        int retInt ;
        Process process = null;
        DataOutputStream dataOutputStream = null;
        DataInputStream dataInputStream = null;
        BufferedReader bufferedReader ;
        String ssidString = "";
        String password = "";
        StringBuffer cmdContext = new StringBuffer();
        try {
            process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            dataInputStream = new DataInputStream(process.getErrorStream());
            dataOutputStream.writeBytes("cat  /proc/net/dev_snmp6/wwan0\n");
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    dataInputStream, "UTF-8");
            bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                cmdContext.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            process.waitFor();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                process.destroy();
            } catch (Exception e) {
                throw e;
            }
        }

        retInt = cmdContext.toString().indexOf("No such file or directory");

        /**
         * true 为存在wwan0
         *
         * */
        return retInt == -1 ? true : false ;
    }
}
