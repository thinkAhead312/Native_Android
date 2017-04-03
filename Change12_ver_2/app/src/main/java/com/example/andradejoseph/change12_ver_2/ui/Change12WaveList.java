package com.example.andradejoseph.change12_ver_2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.example.andradejoseph.change12_ver_2.R;
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

public class Change12WaveList  extends Fragment {
    private ImageView imageView;
    static RecyclerView mRecyclerView;



    private final TransitionInformation mTransitionInformation = new TransitionInformation();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_change12_wave_list, container, false);


        return  v;
    }



}
