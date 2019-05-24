package com.myntai.slightech.test4ghardware;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.i("MainActivitytt", "onCreate: " + CheckWwan.isWwanExistence());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
