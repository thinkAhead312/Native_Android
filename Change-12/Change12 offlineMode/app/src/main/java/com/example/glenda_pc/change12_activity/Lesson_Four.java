package com.example.glenda_pc.change12_activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class Lesson_Four extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson__four);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson__four, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.lesson_1) {
            Intent intent = new Intent(Lesson_Four.this,Lesson_One.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.lesson_2) {
            Intent intent = new Intent(Lesson_Four.this,Lesson_Two.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.lesson_3) {
            Intent intent = new Intent(Lesson_Four.this,Lesson_Three.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.lesson_4) {

            return true;
        }
        if (id == R.id.lesson_5) {
            Intent intent = new Intent(Lesson_Four.this,Lesson_Five.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
