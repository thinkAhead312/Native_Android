package com.example.dna.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by dna on 9/27/16.
 */
public class CrimeListActivity extends  SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
