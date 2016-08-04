package com.example.dna.dialerapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewSms extends AppCompatActivity {
    private Toolbar toolbar;
    ListView listView ;
    ArrayList<String> smsList = new ArrayList<String>();
    String phoneNum="", displayName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sms);

        phoneNum = getIntent().getExtras().getString("Users_ID");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listViewSms);
        Uri uri = Uri.parse("content://sms/inbox");

        ContentResolver contentResolver = getContentResolver();

        String sms = "address='"+ phoneNum + "'";
        Cursor cursor = contentResolver.query(uri, new String[]{"_id", "body"}, sms, null, null);

        Uri lookupUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNum));
        Cursor c = getContentResolver().query(lookupUri, new String[]{ContactsContract.Data.DISPLAY_NAME}, null, null, null);
        try {
            c.moveToFirst();
            displayName = c.getString(0);

        } catch (Exception e) {
            // Do something with the exception like logging it or so.
        }finally{
            c.close();
        }

        if (displayName.equals("")) {
            getSupportActionBar().setTitle(phoneNum);
        }
        else {
            getSupportActionBar().setTitle(displayName);
        }



        //System.out.println(cursor.getCount());
        while (cursor.moveToNext())
        {
            String strbody = cursor.getString(cursor.getColumnIndex("body") );

            smsList.add(strbody);
            //Toast.makeText(ViewSms.this, strbody, Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, smsList);

        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lesson__one, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
