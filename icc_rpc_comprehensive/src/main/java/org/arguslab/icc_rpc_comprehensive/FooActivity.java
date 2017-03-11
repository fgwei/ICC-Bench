package org.arguslab.icc_rpc_comprehensive;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;

/**
 * @testcase_name ICC_RPC_Comprehensive
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description FooActivity start a stateful ICC with BarActivity and waiting for the result and leak it.
 *              The sensitive data is obtained by BarActivity from component RemoteService via RPC call.
 * 				BarActivity then return the sensitive data to FooActivity.
 * @dataflow source -> imei -> RemoteService.getData() -> BarActivity's imei2 -> BarActivity's Intent i3 -> BarActivity.setResult(i3) -> FooActivity.onActivityResult(data) -> imei3 -> leak
 *           source -> imei -> RemoteService.getData() -> BarActivity's imei2 -> BarActivity's Intent i3 -> BarActivity.setResult(i3)
 * @number_of_leaks 2
 * @challenges The analysis must be able to resolve AIDL, stateful ICC and handle data flow
 * 				across different components.
 */
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
