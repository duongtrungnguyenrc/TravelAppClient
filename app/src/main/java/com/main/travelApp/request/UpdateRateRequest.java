package com.main.travelApp.request;

public class UpdateRateRequest {
    private long id;
    private String comment;
    private int star;

    public UpdateRateRequest() {
    }

    public UpdateRateRequest(long id, String comment, int star) {
        this.id = id;
        this.comment = comment;
        this.star = star;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
