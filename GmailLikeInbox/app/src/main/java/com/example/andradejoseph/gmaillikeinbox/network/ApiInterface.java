package com.example.andradejoseph.gmaillikeinbox.network;

import com.example.andradejoseph.gmaillikeinbox.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ANDRADEJOSEPH on 5/5/2017.
 */

public interface ApiInterface {

    @GET("inbox.json")
    Call<List<Message>> getInbox();
}
