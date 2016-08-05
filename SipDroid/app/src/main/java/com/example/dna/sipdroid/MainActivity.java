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



    public String sipAddress = "4225";
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public IncomingCallReceiver callReceiver;
    StringBuilder messageBuilder = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingCallReceiver();
        this.registerReceiver(callReceiver, filter);
        initializeManager();

        makeOutGoing();
    }

    private void makeOutGoing() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            outBound(extras.getString("OUTBOUND_NUMBER"));
        }
    }

    private void outBound(String outbound_number) {
        updateStatus(outbound_number);

        try {
            SipProfile.Builder builder = new SipProfile.Builder(outbound_number, me.getSipDomain());
            builder.setAutoRegistration(false);
            SipProfile rec = builder.build();

            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                  
                    updateStatus(call);
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    updateStatus("Ready.");
                }
            };
            Toast.makeText(MainActivity.this, me.getUriString() + rec.getUriString(), Toast.LENGTH_SHORT).show();
            call = manager.makeAudioCall(me.getUriString(), rec.getUriString(), null, 30);
            call.setListener(listener);


        }
        catch (Exception e) {
            Log.i("/InitiateCall", "Error when trying to close manager.", e);
            if (me != null) {
                try {
                    manager.close(me.getUriString());
                } catch (Exception ee) {
                    Log.i("/InitiateCall",
                            "Error when trying to close manager.", ee);
                    ee.printStackTrace();
                }
            }
            if (call != null) {
                call.close();
            }
        }
    }

    public void initializeManager() {
        //initialize sip manager
        if(manager == null) {
            manager = SipManager.newInstance(this);
        }
        if (Constants.username.length() == 0 || Constants.domain.length() == 0 || Constants.password.length() == 0) {
            Toast.makeText(MainActivity.this, "Please input Details", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //Build the SIP profile
            SipProfile.Builder builder =  new SipProfile.Builder(Constants.username, Constants.domain);
            builder.setPassword(Constants.password);
            builder.setPort(5060);
            builder.setSendKeepAlive(true);
            builder.setAutoRegistration(true);
            builder.setProtocol("UDP");
            me = builder.build();
            // Register a pending intent for incoming calls
            Intent i = new Intent();
            i.setAction("android.SipDemo.INCOMING_CALL");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);
            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.
            // Send registration requests
            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                @Override
                public void onRegistering(String s) {
                    updateStatus("Registering with SIP Server...");
                }

                @Override
                public void onRegistrationDone(String s, long l) {
                    updateStatus("Ready");
                }

                @Override
                public void onRegistrationFailed(String s, int i, String s1) {
                    updateStatus("Registration failed.  Please check settings.");
                    Log.e("ERROR REGISTER", s + ":" + s1);
                }

            });
            // showMessage("Disciple", messageBuilder.toString()); //show Message
        } catch (ParseException pe) {
            messageBuilder.append("Connection Errror" +"\n");
        } catch (SipException se) {
            messageBuilder.append(se.getMessage() +"\n");
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

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
        updateStatus(useName + "@" + call.getPeerProfile().getSipDomain());
    }

    public void updateStatus(final String status) {
        // Be a good citizen.  Make sure UI changes fire on the UI thread.
        this.runOnUiThread(new Runnable() {
            public void run() {
                TextView labelView = (TextView) findViewById(R.id.sipLabel);
                labelView.setText(status);
            }
        });
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
           // call.close();
        }

        try {
            if (me != null) {
              //  manager.close(me.getUriString());
            }
        } catch (Exception ee) {
            //
        }
        if (callReceiver != null) {
           // unregisterReceiver(callReceiver);
        }
    }
}
