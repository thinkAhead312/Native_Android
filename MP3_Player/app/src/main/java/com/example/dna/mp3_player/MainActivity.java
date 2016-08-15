package com.example.dna.mp3_player;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private Button b1, b2, b3, b4;
    private ImageView iv;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler myHandler = new Handler();
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;
    private ImageView bg_image;
    public static int oneTimeOnly = 0;
    private String title="", music="" ,image="";

    private String finalUrl = "https://ia601506.us.archive.org/31/items/my_music_201606/my_music.xml";
    private HandleXML obj;

    MediaPlayer mediaPlayer =new MediaPlayer();
    String musicURL="";

    Bitmap bitmap;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        b4=(Button)findViewById(R.id.button4);
        iv=(ImageView)findViewById(R.id.imageView);
        bg_image = (ImageView) findViewById(R.id.imageView);

        tx1=(TextView)findViewById(R.id.textView2);
        tx2=(TextView)findViewById(R.id.textView3);
        tx3=(TextView)findViewById(R.id.textView4);


        fetchXML();
        setMusic();
        setTitle_Image();


        seekbar=(SeekBar)findViewById(R.id.seekBar);
        //seekbar.setClickable(false);

        seekbar.setEnabled(false);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //textView.setText("Covered: " + progress + "/" + );
               // String str =
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar:" + String.valueOf(progress) +"/" + String.valueOf(seekBar.getMax()), Toast.LENGTH_SHORT).show();
                startTime = startTime + progress;
                mediaPlayer.seekTo((int) progress);

            }
        });


        b2.setEnabled(false);

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                setMusic();
                mediaPlayer.start();
                seekbar.setEnabled(true);
                setTime();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mediaPlayer.pause();
                //seekbar.setClickable(false);
                seekbar.setEnabled(false);
                b2.setEnabled(false);
                b3.setEnabled(true);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null && mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer.pause();
                    setMusic();
                    setTime();
                    Toast.makeText(MainActivity.this, "STOP", Toast.LENGTH_SHORT).show();
                    b3.setEnabled(true);
                }

            }
        });
    }

    private void setTitle_Image(){
        tx3.setText(title);

            new LoadImage().execute(image);

    }

    private void setTime() {
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        seekbar.setMax((int) finalTime);
        if (oneTimeOnly == 0) {
            seekbar.setMax((int) finalTime);
            oneTimeOnly = 1;
        }
        tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime)))
        );

        tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime)))
        );
        seekbar.setProgress((int) startTime);
        myHandler.postDelayed(UpdateSongTime, 100);
        b2.setEnabled(true);
        b3.setEnabled(false);
    }

    private void setMusic() {
        musicURL = music.trim();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        try {
            mediaPlayer.setDataSource(musicURL);
        } catch (IllegalArgumentException e) {
            //Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            // Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            //  Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IllegalStateException e) {
            // Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            //Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        }//
    }

    private void fetchXML() {
        obj = new HandleXML(finalUrl);
        obj.fetchXML();

        while(obj.parsingComplete) ;
        title=obj.getTitle();
        image=obj.getImage();
        music =obj.music();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                            TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                            toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading Image ....");
            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                bg_image.setImageBitmap(image);
                pDialog.dismiss();

            }else{

                pDialog.dismiss();
                Toast.makeText(MainActivity.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
