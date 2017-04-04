package com.example.andradejoseph.change12_ver_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andradejoseph.change12_ver_2.database.C4DbBaseHelper;
import com.example.andradejoseph.change12_ver_2.database.C4DbCursorWrapper;
import com.example.andradejoseph.change12_ver_2.database.C4DbSchema;
import com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.*;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.Change12_ID;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.END_DATE;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.START_DATE;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.WAVE_NUM;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.UsersTable.*;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class DiscpleLab {

    private static DiscpleLab sDiscipleLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DiscpleLab get(Context context) {
        if(sDiscipleLab == null) {
            synchronized (DiscpleLab.class) {
                sDiscipleLab = new DiscpleLab(context);
            }
        }
        return sDiscipleLab;
    }

    private DiscpleLab(Context context) {
        mContext = context;
        mDatabase = new C4DbBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addChangee(Changee changee) {
        ContentValues values = getContentValues(changee);
        Log.d("DiscipleLab", changee.getChange_12());
        mDatabase.insert(C4DbSchema.Changee.NAME, null, values);
    }


    public void addChange12(Change12 change12) {
        ContentValues values = getContentValues(change12);
        Log.d("DiscipleLab", change12.getChange12_id());
        mDatabase.insert(C4DbSchema.Change12.NAME, null, values);
    }


    public void addDisciple(Disciple disciple) {
        ContentValues values = getContentValues(disciple);
        mDatabase.insert(UsersTable.NAME, null, values);
    }


    private static ContentValues getContentValues(Changee changee) {
        ContentValues values = new ContentValues();
        values.put(C4DbSchema.Changee.Cols.CHANGE_12, changee.getChange_12());
        values.put(C4DbSchema.Changee.Cols.CHANGEE, changee.getChangee());
        values.put(C4DbSchema.Changee.Cols.CHANGE_1_OK, changee.getChange_1_ok());
        values.put(C4DbSchema.Changee.Cols.CHANGE_1_DATE, changee.getChange_1_date());
        values.put(C4DbSchema.Changee.Cols.CHANGE_2_OK, changee.getChange_2_ok());
        values.put(C4DbSchema.Changee.Cols.CHANGE_2_DATE, changee.getChange_2_date());
        values.put(C4DbSchema.Changee.Cols.CHANGE_3_OK, changee.getChange_3_ok());
        values.put(C4DbSchema.Changee.Cols.CHANGE_3_DATE, changee.getChange_3_date());
        values.put(C4DbSchema.Changee.Cols.CHANGE_4_OK, changee.getChange_4_ok());
        values.put(C4DbSchema.Changee.Cols.CHANGE_4_DATE, changee.getChange_4_date());
        values.put(C4DbSchema.Changee.Cols.CHANGE_5_OK, changee.getChange_5_ok());
        values.put(C4DbSchema.Changee.Cols.CHANGE_5_DATE, changee.getChange_5_date());

        return values;
    }


    private static ContentValues getContentValues(Change12 change12) {
        ContentValues values = new ContentValues();
        values.put(Change12_ID, change12.getChange12_id());
        values.put(WAVE_NUM,change12.getWave_num());
        values.put(START_DATE,change12.getStart_date());
        values.put(END_DATE,change12.getEnd_date());
        return values;
    }


    private static ContentValues getContentValues(Disciple disciple) {
        ContentValues values = new ContentValues();

        values.put(Cols.DISCIPLE_ID, disciple.getDisciple_id());
        values.put(Cols.SLUG,disciple.getSlug());
        values.put(Cols.FIRST_NAME,disciple.getFirst_name());
        values.put(Cols.MIDDLE_NAME,disciple.getMiddle_name());
        values.put(Cols.LAST_NAME,disciple.getLast_name());
        values.put(Cols.FULL_NAME,disciple.getFull_name());
        values.put(Cols.NICK_NAME,disciple.getNick_name());
        values.put(Cols.GENDER,disciple.getGender());
        values.put(Cols.BIRTH_DATE,disciple.getBirth_date());
        values.put(Cols.NATIONALITY,disciple.getNationality());
        values.put(Cols.HOME_ADDRESS,disciple.getHome_address());
        values.put(Cols.CITY_ADDRESS,disciple.getCity_address());
        values.put(Cols.CONTACT_NUMBER,disciple.getContact_number());
        values.put(Cols.EMAIL_ADDRESS,disciple.getEmail_address());
        values.put(Cols.SCHOOL,disciple.getSchool());
        values.put(Cols.DEGREE,disciple.getDegree());
        values.put(Cols.MARITAL_STATUS,disciple.getMarital_status());
        values.put(Cols.COMPANY,disciple.getCompany());
        values.put(Cols.JOB_POSITION,disciple.getJob_position());
        values.put(Cols.DATE_WON,disciple.getDate_won());
        values.put(Cols.DISCIPLER,disciple.getDiscipler());
        values.put(Cols.INVITED_BY,disciple.getInvited_by());
        values.put(Cols.HEALTH_STATUS,disciple.getHealth_status());
        values.put(Cols.USER_NAME,disciple.getUser_name());
        values.put(Cols.PASSWORD,disciple.getPassword());
        values.put(Cols.ROLES,disciple.getRoles());
        return values;
    }


    public Boolean getRemainedActiveConsolidates(String discipleId) {
        C4DbCursorWrapper cursor = queryDisciples(
                Cols.DISCIPLE_ID + "= ? AND " + Cols.HEALTH_STATUS + "!=?",
                new String[] {discipleId, "HEALTH_INACTIVE"}
        );

        try {
            if(cursor.getCount() == 0) {
                return false;
            }
            cursor.moveToFirst();
            return true;
        } finally {
            cursor.close();
        }
    }



    public List<Changee> getWaveChangee(String waveNum) {
        List<Changee> changees = new ArrayList<>();
        String [] whereArgs = {waveNum};
        C4DbCursorWrapper cursor = queryWaveChangee(C4DbSchema.Changee.Cols.CHANGE_12 + "=?", whereArgs);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                changees.add(cursor.getChangee());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return changees;
    }


    public List<Changee> getChangee() {
        List<Changee> changees = new ArrayList<>();
        C4DbCursorWrapper cursor = queryChangee(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                changees.add(cursor.getChangee());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return changees;
    }

    private C4DbCursorWrapper queryChangee(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                C4DbSchema.Changee.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );

        return new C4DbCursorWrapper(cursor);
    }

    private C4DbCursorWrapper queryWaveChangee(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                C4DbSchema.Changee.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );
        return new C4DbCursorWrapper(cursor);
    }

    public List<Change12> getChange12() {
        List<Change12> change12s = new ArrayList<>();
        C4DbCursorWrapper cursor = queryChange12(null, null);
        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                change12s.add(cursor.getChange12());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return change12s;
    }

    private C4DbCursorWrapper queryChange12(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                C4DbSchema.Change12.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );

        return new C4DbCursorWrapper(cursor);
    }


    public List<Disciple> getDiciples() {
        List<Disciple> disciples = new ArrayList<>();
        C4DbCursorWrapper cursor  = queryDisciples(null, null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                disciples.add(cursor.getDisciple());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return disciples;
    }

    private C4DbCursorWrapper queryDisciples(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                UsersTable.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );

        return new C4DbCursorWrapper(cursor);
    }



}
