package com.example.dna.dialerapp.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.dna.dialerapp.DialPad;
import com.example.dna.dialerapp.SendSms;
import com.example.dna.dialerapp.ViewSms;

/**
 * Created by dna on 8/4/16.
 */
public class IntentStartActivity {
    public void IntetnStartActivity() {}

    public static void intentViewSmsActivity(Context context, String phoneNum) {
        Intent intent = new Intent(context,ViewSms.class);
        intent.putExtra("Users_ID", phoneNum);
        context.startActivity(intent);
    }

    public static void intentDialPadViewActivity(Context context, String phoneNum) {
        Intent intent = new Intent(context,DialPad.class);
        intent.putExtra("Users_ID", phoneNum);
        context.startActivity(intent);
    }

    public static void intentSendSmsViewActivity(Context context) {
        Intent intent = new Intent(context,SendSms.class);
        context.startActivity(intent);
    }


}
