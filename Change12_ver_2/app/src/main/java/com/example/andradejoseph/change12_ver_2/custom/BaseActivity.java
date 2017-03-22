package com.example.andradejoseph.change12_ver_2.custom;

import android.app.ProgressDialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.constants.Change12Api;
import com.example.andradejoseph.change12_ver_2.constants.Constants;
import com.example.andradejoseph.change12_ver_2.database.C4_DbSchema;
import com.example.andradejoseph.change12_ver_2.database.C4_DbSchema.UsersTable;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.utils.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ANDRADEJOSEPH on 3/21/2017.
 */

public class BaseActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    public ProgressDialog progressDoalog;


    public void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change12 Manual");
    }

    public void onTransitionAnimation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(1000);
            getWindow().setEnterTransition(fade);
            getWindow().setExitTransition(fade);
        }
    }


    public void fetchUserTable() {
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Change12Api.USERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Change12ManualAct", "Login Response: " + response.toString());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean error = jsonObject.getBoolean(Constants.KEY_ERROR);
                    if (!error) {
                        Toast.makeText(BaseActivity.this, "First app start", Toast.LENGTH_SHORT).show();
                        //Getting Json Array Node
                        JSONArray disciples  = jsonObject.getJSONArray(Constants.JSON_USERS);
                        for(int i = 0; i < disciples.length(); i++) {
                            JSONObject jsonDisciple = disciples.getJSONObject(i);
                            Disciple disciple = new Disciple();

                            disciple.setFirst_name(jsonDisciple.getString(UsersTable.Cols.FIRST_NAME));
                            disciple.setLast_name(jsonDisciple.getString(UsersTable.Cols.LAST_NAME));
                            DiscpleLab.get(BaseActivity.this).addDisciple(disciple);
                        }
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
//
        AppController.getInstance().addToRequestQueue(strReq);

    }
}
