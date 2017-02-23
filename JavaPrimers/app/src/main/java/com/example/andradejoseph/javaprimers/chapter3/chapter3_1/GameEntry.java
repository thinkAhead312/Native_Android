package com.example.andradejoseph.javaprimers.chapter3.chapter3_1;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */


public class GameEntry {
    private String mName;        // name of the person earning this score
    private int mScore;          // the score value


    /**
     * Constructs a game entry with given parameter
     * @param name
     * @param score
     */
    public GameEntry(String name, int score) {
        mName = name;
        mScore = score;
    }

    /**
     * returns the name field
     */
    public String getName() {return  mName;}

    /**
     *  Return the score field
     */
    public int getScore() {return mScore; }

    public String toString() {
        return "(" + mName + ",. " + mScore + ")";
    }
}
