package com.example.andradejoseph.change12_ver_2;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    EditText etUsername;
    EditText etPassword;
    Button btGo;
    CardView cv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        @InjectView(R.id.et_username)
         etUsername = (EditText) findViewById(R.id.et_username);
//        @InjectView(R.id.et_password)
         etPassword =(EditText) findViewById(R.id.et_password);
         btGo =(Button) findViewById(R.id.bt_go);
         cv =(CardView) findViewById(R.id.cv);
         fab = (FloatingActionButton) findViewById(R.id.fab);
        final ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Explode explode = new Explode();
                    explode.setDuration(500);
                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                }
                Intent i2 = Change12ManualActivity.newIntent(MainActivity.this);
                startActivity(i2, oc2.toBundle());
                finish();
            }
        });

    }

    @OnClick({R.id.bt_go, R.id.fab})
    public void onClick(View view) {
        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(this);

        switch (view.getId()) {
            case R.id.bt_go:

                break;
            case R.id.fab:
                Intent i3 = new Intent(this,Change12ManualActivity.class);
                startActivity(i3, oc2.toBundle());
                break;
        }
    }
}
