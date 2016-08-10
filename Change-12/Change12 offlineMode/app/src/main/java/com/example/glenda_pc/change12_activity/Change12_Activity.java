package com.example.glenda_pc.change12_activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Fragments.OneFragment;
import com.example.glenda_pc.change12_activity.Fragments.TwoFragment;
import com.example.glenda_pc.change12_activity.Model.RoundImage;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class Change12_Activity extends AppCompatActivity {

    private DatabaseAccess databaseAccess;
    private DrawerLayout mDrawerLayout;//Drawer layout
    private TabLayout tabLayout; //TabLayout
    private ViewPager viewPager; //Viewpager
    public int tablayout_flag=0;
    FloatingActionButton fab;

    TextView text;
    LinearLayout lin;
    ImageView imageVIew;

    String fname="";


    public static final String ACTION_CLOSE = "com.example.glenda_pc.change12_activity.ACTION_CLOSE"; //Action close for broadcast reciever
    private FirstReceiver firstReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change12_);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        Intent intent = getIntent();
        tablayout_flag = intent.getIntExtra("dipFlag",0);

        naviDrawer(); //navigation drawer
        toolBar(); //set Toolbar
        tab_Bars();//set tabLayout
        fab = (FloatingActionButton) findViewById(R.id.fab);
        popUpMenu(); //setUP popUPMenu
        try {

        }catch (Exception e){
            tablayout_flag=1;
        }
        databaseAccess.open();
        if(databaseAccess.users_disciplesTableJoin()==0) {
            //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            Toast.makeText(Change12_Activity.this, "Hello " + databaseAccess.first_name, Toast.LENGTH_SHORT).show();
             mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        databaseAccess.close();
        IntentFilter filter = new IntentFilter(ACTION_CLOSE);
        firstReceiver = new FirstReceiver();
        registerReceiver(firstReceiver, filter);
        //Intent close = new Intent(getApplicationContext(), My_Profile_Module.class);
       // startActivity(close);


    }


    private void popUpMenu() {

        final PopupMenu popupMenu = new PopupMenu(this, fab);
        popupMenu.inflate(R.menu.disciples_consolidates_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAccess.open();
                if(databaseAccess.users_disciplesTableJoin()==0){
                    snackBarCall();
                }else {
                    Object menuHelper;
                    Class[] argTypes;
                    try {
                        Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                        fMenuHelper.setAccessible(true);
                        menuHelper = fMenuHelper.get(popupMenu);
                        argTypes = new Class[]{boolean.class};
                        menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                    } catch (Exception e) {
                        popupMenu.show();
                        return;
                    }
                    popupMenu.show();
                }
                databaseAccess.close();
            }
        });

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.myDisciples:
                                Intent intent = new Intent(Change12_Activity.this,Add_Disciple_Module.class);
                                startActivity(intent);

                                break;
                            case R.id.myConsolidates:
                                intent = new Intent(Change12_Activity.this,Add_Consolidates_Module.class);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });
    }

    public void clk_drawerName(View v){
        if(v.equals(text)){
            // Get calendar set to current date and time
            String week="";
            Calendar c = Calendar.getInstance();

            // Set the calendar to monday of the current week
                        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

            // Print dates of the current week starting on Monday
                        DateFormat df = new SimpleDateFormat("EEE MM/dd/yyyy");
                        for (int i = 0; i < 7; i++) {
                            //System.out.println(df.format(c.getTime()));
                                if(i==0){
                                    week= df.format(c.getTime());
                                }
                                if(i==6){
                                    week= week + "-" + df.format(c.getTime());
                                }
                            c.add(Calendar.DATE, 1);
                        }
            Toast.makeText(Change12_Activity.this, week, Toast.LENGTH_LONG).show();


        }
    }

    private void tab_Bars() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


            adapter.addFragment(new OneFragment(), "Introduction");
            adapter.addFragment(new TwoFragment(), "Lessons");

            viewPager.setAdapter(adapter);
            if(tablayout_flag==0) {
                viewPager.setCurrentItem(1);
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



    @SuppressLint("NewApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        text = (TextView) findViewById(R.id.txtDrawerName);
        lin = (LinearLayout) findViewById(R.id.drawerHeader);
        imageVIew = (ImageView) findViewById(R.id.imgB_Background);


        Bitmap bitmapImage = null;
        RoundImage roundedImage = new RoundImage();
        RoundedBitmapDrawable drawable;
        databaseAccess.open();

        /*try {
            bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(databaseAccess.photo));
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            imageVIew.setImageDrawable(drawable);
        } catch (IOException e) {

          Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                      R.drawable.pic);

            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            imageVIew.setImageDrawable(drawable);
            e.printStackTrace();
        }
        */
        if(!databaseAccess.photo.equals("")) {
            byte[] imageAsBytes = Base64.decode(databaseAccess.photo.getBytes(), Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            imageVIew.setImageDrawable(drawable);
        }else{
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            imageVIew.setImageDrawable(drawable);
        }
        text.setText(databaseAccess.first_name);
        text.setTextColor(Color.WHITE);
        databaseAccess.close();
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        databaseAccess.open();
        switch (id) {
            case android.R.id.home:
                if(databaseAccess.users_disciplesTableJoin()==0) {
                   snackBarCall();
                }else {
                    mDrawerLayout.openDrawer(GravityCompat.START);
                }
        databaseAccess.close();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void snackBarCall() {
        Snackbar.make(findViewById(R.id.drawer_layout), "Not Registered Account", Snackbar.LENGTH_LONG).setAction("Register?", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Change12_Activity.this,Registration_Module.class);
                //intent.putExtra("dipFlag",1);
                startActivity(intent);
                finish();
            }
        }).show();
    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Change12 Manual");
    }
    private void naviDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                
                mDrawerLayout.closeDrawers();

                if (menuItem.getTitle().equals("My Disciples")) {

                    Intent intent = new Intent(Change12_Activity.this, View_Disciples_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }

                if (menuItem.getTitle().equals("My Profile")) {
                    Intent intent = new Intent(Change12_Activity.this, My_Profile_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                   // Toast.makeText(Change12_Activity.this, "Not yet Available", Toast.LENGTH_SHORT).show();
                }
                if (menuItem.getTitle().equals("Change 12 Consolidates")) {
                    Intent intent = new Intent(Change12_Activity.this, View_Consolidates_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
       // MainActivity.DBChange12_ver1.close();

    }
    @Override
    protected void onResume() {
        super.onResume();
        //MainActivity.DBChange12_ver1=openOrCreateDatabase("DBChange12_ver1", Context.MODE_PRIVATE, null); //open or create database
        //sql.users_disciplesTableJoin();

    }

    class FirstReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FirstReceiver", "FirstReceiver");
            if (intent.getAction().equals(ACTION_CLOSE)) {
                Change12_Activity.this.finish();

            }
        }
        //unregisterReceiver(FirstReceiver);
    }
}
