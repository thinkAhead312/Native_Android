package com.example.andradejoseph.change12_ver_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andradejoseph.change12_ver_2.database.C4_DB_BaseHelper;
import com.example.andradejoseph.change12_ver_2.database.C4_DbSchema;
import com.example.andradejoseph.change12_ver_2.database.CrimeCursorWrapper;
import com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;

import static com.example.andradejoseph.change12_ver_2.database.C4_DbSchema.*;
import static com.example.andradejoseph.change12_ver_2.database.C4_DbSchema.UsersTable.*;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class DiscpleLab {

    private static DiscpleLab sDiscipleLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static DiscpleLab get(Context context) {
        if(sDiscipleLab == null) {
            sDiscipleLab = new DiscpleLab(context);
        }
        return sDiscipleLab;
    }

    private DiscpleLab(Context context) {
        mContext = context;
        mDatabase = new C4_DB_BaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addDisciple(Disciple disciple) {
        ContentValues values = getContentValues(disciple);
        mDatabase.insert(UsersTable.NAME, null, values);
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
        values.put(Cols.HEALTH_STATUS,disciple.getHealth_status());
        values.put(Cols.USER_NAME,disciple.getUser_name());
        values.put(Cols.PASSWORD,disciple.getPassword());
        values.put(Cols.ROLES,disciple.getRoles());
        return values;
    }

    public List<Disciple> getDiciples() {
        List<Disciple> disciples = new ArrayList<>();
        CrimeCursorWrapper cursor  = queryDisciples(null, null);

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

    private CrimeCursorWrapper queryDisciples(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                UsersTable.NAME,
                null, //columns  = null select all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );

        return new CrimeCursorWrapper(cursor);
    }


}
