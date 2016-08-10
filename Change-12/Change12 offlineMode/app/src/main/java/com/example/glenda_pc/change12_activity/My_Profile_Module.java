package com.example.glenda_pc.change12_activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Database.SqliteHelper;
import com.example.glenda_pc.change12_activity.Model.RoundImage;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class My_Profile_Module extends AppCompatActivity {

    RelativeLayout relDisciples, relConsolidates;
    int getUpdSun=0,getUpdMon=0, getUpdTue=0, getUpdWed=0, getUpdThur=0, getUpdFri=0, getUpdSat=0, totalConsolidated=0;
    private DatabaseAccess databaseAccess;

    AppBarLayout appbar;
    ImageView imgProfile;
    TextView tvThisWeek;
    SqliteHelper sqlitehelper= new SqliteHelper(this);
    RoundImage roundedImage = new RoundImage();
    RoundedBitmapDrawable drawable;

    private DrawerLayout mDrawerLayout;//Drawer layout
    TextView text;
    LinearLayout lin;
    ImageView imageVIew;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile__module);
        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        this.databaseAccess = DatabaseAccess.getInstance(this);

        relDisciples= (RelativeLayout) findViewById(R.id.relDiciplesInfo);
        relConsolidates=(RelativeLayout) findViewById(R.id.relConsolidatesInfo);
        appbar= (AppBarLayout) findViewById(R.id.app_bar);
        imgProfile= (ImageView) findViewById(R.id.imgProfile);
        tvThisWeek=(TextView) findViewById(R.id.tvThisWeek);

        WindowManager windowManager = (WindowManager)getSystemService(WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        relDisciples.setMinimumWidth((int) (width * 0.5));
        relConsolidates.setMinimumWidth((int) (width * 0.5));

        toolBar();
        naviDrawer();
        setThisWeek();
        barDataSet();
        getData();
        onClickMethod();
        setUpPhoto();
    }

    private void getData() {
        databaseAccess.open();
        databaseAccess.queryChange_ALL();
        TextView tvConsolidateCount = (TextView) findViewById(R.id.tvConsolidateCount);
        tvConsolidateCount.setText(String.valueOf(databaseAccess.totalRows));
        databaseAccess.close();

        databaseAccess.open();
        databaseAccess.queryAllDisciples();
        TextView tvDiscipleCount = (TextView) findViewById(R.id.tvDisciplesCount);
        tvDiscipleCount.setText(String.valueOf(databaseAccess.totalRows));
        databaseAccess.close();


        databaseAccess.open();
        int consolidateCount=databaseAccess.queryThisWeekChange12Consolidated("Change 1");
        TextView tcChange1Count = (TextView) findViewById(R.id.tvChange1Count);
        tcChange1Count.setText(String.valueOf(consolidateCount));
        databaseAccess.close();
        databaseAccess.open();
        consolidateCount=databaseAccess.queryThisWeekChange12Consolidated("Change 2");
        TextView tvChange2Count = (TextView) findViewById(R.id.tvChange2Count);
        tvChange2Count.setText(String.valueOf(consolidateCount));
        databaseAccess.close();
        databaseAccess.open();
        consolidateCount=databaseAccess.queryThisWeekChange12Consolidated("Change 3");
        TextView tcChange3Count = (TextView) findViewById(R.id.tvChange3Count);
        tcChange3Count.setText(String.valueOf(consolidateCount));
        databaseAccess.close();
        databaseAccess.open();
        consolidateCount=databaseAccess.queryThisWeekChange12Consolidated("Change 4");
        TextView tcChange4Count = (TextView) findViewById(R.id.tvChange4Count);
        tcChange4Count.setText(String.valueOf(consolidateCount));
        databaseAccess.close();
        databaseAccess.open();
        consolidateCount=databaseAccess.queryThisWeekChange12Consolidated("Change 5");
        TextView tcChange5Count = (TextView) findViewById(R.id.tvChange5Count);
        tcChange5Count.setText(String.valueOf(consolidateCount));
        databaseAccess.close();

        TextView tvTotalConsolidated= (TextView) findViewById(R.id.tvTotalConsolidated);
        tvTotalConsolidated.setText(tvTotalConsolidated.getText().toString() + " " + String.valueOf(totalConsolidated));

    }

    private void onClickMethod() {

        relDisciples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Profile_Module.this, View_Disciples_Module.class);
                intent.putExtra("dipFlag", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        });

        relConsolidates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(My_Profile_Module.this, View_Consolidates_Module.class);
                intent.putExtra("dipFlag", 1);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
        });
    }

    private void barDataSet() {

        BarChart chart = (BarChart) findViewById(R.id.chart);

        BarData data = new BarData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        
    }

    private ArrayList<BarDataSet> getDataSet() {

        //ProcessData

        ArrayList<BarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(getUpdSun, 0); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(getUpdMon, 1); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(getUpdTue, 2); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(getUpdWed, 3); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(getUpdThur, 4); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(getUpdFri, 5); // Jun
        valueSet1.add(v1e6);
        BarEntry v1e7 = new BarEntry(getUpdSat, 6); // Jun
        valueSet1.add(v1e7);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Week");
        barDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);



        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Sun");
        xAxis.add("Mon");
        xAxis.add("Tue");
        xAxis.add("Wed");
        xAxis.add("Thur");
        xAxis.add("Fri");
        xAxis.add("Sat");
        return xAxis;
    }

    private void setThisWeek() {
        String week="";
        Calendar c = Calendar.getInstance();
        // Set the calendar to monday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        // Print dates of the current week starting on Monday
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        databaseAccess.open();

        for (int i = 0; i < 7; i++) {
            //System.out.println(df.format(c.getTime()));

            if(i==0){
                week= df.format(c.getTime());
                getUpdSun = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==1){
                getUpdMon = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==2){
                getUpdTue = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==3){
                getUpdWed = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==4){
                getUpdThur = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==5){
                getUpdFri = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            if(i==6){
                week= week + "-" + df.format(c.getTime());
                getUpdSat = databaseAccess.queryConsolidateUpdate(df.format(c.getTime()));
            }
            c.add(Calendar.DATE, 1);
        }
        totalConsolidated = getUpdFri + getUpdSat + getUpdThur + getUpdWed + getUpdMon + getUpdSun + getUpdTue;
        databaseAccess.close();
        tvThisWeek.setText(tvThisWeek.getText().toString().trim()+" " + week); //Display this week in textView
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setUpPhoto() {
        Bitmap bitmapImage = null;


        /*try {
            bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(sqlitehelper.photo));
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            Drawable d = new BitmapDrawable(getResources(), blur(bitmapImage));
            appbar.setBackground(d);
            imgProfile.setImageDrawable(drawable);

        } catch (IOException e) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            Drawable d = new BitmapDrawable(getResources(), blur(icon));
            appbar.setBackground(d);
            imgProfile.setImageDrawable(drawable);
        }*/

        if(!databaseAccess.photo.equals("")) {
            byte[] imageAsBytes = Base64.decode(databaseAccess.photo.getBytes(), Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            Drawable d = new BitmapDrawable(getResources(), blur(bitmapImage));
            appbar.setBackground(d);
            imgProfile.setImageDrawable(drawable);
        }else{
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            Drawable d = new BitmapDrawable(getResources(), blur(icon));
            appbar.setBackground(d);
            imgProfile.setImageDrawable(drawable);
        }



    }

    private static final float BLUR_RADIUS = 25f;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap blur(Bitmap image) {

        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    private void toolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);
        databaseAccess.open();
        actionBar.setTitle(databaseAccess.first_name); //set Toolbar Title
        databaseAccess.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        slidingDrawer();

        return true;
    }

    private void slidingDrawer() {

        text = (TextView) findViewById(R.id.txtDrawerName);
        lin = (LinearLayout) findViewById(R.id.drawerHeader);
        imageVIew = (ImageView) findViewById(R.id.imgB_Background);


        Bitmap bitmapImage = null;
        RoundImage roundedImage = new RoundImage();
        RoundedBitmapDrawable drawable;
        databaseAccess.open();
        databaseAccess.users_disciplesTableJoin();
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
        //Toast.makeText(My_Profile_Module.this, databaseAccess.first_name, Toast.LENGTH_SHORT).show();
        text.setText(databaseAccess.first_name);
        text.setTextColor(Color.WHITE);
        databaseAccess.close();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        switch (id) {                   // automatically handle clicks on the Home/Up button, so long
            case android.R.id.home:     // as you specify a parent activity in AndroidManifest.xml.
                mDrawerLayout.openDrawer(GravityCompat.START);

                return true;

        }

        if(id==R.id.update){

            Intent myIntent = new Intent(Change12_Activity.ACTION_CLOSE);
            sendBroadcast(myIntent);
            Log.e("onCreate", "onCreate");
            Intent intent = new Intent(this, Profile_Change_Module.class);
            intent.putExtra("dipFlag", 1);
            startActivity(intent);
            //((ResultReceiver)getIntent().getParcelableExtra("finisher")).send(1, new Bundle());
            finish();


        }




        if(id==R.id.cancel){

        }
        return super.onOptionsItemSelected(item);
    }
    private void naviDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                mDrawerLayout.closeDrawers();

                if (menuItem.getTitle().equals("Change12 Manual")) {
                    finish();
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                }

                if (menuItem.getTitle().equals("My Disciples")) {

                    Intent intent = new Intent(My_Profile_Module.this, View_Disciples_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
                if (menuItem.getTitle().equals("Change 12 Consolidates")) {
                    Intent intent = new Intent(My_Profile_Module.this, View_Consolidates_Module.class);
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
    protected  void onPause(){
        super.onPause();
        finish();

    }



}
