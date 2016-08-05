package com.example.dna.sipdroid.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.dna.sipdroid.MainActivity;

/**
 * Created by dna on 7/29/16.
 */
public class OutgoingCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Get phone number
        String phoneNumber = getResultData();

        if (phoneNumber == null) {
            phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        }

        setResultData(null);

        // Start and pass the phone number to our SipDemo class

        Intent i = new Intent(context.getApplicationContext(),
                MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("OUTBOUND_NUMBER", phoneNumber);
        context.startActivity(i);
    }
}
