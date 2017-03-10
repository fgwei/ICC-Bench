package org.arguslab.rpc_returnsensitive;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * @testcase_name RPC_ReturnSensitive
 * @author Fengguo Wei
 * @author_mail fgwei521@gmail.com
 *
 * @description The value v of a source is obtained from component LocalService via RPC call.
 * 				In MainActivity, it will handle the value and leak it.
 * @dataflow source -> imei -> return from LocalService.getData() -> mService.getData() -> data -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to resolve LocalService and handle data flow
 * 				across different components.
 */
public class MainActivity extends Activity {
    LocalService mService;
    boolean mBound = false;

    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            LocalService.LocalBinder binder = (LocalService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, LocalService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
    }

    private void leakData() {
        String data = "" + mService.getData();
        Log.i("data", data); // leak
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1: {
                leakData();
                return;
            }
        }
    }
}
