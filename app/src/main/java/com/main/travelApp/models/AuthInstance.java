package com.main.travelApp.models;

import lombok.Data;

@Data
public class AuthInstance {
    private String accessToken;
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public User getUser() {
        return user;
    }
}
