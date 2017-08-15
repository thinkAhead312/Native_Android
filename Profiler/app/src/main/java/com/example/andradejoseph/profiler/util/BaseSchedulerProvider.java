package com.example.andradejoseph.profiler.util;

import io.reactivex.Scheduler;

/**
 * Created by ANDRADEJOSEPH on 5/12/2017.
 */

public interface BaseSchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();
}
