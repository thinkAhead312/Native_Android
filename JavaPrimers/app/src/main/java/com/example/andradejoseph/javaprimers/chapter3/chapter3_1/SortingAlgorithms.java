package com.example.andradejoseph.javaprimers.chapter3.chapter3_1;

/**
 * Created by ANDRADEJOSEPH on 2/24/2017.
 */

public class SortingAlgorithms {

    public SortingAlgorithms() {}

    public static void insertionSort(char[] data) {
        int n = data.length;                //get length of data array of type char
        for (int k = 1; k < n ; k++) {      //begin with second character
            char cur = data[k];             //time to insert the cur = data[k]
            int j = k;                      //find the correct j for curr
            while (j > 0 && data[j-1] > cur) { //thus, data[j-1] must go after cur
                data[j] = data[j - 1];         //slide data[j-1] rightward
                j--;                            //and consider previous j for cur
            }
            data[j] = cur;                      //this is the proper place for cur
        }
        System.out.println("INSERTIONSORT " + data.length );
        for(int i = 0; i < data.length; i++) {
            System.out.println(data[i]);
        }
    }
}
