package org.arguslab.rpc_localservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class LocalService extends Service {
    public LocalService() {
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void setData(String data) {
        Log.d("imei", "" + data); // sink
    }
}
