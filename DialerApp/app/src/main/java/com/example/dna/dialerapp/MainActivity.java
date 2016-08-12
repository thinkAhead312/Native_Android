package com.example.dna.dialerapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Telephony;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.dna.dialerapp.fragment.*;
import com.example.dna.dialerapp.fragment.CallLogs;
import com.example.dna.dialerapp.helper.IntentStartActivity;
import com.example.dna.dialerapp.model.Constants;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    SipAndroid sipAndroid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkForCrashes();
        sipAndroid = SipAndroid.getInstance();
        //checkSipStatus();
        init();
        //checkPrerenceManager();

    }

    private void checkForCrashes() {
        CrashManager.register(this, "e69ee2e473ee48a19a013292006df233", new MyCustomCrashManagerListener());
    }

    private void checkSipStatus() {
        sipAndroid.SipAndroidInitialize(this);
}

    private void checkPrerenceManager() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Constants.username = prefs.getString("namePref", "");
        Constants.domain = prefs.getString("domainPref", "");
        Constants.password = prefs.getString("passPref", "");

        if (Constants.username.length() == 0 || Constants.domain.length() == 0 || Constants.password.length() == 0) {
            Toast.makeText(MainActivity.this, "You Need To Register to Sip First", Toast.LENGTH_SHORT).show();
            IntentStartActivity.updatePreferences(MainActivity.this);
            return;
        }
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setDefault() {
        final String myPackageName = getPackageName();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (!Telephony.Sms.getDefaultSmsPackage(this).equals(myPackageName)) {
                // App is not default.
                // Show the "not currently set as the default SMS app" interface
                // Set up a button that allows the user to change the default SMS app
                        Intent intent =
                                new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
                        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME,
                                myPackageName);
                        startActivity(intent);

            } else {
                // App is the default.
                // Hide the "not currently set as the default SMS app" interface

            }
        }
    }


    private void setupViewPager(ViewPager viewPager) {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(new ContactList(), "Contact");
            adapter.addFragment(new SmsList(), "Sms Inbox ");
            adapter.addFragment(new CallLogs(), "Call Logs");
            viewPager.setOffscreenPageLimit(2);
            viewPager.setAdapter(adapter);
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


    @Override
    public void onStart() {
        super.onStart();
        // When we get back from the preference setting Activity, assume
        // settings have changed, and re-login with new auth info.
        sipAndroid = SipAndroid.getInstance();
        checkSipStatus();
        checkPrerenceManager();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Constants.SET_AUTH_INFO, 0, "Edit your SIP Info.");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case Constants.SET_AUTH_INFO:
                IntentStartActivity.updatePreferences(MainActivity.this);
                break;
        }
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sipAndroid.call != null) {
            sipAndroid.call.close();
        }
        try {
            if (sipAndroid.me != null) {
                sipAndroid.call.endCall();
                sipAndroid.manager.close(sipAndroid.me.getUriString());
            }
        } catch (Exception ee) {
        }
        unregisterManagers();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sipAndroid.callReceiver != null) {
            unregisterReceiver(sipAndroid.callReceiver);
        }
        unregisterManagers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
        checkForUpdates();
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
