package org.arguslab.icc_dynregister2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver2 extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        String id = "" + arg1.getStringExtra("id");
        process(id);
    }

    private void process(String id) {
        Log.d("leak2", id); //sink
    }
}
