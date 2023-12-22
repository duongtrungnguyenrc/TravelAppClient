package com.main.travelApp.models;

public class LoginHistory {
    private Long id;
    private String loggedDate;
    private String userDevice;
    private String ipAddress;
    private String avatar;

    public LoginHistory() {
    }

    public LoginHistory(Long id, String loggedDate, String userDevice, String ipAddress, String userAvatar) {
        this.id = id;
        this.loggedDate = loggedDate;
        this.userDevice = userDevice;
        this.ipAddress = ipAddress;
        this.avatar = userAvatar;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoggedDate() {
        return loggedDate;
    }

    public void setLoggedDate(String loggedDate) {
        this.loggedDate = loggedDate;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String userAvatar) {
        this.avatar = userAvatar;
    }
}
