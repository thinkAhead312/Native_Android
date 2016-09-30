package com.example.dna.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;

public class CrimeActivity extends SingleFragmentActivity {

//    public  static final String EXTRA_CRIME

    private static final String EXTRA_CRIME_ID = "com.example.dna.criminalintent.crime_id";

    public static Intent newIntent(Context packageContext, UUID crimeId) { //creating explicit intent
        Intent intent = new Intent(packageContext, CrimeActivity.class); //pass string key and value of the key
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return  CrimeFragment.newInstance(crimeId);

//        return new CrimeFragment();
    }
}
