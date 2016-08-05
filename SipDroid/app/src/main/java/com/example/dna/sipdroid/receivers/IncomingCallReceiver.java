package com.example.dna.sipdroid.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.sipdroid.MainActivity;


/**
 * Created by dna on 7/28/16.
 */
public class IncomingCallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        SipAudioCall incomingCall = null;
        final MainActivity wtActivity = (MainActivity) context;
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    Log.i("ONRINGING", "rinnging");
                    Toast.makeText(context, "ONRINGING", Toast.LENGTH_SHORT).show();
                    wtActivity.updateStatus("ONRINGING...");

                }
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    Log.i("onCallEstablished", "onCallEstablished");
                    wtActivity.updateStatus("onCallEstablished");
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    Log.i("onCallEnded", "onCallEnded");
                    wtActivity.updateStatus("Ready");

                }
            };

            incomingCall = wtActivity.manager.takeAudioCall(intent, null);
            incomingCall.setListener(listener, true);
            incomingCall.answerCall(30);
            incomingCall.startAudio();
            //incomingCall.setSpeakerMode(true);

            if(incomingCall.isMuted()) {
            //    incomingCall.toggleMute();
            }
            wtActivity.call = incomingCall;
            wtActivity.updateStatus(incomingCall);

        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }
}
