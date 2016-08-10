package com.example.glenda_pc.change12_activity.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.glenda_pc.change12_activity.Lesson_Five;
import com.example.glenda_pc.change12_activity.Lesson_Four;
import com.example.glenda_pc.change12_activity.Lesson_One;
import com.example.glenda_pc.change12_activity.Lesson_Three;
import com.example.glenda_pc.change12_activity.Lesson_Two;
import com.example.glenda_pc.change12_activity.R;


public class TwoFragment extends Fragment {


    ImageButton btnLesson1,btnLesson2,btnLesson3,btnLesson4,btnLesson5;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);


        btnLesson1 = (ImageButton) rootView.findViewById(R.id.btnLesson1);
        btnLesson2 = (ImageButton) rootView.findViewById(R.id.btnLesson2);
        btnLesson3 = (ImageButton) rootView.findViewById(R.id.btnLesson3);
        btnLesson4 = (ImageButton) rootView.findViewById(R.id.btnLesson4);
        btnLesson5 = (ImageButton) rootView.findViewById(R.id.btnLesson5);

        btnLesson1.getBackground().setAlpha(80);
        btnLesson2.getBackground().setAlpha(90);
        btnLesson3.getBackground().setAlpha(90);
        btnLesson4.getBackground().setAlpha(90);
        btnLesson5.getBackground().setAlpha(90);

        btnLesson1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "asfasassfasdf", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Lesson_One.class));
            }
        });



        btnLesson2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "asfasassfasdf", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Lesson_Two.class));
            }

        });

        btnLesson3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "asfasassfasdf", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Lesson_Three.class));
            }

        });

        btnLesson4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "asfasassfasdf", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Lesson_Four.class));
            }

        });

        btnLesson5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(), "asfasassfasdf", Toast.LENGTH_SHORT).show();
                Context context = v.getContext();
                context.startActivity(new Intent(context, Lesson_Five.class));
            }

        });


        return rootView;
    }





}
