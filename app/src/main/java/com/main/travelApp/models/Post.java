package com.main.travelApp.models;

public class Post {
    private long id;
    private String title;
    private String demoContent;
    private String postTime;
    private String author;
    private String thumbnail;

    public Post(long id, String title, String demoContent, String postTime, String author, String thumbnail) {
        this.id = id;
        this.title = title;
        this.demoContent = demoContent;
        this.postTime = postTime;
        this.author = author;
        this.thumbnail = thumbnail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDemoContent() {
        return demoContent;
    }

    public void setDemoContent(String demoContent) {
        this.demoContent = demoContent;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
