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

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return active;
    }
}
