package org.arguslab.icc_intentservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String imei = "" + intent.getStringExtra("data");
            Log.d("imei", imei); // sink
        }
    }
}
