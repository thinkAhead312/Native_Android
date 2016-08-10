package com.example.glenda_pc.change12_activity.Model;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.glenda_pc.change12_activity.R;

import java.util.Calendar;


/**
 * Created by Glenda-PC on 1/2/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TimePickerFragment extends DialogFragment  implements TimePickerDialog.OnTimeSetListener {
    int i=0;
    public static int fromHour, endHour, fromMinute, endMinute;
    public TimePickerFragment(int i) {
        this.i=i;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Use the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //Create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    //onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        //Do something with the user chosen time
        //Get reference of host activity (XML Layout File) TextView widget
        Button btnFromTime = (Button) getActivity().findViewById(R.id.btnFromTime);
        Button btnEndTime = (Button) getActivity().findViewById(R.id.btnEndTime);
        //Set a message for user
        //Get the AM or PM for current time
        String hourStr = "", minStr="";

        //Display the user changed time on TextView
        if(i==1) {
            fromHour=hourOfDay;
            fromMinute=minute;
            hourStr = String.valueOf(hourOfDay);
            minStr = String.valueOf(minute);
            if(hourStr.length()==1){
                hourStr = "0" +hourStr;
            }
            if(minStr.length()==1){
                minStr = "0" +minStr;
            }
            btnFromTime.setText(hourStr
                    + ":" +minStr  );
        }else if (i==2){
            endHour = hourOfDay;
            endMinute= minute;
            hourStr = String.valueOf(hourOfDay);
            minStr = String.valueOf(minute);
            if(hourStr.length()==1){
                hourStr = "0" +hourStr;
            }
            if(minStr.length()==1){
                minStr = "0" +minStr;
            }
            btnEndTime.setText(hourStr
                    + ":" + minStr  );

        }
    }
}
