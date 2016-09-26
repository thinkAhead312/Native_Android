package com.example.dna.sipmodule.helper;

import android.content.Context;
import android.content.Intent;

import com.example.dna.sipmodule.Calling;
import com.example.dna.sipmodule.DialPad;
import com.example.dna.sipmodule.preference_settings.SipSettings;


/**
 * Created by dna on 8/4/16.
 */
public class IntentStartActivity {
    public void IntetnStartActivity() {}

    /*public static void intentViewSmsActivity(Context context, String phoneNum) {
        Intent intent = new Intent(context,ViewSms.class);
        intent.putExtra("Users_ID", phoneNum);
        context.startActivity(intent);
    }



    public static void intentSendSmsViewActivity(Context context) {
        Intent intent = new Intent(context,SendSms.class);
        context.startActivity(intent);
    }*/

    public static void intentDialPadViewActivity(Context context, String phoneNum) {
        Intent intent = new Intent(context,DialPad.class);
        intent.putExtra("Users_ID", phoneNum);
        context.startActivity(intent);
    }

    public static void intentCallingActivity(Context context, String KEY, String STRING_EXTRA) {

        Intent intentActivity = new Intent(context.getApplicationContext(),
                Calling.class);
        intentActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentActivity.putExtra(KEY, STRING_EXTRA);
        context.startActivity(intentActivity);
    }

    public static void updatePreferences(Context context) {
        Intent settingsActivity = new Intent(context.getApplicationContext(),
                SipSettings.class);
        context.startActivity(settingsActivity);
    }


}
