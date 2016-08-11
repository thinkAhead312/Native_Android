package com.example.dna.dialerapp.receivers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.dialerapp.SipAndroid;
import com.example.dna.dialerapp.service.UtilityService;

/**
 * Created by dna on 8/11/16.
 */
public class LaunchAppBroadcast extends BroadcastReceiver {
    Activity activity = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        //Toast.makeText(context, "Device Re-started", Toast.LENGTH_SHORT).show();

        Intent serviceIntent = new Intent(context, UtilityService.class);
        // Start service
        context.startService(serviceIntent);
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        Log.i("Device Re-started", "Device Re-started");
    }
}
