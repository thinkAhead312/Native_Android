package com.example.andradejoseph.javaprimers.chapter2_3_interface;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class TestChapter2_3_interfaces {

    public TestChapter2_3_interfaces() {
        BoxedItem2 boxedItem2 = new BoxedItem2("Joseph", 10000, 60, false);
        System.out.println(boxedItem2.description());

        Photograph photograph = new Photograph("Andrade", 10000, true);
        System.out.println(photograph.description());


    }
}
