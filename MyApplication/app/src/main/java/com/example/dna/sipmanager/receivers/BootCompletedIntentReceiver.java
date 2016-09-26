package com.example.dna.sipmanager.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.dna.sipmanager.MainActivity;

/**
 * Created by dna on 7/29/16.
 */
public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "booted", Toast.LENGTH_SHORT).show();
        //Intent i = new Intent(context, MainActivity.class);
        //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //context.startActivity(i);
    }
}
