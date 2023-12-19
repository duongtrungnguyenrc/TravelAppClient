package com.main.travelApp.response;

import com.main.travelApp.models.GeneralPost;

import java.util.List;

public class AllPostResponse {
    private int pages;
    private List<GeneralPost> posts;

    public AllPostResponse() {
    }

    public AllPostResponse(int pages, List<GeneralPost> posts) {
        this.pages = pages;
        this.posts = posts;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<GeneralPost> getPosts() {
        return posts;
    }

    public void setPosts(List<GeneralPost> posts) {
        this.posts = posts;
    }
}
