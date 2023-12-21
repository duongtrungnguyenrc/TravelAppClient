package com.main.travelApp.response;

import com.main.travelApp.models.Post;
import com.main.travelApp.models.Tour;

import java.util.List;

public class SearchResponse {
    private List<Tour> tours;
    private List<Post> blogs;

    public List<Tour> getTours() {
        return tours;
    }

    public List<Post> getPosts() {
        return blogs;
    }
}
