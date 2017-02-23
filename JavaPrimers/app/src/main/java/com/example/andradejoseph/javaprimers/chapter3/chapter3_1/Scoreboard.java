package com.example.andradejoseph.javaprimers.chapter3.chapter3_1;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

/**
 * Class for storing high scores in an array in nondecreasing order
 */
public class Scoreboard {
    private int numEntries = 0;             //number of actual entries
    private GameEntry[] board;              //array of game entries (name & scores)

    /**
     * Constructs empty scoreboard with given capacity for storing entries
     * The beggining of a Scoreboard class for maintaining a set of scores as GameEntry objects.
     * @param capacity
     */
    public Scoreboard(int capacity) {
        board = new GameEntry[capacity];
    }


}
