package com.example.andradejoseph.simpleregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andradejoseph.simpleregistration.model.User;
import com.example.andradejoseph.simpleregistration.model.UserLab;

public class AddUser extends AppCompatActivity {

    private EditText mUserNameEditText;
    private EditText mPassWordEditText;
    private Button mLoginButton;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, AddUser.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);


        mUserNameEditText = (EditText) findViewById(R.id.input_username);
        mPassWordEditText = (EditText) findViewById(R.id.input_password);
        mLoginButton = (Button) findViewById(R.id.loginBtn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mUserNameEditText.getText().toString().trim();
                String password = mPassWordEditText.getText().toString().trim();

                if(username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(AddUser.this, "Input Username or Password", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user = new User();
                user.setUser_id();
                user.setUser_name(username);
                user.setPassword(password);

                UserLab.get(AddUser.this).addUser(user);


                Toast.makeText(AddUser.this, "Added New User", Toast.LENGTH_SHORT).show();
                Intent i = DetailActivity.newIntent(AddUser.this);
                startActivity(i);
                finish();
            }
        });

    }
}
