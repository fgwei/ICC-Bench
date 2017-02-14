package org.arguslab.icc_rpc_comprehensive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.TelephonyManager;
import android.util.Log;

public class RemoteService extends Service {
    private String imei = null;
    private final IRemoteService.Stub mBinder = new IRemoteService.Stub() {
        @Override
        public void setImei() throws RemoteException {
            TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
            imei = tel.getDeviceId(); // source
        }
        @Override
        public String getImei() throws RemoteException {
            return imei;
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
