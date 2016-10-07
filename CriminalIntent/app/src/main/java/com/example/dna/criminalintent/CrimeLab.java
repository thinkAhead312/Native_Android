package com.example.dna.criminalintent;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.dna.criminalintent.database.CrimeBaseHelper;

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
    }

    public void deleteCrime(Crime c) {
//        mCrimes.remove(c);
    }

    public List<Crime> getCrimes() {
        return new ArrayList<>();
    }

    public Crime getCrime(UUID id) {
//        for(Crime crime : mCrimes) {
//            if (crime.getId().equals(id)) {
//                Log.i(TAG, String.valueOf(crime.getTitle()));
//                return crime;
//            }
//        }
        return  null;
    }
}
