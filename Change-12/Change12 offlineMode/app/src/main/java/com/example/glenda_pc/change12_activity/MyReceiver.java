package com.example.glenda_pc.change12_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by dna on 4/29/16.
 */
public class MyReceiver extends BroadcastReceiver {
    Context ctx;

    public MyReceiver(Context ctx){
        this.ctx = ctx;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Intent Detected.", Toast.LENGTH_LONG).show();
        ((Change12_Activity) ctx).finish();
    }
}
