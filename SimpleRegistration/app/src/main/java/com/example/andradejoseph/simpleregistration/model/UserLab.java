package com.example.andradejoseph.simpleregistration.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andradejoseph.simpleregistration.database.UserCursorWrapper;
import com.example.andradejoseph.simpleregistration.database.UserDbBaseHelper;
import com.example.andradejoseph.simpleregistration.database.UserDbSchema;
import com.example.andradejoseph.simpleregistration.database.UserDbSchema.UserTable.Cols;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class UserLab {

    private static UserLab sUserLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;



    public static UserLab get(Context context) {
        if(sUserLab == null) {
            synchronized (UserLab.class) {
                sUserLab = new UserLab(context);
            }
        }
        return sUserLab;
    }

    private UserLab(Context context) {
        mContext = context;
        mDatabase = new UserDbBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        Log.d("DiscipleLab", user.getUser_name());
        mDatabase.insert(UserDbSchema.UserTable.NAME, null, values);
    }

    private ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(Cols.USER_ID, user.getUser_id().toString());
        values.put(Cols.USER_NAME, user.getUser_name());
        values.put(Cols.PASSWORD, user.getPassword());
        return values;
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        UserCursorWrapper cursor = queryUser(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(cursor.getUser());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return users;
    }

    private UserCursorWrapper queryUser(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                UserDbSchema.UserTable.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );

        return new UserCursorWrapper(cursor);
    }

}
