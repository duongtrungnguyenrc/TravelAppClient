package com.main.travelApp.models;

import java.util.List;

public class Overview {
    private Long id;
    private String backgroundImage;
    private List<Paragraph> paragraphs;

    public Long getId() {
        return id;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }
}
