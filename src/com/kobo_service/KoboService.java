package com.kobo_service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class KoboService extends Service {
    KoboBroadcastReceiver _koboBroadcastReceiver;

    public static void startService(Context context) {
        Intent service = new Intent(context, KoboService.class);
        context.startService(service);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        Log.i("KoboService", "KoboService is starting.");

        if (_koboBroadcastReceiver == null) {
            _koboBroadcastReceiver = new KoboBroadcastReceiver();
            KoboServiceApplication.getAppContext().registerReceiver(_koboBroadcastReceiver,
                    new IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED));
        }

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Log.i("KoboService", "KoboService is stopping.");

        if (_koboBroadcastReceiver != null) {
            KoboServiceApplication.getAppContext().unregisterReceiver(_koboBroadcastReceiver);
            _koboBroadcastReceiver = null;
        }

        super.onDestroy();
    }
}
