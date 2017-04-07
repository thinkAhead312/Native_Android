package com.example.andradejoseph.simpleregistration.database;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class UserDbSchema {

    public static final class UserTable {
        public static String NAME = "users";

        public static final class Cols {
            public static final String USER_ID = "user_id";
            public static final String USER_NAME = "username";
            public static final String PASSWORD = "password";

        }
    }
}
