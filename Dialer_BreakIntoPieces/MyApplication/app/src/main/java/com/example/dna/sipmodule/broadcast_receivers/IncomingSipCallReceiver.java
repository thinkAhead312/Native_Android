package com.example.dna.sipmodule.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.sipmodule.SipAndroid;
import com.example.dna.sipmodule.helper.IntentStartActivity;
import com.example.dna.sipmodule.model.Constants;

/**
 * Created by dna on 8/15/16.
 */
public class IncomingSipCallReceiver extends BroadcastReceiver{
    private SipAudioCall incomingCall;
    private SipAndroid sipAndroid;

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    super.onRinging(call, caller);
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

        } catch (Exception e) {

        }
    }
}
