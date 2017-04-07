package com.example.andradejoseph.simpleregistration.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.andradejoseph.simpleregistration.constants.Constants;
import com.example.andradejoseph.simpleregistration.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();


    //shared Preferences
    public SharedPreferences userPref;
    SharedPreferences.Editor editor;
    Context mContext;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    public SessionManager(Context context) {
        mContext = context;
        userPref = mContext.getSharedPreferences(Constants.USER_PREF_NAME, PRIVATE_MODE);
        editor = userPref.edit();
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(Constants.KEY_IS_LOGGED_IN, isLoggedIn);
        //commit changes
        editor.commit();

        Log.d(TAG, "User login Session is Modified");
    }
    public boolean isLoggedIn() {
        return userPref.getBoolean(Constants.KEY_IS_LOGGED_IN, false);
    }

    public void setUserData(User user) {
            editor.putString(Constants.USER_ID, user.getUser_id().toString());
            editor.putString(Constants.USER_FIRST_NAME, user.getUser_name());
            editor.putString(Constants.USER_LAST_NAME, user.getPassword());
            //commit changes
            editor.commit();
           Log.d(TAG, "User Data Session is Modified");
    }


}
