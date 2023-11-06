package com.main.travelApp.models;

public class User {
    private String id;
    private String fullName;
    private String email;
    private String address;
    private String password;
    private String phone;
    private boolean active;

    public User(String id, String fullName, String email, String address, String password, String phone, boolean active) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.password = password;
        this.phone = phone;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
