package com.example.dna.dialerapp.adapter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dna.dialerapp.DialPad;
import com.example.dna.dialerapp.fragment.ContactList;
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dna on 7/21/16.
 */
public class ContactAdapter extends ArrayAdapter<Contact> {
    //declaring array list of contacts

    List<Contact> contactItem = null;
    Context context;

    public ContactAdapter(Context context, int resourceId,
                          List<Contact> contactItem) {
        super(context, resourceId, contactItem);


        this.contactItem = contactItem;
        this.context = context;
    }


    /*private view holder class*/
    private class ViewHolder {
        TextView txtName;
        TextView txtNumber;
        RelativeLayout contactLayout;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        Contact contactItem = getItem(position);
        String display = "";
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.contact_row, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            holder.txtNumber = (TextView) convertView.findViewById(R.id.txtPhone);
            holder.contactLayout = (RelativeLayout) convertView.findViewById(R.id.contactLayout);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtName.setText(contactItem.getName());
        holder.txtNumber.setText(contactItem.getNumber());
        return convertView;
    }
}

