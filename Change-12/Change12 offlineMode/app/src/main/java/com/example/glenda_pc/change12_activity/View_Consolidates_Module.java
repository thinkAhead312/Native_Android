package com.example.glenda_pc.change12_activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change12_Graduate;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_1;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_2;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_3;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_4;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_5;
import com.example.glenda_pc.change12_activity.Fragments.Consolidate_Change_All;
import com.example.glenda_pc.change12_activity.Model.RoundImage;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class View_Consolidates_Module extends AppCompatActivity {

    private DatabaseAccess databaseAccess;
    private DrawerLayout mDrawerLayout;//Drawer layout
    public int tablayout_flag=0;//tabLayout flag, which tab to display
    private TabLayout tabLayout; //TabLayout
    private ViewPager viewPager; //Viewpager
    TextView text;
    LinearLayout lin;
    ImageView imageVIew;
    FloatingActionButton fab;
    public static int closeCell=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__consolidates__module);
        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());

        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);

        Intent intent = getIntent();
        tablayout_flag = intent.getIntExtra("dipFlag",0);
        toolBar(); //set Toolbar
        tab_Bars();//set tabLayout
        naviDrawer(); //Navigational Drawer
        fab = (FloatingActionButton)findViewById(R.id.fab);
        popUpMenu(); //setUP popUPMenu
    }

    private void popUpMenu() {
        final PopupMenu popupMenu = new PopupMenu(this, fab);
        popupMenu.inflate(R.menu.fab_view_disciples_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        popupMenu.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.myDisciples:
                                Toast.makeText(View_Consolidates_Module.this, "1", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.myConsolidates:
                                Toast.makeText(View_Consolidates_Module.this, "2", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.myCalendar:
                                Intent intent = new Intent(View_Consolidates_Module.this, Add_CalendarSched.class);
                                startActivity(intent);
                                break;
                        }
                        return true;
                    }
                });
    }

    private void tab_Bars() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {


        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Consolidate_Change_All(), "Change ALL");
        adapter.addFragment(new Consolidate_Change_1(), "Change 1");
        adapter.addFragment(new Consolidate_Change_2(), "Change 2");
        adapter.addFragment(new Consolidate_Change_3(), "Change 3");
        adapter.addFragment(new Consolidate_Change_4(), "Change 4");
        adapter.addFragment(new Consolidate_Change_5(), "Change 5");
        adapter.addFragment(new Consolidate_Change12_Graduate(), "Pre-Encounter");


        viewPager.setOffscreenPageLimit(7);
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
            tablayout_flag=viewPager.getCurrentItem();
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

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Change 12 Consolidates");
    }

    private void naviDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                if (menuItem.getTitle().equals("Change12 Manual")) {
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }

                if (menuItem.getTitle().equals("My Profile")) {
                  Intent intent = new Intent(View_Consolidates_Module.this, My_Profile_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
                if (menuItem.getTitle().equals("My Disciples")) {
                    Intent intent = new Intent(View_Consolidates_Module.this, View_Disciples_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        slidingDrawer();
        return true;
    }

    private void slidingDrawer() {
        text = (TextView) findViewById(R.id.txtDrawerName);
        lin = (LinearLayout) findViewById(R.id.drawerHeader);
        imageVIew = (ImageView) findViewById(R.id.imgB_Background);

        RoundedBitmapDrawable drawable;
        RoundImage roundedImage = new RoundImage();
        Bitmap bitmapImage = null;
        databaseAccess.open();
        /*try {
            bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(databaseAccess.photo));
            BitmapDrawable background = new BitmapDrawable(getResources(), bitmapImage);
           // roundedImage = new RoundImage(bitmapImage);
           // imageVIew.setImageDrawable(roundedImage)
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            imageVIew.setImageDrawable(drawable);
        } catch (IOException e) {

            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);


            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            imageVIew.setImageDrawable(drawable);
            e.printStackTrace();
        }*/
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


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {                   // automatically handle clicks on the Home/Up button, so long
            case android.R.id.home:     // as you specify a parent activity in AndroidManifest.xml.
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected  void onPause(){
        super.onPause();
        finish();

    }
    @Override
    protected  void onStop(){
        super.onStop();


    }
    @Override
    protected void onResume() {
        super.onResume();

    }

}
