package com.main.travelApp.models;

public class Paragraph {
    private String content;
    private String imgName;
    private String imgSrc;

    public Paragraph(String content, String imgName, String imgSrc) {
        this.content = content;
        this.imgName = imgName;
        this.imgSrc = imgSrc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }
}
