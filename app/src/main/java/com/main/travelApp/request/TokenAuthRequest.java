package com.main.travelApp.request;

public class TokenAuthRequest {
    private String token;

    public TokenAuthRequest(String token) {
        this.token = token;
    }

    public TokenAuthRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
