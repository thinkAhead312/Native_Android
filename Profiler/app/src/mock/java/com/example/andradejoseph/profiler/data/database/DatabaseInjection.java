package com.example.andradejoseph.profiler.data.database;

/**
 * Created by ANDRADEJOSEPH on 5/12/2017.
 */

public class DatabaseInjection {
    public static DatabaseSource provideDatabaseSource() {
        return FakeDatabaseService.getInstance();
    }
}