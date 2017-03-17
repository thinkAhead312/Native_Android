package com.example.andradejoseph.change12_ver_2.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LessonsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LessonsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LessonsFragment extends Fragment {
    private ImageView imageView;
    static RecyclerView mRecyclerView;
    private final TransitionInformation mTransitionInformation = new TransitionInformation();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_b, container, false);

        initializeList(ArticlesUtils.mockArticles(), v);



        return  v;
    }

    private void initializeList(List<Article> articles, View v) {
        mRecyclerView = (RecyclerView) v.findViewById(R.id.articles);
        assert mRecyclerView != null;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        final ArticleAdapter articleAdapter = new ArticleAdapter(articles);


        articleAdapter.setOnArticleClickedListener(new ArticleAdapter.OnArticleClickedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onArticleClicked(Article article, ArticleAdapter.ViewHolder articleViewHolder, PointF touchPoint) {
                navigateToDetails(article, articleViewHolder, touchPoint);
            }
        });

        articleAdapter.setTutorialButtonClickedListener(new ArticleAdapter.OnTutorialButtonClickListener() {
            @Override
            public void onTutorialButtonClick() {
                Intent intent = new Intent(getContext(), Introduction.class);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(articleAdapter);
    }

    private void navigateToDetails(final Article article, final ArticleAdapter.ViewHolder articleViewHolder, PointF touchPoint) {
        final Intent[] intent = new Intent[1];
        synchronized (mTransitionInformation) {
            //-Disallow multiple transtion at the same time
            if (mTransitionInformation.getCoveringView() != null) {
                return;
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // - Remember transition to reverse transition.
                mTransitionInformation.rememberTransition(articleViewHolder.ivCoveringImage, touchPoint);
                // - Reveal new covering image.
                articleViewHolder.ivCoveringImage.setVisibility(View.VISIBLE);
                final Animator circularReveal = ViewAnimationUtils.createCircularReveal(articleViewHolder.ivCoveringImage, (int) touchPoint.x, (int) touchPoint.y, 0, articleViewHolder.ivCoveringImage.getWidth());
                circularReveal.setInterpolator(new AccelerateInterpolator());
                circularReveal.setDuration(150);
                circularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // - Start details activity.
                        final ActivityOptions options =
                                ActivityOptions.makeSceneTransitionAnimation(getActivity(), articleViewHolder.ivCoveringImage, articleViewHolder.ivCoveringImage.getTransitionName());
                        intent[0] = LessonsDetailActivity.newIntent(getContext(), article);
                        startActivity(intent[0], options.toBundle());
                    }
                });
                circularReveal.start();

            } else {
                intent[0] = LessonsDetailActivity.newIntent(getContext(), article);
                startActivity(intent[0]);
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reverseTransition();
        }
    }

    private void reverseTransition() {
        synchronized (mTransitionInformation) {
            if (mTransitionInformation.getCoveringView() != null) {
                final Animator circularReveal = ViewAnimationUtils.createCircularReveal(mTransitionInformation.getCoveringView(), (int) mTransitionInformation.getTouchPoint().x, (int) mTransitionInformation.getTouchPoint().y, mTransitionInformation.getCoveringView().getWidth(), 0);
                circularReveal.setInterpolator(new DecelerateInterpolator());
                circularReveal.setDuration(150);
                circularReveal.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mTransitionInformation.getCoveringView().setVisibility(View.INVISIBLE);
                        mTransitionInformation.clear();
                    }
                });
                circularReveal.start();
            }
        }
    }
}
