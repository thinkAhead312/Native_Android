package com.example.josephandrade.article_detail_transition;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.josephandrade.article_detail_transition.model.Article;
import com.jaouan.viewsfrom.Views;

public class LessonsDetailActivity extends AppCompatActivity {
    /**
     *  Current Transition Information
     */
    /**
     * Extra key for card_lessons.
     */
    public static final String EXTRA_ARTICLE = "card_lessons";
    Toolbar mToolbar;
    TextView mTitle;
    TextView mDate;
    ImageView mHeader, mLessonImage;
    View mTitleContent;
    TextView mDetail;


    ViewGroup mDetailsContent;
     Article article;


    public static Intent newIntent(Context packageContext, Article article) {
        Intent i = new Intent(packageContext, LessonsDetailActivity.class);
        i.putExtra(EXTRA_ARTICLE, article);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        article = (Article) getIntent().getExtras().get(EXTRA_ARTICLE);
        if (article.getDate().trim().equals("1")) {
            setContentView(R.layout.change12_lesson1);
        }
        if (article.getDate().trim().equals("2")) {
            setContentView(R.layout.change12_lesson2);
        }
        if (article.getDate().trim().equals("3")) {
            setContentView(R.layout.change12_lesson3);
        }
        if (article.getDate().trim().equals("4")) {
            setContentView(R.layout.change12_lesson4);
        }
        if (article.getDate().trim().equals("5")) {
            setContentView(R.layout.change12_lesson5);
        }

//        ButterKnife.bind(this);

        init();


    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mHeader = (ImageView) findViewById(R.id.header);
        mLessonImage = (ImageView) findViewById(R.id.lesson_image);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            beginEnterTransition();
        }
        mHeader.setBackgroundResource(article.getHeader());

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        final CollapsingToolbarLayout mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= mToolbar.getHeight()){
                    mCollapsingToolbar.setTitle(article.getTitle());

                }else {
                    mCollapsingToolbar.setTitle("");
                }
            }
        });
    }

    /**
     * Begins enter transition.
     */
    private void beginEnterTransition() {

        LinearLayout mDetailsContent = (LinearLayout) findViewById(R.id.mDetailsContent);
        Views.from(mDetailsContent).animateWith(this, R.anim.translaterightandfadein).withDelayBetweenEachChild(50).start();
    }

    /**
     * Binds card_lessons datas.
     */


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }



}
