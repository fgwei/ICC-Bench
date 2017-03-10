package org.arguslab.rpc_localservice;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

/**
 * @testcase_name RPC_LocalService
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description The value v of a source is sent to component LocalService via RPC call.
 * 				In LocalService, it will handle the rpc and leak it.
 * @dataflow source -> imei -> mService.setData() -> LocalService.setData() -> data -> sink
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

    private void leakImei() {
        TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = tel.getDeviceId(); // source
        mService.setData(imei);
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
