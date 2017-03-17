package com.example.andradejoseph.change12_ver_2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.andradejoseph.change12_ver_2.R;
import com.squareup.picasso.Picasso;


public class ChangeIntroFragment extends Fragment  {
    private static final int REQUEST_CODE = 1;
    RelativeLayout relativeLayout;
    private ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_intro, container, false);
        imageView = (ImageView) v.findViewById(R.id.fragment_change_intro_image_view);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.fragment_change_intro_container);


        Picasso.with(getActivity()).load(R.drawable.joel).into(imageView);
        return v;
    }


}
