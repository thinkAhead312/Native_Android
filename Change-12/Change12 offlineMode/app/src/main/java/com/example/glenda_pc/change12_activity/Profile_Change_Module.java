package com.example.glenda_pc.change12_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;
import com.example.glenda_pc.change12_activity.Model.Disciple_Set_Get;
import com.example.glenda_pc.change12_activity.Model.RoundImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class Profile_Change_Module extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private DatabaseAccess databaseAccess;

    private ImageView imgButton;
    EditText edFname, edLName, edEmail, edContactNum, edBirthdate, edAddress, edPassWord, edConfirmPass;
    SwitchCompat switchGender, switchCloseOpenCell;
    Bitmap bitmap1=null;

    private Calendar calendar;
    private int year, month, day,Gender=1, CloseOpenCell=0;
    String savedURL="";

    private Uri mImageCaptureUri;
    private AlertDialog dialog;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;

    RoundImage roundedImage = new RoundImage();
    RoundedBitmapDrawable drawable;

    String[] arrayAffiliation={"University of San Carlos","University of the Philippines Cebu College","St. Theresa’s College","University of Cebu","University of Cebu-METC","University of Southern Philippines","University of San Jose-Recoletos",
            "University of the Visayas","Gullas Medical Center","Velez College","Cebu Doctor’s University","Cebu Normal University","Southwestern University","Cebu Institute of Technology","Asian College of Technology","AMA",
            "St. Paul College Foundation","Cebu Technological University","Center for Industrial Technology and Enterprise","Colegio de la Inmaculada Concepcion","College of Technological Sciences","Don Bosco Technology Center","Interface Computer College, Inc.","Josrika Computer Training Center, Inc.",
            "Salazar Institute of Technology, Inc.","STI College Cebu","STI College - Punta Princesa","Seminario Mayor de San Carlos","Royal Oaks International School - Cebu City","Cebu Sacred Heart College","Cebu City Medical Center College of Nursing","Cebu Eastern College","Cebu International School","Sacred Heart School of the Society of Jesus",
            "Woodridge School", "B.R.I.G.H.T. Academy","Bethany Christian School","St. Francis of Assisi School","Cebu City National Science High School","PAREF Southcrest","Children’s Paradise Montessori School","Benedicto College","De La Salle Andres Soriano Memorial College","St. Scholastica's Academy","Abraham's Children Montessori","Colegio de San Antonio de Padua",
            "Child Learning Foundation (CLF)","Exceptional Children's Educational Center","ETHOS language school"};

    int updateFlag=0; //if zero register account, otherwise update


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__change__module);
        captureImageInitialization();
        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        Intent intent = getIntent();
        updateFlag = intent.getIntExtra("dipFlag", 0);

        imgButton = (ImageView) findViewById(R.id.imgB_AddPicture);
        edFname = (EditText) findViewById(R.id.ed_FirstName);
        edLName = (EditText) findViewById(R.id.ed_LastName);
        edEmail = (EditText) findViewById(R.id.ed_EmailAd);
        edContactNum = (EditText) findViewById(R.id.edContactNum);
        edBirthdate = (EditText) findViewById(R.id.edBirthdate);
        edAddress = (EditText) findViewById(R.id.edAddress);
        edPassWord =(EditText) findViewById(R.id.edPassword);
        edConfirmPass = (EditText) findViewById(R.id.edPasswordConfirm);
        switchCloseOpenCell  = (SwitchCompat) findViewById(R.id.switchCloseCell);
        switchGender = (SwitchCompat) findViewById(R.id.switchGender);
        switchGender.setOnCheckedChangeListener(this);
        switchCloseOpenCell.setOnCheckedChangeListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");


        if(updateFlag==1){
            actionBar.setTitle("Update");
            databaseAccess.open();
            savedURL = databaseAccess.photo;
            setupPhoto();
            edFname.setText(databaseAccess.first_name);
            edLName.setText(databaseAccess.sur_name);
            edContactNum.setText(databaseAccess.contact_num);
            edBirthdate.setText(databaseAccess.birthdate);
            edAddress.setText(databaseAccess.address);
            databaseAccess.close();
        }else {
            setupPhoto();
        }

        calendar = Calendar.getInstance();  //Calendar get instance
        year = calendar.get(Calendar.YEAR); //
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //showDate(year, month + 1, day);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }
    private void setupPhoto() {
        Bitmap bitmapImage =null;
        databaseAccess.open();
        if(!databaseAccess.photo.equals("")) {
            byte[] imageAsBytes = Base64.decode(databaseAccess.photo.getBytes(), Base64.DEFAULT);
            bitmapImage = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(bitmapImage);
            imgButton.setImageDrawable(drawable);
        }else{
            Bitmap icon = BitmapFactory.decodeResource(this.getResources(),
                    R.drawable.pic);
            drawable = (RoundedBitmapDrawable) roundedImage.roundImg(icon);
            imgButton.setImageDrawable(drawable);
        }
        databaseAccess.close();
    }
    public void onStart(){
        super.onStart();
        edBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View view, boolean hasfocus) {
                if (hasfocus) {
                    final CustomDatePicker pickerDialog = new CustomDatePicker(Profile_Change_Module.this ,myDateListener, 2016, 12, 30);
                    pickerDialog.setMaxDate(System.currentTimeMillis());
                    pickerDialog.show();
                }
            }
        });
    }
                                                                                                    ////////////////////////////////////////////////////////////////////////////
    private void showDate(int year, int month, int day) {                                           ////////////////////////////////////////////////////////////////////////////
        edBirthdate.setText(new StringBuilder().append(day).append("-")                             ///////////////////
                .append(month).append("-").append(year));                                           ///////////////////
    }                                                                                               ///////////////////
    /*@Override                                                                                       ///////////////////  CALENDAR DATE PICKER
    protected Dialog onCreateDialog(int id) {                                                       ///////////////////
        // TODO Auto-generated method stub                                                          ////////////////////////////////////////////////////////////////////////////
        if (id == 999) {                                                                            ////////////////////////////////////////////////////////////////////////////
            return new DatePickerDialog(this, myDateListener, year, month, day);                    ////////////////////////////////////////////////////////////////////////////
        }                                                                                           ////////////////////////////////////////////////////////////////////////////
        return null;                                                                                ////////////////////////////////////////////////////////////////////////////
    } */                                                                                              ////////////////////////////////////////////////////////////////////////////
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {///////////////////
        @Override                                                                                   ///////////////////
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {       ////////////////////////////////////////////////////////////////////////////
            showDate(arg1, arg2+1, arg3);
            Log.i("MyActivity", "MyDate— get item number " + arg1);
        }                                                                                           ///////////////////
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.custom_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.save){

            if(edFname.getText().toString().trim().equals("") || edLName.getText().toString().trim().equals("")
                    || edContactNum.getText().toString().trim().equals("")
                    || edBirthdate.getText().toString().trim().equals("") || edAddress.getText().toString().trim().equals("")
                    ) {
                    showMessage("Error","Fill all Fields" );
            }
            else{

                if(edPassWord.getText().toString().trim().equals(edConfirmPass.getText().toString().trim())){
                    alertBox("Are you sure,You wanted to make decision");
                }else {
                    showMessage("Error","Password Not Match");
                }
            }
        }
        if(id==R.id.cancel){
            if(updateFlag==1){

                    alertBox("Are you sure to cancel?");
            }
            else {
                alertCancel("Are you sure to cancel?");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.switchGender:
                if(isChecked){
                    Gender=0;
                }else{
                    Gender=1;
                }
                // Log.i("switch_compat", isChecked + "");
                break;
            case R.id.switchCloseCell:
                if(isChecked){
                    CloseOpenCell=0;
                }else{
                    CloseOpenCell=1;
                }
        }
    }

    private void alertBox(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                //filePathSavetoSqLite(bitmap1);
                if(updateFlag==1){
                    accountUpdate();
                }
                else {
                    accountVerification();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(updateFlag==1) {

                    Intent intent = new Intent(Profile_Change_Module.this,Change12_Activity.class);
                    intent.putExtra("dipFlag",1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();

                }
                return;
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void accountUpdate() {
        databaseAccess.open();
        Disciple_Set_Get discipleTable = new Disciple_Set_Get();
        

        discipleTable.setFirst_name(edFname.getText().toString().trim());
        discipleTable.setSur_name(edLName.getText().toString().trim());
        discipleTable.setPhoto(savedURL);
        discipleTable.setContact_num(edContactNum.getText().toString().trim());
        discipleTable.setBirthdate(edBirthdate.getText().toString().trim());
        discipleTable.setAddress(edAddress.getText().toString().trim());
        databaseAccess.updateProfile(discipleTable);
        databaseAccess.close();

        Intent intent = new Intent(this,Change12_Activity.class);
        intent.putExtra("dipFlag", 1);
        this.startActivity(intent);
        finish();
    }


    private void accountVerification() {

        String users_id= getUserID(), parents_id="0000";
        int prime_flag = 1;
        int consolidates_flag = 0;
        int active = 1;
        int account_verified_flag = 1;


        //Insert to UsersTable



        //Insert to Users Table
        databaseAccess.open();
        Disciple_Set_Get discipleUserTbl = new Disciple_Set_Get();
        discipleUserTbl.setUsers_id(users_id);
        discipleUserTbl.setParents_id(parents_id);
        discipleUserTbl.setPrime_flag(String.valueOf(prime_flag));
        discipleUserTbl.setClose_cell_flag(String.valueOf(CloseOpenCell));
        discipleUserTbl.setConsolidates_flag(String.valueOf(consolidates_flag));
        discipleUserTbl.setActive(String.valueOf(active));
        discipleUserTbl.setAccount_verified_flag(String.valueOf(account_verified_flag));
        discipleUserTbl.setPhotoFlag(String.valueOf(0));


        //Insert to Disciples table
        Disciple_Set_Get discipleTable = new Disciple_Set_Get();
        discipleTable.setUsers_id(users_id);
        discipleTable.setFirst_name(edFname.getText().toString().trim());
        discipleTable.setSur_name(edLName.getText().toString().trim());
        discipleTable.setPhoto(savedURL);
        discipleTable.setContact_num(edContactNum.getText().toString().trim());
        discipleTable.setEmail(edEmail.getText().toString().trim());
        discipleTable.setPassword(edConfirmPass.getText().toString().trim());
        discipleTable.setBirthdate(edBirthdate.getText().toString().trim());
        discipleTable.setGender(String.valueOf(Gender));
        discipleTable.setAddress(edAddress.getText().toString().trim());
        discipleTable.setPepsol_stat("PEPSOL");
        discipleTable.setChange12_stat("");
        discipleTable.setRemarks("");
        discipleTable.setGraduate_flag("");
        discipleTable.setAffliation("");

        /*BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute("profileUserTbl", discipleUserTbl.getUsers_id(), discipleUserTbl.getParents_id(), discipleUserTbl.getPrime_flag(), discipleUserTbl.getClose_cell_flag(), discipleUserTbl.getConsolidates_flag(), discipleUserTbl.getActive(), discipleUserTbl.getAccount_verified_flag(), discipleUserTbl.getPhotoFlag(),
                discipleTable.getFirst_name(), discipleTable.getSur_name(), discipleTable.getPhoto(), discipleTable.getContact_num(), discipleTable.getEmail(), discipleTable.getPassword(), discipleTable.getBirthdate(),
                discipleTable.getGender(), discipleTable.getAddress(), discipleTable.getPepsol_stat(), discipleTable.getChange12_stat(), discipleTable.getRemarks(), discipleTable.getGraduate_flag(), discipleTable.getAffliation());

        */
        databaseAccess.open();
        databaseAccess.insertUsersTable(discipleUserTbl);
        databaseAccess.close();
        databaseAccess.open();
        databaseAccess.insertDisciplesTable(discipleTable);
        databaseAccess.close();

        databaseAccess.open();
        int queryCount = databaseAccess.users_disciplesTableJoin();
        databaseAccess.close();
        //this.finish();

        if(queryCount==1){

            Intent intent = new Intent(Profile_Change_Module.this,Change12_Activity.class);
            intent.putExtra("dipFlag",1);
            startActivity(intent);
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
            finish();
        }
        
    }

    private String getUserID() {

        Random r = new Random();
        int i1 = r.nextInt(9999 - 1 + 1) + 1;
        String randomStr = String.valueOf(i1);
        int strlen = randomStr.length();
        if(strlen==3){
           randomStr = "0" + randomStr;
        }
        else if(strlen ==2){
            randomStr = "00" + randomStr;
        }
        else if(strlen==1){
            randomStr = "000" + randomStr;
        }
        return  randomStr;
    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    private void captureImageInitialization() {
        /**
         * a selector dialog to display two image source options, from camera
         * ‘Take from camera’ and from existing files ‘Select from gallery’
         */
        final String[] items = new String[] { "Take from camera",
                "Select from gallery" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    /**
                     * To take a photo from camera, pass intent action
                     * ‘MediaStore.ACTION_IMAGE_CAPTURE‘ to open the camera app.
                     */
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    /**
                     * Also specify the Uri to save the image on specified path
                     * and file name. Note that this Uri variable also used by
                     * gallery app to hold the selected image path.
                     */
                    mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
                            mImageCaptureUri);

                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // pick from file
                    /**
                     * To select an image from existing files, use
                     * Intent.createChooser to open image chooser. Android will
                     * automatically display a list of supported applications,
                     * such as image gallery or file manager.
                     */
                    Intent intent = new Intent();

                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_FROM_FILE);
                }
            }
        });

        dialog = builder.create();
    }
    public class CropOptionAdapter extends ArrayAdapter<CropOption> {
        private ArrayList<CropOption> mOptions;
        private LayoutInflater mInflater;

        public CropOptionAdapter(Context context, ArrayList<CropOption> options) {
            super(context, R.layout.crop_selector, options);

            mOptions = options;

            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup group) {
            if (convertView == null)
                convertView = mInflater.inflate(R.layout.crop_selector, null);

            CropOption item = mOptions.get(position);

            if (item != null) {
                ((ImageView) convertView.findViewById(R.id.iv_icon))
                        .setImageDrawable(item.icon);
                ((TextView) convertView.findViewById(R.id.tv_name))
                        .setText(item.title);

                return convertView;
            }

            return null;
        }
    }

    public class CropOption {
        public CharSequence title;
        public Drawable icon;
        public Intent appIntent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA:
                /**
                 * After taking a picture, do the crop
                 */
                doCrop();

                break;

            case PICK_FROM_FILE:
                /**
                 * After selecting image from files, save the selected path
                 */
                mImageCaptureUri = data.getData();

                doCrop();

                break;

            case CROP_FROM_CAMERA:
                Bundle extras = data.getExtras();
                /**
                 * After cropping the image, get the bitmap of the cropped image and
                 * display it on imageview.
                 */
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    drawable = (RoundedBitmapDrawable) roundedImage.roundImg(photo);
                    imgButton.setImageDrawable(drawable);





                    //Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),  R.drawable.image);
                    ByteArrayOutputStream bao = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, bao);
                    byte [] ba = bao.toByteArray();
                    String ba1= Base64.encodeToString(ba, Base64.DEFAULT);
                    filePathSavetoSqLite(ba1);
                }

                File f = new File(mImageCaptureUri.getPath());
                /**
                 * Delete the temporary image
                 */
                if (f.exists())
                    f.delete();

                break;

        }
    }

    private void doCrop() {
        final ArrayList<CropOption> cropOptions = new ArrayList<CropOption>();
        /**
         * Open image crop app by starting an intent
         * ‘com.android.camera.action.CROP‘.
         */
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        /**
         * Check if there is image cropper app installed.
         */
        List<ResolveInfo> list = getPackageManager().queryIntentActivities(
                intent, 0);

        int size = list.size();

        /**
         * If there is no image cropper app, display warning message
         */
        if (size == 0) {

            Toast.makeText(this, "Can not find image crop app",
                    Toast.LENGTH_SHORT).show();

            return;
        } else {
            /**
             * Specify the image path, crop dimension and scale
             */
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);
            /**
             * There is posibility when more than one image cropper app exist,
             * so we have to check for it first. If there is only one app, open
             * then app.
             */

            if (size == 1) {
                Intent i = new Intent(intent);
                ResolveInfo res = list.get(0);

                i.setComponent(new ComponentName(res.activityInfo.packageName,
                        res.activityInfo.name));

                startActivityForResult(i, CROP_FROM_CAMERA);
            } else {
                /**
                 * If there are several app exist, create a custom chooser to
                 * let user selects the app.
                 */
                for (ResolveInfo res : list) {
                    final CropOption co = new CropOption();

                    co.title = getPackageManager().getApplicationLabel(
                            res.activityInfo.applicationInfo);
                    co.icon = getPackageManager().getApplicationIcon(
                            res.activityInfo.applicationInfo);
                    co.appIntent = new Intent(intent);

                    co.appIntent
                            .setComponent(new ComponentName(
                                    res.activityInfo.packageName,
                                    res.activityInfo.name));

                    cropOptions.add(co);
                }

                CropOptionAdapter adapter = new CropOptionAdapter(
                        getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Crop App");
                builder.setAdapter(adapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                startActivityForResult(
                                        cropOptions.get(item).appIntent,
                                        CROP_FROM_CAMERA);
                            }
                        });

                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {

                        if (mImageCaptureUri != null) {
                            getContentResolver().delete(mImageCaptureUri, null,
                                    null);
                            mImageCaptureUri = null;
                        }
                    }
                });

                AlertDialog alert = builder.create();

                alert.show();
            }
        }
    }

    private void filePathSavetoSqLite(String bitmapImage) {

        ContentResolver cr = getContentResolver();
        String title = "Change 12";
        String description = "My bitmap created by Android-er";
        savedURL = bitmapImage;
        //MediaStore.Images.Media
          //      .insertImage(cr, bitmapImage, title, description);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code

            if(updateFlag==1){

                alertBox("Are you sure to cancel?");
            }
            else {
                alertCancel("Are you sure to cancel?");
            }

        }

        return super.onKeyDown(keyCode, event);
    }

    private void alertCancel(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                    Intent intent = new Intent(Profile_Change_Module.this, Registration_Module.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                

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


}






