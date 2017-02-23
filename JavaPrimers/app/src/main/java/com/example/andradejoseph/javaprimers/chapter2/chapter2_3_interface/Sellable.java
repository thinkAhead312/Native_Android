package com.example.andradejoseph.javaprimers.chapter2.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

/**
 * interface for objects that can be sold
 */
public interface Sellable {
    /**
     * Returns a description of the object
     */
    public String description();

    /**
     * Returns the list price in cents
     */
    public int listPrice();

    /**
     * Returns the lowest prioce in cents we will accept
     */
    public int lowestPrice();





}
