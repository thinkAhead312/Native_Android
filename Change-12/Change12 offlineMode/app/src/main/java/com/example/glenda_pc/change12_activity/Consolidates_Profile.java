package com.example.glenda_pc.change12_activity;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Adapter.RemarksAdapter;
import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Model.Config;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.Model.RoundImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Consolidates_Profile extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private DatabaseAccess databaseAccess;
    private RecyclerView.Adapter adapter; //adapter
    private List<Disciple_Set_Get> fetchImageList= new ArrayList<>();
    String consolidateID, strFirstName="", strPhoto="", strChange12Stat="",strRemarks="", strContact="",strBday="",strAffiliation="",strAddress="",strSurname="";
    TextView tvRemarks, tvContact,tvChangeStatus, tvChangeDetails,tvBday,tvAffiliation,tvAddress;
    AppBarLayout appBarLayout;
    private RecyclerView recyclerView; //initialize recycleview
    private RecyclerView.LayoutManager layoutManager; //layout manager
    RelativeLayout relContact;

    ImageView imgProfile;

    SeekBar sb;
    TextView seekbartest;
    Bitmap bitmap1=null;
    String savedURL="";
    private Boolean status;

    private Uri mImageCaptureUri;
    private AlertDialog dialog;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    RoundImage roundedImage = new RoundImage();
    RoundedBitmapDrawable drawable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consolidates__profile);

        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());


        consolidateID = getIntent().getExtras().getString("Users_ID");


        overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
        imgProfile = (ImageView) findViewById(R.id.imgProfile);
        tvRemarks = (TextView) findViewById(R.id.tvRemarks);
        relContact = (RelativeLayout) findViewById(R.id.relContact);
        tvContact = (TextView) findViewById(R.id.tvContact);
        tvChangeStatus = (TextView) findViewById(R.id.tvChangeStaus);
        tvChangeDetails = (TextView) findViewById(R.id.tvChangeDetail);
        tvBday = (TextView) findViewById(R.id.tvBday);
        tvAffiliation = (TextView) findViewById(R.id.tvAffiliation);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);

        sb = (SeekBar) findViewById(R.id.myseek);
        seekbartest = (TextView) findViewById(R.id.slider_text);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        getData();
        toolBar();
        setupPhoto();
        setWidgets();

        sb.setOnSeekBarChangeListener(this);
        seekbartest.setText("Update Change12 Stat");
    }

    private void setWidgets() {
        tvChangeStatus.setText(strChange12Stat);
        if(strChange12Stat.equals("Change 1")){
            tvChangeDetails.setText("Change Destination");
        }
        if(strChange12Stat.equals("Change 2")){
            tvChangeDetails.setText("Change Direction");
        }
        if(strChange12Stat.equals("Change 3")){
            tvChangeDetails.setText("Change Heart");
        }
        if(strChange12Stat.equals("Change 4")){
            tvChangeDetails.setText("Change Relationship");
        }
        if(strChange12Stat.equals("Change 5")){
            tvChangeDetails.setText("Change Lifestyle");
        }
        if(strChange12Stat.equals("Graduate")){
            tvChangeDetails.setText("Pre-Encounter");
        }
        tvRemarks.setText(strRemarks);
        tvContact.setText(strContact);
        tvBday.setText(strBday);
        tvAffiliation.setText(strAffiliation);
        tvAddress.setText(strAddress);
        savedURL=strPhoto;
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void alertBox(final Boolean status) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alertDialogBuilder.setMessage("Update Change12 Status?");

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                //accountVerification();
                StringBuilder Change12StatusMessage = new StringBuilder();
                if (status) {
                    if (strChange12Stat.equals("Change 1")) {
                        //Toast.makeText(Consolidates_Profile.this, "Change 2", Toast.LENGTH_SHORT).show();
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 2");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 2");
                        tvChangeDetails.setText("Change Direction");

                        SmsManager smsManager = SmsManager.getDefault();
                        Change12StatusMessage.append("Congratulations" + "\n");
                        Change12StatusMessage.append("John 3:16 " + "\n");
                        Change12StatusMessage.append("For God so love the world that He gave His one and only Son that whoever believe in Him shall not perish but have eternal life");
                        ArrayList<String> parts = smsManager.divideMessage(Change12StatusMessage.toString());
                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);
                    }
                    if (strChange12Stat.equals("Change 2")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 3");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 3");
                        tvChangeDetails.setText("Change Heart");

                        SmsManager smsManager = SmsManager.getDefault();
                        Change12StatusMessage.append("Congratulations" + "\n");
                        Change12StatusMessage.append("John 14:6 " + "\n");
                        Change12StatusMessage.append("Jesus answered, I am the way the truth and the life. No one comes to the Father except through me");
                        ArrayList<String> parts = smsManager.divideMessage(Change12StatusMessage.toString());
                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);
                    }
                    if (strChange12Stat.equals("Change 3")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 4");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 4");
                        tvChangeDetails.setText("Change Relationship");

                        SmsManager smsManager = SmsManager.getDefault();
                        Change12StatusMessage.append("Congratulations" + "\n");
                        Change12StatusMessage.append("Ezekiel 36:26 " + "\n");
                        Change12StatusMessage.append("And I will give you a new heart, and I will put a new Spirit in you. I will take out your stony, and stubborn heart and give you a tender responsive hear.");
                        ArrayList<String> parts = smsManager.divideMessage(Change12StatusMessage.toString());
                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);
                    }
                    if (strChange12Stat.equals("Change 4")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 5");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 5");
                        tvChangeDetails.setText("Change Lifestyle");

                        SmsManager smsManager = SmsManager.getDefault();
                        Change12StatusMessage.append("Congratulations" + "\n");
                        Change12StatusMessage.append("Colossians 2:6-7 " + "\n");
                        Change12StatusMessage.append("And now, just as you accepted Christ Jesus as your Lord, you must continue to follow Him. Let your roots grow down into Him, and let your lives be built on Him. Then your Faith will grow strong in the truth you were taught, and you will overflow with thankfulness");
                        ArrayList<String> parts = smsManager.divideMessage(Change12StatusMessage.toString());
                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);
                    }
                    if (strChange12Stat.equals("Change 5")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Graduate");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Graduate");
                        tvChangeDetails.setText("Pre-Encounter");

                        SmsManager smsManager = SmsManager.getDefault();
                        Change12StatusMessage.append("Congratulations" + "\n");
                        Change12StatusMessage.append("Acts 2:42 " + "\n");
                        Change12StatusMessage.append("All the believers devoted themselves to the apostle's teaching, and to fellowship, and to sharing in meals and prayer");
                        ArrayList<String> parts = smsManager.divideMessage(Change12StatusMessage.toString());
                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);

                    }
                    if (strChange12Stat.equals("Graduate")) {
                        databaseAccess.open();
                        databaseAccess.updateConsolidatetoDisciple(consolidateID);
                        databaseAccess.close();

                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setPepsol_stat("Pre-Encounter");
                        databaseAccess.updateDisciplePepSolStat(consolidateID, updateChange12Stat);
                        databaseAccess.close();

                        Intent intent = new Intent(Consolidates_Profile.this, View_Disciples_Module.class);
                        intent.putExtra("dipFlag", 1);
                        startActivity(intent);
                        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        finish();
                    }
                    //fab.setEnabled(false);
                    showMessage("Message", "Change12 Status is Updated ");
                    strChange12Stat = tvChangeStatus.getText().toString().trim();
                    sb.setVisibility(View.GONE);
                    seekbartest.setVisibility(View.GONE);


                }

            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sb.setProgress(0);
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setupPhoto() {
        Bitmap bitmapImage = null;
        RoundImage roundedImage = new RoundImage();
        RoundedBitmapDrawable drawable;
       // Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.image_icon);
       // imgProfile.setImageBitmap(bm);
        /*try {
            bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(strPhoto));
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            Drawable d = new BitmapDrawable(getResources(), blur(bitmapImage));
            appBarLayout.setBackground(d);
            imgProfile.setImageDrawable(drawable);

        } catch (IOException e) {
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            Drawable d = new BitmapDrawable(getResources(), blur(icon));
            appBarLayout.setBackground(d);
            imgProfile.setImageDrawable(drawable);
        }*/


            if (!strPhoto.equals("")) {
                byte[] imageAsBytes = Base64.decode(strPhoto.getBytes(), Base64.DEFAULT);
                bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
                Drawable d = new BitmapDrawable(getResources(), blur(bitmapImage));
                appBarLayout.setBackground(d);
                imgProfile.setImageDrawable(drawable);
            } else {
                Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                        R.drawable.pic);
                drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
                Drawable d = new BitmapDrawable(getResources(), blur(icon));
                appBarLayout.setBackground(d);
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
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(strFirstName);

    }

    private void getData() {
        try{
            databaseAccess.open();
            databaseAccess.getConsolidateProfile(consolidateID);
            databaseAccess.close();

            parseData();
        }
        catch (Exception e){

        }
    }

    private void parseData() {
        StringBuilder buffer = new StringBuilder();
        // Disciple_Set_Get superHero = new Disciple_Set_Get();
        databaseAccess.open();
        for(int i = 0; i<databaseAccess.resultSet.length(); i++) {
            Disciple_Set_Get disciple_set_get = new Disciple_Set_Get();
            JSONObject json = null;
            try {
                json = databaseAccess.resultSet.getJSONObject(i);
                disciple_set_get.setPhoto(json.getString("photo"));
                disciple_set_get.setFirst_name(json.getString("first_name"));
                disciple_set_get.setUsers_id(json.getString(Config.USERS_USERS_ID));
                disciple_set_get.setContact_num(json.getString(Config.DISCIPLE_CONTACT_NUM));
                disciple_set_get.setSur_name(json.getString("last_name"));
                disciple_set_get.setPhotoFlag(json.getString("photo_updateFlag"));
                disciple_set_get.setEmail(json.getString("email_address"));
                disciple_set_get.setPepsol_stat(json.getString("pepsol_status"));
                disciple_set_get.setAffliation(json.getString("affiliation"));
                disciple_set_get.setChange12_stat(json.getString("change12_status"));
                disciple_set_get.setRemarks(json.getString("remarks"));
                disciple_set_get.setBirthdate(json.getString("birthdate"));
                disciple_set_get.setAddress(json.getString("address"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            strFirstName = disciple_set_get.getFirst_name().toString().trim();//+" " + disciple_set_get.getSur_name().toString().toString();
            strPhoto = disciple_set_get.getPhoto().toString().trim();
            strChange12Stat = disciple_set_get.getChange12_stat().toString().trim();
            strRemarks = disciple_set_get.getRemarks().toString().trim();
            strContact = disciple_set_get.getContact_num().toString().trim();
            strBday = disciple_set_get.getBirthdate().toString().trim();
            strAffiliation = disciple_set_get.getAffliation().toString().trim();
            strAddress = disciple_set_get.getAddress().toString().trim();
            strSurname = disciple_set_get.getSur_name().toString().trim();
        }
        databaseAccess.close();


        databaseAccess.open();
        databaseAccess.getConsolidateRemarks(consolidateID);
        for(int i = 0; i<databaseAccess.resultSet.length(); i++) {
            Disciple_Set_Get disciple_set_get = new Disciple_Set_Get();
            JSONObject json = null;
            try {
                json = databaseAccess.resultSet.getJSONObject(i);
                disciple_set_get.setUsers_id(json.getString(Config.USERS_USERS_ID));
                disciple_set_get.setRemarks(json.getString("remarks"));
                disciple_set_get.setTime_stamp(json.getString("time_stamp"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            buffer.append("" + disciple_set_get.getImageUrl()+"\n");
            fetchImageList.add(disciple_set_get);
        }
        databaseAccess.close();
        adapter = new RemarksAdapter(fetchImageList, this);


        recyclerView.setAdapter(adapter);
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(Consolidates_Profile.this, View_Consolidates_Module.class);
        intent.putExtra("dipFlag", 1);
        startActivity(intent);
        //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        finish();
    }


    public void clk_relContact(View v){
        if(v.equals(relContact)){
            initiatePopupWindow_Contact();
        }
    }

    private PopupWindow pwindo;

    private void initiatePopupWindow_Contact() {
        try {
// We need to get the instance of the LayoutInflater
            final PopupMenu popupMenu = new PopupMenu(this, relContact);
            popupMenu.inflate(R.menu.menu_send__sm);

            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.sending_sms,
                    (ViewGroup) findViewById(R.id.popup_element));
            WindowManager windowManager = (WindowManager)this.getSystemService(this.WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            int height = windowManager.getDefaultDisplay().getHeight();

            pwindo = new PopupWindow(layout, width-(width/6), (int)(height*0.80), true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);
            EditText edParticipants = (EditText) layout.findViewById(R.id.edParticipants);
            final EditText edMessage = (EditText) layout.findViewById(R.id.edMessage);
            FloatingActionButton fabSMS = (FloatingActionButton) layout.findViewById(R.id.fabSMS);
            edParticipants.setText(strFirstName);




            fabSMS.setOnClickListener(new View.OnClickListener() {
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
                    //
                }
            });
            popupMenu.setOnMenuItemClickListener(
                    new PopupMenu.OnMenuItemClickListener() {
                        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.myCancel:
                                    pwindo.dismiss();
                                    break;
                                case R.id.mySms:

                                    SmsManager smsManager = SmsManager.getDefault();
                                    ArrayList<String> parts = smsManager.divideMessage(edMessage.getText().toString().trim());

                                        smsManager.sendMultipartTextMessage(strContact, null, parts, null, null);

                                        databaseAccess.open();
                                        smsManager.sendMultipartTextMessage(databaseAccess.contact_num, null, parts, null, null);
                                        databaseAccess.close();

                                    pwindo.dismiss();
                                    break;
                            }
                            return true;
                        }
                    });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id==R.id.delete){
            alertBox_delete();
        }

        if(id==R.id.update){

            Intent intent = new Intent(Consolidates_Profile.this, Add_Consolidates_Module.class);
            intent.putExtra("dipFlag", 1);
            intent.putExtra("first_name", strFirstName);
            intent.putExtra("sur_name", strSurname);
            intent.putExtra("affliation", strAffiliation);
            intent.putExtra("photo", strPhoto);
            intent.putExtra("birthdate", strBday);
            intent.putExtra("address",strAddress);
            intent.putExtra("contact",strContact);
            intent.putExtra("consolidateId", consolidateID);
            startActivity(intent);
            //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();

        }


        if (id == android.R.id.home) {
            Intent intent = new Intent(Consolidates_Profile.this, View_Consolidates_Module.class);
            intent.putExtra("dipFlag", 1);

            startActivity(intent);
            //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    int dropFlag=0;
    String str="Are you sure to drop Disciple?";
    private void alertBox_delete() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(str);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (dropFlag == 0) {
                    dropFlag = 1;
                    str = "Are you Really sure to drop Disciple?";
                    alertBox_delete();
                } else {
                    databaseAccess.open();
                    databaseAccess.updateActiveStatus(consolidateID);
                    databaseAccess.close();
                    showMessage("Message", "\n" +
                            "2 Peter 3:9New Living Translation (NLT)\n" +
                            "\n" +
                            "9 The Lord isn’t really being slow about his promise, as some people think. " +
                            "No, he is being patient for your sake. He does not want anyone to be destroyed, " +
                            "but wants everyone to repent. ");
                    Intent intent = new Intent(Consolidates_Profile.this, View_Consolidates_Module.class);
                    intent.putExtra("dipFlag", 1);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected  void onPause(){
        super.onPause();
        finish();

    }

    @Override
    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
        if (arg1 > 95) {
            arg0.setThumb(getResources().getDrawable(R.drawable.slider_icon));
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar arg0) {
        seekbartest.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStopTrackingTouch(SeekBar arg0) {
        //Log.d("onStopTrackingTouch", "onStopTrackingTouch");
        if (arg0.getProgress() < 80) {
            arg0.setProgress(0);
            sb.setBackgroundResource(R.drawable.slider_back);
            seekbartest.setVisibility(View.VISIBLE);
            seekbartest.setText("Update Change12 Stat");

        } else {
            arg0.setProgress(100);
            seekbartest.setVisibility(View.VISIBLE);

            status=true;
            alertBox(status);
        }
    }

}


/*else {

                    if (strChange12Stat.equals("Change 2")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 1");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 1");
                        tvChangeDetails.setText("Change Destination");
                    }
                    if (strChange12Stat.equals("Change 3")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 2");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 2");
                        tvChangeDetails.setText("Change Direction");
                    }
                    if (strChange12Stat.equals("Change 4")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 3");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 3");
                        tvChangeDetails.setText("Change Heart");
                    }
                    if (strChange12Stat.equals("Change 5")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 4");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 4");
                        tvChangeDetails.setText("Change Relationship");
                    }
                    if (strChange12Stat.equals("Graduate")) {
                        databaseAccess.open();
                        Disciple_Set_Get updateChange12Stat = new Disciple_Set_Get();
                        updateChange12Stat.setChange12_stat("Change 5");
                        databaseAccess.updateChangeStatus(consolidateID, updateChange12Stat);
                        databaseAccess.close();
                        tvChangeStatus.setText("Change 5");
                        tvChangeDetails.setText("Change Lifestyle");
                    }

                    //fabLeft.setEnabled(false);
                    showMessage("Message", "Change12 Status is Updated ");
                    strChange12Stat = tvChangeStatus.getText().toString().trim();
                }*/