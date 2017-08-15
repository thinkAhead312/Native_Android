package com.example.andradejoseph.profiler.login;

import android.util.Log;

import com.example.andradejoseph.profiler.R;
import com.example.andradejoseph.profiler.data.auth.AuthSource;
import com.example.andradejoseph.profiler.data.auth.Credentials;
import com.example.andradejoseph.profiler.data.auth.User;
import com.example.andradejoseph.profiler.util.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;

/**
 * Created by ANDRADEJOSEPH on 5/9/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private AuthSource authSource;
    private LoginContract.View view;
    private CompositeDisposable compositeDisposable;
    private BaseSchedulerProvider schedulerProvider;

    public LoginPresenter (AuthSource authSource,
                           LoginContract.View view,
                           BaseSchedulerProvider schedulerProvider) {

        this.authSource = authSource;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        this.compositeDisposable = new CompositeDisposable();

    }

    @Override
    public void onLogInClick() {
        String userEmail = view.getEmail();
        String userPassword = view.getPassword();
        if (userEmail.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
        } else if (userPassword.isEmpty()) {
            view.makeToast(R.string.error_empty_input);
        } else if (!userEmail.contains("@")) {
            view.makeToast(R.string.error_invalid_email);
        } else if (userPassword.length() > 19) {
            view.makeToast(R.string.error_password_too_long);
        } else {
            attemptLogIn(new Credentials(userPassword, "", userEmail));
        }
    }

    public void attemptLogIn(Credentials cred) {
        view.showProgressIndicator(true);
        compositeDisposable.add(
                authSource.attemptLogin(cred)
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableCompletableObserver() {
                            @Override
                            public void onComplete() {
                                view.showProgressIndicator(false);
                                view.startProfileActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.toString());
                            }
                        })
        );
    }

    /**
     * When this Activity first starts, check and see if there is a currently logged in user.
     * If so, send the user to their Profile Page.
     */
    public void getUser() {
        view.showProgressIndicator(true);
        compositeDisposable.add(
                authSource.getUser()
                        .subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.ui())
                        .subscribeWith(new DisposableMaybeObserver<User>() {
                            /**
                             * If a uses is found, send them straight to their profile
                             * @param user
                             */
                            @Override
                            public void onSuccess(User user) {
                                view.startProfileActivity();
                            }

                            @Override
                            public void onError(Throwable e) {
                                view.showProgressIndicator(false);
                                view.makeToast(e.getMessage());
                            }

                            /**
                             * This method fires, when we don't find a user in the Auth Database.
                             * This is not considered an error!!!
                             */
                            @Override
                            public void onComplete() {
                                view.showProgressIndicator(false);
                            }
                        })
        );
    }

    @Override
    public void onCreateClick() {
        view.startCreateAccountActivity();
    }

    @Override
    public void subscribe() {
        getUser();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
