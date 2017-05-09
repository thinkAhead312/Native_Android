package com.example.andradejoseph.rxjavawithretrofit.api;

import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by ANDRADEJOSEPH on 5/5/2017.
 */

public interface TwitterApi {
    @GET("search/tweets.json")
    Flowable<SearchResponse> searchTweets(@Query("q") String query);

    @GET("trends/place.json")
    Observable<List<TrendsResponse>> getTrends(@Query("id") String placeId);
}
