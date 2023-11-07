package com.main.travelApp.models;

import lombok.Data;

@Data
public class User {
    private String id;
    private String fullName;
    private String email;
    private String address;
    private String password;
    private String phone;
    private boolean active;
}
