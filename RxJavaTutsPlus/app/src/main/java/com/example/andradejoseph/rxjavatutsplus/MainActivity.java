package com.example.andradejoseph.rxjavatutsplus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    private final String  TAG = "MainACtivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just(1,2,3)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Observer);
    }

    Observer<Integer> Observer = new Observer<Integer>() {
        @Override
        public void onSubscribe(Disposable d) {
            Log.e(TAG, "onSubscribe" + Thread.currentThread().getName());
        }


        @Override
        public void onNext(Integer integer) {
            Log.e(TAG, "onNext: " + integer + Thread.currentThread().getName());
        }

        @Override
        public void onError(Throwable e) {
            Log.e(TAG, "onError: ");
        }

        @Override
        public void onComplete() {
            Log.e(TAG, "onComplete: All Done!" + Thread.currentThread().getName());
        }
    };
}
