package com.example.andradejoseph.change12_ver_2.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.andradejoseph.change12_ver_2.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentB.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentB#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentB extends Fragment {
    private ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_intro, container, false);
        imageView = (ImageView) v.findViewById(R.id.fragment_change_intro_image_view);
        Picasso.with(getActivity()).load(R.drawable.intro_background).into(imageView);

        return  v;
    }
}
