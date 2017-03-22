package com.example.andradejoseph.change12_ver_2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andradejoseph.change12_ver_2.database.C4_DbSchema.UsersTable;
import com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema.CrimeTable;
import com.example.andradejoseph.change12_ver_2.model.Crime;
import com.example.andradejoseph.change12_ver_2.model.Disciple;

import java.util.Date;
import java.util.UUID;

import static com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema.CrimeTable.*;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class CrimeCursorWrapper extends CursorWrapper{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(Cols.UUID));
        String title = getString(getColumnIndex(Cols.TITLE));
        long date = getLong(getColumnIndex(Cols.DATE));
        int isSolved = getInt(getColumnIndex(Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return crime;
    }


    public Disciple getDisciple() {
        Disciple disciple = new Disciple();
        disciple.setFirst_name(getString(getColumnIndex(UsersTable.Cols.FIRST_NAME)).trim());

        return disciple;
    }
}
