package com.example.andradejoseph.profiler.login;

import android.support.annotation.StringRes;

import com.example.andradejoseph.profiler.BasePresenter;
import com.example.andradejoseph.profiler.BaseView;

/**
 * Created by ANDRADEJOSEPH on 5/9/2017.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        //more on setting up the view
        void makeToast(@StringRes int stringId);
        void makeToast(String message);

        String getEmail();

        String getPassword();

        void startProfileActivity();

        void startCreateAccountActivity();

        void setPresenter(LoginContract.Presenter presenter);

        void showProgressIndicator(boolean show);

    }

    interface Presenter extends BasePresenter {
    //more on Events
        void onLogInClick();

        void onCreateClick();

    }
}
