package com.main.travelApp.response;

import com.main.travelApp.models.User;

public class SignUpResponse {
    private String confirmToken;
    private User user;

    public SignUpResponse(String confirmToken, User user) {
        this.confirmToken = confirmToken;
        this.user = user;
    }

    public SignUpResponse() {
    }

    public String getConfirmToken() {
        return confirmToken;
    }

    public void setConfirmToken(String confirmToken) {
        this.confirmToken = confirmToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
