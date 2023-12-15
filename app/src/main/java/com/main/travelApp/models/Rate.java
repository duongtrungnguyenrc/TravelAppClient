package com.main.travelApp.models;

public class Rate {
    private String id;
    private String username;
    private String avatar;
    private int ratedStar;
    private String content;
    private boolean isActive;

    public Rate(String username, String avatar, int ratedStar, String content, boolean isActive) {
        this.username = username;
        this.avatar = avatar;
        this.ratedStar = ratedStar;
        this.content = content;
        this.isActive = isActive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRatedStar() {
        return ratedStar;
    }

    public void setRatedStar(int ratedStar) {
        this.ratedStar = ratedStar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
