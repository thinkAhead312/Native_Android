package com.example.glenda_pc.change12_activity.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Glenda-PC on 3/5/2016.
 */
public class DatabaseAccess {

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


    private DatabaseAccess(Context context) {
        this.openHelper = new SqliteHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }

    }

    public void insertUsersTable(Disciple_Set_Get disciple) {
        ContentValues values = new ContentValues();


        values.put("users_id", disciple.getUsers_id());
        values.put("parents_id", disciple.getParents_id());
        values.put("prime_flag", Integer.valueOf(disciple.getPrime_flag()) );
        values.put("close_cell_flag", Integer.valueOf(disciple.getClose_cell_flag()) );
        values.put("consolidates_flag", Integer.valueOf(disciple.getConsolidates_flag()) );
        values.put("active", Integer.valueOf(disciple.getActive()) );
        values.put("account_verified_flag", Integer.valueOf(disciple.getAccount_verified_flag()) );
        values.put("photo_updateFlag", Integer.valueOf(disciple.getPhotoFlag()));
        database.insert("users_table_ver5", null, values);
    }

    public void insertDisciplesTable(Disciple_Set_Get disciple) {
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
        database.insert("disciples_table_ver5", null, values);

        Log.i("myDiscipleInsert", "MyClass.getView() — get item number " +disciple.getUsers_id());
    }

    public void insertRemarks(Disciple_Set_Get disciple) {
        ContentValues values = new ContentValues();

        values.put("users_id", disciple.getUsers_id());
        values.put("remarks", disciple.getRemarks());
        values.put("time_stamp", disciple.getTime_stamp());

        database.insert("disciples_testimony_table_ver5", null, values);

        String userPhotoSql1="Update disciples_table_ver5 SET remarks='"+disciple.getRemarks()+"'" +
                "WHERE users_id ='"+disciple.getUsers_id()+"' " ;
        database.execSQL(userPhotoSql1);
    }

    public void insertAffiliation (Disciple_Set_Get affiliation){
        ContentValues values = new ContentValues();
        values.put("affiliation", affiliation.getAffliation());
        database.insert("discipleAffiliation_table_ver5", null, values);
    }


    public List<Disciple_Set_Get> getAffiliation() {
        List<Disciple_Set_Get> list = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM discipleAffiliation_table_ver5", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Disciple_Set_Get contact = new Disciple_Set_Get();
            contact.setAffliation(cursor.getString(0));
            list.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
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


    public int users_disciplesTableJoin(){ //Inner Join users_table and disciple_table to Query Account_Verified_Flag, Users_ID, D-First_Name. During Start-Up

        int getCount=0;
        //Where 1
        String sqlQuery = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE 1" +
                "";
        Cursor c = database.rawQuery(sqlQuery, null);//where users_id='"+"0002"+"'
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
                sur_name = c.getString(10);
                address=c.getString(21);

            }
             getCount = c.getCount();
             c.close();
             return getCount;
        }
    }

    public void queryAllDisciples_Consolidates(){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +


                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void queryAllDisciples(){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+0+"'  " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void queryChange_ALL(){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " ORDER BY change12_status ASC " +


                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }


    public void queryCloseCellDisciples(){

        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  AND users_table_ver5.close_cell_flag='"+1+"'  " +
                " AND users_table_ver5.active='"+1+"' " +
                " AND users_table_ver5.consolidates_flag='"+0+"'  " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void queryOpenCellDisciples(){

        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  AND users_table_ver5.close_cell_flag='"+0+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+0+"'  " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }

        c.close();

    }


    public void queryChange12(String change12){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " AND disciples_table_ver5.change12_status='"+change12+"' " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void getDiscipleProfile(String users_id){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+0+"'  "+
                " AND users_table_ver5.users_id='"+users_id+"' " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }



    public void getConsolidateProfile(String users_id){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " AND users_table_ver5.users_id='"+users_id+"' " +

                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void getConsolidateRemarks(String users_id){
        totalRows=0;
        String sqlDisciples = " SELECT * "+
                " FROM  disciples_testimony_table_ver5 " +
                " WHERE users_id='"+users_id+"' " +
                " ORDER BY time_stamp DESC"+
                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'
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
            totalRows++;
            resultSet.put(rowObject);
        }
        c.close();
    }

    public void updateChangeStatus(String users_id, Disciple_Set_Get newDisciple) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());

        String userPhotoSql="Update disciples_table_ver5 SET change12_status='"+newDisciple.getChange12_stat()+"', " +
                "dateUpdated='"+formattedDate+"' " +
                "WHERE users_id ='"+users_id+"'";
        database.execSQL(userPhotoSql);

       // ContentValues values = new ContentValues();
       // values.put("change12_status", newDisciple.getChange12_stat());

      //  database.update("disciples_table_ver5", values, "change12_status=" + users_id, null);
      //  database.update("disciples_table_ver5", values, "change12_status = ?", new String[]{users_id});
    }

    public void updateConsolidatesRemarks(String users_id, Disciple_Set_Get newDisciple, String time_stamp) {
        String userPhotoSql="Update disciples_testimony_table_ver5 SET remarks='"+newDisciple.getRemarks()+"'" +
                "WHERE users_id ='"+users_id+"' " +
                "AND time_stamp ='"+time_stamp+"' ";
        database.execSQL(userPhotoSql);

        String userPhotoSql1="Update disciples_table_ver5 SET remarks='"+newDisciple.getRemarks()+"'" +
                "WHERE users_id ='"+users_id+"' " ;
        database.execSQL(userPhotoSql1);
    }

    public void updateDiscipleTestimony(String users_id, Disciple_Set_Get newDisciple, String strFlags) {

        if(strFlags.equals("cell")) {
            String userPhotoSql = "Update disciples_table_ver5 SET cell_testimony='" + newDisciple.getCell_testimony() + "'" +
                    "WHERE users_id ='" + users_id + "' ";
            database.execSQL(userPhotoSql);
        }
    }

    public void updateDiscipleStat(String users_id, Disciple_Set_Get newDisciple) {
            int closeCellFlag=0;
            if(newDisciple.getClose_cell_flag().toString().trim().equals("1")){
                closeCellFlag=0;
            }else{
                closeCellFlag=1;
            }

            String userPhotoSql = "Update users_table_ver5 SET close_cell_flag='" + closeCellFlag + "'" +
                    "WHERE users_id ='" + users_id + "'";
            database.execSQL(userPhotoSql);
    }

    public void updateDisciplePepSolStat(String users_id, Disciple_Set_Get newDisciple) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = df.format(c.getTime());
        //not in used
        String userPhotoSql = "Update disciples_table_ver5 SET pepsol_status='" + newDisciple.getPepsol_stat() + "' ," +
                "  dateUpdated='"+formattedDate+"' " +
                "WHERE users_id ='" + users_id + "'";
        database.execSQL(userPhotoSql);
    }



    public void  updateActiveStatus(String users_id){

        String userPhotoSql = "Update users_table_ver5 SET active='" + 0 + "'" +
                "WHERE users_id ='" + users_id + "'";
        database.execSQL(userPhotoSql);
    }

    public void updateConsolidatetoDisciple(String users_id){

        String userPhotoSql = "Update users_table_ver5 SET consolidates_flag='" + 0 + "'" +
                "WHERE users_id ='" + users_id + "'";
        database.execSQL(userPhotoSql);


    }

    public void updatePhoto(String users_id, String url){

        String userPhotoSql = "Update disciples_table_ver5 SET photo='" + url + "'" +
                "WHERE users_id ='" + users_id + "'";
        database.execSQL(userPhotoSql);

    }

    public void updateConsolidateInfo(String users_id, Disciple_Set_Get updateConsolidate) {

        String query="Update disciples_table_ver5 SET  first_name='"+updateConsolidate.getFirst_name()+"', last_name='"+updateConsolidate.getSur_name()+"', contact_num='"+updateConsolidate.getContact_num()+"' ,   " +
                     " birthdate='"+updateConsolidate.getBirthdate()+"', address='"+updateConsolidate.getAddress()+"',  affiliation='"+updateConsolidate.getAffliation()+"', photo='"+updateConsolidate.getPhoto()+"'    " +
                     " WHERE  users_id='"+users_id+"'  ";

        database.execSQL(query);
    }

    public void updateDiscipleInfo(String users_id, Disciple_Set_Get updateConsolidate) {

        String query="Update disciples_table_ver5 SET  first_name='"+updateConsolidate.getFirst_name()+"', last_name='"+updateConsolidate.getSur_name()+"', contact_num='"+updateConsolidate.getContact_num()+"' ,   " +
                " birthdate='"+updateConsolidate.getBirthdate()+"', address='"+updateConsolidate.getAddress()+"',  affiliation='"+updateConsolidate.getAffliation()+"', photo='"+updateConsolidate.getPhoto()+"'    " +
                " WHERE  users_id='"+users_id+"'  ";

        database.execSQL(query);
    }

    public int queryConsolidateUpdate( String dateUpdate){

        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " AND dateUpdated='"+dateUpdate+"'   " +
                " ORDER BY change12_status ASC " +


                "";
        Cursor c = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'

        return c.getCount();
    }

    public int queryThisWeekChange12Consolidated( String change12){

        String startWeekDay="", endWeekDay="";
        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        for (int i = 0; i < 7; i++) {
            //System.out.println(df.format(c.getTime()));

            if(i==0){
                startWeekDay= df.format(c.getTime());
            }
            if(i==6){
                endWeekDay= df.format(c.getTime());
            }
            c.add(Calendar.DATE, 1);
        }

        String sqlDisciples = " SELECT * "+
                " FROM  users_table_ver5 " +
                " INNER JOIN disciples_table_ver5 ON users_table_ver5.users_id = disciples_table_ver5.users_id " +
                " WHERE users_table_ver5.parents_id='"+DatabaseAccess.users_id+"'  " +
                " AND users_table_ver5.active='"+1+"'  " +
                " AND users_table_ver5.consolidates_flag='"+1+"'  " +
                " AND dateUpdated between '"+startWeekDay+"' AND '"+endWeekDay+"' " +
                " AND disciples_table_ver5.change12_status='"+change12+"' " +
                " ORDER BY change12_status ASC " +


                "";
        Cursor cursor = database.rawQuery(sqlDisciples, null);//where users_id='"+"0002"+"'

        return cursor.getCount();
    }



    public void updateProfile(Disciple_Set_Get updateConsolidate) {

        String query="Update disciples_table_ver5 SET  first_name='"+updateConsolidate.getFirst_name()+"', last_name='"+updateConsolidate.getSur_name()+"' , contact_num='"+updateConsolidate.getContact_num()+"' ,   " +
                " birthdate='"+updateConsolidate.getBirthdate()+"', address='"+updateConsolidate.getAddress()+"', photo='"+updateConsolidate.getPhoto()+"'    " +
                " WHERE  users_id='"+DatabaseAccess.users_id+"'  ";
        database.execSQL(query);
    }





}
