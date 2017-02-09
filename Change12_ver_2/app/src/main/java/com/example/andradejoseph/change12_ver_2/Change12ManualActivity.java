package com.example.andradejoseph.change12_ver_2;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Toast;

import com.example.andradejoseph.change12_ver_2.fragment.ChangeIntroFragment;
import com.example.andradejoseph.change12_ver_2.fragment.FragmentB;
import com.example.josephandrade.article_detail_transition.ArticleListActivity;


import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


public class Change12ManualActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private BottomNavigation mBottomNavigation;
    int pos = 0;// bottombar position



    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, Change12ManualActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change12_manual);
        initWidgets();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Explode explode = new Explode();
            explode.setDuration(500);
            getWindow().setExitTransition(explode);
            getWindow().setEnterTransition(explode);
        }


        mBottomNavigation.setSelectedIndex(pos,true);
        if (null != mBottomNavigation) {
            mBottomNavigation.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
                @Override
                public void onMenuItemSelect(@IdRes int i, int i1, boolean b) {
                    switch (i) {
                        case R.id.action_new:
                            fragment = new ChangeIntroFragment();
                            break;

                        case R.id.action_project:
                            fragment = new  FragmentB();
                            break;

                        case R.id.action_data:
                            fragment = new FragmentB();
                            break;
                    }
                    final FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.replace(R.id.frame, fragment).commit();
                }

                @Override
                public void onMenuItemReselect(@IdRes int i, int i1, boolean b) {

                }
            });
        }


        /**
         * Add Fragment you want to dsiplay in list
         */
//        List<Fragment> fragmentList = new ArrayList<>();
//        fragmentList.add(new ChangeIntroFragment());
//        fragmentList.add(new FragmentB());
//        fragmentList.add(new FragmentB());

    }

    private void initWidgets() {
        mBottomNavigation = (BottomNavigation) findViewById(R.id.BottomNavigation);
        fragmentManager = getSupportFragmentManager();
        fragment = new ChangeIntroFragment(); //default

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame, fragment).commit();

    }

    //we need the outState to memorize the position
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }
}
