package com.main.travelApp.response;

import com.main.travelApp.models.User;

import lombok.Data;

@Data
public class AuthenticationResponse {
    private String accessToken;
    private User user;
}
