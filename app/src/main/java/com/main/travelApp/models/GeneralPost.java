package com.main.travelApp.models;

public class GeneralPost {
    private int id;
    private String title;
    private String type;
    private String time;
    private String author;
    private String img;
    private String description;

    public GeneralPost(){}

    public GeneralPost(int id, String title, String type, String time, String author, String img, String description) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
        this.author = author;
        this.img = img;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

