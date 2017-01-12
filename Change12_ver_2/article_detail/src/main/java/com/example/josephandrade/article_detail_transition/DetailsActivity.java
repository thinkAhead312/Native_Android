package com.example.josephandrade.article_detail_transition;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.josephandrade.article_detail_transition.model.Article;
import com.jaouan.viewsfrom.Views;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    /**
     *  Current Transition Information
     */
    /**
     * Extra key for article.
     */
    public static final String EXTRA_ARTICLE = "article";
    Toolbar mToolbar;
    TextView mTitle;
    TextView mDate;
    ImageView mHeader;
    View mTitleContent;
    TextView mDetail;

    ViewGroup mDetailsContent;

    public static Intent newIntent(Context packageContext, Article answerIsTrue) {
        Intent i = new Intent(packageContext, DetailsActivity.class);
        i.putExtra(EXTRA_ARTICLE, answerIsTrue);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
//        ButterKnife.bind(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTitle = (TextView) findViewById(R.id.title);
        mDate = (TextView) findViewById(R.id.date);
        mHeader = (ImageView) findViewById(R.id.header);
        mTitleContent = (View) findViewById(R.id.title_content);
        mDetail = (TextView) findViewById(R.id.detail);
        mDetailsContent = (ViewGroup) findViewById(R.id.details_content);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindArticleDatas();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            beginEnterTransition();
        }
    }

    /**
     * Begins enter transition.
     */
    private void beginEnterTransition() {
        Views.from(mDetailsContent).animateWith(this, R.anim.translaterightandfadein).withDelayBetweenEachChild(50).start();
    }

    /**
     * Binds article datas.
     */
    private void bindArticleDatas() {
        final Article article = (Article) getIntent().getExtras().get(EXTRA_ARTICLE);
        mHeader.setImageResource(article.getHeader());
        mTitle.setText(article.getTitle());
        mDate.setText(article.getDate());
        mTitleContent.setBackgroundResource(article.getBackgroundColor());
    }

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
