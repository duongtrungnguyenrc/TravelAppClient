package com.main.travelApp.response;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.models.MinimizeTour;

import java.util.List;

public class SearchResponse {
    private List<MinimizeTour> tours;
    private List<MinimizePost> blogs;

    public List<MinimizeTour> getTours() {
        return tours;
    }

    public List<MinimizePost> getPosts() {
        return blogs;
    }
}
