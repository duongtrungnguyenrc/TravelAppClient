package com.main.travelApp.response;

import com.main.travelApp.models.MinimizeTour;

import java.util.List;

public class AllTourResponse {
    private int pages;
    private List<MinimizeTour> tours;

    public AllTourResponse() {
    }

    public AllTourResponse(int pages, List<MinimizeTour> tours) {
        this.pages = pages;
        this.tours = tours;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<MinimizeTour> getTours() {
        return tours;
    }

    public void setTours(List<MinimizeTour> tours) {
        this.tours = tours;
    }
}
