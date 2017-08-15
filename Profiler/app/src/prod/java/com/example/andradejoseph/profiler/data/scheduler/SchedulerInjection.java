package com.example.andradejoseph.profiler.data.scheduler;

/**
 * Created by ANDRADEJOSEPH on 5/12/2017.
 */

public class SchedulerInjection {
    public static BaseSchedulerProvider baseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
