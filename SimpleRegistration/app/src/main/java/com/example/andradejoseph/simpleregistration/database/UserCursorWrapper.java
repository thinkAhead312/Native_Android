package com.example.andradejoseph.simpleregistration.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andradejoseph.simpleregistration.database.UserDbSchema.UserTable.Cols;
import com.example.andradejoseph.simpleregistration.model.User;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class UserCursorWrapper  extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    public User getUser() {
        User user = new User();
        user.setUser_name(getString(getColumnIndex(Cols.USER_NAME)).trim());
        user.setPassword(getString(getColumnIndex(Cols.PASSWORD)).trim());
        return user;
    }
}
