package com.badoo.chateau.example.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.badoo.barf.mvp.MvpPresenter;
import com.badoo.chateau.example.R;

import java.util.ArrayList;
import java.util.Collection;

public class BaseActivity extends AppCompatActivity {

    private final Collection<MvpPresenter> mPresenters = new ArrayList<>();
    @Nullable
    private BackPressedListener mBackPressedListener;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        else {
            super.setTitle(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleId);
        }
        else {
            super.setTitle(titleId);
        }
    }

    /**
     * Register a presenter to ensure it's lifecycle methods are called.¬
     */
    public void registerPresenter(@NonNull MvpPresenter presenter) {
        mPresenters.add(presenter);
    }

    public void registerBackPressedListener(@NonNull BackPressedListener listener) {
        mBackPressedListener = listener;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (MvpPresenter presenter : mPresenters) {
            presenter.onStart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (MvpPresenter presenter : mPresenters) {
            presenter.onStop();
        }
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedListener == null || !mBackPressedListener.onBackPressed()) {
            super.onBackPressed();
        }
    }

    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar_actionbar);
    }

    public interface BackPressedListener {

        boolean onBackPressed();
    }

}
