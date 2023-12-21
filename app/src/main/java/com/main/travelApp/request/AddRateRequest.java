package com.main.travelApp.request;

public class AddRateRequest {
    private Long tourId;
    private long blogId;
    private String comment;
    private int star;

    public AddRateRequest(long tourId, long blogId, String comment, int star) {
        this.tourId = tourId;
        this.blogId = blogId;
        this.comment = comment;
        this.star = star;
    }

    public AddRateRequest() {
    }

    public long getTourId() {
        return tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }
}
