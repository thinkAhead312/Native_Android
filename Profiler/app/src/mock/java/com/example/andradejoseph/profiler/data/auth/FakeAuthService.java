package com.example.andradejoseph.profiler.data.auth;

import com.example.andradejoseph.profiler.data.GenericTestData;

import io.reactivex.Completable;
import io.reactivex.Maybe;

/**
 * Created by ANDRADEJOSEPH on 5/11/2017.
 */

public class FakeAuthService implements AuthSource{

    boolean returnFailure = false;

    public FakeAuthService() {

    }

    public static AuthSource getInstance() {
        return new FakeAuthService();
    }

    @Override
    public Completable createAccount(Credentials cred) {
       if(returnFailure) {
           return Completable.error(new Exception());
       }

        return Completable.complete();
    }

    @Override
    public Completable attemptLogin(Credentials cred) {
        if(returnFailure) {
            return Completable.error(new Exception());
        }

        return Completable.complete();
    }

    @Override
    public Completable deleteUser() {
        if(returnFailure) {
            return Completable.error(new Exception());
        }

        return Completable.complete();
    }

    @Override
    public Maybe<User> getUser() {
        if(returnFailure) {
             Maybe.error(new Exception());
        }

        return Maybe.just(GenericTestData.getUser());
    }

    @Override
    public Completable logUserOut() {
        if(returnFailure) {
            return Completable.error(new Exception());
        }

        return Completable.complete();
    }

    @Override
    public Completable reAuthenticateUser(String password) {
        if(returnFailure) {
            return Completable.error(new Exception());
        }

        return Completable.complete();
    }

    @Override
    public void setReturnFail(boolean bool) {
        returnFailure = bool;
    }


}
