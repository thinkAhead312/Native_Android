package com.example.dna.smsresolver;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by dna on 7/21/16.
 */
public class SmsReceiver extends BroadcastReceiver {

    public static final String SMS_EXTRA_NAME = "pdus";
    public static final String SMS_URI = "content://sms";
    public static final String ADDRESS = "address";
    public static final String PERSON = "person";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";
    public static final String SEEN = "seen";
    public static final int MESSAGE_TYPE_INBOX = 1;
    public static final int MESSAGE_TYPE_SENT = 2;
    public static final int MESSAGE_IS_NOT_READ = 0;
    public static final int MESSAGE_IS_READ = 1;
    public static final int MESSAGE_IS_NOT_SEEN = 0;
    public static final int MESSAGE_IS_SEEN = 1;
    public static final byte[] PASSWORD = new byte[]{(byte)32, (byte)50, (byte)52, (byte)71, (byte)-124, (byte)51, (byte)88};

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String messages = "";
        if(extras != null) {
            Object[] smsExtra = (Object[])extras.get("pdus");
            ContentResolver contentResolver = context.getContentResolver();

            for(int i = 0; i < smsExtra.length; ++i) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[])smsExtra[i]);
                String body = sms.getMessageBody().toString();
                String address = sms.getOriginatingAddress();
                messages = messages + "SMS from " + address + " :\n";
                messages = messages + body + "\n";
                this.putSmsToDatabase(contentResolver, sms);
            }


            Toast.makeText(context, messages,Toast.LENGTH_SHORT).show();
        }

    }

    private void putSmsToDatabase(ContentResolver contentResolver, SmsMessage sms) {
        ContentValues values = new ContentValues();
        values.put("address", sms.getOriginatingAddress());
        values.put("date", Long.valueOf(sms.getTimestampMillis()));
        values.put("read", Integer.valueOf(0));
        values.put("status", Integer.valueOf(sms.getStatus()));
        values.put("type", Integer.valueOf(1));
        values.put("seen", Integer.valueOf(0));

        try {
            String e = StringCryptor.encrypt(new String(PASSWORD), sms.getMessageBody().toString());
            values.put("body", e);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        contentResolver.insert(Uri.parse("content://sms"), values);
    }
}
