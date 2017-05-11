package com.example.andradejoseph.profiler;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ANDRADEJOSEPH on 5/10/2017.
 */

public class FragmentTemplate extends Fragment implements Contract.View{
    private Contract.Presenter presenter;

    public FragmentTemplate() {
        // Required empty public constructor
    }

    public static FragmentTemplate newInstance() {
        FragmentTemplate fragment = new FragmentTemplate();
        return fragment;
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v  = inflater.inflate(R.layout.fragment_profile_page,container,false);
        //where we assign our Views
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //where we our create our presenter if necessary
        if(presenter == null) {
            presenter = new Presenter();
        }
        presenter.subscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
