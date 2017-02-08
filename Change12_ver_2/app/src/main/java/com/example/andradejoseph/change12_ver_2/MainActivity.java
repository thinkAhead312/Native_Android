package com.example.andradejoseph.change12_ver_2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.andradejoseph.change12_ver_2.utils.Change12Api;
import com.android.volley.Request.Method;
import com.example.andradejoseph.change12_ver_2.utils.Constants;
import com.example.andradejoseph.change12_ver_2.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

import static com.example.andradejoseph.change12_ver_2.utils.Change12Api.*;

public class MainActivity extends AppCompatActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    EditText mEditTextUsername;
    EditText mEditTextPassword;
    Button mButtonLogin;
    FloatingActionButton fab;

    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mEditTextUsername = (EditText) findViewById(R.id.et_username);
        mEditTextPassword =(EditText) findViewById(R.id.et_password);
        mButtonLogin =(Button) findViewById(R.id.bt_go);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        final ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);

        // Session manager
        session = new SessionManager(getApplicationContext());

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeLogInRequest();
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    Explode explode = new Explode();
//                    explode.setDuration(500);
//                    getWindow().setExitTransition(explode);
//                    getWindow().setEnterTransition(explode);
//                }
//                Intent i2 = Change12ManualActivity.newIntent(MainActivity.this);
//                startActivity(i2, oc2.toBundle());
//                finish();
            }
        });

    }

    private void makeLogInRequest() {

        //getting values from inputted edit text
        final String username = mEditTextUsername.getText().toString().trim();
        final String password = mEditTextPassword.getText().toString();
        //show processdialog
        showpDialog();
        //Creating a string request
        StringRequest strReq = new StringRequest(Method.POST,
                Change12Api.USER_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hidepDialog();

                try {
                    JSONObject jsonObject = new JSONObject(response);
//                   Toast.makeText(MainActivity.this, String.valueOf(jsonUserData), Toast.LENGTH_SHORT).show();

                    boolean error = jsonObject.getBoolean(Constants.KEY_ERROR);
                    if (!error) {
                        session.setLogin(true);
                        String id = jsonObject.getString(Constants.USER_ID);
                        String first_name = jsonObject.getString(Constants.USER_FIRST_NAME);
                        String last_name = jsonObject.getString(Constants.USER_LAST_NAME);
                        Toast.makeText(MainActivity.this,id +": "+ first_name + " " + last_name, Toast.LENGTH_SHORT).show();
                    } else {

                    }

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());

                hidepDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put(Constants.KEY_USERNAME, username);
                params.put(Constants.KEY_PASSWORD, password);

                return params;
            }

        };
//
        AppController.getInstance().addToRequestQueue(strReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}



//        // Adding request to request queue
//        AppController.getmInstance().addToRequestQueue(jsonObjReq);