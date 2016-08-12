package com.example.dna.dialerapp.helper;

/**
 * Created by dna on 8/4/16.
 */
public class PhoneNumberFormatter {
    public void PhoneNumberFormatter() {}

    public static String phoneNumberFormat(String phNumber) {
        phNumber = phNumber.replace(" ", "");
        /*if (phNumber.charAt(0)=='0') {
            phNumber = phNumber.substring(1);
            phNumber = "+63" + phNumber;
        }*/

        return phNumber;
    }
}
