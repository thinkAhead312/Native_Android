package com.example.andradejoseph.javaprimers.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

/**
 * Class for photograph that can be sold
 */
public class Photograph implements Sellable{

    private String mDescript;        //description of the photo
    private int mPrice;               //price we are setting
    private boolean mColor;           //true if photo is in color

    public Photograph(String desc, int p, boolean c) {
        mDescript = desc;
        mPrice = p;
        mColor = c;
    }

    @Override
    public String description() {
        return mDescript;
    }

    @Override
    public int listPrice() {
        return mPrice;
    }

    @Override
    public int lowestPrice() {
        return mPrice/2;
    }

    public boolean isColor() {return mColor; }
}
