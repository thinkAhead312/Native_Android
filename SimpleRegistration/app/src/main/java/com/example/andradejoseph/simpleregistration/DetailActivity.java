package com.example.andradejoseph.simpleregistration;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andradejoseph.simpleregistration.constants.Constants;
import com.example.andradejoseph.simpleregistration.model.User;
import com.example.andradejoseph.simpleregistration.model.UserLab;
import com.example.andradejoseph.simpleregistration.session.SessionManager;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private TextView mWelcomeTextview;
    private TextView mTextView;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, DetailActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String full_name  = sessionManager.userPref.getString(Constants.USER_FIRST_NAME, null);
        mWelcomeTextview = (TextView) findViewById(R.id.welcome_heading);

        mTextView = (TextView) findViewById(R.id.textView);
        mWelcomeTextview.setText("Hello " + full_name + "!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        UserLab userLab = UserLab.get(DetailActivity.this);
        List<User> users = userLab.getUsers();
        for(User user: users) {
            Log.d("DetailActivity> ", user.getUser_name() + " " + user.getPassword());
            String text = user.getUser_name();
            mTextView.append(text + "\n");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = AddUser.newIntent(DetailActivity.this);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        finish();
    }
}
