package com.example.andradejoseph.bluetoothconnect.model;

/**
 * Created by ANDRADEJOSEPH on 1/11/2017.
 */

public class BluetoothModel {

    private String mBlueToothName;
    private String mBlueToothAddress;


    public BluetoothModel(String blueToothName, String blueToothAddress) {
        mBlueToothName = blueToothName;
        mBlueToothAddress = blueToothAddress;
    }

    public String getmBlueToothName() {
        return mBlueToothName;
    }

    public String getmBlueToothAddress() {
        return mBlueToothAddress;
    }

}
