package com.johnwayodi.cyclelogger;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static final int STOP_SPLASH = 0;
    private static final long SPLASH_TIME = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Message msg = new Message();
        msg.what = STOP_SPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASH_TIME);
    }



    private Handler splashHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    //remove SplashScreen from view
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
}
