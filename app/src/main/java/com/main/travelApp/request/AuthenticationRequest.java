package com.main.travelApp.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthenticationRequest {
    String email;
    String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
