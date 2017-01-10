package com.example.andradejoseph.change12_ver_2.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * {@link ChangeIntroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChangeIntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangeIntroFragment extends Fragment {
    RelativeLayout relativeLayout;
    private ImageView imageView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change_intro, container, false);
        imageView = (ImageView) v.findViewById(R.id.fragment_change_intro_image_view);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.fragment_change_intro_container);
        Picasso.with(getActivity()).load(R.drawable.intro_background).into(imageView);
        return v;
    }


}
