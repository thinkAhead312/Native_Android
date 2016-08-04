package com.example.dna.dialerapp.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.SendSms;
import com.example.dna.dialerapp.adapter.ContactAdapter;
import com.example.dna.dialerapp.adapter.SmsAdapter;
import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.helper.PhoneNumberFormatter;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.model.Sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SmsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SmsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SmsList extends Fragment  {

    //  GUI Widget
    Button btnSent, btnInbox, btnDraft;
    TextView lblMsg, lblNo;
    ListView lvMsg;

    View rootView;
    Context context;
    // Cursor Adapter
    private FloatingActionButton fab;

    List smsItem = new ArrayList();
    List contactItem = new ArrayList<>();
    public SmsAdapter adapter; //adapter
    public SmsList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_sms, container, false);
        context = rootView.getContext();
        lvMsg = (ListView) rootView.findViewById(R.id.lvMsg);
        LoadContactsAyscn lca = new LoadContactsAyscn();
        lca.execute();
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
            }
        });
        return rootView;
    }
    private void sendSMS() {
        try {
            Intent intent = new Intent(context,SendSms.class);
            context.startActivity(intent);
            Log.i("Finished sending SMS...", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {
        ProgressDialog pd;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(context, "Loading Sms",
                    "Please Wait");
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<String> contacts = new ArrayList<String>();

            Cursor cursorContact = context.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    null, null, null);
            while (cursorContact.moveToNext()) {

                String contactName = cursorContact
                        .getString(cursorContact
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phNumber = cursorContact
                        .getString(cursorContact
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact item = new Contact(contactName, PhoneNumberFormatter.phoneNumberFormat(phNumber));
                contactItem.add(item);
            }
            cursorContact.close();
            Uri uri = Uri.parse("content://sms/inbox");
            Cursor c= context.getContentResolver().query(uri, null, null, null, null);

            if(c.moveToFirst()) {
                for(int i=0; i < c.getCount(); i++) {
                    final Sms sms = new Sms();
                    sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
                    sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
                    sms.setDate(c.getString(c.getColumnIndexOrThrow("date")).toString());

                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            Iterator iterator = (Iterator) contactItem.iterator();
                            while (iterator.hasNext()) {
                                Contact news = (Contact) iterator.next();
                                if(news.getNumber().equals(sms.getNumber())) {
                                    sms.setName(news.getName());
                                }
                            }
                        }
                    };thread.start();
                    smsItem.add(sms);
                    c.moveToNext();
                  }
          }
          c.close();
            return contacts;
        }
        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(contacts);

            pd.cancel();

            Set set = new TreeSet(new StudentsComparator());
            set.addAll(smsItem);

            final ArrayList newList = new ArrayList(set);
            adapter = new SmsAdapter(context,0 ,smsItem); 

            lvMsg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                        long arg3) {
                    // TODO Auto-generated method stub
                    Sms sms = (Sms) smsItem.get(position);
                    Log.d("############", "Items " + sms.getNumber());
                    addNewSms();
                    IntentStartActivity.intentViewSmsActivity(context, sms.getNumber());

                }
            });
            lvMsg.setAdapter((ListAdapter) adapter);
        }

        class StudentsComparator implements Comparator<Sms> {
            @Override
            public int compare(Sms lhs, Sms rhs) {
                if(lhs.getNumber().equals(rhs.getNumber())){
                    return 0;
                }
                return 1;
            }
        }
    }

    private void addNewSms() {



        adapter.notifyDataSetChanged();
    }


}
