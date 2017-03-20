package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;

public class ConsolidatesActivity extends AppCompatActivity implements Callback{


    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ConsolidatesActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidates);
        DrawerActivity.getInstance().DrawerInit(ConsolidatesActivity.this);

        init();

    }

    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Change12 Manual");
    }


    @Override
    public void onMethodCallback(int position) {
        switch(position) {
            case CHANGE_12_MODULE:
                Intent i2 = Change12ManualActivity.newIntent(ConsolidatesActivity.this);
                startActivity(i2);
                finish();
                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            DrawerActivity.getInstance().openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }

}
