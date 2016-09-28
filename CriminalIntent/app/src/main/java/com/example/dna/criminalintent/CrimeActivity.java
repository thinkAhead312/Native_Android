package com.example.dna.criminalintent;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

public class CrimeActivity extends SingleFragmentActivity {

//    public  static final String EXTRA_CRIME

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
