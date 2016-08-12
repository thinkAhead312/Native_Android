package com.example.dna.dialerapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.helper.PhoneNumberFormatter;
import com.example.dna.dialerapp.helper.SetHeight;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.adapter.ContactAdapter;
import com.example.dna.dialerapp.model.Sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
//import info.androidhive.materialtabs.R;



public class ContactList extends Fragment  {
    public ContactList() {
        // Required empty public constructor
    }

    ListView list;
    ArrayList newList;
    View rootView;
    Context context;
    List<Contact> contactItem = new ArrayList<>();
    private Adapter adapter; //adapter
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        // Inflate the layout for this fragment
        context = rootView.getContext();
        initializeView();
        return rootView;
    }

    private void initializeView() {
        list = (ListView) rootView.findViewById(R.id.listView1);
        int height = SetHeight.setDynamicHeight(context);
        Log.i("HEEEEEIGHT", String.valueOf(height));
        LoadContactsAyscn lca = new LoadContactsAyscn();
        lca.execute();

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentStartActivity.intentDialPadViewActivity(context,"");
            }
        });
    }

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(context, "Loading Contacts",
                    "Please Wait");
        }
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<String> contacts = new ArrayList<String>();
            Cursor c = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            final Map<String, Contact> contactMap= new HashMap<String,Contact>();
            if(c!=null) {
                while (c.moveToNext()) {
                    final String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    final String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    if(!contactMap.containsKey(contactName)) {//check if item already duplicated
                        Contact item = new Contact(contactName, PhoneNumberFormatter.phoneNumberFormat(phNumber));
                        contactMap.put(contactName,item);
                        contactItem.add(item);
                    }
                }
                c.close();
            }
            return contacts;
        }
        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(contacts);
            pd.cancel();
            newList = new ArrayList(contactItem);
            Collections.sort(newList, Contact.ContactNameComparator);
            adapter = new ContactAdapter(context,0 ,newList);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    Contact contact = (Contact) newList.get(position);
                    IntentStartActivity.intentDialPadViewActivity(context, contact.getNumber());
                }
            });
            list.setAdapter((ListAdapter) adapter);
        }
    }
}
