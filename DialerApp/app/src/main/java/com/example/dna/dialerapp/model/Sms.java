package com.example.dna.dialerapp.model;

import java.util.Collection;

/**
 * Created by dna on 7/21/16.
 */
public class Sms  {

    private String number="";
    // SMS contact_row body
    private String body="";

    private String name="";

    private String date="";



    public Sms(String name, String number, String body, String date) {
        this.name = name;
        this.number = number;
        this.body = body;
        this.date = date;
    }

    public Sms() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


}
