package org.arguslab.icc_dynregister1;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

/**
 * @testcase_name ICC_DynRegister1
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description MainActivity registered MyReciver. The value v of a source is sent to component
 * 				MyReciver via implicit ICC. In MyReciver, it will retrieve value v and leak it.
 * @dataflow source -> imei -> MainActivity's intent -> sink (implicit ICC)
 * 			 source -> imei -> MainActivity's intent -> MyReciver's intent -> imei -> sink
 * @number_of_leaks 2
 * @challenges The analysis must be able to resolve dynamically registered component, implicit
 * 				ICC calls and handle data flow via different components.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }

        registerReceiver(new MyReceiver(), new IntentFilter("com.fgwei"));
    }

    private void leakImei() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String id = tm.getDeviceId(); //source
        Intent i = new Intent("com.fgwei");
        i.putExtra("id", id);
        sendBroadcast(i); //leak
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
