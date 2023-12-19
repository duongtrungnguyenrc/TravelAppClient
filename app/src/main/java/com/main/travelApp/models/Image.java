package com.main.travelApp.models;

public class Image {
    private String src;
    private String name;

    public Image(String src, String name) {
        this.src = src;
        this.name = name;
    }

    public Image() {
    }

    public String getSrc() { return src; }
    public void setSrc(String value) { this.src = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }
}
