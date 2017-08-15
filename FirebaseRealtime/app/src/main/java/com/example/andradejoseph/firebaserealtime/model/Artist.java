package com.example.andradejoseph.firebaserealtime.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by ANDRADEJOSEPH on 5/22/2017.
 */

@IgnoreExtraProperties
public class Artist {
    private String artistId;
    private String artistName;
    private String artistGenre;


    public Artist(){
        //this constructor is required
    }
    public Artist(String artistId, String artistName, String artistGenre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.artistGenre = artistGenre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtistGenre() {
        return artistGenre;
    }


}
