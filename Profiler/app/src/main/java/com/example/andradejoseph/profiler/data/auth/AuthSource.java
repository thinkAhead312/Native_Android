package com.example.andradejoseph.profiler.data.auth;


import android.support.annotation.VisibleForTesting;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by ANDRADEJOSEPH on 5/11/2017.
 */


//this class describe how the presenter calls the service
public interface AuthSource {

    Completable createAccount(Credentials cred);

    Completable attemptLogin(Credentials cred);

    Completable deleteUser();

    Maybe<User> getUser();

    Completable logUserOut();

    Completable reAuthenticateUser(String password);

    void setReturnFail(boolean bool);
}
