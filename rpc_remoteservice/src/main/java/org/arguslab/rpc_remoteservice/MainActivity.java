package org.arguslab.rpc_remoteservice;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;

/**
 * @testcase_name RPC_RemoteService
 * @author Fengguo Wei & Sankardas Roy
 * @author_mail fgwei521@gmail.com & sroy@ksu.edu
 *
 * @description The value v of a source is sent to component RemoteService via RPC call.
 * 				In RemoteService, it will handle the rpc and leak it.
 * @dataflow source -> imei -> mService.setData() -> RemoteService.setData() -> data -> sink
 * @number_of_leaks 1
 * @challenges The analysis must be able to resolve AIDL and handle data flow
 * 				via different components.
 */
public class MainActivity extends Activity {

    IRemoteService mIRemoteService = null;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, RemoteService.class), mConnection, Context.BIND_AUTO_CREATE);
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

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mIRemoteService = IRemoteService.Stub.asInterface(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            mIRemoteService = null;
            mBound = false;
        }
    };

    private void leakImei() {
        TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String imei = tel.getDeviceId(); // source
        try {
            mIRemoteService.setData(imei);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
