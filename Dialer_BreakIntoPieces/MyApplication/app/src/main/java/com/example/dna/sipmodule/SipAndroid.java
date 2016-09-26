package com.example.dna.sipmodule;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipManager;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.preference.PreferenceManager;
import android.util.Log;

import android.widget.Toast;

import com.example.dna.sipmodule.broadcast_receivers.IncomingSipCallReceiver;
import com.example.dna.sipmodule.model.Constants;


/**
 * Created by dna on 8/5/16.
 */
public class SipAndroid {
    public SipProfile rec;
    public SipManager manager = null;
    public SipProfile me = null;
    public SipAudioCall call = null;
    public IncomingSipCallReceiver callReceiver;
    StringBuilder messageBuilder = new StringBuilder();
    Context context = null;
    MainActivity mainActivity;
    public String status = "";



    public static SipAndroid sipAndroid;
    private SipAndroid() {}
    public static SipAndroid getInstance() {
        if(sipAndroid == null) {
            sipAndroid = new SipAndroid();
        }
        return sipAndroid;
    }

    public void SipAndroidInitialize(Context context)  {

        this.context=context;


        IntentFilter filter = new IntentFilter();
        filter.addAction("android.SipDemo.INCOMING_CALL");
        callReceiver = new IncomingSipCallReceiver();
        context.registerReceiver(callReceiver, filter);
        mainActivity = (MainActivity) context;
        if (SipManager.isVoipSupported(context) && SipManager.isApiSupported(context)){
            // Good to go!
            mainActivity.changeTxtViewDeviceInfoText("Device is Supported");
            initializeManager();
            Toast.makeText(context, "Available and Supported", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, " not Available and Supported", Toast.LENGTH_SHORT).show();
            mainActivity.changeTxtViewDeviceInfoText("Device is Not Supported");
        }
    }

    private void initializeManager() {

        if(manager == null) {
            manager = SipManager.newInstance(context);
        }

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Constants.username = prefs.getString("namePref", "");
        Constants.domain = prefs.getString("domainPref", "");
        Constants.password = prefs.getString("passPref", "");

        if (Constants.username.length() == 0 || Constants.domain.length() == 0 || Constants.password.length() == 0) {
            //Toast.makeText(context, "Please input Details", Toast.LENGTH_SHORT).show();
            mainActivity.changeTxtViewSipInfoText("Please Input your Credentials");
            return;
        }
        try {
            //Build the SIP profile
            Log.i("SipAndroid.class", Constants.username + Constants.domain + Constants.password);
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
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, Intent.FILL_IN_DATA);
            manager.open(me, pi, null);
            // This listener must be added AFTER manager.open is called,
            // Otherwise the methods aren't guaranteed to fire.
            // Send registration requests
            manager.setRegistrationListener(me.getUriString(), new SipRegistrationListener() {
                @Override
                public void onRegistering(String s) {
                    //mainActivity.updateStatus("Registering with SIP Server...");
                    Log.i("SipAndroid.class", "Registering with SIP Server...");
                    mainActivity.changeTxtViewSipInfoText("Registetring...");
                }

                @Override
                public void onRegistrationDone(String s, long l) {
                    //mainActivity.updateStatus("Ready");
                    Log.i("SipAndroid.class", "Ready");
                    mainActivity.changeTxtViewSipInfoText("Ready... Sip ");
                }
                @Override
                public void onRegistrationFailed(String s, int i, String s1) {
                    //mainActivity.updateStatus("Registration failed.  Please check settings.");
                    Log.e("ERROR REGISTER", s + ":" + s1);
                    Log.i("SipAndroid.class", "Registration failed.  Please check settings." + s + " " + s1);
                    mainActivity.changeTxtViewSipInfoText("Registration failed.  Please check settings...");
                    //initializeManager();
                }
            });
            // showMessage("Disciple", messageBuilder.toString()); //show Message
        } catch (ParseException pe) {
            messageBuilder.append("Connection Errror" +"\n");
            Log.e("ERROR REGISTER", String.valueOf(pe));
        } catch (SipException se) {
            messageBuilder.append(se.getMessage() +"\n");
            Log.e("ERROR REGISTER Sip", String.valueOf(se));
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
    }

    public void OutBoundCall(String outbound_number, final Context context) {
        //mainActivity.updateStatus(outbound_number);
        try {
            SipProfile.Builder builder = new SipProfile.Builder(outbound_number, me.getSipDomain());
            builder.setAutoRegistration(false);
            rec = builder.build();



            SipAudioCall.Listener listener = new SipAudioCall.Listener() {
                // Much of the client's interaction with the SIP Stack will
                // happen via listeners.  Even making an outgoing call, don't
                // forget to set up a listener to set things up once the call is established.
                @Override
                public void onRinging(SipAudioCall call, SipProfile caller) {
                    Log.i("ONRINGING", "rinnging");
                    Toast.makeText(context, "ONRINGING", Toast.LENGTH_SHORT).show();
                    //IntentStartActivity.intentCallingActivity(context, Constants.Call_State, Constants.Sip_Outgoing);
                }
                @Override
                public void onCallEstablished(SipAudioCall call) {
                    call.startAudio();
                    call.setSpeakerMode(true);
                    //mainActivity.updateStatus(call);
                }
                @Override
                public void onCallEnded(SipAudioCall call) {
                    //mainActivity.updateStatus("Ready.");
                    context.sendBroadcast(new Intent("kill"));

                }
            };
            Toast.makeText(context, me.getUriString() + rec.getUriString(), Toast.LENGTH_SHORT).show();
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


}
