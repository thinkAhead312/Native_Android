package com.example.andradejoseph.sectionedrecyclerview.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andradejoseph.sectionedrecyclerview.R;
import com.example.andradejoseph.sectionedrecyclerview.model.ItemObject;
import com.example.andradejoseph.sectionedrecyclerview.viewholder.HeaderViewHolder;
import com.example.andradejoseph.sectionedrecyclerview.viewholder.ItemViewHolder;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by ANDRADEJOSEPH on 7/3/2017.
 */

public class HeaderRecyclerViewSection extends StatelessSection {

    private static final String TAG = HeaderRecyclerViewSection.class.getSimpleName();
    private String title;
    private List<ItemObject> list;

    public HeaderRecyclerViewSection(String title, List<ItemObject> list) {
        super(R.layout.header_layout, R.layout.item_layout);



        this.title = title;
        this.list = list;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }



    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder iHolder = (ItemViewHolder)holder;
        iHolder.itemContent.setText(list.get(position).getContents());
    }



    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder)holder;
        hHolder.headerTitle.setText(title);
    }




}
