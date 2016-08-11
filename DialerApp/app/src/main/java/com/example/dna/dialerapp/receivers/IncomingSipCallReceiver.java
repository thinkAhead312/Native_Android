package com.example.dna.dialerapp.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.util.Log;
import android.widget.Toast;


import com.example.dna.dialerapp.Calling;
import com.example.dna.dialerapp.SipAndroid;
import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.model.Constants;

import java.net.URISyntaxException;


/**
 * Created by dna on 7/28/16.
 */
public class IncomingSipCallReceiver extends BroadcastReceiver {



    private SipAudioCall incomingCall;
    private SipAndroid sipAndroid;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    Log.i("ONRINGING", "rinnging");
                    Toast.makeText(context, "ONRINGING", Toast.LENGTH_SHORT).show();

                    IntentStartActivity.intentCallingActivity(context, Constants.Call_State, Constants.Sip_Incoming);
                }
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    Log.i("onCallEstablished", "onCallEstablished");
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    Log.i("onCallEnded", "onCallEnded123");
                    context.sendBroadcast(new Intent("kill"));
                    //Toast.makeText(context, "Ended", Toast.LENGTH_SHORT).show()
                }
            };

            sipAndroid = SipAndroid.getInstance();
            sipAndroid.SipAndroidInitialize(context);
            incomingCall = sipAndroid.manager.takeAudioCall(intent, null);
            incomingCall.setListener(listener, true);
            sipAndroid.call = incomingCall;
            //wtActivity.updateStatus(incomingCall);
        } catch (Exception e) {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }
    }


}
