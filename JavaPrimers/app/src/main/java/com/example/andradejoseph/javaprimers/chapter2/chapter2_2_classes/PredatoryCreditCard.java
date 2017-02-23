package com.example.andradejoseph.javaprimers.chapter2.chapter2_2_classes;

import com.example.andradejoseph.javaprimers.chapter1_7_classes.CreditCard;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class PredatoryCreditCard extends CreditCard{
    //Additional instance variable
    private double mApr;     //anual percentage rate

    //Constructor for this class
    public PredatoryCreditCard(String customer, String bank, String account, int limit, double balance, double rate) {
        super(customer, bank, account, limit, balance); //initialize the superclass attributes
        mApr = rate;
    }

    //A new method for assessing monthly interest charges
    public void processMonth() {
        if(mBalance >0 ) { //only charge interest on a postive balance
            double monthlyFactor = Math.pow(1 + mApr, 1.0/12); //compute monthly rate
            mBalance *= monthlyFactor;                         //assess interest
        }
    }

    @Override
    public boolean charge(double price) {
        boolean isSuccesss = super.charge(price); //call inhereted method
        if(!isSuccesss) {
            mBalance += 5;  //assess a $5 penalty
        }
        return isSuccesss;
    }
}
