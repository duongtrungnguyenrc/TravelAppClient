package com.main.travelApp.response;

import com.main.travelApp.models.MinimizePost;
import com.main.travelApp.models.MinimizeTour;

import java.util.List;

public class RecentActivitiesResponse {
    private List<MinimizeTour> recentTours;
    private List<MinimizePost> recentPosts;

    public List<MinimizeTour> getRecentTours() {
        return recentTours;
    }

    public List<MinimizePost> getRecentPosts() {
        return recentPosts;
    }
}
