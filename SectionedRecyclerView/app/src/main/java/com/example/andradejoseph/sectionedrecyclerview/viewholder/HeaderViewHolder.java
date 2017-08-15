package com.example.andradejoseph.sectionedrecyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andradejoseph.sectionedrecyclerview.R;

/**
 * Created by ANDRADEJOSEPH on 7/3/2017.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView headerTitle;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerTitle = (TextView)itemView.findViewById(R.id.header_id);
    }
}
