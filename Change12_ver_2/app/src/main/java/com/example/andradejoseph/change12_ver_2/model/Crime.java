package com.example.andradejoseph.change12_ver_2.model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ANDRADEJOSEPH on 3/22/2017.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        this(UUID.randomUUID());
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return  mTitle;
    }

    public Date getDate() {
        return  mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public UUID getId() {
        return mId;
    }
}
