package com.example.dna.sipmodule;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.sip.SipException;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dna.sipmodule.helper.TelephonyActions;
import com.example.dna.sipmodule.model.Constants;
import com.example.dna.sipmodule.preference_settings.SipSettings;

public class Calling extends AppCompatActivity {
    private FloatingActionButton fab;
    private TextView textView;
    private  SipAndroid sipAndroid;
    private String callingState = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        sipAndroid = SipAndroid.getInstance();
        initializeWidgets();
        registerReceiver(closeActivity, new IntentFilter("kill")); //register receiver
        callingState = getIntent().getExtras().getString(Constants.Call_State);

        if(callingState.equals(Constants.Sip_Incoming) || callingState.equals(Constants.Telephony_Incoming))
        {
            changeFabButtonImage();
            String useName = sipAndroid.call.getPeerProfile().getDisplayName();
            textView.setText("Incoming Call From: " + useName);
        }
        else if(callingState.equals(Constants.Sip_Outgoing))
        {
            try {
                textView.setText("Dialing Contact to: " +  sipAndroid.rec.getUriString());
            }
            catch (Exception e) {}
        }
        final Boolean[] calling = {false};
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (callingState.equals(Constants.Telephony_Incoming)) {
                   if(calling[0] ==false) {
                       Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);  //Answering Phone Call
                       i.putExtra(Intent.EXTRA_KEY_EVENT,
                               new KeyEvent(KeyEvent.ACTION_UP,
                                       KeyEvent.KEYCODE_HEADSETHOOK));
                       sendOrderedBroadcast(i, null);
                       calling[0] = true;
                   }
                   else if (calling[0]) {
                       TelephonyActions.EndCall();
                   }
               }
               else if (callingState.equals(Constants.Sip_Incoming)) {

                   if (calling[0] == false) {
                       try {
                           sipAndroid.call.answerCall(30);
                           sipAndroid.call.startAudio();
                           calling[0] = true;
                       } catch (SipException e) {
                           e.printStackTrace();
                       }
                   } else if (calling[0]) {
                       endSipCall();
                   }
               }

               else if (callingState.equals(Constants.Sip_Outgoing)) {
                   if(sipAndroid.call.isInCall()) {
                       Toast.makeText(Calling.this, "End Calling", Toast.LENGTH_SHORT).show();
                       endSipCall();
                   }
               }

               else if (callingState.equals(Constants.Telephony_Outgoing)) {
                   TelephonyActions.EndCall();
               }
            }
        });

    }
    private void endSipCall() {
        try {
            sipAndroid.call.endCall();

        } catch (SipException e) {
            Log.e("END THIS CALL PLEASE", "END NOW");
        }
    }

    private void changeFabButtonImage() {
        fab.setBackgroundColor(Color.parseColor("#03A9F4"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.answer_call, this.getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.answer_call));
        }
    }

    private void initializeWidgets() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        textView = (TextView) findViewById(R.id.txtContact);
    }

    private final BroadcastReceiver closeActivity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Constants.SET_AUTH_INFO, 0, "Edit your SIP Info.");
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Constants.SET_AUTH_INFO:
                updatePreferences();
                break;
        }
        return true;
    }
    public void updatePreferences() {
        Intent settingsActivity = new Intent(getBaseContext(),
                SipSettings.class);
        startActivity(settingsActivity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(closeActivity);
    }
}
