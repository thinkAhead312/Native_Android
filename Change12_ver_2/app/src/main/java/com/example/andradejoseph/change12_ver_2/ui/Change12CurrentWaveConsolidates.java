package com.example.andradejoseph.change12_ver_2.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.WaveDetailActivity;
import com.example.andradejoseph.change12_ver_2.WaveListActivity;
import com.example.andradejoseph.change12_ver_2.adapter.WaveListAdapter;
import com.example.andradejoseph.change12_ver_2.custom.CustomFragment;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.utils.C4DbHelperFunctions;
import com.example.andradejoseph.change12_ver_2.utils.GridSpacingItemDecoration;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by ANDRADEJOSEPH on 4/5/2017.
 */

public class Change12CurrentWaveConsolidates extends CustomFragment{
    public static final String EXTRA_WAVE_NUM = "wave_num";
    public static final String EXTRA_WAVE_ID = "wave_id";

    private RecyclerView mRecyclerView;

    DiscpleLab discpleLab = null;
    Change12Lab change12Lab = null;
    C4DbHelperFunctions c4DbHelperFunction;
    Change12 change12;
    String waveId = "";

    private BarChart mChart;

    public static Change12CurrentWaveConsolidates newInstance(String waveId) {
        Bundle args = new Bundle();
        args.putString(EXTRA_WAVE_ID, waveId);

        Change12CurrentWaveConsolidates fragment = new Change12CurrentWaveConsolidates();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        discpleLab = DiscpleLab.get(getContext());
        change12Lab = Change12Lab.get(getContext());
        c4DbHelperFunction = new C4DbHelperFunctions(getContext());
        waveId = getArguments().getString(EXTRA_WAVE_ID);
        change12 = change12Lab.getChange12(waveId);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_wave_consolidates, container, false);
        init(v);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.articles);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        updateWaveConsolidateList();

        // create a new chart object
        mChart = new BarChart(getActivity());
        mChart.getDescription().setEnabled(false);

//        MyMarkerView mv = new MyMarkerView(getActivity(), R.layout.custom_marker_view);
        mChart.setDrawGridBackground(false);
        mChart.setDrawBarShadow(false);

        mChart.setData(generateBarData(1, 20000, 12));

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        mChart.getAxisRight().setEnabled(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setEnabled(false);

        RelativeLayout parent = (RelativeLayout) v.findViewById(R.id.parentLayout);
        parent.addView(mChart);

        return v;
    }

    private void updateWaveConsolidateList() {

        final List<Changee> changees = change12Lab.getWaveChangee(waveId);
        final List<Changee> myChangees = new ArrayList<>();
        for (Changee changee: changees) {
            final Disciple disciple = discpleLab.getDisciple(changee.getChangee().trim());
            boolean is_leader = c4DbHelperFunction.can_edit_and_delete_user(disciple.getDiscipler());
            if(is_leader) {
                Log.d("CHANGE12CurrentWave", changee.getChange_12() + " " + changee.getChangee());
                myChangees.add(changee);
            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getContext().getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void init(View view) {
        super.init(view);



        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("Change 12 Wave " + change12.getWave_num());
    }


    protected BarData generateBarData(int dataSets, float range, int count) {

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();

        for(int i = 0; i < dataSets; i++) {

            ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

//            entries = FileUtils.loadEntriesFromAssets(getActivity().getAssets(), "stacked_bars.txt");

            for(int j = 0; j < count; j++) {
                entries.add(new BarEntry(j, (float) (Math.random() * range) + range / 4));
            }

            BarDataSet ds = new BarDataSet(entries, getLabel(i));
            ds.setColors(ColorTemplate.VORDIPLOM_COLORS);
            sets.add(ds);
        }

        BarData d = new BarData(sets);
        return d;
    }

    private String[] mLabels = new String[] { "Company A", "Company B", "Company C", "Company D", "Company E", "Company F" };
//    private String[] mXVals = new String[] { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec" };

    private String getLabel(int i) {
        return mLabels[i];
    }


}
