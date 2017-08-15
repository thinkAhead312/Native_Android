package com.example.andradejoseph.profiler.data.scheduler;

import com.example.andradejoseph.profiler.util.BaseSchedulerProvider;
import com.example.andradejoseph.profiler.util.ImmediateSchedulerProvider;

/**
 * Get the Immediate Scheduler for test
 * Created by ANDRADEJOSEPH on 5/12/2017.
 */

public class SchedulerInjection {
    public static BaseSchedulerProvider baseSchedulerProvider() {
        return ImmediateSchedulerProvider.getInstance();
    }
}
