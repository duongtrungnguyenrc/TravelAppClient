package com.main.travelApp.models;

import java.util.List;

public class Post {
    private long id;
    private String title;
    private String type;
    private String time;
    private String author;
    private String img;
    private List<Paragraph> paragraphs;
    private String description;

    public Post(long id, String title, String type, String time, String author, String img, List<Paragraph> paragraphs) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.time = time;
        this.author = author;
        this.img = img;
        this.paragraphs = paragraphs;
    }

    public Post() {
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

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
