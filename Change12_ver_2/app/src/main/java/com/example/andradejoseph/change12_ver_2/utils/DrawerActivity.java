package com.example.andradejoseph.change12_ver_2.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.GravityCompat;
import android.view.View;
import android.widget.ImageView;

import com.example.andradejoseph.change12_ver_2.R;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;


/**
 * Created by ANDRADEJOSEPH on 3/15/2017.
 */

public class DrawerActivity extends Activity {
    Drawer result;

    private static DrawerActivity instance = null;

    private DrawerActivity() {
        // Exists only to defeat instantiation.
    }

    public static DrawerActivity getInstance() {
        if(instance == null) {
            instance = new DrawerActivity();
        }
        return instance;
    }

    public  void DrawerInit(Activity activity) {


        //Create account header
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.mateial_bg)
                .addProfiles(new ProfileDrawerItem().withIcon(R.drawable.joel).withName("Joseph C. Andrade II").withEmail("joseph_andrade@outlook.ph"))
                .build();


        result = new DrawerBuilder()
                .withActivity(activity)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        new PrimaryDrawerItem().withIcon(R.mipmap.logo).withName("Change12"),
                        new PrimaryDrawerItem().withBadge("20").withName("Consolidates"),
                        new PrimaryDrawerItem().withBadge("10").withName("Disciples"),
                        new PrimaryDrawerItem().withName("Statistics"),
                        new PrimaryDrawerItem().withName("Events"),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("My Profile"),
                        new SecondaryDrawerItem().withName("Settings")


        )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        return false;
                    }
                })
                .build();
    }

    public void openDrawer() {
        result.openDrawer();
    }

}
