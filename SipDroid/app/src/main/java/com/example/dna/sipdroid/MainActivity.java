package com.example.dna.sipdroid;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ParseException;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dna.sipdroid.receivers.IncomingCallReceiver;


public class MainActivity extends AppCompatActivity {

    SipAndroid sipAndroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sipAndroid = SipAndroid.getInstance();
        sipAndroid.SipAndroidInitialize(this);
        makeOutGoing();
    }

    private void makeOutGoing() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            outBound(extras.getString("OUTBOUND_NUMBER"));
        }
    }

    private void outBound(String outbound_number) {
        sipAndroid.OutBoundCall(outbound_number, this);
    }

    /**
     * Updates the status box with the SIP address of the current call.
     * @param call The current, active call.
     */
    public void updateStatus(SipAudioCall call) {
        String useName = call.getPeerProfile().getDisplayName();
        if(useName == null) {
            useName = call.getPeerProfile().getUserName();
        }
        //updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }

    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
               // TextView labelView = (TextView) findViewById(R.id.sipLabel);
                //labelView.setText(status);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sipAndroid.call != null) {
            sipAndroid.call.close();
        }
        try {
            if (sipAndroid.me != null) {
                sipAndroid.manager.close(sipAndroid.me.getUriString());
            }
        } catch (Exception ee) {
            //
        }
        if (sipAndroid.callReceiver != null) {
            unregisterReceiver(sipAndroid.callReceiver);
        }
    }
}
