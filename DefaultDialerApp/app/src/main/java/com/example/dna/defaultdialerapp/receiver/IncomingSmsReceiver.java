package com.example.dna.defaultdialerapp.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.defaultdialerapp.MainActivity;
import com.example.dna.defaultdialerapp.R;
import com.example.dna.defaultdialerapp.ViewSms;

/**
 * Created by dna on 8/2/16.
 */
public class IncomingSmsReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        final SmsManager sms = SmsManager.getDefault();
        NotificationManager notificationManager = null;
        int notiId = 1;
        final Bundle bundle = intent.getExtras(); // Retrieves a map of extended data from the intent.
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
