package com.example.andradejoseph.javaprimers.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public interface Insurable extends Sellable, Transportable{
    /**
     * Returns insured value in cents
     */
    public int insuredValue();
}
