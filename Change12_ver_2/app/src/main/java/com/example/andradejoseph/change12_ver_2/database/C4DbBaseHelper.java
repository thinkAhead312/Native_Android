package com.example.andradejoseph.change12_ver_2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.andradejoseph.change12_ver_2.database.C4DbSchema.UsersTable;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class C4DbBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "c4_db.db";


    public C4DbBaseHelper(Context context) {
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

        db.execSQL("create table " + C4DbSchema.Change12.NAME + "(" +
                " _id integer primary key autoincrement, " +
                C4DbSchema.Change12.Cols.Change12_ID + ", " +
                C4DbSchema.Change12.Cols.WAVE_NUM + ", " +
                C4DbSchema.Change12.Cols.START_DATE + ", " +
                C4DbSchema.Change12.Cols.END_DATE +
                ")"
        );


        db.execSQL("create table " + C4DbSchema.Changee.NAME + "(" +
                " _id integer primary key autoincrement, " +
                C4DbSchema.Changee.Cols.CHANGE_12 + ", " +
                C4DbSchema.Changee.Cols.CHANGEE + ", " +
                C4DbSchema.Changee.Cols.CHANGE_1_OK + ", " +
                C4DbSchema.Changee.Cols.CHANGE_1_DATE + ", " +
                C4DbSchema.Changee.Cols.CHANGE_2_OK + ", " +
                C4DbSchema.Changee.Cols.CHANGE_2_DATE + ", " +
                C4DbSchema.Changee.Cols.CHANGE_3_OK + ", " +
                C4DbSchema.Changee.Cols.CHANGE_3_DATE + ", " +
                C4DbSchema.Changee.Cols.CHANGE_4_OK + ", " +
                C4DbSchema.Changee.Cols.CHANGE_4_DATE + ", " +
                C4DbSchema.Changee.Cols.CHANGE_5_OK + ", " +
                C4DbSchema.Changee.Cols.CHANGE_5_DATE +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
