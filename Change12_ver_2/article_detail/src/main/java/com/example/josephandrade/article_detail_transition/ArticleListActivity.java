package com.example.josephandrade.article_detail_transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.example.josephandrade.article_detail_transition.adapter.ArticleAdapter;
import com.example.josephandrade.article_detail_transition.model.Article;
import com.example.josephandrade.article_detail_transition.utils.ArticlesUtils;
import com.example.josephandrade.article_detail_transition.utils.TransitionInformation;

import java.util.List;


public class ArticleListActivity extends AppCompatActivity {

    static RecyclerView mRecyclerView;
    /**
     *  Current transition information
     */
    private final TransitionInformation mTransitionInformation = new TransitionInformation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);

        initializeList(ArticlesUtils.mockArticles());
    }

    private void initializeList(List<Article> articles) {
        mRecyclerView = (RecyclerView) findViewById(R.id.articles);
        assert mRecyclerView != null;

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final ArticleAdapter articleAdapter = new ArticleAdapter(articles);
        articleAdapter.setOnArticleClickedListener(new ArticleAdapter.OnArticleClickedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onArticleClicked(Article article, ArticleAdapter.ViewHolder articleViewHolder, PointF touchPoint) {
                navigateToDetails(article, articleViewHolder, touchPoint);
            }
        });

        mRecyclerView.setAdapter(articleAdapter);
    }


    /**
     * Navigate to details.
     *
     * @param article           Article.
     * @param articleViewHolder Article view holder.
     * @param touchPoint        Touch point.
     */
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
                                ActivityOptions.makeSceneTransitionAnimation(ArticleListActivity.this, articleViewHolder.ivCoveringImage, articleViewHolder.ivCoveringImage.getTransitionName());
                        intent[0] = new Intent(ArticleListActivity.this, DetailsActivity.class);
                        intent[0].putExtra(DetailsActivity.EXTRA_ARTICLE, article);
                        startActivity(intent[0], options.toBundle());
                    }
                });
                circularReveal.start();

            } else {
                intent[0] = new Intent(ArticleListActivity.this, DetailsActivity.class);
                intent[0].putExtra(DetailsActivity.EXTRA_ARTICLE, article);
                startActivity(intent[0]);
            }

        }
    }

    /**
     * Reverse transition from details.
     */
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

    @Override
    protected void onResume() {
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            reverseTransition();
        }
    }
}
