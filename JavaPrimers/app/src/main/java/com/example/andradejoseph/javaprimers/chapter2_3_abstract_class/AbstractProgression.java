package com.example.andradejoseph.javaprimers.chapter2_3_abstract_class;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public abstract  class AbstractProgression {
    protected long mCurrent;
    public AbstractProgression() {this(0);}
    public AbstractProgression(long start) {mCurrent = start; }

    public long nextValue() {
        long answer = mCurrent;
        advance(); //this protected call is reponsible for advancingthe current valuoe
        return answer;
    }

    public void printProgression(int n) { //this is a concrete method
        System.out.print(nextValue());    //print first valuoe without leading space
        for (int j = 1; j< n; j++) {
            System.out.print(" " + nextValue()); //print leading space before others
        }
        System.out.println();                //end of the line

    }

    protected abstract void advance();  //notice the lack of method body
}

