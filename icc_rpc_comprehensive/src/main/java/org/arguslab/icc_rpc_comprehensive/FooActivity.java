package org.arguslab.icc_rpc_comprehensive;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

public class FooActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foo);
        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                Intent i1 = new Intent();
                i1.setClass(getApplicationContext(), BarActivity.class);
                startActivityForResult(i1, 1);
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String imei3 = "" + data.getStringExtra("key");
        Log.d("imei", imei3); // sink
    }
}
