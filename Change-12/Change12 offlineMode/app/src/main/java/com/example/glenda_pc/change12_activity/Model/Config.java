package com.example.glenda_pc.change12_activity.Model;

/**
 * Created by Glenda-PC on 12/15/2015.
 */
public class Config {

    public static final String URL_Disciple_EMAIL_VALIDATE = "http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/log_in.php";
    public static final String Url_Users_ACC_VERIFIED="http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/acc_verify.php";
    public static final String URL_ADD_DISCIPLE="http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/add_disciples.php";
    public static String URL_GET_MY_DISCIPLES="http://192.168.254.181:8081/Change12/Change12_manual/mysql_QUERY/my_disciples.php?parents_id=";

    //DISCIPLES TABLE
    public static final String DISCIPLE_USERS_ID = "users_id";
    public static final String DISCIPLE_FIRST_NAME = "first_name";
    public static final String DISCIPLE_SUR_NAME = "sur_name";
    public static final String DISCIPLE_PHOTO = "photo";
    public static final String DISCIPLE_CONTACT_NUM = "contact_num";
    public static final String DISCIPLE_PASSWORD = "password";
    public static final String DISCIPLE_EMAIL = "email";
    public static final String DISCIPLE_BIRTHDATE = "birthdate";
    public static final String DISCIPLE_GENDER = "gender";
    public static final String DISCIPLE_CHURCH_TESTIMONY = "church_testimony";
    public static final String DISCIPLE_CELL_TESTIMONY = "cell_testimony";
    public static final String DISCIPLE_DEVOTIONAL_TESTIMONY = "devotional_testimony";
    public static final String DISCIPLE_ENCOUNTER_TESTIMONY = "encounter_testimony";
    public static final String DISCIPLE_ADDRESS = "address";

    //USERS TABLE
    public static final String USERS_USERS_ID = "users_id";
    public static final String USERS_PARENTS_ID= "parents_id";
    public static final String USERS_PRIME_FLAG = "prime_flag";
    public static final String USERS_CLOSE_CELL_FLAG = "close_cell_flag";
    public static final String USERS_CONSOLIDATES_FLAG = "consolidates_flag";
    public static final String USERS_ACTIVE = "active";
    public static final String USERS_ACCOUNT_VERIFIED_FLAG = "account_verified_flag";

    //CONSLIDATES TABLE
    public static final String CONSLIDATES_USERS_ID = "users_id";
    public static final String CONSLIDATES_CHANGE12_STATUS = "change12_status";
    public static final String CONSLIDATES_WIN_AT= "win_at";
    public static final String CONSLIDATES_REMARKS = "remarks";
    public static final String JSON_ARRAY = "result";


    //JSON for SQLITE
    public static final String SqLiteDISCIPLE_LAST_NAME = "last_name";



}
