package com.example.glenda_pc.change12_activity.Database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Glenda-PC on 2/27/2016.
 */
public class SqliteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MY_DB_NAME";

    private SqliteHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    public static JSONArray resultSet;
    public static int totalRows;
    public static String users_id="", parents_id="",
            prime_flag="",close_cell_flag="",
            consolidates_flag="", active="",account_verified_flag="", photoUpdateFlag="",
            first_name="", sur_name="",
            photo="", contact_num="",
            password="",email,birthdate="", gender="",
            church_testimony="", cell_testimony="",
            devotional_testimony="", encounter_testimony="", address="", pepsolStat="" ,change12_stat="", remarks="", graduate_flag="0", affiliation="";

    String[] arrayAffiliation={"University of San Carlos","University of the Philippines Cebu College","St. Theresa’s College","University of Cebu","University of Cebu-METC","University of Southern Philippines","University of San Jose-Recoletos",
            "University of the Visayas","Gullas Medical Center","Velez College","Cebu Doctor’s University","Cebu Normal University","Southwestern University","Cebu Institute of Technology","Asian College of Technology","AMA",
            "St. Paul College Foundation","Cebu Technological University","Center for Industrial Technology and Enterprise","Colegio de la Inmaculada Concepcion","College of Technological Sciences","Don Bosco Technology Center","Interface Computer College, Inc.","Josrika Computer Training Center, Inc.",
            "Salazar Institute of Technology, Inc.","STI College Cebu","STI College - Punta Princesa","Seminario Mayor de San Carlos","Royal Oaks International School - Cebu City","Cebu Sacred Heart College","Cebu City Medical Center College of Nursing","Cebu Eastern College","Cebu International School","Sacred Heart School of the Society of Jesus",
            "Woodridge School", "B.R.I.G.H.T. Academy","Bethany Christian School","St. Francis of Assisi School","Cebu City National Science High School","PAREF Southcrest","Children’s Paradise Montessori School","Benedicto College","De La Salle Andres Soriano Memorial College","St. Scholastica's Academy","Abraham's Children Montessori","Colegio de San Antonio de Padua",
            "Child Learning Foundation (CLF)","Exceptional Children's Educational Center","ETHOS language school"};



    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE IF NOT EXISTS users_table_ver5( users_id VARCHAR NOT NULL, parents_id VARCHAR NOT NULL, prime_flag INTEGER NOT NULL, close_cell_flag INTEGER NOT NULL, consolidates_flag INTEGER NOT NULL, active INTEGER NOT NULL, account_verified_flag INTEGER NOT NULL,photo_updateFlag INTEGER NOT NULL );"); //create table if not Exist
         db.execSQL("CREATE TABLE IF NOT EXISTS disciples_table_ver5( users_id VARCHAR NOT NULL, first_name VARCHAR NOT NULL, last_name VARCHAR NOT NULL,photo VARCHAR NULL, contact_num VARCHAR NULL, email_address VARCHAR NOT NULL, password VARCHAR NOT NULL, birthdate DATE  NULL,gender INTEGER NOT NULL, church_testimony TEXT  NULL, cell_testimony TEXT  NULL, devotional_testimony TEXT  NULL, encounter_testimony TEXT NULL, address TEXT  NULL ,pepsol_status VARCHAR  NULL, change12_status VARCHAR NULL,  remarks VARCHAR NULL, graduate_flag INTEGER NULL, affiliation VARCHAR NULL, dateUpdated DATE NULL);");
         //db.execSQL("CREATE TABKE UF NOT EXISTS discipleAffiliation_table_ver5   ");
         db.execSQL("CREATE TABLE IF NOT EXISTS discipleAffiliation_table_ver5( affiliation VARCHAR NOT NULL );"); //cre
         db.execSQL("CREATE TABLE IF NOT EXISTS disciples_testimony_table_ver5( users_id VARCHAR NOT NULL,remarks VARCHAR NULL, time_stamp DATETIME NULL );"); //cre
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS BOOK");
        // create fresh books table
        this.onCreate(db);
    }


    public void insertUsersTable(SQLiteDatabase db,Disciple_Set_Get disciple) {
        ContentValues values = new ContentValues();


        values.put("users_id", disciple.getUsers_id());
        values.put("parents_id", disciple.getParents_id());
        values.put("prime_flag", Integer.valueOf(disciple.getPrime_flag()) );
        values.put("close_cell_flag", Integer.valueOf(disciple.getClose_cell_flag()) );
        values.put("consolidates_flag", Integer.valueOf(disciple.getConsolidates_flag()) );
        values.put("active", Integer.valueOf(disciple.getActive()) );
        values.put("account_verified_flag", Integer.valueOf(disciple.getAccount_verified_flag()) );
        values.put("photo_updateFlag", Integer.valueOf(disciple.getPhotoFlag()));
        db.insert("users_table_ver5", null, values);
    }

    public void insertDisciplesTable(SQLiteDatabase db, Disciple_Set_Get disciple) {
        ContentValues values = new ContentValues();

        values.put("users_id", disciple.getUsers_id());
        values.put("first_name", disciple.getFirst_name());
        values.put("last_name", disciple.getSur_name());
        values.put("photo", disciple.getPhoto());
        values.put("contact_num", disciple.getContact_num());
        values.put("email_address", disciple.getEmail());
        values.put("password", disciple.getPassword());
        values.put("birthdate", disciple.getBirthdate());
        values.put("gender",Integer.valueOf(disciple.getGender()));
        values.put("address", disciple.getAddress());
        values.put("pepsol_status", disciple.getPepsol_stat());
        values.put("change12_status", disciple.getChange12_stat());
        values.put("remarks", disciple.getRemarks());
        values.put("graduate_flag", disciple.getGraduate_flag());
        values.put("affiliation", disciple.getAffliation());
        values.put("dateUpdated", disciple.getDateUpdated());
        db.insert("disciples_table_ver5", null, values);
    }

      /* Users Table & Disciples Table: Cursor Number

 users_table_ver5.users_id=0                    disciples_table_ver5.users_id=8   church_testimony=17
                parents_id=1                    first_name=9                      cell_testimony = 18
                prime_flag=2                    sur_name=10                        devotional_testimony=19
                close_cell_flag=3               photo=11                          encounter_testimony=20
                consolidates_flag=4             contact_num=12                    address=21
                active=5                        password=14                       pepsol=22
                account_verified_flag=6         email =13                         change12_stat=23
                photo_updateFlag =7             birthdate=15                      remarks=24
                                                gender=16                         graduate_flag=25
                                                                                  affiliation=26
         */


    public int users_disciplesTableJoin(SQLiteDatabase db){ //Inner Join users_table and disciple_table to Query Account_Verified_Flag, Users_ID, D-First_Name. During Start-Up

        int getCount=0;
        //Where 1
        String sqlQuery = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE 1" +
                "";
        Cursor c = db.rawQuery(sqlQuery, null);//where users_id='"+"0002"+"'
        if(c.getCount()==0) {
            getCount = c.getCount();
            c.close();
            return getCount;
        }
        else{
            if(c.moveToFirst()){

                users_id= c.getString(0);
                account_verified_flag=c.getString(6);
                first_name= c.getString(9);
                photo = c.getString(11);
                birthdate= c.getString(15);
                gender = c.getString(16);
                photoUpdateFlag = c.getString(7);
                pepsolStat = c.getString(22);
                contact_num = c.getString(12);
                sur_name=c.getString(10);
                address=c.getString(21);
            }
            getCount = c.getCount();
            c.close();
            return getCount;
        }
    }


    public void insertRemarks(SQLiteDatabase db, Disciple_Set_Get disciple) {
        ContentValues values = new ContentValues();

        values.put("users_id", disciple.getUsers_id());
        values.put("remarks", disciple.getRemarks());
        values.put("time_stamp", disciple.getTime_stamp());

        db.insert("disciples_testimony_table_ver5", null, values);

        String userPhotoSql1="Update disciples_table_ver5 SET remarks='"+disciple.getRemarks()+"'" +
                "WHERE users_id ='"+disciple.getUsers_id()+"' " ;
        db.execSQL(userPhotoSql1);
    }

    public static JSONArray resultChange1 = new JSONArray();
    public static JSONArray resultChange2 = new JSONArray();
    public static JSONArray resultChange3 = new JSONArray();
    public static JSONArray resultChange4 = new JSONArray();
    public static JSONArray resultChange5 = new JSONArray();
    public void queryChange12(SQLiteDatabase db_Change1, String change12){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " AND disciples_table_ver5.change12_status='"+change12+"' " +

                "";
        Cursor c = db_Change1.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
        resultSet 	= new JSONArray();
        if(c.getCount()==0){
            // Toast.makeText(MainActivity.this, "Sorry", Toast.LENGTH_SHORT).show();
        }

        while(c.moveToNext()){
            int totalColumn = c.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for(int i=0; i<totalColumn;i ++){
                if(c.getColumnName(i)!=null){
                    try{
                        if(c.getString(i)!=null){
                            rowObject.put(c.getColumnName(i), c.getString(i));
                        }else{
                            rowObject.put(c.getColumnName(i),"");
                        }

                    }catch (Exception e){

                    }
                }
            }

            if(change12.equals("Change 1")) {
                totalRows++;
                resultChange1.put(rowObject);
            }
            if(change12.equals("Change 2")) {
                totalRows++;
                resultChange2.put(rowObject);
            }
            if(change12.equals("Change 3")) {
                totalRows++;
                resultChange3.put(rowObject);
            }
            if(change12.equals("Change 4")) {
                totalRows++;
                resultChange4.put(rowObject);
            }
            if(change12.equals("Change 5")) {
                totalRows++;
                resultChange5.put(rowObject);
            }


        }
        c.close();

    }
    // db.execSQL("CREATE TABLE IF NOT EXISTS users_table_ver5( users_id VARCHAR NOT NULL, parents_id VARCHAR NOT NULL, prime_flag INTEGER NOT NULL, close_cell_flag INTEGER NOT NULL, consolidates_flag INTEGER NOT NULL, active INTEGER NOT NULL, account_verified_flag INTEGER NOT NULL,photo_updateFlag INTEGER NOT NULL );"); //create table if not Exist
        //db.execSQL("CREATE TABLE IF NOT EXISTS disciples_table_ver5( users_id VARCHAR NOT NULL, first_name VARCHAR NOT NULL, last_name VARCHAR NOT NULL,photo VARCHAR NULL, contact_num VARCHAR NULL, email_address VARCHAR NOT NULL, password VARCHAR NOT NULL, birthdate DATE  NULL,gender INTEGER NOT NULL, church_testimony TEXT  NULL, cell_testimony TEXT  NULL, devotional_testimony TEXT  NULL, encounter_testimony TEXT NULL, address TEXT  NULL ,pepsol_status VARCHAR  NULL, change12_status VARCHAR NULL,  remarks VARCHAR NULL, graduate_flag INTEGER NULL);");

}
