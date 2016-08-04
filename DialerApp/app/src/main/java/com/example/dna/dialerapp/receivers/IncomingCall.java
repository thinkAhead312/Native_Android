package com.example.dna.dialerapp.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.dna.dialerapp.Calling;
import com.example.dna.dialerapp.MainActivity;
import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.ViewSms;

/**
 * Created by dna on 7/25/16.
 */
public class IncomingCall extends BroadcastReceiver {

    Context context= null;
    NotificationManager notificationManager = null;
    int notiId = 1;

    private static IncomingCall instance = null;



    public static IncomingCall getInstance() {
        if(instance == null) {
            instance = new IncomingCall();
        }
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent != null && intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL"))
        {
            Toast.makeText(context, "Outgoign call" , Toast.LENGTH_LONG).show();
        }
        else
        {
                if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING))
                {
                    // This code will execute when the phone has an incoming call
                    // get the phone number
                    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                    Toast.makeText(context, "Call from:" + incomingNumber, Toast.LENGTH_LONG).show();

                    Intent resultIntent = new Intent(context, MainActivity.class);
                    resultIntent.putExtra("Users_ID", incomingNumber);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle(incomingNumber);
                    builder.setContentText(incomingNumber);
                    builder.setSmallIcon(R.drawable.c_status);
                    builder.setTicker(incomingNumber);
                    builder.setContentIntent(resultPendingIntent);
                    builder.setAutoCancel(true);
                    notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    notificationManager.notify(notiId, builder.build());
                    Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();
                    // end for loop

                    Intent intentActivity = new Intent(context.getApplicationContext(),
                            Calling.class);
                    intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("STATE", "Incoming_Call");
                    context.startActivity(intentActivity);

                    //answerCall();
                }
                else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals( TelephonyManager.EXTRA_STATE_IDLE))
                {
                    // This code will execute when the call is disconnected
                    Toast.makeText(context, "Device call state: No activity.t", Toast.LENGTH_LONG).show();
                    Calling inst = Calling.instance();
                    //inst.closeActivity();
                } else if(intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
                {
                    Toast.makeText(context, "Device call state: Ringing. A new call arrived and is ringing or waiting. In the latter case, another call is already active.", Toast.LENGTH_LONG).show();
                }

            }
    }
    public void answerCall() {
        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
        i.putExtra(Intent.EXTRA_KEY_EVENT,
                new KeyEvent(KeyEvent.ACTION_UP,
                        KeyEvent.KEYCODE_HEADSETHOOK));
        context.sendOrderedBroadcast(i, null);
    }

}
