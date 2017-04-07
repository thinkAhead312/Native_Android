package com.example.andradejoseph.simpleregistration.model;

import java.util.UUID;

/**
 * Created by ANDRADEJOSEPH on 3/27/2017.
 */

public class User {

    private UUID user_id;
    private String user_name;
    private String password;

    public User() {}

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id() {
        user_id = UUID.randomUUID();
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
