package com.example.andradejoseph.change12_ver_2;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.transition.Slide;
import android.view.MenuItem;


import com.example.andradejoseph.change12_ver_2.custom.BaseActivity;
import com.example.andradejoseph.change12_ver_2.sessions.SessionManager;
import com.example.andradejoseph.change12_ver_2.ui.ChangeIntroFragment;
import com.example.andradejoseph.change12_ver_2.ui.LessonsFragment;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;

import java.util.ArrayList;
import java.util.List;


public class Change12ManualActivity extends BaseActivity implements Callback{
    private static final int REQUEST_CODE = 1;
    private TabLayout tabLayout; //TabLayout
    private ViewPager viewPager; //Viewpager
    private SessionManager session;



    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, Change12ManualActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change12_manual);

        session = new SessionManager(getApplicationContext());

        DrawerActivity.getInstance().DrawerInit(Change12ManualActivity.this);
        init();
        tabbar();
//        setupWindowAnimations();

        if(!session.isFirstStart()) {
            progressDoalog = new ProgressDialog(this);
            progressDoalog.setMessage("Please wait...");
            progressDoalog.setCancelable(false);
            progressDoalog.show();
            fetchDB();
        }
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide();
            slide.setDuration(1000);
            getWindow().setExitTransition(slide);
        }
    }

    private void tabbar() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ChangeIntroFragment(), "Introduction");
        adapter.addFragment(new LessonsFragment(), "Lessons");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onMethodCallback(int position) {
//        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_SHORT).show();

         switch(position) {
             case CHANGE_12_CONSOLIDATE:
//                 final ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(Change12ManualActivity.this);
//                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                     Slide slide = new Slide();
//                     slide.setDuration(1000);
//                     getWindow().setExitTransition(slide);
//                 }
                 Intent i2 = WaveListActivity.newIntent(Change12ManualActivity.this);
                 startActivity(i2);
                 finish();
                 break;
         }


    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }
        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //we need the outState to memorize the position
    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            DrawerActivity.getInstance().openDrawer();
        }
        return super.onOptionsItemSelected(item);
    }
}
