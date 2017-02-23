package com.example.andradejoseph.javaprimers.chapter2_classes;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class ArithmeticProgression extends Progression {
    protected long increment;

    /**
     * Construct progression 0,1,2 ...
     */
    public ArithmeticProgression() {
        this(1,0); //start at 0 with increment of 1
    }

    /**
     * Construct progression 0, stepsize, 2 * stepsize
     * @param stepsize
     */
    public ArithmeticProgression(long stepsize) {
        this(stepsize, 0); //start at 0
    }

    /**
     * Construct arithmetic progression with arbitrary start and increment
     * @param stepsize
     * @param start
     */
    public ArithmeticProgression(long stepsize, long start) {
        super(start);
        increment = stepsize;
    }

    @Override
    protected void advance() {
        current += increment;
    }
}
