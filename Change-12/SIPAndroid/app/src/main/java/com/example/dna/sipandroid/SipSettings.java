package com.example.dna.sipandroid;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by dna on 7/28/16.
 */
public class SipSettings extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Note that none of the preferences are actually defined here.
        // They're all in the XML file res/xml/preferences.xml.
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}