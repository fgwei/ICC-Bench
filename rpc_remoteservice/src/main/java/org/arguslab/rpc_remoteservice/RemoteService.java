package org.arguslab.rpc_remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {
    public RemoteService() {
    }

    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void setData(String data) throws RemoteException {
            Log.d("imei", "" + data); // sink
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
