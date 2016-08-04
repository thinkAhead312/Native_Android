package com.example.dna.dialerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dna.dialerapp.helper.DateFormatter;
import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.ViewSms;
import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.helper.NameChecker;
import com.example.dna.dialerapp.model.Sms;
import com.example.dna.dialerapp.receivers.IncomingCall;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dna on 7/21/16.
 */
public class SmsAdapter extends ArrayAdapter<Sms> implements UpdateListView{

    // List context
    private Context context;
    // List values
    private List<Sms> _smsItem = null;

    public SmsAdapter(Context context, int resourceId,
                          List<Sms> smsItem) {
        super(context, resourceId, smsItem);

        this._smsItem = smsItem;
        this.context = context;
    }


    @Override
    public void updatingListView() {

    }

    /*private view holder class*/
    private class ViewHolder {

        TextView txtContact;
        TextView txtMessage;
        TextView txtPhone;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        Sms smsItem = _smsItem.get(position);
        String display = "";
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.sms_row, null);
            holder = new ViewHolder();
            holder.txtMessage = (TextView) convertView.findViewById(R.id.lblMessage);
            holder.txtContact = (TextView) convertView.findViewById(R.id.lblContact);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.lblPhone);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtContact.setText(NameChecker.nameChecker(smsItem.getName(), smsItem.getNumber()) + " " + DateFormatter.formatDate(smsItem.getDate()));
        holder.txtMessage.setText(smsItem.getBody());
        holder.txtPhone.setText(smsItem.getNumber());
        return convertView;
    }

    @Override
    public void add(Sms object) {
        super.add(object);
    }
}
