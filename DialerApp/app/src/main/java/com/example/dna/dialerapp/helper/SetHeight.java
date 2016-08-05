package com.example.dna.dialerapp.helper;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by dna on 8/5/16.
 */
public class SetHeight {
    public SetHeight () {}

    public static int setDynamicHeight(Context context) {
        WindowManager windowManager = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        return  windowManager.getDefaultDisplay().getHeight();
    }
}
