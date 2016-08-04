package com.example.dna.dialerapp.fragment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.adapter.CallLogsAdapter;
import com.example.dna.dialerapp.adapter.ContactAdapter;
import com.example.dna.dialerapp.model.Contact;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;


public class CallLogs extends Fragment {


    ListView list;
    View rootView;
    Context context;
    List<com.example.dna.dialerapp.model.CallLogs> callLogsItem = new ArrayList<>();
    private Adapter adapter; //adapter
    private static final String TAG = "CallLog";
    private static final int URL_LOADER = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_call_logs, container, false);
        // Inflate the layout for this fragment
        context = rootView.getContext();
        list = (ListView) rootView.findViewById(R.id.lvLogs);

        LoadContactsAyscn lca = new LoadContactsAyscn();
        lca.execute();
        return rootView;
    }


    private class LoadContactsAyscn extends AsyncTask<Void, Void, ArrayList<String>> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pd = ProgressDialog.show(context, "Loading Contacts",
                    "Please Wait");
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO Auto-generated method stub
            ArrayList<String> call_logs = new ArrayList<String>();

            if (checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return null;
            }
            Cursor cursor = context.getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    null,
                    null,
                    null,
                    null);
            int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = cursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = cursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = cursor.getColumnIndex(CallLog.Calls.DURATION);
            int name  = cursor.getColumnIndex(CallLog.Calls.CACHED_NAME);

            while (cursor.moveToNext()) {

                String phNumber = cursor.getString(number);
                String callType = cursor.getString(type);
                String callDate = cursor.getString(date);
                String callDuration = cursor.getString(duration);
                String contactName = cursor.getString(name);

                com.example.dna.dialerapp.model.CallLogs logItem =
                        new com.example.dna.dialerapp.model.CallLogs( phNumber,  callType,  callDate,  callDuration, contactName);
                callLogsItem.add(logItem);
            }
            cursor.close();

            return call_logs;
        }
        @Override
        protected void onPostExecute(ArrayList<String> contacts) {
            // TODO Auto-generated method stub
            super.onPostExecute(contacts);
            pd.cancel();
            adapter = new CallLogsAdapter(context,0 ,callLogsItem);
            list.setAdapter((ListAdapter) adapter);
        }
    }
    private int checkSelfPermission(String readCallLog) {
        return 0;
    }
}
