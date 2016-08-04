package com.example.dna.dialerapp.helper;

import android.content.Context;
import android.content.Intent;

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


}
