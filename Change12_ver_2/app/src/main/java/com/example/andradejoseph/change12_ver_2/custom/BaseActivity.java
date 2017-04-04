package com.example.andradejoseph.change12_ver_2.custom;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.constants.Change12Api;
import com.example.andradejoseph.change12_ver_2.constants.Constants;
import com.example.andradejoseph.change12_ver_2.database.C4DbSchema;
import com.example.andradejoseph.change12_ver_2.database.C4DbSchema.UsersTable;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.sessions.SessionManager;
import com.example.andradejoseph.change12_ver_2.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ANDRADEJOSEPH on 3/21/2017.
 */

public class BaseActivity extends AppCompatActivity{

    public ProgressDialog progressDoalog;
    private SessionManager session;

    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change12 Manual");
    }

    public void fetchDB() {
        session = new SessionManager(getApplicationContext());
        fetchUserDB();
    }

    private void fetchUserDB() {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Change12Api.USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean(Constants.KEY_ERROR);
                    if (!error) {
                        //Getting Json Array Node
                        JSONArray disciples  = jsonObject.getJSONArray(Constants.JSON_USERS);
                        for(int i = 0; i < disciples.length(); i++) {
                            JSONObject jsonDisciple = disciples.getJSONObject(i);
                            Disciple disciple = new Disciple();
                            disciple.setDisciple_id(jsonDisciple.getString(UsersTable.Cols.DISCIPLE_ID));
                            disciple.setSlug(jsonDisciple.getString(UsersTable.Cols.SLUG));
                            disciple.setFirst_name(jsonDisciple.getString(UsersTable.Cols.FIRST_NAME));
                            disciple.setMiddle_name(jsonDisciple.getString(UsersTable.Cols.MIDDLE_NAME));
                            disciple.setLast_name(jsonDisciple.getString(UsersTable.Cols.LAST_NAME));
                            disciple.setFull_name(jsonDisciple.getString(UsersTable.Cols.FULL_NAME));
                            disciple.setNick_name(jsonDisciple.getString(UsersTable.Cols.NICK_NAME));
                            disciple.setGender(jsonDisciple.getString(UsersTable.Cols.GENDER));
                            disciple.setBirth_date(jsonDisciple.getString(UsersTable.Cols.BIRTH_DATE));
                            disciple.setNationality(jsonDisciple.getString(UsersTable.Cols.NATIONALITY));
                            disciple.setHome_address(jsonDisciple.getString(UsersTable.Cols.HOME_ADDRESS));
                            disciple.setCity_address(jsonDisciple.getString(UsersTable.Cols.CITY_ADDRESS));
                            disciple.setContact_number(jsonDisciple.getString(UsersTable.Cols.CONTACT_NUMBER));
                            disciple.setEmail_address(jsonDisciple.getString(UsersTable.Cols.EMAIL_ADDRESS));
                            disciple.setSchool(jsonDisciple.getString(UsersTable.Cols.SCHOOL));
                            disciple.setDegree(jsonDisciple.getString(UsersTable.Cols.DEGREE));
                            disciple.setMarital_status(jsonDisciple.getString(UsersTable.Cols.MARITAL_STATUS));
                            disciple.setCompany(jsonDisciple.getString(UsersTable.Cols.COMPANY));
                            disciple.setJob_position(jsonDisciple.getString(UsersTable.Cols.JOB_POSITION));
                            disciple.setDate_won(jsonDisciple.getString(UsersTable.Cols.DATE_WON));
                            disciple.setDiscipler(jsonDisciple.getString(UsersTable.Cols.DISCIPLER));
                            disciple.setInvited_by(jsonDisciple.getString(UsersTable.Cols.INVITED_BY));
                            disciple.setHealth_status(jsonDisciple.getString(UsersTable.Cols.HEALTH_STATUS));
                            disciple.setUser_name(jsonDisciple.getString(UsersTable.Cols.USER_NAME));
                            disciple.setPassword(jsonDisciple.getString(UsersTable.Cols.PASSWORD));
                            disciple.setRoles(jsonDisciple.getString(UsersTable.Cols.ROLES));
                            DiscpleLab.get(BaseActivity.this).addDisciple(disciple);
                        }
                        fetchChange12Db();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDoalog.hide();
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Change12ManualAct", "Login Error: " + error.getMessage());
                progressDoalog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void fetchChange12Db() {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Change12Api.CHANGE12, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("BASEACT", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean(Constants.KEY_ERROR);
                    if (!error) {

                        JSONArray change12Array  = jsonObject.getJSONArray(Constants.JSON_CHANGE12);
                        for(int i = 0; i < change12Array.length(); i++) {
                            JSONObject jsonDisciple = change12Array.getJSONObject(i);
                            Change12 change12 = new Change12();
                            change12.setChange12_id(jsonDisciple.getString(C4DbSchema.Change12.Cols.Change12_ID));
                            change12.setWave_num(jsonDisciple.getString(C4DbSchema.Change12.Cols.WAVE_NUM));
                            change12.setStart_date(jsonDisciple.getString(C4DbSchema.Change12.Cols.START_DATE));
                            change12.setEnd_date(jsonDisciple.getString(C4DbSchema.Change12.Cols.END_DATE));
                            DiscpleLab.get(BaseActivity.this).addChange12(change12);
                        }

                        JSONArray changeeArray  = jsonObject.getJSONArray(Constants.JSON_CHANGEES);
                        for(int i = 0; i < changeeArray.length(); i++) {
                            JSONObject jsonChangee = changeeArray.getJSONObject(i);
                            Changee changee = new Changee();
                            changee.setChange_12(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_12));
                            changee.setChangee(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGEE));
                            changee.setChange_1_ok(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_1_OK));
                            changee.setChange_1_date(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_1_DATE));
                            changee.setChange_2_ok(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_2_OK));
                            changee.setChange_2_date(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_2_DATE));
                            changee.setChange_3_ok(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_3_OK));
                            changee.setChange_3_date(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_3_DATE));
                            changee.setChange_4_ok(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_4_OK));
                            changee.setChange_4_date(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_4_DATE));
                            changee.setChange_5_ok(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_5_OK));
                            changee.setChange_5_date(jsonChangee.getString(C4DbSchema.Changee.Cols.CHANGE_5_DATE));
                            DiscpleLab.get(BaseActivity.this).addChangee(changee);
                        }

                        session.setOnFirstAppStart(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }finally {
                    progressDoalog.hide();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Change12ManualAct", "Login Error: " + error.getMessage());
                progressDoalog.hide();
            }
        });
        AppController.getInstance().addToRequestQueue(strReq);
    }
}



//    @Override
//    public void init() {
////        mSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
////
////        mDimSearchViewBackground = findViewById(R.id.dim_background);
////        mDimDrawable = new ColorDrawable(Color.BLACK);
////        mDimDrawable.setAlpha(0);
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
////            mDimSearchViewBackground.setBackground(mDimDrawable);
////        }else {
////            mDimSearchViewBackground.setBackgroundDrawable(mDimDrawable);
////        }
////
////        mSearchView.setOnLeftMenuClickListener(
////                new FloatingSearchView.OnLeftMenuClickListener() {
////                    @Override
////                    public void onMenuOpened() {
////                        DrawerActivity.getInstance().openDrawer();
////                    }
////                    @Override
////                    public void onMenuClosed() {
////                        DrawerActivity.getInstance().closeDraweer();
////                    }
////                } );
//    }
