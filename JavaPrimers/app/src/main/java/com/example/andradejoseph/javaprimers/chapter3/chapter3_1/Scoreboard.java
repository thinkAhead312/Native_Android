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


    /**
     * Attempt to add a new score to the collection (it it is high enough)
     * @param e
     */
    public void add(GameEntry e) {
        int newScore = e.getScore();

        //is the new entry e really a high score
        if (numEntries < board.length || newScore > board[numEntries-1].getScore()) {
            if (numEntries < board.length) {    //no score drops from the board
                numEntries++;                   //so overall number increases
                //shift any lower scores rightward to make room for tne new entry
                int j = numEntries -1;
                while (j > 0 && board[j-1].getScore() < newScore) {
                    board[j] = board[j-1];          //shift any entry from j-1 to j
                    j--;                            // and decrement j
                }
                board[j] = e;                       //when done, add new entry

            }
        }
    }

    public GameEntry remove(int i) throws IndexOutOfBoundsException{
        if (i < 0 || i >= numEntries) {
            throw  new IndexOutOfBoundsException("Invalid index: " + i);
        }
        GameEntry temp = board[i];      //save the object to be removed
        for (int j = i; j < numEntries - 1; j++)    //count up from i (not down)
            board[j] = board[j + 1];                //move one cell to the left; replace the value of the replaced index from the right
        board[numEntries -1 ] = null;               //null out the old last score
        numEntries--;
        return temp;                                //return the removed object
    }







}
