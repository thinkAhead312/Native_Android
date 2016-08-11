package com.example.dna.dialerapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.dialerapp.SipAndroid;

/**
 * Created by dna on 8/11/16.
 */
public class UtilityService extends Service {

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        SipAndroid sipAndroid = SipAndroid.getInstance();
        sipAndroid.SipAndroidInitialize(this);
        Log.i("Device Re-started", "Sip Re-started");

        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}
