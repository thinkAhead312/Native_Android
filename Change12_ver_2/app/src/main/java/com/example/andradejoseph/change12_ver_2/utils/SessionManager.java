package com.example.andradejoseph.change12_ver_2.utils;

import android.content.Context;
import android.content.SharedPreferences;

import android.content.SharedPreferences.Editor;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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

    public void setUserData(JSONObject jsonUserData) {

        try {
            editor.putString(Constants.USER_ID, jsonUserData.getString(Constants.USER_ID));
            editor.putString(Constants.USER_FIRST_NAME, jsonUserData.getString(Constants.USER_FIRST_NAME));
            editor.putString(Constants.USER_LAST_NAME, jsonUserData.getString(Constants.USER_LAST_NAME));
            editor.putString(Constants.USER_NICK_NAME, jsonUserData.getString(Constants.USER_NICK_NAME));
            editor.putString(Constants.USER_MIDDLE_NAME, jsonUserData.getString(Constants.USER_MIDDLE_NAME));
            editor.putString(Constants.USER_FULL_NAME, jsonUserData.getString(Constants.USER_FULL_NAME));
            editor.putString(Constants.USER_USERNAME, jsonUserData.getString(Constants.USER_USERNAME));
            editor.putString(Constants.USER_IS_ADMIN, jsonUserData.getString(Constants.USER_IS_ADMIN));
            //commit changes
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "User Data Session is Modified");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }


}
