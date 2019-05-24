package com.myntai.slightech.testvirtualioservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.IVirtualioService ;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static private String TAG = "MainActivity" ;
    IVirtualioService iVirtaulioService ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getVirtualioService() ;

        try{
            if(null == iVirtaulioService){
                Log.e(TAG, "iVirtaulioService = " + iVirtaulioService) ;
            }

            Log.i(TAG, "hasVirtualio = " + iVirtaulioService.hasVirtualio()) ;
            iVirtaulioService.setReg(1, 3) ;
            iVirtaulioService.setReg(2, 5) ;
//            iVirtaulioService.setReg(2, 6) ;
//            iVirtaulioService.setReg(3, 8) ;

            int reg1 = iVirtaulioService.getReg(1) ;
            int reg2 = iVirtaulioService.getReg(2) ;
//            int reg3 = iVirtaulioService.getReg(2) ;
//            int reg4 = iVirtaulioService.getReg(3) ;

            Log.i(TAG, "reg1:" + reg1 + "  reg2:" + reg2) ;
//            Log.i(TAG, "reg3:" + reg3 + "  reg4:" + reg4) ;

        }catch(Exception e){
            Log.e(TAG, "" + e) ;
        }

    }

    void getVirtualioService(){
        try{
            android.os.IBinder b = android.os.ServiceManager.getService("virtualio") ;
            Log.e(TAG, "b = " + b) ;
            iVirtaulioService = IVirtualioService.Stub.asInterface(b) ;
        }catch(Exception e){
            Log.e(TAG, "" + e) ;
        }
    }
}
