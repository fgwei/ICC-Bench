package org.arguslab.icc_intentservice;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * @testcase_name ICC_IntentService
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description The value v of a source is sent to component MyIntentService.
 * 				In MyIntentService, it will handle the Intent and leak it.
 * @dataflow source -> imei -> MainActivity's intent -> MyIntentService's onHandleIntent() -> imei -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to resolve IntentService and handle data flow
 * 				via different components.
 */
public class MainActivity extends AppCompatActivity {

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

        Intent i = new Intent(getApplicationContext(), MyIntentService.class);
        i.putExtra("data", imei);
        startService(i);
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
