package com.example.andradejoseph.profiler.profilepage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.andradejoseph.profiler.R;

public class ProfilePageActivity extends AppCompatActivity {
    private static final String PAGE_FRAGMENT = "PAGE_FRAGMENT";

    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        manager = this.getSupportFragmentManager();

        ProfilePageFragment fragment = (ProfilePageFragment) manager.findFragmentByTag(PAGE_FRAGMENT);
        if(fragment == null) {
            fragment = ProfilePageFragment.newInstance();
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.cont_profile_page_fragment, fragment, PAGE_FRAGMENT)
                .commit();
    }
}
