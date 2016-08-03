package com.team_coder.myapplication.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    private static final String TAG = MyIntentService.class.getSimpleName();

    public MyIntentService() {
        super("MyIntentService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent.getAction().equals("start")) {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                    Log.d(TAG, "onStartCommand: start " + i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
