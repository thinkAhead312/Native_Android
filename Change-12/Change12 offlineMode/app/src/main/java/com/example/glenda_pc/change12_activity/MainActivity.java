package com.example.glenda_pc.change12_activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.glenda_pc.change12_activity.Database.DatabaseAccess;

public class MainActivity extends AppCompatActivity {



    private DatabaseAccess databaseAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // createDatabase(); //Create Database method
        this.databaseAccess = DatabaseAccess.getInstance(getApplicationContext());


        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
              //  registerUser();
                queryCheck();
            }
        }.start();
    }






    private void queryCheck() {


        //DatePickerDialog.OnDateSetListener myDateListener = null;






            try {

            databaseAccess.open();

            if(databaseAccess.users_disciplesTableJoin()==0) {

                Intent intent = new Intent(MainActivity.this,Registration_Module.class);
                //way labot              //intent.putExtra("dipFlag",1);
                startActivity(intent);
                overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                finish();
            }
            else{

             if(databaseAccess.account_verified_flag.equals("1")){

                 //Toast.makeText(MainActivity.this, "Hi", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,Change12_Activity.class);
                    intent.putExtra("dipFlag",1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                    finish();
                }
            }
                databaseAccess.close();
            
        }catch (Exception e){
            showMessage("Errow", e.toString());
        }


    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }




}
