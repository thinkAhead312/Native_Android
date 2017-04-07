package com.example.andradejoseph.change12_ver_2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.example.andradejoseph.change12_ver_2.Change12ManualActivity;
import com.example.andradejoseph.change12_ver_2.LoginActivity;
import com.example.andradejoseph.change12_ver_2.R;
import com.example.andradejoseph.change12_ver_2.WaveDetailActivity;
import com.example.andradejoseph.change12_ver_2.adapter.WaveListAdapter;
import com.example.andradejoseph.change12_ver_2.custom.CustomFragment;
import com.example.andradejoseph.change12_ver_2.model.Change12;
import com.example.andradejoseph.change12_ver_2.model.Change12Lab;
import com.example.andradejoseph.change12_ver_2.model.Changee;
import com.example.andradejoseph.change12_ver_2.model.Disciple;
import com.example.andradejoseph.change12_ver_2.model.DiscpleLab;
import com.example.andradejoseph.change12_ver_2.utils.RecyclerClickListener;
import com.example.josephandrade.article_detail_transition.Introduction;
import com.example.josephandrade.article_detail_transition.LessonsDetailActivity;
import com.example.josephandrade.article_detail_transition.adapter.ArticleAdapter;
import com.example.josephandrade.article_detail_transition.model.Article;
import com.example.josephandrade.article_detail_transition.utils.ArticlesUtils;
import com.example.josephandrade.article_detail_transition.utils.TransitionInformation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ANDRADEJOSEPH on 3/23/2017.
 */

public class Change12WaveList  extends CustomFragment {
    private ImageView imageView;
    static RecyclerView mRecyclerView;
    WaveListAdapter mWaveListAdapter;
    Change12Lab change12;
    private final TransitionInformation mTransitionInformation = new TransitionInformation();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        change12 = Change12Lab.get(getContext());
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_wave_list, container, false);
        init(v);
        mRecyclerView = (RecyclerView)v.findViewById(R.id.articles);
        assert mRecyclerView != null;
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateWaveList();
        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(), new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                List<Change12> change12s = change12.getChange12();
                Change12 change12 = change12s.get(position);
                Intent i = WaveDetailActivity.newIntent(getContext(),change12.getChange12_id());

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        new Pair<View, String>(view.findViewById(R.id.wave_item_card_view),
                                getString(R.string.transition_name_card))
                );
                ActivityCompat.startActivity(getActivity(), i, options.toBundle());

//                startActivity(i2);//, oc2.toBundle());
            }
        }));

        return  v;
    }

    @Override
    public void init(View view) {
        super.init(view);
        final AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setTitle("Consolidates");
    }

    private void updateWaveList() {

        List<Change12> change12s = change12.getChange12();
        if(mWaveListAdapter == null) {
            mWaveListAdapter = new WaveListAdapter(change12s,getContext());
            mRecyclerView.setAdapter(mWaveListAdapter);
        } else {
            mWaveListAdapter.setChange(change12s);
            mWaveListAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateWaveList();
    }
}

/*

 DiscpleLab discpleLab = DiscpleLab.get(getContext());

        List<Changee> changees = discpleLab.getWaveChangee("6");
        int countTotalChangee = 0;
        int countTotalGraduate =0;
        int countRemainedActive = 0;
        for(Changee changee: changees) {
            Log.d("Change12WaveList ", changee.getChange_12() + " " + changee.getChangee());
            countTotalChangee++;
            if(changee.getChange_5_ok().trim().equals("on")) {
                countTotalGraduate++;
                if(discpleLab.getRemainedActiveConsolidates(String.valueOf(changee.getChangee()))) {
                    countRemainedActive++;
                }
            }


        }

        Toast.makeText(getContext(),String.valueOf(countRemainedActive), Toast.LENGTH_SHORT).show();

        RoundCornerProgressBar mTotalProgessBar = (RoundCornerProgressBar) v.findViewById(R.id.total_consolidates_progress_bar);
        mTotalProgessBar.setMax(countTotalChangee);
        mTotalProgessBar.setProgress(countTotalGraduate);

        RoundCornerProgressBar mGraduatedProgressBar = (RoundCornerProgressBar) v.findViewById(R.id.graduated_consolidates_progress_bar);
        mGraduatedProgressBar.setMax(countTotalChangee);
        mGraduatedProgressBar.setProgress(countTotalGraduate);

        RoundCornerProgressBar mRemainedProgressBar = (RoundCornerProgressBar) v.findViewById(R.id.remained_consolidates_progress_bar);
        mRemainedProgressBar.setMax(countTotalGraduate);
        mRemainedProgressBar.setProgress(countRemainedActive);

 */
