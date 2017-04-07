package com.example.andradejoseph.change12_ver_2;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andradejoseph.change12_ver_2.custom.SingleFragmentActivity;
import com.example.andradejoseph.change12_ver_2.ui.Change12CurrentWaveConsolidates;
import com.example.andradejoseph.change12_ver_2.utils.DrawerActivity;


public class WaveDetailActivity extends SingleFragmentActivity{


    public static Intent newIntent(Context packageContext, String waveId) {
        Intent i = new Intent(packageContext, WaveDetailActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.putExtra(Change12CurrentWaveConsolidates.EXTRA_WAVE_ID, waveId);
        return i;
    }

    @Override
    protected Fragment createFragment() {
        String waveId = getIntent().getStringExtra(Change12CurrentWaveConsolidates.EXTRA_WAVE_ID);
        return Change12CurrentWaveConsolidates.newInstance(waveId);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }




}
