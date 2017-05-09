package com.example.andradejoseph.transitionpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.andradejoseph.transitionpractice.model.Contact;

public class DetailActivity extends AppCompatActivity {

    // Before the onCreate
    public final static String ID = "ID";
    public Contact mContact;
    TextView mName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // In the onCreate, after the setContentView method
        mContact = Contact.getItem(getIntent().getIntExtra(ID, 0));
        mName = (TextView) findViewById(R.id.DETAILS_name);
        mName.setText(mContact.get(Contact.Field.NAME));
    }
}
