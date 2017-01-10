package com.example.josephandrade.admob_adview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import com.google.android.gms.ads.MobileAds;

public class AdbmoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adbmo);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-9347789477148979~3097500244");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("016D213C00350ECE537134CBDE8935A7")
                .build();
        mAdView.loadAd(adRequest);

    }
}
