package com.example.dna.dialerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.ViewSms;
import com.example.dna.dialerapp.model.CallLogs;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.model.Sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by dna on 8/2/16.
 */
public class CallLogsAdapter extends ArrayAdapter<CallLogs> {

    List<CallLogs> callLogsItem = null;
    Context context;


    public CallLogsAdapter(Context context, int resourceId,
                          List<CallLogs> callLogsItem) {
        super(context, resourceId, callLogsItem);
        this.callLogsItem = callLogsItem;
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView txtCallName, txtCallType, txtCallDuration, txtcallDate, txtCallNumber;
        LinearLayout call_logs_Layout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        CallLogs logsItem = getItem(position);
        String display = "";
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.call_log_row, null);
            holder = new ViewHolder();
            holder.txtCallName = (TextView) convertView.findViewById(R.id.txtCallName);
            holder.txtCallType = (TextView) convertView.findViewById(R.id.txtCallType);
            holder.txtCallDuration = (TextView) convertView.findViewById(R.id.txtCallDuration);
            holder.txtcallDate = (TextView) convertView.findViewById(R.id.txtCallDate);
            holder.txtCallNumber = (TextView) convertView.findViewById(R.id.txtCallNumber);
            holder.call_logs_Layout = (LinearLayout) convertView.findViewById(R.id.call_logs_Layout);
            convertView.setTag(holder);
        }
        else
            holder = (ViewHolder) convertView.getTag();


        display = logsItem.getContactName();
        if(display == null) {
            holder.txtCallName.setText(logsItem.getNumber());
        }
        else {
            holder.txtCallName.setText(display);
        }

        int callTypeCode = Integer.parseInt(logsItem.getType());
        switch(callTypeCode) {
            case CallLog.Calls.OUTGOING_TYPE:
                holder.txtCallType.setText("OutGoing");
                break;
            case CallLog.Calls.INCOMING_TYPE:
                holder.txtCallType.setText("InComing");
                break;
            case CallLog.Calls.MISSED_TYPE:
                holder.txtCallType.setText("MIssed");
                break;
        }

        holder.txtCallDuration.setText(logsItem.getDuration()+"(seconds)");
        Date callDayTime = new Date(Long.valueOf(logsItem.getDate()));
        holder.txtcallDate.setText(String.valueOf(callDayTime));
        return convertView;
    }

}
