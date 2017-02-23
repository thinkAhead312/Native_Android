package com.example.andradejoseph.javaprimers.chapter2_classes;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class FibonacciProgression extends Progression {

    protected long prev;

    /**
     * Construct traditional Fibonacci, starting 0,1,1,2,3
     */
    public FibonacciProgression( ) { this(0, 1); }

    public FibonacciProgression(long first, long second) {
        super(first);
        prev = second - first;  //fictitious value preceding the first
    }

    /**
     * Replaces (prev current) with (current, current + prev)
     */
    @Override
    protected void advance() {
        long temp = prev;
        prev = current;
        current += temp;

    }
}
