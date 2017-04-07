package com.example.andradejoseph.simpleregistration.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andradejoseph.simpleregistration.database.UserDbSchema.UserTable;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class UserDbBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "user.db";

    public UserDbBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.USER_ID + ", " +
                UserTable.Cols.USER_NAME + ", " +
                UserTable.Cols.PASSWORD +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
