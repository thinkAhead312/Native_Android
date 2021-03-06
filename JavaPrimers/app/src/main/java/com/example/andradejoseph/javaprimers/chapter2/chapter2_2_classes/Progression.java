package com.example.andradejoseph.javaprimers.chapter2.chapter2_2_classes;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

/**
 * Generates simple progression. By default: 0, 1, 2, ...
 */
public class Progression {
    //instance variable
    protected long current;

    /**
     * Constructs a progression starting at zero
     */
    public Progression() {this(0);}

    /**
     * Construct progession with a given start value
     * @param start
     */
    public Progression(long start) {
        current = start;
    }

    /**
     *  Returns the next value of the progression
     * @return answer
     */
    public long nextValue() {
        long answer = current;
        advance(); //this protected call is responsible for advancing the current value
        return  answer;
    }

    /**
     * Advances the current value to the next value of the progression
     */
    protected void advance() {
        current++;
    }

    public void printProgression(int n) {
        System.out.println(nextValue()); //print first value without leading space

        for (int j = 1; j < n; j++) {
            System.out.println(" " + nextValue()); //print leading space before others
            System.out.println();   //end the line
        }
    }
}
