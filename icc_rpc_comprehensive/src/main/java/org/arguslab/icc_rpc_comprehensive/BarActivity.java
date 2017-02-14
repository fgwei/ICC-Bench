package org.arguslab.icc_rpc_comprehensive;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;

public class BarActivity extends Activity implements View.OnClickListener {
    private IRemoteService s = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent i2 = new Intent();
        i2.setClass(getApplicationContext(), RemoteService.class);
        bindService(i2, mConnections, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(s != null) {
            unbindService(mConnections);
        }
    }
    // button click callback defined in activity_bar.xml
    @Override
    public void onClick(View view) {
        if(s != null) {
            try {
                String imei2 = s.getImei();
                Intent i3 = getIntent();
                i3.putExtra("key", imei2);
                setResult(RESULT_OK, i3);
                finish();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    private ServiceConnection mConnections = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            s = IRemoteService.Stub.asInterface(iBinder);
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            s = null;
        }
    };
}
