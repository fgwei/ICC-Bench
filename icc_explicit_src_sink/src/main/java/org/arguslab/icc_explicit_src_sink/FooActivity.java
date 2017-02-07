package org.arguslab.icc_explicit_src_sink;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class FooActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String imei = "" + i.getStringExtra("data");
        Log.d("deviceid", imei); // sink
    }
}