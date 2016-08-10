package com.example.glenda_pc.change12_activity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Model.TimePickerFragment;

import java.util.ArrayList;
import java.util.Calendar;

public class Add_CalendarSched extends AppCompatActivity {

    EditText edEvent, edLocation, edDescription;
    Button btnFromDate, btnFromTime, btnEndDate, btnEndTime,btnCalendarSave, btnCalendarCancel;
    SwitchCompat switchCloseOpenCell;
    CheckBox chkAllDay;
    String cellStr="";
    private ArrayList<String> listContactNumber = new ArrayList<>();
    private Calendar calendar;
    private int year, month, day,dateFlag=0, fromYear, fromMonth, fromDay, endYear,endMonth,endDay;
    StringBuilder calendarMessage = new StringBuilder();
    int disciple_consolidate_Flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__calendar_sched);
        Intent intent = getIntent();
        cellStr = intent.getStringExtra("cellStr");
        listContactNumber =  getIntent().getStringArrayListExtra("key");

        disciple_consolidate_Flag = intent.getIntExtra("dipFlag", 0);
        edEvent = (EditText) findViewById(R.id.edEvent);
        edLocation=(EditText) findViewById(R.id.edLocation);
        edDescription = (EditText) findViewById(R.id.edDescription);
        btnFromDate = (Button) findViewById(R.id.btnFromDate);
        btnFromTime = (Button) findViewById(R.id.btnFromTime);
        btnEndDate = (Button) findViewById(R.id.btnEndDate);
        btnEndTime = (Button) findViewById(R.id.btnEndTime);
        btnCalendarSave = (Button) findViewById(R.id.btnCalendarSave);
        btnCalendarCancel = (Button) findViewById(R.id.btnCalendarSkip);
      //  switchCloseOpenCell = (SwitchCompat) findViewById(R.id.switchCalendarCloseCell);
        chkAllDay = (CheckBox)findViewById(R.id.checkALlDay);

        calendar = Calendar.getInstance();  //Calendar get instance
        year = calendar.get(Calendar.YEAR); //
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        edDescription.setText(cellStr);
        toolBarSet();
    }

    public void clkFromDate(View v){
        if(v.equals(btnFromDate)){
            dateFlag=0;
            showDialog(999);
        }
    }
    public void clkEndDate(View v){
        if(v.equals(btnEndDate)){
            dateFlag=1;
            showDialog(999);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void clkFromTime(View v){
        if(v.equals(btnFromTime)){
            DialogFragment newFragment = new TimePickerFragment(1);
            newFragment.show(getFragmentManager(), "TimePicker");
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void clkEndTime(View v){
        if(v.equals(btnEndTime)){
            DialogFragment newFragment = new TimePickerFragment(2);
            newFragment.show(getFragmentManager(), "TimePicker");
        }
    }

    public void clikCalendarSave(View v){
        if(v.equals(btnCalendarSave)){
            final ProgressDialog loading = ProgressDialog.show(this, "Loading Data", "Please wait...", false, false);
            saveToCalendar(loading);
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(calendarMessage.toString());
            for (String contacts : listContactNumber) {
                smsManager.sendMultipartTextMessage(contacts, null, parts, null, null);
            }
            finish();
        }
    }
    public void clkCalendarSkip(View v){
        if(v.equals(btnCalendarCancel)){

            if(disciple_consolidate_Flag==1) {
                Intent intent = new Intent(Add_CalendarSched.this, View_Consolidates_Module.class);
                intent.putExtra("dipFlag", 1);
                startActivity(intent);
                //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }else{
                Intent intent = new Intent(Add_CalendarSched.this, View_Disciples_Module.class);
                intent.putExtra("dipFlag", 1);
                startActivity(intent);
                //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        }
    }

    private void saveToCalendar(ProgressDialog loading) {
        String[] calendarsProjection = {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.ACCOUNT_NAME
        };

        String calName;
        String calId = null;
        Uri calendars= Uri.parse("content://com.android.calendar/events");
        Cursor managedCursor = managedQuery(calendars, calendarsProjection, null, null, null);
        if (managedCursor.moveToFirst())
        {

            int nameColumn = managedCursor.getColumnIndex("account_name");
            int idColumn = managedCursor.getColumnIndex("_id");
            do
            {
                calName = managedCursor.getString(nameColumn);
                calId = managedCursor.getString(idColumn);
            }
            while (managedCursor.moveToNext());
        }


        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(fromYear, fromMonth-1, fromDay, TimePickerFragment.fromHour, TimePickerFragment.fromMinute,0);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(endYear, endMonth-1, endDay, TimePickerFragment.endHour, TimePickerFragment.endMinute, 0);
        endMillis = endTime.getTimeInMillis();
        System.out.println("Date start :"+startMillis);
        System.out.println("Date start :"+endMillis);

        // Insert Event
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.TITLE,edEvent.getText().toString().trim());
        values.put(CalendarContract.Events.DESCRIPTION, edDescription.getText().toString().trim());
        values.put(CalendarContract.Events.CALENDAR_ID, 1 );
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "UTC");
        values.put(CalendarContract.Events.EVENT_LOCATION, edLocation.getText().toString().trim());
        Uri uri = cr.insert(Uri.parse("content://com.android.calendar/events"), values);
        loading.dismiss();
        Toast.makeText(Add_CalendarSched.this, "Save Date", Toast.LENGTH_SHORT).show();

        calendarMessage.append("Announcement:" + "\n");
        calendarMessage.append("Event: " + edEvent.getText().toString().trim() + "\n");
        calendarMessage.append("Location: " + edLocation.getText().toString().trim() + "\n");
        calendarMessage.append("Description: "+ edDescription.getText().toString().trim()  +"\n");
        calendarMessage.append("From:" + "\n");
        calendarMessage.append("Date : "+ fromDay +"/" + (fromMonth) +"/" + fromYear  +"\n");
        calendarMessage.append("Time : "+ TimePickerFragment.fromHour +":" + TimePickerFragment.fromMinute  +"\n");
        calendarMessage.append("To : "+"\n");
        calendarMessage.append("Date : "+ endDay +"/" + (endMonth) +"/" + endYear  +"\n");
        calendarMessage.append("Time : "+ TimePickerFragment.endHour +":" + TimePickerFragment.endMinute  +"\n");
    }

    private void showDate(int year, int month, int day) {                                           ////////////////////////////////////////////////////////////////////////////
        String yearStr="", monthStr="", dayStr="";

        if(dateFlag==0) {
            fromYear=year;
            fromMonth=month;
            fromDay=day;
            monthStr = String.valueOf(month);
            dayStr = String.valueOf(day);
            if(String.valueOf(month).length()==1){
                monthStr = "0" + String.valueOf(month);
            }
            if(String.valueOf(day).length()==1){
                dayStr = "0" + String.valueOf(day);
            }



            btnFromDate.setText(new StringBuilder().append(dayStr).append("-")                         ///////////////////
                    .append(monthStr).append("-").append(year));                                        ///////////////////
        }else{
            endYear=year;
            endMonth=month;
            endDay=day;
            monthStr = String.valueOf(month);
            dayStr = String.valueOf(day);
            if(String.valueOf(month).length()==1){
                monthStr = "0" + String.valueOf(month);
            }
            if(String.valueOf(day).length()==1){
                dayStr = "0" + String.valueOf(day);
            }

            btnEndDate.setText(new StringBuilder().append(dayStr).append("-")                          ///////////////////
                    .append(monthStr).append("-").append(year));
        }
    }                                                                                               ///////////////////
    @Override                                                                                       ///////////////////  CALENDAR DATE PICKER
    protected Dialog onCreateDialog(int id) {                                                       ///////////////////
        // TODO Auto-generated method stub                                                          ////////////////////////////////////////////////////////////////////////////
        if (id == 999) {                                                                            ////////////////////////////////////////////////////////////////////////////
            return new DatePickerDialog(this, myDateListener, year, month, day);                    ////////////////////////////////////////////////////////////////////////////
        }                                                                                           ////////////////////////////////////////////////////////////////////////////
        return null;                                                                                ////////////////////////////////////////////////////////////////////////////
    }                                                                                               ////////////////////////////////////////////////////////////////////////////
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {///////////////////
        @Override                                                                                   ///////////////////
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {       ////////////////////////////////////////////////////////////////////////////
            showDate(arg1, arg2+1, arg3);
        }                                                                                           ///////////////////
    };

    private void toolBarSet() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Event");

    }


}
