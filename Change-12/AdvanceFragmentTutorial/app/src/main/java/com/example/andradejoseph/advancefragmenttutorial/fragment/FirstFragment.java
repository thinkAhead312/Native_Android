package com.example.andradejoseph.advancefragmenttutorial.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andradejoseph.advancefragmenttutorial.R;

/**
 * Created by ANDRADEJOSEPH on 2/24/2017.
 */

public class FirstFragment extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.first_fragment,
                container, false);

        Button mButtonNext = (Button) view.findViewById(R.id.button_first);
        mButtonNext.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

    }
}
