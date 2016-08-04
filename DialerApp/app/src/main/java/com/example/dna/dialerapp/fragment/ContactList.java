package com.example.dna.dialerapp.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dna.dialerapp.helper.PhoneNumberFormatter;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.adapter.ContactAdapter;
import com.example.dna.dialerapp.model.Sms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
//import info.androidhive.materialtabs.R;



public class ContactList extends Fragment  {
    public ContactList() {
        // Required empty public constructor
    }

    ListView list;
    LinearLayout ll;
    Button loadBtn;

    View rootView;
    Context context;
    List<Contact> contactItem = new ArrayList<>();
    private Adapter adapter; //adapter

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

        ll = (LinearLayout) rootView.findViewById(R.id.LinearLayout1);
        list = (ListView) rootView.findViewById(R.id.listView1);
        loadBtn = (Button) rootView.findViewById(R.id.button1);

        ll.removeView(loadBtn);
        LoadContactsAyscn lca = new LoadContactsAyscn();
        lca.execute();
        return rootView;
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
            if(c!=null) {
                while (c.moveToNext()) {
                    String contactName = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phNumber = c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact item = new Contact(contactName, PhoneNumberFormatter.phoneNumberFormat(phNumber));
                    contactItem.add(item);
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

            Set set = new TreeSet(new ContactComparator());
            set.addAll(contactItem);
            final ArrayList newList = new ArrayList(set);
            Collections.sort(newList, Contact.ContactNameComparator);
            adapter = new ContactAdapter(context,0 ,newList);
            list.setAdapter((ListAdapter) adapter);
        }

        class ContactComparator implements Comparator<Contact> {
            @Override
            public int compare(Contact lhs, Contact rhs) {

                if(lhs.getNumber().equals(rhs.getNumber())){
                    Log.e("Comparator", lhs.getNumber() +" " + rhs.getNumber());
                    return 0;
                }
                return 1;
            }
        }


    }

}
