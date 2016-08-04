package com.example.dna.dialerapp;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dna.dialerapp.receivers.IncomingCall;

import java.lang.reflect.Method;

public class Calling extends AppCompatActivity {
    private FloatingActionButton fab;
    private TextView txtContact;

    private static Calling activity;
    public static Calling instance() {
        return activity;
    }
    private ITelephony telephonyService;
    String callingState ="Incoming_Call";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        initializeWidgets();

        //callingState = getIntent().getExtras().getString("STATE");
        Toast.makeText(Calling.this, callingState, Toast.LENGTH_SHORT).show();

        if(callingState.equals("Incoming_Call")) {
            changeFabButtonImage();
        }
        catchOutGoindCallInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        final Boolean[] calling = {false};

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(callingState.equals("Incoming_Call")) {
                   //inst.answerCall();
                    if(calling[0] == false) {
                        Intent i = new Intent(Intent.ACTION_MEDIA_BUTTON);
                        i.putExtra(Intent.EXTRA_KEY_EVENT,
                                new KeyEvent(KeyEvent.ACTION_UP,
                                        KeyEvent.KEYCODE_HEADSETHOOK));
                        sendOrderedBroadcast(i, null);

                        calling[0] = true;


                    } else if (calling[0]){
                        try {

                            String serviceManagerName = "android.os.ServiceManager";
                            String serviceManagerNativeName = "android.os.ServiceManagerNative";
                            String telephonyName = "com.android.internal.telephony.ITelephony";
                            Class<?> telephonyClass;
                            Class<?> telephonyStubClass;
                            Class<?> serviceManagerClass;
                            Class<?> serviceManagerNativeClass;
                            Method telephonyEndCall;
                            Object telephonyObject;
                            Object serviceManagerObject;
                            telephonyClass = Class.forName(telephonyName);
                            telephonyStubClass = telephonyClass.getClasses()[0];
                            serviceManagerClass = Class.forName(serviceManagerName);
                            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
                            Method getService = // getDefaults[29];
                                    serviceManagerClass.getMethod("getService", String.class);
                            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
                            Binder tmpBinder = new Binder();
                            tmpBinder.attachInterface(null, "fake");
                            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
                            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
                            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
                            telephonyObject = serviceMethod.invoke(null, retbinder);
                            telephonyEndCall = telephonyClass.getMethod("endCall");
                            telephonyEndCall.invoke(telephonyObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(String.valueOf(Calling.this),"FATAL ERROR: could not connect to telephony subsystem");
                            Log.e(String.valueOf(Calling.this), "Exception object: " + e);
                        }
                    }

               }

            }
        });
    }



    private void changeFabButtonImage() {
        fab.setBackgroundColor(Color.parseColor("#03A9F4"));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.answer_call, this.getTheme()));
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.answer_call));
        }
    }

    private void catchOutGoindCallInstance() {
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            outBound(extras.getString("OUTBOUND_NUMBER"));
        }
    }

    private void initializeWidgets() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        txtContact = (TextView) findViewById(R.id.txtContact);
    }

    public int checkSelfPermission(String callPhone) {
        return 0;
    }

    public void closeActivity(){
        Toast.makeText(Calling.this, "Closing", Toast.LENGTH_SHORT).show();
    }

    private void outBound(String outbound_number) {
        Toast.makeText(Calling.this, "OUTBOUND ^_^" +outbound_number, Toast.LENGTH_SHORT).show();
    }
}
