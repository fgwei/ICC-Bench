package org.arguslab.rpc_messengerservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class MessengerService extends Service {
    public MessengerService() {
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d("imei", "" + msg.obj); // sink
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
