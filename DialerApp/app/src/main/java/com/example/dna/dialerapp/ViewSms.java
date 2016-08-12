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
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dna.dialerapp.helper.PhoneNumberFormatter;
import com.example.dna.dialerapp.model.Constants;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.settings.SipSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ViewSms extends AppCompatActivity {
    private Toolbar toolbar;
    private String SMS_URL = "content://sms/";
    private String Address = "address";
    public static String intentString = "Users_ID";
    ListView listView ;
    ArrayList<String> smsList = new ArrayList<String>();
    String phoneNum="", displayName="";
    private  SipAndroid sipAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sms);
        sipAndroid = SipAndroid.getInstance();
        phoneNum = getIntent().getExtras().getString(intentString);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listViewSms);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        Uri uri = Uri.parse(SMS_URL);

        ContentResolver contentResolver = getContentResolver();

        String sms = "address= '"+phoneNum+"' ";
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


        getSupportActionBar().setTitle(displayName.equals("") ? phoneNum : displayName);
        //System.out.println(cursor.getCount());
        final Map<String, String> smsMap= new HashMap<String,String>();
        while (cursor.moveToNext())
        {
            String strbody = cursor.getString(cursor.getColumnIndex("body") );
            if(!smsMap.containsKey(strbody)) {//check if item already duplicated
                smsMap.put(strbody,strbody);
                smsList.add(strbody);
            }
            //Toast.makeText(ViewSms.this, strbody, Toast.LENGTH_SHORT).show();
        }

        Collections.reverse(smsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, smsList);

        listView.setAdapter(adapter);
        listView.setSelection(adapter.getCount() - 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.add(0, Constants.SET_AUTH_INFO, 0, "Edit your SIP Info.");
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

        switch (item.getItemId()) {
            case Constants.SET_AUTH_INFO:
                updatePreferences();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public void updatePreferences() {
        Intent settingsActivity = new Intent(getBaseContext(),
                SipSettings.class);
        startActivity(settingsActivity);
    }



}
