package com.main.travelApp.request;

public class ConfirmCodeRequest {
    private String token;
    private String activateCode;

    public ConfirmCodeRequest(String token, String activateCode) {
        this.token = token;
        this.activateCode = activateCode;
    }

    public ConfirmCodeRequest() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getActivateCode() {
        return activateCode;
    }

    public void setActivateCode(String activateCode) {
        this.activateCode = activateCode;
    }
}
