package com.main.travelApp.request;

public class ActivitiesRecordRequest {
    private Long tourId;
    private Long blogId;

    public ActivitiesRecordRequest(Long tourId, Long blogId) {
        this.tourId = tourId;
        this.blogId = blogId;
    }
}
