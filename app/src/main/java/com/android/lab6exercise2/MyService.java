package com.android.lab6exercise2;


import android.app.Service;
import android.content.Intent;
import android.net.LocalServerSocket;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {

    private final IBinder iBinder = new LocalService();

    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return iBinder;
    }

    class LocalService extends Binder {

        public MyService getService(){

            return MyService.this;
        }
    }



}
