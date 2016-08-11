package com.example.dna.dialerapp;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.model.Constants;
import com.example.dna.dialerapp.receivers.IncomingCall;
import com.example.dna.dialerapp.receivers.SmsReceiver;
import com.example.dna.dialerapp.settings.SipSettings;

public class DialPad extends AppCompatActivity implements View.OnClickListener {
    private EditText screen;
    private String phoneNum="";
    private Toolbar toolbar;
    SipAndroid sipAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_pad);
        sipAndroid = SipAndroid.getInstance();

        initializeView();
        phoneNum = getIntent().getExtras().getString("Users_ID");
        display(phoneNum);
    }

    private void initializeView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        screen = (EditText) findViewById(R.id.editDial);
        int idList[] = {R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
                R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnStar, R.id.btnSharp, R.id.btnCall, R.id.btnDel};

        for (int d : idList) {
            View v = (View) findViewById(d);
            v.setOnClickListener(this);
        }
    }
    public void display(String val) {
        try {
            screen.append(val);
        } catch (Exception e){
            Toast.makeText(DialPad.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public int checkSelfPermission(String callPhone) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                display("1");
                break;
            case R.id.btn2:
                display("2");
                break;
            case R.id.btn3:
                display("3");
                break;
            case R.id.btn4:
                display("4");
                break;
            case R.id.btn5:
                display("5");
                break;
            case R.id.btn6:
                display("6");
                break;
            case R.id.btn7:
                display("7");
                break;
            case R.id.btn8:
                display("8");
                break;
            case R.id.btn9:
                display("9");
                break;
            case R.id.btn0:
                display("0");
                break;
            case R.id.btnStar:
                display("*");
                break;
            case R.id.btnSharp:
                display("#");
                break;
            case R.id.btnDel:
                if(screen.getText().toString().length() >= 1 ) {
                    String newScreen = screen.getText().toString().substring(0, screen.getText().toString().length()-1);
                    screen.setText(newScreen);
                }
                break;
            case R.id.btnCall:
                chooseTypeOfCall();

                break;
            default:
                break;
        }
    }

    private void chooseTypeOfCall() {
        final CharSequence callType[] = new CharSequence[] {"Telephony", "Sip"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick type of Call");
        builder.setItems(callType, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // the user clicked on colors[which]
                Toast.makeText(DialPad.this, callType[which], Toast.LENGTH_SHORT).show();

                if (callType[which].equals("Telephony")) {
                    telephonyCall();
                } else if (callType[which].equals("Sip")) {
                    sipCall();
                }

            }
        });
        builder.show();
    }

    private void sipCall() {
        Toast.makeText(DialPad.this, "Sip", Toast.LENGTH_SHORT).show();
        sipAndroid.OutBoundCall(screen.getText().toString().trim(), this);
        IntentStartActivity.intentCallingActivity(DialPad.this,Constants.Call_State,Constants.Sip_Outgoing);
    }

    private void telephonyCall() {
        Toast.makeText(DialPad.this, "Telephony", Toast.LENGTH_SHORT).show();
        if (screen.getText().toString().isEmpty()) {
            Toast.makeText(DialPad.this, "Enter Some Digit", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Uri.encode(screen.getText().toString().trim())));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
                IntentStartActivity.intentCallingActivity(DialPad.this, Constants.Call_State, Constants.Telephony_Outgoing);
            } catch (ActivityNotFoundException activityException) {
                Log.e("helloandroide", "Call failed", activityException);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        menu.add(0, Constants.SET_AUTH_INFO, 0, "Edit your SIP Info.");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        switch (item.getItemId()) {
            case Constants.SET_AUTH_INFO:
                updatePreferences();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void updatePreferences() {
        Intent settingsActivity = new Intent(getBaseContext(),
                SipSettings.class);
        startActivity(settingsActivity);
    }




}
