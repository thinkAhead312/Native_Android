package com.example.dna.dialerapp;

import android.util.Log;

import net.hockeyapp.android.CrashManagerListener;

/**
 * Created by dna on 8/12/16.
 */

public class MyCustomCrashManagerListener extends CrashManagerListener {
    @Override
    public boolean shouldAutoUploadCrashes() {
        Log.i("Crashes Detected", "App Crash");
        return true;
    }

}
