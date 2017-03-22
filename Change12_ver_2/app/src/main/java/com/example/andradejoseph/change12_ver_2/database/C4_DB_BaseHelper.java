package com.example.andradejoseph.change12_ver_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andradejoseph.change12_ver_2.database.C4_DbSchema.UsersTable;

import static com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema.*;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class C4_DB_BaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "c4_db.db";


    public C4_DB_BaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + UsersTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UsersTable.Cols.DISCIPLE_ID + ", " +
                UsersTable.Cols.SLUG + ", " +
                UsersTable.Cols.FIRST_NAME + ", " +
                UsersTable.Cols.MIDDLE_NAME + ", " +
                UsersTable.Cols.LAST_NAME + ", " +
                UsersTable.Cols.FULL_NAME + ", " +
                UsersTable.Cols.NICK_NAME + ", " +
                UsersTable.Cols.GENDER + ", " +
                UsersTable.Cols.BIRTH_DATE + ", " +
                UsersTable.Cols.NATIONALITY + ", " +
                UsersTable.Cols.HOME_ADDRESS + ", " +
                UsersTable.Cols.CITY_ADDRESS + ", " +
                UsersTable.Cols.CONTACT_NUMBER + ", " +
                UsersTable.Cols.EMAIL_ADDRESS + ", " +
                UsersTable.Cols.SCHOOL + ", " +
                UsersTable.Cols.DEGREE + ", " +
                UsersTable.Cols.MARITAL_STATUS + ", " +
                UsersTable.Cols.COMPANY + ", " +
                UsersTable.Cols.JOB_POSITION + ", " +
                UsersTable.Cols.DATE_WON + ", " +
                UsersTable.Cols.DISCIPLER + ", " +
                UsersTable.Cols.INVITED_BY + ", " +
                UsersTable.Cols.HEALTH_STATUS + ", " +
                UsersTable.Cols.USER_NAME + ", " +
                UsersTable.Cols.PASSWORD + ", " +
                UsersTable.Cols.ROLES +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
