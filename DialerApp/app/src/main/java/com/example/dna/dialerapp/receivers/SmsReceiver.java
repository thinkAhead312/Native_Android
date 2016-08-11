package com.example.dna.dialerapp.receivers;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.ViewSms;
import com.example.dna.dialerapp.fragment.SmsList;

/**
 * Created by dna on 7/21/16.
 */
public class SmsReceiver extends BroadcastReceiver{

    SmsList smsList = new SmsList();
    String SMS_EXTRA_NAME = "pdus";
    String SMS_URI = "content://sms/inbox";
    String ADDRESS = "address";
    String PERSON = "person";
    String DATE = "date";
    String READ = "read";
    String STATUS = "status";
    String TYPE = "type";
    String BODY = "body";
    String SEEN = "seen";
    int MESSAGE_TYPE_INBOX = 1;
    int MESSAGE_TYPE_SENT = 2;
    int MESSAGE_IS_NOT_READ = 0;
    int MESSAGE_IS_READ = 1;
    int MESSAGE_IS_NOT_SEEN = 0;
    int MESSAGE_IS_SEEN = 1;
    // Change the password here or give a user possibility to change it
    final byte[] PASSWORD = new byte[]{ 0x20, 0x32, 0x34, 0x47, (byte) 0x84, 0x33, 0x58 };
    private boolean saving = false;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        final SmsManager sms = SmsManager.getDefault();
        int notiId = 1;
        NotificationManager notificationManager = null;
        final Bundle bundle = intent.getExtras();



        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                ContentResolver contentResolver = context.getContentResolver();
                String phoneNumber="";
                String senderNum="";
                String message="";

                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    senderNum = phoneNumber;
                    message = currentMessage.getDisplayMessageBody();

                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    putSmsToDatabase(contentResolver, currentMessage);
                } // end for loop.

                Intent resultIntent = new Intent(context, ViewSms.class);
                resultIntent.putExtra(ViewSms.intentString, phoneNumber);
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



            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }
    private void putSmsToDatabase(ContentResolver contentResolver, SmsMessage sms) {
        // Create SMS row
        ContentValues values = new ContentValues();
        values.put( ADDRESS, sms.getDisplayOriginatingAddress() );
        values.put( DATE, sms.getTimestampMillis() );
        values.put( READ, MESSAGE_IS_NOT_READ );
        values.put( STATUS, sms.getStatus() );
        values.put( TYPE, MESSAGE_TYPE_INBOX );
        values.put(SEEN, MESSAGE_IS_NOT_SEEN);
        values.put( BODY, sms.getMessageBody() );
        String encryptedPassword = null;
        // Push row into the SMS table
        if(!saving) {
            contentResolver.insert( Uri.parse(SMS_URI), values );
            saving = true;
        }
    }
}
