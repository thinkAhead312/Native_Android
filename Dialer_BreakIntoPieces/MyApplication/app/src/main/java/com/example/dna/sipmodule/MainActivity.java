package com.example.dna.sipmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dna.sipmodule.helper.IntentStartActivity;
import com.example.dna.sipmodule.helper.MyCustomCrashManagerListener;
import com.example.dna.sipmodule.model.Constants;

import net.hockeyapp.android.CrashManager;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextView txtSipInfo, txtDeviceInfo;
    private SipAndroid sipAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sipAndroid = SipAndroid.getInstance();
        initializeWidgets();
        //sipAndroid.SipAndroidInitialize(MainActivity.this);
    }

    private void initializeWidgets() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        txtSipInfo = (TextView) findViewById(R.id.txtStatusInfo);
        txtDeviceInfo = (TextView) findViewById(R.id.txtDeviceStatusInfo);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentStartActivity.intentDialPadViewActivity(MainActivity.this, "");
            }
        });
    }
    public void changeTxtViewSipInfoText(final String strStatus) {

        this.runOnUiThread(new Runnable() {
            public void run() {
                txtSipInfo.setText(strStatus);
            }
        });
    }

    public void changeTxtViewDeviceInfoText(final String strStatus) {

        this.runOnUiThread(new Runnable() {
            public void run() {
                txtDeviceInfo.setText(strStatus);
            }
        });
    }

    private void checkSipStatus() {
        sipAndroid.SipAndroidInitialize(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Constants.SET_AUTH_INFO, 0, "Edit your SIP Info.");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Constants.SET_AUTH_INFO:
                IntentStartActivity.updatePreferences(MainActivity.this);
                break;
        }
        return true;
    }


    @Override
    public void onResume() {
        super.onResume();
        // ... your own onResume implementation
        checkForCrashes();
    }
    private void checkForCrashes() {
        CrashManager.register(this, "a435aaaa0f17496cb39cae22cf376e15", new MyCustomCrashManagerListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        sipAndroid = SipAndroid.getInstance();
        checkSipStatus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sipAndroid.call != null) {
            sipAndroid.call.close();
        }
        closeLocalProfile();
    }
    private void closeLocalProfile() {
        if (sipAndroid.manager == null) {
            return;
        }
        try {
            if (sipAndroid.me != null) {
                sipAndroid.manager.close(sipAndroid.me.getUriString());
            }
        } catch (Exception ee) {
            Log.d("onDestroy", "Failed to close local profile.", ee);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sipAndroid.callReceiver != null) {
            unregisterReceiver(sipAndroid.callReceiver);
        }
    }
}
