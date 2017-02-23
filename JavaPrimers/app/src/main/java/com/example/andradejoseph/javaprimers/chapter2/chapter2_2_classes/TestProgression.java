package com.example.andradejoseph.javaprimers.chapter2.chapter2_2_classes;

import com.example.andradejoseph.javaprimers.chapter2.chapter2_3_abstract_class.AbstractProgression;

/**
 * Created by ANDRADEJOSEPH on 2/23/2017.
 */

public class TestProgression {


    public TestProgression() {
        AbstractProgression prog;

        //test Arithmetic Progression
        System.out.println("Arithmetic progression with default increment: ");
        prog = new ArithmeticProgression();
        prog.printProgression(10);
        System.out.println("Arithmetic progression with increment 5: ");
        prog = new ArithmeticProgression(5);
        prog.printProgression(10);
        System.out.println("Arithmetic progression with start 2: ");
        prog = new ArithmeticProgression(5, 2);
        prog.printProgression(10);

        //test GeometricProgression
        System.out.println("Geometric progression with default base: ");
        prog = new GeometricProgression( );
        prog.printProgression(10);
        System.out.println("Geometric progression with base 3: ");
        prog = new GeometricProgression(3);
        prog.printProgression(10);


        //test FibonacciProgression
        System.out.println("Fibonacci progression with default start values: ");
        prog = new FibonacciProgression( );
        prog.printProgression(10);
        System.out.println("Fibonacci progression with start values 4 and 6: ");
        prog = new FibonacciProgression(4, 6);
        prog.printProgression(8);

    }
}
