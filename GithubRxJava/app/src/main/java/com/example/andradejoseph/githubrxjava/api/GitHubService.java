package com.example.andradejoseph.githubrxjava.api;

import com.example.andradejoseph.githubrxjava.model.GitHubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ANDRADEJOSEPH on 5/8/2017.
 */

public interface GitHubService {
    @GET("users/{user}/starred")
    Observable<List<GitHubRepo>> getStarredRepositories(@Path("user") String userName);
}
