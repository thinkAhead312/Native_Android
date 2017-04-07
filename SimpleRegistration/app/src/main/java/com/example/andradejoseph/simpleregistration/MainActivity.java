package com.example.andradejoseph.simpleregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andradejoseph.simpleregistration.model.User;
import com.example.andradejoseph.simpleregistration.model.UserLab;
import com.example.andradejoseph.simpleregistration.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    private EditText mUserNameEditText;
    private EditText mPassWordEditText;
    private Button mLoginButton;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionManager(getApplicationContext());


        mUserNameEditText = (EditText) findViewById(R.id.input_username);
        mPassWordEditText = (EditText) findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.loginBtn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserNameEditText.getText().toString().trim();
                String password = mPassWordEditText.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Input Username or Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUser_id();
                user.setUser_name(username);
                user.setPassword(password);

                UserLab.get(MainActivity.this).addUser(user);

                session.setLogin(true); //set session to true

                session.setUserData(user);

                Intent i = DetailActivity.newIntent(MainActivity.this);
                startActivity(i);
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(session.isLoggedIn()) {
            Intent i = DetailActivity.newIntent(MainActivity.this);
            startActivity(i);
            finish();
        }
    }

}
