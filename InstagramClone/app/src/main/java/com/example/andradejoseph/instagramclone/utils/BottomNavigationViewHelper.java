package com.example.andradejoseph.instagramclone.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.andradejoseph.instagramclone.Likes.LikeActivity;
import com.example.andradejoseph.instagramclone.Home.MainActivity;
import com.example.andradejoseph.instagramclone.Profile.ProfileActivity;
import com.example.andradejoseph.instagramclone.R;
import com.example.andradejoseph.instagramclone.Search.SearchActivity;
import com.example.andradejoseph.instagramclone.Share.ShareActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by ANDRADEJOSEPH on 8/15/2017.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavHeler";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx) {
        Log.d(TAG, "setting up");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.ic_house:
                        Intent intent1 = new Intent(context, MainActivity.class); //activity_num = 0
                        context.startActivity(intent1);
                        break;
                    case R.id.ic_search:
                        Intent intent2 = new Intent(context, SearchActivity.class);  //activity_num = 1
                        context.startActivity(intent2);
                        break;
                    case R.id.ic_circle:
                        Intent intent3 = new Intent(context, ShareActivity.class);  //activity_num = 2
                        context.startActivity(intent3);
                        break;
                    case R.id.ic_alert:
                        Intent intent4 = new Intent(context, LikeActivity.class);  //activity_num = 3
                        context.startActivity(intent4);
                        break;
                    case R.id.ic_android:
                        Intent intent5 = new Intent(context, ProfileActivity.class);  //activity_num = 4
                        context.startActivity(intent5);
                        break;
                }

                return false;
            }
        });
    }
}
