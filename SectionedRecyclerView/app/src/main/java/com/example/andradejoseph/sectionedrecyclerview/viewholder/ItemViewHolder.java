package com.example.andradejoseph.sectionedrecyclerview.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.andradejoseph.sectionedrecyclerview.R;

/**
 * Created by ANDRADEJOSEPH on 7/3/2017.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public TextView itemContent;
    public ItemViewHolder(View itemView) {
        super(itemView);
        itemContent = (TextView)itemView.findViewById(R.id.item_content);
    }
}
