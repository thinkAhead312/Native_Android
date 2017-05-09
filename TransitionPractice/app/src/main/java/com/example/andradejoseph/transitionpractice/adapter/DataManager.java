package com.example.andradejoseph.transitionpractice.adapter;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andradejoseph.transitionpractice.R;
import com.example.andradejoseph.transitionpractice.model.Contact;

/**
 * Created by ANDRADEJOSEPH on 4/7/2017.
 */

public class DataManager extends RecyclerView.Adapter<DataManager.RecyclerViewHolder>{
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
// get the single element from the main array
        final Contact contact = Contact.CONTACTS[position];
        // Set the values
        viewHolder.mName.setText(contact.get(Contact.Field.NAME));
        viewHolder.mPhone.setText(contact.get(Contact.Field.PHONE));
        // Set the color of the shape
        GradientDrawable bgShape = (GradientDrawable) viewHolder.mCircle.getBackground();
//        bgShape.setColor(Color.parseColor(contact.get(Contact.Field.COLOR)));
    }

    @Override
    public int getItemCount() {
        return Contact.CONTACTS.length;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mName, mPhone;
        View mCircle;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.CONTACT_name);
            mPhone = (TextView) itemView.findViewById(R.id.CONTACT_phone);
            mCircle = itemView.findViewById(R.id.CONTACT_circle);
        }
    }


}
