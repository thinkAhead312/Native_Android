package com.example.dna.dialerapp.model;

import java.util.Comparator;

/**
 * Created by dna on 7/21/16.
 */
public class Contact {
    private String name;
    private String number;

    public Contact() {

    }

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  String getNumber() {
        return  number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public static Comparator<Contact> ContactNameComparator = new Comparator<Contact>() {

        public int compare(Contact s1, Contact s2) {
            String ContactName1 = s1.getName().toUpperCase();
            String ContactName2 = s2.getName().toUpperCase();
            //ascending order
            return ContactName1.compareTo(ContactName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    @Override
    public String toString() {
        return "[ name=" + name + ", number=" + number  + "]";
    }
}
