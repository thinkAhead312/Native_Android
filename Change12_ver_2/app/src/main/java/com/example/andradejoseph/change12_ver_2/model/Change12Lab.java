package com.example.andradejoseph.change12_ver_2.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.andradejoseph.change12_ver_2.database.C4DbBaseHelper;
import com.example.andradejoseph.change12_ver_2.database.C4DbCursorWrapper;
import com.example.andradejoseph.change12_ver_2.database.C4DbSchema;

import java.util.ArrayList;
import java.util.List;

import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.Change12_ID;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.END_DATE;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.START_DATE;
import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.Change12.Cols.WAVE_NUM;


/**
 * Created by ANDRADEJOSEPH on 4/5/2017.
 */

public class Change12Lab extends Disciple{

    private static Change12Lab sChange12Lab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static Change12Lab get(Context context) {
        if(sChange12Lab == null) {
            synchronized (DiscpleLab.class) {
                sChange12Lab = new Change12Lab(context);
            }
        }
        return sChange12Lab;
    }

    private Change12Lab(Context context) {
        mContext = context;
        mDatabase = new C4DbBaseHelper(mContext)
                .getWritableDatabase();
    }


/*                                   QUERY METHODS
//////////////////////////////////////////////////////////////////////////////////////////////////////////
                                     QUERY METHODS                                                  */


    public Change12 getChange12(String changeWaveId) {
        C4DbCursorWrapper cursor = queryChange12(
                C4DbSchema.Change12.Cols.Change12_ID + " = ?",
                new String[] {changeWaveId}
        );

        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getChange12();
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
//////////////////////////////////////////////////////////////////////////////////////////////////////////



/*                                   INSERTION METHODS
//////////////////////////////////////////////////////////////////////////////////////////////////////////
                                     INSERTION METHODS                                                  */


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


    /**
     * getContent value for insertion Changee
     * @param changee
     * @return
     */

    public static ContentValues getContentValues(Changee changee) {
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

    /**
     * getContent Value for insertion Change12 wave
     * @param change12
     * @return
     */

    public static ContentValues getContentValues(Change12 change12) {
        ContentValues values = new ContentValues();
        values.put(Change12_ID, change12.getChange12_id());
        values.put(WAVE_NUM,change12.getWave_num());
        values.put(START_DATE,change12.getStart_date());
        values.put(END_DATE,change12.getEnd_date());
        return values;
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////





}
