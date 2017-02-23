package com.example.andradejoseph.javaprimers.chapter2.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class BoxedItem2 implements Insurable{

    private String mDescript;       //description of this item
    private int mPrice;             //list price in cents
    private int mWeight;            //weight in grams
    private boolean mHaz;           //true if object is hazardous
    private int mHeight;            //box height in centi
    private int mWidth;             //box width in centi
    private int mDepth;             //box depth in centi

    /**
     * Constructor
     * @param desc
     * @param p
     * @param w
     * @param h
     */
    public BoxedItem2(String desc, int p, int w, boolean h) {
        mDescript = desc;
        mPrice = p;
        mWeight = w;
        mHaz  = h;
    }



    @Override
    public int insuredValue() {
        return mPrice * 2;
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

    @Override
    public int weight() {
        return mWeight;
    }

    @Override
    public boolean isHazardous() {
        return mHaz;
    }

    /**
     * Methods that set the dimension of the cargo box
     * @param h height of the box
     * @param w width of the box
     * @param d depth of the box
     */
    public void setBox(int h, int w, int d) {
        mHeight = h;
        mWidth = w;
        mDepth = d;
    }
}
