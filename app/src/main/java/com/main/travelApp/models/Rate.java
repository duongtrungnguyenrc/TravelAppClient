package com.main.travelApp.models;

import com.google.gson.annotations.SerializedName;

public class Rate {
    private String id;
    private String username;
    private String email;
    private int star;
    private String avatar;
    private int ratedStar;
    private String ratedDate;
    @SerializedName("comment")
    private String content;
    @SerializedName("active")
    private boolean isActive;

    public Rate(String username, String avatar, int ratedStar, String content, boolean isActive) {
        this.username = username;
        this.avatar = avatar;
        this.ratedStar = ratedStar;
        this.content = content;
        this.isActive = isActive;
    }

    public Rate(String id, String username, String email, int star, String avatar, int ratedStar, String ratedDate, String content, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.star = star;
        this.avatar = avatar;
        this.ratedStar = ratedStar;
        this.ratedDate = ratedDate;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getRatedDate() {
        return ratedDate;
    }

    public void setRatedDate(String ratedDate) {
        this.ratedDate = ratedDate;
    }
}
