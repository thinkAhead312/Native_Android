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



    public void addDisciple(Disciple disciple) {
        ContentValues values = getContentValues(disciple);
        mDatabase.insert(UsersTable.NAME, null, values);
    }





    public static ContentValues getContentValues(Disciple disciple) {
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



    public Disciple getDisciple(String discipleId) {
        C4DbCursorWrapper cursor = queryDisciples(
                Cols.DISCIPLE_ID + " = ?",
                new String[] {discipleId}
        );

        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getDisciple();
        } finally {
            cursor.close();
        }
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
