package com.example.dna.dialerapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by dna on 8/1/16.
 */
public class HeadlessSmsSendService extends Service {
    public HeadlessSmsSendService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
