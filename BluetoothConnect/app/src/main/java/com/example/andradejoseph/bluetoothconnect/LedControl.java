package com.example.andradejoseph.bluetoothconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andradejoseph.bluetoothconnect.utils.Constants;

public class LedControl extends AppCompatActivity implements View.OnTouchListener, SeekBar.OnSeekBarChangeListener{


    public final static String TAG = "AndroidColor";
    public ColorPickerView colorPicker;
    private TextView text1;
    SeekBar seek;
    private static final int blueStart = 100;

    private Toolbar toolbar;
    private String mBtInfo;

    public static Intent newIntent(Context packageContext , String btInfo) {
        Intent i = new Intent(packageContext, LedControl.class);
        i.putExtra(Constants.EXTRA_ADDRESS, btInfo);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);
        mBtInfo = getIntent().getStringExtra(Constants.EXTRA_ADDRESS);

        init();
    }

    private void init() {


        LinearLayout layout = (LinearLayout) findViewById(R.id.color_picker_layout);
        final int width = layout.getWidth();
        text1 = (TextView) findViewById(R.id.result1_textview);
        //get the display density
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        colorPicker = new ColorPickerView(this,blueStart,metrics.densityDpi);

        layout.setMinimumHeight(width);
        layout.addView(colorPicker);
        layout.setOnTouchListener(this);

        text1.setText("Tap a color!");

         seek = (SeekBar) findViewById(R.id.seekBar1);
        seek.setProgress(blueStart);
        seek.setMax(255);
        seek.setOnSeekBarChangeListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        int color = 0;
        color = colorPicker.getColor(event.getX(),event.getY(),true);
        colorPicker.invalidate();
        //re-draw the selected colors text
        updateTextAreas(color);
        //send data to arduino
        sendToArduino(color);
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        int amt = seek.getProgress();
        int col = colorPicker.updateShade(amt);
        updateTextAreas(col);
        sendToArduino(col);
        colorPicker.invalidate();

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    // generate a random hex color & display it
    public void randomColor(View v) {
        int z = (int) (Math.random()*255);
        int x = (int) (Math.random()*255);
        int y = (int) (Math.random()*255);
        colorPicker.setColor(x,y,z);
        SeekBar seek = (SeekBar) findViewById(R.id.seekBar1);
        seek.setProgress(z);
    }

    // sends color data to a Serial device as {R, G, B, 0x0A}
    private void sendToArduino(int color){
        byte[] dataToSend = {(byte)Color.red(color),(byte)Color.green(color),(byte)Color.blue(color), 0x0A};
        //remove spurious line endings from color bytes so the serial device doesn't get confused
        for (int i=0; i<dataToSend.length-1; i++){
            if (dataToSend[i] == 0x0A){
                dataToSend[i] = 0x0B;
            }
        }
//        //send the color to the serial device
//        if (device != null){
//            try{
//                device.write(dataToSend, 500);
//            }
//            catch (IOException e){
//                Log.e(TAG, "couldn't write color bytes to serial device");
//            }
//        }
    }

    // sets the text boxes' text and color background.
    private void updateTextAreas(int col) {
        int[] colBits = {Color.red(col),Color.green(col),Color.blue(col)};
        //set the text & color backgrounds
        text1.setText("You picked #" + String.format("%02X", Color.red(col)) + String.format("%02X", Color.green(col)) + String.format("%02X", Color.blue(col)));
        text1.setBackgroundColor(col);

        if (isDarkColor(colBits)) {
            text1.setTextColor(Color.WHITE);
        } else {
            text1.setTextColor(Color.BLACK);
        }
    }

    // returns true if the color is dark.  useful for picking a font color.
    public boolean isDarkColor(int[] color) {
        if (color[0]*.3 + color[1]*.59 + color[2]*.11 > 150) return false;
        return true;
    }
}
