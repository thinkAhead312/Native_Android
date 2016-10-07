package com.example.dna.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.example.dna.criminalintent.database.CrimeBaseHelper;
import com.example.dna.criminalintent.database.CrimeCursorWrapper;
import com.example.dna.criminalintent.database.CrimeDbSchema.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dna on 9/27/16.
 */
public class CrimeLab {
    private String TAG = "CrimeLab";
    private static CrimeLab sCrimeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

//    private List<Crime> mCrimes;

    public  static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return  sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
//        mCrimes = new ArrayList<>();
//        for (int i = 0; i< 100; i++) {
//            Crime crime = new Crime();
//            crime.setTitle("Crime #" + i);
//            crime.setSolved(i % 2 == 0);
//            crime.setDate(calendar.getTime());
//            mCrimes.add(crime);
//        }
    }

    public void addCrime(Crime c) {
//        mCrimes.add(c);

        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteCrime(Crime crime) {
//        mCrimes.remove(c);
        String uuidString = crime.getId().toString();

        ContentValues values = getContentValues(crime);

        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " = ?",
                new String[] {uuidString});
    }

    public List<Crime> getCrimes() {
//        return new ArrayList<>();
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return crimes;
    }

    public Crime getCrime(UUID id) {
//        for(Crime crime : mCrimes) {
//            if (crime.getId().equals(id)) {
//                Log.i(TAG, String.valueOf(crime.getTitle()));
//                return crime;
//            }
//        }
         CrimeCursorWrapper cursor = queryCrimes(
                 CrimeTable.Cols.UUID + " = ?",
                 new String[] {id.toString()}
         );
        try {
            if(cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }
        finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SOLVED, crime.getSuspect());
        return values;
    }

//    private Cursor queryCrimes(String whereClause, String[] whereArgs) {
//        Cursor cursor = mDatabase.query(
//                CrimeTable.NAME,
//                null, //Columns - null selects all columns,
//                whereClause,
//                whereArgs,
//                null, //groupby
//                null, //having
//                null //orderby
//        );
//
//        return cursor;
//    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                CrimeTable.NAME,
                null, //Columns - null selects all columns
                whereClause,
                whereArgs,
                null, //groupby
                null, //having
                null //orderby
        );
        return new CrimeCursorWrapper(cursor);
    }


    public File getPhotoFile(Crime crime) {
        File externalFilesDir = mContext
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, crime.getPhotoFilename());
    }

}
