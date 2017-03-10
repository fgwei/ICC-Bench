package org.arguslab.icc_stateful;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

/**
 * @testcase_name RPC_Stateful
 * @author Fengguo Wei
 * @author_mail fgwei521@gmail.com
 *
 * @description MainActivity start FooActivity and waiting for the result and leak it.
 * 				FooActivity obtains sensitive data and return to MainActivity.
 * @dataflow source -> imei -> i2 -> FooActivity.setResult(i2) -> MainActivity.onActivityResult(data) -> imei3 -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to resolve stateful ICC call and handle data flow
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
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                Intent i1 = new Intent();
                i1.setClass(getApplicationContext(), FooActivity.class);
                startActivityForResult(i1, 1);
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String imei3 = "" + data.getStringExtra("data");
        Log.d("imei", imei3); // sink
    }
}
