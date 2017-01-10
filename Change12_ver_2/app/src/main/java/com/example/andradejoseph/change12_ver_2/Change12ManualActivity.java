package com.example.andradejoseph.change12_ver_2;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import com.example.andradejoseph.change12_ver_2.fragment.ChangeIntroFragment;
import com.example.andradejoseph.change12_ver_2.fragment.FragmentB;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class Change12ManualActivity extends AppCompatActivity {
    private SpaceTabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change12_manual);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }

        /**
         * Add Fragment you want to dsiplay in list
         */
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ChangeIntroFragment());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentB());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);

        tabLayout.getButton();
        tabLayout.setTabOneOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Change12ManualActivity.this, "karen", Toast.LENGTH_SHORT).show();
            }
        });
        //we need the saveInstanceState to retrieve the position
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, savedInstanceState);
    }

    //we need the outState to memorize the position
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
