package com.example.dna.dialerapp.receivers;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.ViewSms;
import com.example.dna.dialerapp.adapter.SmsAdapter;
import com.example.dna.dialerapp.adapter.UpdateListView;
import com.example.dna.dialerapp.fragment.SmsList;
import com.example.dna.dialerapp.model.Sms;

import java.util.ArrayList;

/**
 * Created by dna on 7/21/16.
 */
public class SmsReceiver extends BroadcastReceiver{

    SmsList smsList = new SmsList();

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        final SmsManager sms = SmsManager.getDefault();
        int notiId = 1;
        NotificationManager notificationManager = null;
        // Retrieves a map of extended data from the intent.
        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {

                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                String phoneNumber="";
                String senderNum="";
                String message="";
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);

                    Intent resultIntent = new Intent(context, ViewSms.class);
                    resultIntent.putExtra("Users_ID", phoneNumber);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    context,
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                    builder.setContentTitle(phoneNumber);
                    builder.setContentText(message);
                    builder.setSmallIcon(R.drawable.c_status);
                    builder.setTicker(message);
                    builder.setContentIntent(resultPendingIntent);
                    builder.setAutoCancel(true);
                    notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
                    notificationManager.notify(notiId, builder.build());
                    Toast.makeText(context, "received", Toast.LENGTH_SHORT).show();


                } // end for loop


            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }

    }
}
