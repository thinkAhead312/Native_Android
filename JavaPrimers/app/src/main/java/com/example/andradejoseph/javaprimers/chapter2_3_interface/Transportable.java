package com.example.andradejoseph.javaprimers.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

/**
 * Interface for objects that can be transported
 */
public interface Transportable {
    /**
     * Returns the weight in grams
     */
    public int weight();

    /**
     * Returns whether the object is hazardous
     */
    public boolean isHazardous();
}
