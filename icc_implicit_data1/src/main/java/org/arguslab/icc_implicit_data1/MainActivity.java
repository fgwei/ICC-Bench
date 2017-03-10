package org.arguslab.icc_implicit_data1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * @testcase_name ICC_Implicit_Data1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description The value v of a source is sent to component FooActivity via implicit ICC.
 * 				In FooActivity, it will retrieve value v and leak it.
 * @dataflow source -> imei -> MainActivity's intent -> sink (implicit ICC)
 * 			 source -> imei -> MainActivity's intent -> FooActivity's intent -> imei -> sink
 * @number_of_leaks 2
 * @challenges The analysis must be able to resolve implicit (Data) ICC calls and handle data flow
 * 				across different components.
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

        Intent i = new Intent();
        Uri uri = Uri.parse("amandroid://fgwei:4444");
        i.setData(uri);

        i.putExtra("data", imei);

        startActivity(i); // sink
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
