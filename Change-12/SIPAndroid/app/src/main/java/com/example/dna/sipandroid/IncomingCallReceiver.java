package com.example.dna.sipandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.sip.SipAudioCall;
import android.net.sip.SipProfile;

/**
 * Created by dna on 7/28/16.
 */
public class IncomingCallReceiver extends BroadcastReceiver
{
    /**
     * Processes the incoming call, answers it, and hands it over to the
     * WalkieTalkieActivity.
     * @param context The context under which the receiver is running.
     * @param intent The intent being received.
     */

    @Override
    public void onReceive(Context context, Intent intent)
    {
        SipAudioCall incomingCall = null;
        try
        {
            SipAudioCall.Listener listener = new SipAudioCall.Listener()
            {
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    try {
                        call.answerCall(30);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };

            MainActivity mainActivity = (MainActivity) context;

            incomingCall = mainActivity.manager.takeAudioCall(intent, listener);
            incomingCall.answerCall(30);
            incomingCall.startAudio();
            incomingCall.setSpeakerMode(true);

            if(incomingCall.isMuted())
            {
                incomingCall.toggleMute();
            }

            mainActivity.call = incomingCall;
            mainActivity.updateStatus(incomingCall);

        }
        catch (Exception e)
        {
            if (incomingCall != null) {
                incomingCall.close();
            }
        }

    }
}
