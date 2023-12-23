package com.main.travelApp.response;

import com.main.travelApp.models.MinimizePost;

import java.util.List;

public class AllPostResponse {
    private int pages;
    private List<MinimizePost> posts;

    public AllPostResponse() {
    }

    public AllPostResponse(int pages, List<MinimizePost> posts) {
        this.pages = pages;
        this.posts = posts;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<MinimizePost> getPosts() {
        return posts;
    }

    public void setPosts(List<MinimizePost> posts) {
        this.posts = posts;
    }
}
