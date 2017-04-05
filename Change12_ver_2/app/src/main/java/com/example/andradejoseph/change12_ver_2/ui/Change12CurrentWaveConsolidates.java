package com.example.andradejoseph.change12_ver_2.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andradejoseph.change12_ver_2.R;

/**
 * Created by ANDRADEJOSEPH on 4/5/2017.
 */

public class Change12CurrentWaveConsolidates extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_wave_consolidates, container, false);
        String cardTransitionName = "";
        Bundle bundle = getArguments();

        if(bundle != null) {
            cardTransitionName = bundle.getString("TRANS_NAME");
            ((TextView) v.findViewById(R.id.wave_num_title_tv)).setText(bundle.getString("WAVE_NUM"));
            ((TextView) v.findViewById(R.id.date_tv)).setText(bundle.getString("WAVE_DATE"));

        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            v.findViewById(R.id.wave_list_card_view).setTransitionName(cardTransitionName);
        }



        return v;
    }
}
