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
import com.example.dna.dialerapp.model.Contact;
import com.example.dna.dialerapp.R;

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
            //holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        display = contactItem.getName();
        holder.txtName.setText(display);
        holder.txtNumber.setText(contactItem.getNumber());

        final ViewHolder finalHolder = holder;
        holder.contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNum = finalHolder.txtNumber.getText().toString().trim();
                try {
                    Intent intent = new Intent(context,DialPad.class);
                    intent.putExtra("Users_ID", phoneNum);
                    context.startActivity(intent);
                } catch (ActivityNotFoundException activityException) {
                    Log.e("helloandroide", "Call failed", activityException);
                }
            }
        });

        return convertView;
    }

    private int checkSelfPermission(String callPhone) {
        return 0;
    }


}








/* Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + Uri.encode(phoneNum.trim())));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //callIntent.setPackage("com.android.server.telecom");
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for Activity#requestPermissions for more details.
                        return;
                    }*/
//context.startActivity(callIntent);
