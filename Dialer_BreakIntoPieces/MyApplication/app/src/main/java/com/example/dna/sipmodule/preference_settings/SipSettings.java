package com.example.dna.sipmodule.preference_settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.example.dna.sipmodule.R;

/**
 * Created by dna on 8/15/16.
 */
public class SipSettings extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Note that none of the preferences are actually defined here.
        // They're all in the XML file res/xml/preferences.xml.
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}
