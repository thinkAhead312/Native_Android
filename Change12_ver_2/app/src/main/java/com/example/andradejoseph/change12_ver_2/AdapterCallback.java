package com.example.andradejoseph.change12_ver_2;

import android.view.View;
import android.widget.TextView;

/**
 * Created by ANDRADEJOSEPH on 4/5/2017.
 */

public interface AdapterCallback {

    void onWaveItemCallback(View mChangeWaveNum, String waveNum, String waveDate);

    void onPopStack();


}
