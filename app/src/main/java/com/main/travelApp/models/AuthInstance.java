package com.main.travelApp.models;

import lombok.Data;

public class AuthInstance extends User{
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int NOT_ACTIVATED_CODE = 406;
    private String accessToken;
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
