package com.example.dna.dialerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dna.dialerapp.R;
import com.example.dna.dialerapp.fragment.SmsList;
import com.example.dna.dialerapp.helper.DateFormatter;
import com.example.dna.dialerapp.helper.NameChecker;
import com.example.dna.dialerapp.model.Sms;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by dna on 8/4/16.
 */
public class CompleteAdapter extends BaseAdapter{
    private Activity mContext;
    private List<Sms> smsList;
    private LayoutInflater mlayoutInflater = null;

    public CompleteAdapter(Activity context, List<Sms> smsList) {
        mContext = context;
        this.smsList = smsList;
        mlayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return smsList.size();
    }

    @Override
    public Object getItem(int position) {
        return smsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Sms smsItem = smsList.get(position);
        View v = convertView;
        CompleteViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.sms_row, null);
            viewHolder = new CompleteViewHolder(v);
            v.setTag(viewHolder);
        } else {
            viewHolder = (CompleteViewHolder) v.getTag();
        }
        viewHolder.txtContact.setText(NameChecker.nameChecker(smsItem.getName(), smsItem.getNumber()) + " " + DateFormatter.formatDate(smsItem.getDate()));
        viewHolder.txtMessage.setText(smsItem.getBody());
        viewHolder.txtPhone.setText(smsItem.getNumber());
        return v;
    }
    private class CompleteViewHolder {
        public TextView txtContact;
        public TextView txtMessage;
        public TextView txtPhone;

        public CompleteViewHolder(View base) {
            txtMessage = (TextView) base.findViewById(R.id.lblMessage);
            txtContact = (TextView) base.findViewById(R.id.lblContact);
            txtPhone = (TextView) base.findViewById(R.id.lblPhone);
        }
    }
}
