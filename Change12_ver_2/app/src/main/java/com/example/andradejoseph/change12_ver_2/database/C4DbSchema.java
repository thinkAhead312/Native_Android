package com.example.andradejoseph.change12_ver_2.database;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class C4DbSchema {

    public static final class UsersTable {
        public static String NAME = "users";

        public static final class Cols {
            public static final String DISCIPLE_ID = "disciple_id";
            public static final String SLUG = "slug";
            public static final String FIRST_NAME = "first_name";
            public static final String MIDDLE_NAME = "middle_name";
            public static final String LAST_NAME = "last_name";
            public static final String FULL_NAME = "full_name";
            public static final String NICK_NAME = "nick_name";
            public static final String GENDER = "gender";
            public static final String BIRTH_DATE = "birth_date";
            public static final String NATIONALITY = "nationality";
            public static final String HOME_ADDRESS = "home_address";
            public static final String CITY_ADDRESS = "city_address";
            public static final String CONTACT_NUMBER = "contact_number";
            public static final String EMAIL_ADDRESS = "email_address";
            public static final String SCHOOL = "school";
            public static final String DEGREE = "degree";
            public static final String MARITAL_STATUS = "marital_status";
            public static final String COMPANY = "company";
            public static final String JOB_POSITION = "job_position";
            public static final String DATE_WON = "date_won";
            public static final String DISCIPLER = "discipler";
            public static final String INVITED_BY = "invited_by";
            public static final String HEALTH_STATUS = "health_status";
            public static final String USER_NAME = "username";
            public static final String PASSWORD = "password";
            public static final String ROLES = "roles";
        }
    }

   public static final class Change12 {
       public static String NAME = "change12";

       public static final class Cols {
           public static final String Change12_ID = "id";
           public static final String WAVE_NUM = "wave_num";
           public static final String START_DATE = "start_date";
           public static final String END_DATE = "end_date";
       }
   }


    public static final class Changee {
        public static String NAME = "changees";

        public static final class Cols {
            public static final String CHANGE_12 = "change_12";
            public static final String CHANGEE = "changee";
            public static final String CHANGE_1_OK = "change_1_ok";
            public static final String CHANGE_1_DATE = "change_1_date";
            public static final String CHANGE_2_OK = "change_2_ok";
            public static final String CHANGE_2_DATE = "change_2_date";
            public static final String CHANGE_3_OK = "change_3_ok";
            public static final String CHANGE_3_DATE = "change_3_date";
            public static final String CHANGE_4_OK = "change_4_ok";
            public static final String CHANGE_4_DATE = "change_4_date";
            public static final String CHANGE_5_OK = "change_5_ok";
            public static final String CHANGE_5_DATE = "change_5_date";
        }
    }

}
