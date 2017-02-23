package com.example.andradejoseph.javaprimers.chapter2_2_classes;

import com.example.andradejoseph.javaprimers.chapter2_3_abstract_class.AbstractProgression;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class GeometricProgression extends AbstractProgression {
    protected long base;

    /** Constructs progression 1, 2, 4, 8, 16, ... */
    public GeometricProgression( ) { this(2, 1); } // start at 1 with base of 2

    /** Constructs progression 1, b, bˆ2, bˆ3, bˆ4, ... for base b. */
    public GeometricProgression(long b) { this(b, 1); }

    /** Constructs geometric progression with arbitrary base and start. */
    public GeometricProgression(long b, long start) {
        super(start);
         base = b;
    }

    /** Multiplies the current value by the geometric base. */
    @Override
    protected void advance() {
        mCurrent *= base;        //multiply current vt the geometric base
    }
}
