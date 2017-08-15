package com.example.andradejoseph.profiler.util;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ANDRADEJOSEPH on 5/12/2017.
 */

public class ImmediateSchedulerProvider implements BaseSchedulerProvider{

    private static ImmediateSchedulerProvider INSTANCE;

    private ImmediateSchedulerProvider() {

    }

    public static synchronized ImmediateSchedulerProvider getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new ImmediateSchedulerProvider();
        }
        return INSTANCE;
    }

    @Override
    public Scheduler computation() {
        return Schedulers.trampoline(); //it means things happen when they're called
    }

    @Override
    public Scheduler io() {
        return Schedulers.trampoline();
    }

    @Override
    public Scheduler ui() {
        return Schedulers.trampoline();
    }
}
