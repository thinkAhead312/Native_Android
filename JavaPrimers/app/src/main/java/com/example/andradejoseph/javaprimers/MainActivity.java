package com.example.andradejoseph.javaprimers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andradejoseph.javaprimers.chapter2.chapter2_2_classes.TestProgression;
import com.example.andradejoseph.javaprimers.chapter2.chapter2_3_interface.TestChapter2_3_interfaces;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        CreditCard[] wallet = new CreditCard[3];
//        wallet[0] = new CreditCard("John Brownman", "California Savings", "5391 0375 9387 5309", 5000);
//        wallet[1] = new CreditCard("John Bowman", "California Federal", "3485 0399 3395 1954", 3500);
//        wallet[2] = new CreditCard("John Bowman", "California Finance","5391 0375 9387 5309", 2500, 300);
//
//        for(int val = 1 ;val <= 16; val ++) {
//            wallet[0].charge(3*val);
//            wallet[1].charge(2*val);
//            wallet[2].charge(val);
//        }
//
//        for (CreditCard card : wallet) {
//            CreditCard.printSummary(card);
//            while (card.getBalance() > 200.00) {
//                card.makePayment(200);
//                System.out.println("New balance = " + card.getBalance( ));
//            }
//        }


        new TestProgression();
        new TestChapter2_3_interfaces();

    }
}
