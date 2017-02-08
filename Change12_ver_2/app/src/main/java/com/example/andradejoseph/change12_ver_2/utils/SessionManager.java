package com.example.andradejoseph.change12_ver_2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by ANDRADEJOSEPH on 2/8/2017.
 */

public class SessionManager {
    //LogCat Tag
    private static String TAG = SessionManager.class.getSimpleName();

    //shared Preferences
    SharedPreferences pref;
    Editor editor;
    Context mContext;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        mContext = context;
        pref = mContext.getSharedPreferences(Constants.PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, isLoggedIn);
        //commit changes
        editor.commit();

        Log.d(TAG, "User login Session is Modified");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }


}
