package com.example.andradejoseph.change12_ver_2.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andradejoseph.change12_ver_2.database.C4DbSchema.UsersTable;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Crime;
import com.example.andradejoseph.change12_ver_2.model.Disciple;

import java.util.Date;
import java.util.UUID;

import static com.example.andradejoseph.change12_ver_2.database.C4DbSchema.*;
import static com.example.andradejoseph.change12_ver_2.database.CrimeDbSchema.CrimeTable.*;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class C4DbCursorWrapper extends CursorWrapper{
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public C4DbCursorWrapper(Cursor cursor) {
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


    public Change12 getChange12() {
        Change12 change12 = new Change12();
        change12.setChange12_id(getString(getColumnIndex(C4DbSchema.Change12.Cols.Change12_ID)).trim());
        change12.setWave_num(getString(getColumnIndex(C4DbSchema.Change12.Cols.WAVE_NUM)).trim());
        change12.setStart_date(getString(getColumnIndex(C4DbSchema.Change12.Cols.START_DATE)).trim());
        change12.setEnd_date(getString(getColumnIndex(C4DbSchema.Change12.Cols.END_DATE)).trim());
        return  change12;
    }

    public Disciple getDisciple() {
        Disciple disciple = new Disciple();

        disciple.setDisciple_id(getString(getColumnIndex(UsersTable.Cols.DISCIPLE_ID)).trim());
        disciple.setSlug(getString(getColumnIndex(UsersTable.Cols.SLUG)).trim());
        disciple.setFirst_name(getString(getColumnIndex(UsersTable.Cols.FIRST_NAME)).trim());
        disciple.setMiddle_name(getString(getColumnIndex(UsersTable.Cols.MIDDLE_NAME)).trim());
        disciple.setLast_name(getString(getColumnIndex(UsersTable.Cols.LAST_NAME)).trim());
        disciple.setFull_name(getString(getColumnIndex(UsersTable.Cols.FULL_NAME)).trim());
        disciple.setNick_name(getString(getColumnIndex(UsersTable.Cols.NICK_NAME)).trim());
        disciple.setGender(getString(getColumnIndex(UsersTable.Cols.GENDER)).trim());
        disciple.setBirth_date(getString(getColumnIndex(UsersTable.Cols.BIRTH_DATE)).trim());
        disciple.setNationality(getString(getColumnIndex(UsersTable.Cols.NATIONALITY)).trim());
        disciple.setHome_address(getString(getColumnIndex(UsersTable.Cols.HOME_ADDRESS)).trim());
        disciple.setCity_address(getString(getColumnIndex(UsersTable.Cols.CITY_ADDRESS)).trim());
        disciple.setContact_number(getString(getColumnIndex(UsersTable.Cols.CONTACT_NUMBER)).trim());
        disciple.setEmail_address(getString(getColumnIndex(UsersTable.Cols.EMAIL_ADDRESS)).trim());
        disciple.setSchool(getString(getColumnIndex(UsersTable.Cols.SCHOOL)).trim());
        disciple.setDegree(getString(getColumnIndex(UsersTable.Cols.DEGREE)).trim());
        disciple.setMarital_status(getString(getColumnIndex(UsersTable.Cols.MARITAL_STATUS)).trim());
        disciple.setCompany(getString(getColumnIndex(UsersTable.Cols.COMPANY)).trim());
        disciple.setJob_position(getString(getColumnIndex(UsersTable.Cols.JOB_POSITION)).trim());
        disciple.setDate_won(getString(getColumnIndex(UsersTable.Cols.DATE_WON)).trim());
        disciple.setDiscipler(getString(getColumnIndex(String.valueOf(UsersTable.Cols.DISCIPLER))).trim());
        disciple.setInvited_by(getString(getColumnIndex(String.valueOf(UsersTable.Cols.INVITED_BY))).trim());
        disciple.setHealth_status(getString(getColumnIndex(UsersTable.Cols.HEALTH_STATUS)).trim());
        disciple.setUser_name(getString(getColumnIndex(UsersTable.Cols.USER_NAME)).trim());
        disciple.setPassword(getString(getColumnIndex(UsersTable.Cols.PASSWORD)).trim());
        disciple.setRoles(getString(getColumnIndex(UsersTable.Cols.ROLES)).trim());

        return disciple;
    }
}
