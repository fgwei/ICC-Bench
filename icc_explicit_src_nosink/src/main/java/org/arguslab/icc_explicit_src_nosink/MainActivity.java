package org.arguslab.icc_explicit_src_nosink;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.app.Activity;
import android.content.Intent;

/**
 * @testcase_name ICC_Explicit_Src_NoSink
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description The value v of a source is sent to component FooActivity via explicit ICC.
 * 				In FooActivity, it will retrieve value v but not leak it.
 * @dataflow source -> imei -> MainActivity's intent -> FooActivity's intent -> imei
 * @number_of_leaks 0
 * @challenges The analysis must be able to resolve explicit ICC calls and handle data flow
 * 				via different components.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    private void leakImei() {
        TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = tel.getDeviceId(); // source

        Intent i = new Intent(getApplicationContext(), FooActivity.class);

        i.putExtra("data", imei);
        startActivity(i);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                leakImei();
                return;
            }
        }
    }
}