package com.example.dna.dialerapp.model;

import android.provider.CallLog;

/**
 * Created by dna on 8/2/16.
 */
public class CallLogs {

    String number = "", type ="", date = "",  duration = "", contactName= "";

    public CallLogs(String number, String type, String date, String duration, String contactName) {
        this.number = number;
        this.type = type;
        this.date = date;
        this.duration = duration;
        this.contactName = contactName;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
}
