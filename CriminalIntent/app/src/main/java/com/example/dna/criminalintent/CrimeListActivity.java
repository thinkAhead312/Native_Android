package com.example.dna.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by dna on 9/27/16.
 */
public class CrimeListActivity extends  SingleFragmentActivity{

    private static final String CRIME_LIST_ACTIVITY = "com.example.dna.criminalintent.crime_activity";


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, CrimeListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }



}
