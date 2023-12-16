package com.main.travelApp.models;

public class Paragraph {
    private long id;
    private String content;
    private Image image;
    private long layout;

    public long getID() { return id; }
    public void setID(long value) { this.id = value; }

    public String getContent() { return content; }
    public void setContent(String value) { this.content = value; }

    public Image getImage() { return image; }
    public void setImage(Image value) { this.image = value; }

    public long getLayout() { return layout; }
    public void setLayout(long value) { this.layout = value; }
}
