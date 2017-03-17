package com.example.andradejoseph.change12_ver_2;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.andradejoseph.change12_ver_2.utils.AppController;
import com.example.andradejoseph.change12_ver_2.utils.Change12Api;
import com.android.volley.Request.Method;
import com.example.andradejoseph.change12_ver_2.constants.Constants;
import com.example.andradejoseph.change12_ver_2.sessions.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static String TAG = LoginActivity.class.getSimpleName();
    private RelativeLayout mRelativeLayout;
    private EditText mEditTextUsername;
    private EditText mEditTextPassword;
    private Button mButtonLogin;
    private FloatingActionButton fab;

    private ProgressDialog pDialog;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initWidgets();
        // Session manager
        session = new SessionManager(getApplicationContext());
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToChange12Module();
//                makeLogInRequest();
            }
        });

    }

    private void initWidgets() {
        mEditTextUsername = (EditText) findViewById(R.id.et_username);
        mEditTextPassword =(EditText) findViewById(R.id.et_password);
        mButtonLogin =(Button) findViewById(R.id.bt_go);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main_relative_layout_id);
    }

    private void displaySnackbar(String message) {
        Snackbar snackbar = Snackbar.make(mRelativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void makeLogInRequest() {

        //getting values from inputted edit text
        final String username = mEditTextUsername.getText().toString().trim();
        final String password = mEditTextPassword.getText().toString();

        if(username.equals("") || password.equals("")){
            displaySnackbar("Please input username or password");
            return;
        }

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

                    boolean error = jsonObject.getBoolean(Constants.KEY_ERROR);
                    if (!error) {
                        session.setLogin(true); //set session to true
                        JSONObject jsonUserData  = jsonObject.getJSONObject(Constants.KEY_USER);
                        session.setUserData(jsonUserData);
                        goToChange12Module();
                    } else {
                        displaySnackbar("Incorrect username or password");
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

    @Override
    protected void onResume() {
        super.onResume();

        if(session.isLoggedIn()) {
            goToChange12Module();
        }
    }

    private void goToChange12Module() {

        final ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }
        Intent i2 = Change12ManualActivity.newIntent(LoginActivity.this);
        startActivity(i2, oc2.toBundle());
        finish();
    }
}
