package com.example.glenda_pc.change12_activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;

import java.text.DateFormat;
import java.util.Date;

public class Registration_Module extends AppCompatActivity {

    Disciple_Set_Get set_getDisciple = new Disciple_Set_Get();

    Button btnSkip,btnSubmit;
    EditText editTextEmail, editTextPassword;
    public String email="", password="";

    String test1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration__module);

        btnSkip = (Button) findViewById(R.id.btnSkip);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        editTextEmail= (EditText) findViewById(R.id.ed_input_EMAIL);
        editTextPassword = (EditText) findViewById(R.id.ed_input_PASSWORD);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        //Toast.makeText(Registration_Module.this, currentDateTimeString, Toast.LENGTH_SHORT).show();
        btnSkip.setVisibility(View.GONE);


    }

    public void clkSkip(View v){
        if(v.equals(btnSkip)){
           /* Intent intent = new Intent(Registration_Module.this,Change12_Activity.class);
            intent.putExtra("dipFlag",1);
            startActivity(intent);
            finish();*/

            Toast.makeText(Registration_Module.this, "Cant Proceed Without password", Toast.LENGTH_SHORT).show();
        }
    }
    public void clkSubmit(View v){

        if(v.equals(btnSubmit)){
                password = editTextPassword.getText().toString().trim();
                if(password.equals("crosstrainers")){
                      Intent intent = new Intent(Registration_Module.this,Profile_Change_Module.class);
                      intent.putExtra("dipFlag",0);
                      startActivity(intent);
                      finish();
                }
                 else{
                    snackBarCall();
                }

           //
        }
    }



    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    private void snackBarCall() {

        Snackbar.make(findViewById(R.id.reg_layout), "Enter Correct Password", Snackbar.LENGTH_LONG).setAction("Okay", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }



}

/*
    private void userLogin() {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        final ProgressDialog loading = ProgressDialog.show(this,"Loading Data", "Please wait...",false,false);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_Disciple_EMAIL_VALIDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //Toast.makeText(Registration_Module.this, response.toString(), Toast.LENGTH_SHORT).show();
                        showJSON(response, loading);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Toast.makeText(Registration_Module.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(Config.DISCIPLE_EMAIL,email);
                map.put(Config.DISCIPLE_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    private void showJSON(String response, ProgressDialog loading) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject discipleData = result.getJSONObject(0);

            set_getDisciple.setUsers_id(discipleData.getString(Config.USERS_USERS_ID));
            set_getDisciple.setParents_id(discipleData.getString(Config.USERS_PARENTS_ID));
            set_getDisciple.setPrime_flag(discipleData.getString(Config.USERS_PRIME_FLAG));
            set_getDisciple.setClose_cell_flag(discipleData.getString(Config.USERS_CLOSE_CELL_FLAG));
            set_getDisciple.setConsolidates_flag(discipleData.getString(Config.USERS_CONSOLIDATES_FLAG));
            set_getDisciple.setActive(discipleData.getString(Config.USERS_ACTIVE));
            set_getDisciple.setAccount_verified_flag(discipleData.getString(Config.USERS_ACCOUNT_VERIFIED_FLAG));

            set_getDisciple.setFirst_name(discipleData.getString(Config.DISCIPLE_FIRST_NAME));
            set_getDisciple.setSur_name(discipleData.getString(Config.DISCIPLE_SUR_NAME));
            set_getDisciple.setPhoto(discipleData.getString(Config.DISCIPLE_PHOTO));
            set_getDisciple.setContact_num(discipleData.getString(Config.DISCIPLE_CONTACT_NUM));
            set_getDisciple.setPassword(discipleData.getString(Config.DISCIPLE_PASSWORD));
            set_getDisciple.setEmail(discipleData.getString(Config.DISCIPLE_EMAIL));
            set_getDisciple.setBirthdate(discipleData.getString(Config.DISCIPLE_BIRTHDATE));
            set_getDisciple.setGender(discipleData.getString(Config.DISCIPLE_GENDER));
            set_getDisciple.setChurch_testimony(discipleData.getString(Config.DISCIPLE_CHURCH_TESTIMONY));
            set_getDisciple.setCell_testimony(discipleData.getString(Config.DISCIPLE_CELL_TESTIMONY));
            set_getDisciple.setDevotional_testimony(discipleData.getString(Config.DISCIPLE_DEVOTIONAL_TESTIMONY));
            set_getDisciple.setEncounter_testimony(discipleData.getString(Config.DISCIPLE_ENCOUNTER_TESTIMONY));
            set_getDisciple.setAddress(discipleData.getString(Config.DISCIPLE_ADDRESS));
            sql.users_id = set_getDisciple.getUsers_id(); // get the users_id from the Server


            insertSqlite(loading);

        } catch (JSONException e) {
            loading.dismiss();// dismiss Dialog Loading
            showMessage("Disciple", e.toString() + " " + "Error username or Password");
            e.printStackTrace();
        }

    }

    private void insertSqlite(ProgressDialog loading) {
      //  showMessage("Disciple", set_getDisciple.getFirst_name() + " " + set_getDisciple.getSur_name());
    try{
        int checkQuery =  sql.checkRegistration_Email_Pass(set_getDisciple.getEmail(),set_getDisciple.getPassword() );
        if(checkQuery==0){

            int prime_flag = Integer.valueOf(set_getDisciple.getPrime_flag().trim());
            int close_cell_flag = Integer.valueOf(set_getDisciple.getClose_cell_flag().trim());
            int consolidates_flag = Integer.valueOf(set_getDisciple.getConsolidates_flag().trim());
            int active = Integer.valueOf(set_getDisciple.getActive().trim());
            int account_verified_flag = Integer.valueOf(set_getDisciple.getAccount_verified_flag().trim());
            //Insert to UsersTable
            sql.insertUsersTable(set_getDisciple.getUsers_id(), set_getDisciple.getParents_id(), prime_flag, close_cell_flag, consolidates_flag, active, account_verified_flag);
            int gender = Integer.valueOf(set_getDisciple.getGender().trim());

            //Insert to Disciples Table
            sql.insertDisciplesTable(set_getDisciple.getUsers_id(), set_getDisciple.getFirst_name(), set_getDisciple.getSur_name(), set_getDisciple.getPhoto(), set_getDisciple.getContact_num(), set_getDisciple.getEmail(), set_getDisciple.getPassword(), set_getDisciple.getBirthdate(), gender, set_getDisciple.getAddress(),"PEPSOL","","",0);
            int acc_verified_flag=sql.getAccVerification(set_getDisciple.getUsers_id());
            sql.users_disciplesTableJoin();//Queryy Cell Leader Profile



            getMyDisciples();

            loading.dismiss();// dismiss Dialog Loading
            if(acc_verified_flag==0){

                Intent intent = new Intent(Registration_Module.this,Profile_Change_Module.class);
                intent.putExtra("dipFlag",set_getDisciple.getFirst_name());
                startActivity(intent);
                finish();
            }
            else if(acc_verified_flag==1){
                Intent intent = new Intent(Registration_Module.this,Change12_Activity.class);
                intent.putExtra("dipFlag",1);
                startActivity(intent);
                finish();
            }

        }

    }catch (Exception e){
        loading.dismiss();// dismiss Dialog Loading
        showMessage("Errow", e.toString());
    }


    }

    private void getMyDisciples() throws MalformedURLException {

        //Creating a json array request
        Config.URL_GET_MY_DISCIPLES+=sql.users_id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config.URL_GET_MY_DISCIPLES,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        //Toast.makeText(Registration_Module.this, "Success", Toast.LENGTH_SHORT).show();

                        insertDisciplesToSqLiteFromJson(response);//call this
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Registration_Module.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });

        //Creating request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);




    }

    private void insertDisciplesToSqLiteFromJson(JSONArray response) {
        StringBuilder buffer = new StringBuilder();
        for(int i = 0; i<response.length(); i++) {
            Disciple_Set_Get disciple_set_get = new Disciple_Set_Get();
            JSONObject discipleData = null;
            try {
                discipleData = response.getJSONObject(i);
                disciple_set_get.setUsers_id(discipleData.getString(Config.USERS_USERS_ID));
                disciple_set_get.setParents_id(discipleData.getString(Config.USERS_PARENTS_ID));
                disciple_set_get.setPrime_flag(discipleData.getString(Config.USERS_PRIME_FLAG));
                disciple_set_get.setClose_cell_flag(discipleData.getString(Config.USERS_CLOSE_CELL_FLAG));
                disciple_set_get.setConsolidates_flag(discipleData.getString(Config.USERS_CONSOLIDATES_FLAG));
                disciple_set_get.setActive(discipleData.getString(Config.USERS_ACTIVE));
                disciple_set_get.setAccount_verified_flag(discipleData.getString(Config.USERS_ACCOUNT_VERIFIED_FLAG));

                disciple_set_get.setFirst_name(discipleData.getString(Config.DISCIPLE_FIRST_NAME));
                disciple_set_get.setSur_name(discipleData.getString(Config.DISCIPLE_SUR_NAME));
                disciple_set_get.setPhoto(discipleData.getString(Config.DISCIPLE_PHOTO));
                disciple_set_get.setContact_num(discipleData.getString(Config.DISCIPLE_CONTACT_NUM));
                disciple_set_get.setPassword(discipleData.getString(Config.DISCIPLE_PASSWORD));
                disciple_set_get.setEmail(discipleData.getString(Config.DISCIPLE_EMAIL));
                disciple_set_get.setBirthdate(discipleData.getString(Config.DISCIPLE_BIRTHDATE));
                disciple_set_get.setGender(discipleData.getString(Config.DISCIPLE_GENDER));
                disciple_set_get.setChurch_testimony(discipleData.getString(Config.DISCIPLE_CHURCH_TESTIMONY));
                disciple_set_get.setCell_testimony(discipleData.getString(Config.DISCIPLE_CELL_TESTIMONY));
                disciple_set_get.setDevotional_testimony(discipleData.getString(Config.DISCIPLE_DEVOTIONAL_TESTIMONY));
                disciple_set_get.setEncounter_testimony(discipleData.getString(Config.DISCIPLE_ENCOUNTER_TESTIMONY));
                disciple_set_get.setAddress(discipleData.getString(Config.DISCIPLE_ADDRESS));

                int prime_flag = Integer.valueOf(disciple_set_get.getPrime_flag().trim());
                int close_cell_flag = Integer.valueOf(disciple_set_get.getClose_cell_flag().trim());
                int consolidates_flag = Integer.valueOf(disciple_set_get.getConsolidates_flag().trim());
                int active = Integer.valueOf(disciple_set_get.getActive().trim());
                int account_verified_flag = Integer.valueOf(disciple_set_get.getAccount_verified_flag().trim());

                sql.insertUsersTable(disciple_set_get.getUsers_id(), disciple_set_get.getParents_id(), prime_flag, close_cell_flag, consolidates_flag, active, account_verified_flag);

                int gender = Integer.valueOf(disciple_set_get.getGender().trim());

                //Insert to Disciples Table
                sql.insertDisciplesTable(disciple_set_get.getUsers_id(), disciple_set_get.getFirst_name(), disciple_set_get.getSur_name(), disciple_set_get.getPhoto(), disciple_set_get.getContact_num(), disciple_set_get.getEmail(), disciple_set_get.getPassword(), disciple_set_get.getBirthdate(), gender, disciple_set_get.getAddress(),"PEPSOL","","",0);
                sql.cellGroupDisciplesTableJoin();
                buffer.append(set_getDisciple.getUsers_id() + '\n');
                buffer.append(set_getDisciple.getAccount_verified_flag() + '\n');
                buffer.append(set_getDisciple.getFirst_name() + '\n');
                buffer.append(set_getDisciple.getPhoto() + '\n');
                buffer.append(set_getDisciple.getBirthdate() + '\n');
                buffer.append(set_getDisciple.getGender() + '\n');
                buffer.append(set_getDisciple.getSur_name() + '\n');


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //Toast.makeText(Registration_Module.this, response.toString(), Toast.LENGTH_LONG).show();

    }
   */

/* buffer.append("users_id: " + c_Users.getString(0) + "\n");
buffer.append("parents_id: " + c_Users.getString(1) + "\n");
buffer.append("prime_flag: " + c_Users.getString(2) + "\n");
buffer.append("close_cell_flag: " + c_Users.getString(3) + "\n");
buffer.append("consolidates_flag: " + c_Users.getString(4) + "\n");
buffer.append("active: " + c_Users.getString(5) + "\n");
buffer.append("acc_verified_flag: " + c_Users.getString(6) + "\n");*/
